import createRouter from 'router5'
import loggerPlugin from 'router5-plugin-logger'
import browserPlugin from 'router5-plugin-browser'
import { routes }from './routes'

export function configureRouter() {
    const router = createRouter(routes, {
        defaultRoute: 'auth'
    });

    router.usePlugin(loggerPlugin);

    router.usePlugin(browserPlugin());

    return router;
}
