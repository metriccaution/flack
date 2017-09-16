package com.github.metriccaution.flack.input.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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

}
