import { useEffect, useState } from "react";

function FriendList() {
    const [friends, setFriends] = useState([]);

    useEffect(() => {
        const doFetch = async () => {
            const response = await fetch(`http://localhost:8080/api/friendship`, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
            });
            if(response.ok) {
                const payload = await response.json();
                setFriends(payload);
            }
        }
        doFetch();
    }, []);

    return (
        <>
            <h3>Friends</h3>
            <div className="d-flex flex-column gap-3 overflow-auto">
                {friends.map(user => {
                    <div className="card shadow-sm" key={user.id}>
                        <div className="card-body">
                            <span>{user.username}</span>

                            <button>
                                <i className="bi bi-check"></i>
                            </button>

                            <button>
                                <i className="bi bi-x"></i>
                            </button>
                        </div>
                    </div>
                })}
            </div>
        </>
        
    );
}

export default FriendList;