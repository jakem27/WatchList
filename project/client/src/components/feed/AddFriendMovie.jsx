import { useEffect, useState } from "react";

function AddFriendMovie({ movie }) {
    const [myFolders, setMyFolders] = useState([]);
    const [folder, setFolder] = useState(null);

    useEffect(() => {
        const doFetch = async () => {
            const response = await fetch('http://localhost:8080/api/folder/all', {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
            });

            if(response.ok) {
                const payload = await response.json();
                setMyFolders(payload);
                setFolder(myFolders[0]);
            }
        }
        doFetch();
    }, [])


    async function handleAdd(event) {
        event.preventDefault();

        if(movie === null || folder == null) {
            return;
        }

        const response = await fetch('http://localhost:8080/api/movie', {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${localStorage.getItem("token")}`
            },
            body: JSON.stringify({
                movie: movie,
                folder: folder
            })
        });

        if(response.ok) {
            
        }
        // handle errors
    }

    function handleChange(event) {
        const selectedFolder = myFolders.find(
            f => f.id === Number(event.target.value)
        );

        setFolder(selectedFolder);
    }

    return (
        <div className="card-p3">
            <h3 className="mb-3">
                Add Movie
            </h3>

            <form onSubmit={handleAdd} className="d-flex flex-column gap-2 mb-3">
                <select className="form-select" value={folder?.id || ""} onChange={handleChange}>
                    <option value="">Select Folder</option>
                    
                    {myFolders.map(f => (
                        <option value={f.id} key={f.id}>
                            {f.name}
                        </option>
                    ))}
                </select>

                <button type="submit" className="btn btn-success" disabled={!folder || !movie}>
                    {folder ? `Add to ${folder.name}` : "Add"}
                </button>
            </form>
        </div>
    )
}

export default AddFriendMovie;