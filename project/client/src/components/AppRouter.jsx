import { useState } from "react";
import Layout from "./Layout";
import UserForm from "./users/UserForm";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import MyWatchList from "./MyWatchList";
import FriendsPage from "./friends/friendsPage";
import FolderFeed from "./folders/FolderFeed";

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
                },
                {
                    path: "/friends",
                    element: <FriendsPage />
                },
                {
                    path: "/feed",
                    element: <FolderFeed />
                }
            ]
        }
    ]

    const router = createBrowserRouter(routes)

    return <RouterProvider router={router} />

}

export default AppRouter;