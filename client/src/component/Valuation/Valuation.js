import React from 'react';
import CardContent from "@material-ui/core/CardContent";
import {Card, Typography, Button} from "@material-ui/core";
import CloudUploadIcon from '@material-ui/icons/CloudUpload';
import CardActions from '@material-ui/core/CardActions';
import CardHeader from '@material-ui/core/CardHeader';
import makeStyles from "@material-ui/styles/makeStyles/makeStyles";
import DeleteOutlinedIcon from '@material-ui/icons/DeleteOutlined';

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

    return (
        <Card className={classes.valuation}>

            <Typography>
                {valuation.name}
            </Typography>

            <CardContent>
                <Typography>
                    {valuation.description}
                </Typography>
                <Typography>
                    {valuation.value}
                </Typography>
            </CardContent>
            <CardActions>
                <Button variant="contained" color="secondary">
                    <DeleteOutlinedIcon/>
                </Button>
            </CardActions>
        </Card>
    )
};

