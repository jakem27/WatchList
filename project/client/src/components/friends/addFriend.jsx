import { useState } from "react";

function AddFriend() {
    const [username, setUsername] = useState("");
    const [errors, setErrors] = useState([]);

    function handleChange(event) {
        setUsername(event.target.value);
        setErrors([]);
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
        } else {
            const payload = await response.json();
            setErrors(payload);
        }
        // handle errors
    }

    return (
        <>
            {errors.length > 0 && 
                <>
                    {errors.map(error => <small key={error}>Error: {error}</small>)}
                </>
            }
            <form onSubmit={handleSubmit}>
                <label htmlFor="username">Find Friend</label>
                
                <input id="username" name="username"
                    className="form-control" 
                    type="text" 
                    onChange={handleChange} 
                    value={username} 
                    required />

                <button className="btn btn-primary mt-2" type="submit">
                    Send Request
                </button>
            </form>
        </>
        
    );
}

export default AddFriend;