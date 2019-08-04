import {rest} from "../../../client/restClient"
import {keyBy} from "lodash"

export const FETCH_VALUATIONS = "FETCH_VALUATIONS";

export const fetchValuations = () => dispatch =>{
    rest.get("/valuations")
        .then(res => {
            dispatch({
                type: FETCH_VALUATIONS,
                payload: keyBy(res.data, "id")
            });
        })
        .catch(e => console.log('eeeee', e))


};
