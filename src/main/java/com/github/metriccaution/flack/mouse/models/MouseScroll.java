package com.github.metriccaution.flack.mouse.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MouseScroll implements Model {
	private final int distance;

	@JsonCreator
	public MouseScroll(@JsonProperty("distance") final int distance) {
		this.distance = distance;
	}

	public int getDistance() {
		return distance;
	}
}
