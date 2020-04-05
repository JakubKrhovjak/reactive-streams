import axios from 'axios';

const rest = axios.create({
    baseURL: 'http://localhost:8082'
});

rest.interceptors.response.use((response, a, b) => {
    // let headers = response.headers.jwtAuthToken;
    return response => response;
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
    authenticate: (credential) => {
        const credentialString  = `${credential.username}:${credential.password}`;
        // return rest.head("/auth", {headers:{"Authorization": `Basic ${btoa(credentialString)}`}})

        // return rest.head("/auth", {headers:{"Authorization": " Basic dGVzdDoxMjM=", "Access-Control-Allow-Origin": "*"}})

        return rest.head("/auth", {   auth: {
                username: 'test',
                password: '123'
            },
        })

   }
};
