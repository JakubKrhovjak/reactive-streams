import React, {Fragment, useEffect} from 'react';
import {fetchValuations} from "./duck/valuationAction";
import {connect} from "react-redux";

import {Valuation} from "./Valuation"
import Grid from "@material-ui/core/Grid";
import makeStyles from "@material-ui/styles/makeStyles";
import {Card} from "@material-ui/core";

import {keys} from "lodash";

const useStyles = makeStyles(theme => ({
    valuationList: {
       margin: theme.spacing(1)
        // color: theme.palette.text.secondary,
    },

    content: {
        padding: theme.spacing(1)
    }

}));


const ValuationList = ({fetchValuations, valuations}) => {

    const classes = useStyles();

    useEffect(() => {
        fetchValuations();
    }, []);

    const a = () => {
        return <Grid className={classes.valuationList} container justify={"center"}>
            <Card className={classes.content}>
                <Grid>
                    {keys(valuations).map(id => <Valuation key={id} valuation={valuations[id]}/>)}
                </Grid>
            </Card>
        </Grid>
    };

    return a();


};

const mapStateToProps = ({valuations}) => {
    return {
        valuations
    }
};


export default connect(mapStateToProps, {fetchValuations})(ValuationList);