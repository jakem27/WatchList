import { useEffect, useState } from "react";

function FolderDirectory({ currFolder, setCurrFolder }) {
    const [folders, setFolders] = useState([]);
    const [folderStack, setFolderStack] = useState([]);
    const [showForm, setShowForm] = useState(false);
    const [newFolderName, setNewFolderName] = useState("");

    const parentFolder = folderStack[folderStack.length - 1];

    useEffect(() => {
        const doFetch = async () => {
            const response = await fetch("http://localhost:8080/api/folder/root", {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
            });
            const payload = await response.json();
            setFolderStack([payload]);
        }
        doFetch();
    }, []);

    useEffect(() => {
        if(!parentFolder) {
            return;
        }

        const doFetch = async () => {
            const response = await fetch(`http://localhost:8080/api/folder/${parentFolder.id}/children`, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
            });
            
            if(response.ok) {
                const payload = await response.json();
                setFolders(payload);
            }
            
        }
        doFetch();
    }, [folderStack]);

    async function handleAdd(event) {
        event.preventDefault();

        const folder = {
            name: newFolderName,
            is_public: false,
            parent_id: parentFolder ? parentFolder.id : null
        }

        const response = await fetch("http://localhost:8080/api/folder", {
            method: "POST",
            headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`,
                "Content-Type": "application/json"
            },
            body: JSON.stringify(folder)
        })

        if(response.ok) {
            const newFolder = await response.json();
            setFolders([...folders, newFolder]);

            setShowForm(false);
            setFolderName("");
        }
    }

    return (
        <div className="container">
            <button 
                disabled={folderStack.length <= 1}
                onClick={() => { setFolderStack(folderStack.slice(0, -1))}}
            >
                {`<`}
            </button>
            <h3>{!parentFolder || parentFolder.name === "root" ? "My WatchList" : parentFolder.name}</h3>
            <button onClick={() => setShowForm(true)}>+</button>
            <div className="list-group">
                {folders.map(folder => 
                    <div  className={"list-group-item list-group-item-action" + (currFolder && currFolder.name === folder.name ? " active" : "")}
                    onClick={() => setCurrFolder(folder)}
                    key = {folder.id}>
                        <span>{folder.name}</span>
                        <button onClick={() => setFolderStack([...folderStack, folder])}>{`>`}</button>
                    </div>)}
                {showForm && 
                    <form onSubmit={handleAdd}>
                        <input 
                            type="text" 
                            className="form-control" 
                            value={newFolderName}
                            onChange={(e) => setNewFolderName(e.target.value)}/>
                    </form>}
                    
            </div>            
        </div>
    )
}

export default FolderDirectory;