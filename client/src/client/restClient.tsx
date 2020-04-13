import axios from 'axios';
const rest = axios.create({
    baseURL: 'http://localhost:8082',
    headers: {
        'Content-Type': 'application/json'
    }
});

export const INIT_SECURITY = "INIT_SECURITY";
export const JWT_HEADER ="jwtAuthToken";

rest.interceptors.response.use((response) => {
    if(response.data.jwtAuthToken) {
        localStorage.setItem(JWT_HEADER, response.data.jwtAuthToken);
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
    },

    post: (url, data) => {
        return rest.post(url, data);
    }
};
