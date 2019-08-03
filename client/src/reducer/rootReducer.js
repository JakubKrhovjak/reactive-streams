import {combineReducers} from "redux";
import {valuationReducer} from "../component/Valuation/duck/valuationReducer";

export const rootReducer = combineReducers({
    valuations: valuationReducer
});
