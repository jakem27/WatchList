function MovieView({ currMovie }) {
    return ( 
        <div>
            {currMovie !== null && <>

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