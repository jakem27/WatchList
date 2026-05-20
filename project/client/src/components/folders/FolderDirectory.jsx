import { useEffect, useState } from "react";

function FolderDirectory({ currFolder, setCurrFolder }) {
    const [folders, setFolders] = useState([]);
    const [folderStack, setFolderStack] = useState([]);
    const [showForm, setShowForm] = useState(false);
    const [newFolderName, setNewFolderName] = useState("");
    const [editingFolder, setEditingFolder] = useState(null);
    const [editFolderName, setEditFolderName] = useState("");

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

    async function handleUpdate(event) {
        event.preventDefault();

        const updatedFolder = {...editingFolder, name: editFolderName};

        const response = await fetch("http://localhost:8080/api/folder", {
            method: "PUT",
            headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`,
                "Content-Type": "application/json"
            },
            body: JSON.stringify(updatedFolder)
        });

        if(response.ok) {
            setFolders(prev => 
                prev.map(f => {
                    if(f.id === updatedFolder.id) {
                        return updatedFolder;
                    }
                    return f;
                })
            );

            if(editingFolder.id === currFolder.id) {
                setCurrFolder({...currFolder, name: editFolderName});
            }

            setEditingFolder(null);
            setEditFolderName("");
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
                <i className="bi bi-arrow-left fs-4"></i>
            </button>
            <h3 className="mb-0">{!parentFolder || parentFolder.name}</h3>
            <button className="btn btn-link p-0" onClick={() => setShowForm(true)}>
                <i className="bi bi-plus fs-4"></i>    
            </button> 
            </div>
            
            <div className="list-group mt-4">
                {folders.map(folder => 
                    <div  className={" d-flex justify-content-between align-items-center gap-2 list-group-item list-group-item-action" +
                        (currFolder?.name === folder.name ? " active" : "")} key = {folder.id}>
                            <div className="dropdown" onClick={() => setCurrFolder(folder)}>
                                <button className="btn p-0 text-reset cursor-pointer" id={`folderDropdown-${folder.id}`} data-bs-toggle="dropdown" aria-expanded="false">
                                    <i className="bi bi-three-dots-vertical"></i>
                                </button>
                                <div className="dropdown-menu" aria-labelledby={`folderDropdown-${folder.id}`}>
                                    <button className="dropdown-item" onClick={() => {
                                        setEditingFolder(folder);
                                        setCurrFolder(folder);
                                        setEditFolderName(folder.name);
                                        }}>Edit Name</button>
                                    <button className="dropdown-item">Delete</button>
                                </div>
                            </div>
                            
                            <div className="flex-grow-1"
                                onClick={() => {
                                    if(currFolder?.id === folder.id) {
                                        setCurrFolder(parentFolder);
                                    } else {
                                        setCurrFolder(folder);
                                    }
                                }}>
                                {editingFolder && editingFolder.id === folder.id ? (
                                    <form onSubmit={handleUpdate}>
                                        <input 
                                            className="form-control"    
                                            value={editFolderName} 
                                            onChange={(e) => setEditFolderName(e.target.value)}
                                            onBlur={() => setEditingFolder(null)}
                                            autoFocus/>
                                    </form>
                                )
                                :
                                (
                                    <span>{folder.name}</span>
                                )}
                            </div>
                            
                            
                            <button className="btn p-0 text-reset cursor-pointer"
                                onClick={() => {
                                    setFolderStack([...folderStack, folder]);
                                    setCurrFolder(folder);
                                }}
                            >
                                <i className="bi bi-arrow-right fs-4"></i>
                            </button>
                    </div>)}
                {showForm && 
                    <form onSubmit={handleAdd}>
                        <input 
                            type="text" 
                            className="form-control" 
                            value={newFolderName}
                            onChange={(e) => setNewFolderName(e.target.value)}
                            onBlur={() => {
                                setShowForm(false);
                                setNewFolderName("");
                            }}
                            autoFocus/>
                    </form>}
                    
            </div>            
        </>
    )
}

export default FolderDirectory;