package com.github.metriccaution.flack;

import static spark.Spark.*;

public class Main {

	public static void main(final String[] args) {
		staticFileLocation("/static");
		webSocket("/control", WebsocketHandler.class);
		init();

		/*before("/control", (req, res) -> {
			// TODO - WS access control
			System.out.println(req.pathInfo());
			System.out.println(req.requestMethod());
		});*/
	}

}
