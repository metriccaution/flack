package com.github.metriccaution.flack;

import static spark.Spark.*;

public class Main {

	public static void main(final String[] args) {

		staticFileLocation("/static");
		webSocket("/control", WebsocketHandler.class);
		init();

		get("/kill", (req, res) -> {
			stop();
			return "A friend of mine is out of time";
		});
	}

}
