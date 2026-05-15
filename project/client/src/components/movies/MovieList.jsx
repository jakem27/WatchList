import { useEffect, useState } from "react";

function MovieList({ currFolder, setCurrMovie }) {
    const [movieFolders, setMovieFolders] = useState([]);

    useEffect(() => {
        const doFetch = async () => {
            const response = await fetch(`http://localhost:8080/api/folder/${currFolder.id}/movies`, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
            });

            if(response.ok) {
                const payload = await response.json();
                setMovieFolders(payload);
            }
        }
        doFetch();
    }, [currFolder]);

    return (
        <>
            {currFolder !== null && 
                    <div className="d-flex justify-content-between align-items-center gap-2">
                        <h3>{currFolder.name !== "root" ? currFolder.name : "My WatchList"}</h3>
                    </div>
            }

            {movieFolders.map(mf => (
                <div className="card w-75 shadow-sm">
                    <div className="card-body">
                        <div className="d-flex justify-content-between align-itmes-center mb-3">
                            <div className="d-flex align-items-center gap-2">
                                <i className="bi bi-folder fs-4"></i>
                                <small>
                                    {mf.folder.name}
                                </small>
                            </div>

                            <div className="d-flex align-items-center gap-2">
                                <i className="bi bi-camera-reels fs-4"></i>
                                <span className="fw-semibold">
                                    {mf.movie.title}
                                </span>
                            </div>

                            <div className="d-flex align-items-center gap-2">
                                watched
                            </div>
                        </div>
                    </div>
                </div>
            ))}
        </>
    );
}

export default MovieList;