import axios from 'axios';
import EventEmitter from "events";
export const emitter = new EventEmitter();
const rest = axios.create({
    baseURL: 'http://localhost:8082'
});

export const INIT_SECURITY = "INIT_SECURITY";

rest.interceptors.response.use((response, a, b) => {
    if(response.data.jwtAuthToken) {
        localStorage.setItem("jwtAuthToken", response.data.jwtAuthToken);
        emitter.emit(INIT_SECURITY, response.data.jwtAuthToken);
    }
    return response;
}, (error) => {
    return Promise.reject(error);
});

export const restService =  {
    authenticate: (username, password) => {
        return rest.get("/auth", {   auth: {
                username,
                password,
            },
        })

   }
};
