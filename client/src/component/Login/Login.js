import React from "react";
import TextField from "@material-ui/core/TextField";
import Paper from "@material-ui/core/Paper";
import Box from "@material-ui/core/Box";
import Button from "@material-ui/core/Button";

import { restService } from "../../client/restClient.js";

export const Login = (props) => {

    return (


        <Box display="flex" flexDirection="column" justifyContent="center" alignItems="center"
             style={{ width: "100%", height: "100%" }}>

            <Paper elevation={3}>
                <Box display="flex" flexDirection="column" justifyContent="center" m={1} mt={0}
                     style={{ height: "200px" }}>
                    <Box p={2} pt={0}>
                        <TextField
                            id="standard-read-only-input"
                            label="Username"
                            // defaultValue="Hello World"

                        />
                    </Box>

                    <Box p={2}>
                        <TextField
                            id="standard-number"
                            label="Password"
                            type="number"

                        />
                    </Box>
                    <Button variant="contained" color="primary" onClick={() => restService.authenticate({username: "test", password: "123"})}>
                        Login
                    </Button>
                </Box>
            </Paper>

        </Box>


    );

};

