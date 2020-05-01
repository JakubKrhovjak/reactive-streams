import React from "react";
import Paper from "@material-ui/core/Paper";
import Box from "@material-ui/core/Box";
import Button from "@material-ui/core/Button";
import { Formik, Form, Field } from "formik";
import { TextField } from "formik-material-ui";
import * as Yup from "yup";
import { Grid, Typography } from "@material-ui/core";

const signInSchema = Yup.object().shape({
    email: Yup.string().email("Invalid email").required("Required"),
});

export const SignIn = ({ signIn }) => {
    return (
        <Grid item>
            <Paper elevation={3}>
                <Formik
                    validationSchema={signInSchema}
                    initialValues={{ email: "" }}
                    onSubmit={(values, props) => {
                        props.setSubmitting(false);
                        signIn(values.email)
                    }}
                >
                    {(props) => (
                        <Form>

                            <Box p={3} pt={2}
                                 display="flex"
                                 flexDirection="column"
                                 alignItems="flex-end"
                                 width={350}
                           >

                                <Box alignSelf="start">
                                    <Typography variant="h5" gutterBottom>
                                        Sign in
                                    </Typography>
                                </Box>
                                <Box mb={4} width="100%" >
                                    <Field
                                        fullWidth
                                        component={TextField}
                                        name="email"
                                        type="email"
                                        label="Email"
                                    ></Field>
                                </Box>

                                <Box >
                                    <Button>Cancel</Button>
                                    <Button
                                        variant="contained"
                                        color="primary"
                                        disabled={props.isSubmitting}
                                        onClick={props.submitForm}
                                    >
                                        Next
                                    </Button>
                                </Box>
                            </Box>
                        </Form>
                    )}
                </Formik>
            </Paper>
        </Grid>
    );
};
