import React from "react";
import { useActiveComponent } from "./hook/useActiveComponent";


function App() {

    const component = useActiveComponent();
    return (
        <>
            {component}
        </>
    );
}

export default App;
