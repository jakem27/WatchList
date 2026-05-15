import { Outlet } from "react-router-dom";
import Nav from "./Nav";

function Layout() {
    return(
        <div className="container-fluid px-5 vh-100 d-flex flex-column">
            <header className="mb-3">
                <Nav />
            </header>
            <main className="flex-grow-1">
                <Outlet />
            </main>
        </div>
    )
}

export default Layout;