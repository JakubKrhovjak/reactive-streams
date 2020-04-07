import axios from 'axios';

const rest = axios.create({
    baseURL: 'http://localhost:8082'
});

rest.interceptors.response.use((response, a, b) => {
    if(response.data.jwtAuthToken) {
        localStorage.setItem("jwtAuthToken", response.data.jwtAuthToken)
    }
    return response;
}, (error) => {
    return Promise.reject(error);
});

// rest.interceptors.request.use((config) => {
//     config.headers.genericKey = "someGenericValue";
//     return config;
// }, (error) => {
//     return Promise.reject(error);
// });

// export const get = (url, actionType) => async dispatch => {
//     const response = await rest.get(url);
//     dispatch({ type: actionType, payload: keyBy(response.data, "id") });
// };

export const restService =  {
    authenticate: (username, password) => {
        return rest.get("/auth", {   auth: {
                username,
                password,
            },
        })

   }
};
