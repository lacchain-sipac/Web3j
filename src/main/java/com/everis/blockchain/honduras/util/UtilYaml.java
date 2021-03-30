package com.everis.blockchain.honduras.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UtilYaml {
	private static final Logger log = LoggerFactory.getLogger("Yaml");
	public static final String NAME_FILE = System.getProperty("user.dir") + "/resource/configuration.yaml";
//	private static final String NAME_FILE_SPRING = System.getProperty("user.dir")
//			+ "/resource/configurationSpring.yaml";
//	private static final String NAME_FILE_DEPLOYMENT = System.getProperty("user.dir")
//			+ "/resource/deployment.yaml";
//			
	public static final String NAME_FILE_JS = System.getProperty("user.dir") + "/resource/configuration.js";

	public static void saveFile(LinkedHashMap<String, String> map, String nameFile) {

		log.info("<---SAVE---->");

		Path path = Paths.get(nameFile);//NOSONAR
		try (BufferedWriter writer = java.nio.file.Files.newBufferedWriter(path)) {

			for (Map.Entry<String, String> entry : map.entrySet()) {
				// System.out.println(" - " + entry.getKey() + " = " + entry.getValue());

				writer.write("  - " + entry.getKey() + "=" + entry.getValue() + "\n");
			}
		} catch (IOException io) {
			log.info("ERROR.saveFile", io);
		}

	}

	public static void saveFileSpring(LinkedHashMap<String, String> map, String nameFile) {

		log.info("<---SAVE---->");

		Path path = Paths.get(nameFile);//NOSONAR
		try (BufferedWriter writer = java.nio.file.Files.newBufferedWriter(path)) {

			for (Map.Entry<String, String> entry : map.entrySet()) {
				writer.write("  " + capitalize(entry.getKey()) + ": '" + entry.getValue() + "'\n");
			}
		} catch (IOException io) {
			log.info("ERROR.saveFileSpring", io);
		}

	}

	public static void saveFileDeployment(LinkedHashMap<String, String> map, String nameFile) {

		log.info("<---SAVE---->");

		Path path = Paths.get(nameFile);//NOSONAR
		try (BufferedWriter writer = java.nio.file.Files.newBufferedWriter(path)) {

			for (Map.Entry<String, String> entry : map.entrySet()) {
				writer.write("        - name: " + entry.getKey() + "\n");
				writer.write("          value: " + entry.getValue() + "\n");
			}
		} catch (IOException io) {
			log.info("ERROR.saveFileDeployment", io);
		}

	}

	public static void saveFileJS(LinkedHashMap<String, String> map, String nameFile) {

		log.info("<---SAVE---->");

		Path path = Paths.get(nameFile);//NOSONAR
		try (BufferedWriter writer = java.nio.file.Files.newBufferedWriter(path)) {

			for (Map.Entry<String, String> entry : map.entrySet()) {
				writer.write("  '" + capitalize(entry.getKey()) + "': '" + entry.getValue() + "',\n");
			}

		} catch (IOException io) {
			log.info("ERROR.saveFileJS", io);
		}
	}

	@SuppressWarnings("deprecation")
	private static String capitalize(String value) {
		return org.apache.commons.lang3.text.WordUtils.uncapitalize(org.apache.commons.lang3.text.WordUtils
				.capitalize(value.replaceAll("_", " ").toLowerCase()).replace(" ", ""));
	}

	public static LinkedHashMap<String, String> readFile(String nameFile) {
		try {
			// System.out.println("<---READ---->");
			LinkedHashMap<String, String> map = new LinkedHashMap<>();
			Scanner sc = new Scanner(new File(nameFile));//NOSONAR
			while (sc.hasNext()) {

				String str = sc.nextLine();
				String[] tokensVal = str.split("=");

				String key = tokensVal[0].replace('-', ' ').trim();
				String value = "";

				if (tokensVal.length >= 2) {
					value = tokensVal[1].trim();

				}

				if (!StringUtils.isEmpty(key)) {
					// System.out.println(key + " = " + value);
					map.put(key, value);
				}
			}
			sc.close();
			return map;
		} catch (IOException e) {
			log.info("ERROR.readFile", e);

			return null;
		}

	}
}
