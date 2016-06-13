package com.github.metriccaution.flack.mouse.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use = Id.NAME, include = As.EXTERNAL_PROPERTY, property = "type")
@JsonSubTypes(value = { //
		@JsonSubTypes.Type(value = MouseClick.class), //
		@JsonSubTypes.Type(value = MouseMove.class), //
		@JsonSubTypes.Type(value = MouseScroll.class) })
public interface Model {
}
