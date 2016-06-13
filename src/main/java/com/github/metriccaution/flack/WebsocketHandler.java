package com.github.metriccaution.flack;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.function.BiConsumer;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.metriccaution.flack.mouse.Mouse;
import com.github.metriccaution.flack.mouse.models.Model;
import com.github.metriccaution.flack.mouse.models.MouseClick;
import com.github.metriccaution.flack.mouse.models.MouseMove;
import com.github.metriccaution.flack.mouse.models.MouseScroll;

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
		final Model model = mapper.readValue(message, Model.class);
		MethodMap.consume(model, mouse);
	}

	@OnWebSocketError
	public void onError(final Session user, final Throwable ex) {
		ex.printStackTrace();
	}

	private static enum MethodMap {
		MOVE(MouseMove.class, (m, mouse) -> {
			final MouseMove move = (MouseMove) m;
			mouse.moveMouse(move.getX(), move.getY());
		}), //
		CLICK(MouseClick.class, (m, mouse) -> System.out.println("Click")), //
		SCROLL(MouseScroll.class, (m, mouse) -> System.out.println("Scroll"));

		private Class<? extends Model> type;
		private BiConsumer<Model, Mouse> consumer;

		private MethodMap(final Class<? extends Model> type, final BiConsumer<Model, Mouse> consumer) {
			this.type = type;
			this.consumer = consumer;
		}

		public static void consume(final Model model, final Mouse mouse) {

			final Class<? extends Model> modelClass = model.getClass();

			for (final MethodMap method : MethodMap.values()) {

				final Class<? extends Model> checkedType = method.type;

				if (checkedType.equals(modelClass)) {
					method.consumer.accept(model, mouse);
					return;
				}
			}

			throw new IllegalArgumentException("Unmapped method " + modelClass);
		}
	}
}
