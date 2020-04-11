import React, { createContext, useEffect, useState } from "react";
import  jwt  from "jsonwebtoken"

export interface SecurityContext {
    username: string | undefined
    roles: [];
}

const SecurityContext = createContext<SecurityContext>({username: undefined, roles: []});

export const AppContextProvider = SecurityContext.Provider;
export const SecurityContextConsumer = SecurityContext.Consumer;

export const SecurityContextProvider = ({ children }) => {

    const [securityContext,  setSecurityContext] =  useState<SecurityContext>({username: undefined, roles: []});

    useEffect(() => {
        let jwtToken = localStorage.getItem("jwtAuthToken");
        if(jwtToken) {
            let token = jwt.decode(jwtToken);
            setSecurityContext({ username: token.sub, roles: token.roles });
        }
    }, []);

   return (
        <AppContextProvider
            value={securityContext}
        >
            {children}
        </AppContextProvider>
    );

};

export const VisibleOnRoles = ({roles, children}) => {


    return (
        <SecurityContextConsumer>
                      {
                          // @ts-ignore
                          context => context.roles.includes(roles) ? children : <div/>
                      }
        </SecurityContextConsumer>
    )
};



