import { useState } from "react";
import AddFriend from "./addFriend";
import FriendList from "./friendList";
import RequestList from "./requestList";

function FriendsPage() {
    const [friends, setFriends] = useState([]);

    return (
        <div className="row">
            <div className="col-2"></div>
            <div className="col-4 d-flex flex-column">
                <FriendList friends={friends} setFriends={setFriends}/>
            </div>

            <div className="col-4 d-flex flex-column">
                <RequestList friends={friends} setFriends={setFriends}/>
                <AddFriend />
            </div>

            <div className="col-2"></div>
        </div>
    );
}

export default FriendsPage;