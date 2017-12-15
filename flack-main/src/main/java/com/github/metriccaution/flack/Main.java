package com.github.metriccaution.flack;

import static spark.Spark.*;

import java.awt.MouseInfo;
import java.awt.Robot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.metriccaution.flack.input.service.InputEventParser;
import com.github.metriccaution.flack.input.service.Mouse;
import com.github.metriccaution.flack.input.service.MouseSubscriber;
import com.github.metriccaution.flack.input.ws.InputSocket;
import com.github.metriccaution.flack.ui.StaticResourcesConfig;
import com.google.common.eventbus.EventBus;

/**
 * Main entry point for the web-application
 */
public class Main {

	// TODO - Define an actual logger
	// TODO - Application-level crypto (WSS isn't really an option without massive cert warnings)
	// TODO - Some sort of auth (e.g. PIN)
	// TODO - Response data / errors

	public static void main(final String[] args) throws Exception {
		final InputEventParser parser = new InputEventParser(new ObjectMapper());

		final Robot robot = new Robot();
		final Mouse mouse = new Mouse(robot, () -> MouseInfo.getPointerInfo().getLocation());
		final MouseSubscriber service = new MouseSubscriber(mouse);

		final EventBus messages = new EventBus();
		messages.register(service);

		staticFileLocation(StaticResourcesConfig.STATIC_RESOURCES_DIR);
		webSocket("/control", new InputSocket(parser, messages));

		port(9731);
		init();
	}

}
