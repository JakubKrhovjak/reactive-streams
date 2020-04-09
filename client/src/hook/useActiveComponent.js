import { useEffect, useState } from "react";
import { useRouteNode } from "react-router5";
import { getLocationsMap } from "../router/locations";



export const useActiveComponent = () => {

    const { route } = useRouteNode("");
    const [component, setComponent] = useState();

    useEffect(() => {
        if (route) {
            const locationMap = getLocationsMap();
            const routeName = route.name.split(/\s(\.)$/gi);
            setComponent(locationMap[routeName].config.component);
        } else {

        }

    }, [route]);

    return component;

};

