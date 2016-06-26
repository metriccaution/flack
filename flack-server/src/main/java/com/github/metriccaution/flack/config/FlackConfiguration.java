package com.github.metriccaution.flack.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class FlackConfiguration {

	private final static String LOCATION = "default.properties";

	private final Properties props;

	public FlackConfiguration() {
		final Properties defaultProps = readDefaults();
		final Properties overriddenProps = readOverrides(defaultProps
				.getProperty("override"));

		props = new Properties();
		props.putAll(defaultProps);
		props.putAll(overriddenProps);
	}

	/**
	 * Read the default properties out of the classpath
	 *
	 * @return The default properties of the application
	 *
	 * @throws IllegalStateException
	 *             If the file could not be read
	 */
	private Properties readDefaults() {
		final Properties defaultProps = new Properties();

		try (final InputStream stream = FlackConfiguration.class
				.getClassLoader().getResourceAsStream(LOCATION)) {
			defaultProps.load(stream);
		} catch (final Exception e) {
			throw new IllegalStateException("Could not load default config", e);
		}

		return defaultProps;
	}

	/**
	 * Read the overridden properties from elsewhere
	 *
	 * @param location
	 *            The location of the file
	 *
	 * @return The config file
	 */
	private Properties readOverrides(final String location) {
		final Properties overriddenProps = new Properties();

		// Check it's been set
		if (location == null)
			return overriddenProps;

		// Check it's there
		final Path path = Paths.get(location);
		if (!(Files.exists(path) || Files.isDirectory(path)))
			return overriddenProps;

		try (InputStream fis = new FileInputStream(path.toFile())) {
			overriddenProps.load(fis);
		} catch (final IOException e) {
			return overriddenProps;
		}

		return overriddenProps;

	}

	public ServerConfig server() {
		return new ServerConfig(props);
	}
}
