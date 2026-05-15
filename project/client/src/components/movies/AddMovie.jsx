import { useState } from "react";

function AddMovie({ currFolder }) {
    const [movieTitle, setMovieTitle] = useState("");
    const[movie, setMovie] = useState(null);

    async function handleSearch(event) {
        event.preventDefault();

        const response = await fetch(`http://localhost:8080/api/movie/${movieTitle}`, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`
            }
        })

        if(response.ok) {
            const payload = await response.json();
            setMovie(payload);
        }
    }

    async function handleAdd() {
        if(movie === null) {
            return;
        }

        const response = await fetch(`http://localhost:8080/api/folder/add-movie`, {
            method: "POST",
            headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`,
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                movie: movie,
                folder: currFolder
            })
        })

        if(response.ok) {
            setMovieTitle("");
            setMovie(null);
        }
    }

    return ( 
        <div className="modal-dialog">
            <div className="modal-content">
                <div className="modal-header">
                    <h1 className="modal-title fs-5">{`Add a Movie to ${currFolder.name}`}</h1>
                    <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div className="modal-body">
                    <form onSubmit={handleSearch}>
                        <label htmlFor="movieTitle">Movie Title</label>
                        <input 
                            id="movieTitle"
                            className="form-control"
                            value={movieTitle}
                            onChange={(e) => setMovieTitle(e.target.value)}
                            required 
                        />

                        <button type="submit" className="btn btn-primary">
                            <i className="bi bi-search"></i>
                        </button>
                    </form>

                    <span>{movie !== null && movie.title}</span>
                </div>
                <div className="modal-footer">
                    <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="button" className="btn btn-primary" onClick={handleAdd}>Add</button>
                </div>
            </div>
        </div>
    );
}

export default AddMovie;