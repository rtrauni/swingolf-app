package at.swingolf.app.web;


import io.vertx.ext.web.handler.sockjs.BridgeEventType;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.handler.StaticHandler;
import io.vertx.rxjava.ext.web.handler.sockjs.SockJSHandler;

public class UIVerticle extends AbstractVerticle {
    public void start() throws Exception {
        Router router = Router.router(vertx);
        BridgeOptions options = new BridgeOptions().
                addOutboundPermitted(new PermittedOptions().setAddress("ping")).
                addInboundPermitted(new PermittedOptions().setAddress("users")).
                addInboundPermitted(new PermittedOptions().setAddress("users-and-license"));

        router.route("/eventbus/*").handler(SockJSHandler.create(vertx).bridge(options, event -> {
            // You can also optionally provide a handler like this which will be passed any events that occur on the bridge
            // You can use this for monitoring or logging, or to change the raw messages in-flight.
            // It can also be used for fine grained access control.

            if (event.type() == BridgeEventType.SOCKET_CREATED) {
                System.out.println("A socket was created");
            }

            // This signals that it's ok to process the event
            event.complete(true);
        }));

        // Serve the static resources
        router.route().handler(StaticHandler.create());

        vertx.createHttpServer().requestHandler(router::accept).listen(8080);

        vertx.setPeriodic(1000, t -> vertx.eventBus().publish("ping", "ping"));

        vertx.deployVerticle("at.swingolf.app.web.PersistenceVerticle");
    }
}
