package com.github.metriccaution.flack.input.ws;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.metriccaution.flack.input.models.InputEventModel;
import com.github.metriccaution.flack.input.service.InputEventParser;
import com.google.common.eventbus.EventBus;

/**
 * The websocket handler for managing user input events
 */
@WebSocket
public class InputSocket {

	private static final Logger LOGGER = LoggerFactory.getLogger(InputSocket.class);

	private final InputEventParser parser;
	private final EventBus events;

	public InputSocket(final InputEventParser parser, final EventBus events) {
		checkNotNull(parser);
		checkNotNull(events);

		this.parser = parser;
		this.events = events;
	}

	@OnWebSocketMessage
	public void onMessage(final Session user, final String message) throws IOException {
		try {
			final InputEventModel event = parser.apply(message);
			LOGGER.info("Input: " + event);
			events.post(event);
		} catch (final IllegalArgumentException e) {
			LOGGER.debug("Unparsable websocket message", e);
		}
	}

	@OnWebSocketError
	public void onError(final Session user, final Throwable ex) {
		LOGGER.error("Websocket message exception", ex);
	}

	@OnWebSocketConnect
	public void onConnect(final Session session) {
	}

	@OnWebSocketClose
	public void close(final Session user, final int statusCode, final String reason) {
	}

}
