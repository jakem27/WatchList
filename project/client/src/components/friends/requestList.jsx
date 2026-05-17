import { useEffect, useState } from "react";

function RequestList() {
    const [requests, setRequests] = useState([]);

    useEffect(() => {
        const doFetch = async () => {
            const response = await fetch(`http://localhost:8080/api/friendship/requests`, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
            });
            if(response.ok) {
                const payload = await response.json();
                setRequests(payload);
            }
        }
        doFetch();
    }, []);

    return (
        <>
            <h3>Friend Requests</h3>
            <div className="d-flex flex-column gap-3">
                {requests.map(user => (
                        <div className="card shadow-sm" key={user.id}>
                            <div className="card-body d-flex justify-content-between align-items-center">
                                <span>{user.username}</span>

                                <div>
                                    <button className="btn btn-success btn-sm me-2">
                                        <i className="bi bi-check"></i>
                                    </button>

                                    <button className="btn btn-danger btn-sm">
                                        <i className="bi bi-x"></i>
                                    </button>
                                </div>

                                
                            </div>
                        </div>
                    )
                )}
            </div>
        </>
        
    );
}

export default RequestList;