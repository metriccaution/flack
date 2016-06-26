package com.github.metriccaution.flack;

import static spark.Spark.*;

import com.github.metriccaution.flack.config.FlackConfiguration;
import com.github.metriccaution.flack.config.ServerConfig;

public class Main {

	public static void main(final String[] args) {

		final FlackConfiguration config = new FlackConfiguration();
		final ServerConfig serverConfig = config.server();

		staticFileLocation("/static");
		webSocket("/control", WebsocketHandler.class);
		port(serverConfig.port());
		init();

		get("/kill", (req, res) -> {
			stop();
			return "A friend of mine is out of time";
		});
	}

}
