package com.github.metriccaution.flack.input.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An instruction to scroll the mouse
 */
public class MouseScrollEvent implements InputEventModel {

	private final int distance;

	@JsonCreator
	public MouseScrollEvent(@JsonProperty("distance") final int distance) {
		this.distance = distance;
	}

	public int getDistance() {
		return distance;
	}

}
