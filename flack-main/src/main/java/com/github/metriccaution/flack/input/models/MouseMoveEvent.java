package com.github.metriccaution.flack.input.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An instruction to move the mouse
 */
public class MouseMoveEvent implements InputEventModel {

	private final int x;
	private final int y;

	@JsonCreator
	public MouseMoveEvent(@JsonProperty("x") final int x, @JsonProperty("y") final int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
