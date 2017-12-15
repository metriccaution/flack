package com.github.metriccaution.flack.input.service;

import static com.google.common.base.Preconditions.checkNotNull;

import com.github.metriccaution.flack.input.models.InputEventModel;
import com.github.metriccaution.flack.input.models.MouseClickEvent;
import com.github.metriccaution.flack.input.models.MouseMoveEvent;
import com.github.metriccaution.flack.input.models.MouseScrollEvent;
import com.github.metriccaution.flack.input.service.Mouse.Click;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * Glues a {@link Mouse} to an {@link EventBus} accepting
 * {@link InputEventModel}s
 */
public class MouseSubscriber {
	private final Mouse mouse;

	public MouseSubscriber(final Mouse mouse) {
		checkNotNull(mouse);

		this.mouse = mouse;
	}

	@Subscribe
	public void subscribe(final InputEventModel event) {
		if (event instanceof MouseMoveEvent) {
			// Move the mouse
			final MouseMoveEvent moveEvent = (MouseMoveEvent) event;
			mouse.move(moveEvent.getX(), moveEvent.getY());
		}

		if (event instanceof MouseClickEvent) {
			// Click the mouse
			final MouseClickEvent clickEvent = (MouseClickEvent) event;
			mouse.click(Click.fromCode(clickEvent.getCode()), clickEvent.isDown());
		}

		if (event instanceof MouseScrollEvent) {
			// Scroll the mouse
			mouse.scroll(((MouseScrollEvent) event).getDistance());
		}
	}

}
