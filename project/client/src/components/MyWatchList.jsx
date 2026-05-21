import { useEffect, useState } from "react";
import FolderDirectory from "./folders/FolderDirectory";
import { Link } from "react-router-dom";
import AddMovie from "./movies/AddMovie";
import MovieView from "./movies/MovieView";
import MovieList from "./movies/MovieList";

function MyWatchList() {
    const [currFolder, setCurrFolder] = useState(null);
    const [currMovie, setCurrMovie] = useState(null);
    const [canAdd, setCanAdd] = useState(false);
    const [userServices, setUserServices] = useState([]);

    useEffect(() => {
            const doFetch = async () => {
                const response = await fetch("http://localhost:8080/api/profile", {
                    headers: {
                        Authorization: `Bearer ${localStorage.getItem("token")}`
                    }
                })
    
                const payload = await response.json();
                setUserServices(payload.services || []);
            }
            doFetch();
        }, []);

    return (
        <div className="row h-100 px-3">
            <div className="col-3 border rounded shadow-sm p-4 d-flex flex-column h-100">
                <FolderDirectory 
                    currFolder={currFolder} 
                    setCurrFolder={setCurrFolder}
                />
            </div>

            <div className="col-1"></div>

            <div className="col-4 d-flex flex-column h-100">
                <MovieList 
                    currFolder={currFolder} 
                    setCurrFolder={setCurrFolder}
                    currMovie={currMovie} 
                    setCurrMovie={setCurrMovie} 
                    canAdd={canAdd}
                    setCanAdd={setCanAdd}
                />
            </div>

            <div className="col-1"></div>

            <div className="col-3 border rounded shadow-sm p-4 d-flex flex-column h-100">
                    <div className="flex-grow-1 overflow-hidden">
                        <MovieView 
                            currMovie={currMovie}
                            setCurrMovie={setCurrMovie}
                            userServices={userServices}
                        />
                    </div>
                    
                    <div className="mt-auto pt-3 flex-shrink-0">
                        <AddMovie 
                            currFolder={currFolder} 
                            currMovie={currMovie} 
                            setCurrMovie={setCurrMovie}
                            canAdd={canAdd}
                            setCanAdd={setCanAdd}
                        />
                    </div>
            </div>
        </div>
    )
}

export default MyWatchList;