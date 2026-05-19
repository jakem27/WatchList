import { useContext } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import { UserContext } from "./users/UserContext";

function Nav() {
    const { token, setToken } = useContext(UserContext);
    const navigate = useNavigate();

    return (
        <nav className="navbar navbar-expand">
            <div className="d-flex">
                <ul className="navbar-nav">
                    <li className="nav-item">
                        <NavLink id="link" className="nav-link" to="/">
                            Home
                        </NavLink>
                    </li>

                    {token === null ? 
                    <>
                        <li className="nav-item">
                            <NavLink id="link" className="nav-link" to="/users/create">
                                Sign-Up
                            </NavLink>
                        </li>
                        <li className="nav-item">
                            <NavLink id="link" className="nav-link" to="/users/login">
                                Login
                            </NavLink>
                        </li>
                    </>
                    :
                    <>
                        <li className="nav-item">
                            <NavLink id="link" className="nav-link" to="/watchlist">
                                My WatchList
                            </NavLink>
                        </li>

                        <li className="nav-item">
                            <NavLink id="link" className="nav-link" to="/feed">
                                Feed
                            </NavLink>
                        </li>

                        <li className="nav-item">
                            <NavLink id="link" className="nav-link" to="/friends">
                                Friends
                            </NavLink>
                        </li>

                        <li className="nav-item">
                            <button id="link" className="nav-link" onClick={() => {
                                setToken(null);
                                localStorage.clear("token");
                                navigate("/")
                            }}>
                                Logout
                            </button>
                        </li>
                    </>
                    }
                    
                </ul>
            </div>
        </nav>
    )
}

export default Nav;