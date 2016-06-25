package com.github.metriccaution.flack.mouse.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MouseClick implements Model {
	private final boolean down;
	private final List<Integer> buttons;

	@JsonCreator
	public MouseClick(@JsonProperty("buttons") final List<Integer> buttons, @JsonProperty("down") final boolean down) {
		this.buttons = new ArrayList<>(buttons);
		this.down = down;
	}

	public List<Integer> getButtons() {
		return new ArrayList<>(buttons);
	}

	public boolean isDown() {
		return down;
	}
}
