import React from "react";
import ThemeProvider from "@material-ui/styles/ThemeProvider";
import { theme } from "./theme/theme";

import { Login } from "./component/Login/Login";



function App() {
    return (
        <ThemeProvider theme={theme}>
            <Login/>
        </ThemeProvider>
    );
}

export default App;
