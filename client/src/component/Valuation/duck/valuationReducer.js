import {FETCH_VALUATIONS} from "./valuationAction";

export const valuationReducer = (state = {}, action) => {
    if(action.type === FETCH_VALUATIONS) {
        state = {...state, ...action.payload};
        return state;
    }

    return state;
};

