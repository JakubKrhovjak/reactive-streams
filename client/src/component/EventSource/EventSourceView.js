import React, {Component, Fragment, useState, useEffect} from 'react';
import ReactEventSource from 'react-eventsource'
import axios from 'axios'

const EventSourceView = (props) => {

    const [message, setMessage] = useState('init-message');

    useEffect(() => {
        new EventSource('http://localhost:8280/flux-rest')
            .addEventListener('message',  (message) => setMessage(message.data));
    }, []);


    return (
        <Fragment>
            <h1>{message}</h1>
        </Fragment>
    )
};

export default EventSourceView;