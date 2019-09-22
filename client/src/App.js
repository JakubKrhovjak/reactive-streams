import React from "react";
 import EventSourceView from "./component/EventSource/EventSourceView";
import Valuations from "./component/Valuation/ValuationList";
import ThemeProvider from "@material-ui/styles/ThemeProvider";
import CssBaseline from "@material-ui/core/CssBaseline";
import  {theme} from "./theme/theme";


import Nav from "./component/Nav/Nav";



function App() {
    return (
        <ThemeProvider theme={theme}>
            <CssBaseline />
            <EventSourceView/>
            {/*<Nav />*/}
            {/*<Valuations />*/}
        </ThemeProvider>
    );
}

export default App;
