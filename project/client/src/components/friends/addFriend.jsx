import { useState } from "react";

function AddFriend() {
    const [username, setUsername] = useState("");

    function handleChange(event) {
        setUsername(event.target.value);
    }

    async function handleSubmit(event) {
        event.preventDefault();

        const response = await fetch(`http://localhost:8080/api/friendship/${username}`, {
            method: "POST",
            headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`
            }
        });

        if(response.ok) {
            setUsername("");
        }
        // handle errors
    }

    return (
        <form onSubmit={handleSubmit}>
            <label htmlFor="username">Find Friend</label>
            <input id="username" name="username"
                className="form-control" 
                type="text" 
                onChange={handleChange} 
                value={username} 
                required />

            <button className="btn btn-primary me-2" type="submit">
                Send Request
            </button>
        </form>
    );
}

export default AddFriend;