import React from "react";
import ReactDOM from "react-dom";
import { RouterProvider } from "react-router5";
import { configureRouter } from "./router/routerConfig";
import App from "./App";
import "./index.css";
import ThemeProvider from "@material-ui/styles/ThemeProvider";
import { theme } from "./theme/theme";
import { SecurityContextProvider } from "./security/SecurityContext";

const router = configureRouter();


router.start(() => {
    ReactDOM.render(
        <ThemeProvider theme={theme}>
            <SecurityContextProvider>
            <RouterProvider router={router}>
                <App/>
            </RouterProvider>
           </SecurityContextProvider>
        </ThemeProvider>,
        document.getElementById("root"));

});

