import React from "react";
import ReactDOM from "react-dom";
import { RouterProvider } from "react-router5";
import { configureRouter } from "./router/routerConfig";
import App from "./App";
import "./index.css";
import ThemeProvider from "@material-ui/styles/ThemeProvider";
import { theme } from "./theme/theme";

const router = configureRouter();


router.start(() => {
    ReactDOM.render(
        <ThemeProvider theme={theme}>
            <RouterProvider router={router}>
                <App/>,
            </RouterProvider>
        </ThemeProvider>,
        document.getElementById("root"));

});

