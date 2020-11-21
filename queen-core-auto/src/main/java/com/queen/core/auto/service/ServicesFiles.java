package com.queen.core.auto.service;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * A helper class for reading and writing Services files.
 *
 * @author jensen
 */
class ServicesFiles {
	private static final Charset UTF_8 = StandardCharsets.UTF_8;

	/**
	 * Reads the set of service classes from a service file.
	 *
	 * @param input not {@code null}. Closed after use.
	 * @return a not {@code null Set} of service class names.
	 * @throws IOException
	 */
	static Set<String> readServiceFile(InputStream input) throws IOException {
		HashSet<String> serviceClasses = new HashSet<>();
		try (
			InputStreamReader isr = new InputStreamReader(input, UTF_8);
			BufferedReader r = new BufferedReader(isr)
		) {
			String line;
			while ((line = r.readLine()) != null) {
				int commentStart = line.indexOf('#');
				if (commentStart >= 0) {
					line = line.substring(0, commentStart);
				}
				line = line.trim();
				if (!line.isEmpty()) {
					serviceClasses.add(line);
				}
			}
			return serviceClasses;
		}
	}

	/**
	 * Writes the set of service class names to a service file.
	 *
	 * @param output   not {@code null}. Not closed after use.
	 * @param services a not {@code null Collection} of service class names.
	 * @throws IOException
	 */
	static void writeServiceFile(Collection<String> services, OutputStream output) throws IOException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, UTF_8));
		for (String service : services) {
			writer.write(service);
			writer.newLine();
		}
		writer.flush();
	}
}
