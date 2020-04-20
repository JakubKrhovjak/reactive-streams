import React from "react";
import Paper from "@material-ui/core/Paper";
import Box from "@material-ui/core/Box";
import Button from "@material-ui/core/Button";
import { Formik, Form, Field } from "formik";
import { TextField } from "formik-material-ui";
import * as Yup from "yup";
import { Typography } from "@material-ui/core";
import { Auth } from "./LoginContainer";

const loginSchema = Yup.object().shape({
    username: Yup.string().email("Invalid email").required("Required"),
    password: Yup.string().required("password", "Required"),
});

export const Login = ({ accountAction, header, username }) => {
    return (
        <Box
            display="flex"
            flexDirection="column"
            justifyContent="center"
            alignItems="center"
        >
            <Paper elevation={3}>
                <Formik
                    validationSchema={loginSchema}
                    initialValues={{ username, password: "" }}
                    onSubmit={(values, props) => {
                        props.setSubmitting(false);
                        accountAction(values, props.setFieldError);
                    }}
                >
                    {(props) => (
                        <Form>
                            <Box
                                p={3}
                                pt={2}
                                display="flex"
                                flexDirection="column"
                                alignItems="flex-end"
                                width={350}
                            >
                                <Box alignSelf="start">
                                    <Typography variant="h5" gutterBottom>
                                        {header}
                                    </Typography>
                                </Box>

                                <Box mb={3} width="100%">
                                    <Field
                                        fullWidth
                                        component={TextField}
                                        name="username"
                                        type="email"
                                        label="Username"
                                    ></Field>
                                </Box>

                                <Box mb={4} width="100%">
                                    <Field
                                        fullWidth
                                        component={TextField}
                                        name="password"
                                        type="password"
                                        label="Password"
                                    ></Field>
                                </Box>
                                <Button
                                    variant="contained"
                                    color="primary"
                                    disabled={props.isSubmitting}
                                    onClick={props.submitForm}
                                >
                                    Login
                                </Button>
                            </Box>
                        </Form>
                    )}
                </Formik>
            </Paper>
        </Box>
    );
};
