import { Basic } from "../component/Content/Basic";
import { indexBy, prop } from "ramda";
import { Login } from "../component/Login/Login";

const location = (name, path, config = {}) => {
    return {
        name,
        path,
        config
    };
};

export const locations = [
    location("login", "/login", { component: Login }),
    location("basic", "/basic", { component: Basic })
];




export const getLocationsMap = () => {
    return indexBy(prop("name"), locations);

};
