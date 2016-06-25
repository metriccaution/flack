package com.github.metriccaution.flack.mouse;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Wraps mouse functionality
 */
public class Mouse {

	/**
	 * Hardcoded press to mask mapping
	 */
	private static Map<Integer, Integer> CODE_TO_MASK;

	static {
		CODE_TO_MASK = new HashMap<>();
		CODE_TO_MASK.put(1, InputEvent.BUTTON1_DOWN_MASK);
		CODE_TO_MASK.put(2, InputEvent.BUTTON2_DOWN_MASK);
		CODE_TO_MASK.put(3, InputEvent.BUTTON3_DOWN_MASK);
	}

	private final Robot robot;
	private final boolean extraKeysEnabled;
	private int pressedMask;

	public Mouse(final Robot rb, final Toolkit tk) throws AWTException {
		robot = rb;
		extraKeysEnabled = tk.areExtraMouseButtonsEnabled();
		pressedMask = 0;
	}

	/**
	 * Move the mouse a relative distance
	 *
	 * @param x
	 *            The x amount to move
	 * @param y
	 *            The y amount to move
	 */
	public void moveMouse(final int x, final int y) {
		final Point p = MouseInfo.getPointerInfo().getLocation();
		robot.mouseMove(p.x + x, p.y + y);
	}

	/**
	 * Press a number of buttons on the mouse
	 *
	 * @param buttons
	 *            The buttons to press
	 */
	public void buttonPress(final List<Integer> buttons) {
		final int mask = flattenMouseButtonList(buttons);
		updatePressedKeys(mask, true);
		robot.mousePress(mask);
	}

	/**
	 * Release currently pressed buttons
	 *
	 * @param buttons
	 *            The buttons to release
	 */
	public void buttonRelease(final List<Integer> buttons) {
		final int mask = flattenMouseButtonList(buttons);
		updatePressedKeys(mask, false);
		robot.mouseRelease(mask);
	}

	/**
	 * Scroll a given amount a given number of 'notches'
	 *
	 * @param scroll
	 *            The number of times to scroll, negative for backwards
	 */
	public void scroll(final int scroll) {
		robot.mouseWheel(scroll);
	}

	/**
	 * Stop pressing any pressed keys keys
	 */
	public void cleanUp() {
		robot.mouseRelease(pressedMask);
		pressedMask = 0;
	}

	/**
	 * Turn a list of buttons into a key-press mask
	 *
	 * @param buttons
	 *            The list of buttons to press
	 * @return The mask
	 */
	private int flattenMouseButtonList(final List<Integer> buttons) {
		Stream<Integer> buttonStream = buttons.stream();

		if (extraKeysEnabled) {
			buttonStream = buttonStream //
					.map(button -> InputEvent.getMaskForButton(button));
		} else {
			buttonStream = buttonStream //
					.filter(button -> CODE_TO_MASK.keySet().contains(button)) //
					.map(button -> CODE_TO_MASK.get(button));
		}

		final Optional<Integer> mask = buttonStream //
				.reduce((prev, curr) -> {
					return prev | curr;
				});

		return mask.orElse(0);
	}

	/**
	 * Update the internal list of pressed keys
	 *
	 * @param mask
	 *            The update
	 * @param add
	 *            If the keys are being pressed (true) or released (false)
	 */
	private void updatePressedKeys(final int mask, final boolean add) {
		if (add) {
			pressedMask = pressedMask | mask;
		} else {
			pressedMask = pressedMask & ~mask;
		}
	}

}