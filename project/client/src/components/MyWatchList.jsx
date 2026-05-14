import { useState } from "react";
import FolderDirectory from "./folders/FolderDirectory";
import { Link } from "react-router-dom";
import AddMovie from "./movies/AddMovie";

function MyWatchList() {
    const [currFolder, setCurrFolder] = useState(null);
    
    return (
        <div className="row">
            <div className="col-3">
                <FolderDirectory currFolder={currFolder} setCurrFolder={setCurrFolder}/>
            </div>
            <div className="col-5">
                {currFolder !== null && <>
                    <div className="d-flex justify-content-between align-items-center gap-2">
                        <h3>{currFolder.name !== "root" ? currFolder.name : "My WatchList"}</h3>
                        <button type="button" className="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addMovie">
                            <i className="bi bi-plus"></i>
                        </button>

                        <div className="modal fade" id="addMovie" tabIndex="-1" aria-hidden="true">
                            <AddMovie currFolder={currFolder}/>
                        </div>
                    </div>
                </>}
                
                
            </div>
        </div>
    )
}

export default MyWatchList;