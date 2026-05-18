import { useEffect, useState } from "react";

function FriendList({friends, setFriends}) {

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

    async function deleteFriend(user) {
        const response = await fetch(`http://localhost:8080/api/friendship/${user.username}`, {
            method: "DELETE",
            headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`
            }
        });

        if(response.ok) {
            setFriends(friends.filter(friend => friend !== user));
        }
        // display errors

    }

    return (
        <>
            <h3>Friends</h3>
            <div className="d-flex flex-column gap-3">
                {friends.map(user => (
                    <div className="card shadow-sm" key={user.id}>
                        <div className="card-body d-flex justify-content-between align-items-center">
                            <span>{user.username}</span>

                            <button className="btn btn-danger btn-sm" onClick={() => deleteFriend(user)}>
                                <i className="bi bi-x"></i>
                            </button>
                        </div>
                    </div>
                ))}
            </div>
        </>
        
    );
}

export default FriendList;