import { useState } from "react";
import FriendMovieList from "./FriendMovieList";
import { useLocation } from "react-router-dom";
import MovieView from "../movies/MovieView";
import AddFriendMovie from "./AddFriendMovie";

function FriendWatchList() {
    const { state } = useLocation();
    const folder = state.folder;
    const [movie, setMovie] = useState(null);


    return (
        <div className="row h-100 px-3">
            <div className="col-1"></div>

            <div className="col-4 d-flex flex-column h-100">
                <FriendMovieList folder={folder} setMovie={setMovie}/>
            </div>

            <div className="col-2"></div>

            <div className="col-4 border rounded shadow-sm p-4 d-flex flex-column h-100">
                <div className="flex-grow-1 overflow-hidden">
                    <MovieView currMovie={movie}/>
                </div>

                <div className="mt-auto pt-3 flex-shrink-0">
                    <AddFriendMovie movie={movie}/>
                </div>
            </div>
        </div>
    );
}

export default FriendWatchList;