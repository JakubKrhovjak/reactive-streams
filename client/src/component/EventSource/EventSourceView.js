import React, {Component, Fragment, useState, useEffect} from 'react';
import ReactEventSource from 'react-eventsource'
import axios from 'axios'

class EventSourceView extends React.Component {

    constructor(props) {
        super(props);

        this.state = {

            message: "init-message"
        }
        //

        let eventSource = new EventSource('http://localhost:8280/flux-rest');

        eventSource.addEventListener('message', function(message) {
            this.setState({message: message.data});
        }.bind(this), false);
    }

    componentDidMount() {
        axios.get('http://localhost:8280/flux-rest')
             .then(function (response) {
                console.log(response.data);
            })
            .catch(function (error) {
                console.log(error);
            })
    }

    render() {
        debugger;
        // const state = this.state;
        return (
            <Fragment>

                <h1>{this.state.message}</h1>

            </Fragment>
        )

    }
}

export default EventSourceView;