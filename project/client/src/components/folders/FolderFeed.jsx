import { useEffect, useState } from "react";

function FolderFeed() {
    const [folders, setFolders] = useState([]);

    useEffect(() => {
        const doFetch = async () => {
            const response = await fetch('http://localhost:8080/api/folder/feed', {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
            });
        }
        doFetch();
    }, []);

    return (
        <>
            <h3>WatchList Feed</h3>
            <div className="d-flex flex-column gap-3 overflow-auto">
                {folders.map(folder => (
                    <div className="card w-100 shadow-sm" key={folder.id}>
                        <div className="card-header">
                            <div className="d-flex align-items-center gap-2">
                                <i className="bi bi-person-circle"></i>
                                <small>
                                    {folder.user.username}
                                </small>
                            </div>
                        </div>
                        <div className="card-body">
                            <div className="d-flex justify-content-between align-items-center mb-3">
                                <div className="d-flex align-items-center gap-2">
                                    <i className="bi bi-folder"></i>
                                    <span className="fw-semibold">
                                        {folder.name}
                                    </span>
                                </div>

                                <div className="d-flex align-items-center gap-2">
                                    Timestamp
                                </div>
                            </div>
                            
                        </div>
                    </div>
                ))}
            </div>
        </>
        
    );
}

export default FolderFeed;