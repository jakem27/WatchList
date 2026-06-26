import clapperImage from "../assets/movie-clapper-and-reel.png";

function Home() {
    return (
        <div className="d-flex flex-column justify-content-center align-items-center h-100">
            <h1>Welcome to WatchList!</h1>
            <img src={clapperImage}></img>
            <h5>Create, organize and share your movie watch lists with ease</h5>
        </div>
    )
}

export default Home;