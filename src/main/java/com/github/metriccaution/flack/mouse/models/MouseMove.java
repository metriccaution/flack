package com.github.metriccaution.flack.mouse.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MouseMove implements Model {
	private final int x;
	private final int y;

	@JsonCreator
	public MouseMove(@JsonProperty("x") final int x, //
			@JsonProperty("y") final int y) {
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
		return x + "," + y;
	}
}
