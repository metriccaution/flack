package com.github.metriccaution.flack.input.ws;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.eclipse.jetty.websocket.api.Session;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.metriccaution.flack.input.models.InputEventModel;
import com.github.metriccaution.flack.input.service.InputEventParser;
import com.github.metriccaution.flack.utils.TestData;
import com.google.common.eventbus.EventBus;

/**
 * Tests for {@link InputSocket}
 */
public class InputSocketTest {
	@Test(expected = NullPointerException.class)
	public void constructor_nullParser() {
		new InputSocket(null, mock(EventBus.class));
	}

	@Test(expected = NullPointerException.class)
	public void constructor_nullEventBus() {
		new InputSocket(mock(InputEventParser.class), null);
	}

	@Test
	public void onMessage_goodMessages() throws IOException {
		final EventBus eventBus = mock(EventBus.class);

		final InputSocket socket = new InputSocket(new InputEventParser(new ObjectMapper()), eventBus);
		for (final InputEventModel model : TestData.allModelTypes()) {
			final String json = new ObjectMapper().writeValueAsString(model);

			socket.onMessage(mock(Session.class), json);

			verify(eventBus, times(1)).post(model);
			reset(eventBus);
		}
	}

	@Test
	public void onMessage_invalidJson() throws IOException {
		final EventBus eventBus = mock(EventBus.class);
		final InputSocket socket = new InputSocket(new InputEventParser(new ObjectMapper()), eventBus);
		socket.onMessage(mock(Session.class), "}{");
		verify(eventBus, never()).post(any());
	}

	@Test
	public void onConnect_doesNothing() throws IOException {
		final EventBus eventBus = mock(EventBus.class);
		final InputSocket socket = new InputSocket(new InputEventParser(new ObjectMapper()), eventBus);
		socket.onConnect(mock(Session.class));
	}

	@Test
	public void onClose_doesNothing() throws IOException {
		final EventBus eventBus = mock(EventBus.class);
		final InputSocket socket = new InputSocket(new InputEventParser(new ObjectMapper()), eventBus);
		socket.close(mock(Session.class), 20, "Blah");
	}

	@Test
	public void onError_doesNothing() throws IOException {
		final EventBus eventBus = mock(EventBus.class);
		final InputSocket socket = new InputSocket(new InputEventParser(new ObjectMapper()), eventBus);
		socket.onError(mock(Session.class), new Exception());
	}
}
