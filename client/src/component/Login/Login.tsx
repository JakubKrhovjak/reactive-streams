import React from "react";
import Paper from "@material-ui/core/Paper";
import Box from "@material-ui/core/Box";
import Button from "@material-ui/core/Button";
import { Formik, Form, Field } from "formik";
import { TextField } from "formik-material-ui";
import * as Yup from "yup";
import { Typography } from "@material-ui/core";

const loginSchema = Yup.object().shape({
    username: Yup.string().email("Invalid email").required("Required"),
    password: Yup.string().required("password", "Required"),
});

export const Login = ({ authenticate, header }) => {
    return (
        <Box
            display="flex"
            flexDirection="column"
            justifyContent="center"
            alignItems="center"
            style={{ width: "100%", height: "100%" }}
        >
            <Paper elevation={3}>
                <Formik
                    validationSchema={loginSchema}
                    initialValues={{ username: "", password: "" }}
                    onSubmit={(values, props) => {
                        props.setSubmitting(false);
                        authenticate(values, props.setFieldError);
                    }}
                >
                    {(props) => (
                        <Form>
                            <Box
                                display="flex"
                                flexDirection="column"
                                justifyContent="center"
                                p={3} pt={2}
                                style={{ height: "200px" }}
                            >


                                <Box alignSelf="start">
                                    <Typography variant="h5" gutterBottom>
                                        {header}
                                    </Typography>
                                </Box>

                                <Box p={2} pt={0}>
                                    <Field
                                        component={TextField}
                                        name="username"
                                        type="email"
                                        label="Username"
                                    ></Field>
                                </Box>

                                <Box p={2}>
                                    <Field
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
