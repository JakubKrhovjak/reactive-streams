import React, { createContext, useEffect, useState } from "react";
import {emitter} from "../client/restClient.js"
import  jwt  from "jsonwebtoken"
import { INIT_SECURITY } from "../client/restClient";

const SecurityContext = createContext();


export const SecurityContextProvider = ({ children }) => {

    const [securityContext,  setSecurityContext] =  useState({});

    useEffect(() => {
        emitter.on(INIT_SECURITY, jwtToken =>
        {
            let token = jwt.decode(jwtToken);
            setSecurityContext({username: token.sub, roles: token.roles});
        });

    }, []);

    return (
        <SecurityContext.Provider
            value={securityContext}
        >
            {children}
        </SecurityContext.Provider>
    );

};

export const VisibleOnRoles = ({roles, children}) => {

    return (
        <SecurityContext.Consumer>
            {context => context.roles.includes(roles) ? children : <div/>}
        </SecurityContext.Consumer>
    )
};



