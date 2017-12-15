package com.github.metriccaution.flack.input.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import org.junit.Test;

import com.github.metriccaution.flack.input.models.InputEventModel;
import com.github.metriccaution.flack.input.models.MouseClickEvent;
import com.github.metriccaution.flack.input.models.MouseMoveEvent;
import com.github.metriccaution.flack.input.models.MouseScrollEvent;
import com.github.metriccaution.flack.input.service.Mouse.Click;

/**
 * Tests for {@link MouseSubscriber}
 */
public class MouseSubscriberTest {
	@Test(expected = NullPointerException.class)
	public void constructor_nullMouse() {
		new MouseSubscriber(null);
	}

	@Test
	public void subscribe_nullEvent() {
		final Mouse mouse = mock(Mouse.class);
		final MouseSubscriber subscriber = new MouseSubscriber(mouse);
		subscriber.subscribe(null);

		verify(mouse, never()).move(any(Integer.class), any(Integer.class));
		verify(mouse, never()).scroll(any(Integer.class));
		verify(mouse, never()).click(any(Click.class), any(Boolean.class));
	}

	@Test
	public void subscribe_unhandledEvent() {
		final Mouse mouse = mock(Mouse.class);
		final MouseSubscriber subscriber = new MouseSubscriber(mouse);
		subscriber.subscribe(new InputEventModel() {});

		verify(mouse, never()).move(any(Integer.class), any(Integer.class));
		verify(mouse, never()).scroll(any(Integer.class));
		verify(mouse, never()).click(any(Click.class), any(Boolean.class));
	}

	@Test
	public void subscribe_mouseMove() {
		final Mouse mouse = mock(Mouse.class);
		final MouseSubscriber subscriber = new MouseSubscriber(mouse);
		subscriber.subscribe(new MouseMoveEvent(5, 12));

		verify(mouse, times(1)).move(5, 12);
		verify(mouse, never()).scroll(any(Integer.class));
		verify(mouse, never()).click(any(Click.class), any(Boolean.class));
	}

	@Test
	public void subscribe_mouseScroll() {
		final Mouse mouse = mock(Mouse.class);
		final MouseSubscriber subscriber = new MouseSubscriber(mouse);
		subscriber.subscribe(new MouseScrollEvent(91));

		verify(mouse, never()).move(any(Integer.class), any(Integer.class));
		verify(mouse, times(1)).scroll(91);
		verify(mouse, never()).click(any(Click.class), any(Boolean.class));
	}

	@Test
	public void subscribe_mouseClick() {
		final Mouse mouse = mock(Mouse.class);
		final MouseSubscriber subscriber = new MouseSubscriber(mouse);
		subscriber.subscribe(new MouseClickEvent(true, 2));

		verify(mouse, never()).move(any(Integer.class), any(Integer.class));
		verify(mouse, never()).scroll(any(Integer.class));
		verify(mouse, times(1)).click(Click.fromCode(2), true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void subscribe_mouseClickBadCode() {
		final Mouse mouse = mock(Mouse.class);
		final MouseSubscriber subscriber = new MouseSubscriber(mouse);
		subscriber.subscribe(new MouseClickEvent(true, -1));
	}
}
