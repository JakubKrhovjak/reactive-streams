import axios from 'axios';

const rest = axios.create({
    baseURL: 'http://localhost:8082'
});


// export const get = (url, actionType) => async dispatch => {
//     const response = await rest.get(url);
//     dispatch({ type: actionType, payload: keyBy(response.data, "id") });
// };

export const restService =  {
    authenticate: (credential) => {
        const credentialString  = `${credential.username}:${credential.password}`;
        return rest.head("/auth", {headers:{"Authorization": btoa(credentialString)}})
   }
};
