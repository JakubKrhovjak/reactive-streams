import React, {Fragment, useEffect} from 'react';
import {fetchValuations} from "./duck/valuationAction";
import {connect} from "react-redux";

import {Valuation} from "./Valuation"
import Grid from "@material-ui/core/Grid";
import makeStyles from "@material-ui/styles/makeStyles";



const ValuationList = ({fetchValuations, valuations}) => {

    useEffect(() => {
        fetchValuations();
    }, []);


    return (
        <Grid container justify={"center"}>
            <Grid item xs={3}>
                {valuations.map(valuation => {
                    return <Valuation key={valuation.id} valuation={valuation}/>
                })}
            </Grid>
        </Grid>
    )


};

const mapStateToProps = ({valuations}) => {
    return {
        valuations
    }
};


export default connect(mapStateToProps, {fetchValuations})(ValuationList);