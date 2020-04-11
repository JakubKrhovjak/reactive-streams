import axios from 'axios';
import EventEmitter from "events";
export const emitter = new EventEmitter();
const rest = axios.create({
    baseURL: 'http://localhost:8082'
});

export const INIT_SECURITY = "INIT_SECURITY";
export const JWT_HEADER ="jwtAuthToken";

rest.interceptors.response.use((response) => {
    if(response.data.jwtAuthToken) {
        localStorage.setItem(JWT_HEADER, response.data.jwtAuthToken);
        emitter.emit(INIT_SECURITY, response.data.jwtAuthToken);
    }
    return response;
}, (error) => {
    return Promise.reject(error);
});


rest.interceptors.request.use((request) => {
    if(request.url !== "/login") {
        request.headers.Authorization =`Bearer ${localStorage.getItem(JWT_HEADER)}`
    }
    return request
}, (error) => {
    return Promise.reject(error);
});

export const restService =  {
    authenticate: (username, password) => {
        return rest.get("/login", {   auth: {
                username,
                password,
            },
        })

   },

    get: (url) => {
        return rest.get(url);
    }
};
