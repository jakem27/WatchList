import { useEffect, useState } from "react";

function FriendMovieList({ folder, setMovie }) {
    const [movieFolders, setMovieFolders] = useState([]);

    useEffect(() => {
        const doFetch = async () => {
            const response = await fetch(`http://localhost:8080/api/movie/folder/${folder.id}`, {
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
    }, [folder]);

    return (
        <>
            <div className="d-flex">
                <h3>{`${folder.name} by @${folder.user.username}`}</h3>
            </div>

            <div className="d-flex flex-column gap-3 overflow-auto">
                {movieFolders.map(mf => (
                    <div className="card w-100 shadow-sm movie-card" key={[mf.movie.id, mf.folder.id]} onClick={() => setMovie(mf.movie)}>
                        <div className="card-body">
                            <div className="d-flex justify-content-between align-items-center mb-3">
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
            </div>
        </>
    );
}

export default FriendMovieList;