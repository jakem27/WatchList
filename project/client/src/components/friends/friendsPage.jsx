import AddFriend from "./addFriend";
import FriendList from "./friendList";
import RequestList from "./requestList";

function FriendsPage() {
    return (
        <div className="row">
            <div className="col-2"></div>
            <div className="col-4 d-flex">
                <FriendList />
            </div>

            <div className="col-4 d-flex flex-column">
                <RequestList />
                <AddFriend />
            </div>

            <div className="col-2"></div>
        </div>
    );
}

export default FriendsPage;