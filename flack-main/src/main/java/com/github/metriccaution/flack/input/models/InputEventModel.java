package com.github.metriccaution.flack.input.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.github.metriccaution.flack.input.ws.InputSocket;

/**
 * A marker interface for actions we can expect to receive over the
 * {@link InputSocket}
 */
@JsonTypeInfo(use = Id.NAME, include = As.EXTERNAL_PROPERTY, property = "type")
@JsonSubTypes(value = { //
		@JsonSubTypes.Type(name = InputEventModel.MOUSE_MOVE, value = MouseMoveEvent.class),
		@JsonSubTypes.Type(name = InputEventModel.MOUSE_CLICK, value = MouseClickEvent.class),
		@JsonSubTypes.Type(name = InputEventModel.MOUSE_SCROLL, value = MouseScrollEvent.class) })
public interface InputEventModel {
	public static final String MOUSE_MOVE = "mouse.move";
	public static final String MOUSE_CLICK = "mouse.click";
	public static final String MOUSE_SCROLL = "mouse.scroll";
}
