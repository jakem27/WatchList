import { useEffect, useState } from "react";

function MovieList({ currFolder, setCurrFolder, currMovie, setCurrMovie, canAdd, setCanAdd }) {
    const [movieFolders, setMovieFolders] = useState([]);

    useEffect(() => {
        if(currFolder === null) {
            return;
        }

        const doFetch = async () => {
            const response = await fetch(`http://localhost:8080/api/movie/folder/${currFolder.id}`, {
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
    }, [currFolder, currMovie, canAdd]);

    function handleClick(movie) {
        setCurrMovie(movie);
        setCanAdd(false);
    }

    async function togglePublic(event) {
        const isPublic = event.target.checked;

        const response = await fetch(`http://localhost:8080/api/folder/${currFolder.id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${localStorage.getItem("token")}`
            },
            body: JSON.stringify({
                ...currFolder,
                public: isPublic
            })
        });

        if(response.ok) {
            setCurrFolder({
                ...currFolder,
                public: isPublic
            });
        }
    }

    return (
        <>
            {currFolder !== null && 
                    <div className="d-flex justify-content-between align-items-center gap-2">
                        <h3>{currFolder.name !== "root" ? currFolder.name : "My WatchList"}</h3>
                        <div className="form-check form-switch">
                            <label className="form-check-label" htmlFor="publicSwitch">Public</label>
                            <input 
                                className="form-check-input" 
                                type="checkbox" role="switch" 
                                id="publicSwitch" 
                                checked={currFolder.public}
                                onChange={togglePublic}/>
                        </div>
                    </div>
            }

            <div className="d-flex flex-column gap-3 overflow-auto">
                {movieFolders.map(mf => (
                    <div className="card w-100 shadow-sm movie-card" key={[mf.movie.id, mf.folder.id]} onClick={() => handleClick(mf.movie)}>
                        <div className="card-body">
                            <div className="d-flex justify-content-between align-itmes-center mb-3">

                                <div className="d-flex flex-column">
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
                                </div>

                                

                                <div className="d-flex align-items-center gap-2">
                                    watched
                                </div>
                            </div>

                        </div>
                    </div>
                ))}
            </div>
        </>
    );
}

export default MovieList;