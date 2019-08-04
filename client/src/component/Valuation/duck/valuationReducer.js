import {FETCH_VALUATIONS} from "./valuationAction";
import {keyBy} from "lodash";

export const valuationReducer = (state = {}, action) => {
    if(action.type === FETCH_VALUATIONS) {
        state = {...state, ...action.payload};
        return state;
    }

    return state;
};

