import React, { useEffect, useState,  } from "react";
import { VisibleOnRoles } from "../../security/SecurityContext";

import {restService} from "../../client/restClient";

export const Basic = () => {

    const [content, setContent] = useState(null);

    useEffect(() => {
        restService.get("/basic")
            .then(res => setContent(res.data))
            .catch(e => {
                console.log("Error", e)
            })
    },[])

    return(
          <VisibleOnRoles roles={"USER"}>
             <div>{content}</div>
          </VisibleOnRoles>
    )

};