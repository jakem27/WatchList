import { useEffect, useState } from "react";

function AddMovie({ currFolder, currMovie, setCurrMovie, canAdd, setCanAdd}) {
    const [movieTitle, setMovieTitle] = useState("");

    useEffect(() => {setCanAdd(false)}, [movieTitle])

    async function handleSearch(event) {
        event.preventDefault();

        const response = await fetch(`http://localhost:8080/api/movie/${movieTitle}`, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`
            }
        })

        if(response.ok) {
            const payload = await response.json();
            setCurrMovie(payload);
            setCanAdd(true);
        }
    }

    async function handleAdd() {
        if(currMovie === null) {
            return;
        }

        const response = await fetch(`http://localhost:8080/api/folder/add-movie`, {
            method: "POST",
            headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`,
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                movie: currMovie,
                folder: currFolder
            })
        })

        if(response.ok) {
            setMovieTitle("");
            setCanAdd(false);
        }
    }

    return ( 
        <div className="card p-3">
            <h3 className="mb-3">
                Search Movie
            </h3>

            <form
                onSubmit={handleSearch}
                className="d-flex gap-2 mb-3"
            >
                <input
                    id="movieTitle"
                    className="form-control"
                    placeholder="Search movie title..."
                    value={movieTitle}
                    onChange={(e) => setMovieTitle(e.target.value)}
                    required
                />

                <button type="submit" className="btn btn-primary">
                    <i className="bi bi-search"></i>
                </button>
            </form>

            <button className="btn btn-success" onClick={handleAdd} disabled={!canAdd}>
                {currFolder !== null && `Add to ${currFolder.name === 'root' ? 'My WatchList' : currFolder.name}`}
            </button>
        </div>
    );
}

export default AddMovie;