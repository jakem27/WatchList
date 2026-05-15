function MovieView({ currMovie }) {
    return ( 
        <div>
            {currMovie !== null && <>
                <div className="flex-shrink-0 text-center mb-3">
                    <img
                        src={currMovie.posterUrl}
                        alt={currMovie.title}
                        className="img-fluid rounded shadow-sm"
                        style={{
                            maxHeight: "35vh",
                            objectFit: "contain"
                        }}
                    />
                </div>

                <h3>{currMovie.title}</h3>
                <h5>{`Released: ${currMovie.year}`}</h5>
                <h5>{`Runtime: ${currMovie.runtime} min`}</h5>
                <h5>{`Directed by: ${currMovie.director}`}</h5>
                <h5>{`Genre: ${currMovie.genre}`}</h5>
            </>}
            
        </div>
    );
}

export default MovieView;