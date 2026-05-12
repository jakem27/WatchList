import { useContext, useEffect, useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { UserContext } from "./UserContext";

const INITIAL_USER = {
    username: "",
    password: ""
}

function UserForm() {

    const navigate = useNavigate();
    const location = useLocation();

    const isSignUp = location.pathname.includes("create");

    const [user, setUser] = useState(INITIAL_USER);
    const [errors, setErrors] = useState([]);

    const { setToken } = useContext(UserContext);

    useEffect(() => {
        setUser(INITIAL_USER);
        setErrors([]);
    }, [isSignUp]);

    function handleChange(event) {
        setUser({...user, [event.target.name]: event.target.value});
    }

    async function handleSubmit(event) {
        event.preventDefault();

        let url = "http://localhost:8080/auth"

        if(!isSignUp) {
            url += "/login";
        }

        const response = await fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(user)
        });

        const payload = await response.json();

        if(response.ok) {
            setToken(payload.token);
            localStorage.setItem("token", payload.token);
            navigate("/");
        } else {
            setErrors(payload);
        }
        
    }

    return (
        <>
            <h3>{isSignUp ? "Sign Up for an Account" : "Login to Your Account"}</h3>
            <div className="d-flex flex-column align-items-center mt-4">
                <form className="w-50" onSubmit={handleSubmit}>
                    {errors.length > 0 && <ul>
                        {errors.map(error => <li key={error}>{error}</li>)}    
                    </ul>}

                    <div className="mb-3">
                        <label htmlFor="username">Username</label>
                        <input className="form-control" id="username" name="username" 
                            onChange={handleChange} 
                            value={user.username} 
                            required />
                    </div>

                    <div className="mb-3">
                        <label htmlFor="password">Password</label>
                        <input className="form-control" id="password" name="password" 
                            onChange={handleChange} 
                            value={user.password} 
                            required />
                    </div>

                    <div className="mb-3">
                        <button className="btn btn-primary me-2" type="submit">
                            {isSignUp ? "Create" : "Login"}
                        </button>
                        <Link type="button" className="btn btn-warning" to="/">
                            Cancel
                        </Link>
                    </div>
                </form>
            </div>
        </>
    )
}

export default UserForm;