import { Outlet } from "react-router-dom";
import Nav from "./Nav";

function Layout() {
    return(
        <div className="container-fluid px-0 pb-5 vh-100 d-flex flex-column">
            <header className="mb-3">
                <Nav />
            </header>
            <main className="flex-grow-1 mx-5 overflow-hidden">
                <Outlet />
            </main>
        </div>
    )
}

export default Layout;