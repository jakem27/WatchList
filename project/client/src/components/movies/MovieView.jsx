import { useEffect, useState } from "react";

function MovieView({ currMovie, setCurrMovie, userServices }) {
    const [showDescription, setShowDescription] = useState(false);
    const [editingServices, setEditingServices] = useState(false);
    const [services, setServices] = useState([]);



    useEffect(() => {
        setShowDescription(false);
        if(currMovie === null) {
            return;
        }
        const doFetch = async () => {
            const response = await fetch(`http://localhost:8080/api/movie/services/${currMovie.title}`, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
            });
            const payload = await response.json();
            setServices(payload);
        }
        doFetch();
    }, [currMovie?.id]);

    function handleServiceChange(e) {
        const value = e.target.value;

        if(e.target.checked) {
            setServices([...services, value]);
        } else {
            setServices(services.filter(service => service !== value));
        }
    }

    async function handleSaveServices() {
        const response = await fetch(`http://localhost:8080/api/movie/services/${currMovie.title}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${localStorage.getItem("token")}`
            },
            body: JSON.stringify(services)
        });

        if(response.ok) {
            setCurrMovie({...currMovie, services: services});
            setEditingServices(false);
        }
    }

    return ( 
        <>
        <div className="h-100 overflow-auto">
            {currMovie !== null && <>
                <div className="flex-shrink-0 text-center">
                    <img
                        src={currMovie.posterUrl}
                        alt={currMovie.title}
                        className="img-fluid rounded shadow-sm"
                        style={{
                            maxHeight: "33vh",
                            objectFit: "contain"
                        }}
                    />
                </div>
                
                <div className="d-flex align-items-center justify-content-between">
                    <h3>{currMovie.title}</h3>
                    {userServices.some(s => services.includes(s)) && <i className="bi bi-eye-fill mx-4 fs-4 text-success"></i>}
                </div>
                <h5>{`Released: ${currMovie.year}`}</h5>
                <h5>{`Runtime: ${currMovie.runtime} min`}</h5>
                <h5>{`Directed by: ${currMovie.director}`}</h5>
                <h5>{`Genre: ${currMovie.genre}`}</h5>

                {showDescription && <p>{`Description: ${currMovie.description}`}</p>}

                {showDescription && 
                <>
                <p>{`Available on: ${services}`}</p>
                <button className="btn btn-warning" onClick={() => setEditingServices(true)}>
                    Edit
                </button>
                </>
                }

                <div className="d-flex justify-content-center">
                    {showDescription ? 
                        <button className="btn btn-link p-0 m-0" onClick={() => setShowDescription(false)}>
                            <i className="bi bi-caret-up-fill fs-3"></i>
                        </button>
                        :
                        <button className="btn btn-link p-0 m-0" onClick={() => setShowDescription(true)}>
                            <i className="bi bi-caret-down-fill fs-3"></i>
                        </button>
                    }
                </div>
                
            </>}
            
        </div>

        {editingServices && (
            <>
            <div className="modal fade show d-block">
                    <div className="modal-dialog modal-dialog-centered">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5>Delete Folder</h5>
                            </div>

                            <div className="modal-body">
                                <div>
                                    {`Edit Streaming Services "${currMovie.title}" is available on`}
                                </div>
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
                            
                            </div>

                            <div className="modal-footer">
                                <button className="btn btn-success me-2 mb-3" onClick={handleSaveServices}>
                                    Save
                                </button>
                                <button className="btn btn-secondary mb-3" onClick={() => {
                                    setEditingServices(false);
                                    setServices(currMovie.services || []);
                                    }}>
                                    Cancel
                                </button>
                            </div>
                        </div>
                    </div>

                </div>

                <div className="modal-backdrop fade show"></div>
            </>
        )}
        </>
    );
}

export default MovieView;