package com.github.metriccaution.flack.input.service;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.Math.addExact;

import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.function.IntConsumer;
import java.util.function.Supplier;

/**
 * A wrapper to handle mouse events
 */
public class Mouse {
	private final Robot robot;
	private final Supplier<Point> mouseLocation;

	/**
	 * Create a new mouse object around the AWT base control object
	 *
	 * @param robot
	 *            The thing we actually use to move the mouse
	 * @param mouseLocation
	 *            Supplies the current location of the mouse pointer
	 */
	public Mouse(final Robot robot, final Supplier<Point> mouseLocation) {
		checkNotNull(robot);
		checkNotNull(mouseLocation);

		this.robot = robot;
		this.mouseLocation = mouseLocation;
	}

	/**
	 * Move the mouse (movements are relative)
	 *
	 * @param x
	 *            How far to move right (negative is left)
	 * @param y
	 *            How far to move down (negative is up)
	 * @return This object again
	 */
	public Mouse move(final int x, final int y) {
		final Point p = mouseLocation.get();
		robot.mouseMove(addExact(p.x, x), addExact(p.y, y));
		return this;
	}

	/**
	 * Scroll the mousewheel
	 *
	 * @param distance
	 *            How far down to scroll (Down is positive, up is negative)
	 * @return This object again
	 */
	public Mouse scroll(final int distance) {
		robot.mouseWheel(distance);
		return this;
	}

	/**
	 * Click a mouse button
	 *
	 * @param click
	 *            The button being clicked
	 * @param down
	 *            If the button is going down, or coming up
	 * @return This object again
	 */
	public Mouse click(final Click click, final boolean down) {
		// TODO - Does this allow for having multiple buttons held down at once?
		final IntConsumer method = down ? robot::mousePress : robot::mouseRelease;
		method.accept(click.getAwtCode());
		return this;
	}

	/**
	 * Maps our own protocol button codes to the AWT ones
	 */
	public static enum Click {
		LEFT(0, InputEvent.BUTTON1_MASK),
		RIGHT(1, InputEvent.BUTTON3_MASK),
		MIDDLE(2, InputEvent.BUTTON2_MASK);

		private int code;
		private int awtCode;

		private Click(final int code, final int awtCode) {
			this.code = code;
			this.awtCode = awtCode;
		}

		public int getAwtCode() {
			return awtCode;
		}

		public static Mouse.Click fromCode(final int code) {
			for (final Mouse.Click click : Click.values())
				if (click.code == code)
					return click;

			throw new IllegalArgumentException("Unknown click code " + code);
		}
	}
}