import { useEffect, useState } from "react";

function MovieView({ currMovie }) {
    const [showDescription, setShowDescription] = useState(false);

    useEffect(() => {setShowDescription(false)}, [currMovie]);

    return ( 
        <div className="h-100 overflow-auto">
            {currMovie !== null && <>
                <div className="flex-shrink-0 text-center">
                    <img
                        src={currMovie.posterUrl}
                        alt={currMovie.title}
                        className="img-fluid rounded shadow-sm"
                        style={{
                            maxHeight: "33vh",
                            objectFit: "contain"
                        }}
                    />
                </div>

                <h3>{currMovie.title}</h3>
                <h5>{`Released: ${currMovie.year}`}</h5>
                <h5>{`Runtime: ${currMovie.runtime} min`}</h5>
                <h5>{`Directed by: ${currMovie.director}`}</h5>
                <h5>{`Genre: ${currMovie.genre}`}</h5>

                {showDescription && <p>{`Description: ${currMovie.description}`}</p>}

                <div className="d-flex justify-content-center">
                    {showDescription ? 
                        <button className="btn btn-link p-0 m-0" onClick={() => setShowDescription(false)}>
                            <i className="bi bi-caret-up-fill fs-3"></i>
                        </button>
                        :
                        <button className="btn btn-link p-0 m-0" onClick={() => setShowDescription(true)}>
                            <i className="bi bi-caret-down-fill fs-3"></i>
                        </button>
                    }
                </div>
                
            </>}
            
        </div>
    );
}

export default MovieView;