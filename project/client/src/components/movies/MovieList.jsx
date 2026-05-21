import { useEffect, useState } from "react";

function MovieList({ currFolder, setCurrFolder, currMovie, setCurrMovie, canAdd, setCanAdd }) {
    const [movieFolders, setMovieFolders] = useState([]);
    const [hoverId, setHoverId] = useState(null);

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

        const response = await fetch(`http://localhost:8080/api/folder`, {
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

    async function updateMF(event, mf) {
        let name = event.target.name;
        let value;
        if(name.startsWith("liked")) {
            name = "liked"
            value = event.target.value === "true";
        } else {
            value = event.target.checked;
        }

        const response = await fetch(`http://localhost:8080/api/movie`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${localStorage.getItem("token")}`
            },
            body: JSON.stringify({...mf, [name]: value})
        });
        
        if(response.ok) {
            setMovieFolders(prev => 
                prev.map(movieFolder => {
                    if(movieFolder.movie.id === mf.movie.id && movieFolder.folder.id === mf.folder.id) {
                        return {...movieFolder, [name]: value}
                    }
                    return movieFolder;
                })
            );
        }
    }

    async function handleDelete(mf) {
        const response = await fetch(`http://localhost:8080/api/movie`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${localStorage.getItem("token")}`
            },
            body: JSON.stringify(mf)
        });

        if(response.ok) {
            setMovieFolders(movieFolders.filter((prev) => prev.movie.id != mf.movie.id));
        }
    }

    return (
        <>
            {currFolder !== null && 
                    <div className="d-flex justify-content-between align-items-center gap-2">
                        <h3>{currFolder.name}</h3>
                        <div className="form-check form-switch">
                            <label className="form-check-label" htmlFor="publicSwitch">Public</label>
                            <input 
                                className="form-check-input" 
                                type="checkbox" role="switch" 
                                id="publicSwitch" 
                                checked={currFolder.public}
                                onChange={togglePublic}
                                disabled={movieFolders.length === 0}/>
                        </div>
                    </div>
            }

            <div className="d-flex flex-column gap-3 overflow-auto">
                {movieFolders.map(mf => (
                    <div className="card w-100 shadow-sm movie-card" key={mf.movie.id} onClick={() => handleClick(mf.movie)}>
                        <div className="card-body">
                            <div className="d-flex justify-content-between align-itmes-center mb-3">
                                <div className="d-flex align-items-center gap-2">
                                    <button className="btn p-0 text-danger" 
                                        onClick={() => handleDelete(mf)}
                                        onMouseEnter={() => setHoverId(mf.movie.id)}
                                        onMouseLeave={() => setHoverId(null)}
                                    >
                                        <i className={`bi bi-${hoverId === mf.movie.id ? "x-circle-fill" : "x-circle"} fs-5`}></i>
                                    </button>
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
                                </div>

                                <div className="d-flex flex-column justify-content-center">
                                    <div className="form-check">
                                        <input 
                                            className="form-check-input" 
                                            type="checkbox" 
                                            checked={mf.watched} 
                                            onChange={(e) => updateMF(e, mf)}
                                            name="watched"
                                            id={`watchedCheck-${mf.movie.id}`}/>
                                        <label className="form-check-label" htmlFor={`watchedCheck-${mf.movie.id}`}>Watched</label>
                                    </div>

                                    {mf.watched === true && 
                                        <div className="d-flex align-items-center gap-1">
                                                <input
                                                    className="btn-check"
                                                    type="radio"
                                                    value="true"
                                                    checked={mf.liked === true}
                                                    onChange={(e) => updateMF(e, mf)}
                                                    name={`liked-${mf.movie.id}`}
                                                    id={`liked-yes-${mf.movie.id}`}/>
                                                <label className="btn btn-outline-success" htmlFor={`liked-yes-${mf.movie.id}`}>
                                                    <i className="bi bi-hand-thumbs-up-fill"></i>
                                                </label>
                    
                                                <input
                                                    className="btn-check"
                                                    type="radio"
                                                    value="false"
                                                    checked={mf.liked === false}
                                                    onChange={(e) => updateMF(e, mf)}
                                                    name={`liked-${mf.movie.id}`}
                                                    id={`liked-no-${mf.movie.id}`}/>
                                                <label className="btn btn-outline-danger" htmlFor={`liked-no-${mf.movie.id}`}>
                                                    <i className="bi bi-hand-thumbs-down-fill"></i>
                                                </label>
                                        </div>
                                    }
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