package com.github.metriccaution.flack;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.IOException;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.metriccaution.flack.mouse.Mouse;
import com.github.metriccaution.flack.mouse.executor.MouseMove;

@WebSocket
public class WebsocketHandler {

	private Mouse mouse;
	private final ObjectMapper mapper;

	public WebsocketHandler() {
		mapper = new ObjectMapper();

		try {
			mouse = new Mouse(new Robot(), Toolkit.getDefaultToolkit());
		} catch (final AWTException e) {
			// TODO - Log / fail
			System.out.println("Error making mouse controller");
		}
	}

	@OnWebSocketMessage
	public void onMessage(final Session user, final String message) throws IOException {
		final MouseMove move = mapper.readValue(message, MouseMove.class);
		System.out.println(move);
		mouse.moveMouse(move.getX(), move.getY());
	}

	@OnWebSocketError
	public void onError(final Session user, final Throwable ex) {
		ex.printStackTrace();
	}
}
