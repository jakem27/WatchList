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
            setCurrFolder(payload);
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
                setShowForm(false);
                setNewFolderName("");
            }
            
        }
        doFetch();
    }, [folderStack]);

    async function handleAdd(event) {
        event.preventDefault();

        const folder = {
            name: newFolderName,
            public: false,
            parentId: parentFolder ? parentFolder.id : null
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
            setNewFolderName("");
        }
    }

    return (
        <>
            <div className="d-flex justify-content-between align-items-center gap-2">
               <button 
                className="btn btn-link p-0"
                disabled={folderStack.length <= 1}
                onClick={() => { 
                    setFolderStack(folderStack.slice(0, -1));
                    setCurrFolder(parentFolder);
                }}
            >
                <i className="bi bi-arrow-left"></i>
            </button>
            <h3 className="mb-0">{!parentFolder || parentFolder.name === "root" ? "My WatchList" : parentFolder.name}</h3>
            <button className="btn btn-link p-0" onClick={() => setShowForm(true)}>
                <i className="bi bi-plus"></i>    
            </button> 
            </div>
            
            <div className="list-group mt-4">
                {folders.map(folder => 
                    <div  className={" d-flex justify-content-between align-items-center gap-2" +
                        " list-group-item list-group-item-action" + (currFolder && currFolder.name === folder.name ? " active" : "")}
                        onClick={() => {
                            if(currFolder === folder) {
                                setCurrFolder(parentFolder);
                            } else {
                                setCurrFolder(folder);
                            }
                        }}
                        key = {folder.id}>
                            <span>{folder.name}</span>
                            <button
                                className="btn btn-link p-0"
                                onClick={(e) => {
                                    e.stopPropagation();

                                    setFolderStack([...folderStack, folder]);
                                    setCurrFolder(folder);
                                }}
                            >
                                <i className="bi bi-arrow-right"></i>
                            </button>
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
        </>
    )
}

export default FolderDirectory;