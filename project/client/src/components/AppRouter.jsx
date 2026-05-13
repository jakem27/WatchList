import { useState } from "react";
import Layout from "./Layout";
import UserForm from "./users/UserForm";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import MyWatchList from "./MyWatchList";

function AppRouter() {

    const routes = [
        {
            path: "",
            element: <Layout />,
            children: [
                {
                    path: "/",
                    element: <h2>Home</h2>
                },
                {
                    path: "/users/create",
                    element: <UserForm />
                },
                {
                    path: "/users/login",
                    element: <UserForm />
                },
                {
                    path: "/watchlist",
                    element: <MyWatchList />
                }
            ]
        }
    ]

    const router = createBrowserRouter(routes)

    return <RouterProvider router={router} />

}

export default AppRouter;