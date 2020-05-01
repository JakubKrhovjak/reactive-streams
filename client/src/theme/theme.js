import {createMuiTheme} from "@material-ui/core";
import bluGrey from "@material-ui/core/colors/blueGrey";
import grey from "@material-ui/core/colors/grey";

export const theme = createMuiTheme({
    palette: {
        primary: {
            light: bluGrey[200],
            main: bluGrey[400],
            dark: bluGrey[600],
        },
        secondary: {
            light: grey[200],
            main: grey[300],
            dark: grey[400],
        }
    }
});