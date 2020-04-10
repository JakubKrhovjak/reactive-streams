import React, { createContext, useEffect, useState } from "react";
import  jwt  from "jsonwebtoken"

const SecurityContext = createContext();

export const SecurityContextProvider = ({ children }) => {

    const [securityContext,  setSecurityContext] =  useState({roles: []});

    useEffect(() => {
        let jwtToken = localStorage.getItem("jwtAuthToken");
        if(jwtToken) {
            let token = jwt.decode(jwtToken);
            setSecurityContext({ username: token.sub, roles: token.roles });
        }
    }, []);

 ;   return (
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



