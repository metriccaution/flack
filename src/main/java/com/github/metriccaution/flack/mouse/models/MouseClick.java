package com.github.metriccaution.flack.mouse.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MouseClick implements Model {
	private final List<Integer> buttons;

	@JsonCreator
	public MouseClick(@JsonProperty("buttons") final List<Integer> buttons) {
		this.buttons = new ArrayList<>(buttons);
	}

	public List<Integer> getButtons() {
		return new ArrayList<>(buttons);
	}
}
