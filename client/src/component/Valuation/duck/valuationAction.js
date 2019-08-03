import {rest} from "../../../client/restClient"

export const FETCH_VALUATIONS = "FETCH_VALUATIONS";

export const fetchValuations = () => dispatch =>{
    rest.get("/valuations")
        .then(res => {
            dispatch({
                type: FETCH_VALUATIONS,
                payload: res.data
            });
        })
        .catch(e => console.log('eeeee', e))


};
