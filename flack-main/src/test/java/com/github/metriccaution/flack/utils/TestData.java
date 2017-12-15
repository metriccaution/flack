package com.github.metriccaution.flack.utils;

import java.util.Collection;

import com.github.metriccaution.flack.input.models.InputEventModel;
import com.github.metriccaution.flack.input.models.MouseClickEvent;
import com.github.metriccaution.flack.input.models.MouseMoveEvent;
import com.github.metriccaution.flack.input.models.MouseScrollEvent;
import com.google.common.collect.Lists;

public class TestData {

	private TestData() {
		// Prevent instantiation
	}

	/**
	 * Get an example instance of all of the possible types of
	 * {@link InputEventModel} so we can test that they parse as expected
	 *
	 * @return A collection of all input models
	 */
	public static Collection<InputEventModel> allModelTypes() {
		return Lists.newArrayList(new MouseClickEvent(true, 15), new MouseMoveEvent(5, 10), new MouseScrollEvent(95));
	}
}
