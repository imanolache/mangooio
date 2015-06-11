package mangoo.io.routing.handlers;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;
import mangoo.io.enums.Templates;

/**
 *
 * @author svenkubiak
 *
 */
public class ExceptionHandler implements HttpHandler {
    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        exchange.setResponseCode(StatusCodes.BAD_REQUEST);
        exchange.getResponseSender().send(Templates.DEFAULT.badRequest());
    }
}