import React, {Component, Fragment, useState, useEffect} from 'react';


const EventSourceView = (props) => {

    const [message, setMessage] = useState('init-message');

    useEffect(() => {
        var evenSource = new EventSource('http://localhost:8082/flux-rest');
        evenSource.addEventListener('message',  (event) => {
                setMessage(event.data);
                if(event.data === "FINISHED") {
                    evenSource.close()
                }
            });
    }, []);


    return (
        <Fragment>
            <h1>{message}</h1>
        </Fragment>
    )
};

export default EventSourceView;