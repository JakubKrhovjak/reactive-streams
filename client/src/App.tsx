import React from "react";
import { useActiveComponent } from "./hook/useActiveComponent";
import { ErrorHandler } from "./errorHandling/ErroHandel";


function App() {

    const component = useActiveComponent();
    return (
        <>
            {component ?
            <ErrorHandler>
                {component}
            </ErrorHandler>
            : <div></div>}
        </>
    );
}

export default App;
