import React, {useEffect} from 'react';
import {fetchValuations} from "./duck/valuationAction";
import {connect} from "react-redux";


const Valuations = ({fetchValuations, valuations}) => {

    useEffect(() => {
        console.log("fetch")
        fetchValuations();

    }, []);


    const a = () => {
        console.log("valuations:", valuations)
        return (
            <div>
                {valuations.map(v => {
                    console.log("v:", v)
                return <div key={v.id}>{v.id}</div>
            })}
            </div>
        )
    }

    return a();


};

const mapStateToProps = ({valuations}) => {
    console.log(valuations)
    return {
        valuations
    }
};


export default connect(mapStateToProps, {fetchValuations})(Valuations);