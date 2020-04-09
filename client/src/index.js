import React from 'react';
import ReactDOM from 'react-dom';
import { RouterProvider } from "react-router5";
import {configureRouter } from './router/routerConfig'
import App from './App';
import './index.css';

const router = configureRouter();


router.start(() =>
{
    ReactDOM.render(
        <RouterProvider router={router}>
            <App/>,
        </RouterProvider>,
        document.getElementById('root'));

})

