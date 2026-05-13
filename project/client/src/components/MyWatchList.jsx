import { useState } from "react";
import FolderDirectory from "./folders/FolderDirectory";

function MyWatchList() {
    const [currFolder, setCurrFolder] = useState(null);

    return (
        <div className="row">
            <div className="col-3">
                <FolderDirectory currFolder={currFolder} setCurrFolder={setCurrFolder}/>
            </div>
            <div className="col-5">
                <h3>{currFolder && currFolder.name !== "root" ? currFolder.name : "My WatchList"}</h3>
            </div>
        </div>
    )
}

export default MyWatchList;