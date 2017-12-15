package com.github.metriccaution.flack.input.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.awt.Point;
import java.awt.Robot;
import java.util.Random;
import java.util.function.Supplier;

import org.junit.Test;

import com.github.metriccaution.flack.input.service.Mouse.Click;

/**
 * Tests {@link Mouse}
 */
public class MouseTest {
	@Test(expected = NullPointerException.class)
	public void constructor_nullRobot() {
		new Mouse(null, fixedMouseLocation(0, 0));
	}

	@Test(expected = NullPointerException.class)
	public void constructor_nullMouseLocation() {
		new Mouse(mock(Robot.class), null);
	}

	@Test
	public void move_comprehensiveSmallSquare() {
		final int minLocation = -100;
		final int maxLocation = 100;

		final Robot robotMock = mock(Robot.class);
		final Mouse mouse = new Mouse(robotMock, fixedMouseLocation(0, 0));

		for (int x = minLocation ; x < maxLocation ; x++) {
			for (final int y = minLocation ; x < maxLocation ; x++) {
				mouse.move(x, y);
				verify(robotMock, times(1)).mouseMove(x, y);
			}
		}
	}

	@Test
	public void move_randomMoves() {
		final Robot robotMock = mock(Robot.class);
		final Random rand = new Random();
		final int maxValue = Integer.MAX_VALUE / 2;

		final int iterations = 1000;
		for (int i = 0; i < iterations; i++) {
			final int startingX = rand.nextInt(maxValue);
			final int startingY = rand.nextInt(maxValue);
			final int moveX = rand.nextInt(maxValue);
			final int moveY = rand.nextInt(maxValue);

			final Mouse mouse = new Mouse(robotMock, fixedMouseLocation(startingX, startingY));
			mouse.move(moveX, moveY);
			verify(robotMock, times(1)).mouseMove(startingX + moveX, startingY + moveY);
		}
	}

	@Test
	public void move_returnsSelf() {
		final Robot robotMock = mock(Robot.class);
		final Mouse mouse = new Mouse(robotMock, fixedMouseLocation(0, 0));
		final Mouse retVal = mouse.move(0, 0);
		assertEquals(mouse, retVal);
	}

	@Test(expected = ArithmeticException.class)
	public void move_integerOverflowX() {
		final Robot robotMock = mock(Robot.class);
		final Mouse mouse = new Mouse(robotMock, fixedMouseLocation(Integer.MAX_VALUE, 0));
		mouse.move(Integer.MAX_VALUE, 0);
	}

	@Test(expected = ArithmeticException.class)
	public void move_integerOverflowY() {
		final Robot robotMock = mock(Robot.class);
		final Mouse mouse = new Mouse(robotMock, fixedMouseLocation(0, Integer.MAX_VALUE));
		mouse.move(0, Integer.MAX_VALUE);
	}

	@Test
	public void scroll_returnsSelf() {
		final Robot robotMock = mock(Robot.class);
		final Mouse mouse = new Mouse(robotMock, fixedMouseLocation(0, 0));
		final Mouse retVal = mouse.scroll(0);
		assertEquals(retVal, mouse);
	}

	@Test
	public void scroll_valuePassThrough() {
		final Robot robotMock = mock(Robot.class);
		final Mouse mouse = new Mouse(robotMock, fixedMouseLocation(0, 0));

		final int minValue = -100;
		final int maxValue = 100;
		for (int i = minValue; i < maxValue; i++) {
			mouse.scroll(i);
			verify(robotMock, times(1)).mouseWheel(i);
		}
	}

	@Test
	public void scroll_minMaxValues() {
		final Robot robotMock = mock(Robot.class);
		final Mouse mouse = new Mouse(robotMock, fixedMouseLocation(0, 0));

		mouse.scroll(Integer.MIN_VALUE);
		verify(robotMock, times(1)).mouseWheel(Integer.MIN_VALUE);

		mouse.scroll(Integer.MAX_VALUE);
		verify(robotMock, times(1)).mouseWheel(Integer.MAX_VALUE);
	}

	@Test
	public void click_down() {
		final Robot robotMock = mock(Robot.class);
		final Mouse mouse = new Mouse(robotMock, fixedMouseLocation(0, 0));

		for (final Click click : Click.values()) {
			mouse.click(click, true);
			verify(robotMock, times(1)).mousePress(click.getAwtCode());
		}
	}

	@Test
	public void click_up() {
		final Robot robotMock = mock(Robot.class);
		final Mouse mouse = new Mouse(robotMock, fixedMouseLocation(0, 0));

		for (final Click click : Click.values()) {
			mouse.click(click, false);
			verify(robotMock, times(1)).mouseRelease(click.getAwtCode());
		}
	}

	@Test
	public void click_returnsSelf() {
		final Robot robotMock = mock(Robot.class);
		final Mouse mouse = new Mouse(robotMock, fixedMouseLocation(0, 0));
		final Mouse retVal = mouse.click(Click.LEFT, true);
		assertEquals(mouse, retVal);
	}

	@Test
	public void clickEnum_fromCode_valid() {
		assertEquals(Click.LEFT, Click.fromCode(0));
		assertEquals(Click.RIGHT, Click.fromCode(1));
		assertEquals(Click.MIDDLE, Click.fromCode(2));
	}

	@Test(expected = IllegalArgumentException.class)
	public void clickEnum_fromCode_invalid() {
		Click.fromCode(-1);
	}

	/**
	 * Create a mock mouse location fetcher based off a fixed point
	 *
	 * @param x
	 *            The x value of the point
	 * @param y
	 *            The y value of the point
	 * @return Always the same point
	 */
	private static Supplier<Point> fixedMouseLocation(final int x, final int y) {
		return () -> new Point(x, y);
	}
}
