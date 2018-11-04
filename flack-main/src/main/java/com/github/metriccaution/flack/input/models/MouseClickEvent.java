package com.github.metriccaution.flack.input.models;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

/**
 * An instruction to click the mouse
 */
public class MouseClickEvent implements InputEventModel {

	private final boolean down;
	private final int code;

	@JsonCreator
	public MouseClickEvent(@JsonProperty("down") final boolean down, @JsonProperty("code") final int code) {
		this.down = down;
		this.code = code;
	}

	public boolean isDown() {
		return down;
	}

	public int getCode() {
		return code;
	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof MouseClickEvent)) {
			return false;
		}

		final MouseClickEvent other = (MouseClickEvent)obj;

		return Objects.equals(isDown(), other.isDown())
				&& Objects.equals(getCode(), other.getCode());
	}

	@Override
	public int hashCode() {
		return Objects.hash(isDown(), getCode());
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("Button", getCode())
				.add("Down", isDown())
				.toString();
	}
}
