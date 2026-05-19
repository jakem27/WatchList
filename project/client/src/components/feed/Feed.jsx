import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

function FolderFeed() {
    const [folders, setFolders] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const doFetch = async () => {
            const response = await fetch('http://localhost:8080/api/folder/feed', {
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
    }, []);

    function handleClick(folder) {
        navigate('/friend-watchlist', {
            state: { folder }
        });
    }

    return (
        <div className="row">
            <div className="col-3"></div>

            <div className="col-6 d-flex flex-column gap-3 overflow-auto">
                <h3>WatchList Feed</h3>
                {folders.map(folder => (
                    <div className="card w-100 shadow-sm" key={folder.id} onClick={() => handleClick(folder)}>
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

            <div className="col-3"></div>
        </div>
        
    );
}

export default FolderFeed;