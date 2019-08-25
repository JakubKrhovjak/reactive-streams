import React from 'react';

import EventSourceView from "./component/EventSource/EventSourceView";
import Valuations from "./component/Valuation/ValuationList";
import {Container} from "@material-ui/core";
import ThemeProvider from "@material-ui/styles/ThemeProvider";
import NoSsr from '@material-ui/core/NoSsr';
import { createMuiTheme } from '@material-ui/core/styles';

const theme = createMuiTheme();

function App() {
    return (
        <NoSsr>
            <ThemeProvider theme={theme}>
                {/*<div className="app">*/}
                    <Container maxWidth="xl" style={{height: '100%'}}>
                        {/*<EventSourceView/>*/}
                        <Valuations/>
                    </Container>
                {/*</div>*/}
            </ThemeProvider>
        </NoSsr>
    );
}

export default App;
