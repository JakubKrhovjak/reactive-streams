import axios from 'axios';

import {keyBy} from "lodash"

const rest = axios.create({
    baseURL: 'http://localhost:8082'
});


export const get = (url, actionType) => async dispatch => {
    const response = await rest.get(url);
    dispatch({ type: actionType, payload: keyBy(response.data, "id") });
};
