import { useEffect, useState } from "react";

function Profile() {
    const [user, setUser] = useState(null);
    const [editing, setEditing] = useState(false);

    useEffect(() => {
        const doFetch = async () => {
            const response = await fetch("http://localhost:8080/api/profile", {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
            })

            const payload = await response.json();
            setUser(payload);
        }
        doFetch();
    }, []);

    function handleChange(e) {
        setUser({...user, [e.target.name]: e.target.value});
    }

    async function handleSave() {
        const response = await fetch("http://localhost:8080/api/profile", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${localStorage.getItem("token")}`
            },
            body: JSON.stringify(user)
        });

        if(response.ok) {
            setEditing(false);
        }
    }

    return (
        <>
        {user !== null && 
        <>
        <h2>{user.username}</h2>
        <div className="row">
            <div className="col-6">
                <ul className="list-unstyled">
                    {editing ? 
                    <>
                    <li className="mb-2 d-flex align-items-center gap-2 w-50">
                        <label className="form-label mb-0 text-nowrap">Favorite Movie:</label>
                        <input type="text" className="form-control" name="favoriteMovie" value={user.favoriteMovie || ""} onChange={handleChange} />
                    </li>
                    <li className="mb-2 d-flex align-items-center gap-2 w-50">
                        <label className="form-label mb-0 text-nowrap">Favorite Actor:</label>
                        <input type="text" className="form-control" name="favoriteActor" value={user.favoriteActor || ""} onChange={handleChange} />
                    </li>
                    <li className="mb-2 d-flex align-items-center gap-2 w-50">
                        <label className="form-label mb-0 text-nowrap">Favorite Genre:</label>
                        <input type="text" className="form-control" name="favoriteGenre" value={user.favoriteGenre || ""} onChange={handleChange} />
                    </li>
                    </>
                    :
                    <>
                    <li>{`Favorite Movie: ${user.favoriteMovie || ""}`}</li>
                    <li>{`Favorite Actor: ${user.favoriteActor || ""}`}</li>
                    <li>{`Favorite Genre: ${user.favoriteGenre || ""}`}</li>
                    </>}
                    
                    <li>{`Movies Watched: ${user.stats?.moviesWatched}`}</li>
                    <li>{`Total Minutes Watched: ${user.stats?.minutesWatched}`}</li>
                </ul>

                {editing ? 
                <div>
                    <button className="btn btn-success me-2" onClick={handleSave}>
                        Save
                    </button>
                    <button className="btn btn-secondary" onClick={() => setEditing(false)}>
                        Cancel
                    </button>
                </div>
                :
                <button className="btn btn-warning" onClick={() => setEditing(true)}>
                    Edit
                </button>
                }
                
            </div>
            

            <div className="col-6 border rounded shadow-sm">

            </div>
        </div>
        </>
        }
        
        </>
    )
}

export default Profile;