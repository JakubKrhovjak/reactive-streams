import React, { useState } from "react";
import { restService } from "../../client/restClient";
import { useRouter } from "react-router5";
import { SignIn } from "./SigIn";
import { Login } from "./Login";
import { Grid } from "@material-ui/core";
import Box from "@material-ui/core/Box";

const SIGN_IN = "SIGN_IN";

interface Credential {
    username: string;
    password: string;
}




export const LoginContainer = (props) => {
    const router = useRouter();

    const [type, setType] = useState<string>(SIGN_IN);

    const signIn = (username: string) => {
        restService
            .post("/signIn", username)
            .then((res) => {
                if (res.data) {
                    setType(SIGN_IN)
                }
            })
            .catch((e) => {
                console.info(e)
                // setFieldError("password", "Invalid password!")
            });
    };

    const authenticate = (
        values: Credential,
        setFieldError: (a: string, b: string) => void
    ) => {
        restService
            .authenticate(values.username, values.password)
            .then((res) => {
                router.navigate("basic");
            })
            .catch((e) => {
                setFieldError("password", "Invalid password!");
            });
    };

    const resolveComponent = () => {
        return type === SIGN_IN ? (
            <SignIn signIn={signIn} />
        ) : (
            <Login authenticate={authenticate}/>
        );
    }

    return (
        <Grid  container
               direction="row"
               justify="center"
               alignItems="center"
               style={{height: "100%"}}
        >
            {resolveComponent()}
        </Grid>
    )


};
