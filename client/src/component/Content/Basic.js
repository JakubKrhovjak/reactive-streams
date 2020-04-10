import React from 'react';
import { VisibleOnRoles } from "../../security/SecurityContext";

export const Basic = () => {

    return(
          <VisibleOnRoles roles={"USER"}>
             <div>Basic</div>
          </VisibleOnRoles>
    )

};