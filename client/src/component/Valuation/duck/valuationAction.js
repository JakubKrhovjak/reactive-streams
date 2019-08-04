import {get} from "../../../client/restClient"


export const FETCH_VALUATIONS = "FETCH_VALUATIONS";
export const DELETE_VALUATION = "DELETE_VALUATION";

export const fetchValuations = () => get("/valuations", FETCH_VALUATIONS);



