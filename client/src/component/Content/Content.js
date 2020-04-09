import React from "react";
import { useActiveComponent } from "../../hook/useActiveComponent";

export const Content = () => {
     const component = useActiveComponent();
    return (
        <div>
            {component}
        </div>
    );
};

