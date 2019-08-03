import React from 'react';
import {Card, Typography} from "@material-ui/core";
import CardContent from "@material-ui/core/CardContent";
import makeStyles from "@material-ui/styles/makeStyles/makeStyles";

const useStyles = makeStyles(theme => ({
    valuation: {
        marginTop: theme.spacing(1),
        '&:last-child': {
            marginBottom: theme.spacing(1)
        }
        // color: theme.palette.text.secondary,
    },
}));

export const Valuation = ({valuation}) => {

    const classes = useStyles();

    return(
        <Card className={classes.valuation}>
        <CardContent>
            <Typography >
                {valuation.name}
            </Typography>
            <Typography>
                {valuation.description}
            </Typography>
            <Typography>
                {valuation.value}
            </Typography>
        </CardContent>
        </Card>
    )
};

