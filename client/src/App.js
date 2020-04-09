import React from "react";
import ThemeProvider from "@material-ui/styles/ThemeProvider";
import { theme } from "./theme/theme";
import { Content } from "./component/Content/Content";
import { RouterProvider,} from "react-router5";

function App() {


    return (
        <ThemeProvider theme={theme}>
            <Content/>
        </ThemeProvider>
    );
}

export default App;
