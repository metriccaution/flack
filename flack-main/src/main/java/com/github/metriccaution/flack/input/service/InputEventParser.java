package com.github.metriccaution.flack.input.service;

import java.io.IOException;
import java.util.function.Function;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.metriccaution.flack.input.models.InputEventModel;

/**
 * Handles mapping strings from the websocket into java objects
 */
public class InputEventParser implements Function<String, InputEventModel> {

	private final ObjectMapper mapper;

	public InputEventParser(final ObjectMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public InputEventModel apply(final String t) {
		try {
			return mapper.readValue(t, InputEventModel.class);
		} catch (final IOException e) {
			throw new IllegalArgumentException("Could not parse websocket data", e);
		}
	}

}
