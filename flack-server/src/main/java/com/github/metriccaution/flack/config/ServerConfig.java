package com.github.metriccaution.flack.config;

import java.util.Properties;

public class ServerConfig extends Config {
	public ServerConfig(final Properties props) {
		super("server", props);
	}

	public int port() {
		return getInt("port");
	}
}
