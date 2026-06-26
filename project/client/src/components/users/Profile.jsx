import { useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { UserContext } from "./UserContext";

function Profile() {
    const [user, setUser] = useState(null);
    const [editingFavorites, setEditingFavorites] = useState(false);
    const [editingServices, setEditingServices] = useState(false);
    const [services, setServices] = useState([]);
    const navigate = useNavigate();
     const { setToken } = useContext(UserContext);

    useEffect(() => {
        const doFetch = async () => {
            const response = await fetch("http://localhost:8080/api/profile", {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
            })

            const payload = await response.json();
            setUser(payload);
            setServices(payload.services || []);
        }
        doFetch();
    }, []);

    function handleChange(e) {
        setUser({...user, [e.target.name]: e.target.value});
    }

    function handleServiceChange(e) {
        const value = e.target.value;

        if(e.target.checked) {
            setServices([...services, value]);
        } else {
            setServices(services.filter(service => service !== value));
        }
    }

    async function handleSaveInfo() {
        const response = await fetch("http://localhost:8080/api/profile", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${localStorage.getItem("token")}`
            },
            body: JSON.stringify(user)
        });

        if(response.ok) {
            setEditingFavorites(false);
        }
    }

    async function handleSaveServices() {
        const response = await fetch("http://localhost:8080/api/profile/services", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${localStorage.getItem("token")}`
            },
            body: JSON.stringify(services)
        });

        if(response.ok) {
            setUser({...user, services: services});
            setEditingServices(false);
        }
    }

    return (
        <>
        {user !== null && 
        <>
        <div className="row">
            <div className="col-1"></div>
            <div className="col-4">
                <h1>{user.username}</h1>
            </div>
            
        </div>
        
        <div className="row">
            <div className="col-1"></div>
            <div className="col-4 border rounded shadow-sm box">
                <h3 className="mt-3">Info</h3>
                <ul className="list-unstyled">
                    {editingFavorites ? 
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

                {editingFavorites ? 
                <div>
                    <button className="btn btn-success me-2 mb-3" onClick={handleSaveInfo}>
                        Save
                    </button>
                    <button className="btn btn-secondary mb-3" onClick={() => setEditingFavorites(false)}>
                        Cancel
                    </button>
                </div>
                :
                <button className="btn btn-warning mb-3" onClick={() => setEditingFavorites(true)}>
                    Edit
                </button>
                }
                
            </div>

            <div className="col-2"></div>

            <div className="col-4 border rounded shadow-sm box">
                <h3 className="mt-3">Streaming Services</h3>
                {editingServices ? 
                <>
                <div className="form-check">
                    <input className="form-check-input" type="checkbox" id="service1" value="Netflix" checked={services.includes("Netflix")} onChange={handleServiceChange} />
                    <label className="form-check-label" htmlFor="service1">Netflix</label>
                </div>
                <div className="form-check">
                    <input className="form-check-input" type="checkbox" id="service2" value="Hulu" checked={services.includes("Hulu")} onChange={handleServiceChange} />
                    <label className="form-check-label" htmlFor="service2">Hulu</label>
                </div>
                <div className="form-check">
                    <input className="form-check-input" type="checkbox" id="service3" value="HBO Max" checked={services.includes("HBO Max")} onChange={handleServiceChange} />
                    <label className="form-check-label" htmlFor="service3">HBO Max</label>
                </div>
                <div className="form-check">
                    <input className="form-check-input" type="checkbox" id="service4" value="Apple TV" checked={services.includes("Apple TV")} onChange={handleServiceChange} />
                    <label className="form-check-label" htmlFor="service4">Apple TV</label>
                </div>
                <div className="form-check">
                    <input className="form-check-input" type="checkbox" id="service5" value="Paramount+" checked={services.includes("Paramount+")} onChange={handleServiceChange} />
                    <label className="form-check-label" htmlFor="service5">Paramount+</label>
                </div>
                <div className="form-check">
                    <input className="form-check-input" type="checkbox" id="service6" value="Amazon Prime" checked={services.includes("Amazon Prime")} onChange={handleServiceChange} />
                    <label className="form-check-label" htmlFor="service6">Amazon Prime</label>
                </div>

                <div>
                    <button className="btn btn-success me-2 mb-3" onClick={handleSaveServices}>
                        Save
                    </button>
                    <button className="btn btn-secondary mb-3" onClick={() => {
                        setEditingServices(false);
                        setServices(user.services || []);
                        }}>
                        Cancel
                    </button>
                </div>
                </>
                :
                <>
                <ul>
                    {user?.services.map(service => <li key={service}>{service}</li>)}
                </ul>
                <button className="btn btn-warning mb-3" onClick={() => setEditingServices(true)}>
                    Edit
                </button>
                </>
                }
                
            </div>
            <div className="col-1"></div>
        </div>

        <div className="row mt-2">
            <div className="col-1"></div>
            <div className="col-1">
                <button id="link" className="btn btn-danger" onClick={() => {
                    setToken(null);
                    localStorage.clear("token");
                    navigate("/")
                }}>
                    Logout
                </button>
            </div>
            
        </div>
        
        </>
        }
        
        </>
    )
}

export default Profile;