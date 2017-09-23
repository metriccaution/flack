package com.github.metriccaution.flack.input.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

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

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("x", x)
				.add("y", y)
				.toString();
	}

}
