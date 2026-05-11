import { NavLink } from "react-router-dom";

function Nav() {
    return (
        <nav className="navbar navbar-expand">
            <div className="d-flex">
                <ul className="navbar-nav">
                    <li className="nav-item">
                        <NavLink id="link" className="nav-link" to="/">
                            Home
                        </NavLink>
                    </li>
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
                </ul>
            </div>
        </nav>
    )
}

export default Nav;