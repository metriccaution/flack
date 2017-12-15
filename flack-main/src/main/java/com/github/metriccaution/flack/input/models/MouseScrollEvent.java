package com.github.metriccaution.flack.input.models;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

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

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof MouseScrollEvent)) {
			return false;
		}

		final MouseScrollEvent other = (MouseScrollEvent)obj;

		return Objects.equals(getDistance(), other.getDistance());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getDistance());
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("Distance", getDistance())
				.toString();
	}
}
