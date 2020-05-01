import React from "react";
import { Basic } from "../component/Content/Basic";
import { indexBy, prop } from "ramda";
import { LoginContainer } from "../component/Login/LoginContainer";


const location = (name, path, config = {}) => {
    return {
        name,
        path,
        config
    };
};

export const locations = [
    location("login", "/login", { component: <LoginContainer/> }),
    location("basic", "/basic", { component: <Basic /> })
];




export const getLocationsMap = () => {
    return indexBy(prop("name"), locations);

};
