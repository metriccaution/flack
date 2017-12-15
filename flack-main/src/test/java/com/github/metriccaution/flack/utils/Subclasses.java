package com.github.metriccaution.flack.utils;

import java.util.Set;
import java.util.stream.Collectors;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

/**
 * Utility for finding subclasses of a given class
 *
 * Currently only exists for testing
 */
public class Subclasses {
	public static <T> Set<Class<? extends T>> subclasses(final String packageLimit, final Class<T> baseClass) {
		final Reflections reflections = new Reflections(
				new ConfigurationBuilder()
				.filterInputsBy(new FilterBuilder().includePackage(packageLimit))
				.setUrls(ClasspathHelper.forPackage(packageLimit))
				.setScanners(new SubTypesScanner()));

		return reflections.getSubTypesOf(baseClass).stream()
				.filter(c -> !c.isAnonymousClass())
				.collect(Collectors.toSet());
	}

	private Subclasses() {
		// Prevent instantiation
	}
}
