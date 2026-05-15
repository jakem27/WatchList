import { useState } from "react";
import FolderDirectory from "./folders/FolderDirectory";
import { Link } from "react-router-dom";
import AddMovie from "./movies/AddMovie";
import MovieView from "./movies/MovieView";
import MovieList from "./movies/MovieList";

function MyWatchList() {
    const [currFolder, setCurrFolder] = useState(null);
    const [currMovie, setCurrMovie] = useState(null);

    return (
        <div className="row h-75 mt-5">
            <div className="col-3 border rounded shadow-sm p-4 d-flex flex-column">
                <FolderDirectory currFolder={currFolder} setCurrFolder={setCurrFolder}/>
            </div>

            <div className="col-1"></div>

            <div className="col-4">
                <MovieList currFolder={currFolder} setCurrMovie={setCurrMovie} />
            </div>

            <div className="col-1"></div>

            <div className="col-3 border rounded shadow-sm p-4 d-flex flex-column h-100">
                    <MovieView currMovie={currMovie}/>
                    <div className="mt-auto">
                        <AddMovie currFolder={currFolder} currMovie={currMovie} setCurrMovie={setCurrMovie}/>
                    </div>
            </div>
        </div>
    )
}

export default MyWatchList;