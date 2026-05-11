import { Outlet } from "react-router-dom";
import Nav from "./Nav";

function Layout() {
    return(
        <div className="container">
            <header className="mb-3">
                <Nav />
            </header>
            <main>
                <Outlet />
            </main>
        </div>
    )
}

export default Layout;