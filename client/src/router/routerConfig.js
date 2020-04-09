import createRouter from 'router5'
import loggerPlugin from 'router5-plugin-logger'
import browserPlugin from 'router5-plugin-browser'
import { locations } from "./locations";

export function configureRouter() {
    const router = createRouter(locations, {
        defaultRoute: 'login'
    });

    router.usePlugin(loggerPlugin);

    router.usePlugin(browserPlugin());

    return router;
}
