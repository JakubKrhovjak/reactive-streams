import React from "react";
 import EventSourceView from "./component/EventSource/EventSourceView";
import Valuations from "./component/Valuation/ValuationList";
import ThemeProvider from "@material-ui/styles/ThemeProvider";
import CssBaseline from "@material-ui/core/CssBaseline";
import  {theme} from "./theme/theme";


import Nav from "./component/Nav/Nav";
import { Login } from "./component/Login/Login";
import Container from "@material-ui/core/Container";



function App() {
    return (
        <ThemeProvider theme={theme}>
            {/*<CssBaseline />*/}
            {/*<Container fixed>*/}
            <Login/>
                {/*</Container>*/}
            {/*<EventSourceView/>*/}
            {/*<Nav />*/}
            {/*<Valuations />*/}
        </ThemeProvider>
    );
}

export default App;
