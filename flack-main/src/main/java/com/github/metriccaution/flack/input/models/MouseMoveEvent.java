package com.github.metriccaution.flack.input.models;

import java.util.Objects;

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
	public boolean equals(final Object obj) {
		if (!(obj instanceof MouseMoveEvent)) {
			return false;
		}

		final MouseMoveEvent other = (MouseMoveEvent)obj;

		return Objects.equals(getX(), other.getX())
				&& Objects.equals(getY(), other.getY());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getX(), getY());
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("x", getX())
				.add("y", getY())
				.toString();
	}
}
