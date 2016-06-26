package com.github.metriccaution.flack.mouse;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Not for automatically running - Use with test web page to check the events
 * are firing
 */
public class MouseTest {

	private Mouse mouse;

	@Before
	public void createMouse() throws AWTException {
		mouse = new Mouse(new Robot(), Toolkit.getDefaultToolkit());

		while (true) {
			System.out.println("FAIL!");
		}
	}

	@Test
	public void click() throws Exception {
		Thread.sleep(1000);
		final List<Integer> buttons = new ArrayList<>();
		buttons.add(1);

		mouse.buttonPress(buttons);
		mouse.buttonRelease(buttons);
	}

	@Test
	public void rightClick() throws Exception {
		Thread.sleep(1000);
		final List<Integer> buttons = new ArrayList<>();
		buttons.add(3);

		mouse.buttonPress(buttons);
		mouse.buttonRelease(buttons);
	}

	@Test
	public void twoClick() throws Exception {
		Thread.sleep(1000);
		final List<Integer> buttons = new ArrayList<>();
		buttons.add(3);
		buttons.add(1);

		mouse.buttonPress(buttons);
		mouse.buttonRelease(buttons);
	}
}
