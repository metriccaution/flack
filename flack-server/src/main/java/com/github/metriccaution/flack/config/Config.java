package com.github.metriccaution.flack.config;

import java.util.Properties;

abstract class Config {

	private final String prefix;
	private final Properties props;

	protected Config(final String prefix, final Properties props) {
		this.prefix = prefix;
		this.props = props;
	}

	protected String get(final String property) {
		return props.getProperty(prefix + "." + property);
	}

	protected int getInt(final String property) {
		final String raw = get(property);
		return Integer.parseInt(raw);
	}
}
