import React from "react";

// import EventSourceView from "./component/EventSource/EventSourceView";
import Valuations from "./component/Valuation/ValuationList";
import ThemeProvider from "@material-ui/styles/ThemeProvider";
import { createMuiTheme } from "@material-ui/core/styles";
import CssBaseline from "@material-ui/core/CssBaseline";

import Nav from "./component/Nav/Nav";

const theme = createMuiTheme();

function App() {
    return (
        <ThemeProvider theme={theme}>
            <CssBaseline />
            {/*<EventSourceView/>*/}
            <Nav />
            {/*<Valuations />*/}
        </ThemeProvider>
    );
}

export default App;
