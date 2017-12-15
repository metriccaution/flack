package com.github.metriccaution.flack.input.service;

import static com.github.metriccaution.flack.utils.Subclasses.subclasses;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.metriccaution.flack.input.models.InputEventModel;
import com.github.metriccaution.flack.utils.TestData;
import com.google.common.collect.Sets;

/**
 * Tests for {@link InputEventParser}
 */
public class InputEventParserTest {
	@Test(expected = NullPointerException.class)
	public void constructor_nullMapper() {
		new InputEventParser(null);
	}

	@Test(expected = NullPointerException.class)
	public void apply_nullArgument() {
		final InputEventParser parser = new InputEventParser(new ObjectMapper());
		parser.apply(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void apply_blankString() {
		final InputEventParser parser = new InputEventParser(new ObjectMapper());
		parser.apply("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void apply_invalidJson() {
		final InputEventParser parser = new InputEventParser(new ObjectMapper());
		parser.apply("}{");
	}

	@Test(expected = IllegalArgumentException.class)
	public void apply_validJsonNotMatchingModel() {
		final InputEventParser parser = new InputEventParser(new ObjectMapper());
		parser.apply("{ \"a\" : \"b\" }");
	}

	@Test
	public void apply_allValidModels() throws Exception {
		final ObjectMapper objectMapper = new ObjectMapper();
		final InputEventParser parser = new InputEventParser(objectMapper);

		for (final InputEventModel model : TestData.allModelTypes()) {
			assertEquals(model, parser.apply(objectMapper.writeValueAsString(model)));
		}
	}

	@Test
	// Test that the list we expect to contain all of the input models does
	public void meta_allModelsTested() {
		final Set<Class<? extends InputEventModel>> classes = subclasses("com.github.metriccaution.flack",
				InputEventModel.class);

		assertTrue("No types of " + InputEventModel.class.getCanonicalName() + " found", classes.size() > 0);

		for (final InputEventModel model : TestData.allModelTypes()) {
			classes.remove(model.getClass());
		}

		assertEquals("There are untested types", Sets.newHashSet(), classes);
	}
}
