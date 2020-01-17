package com.everis.blockchain.honduras;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator.Feature;

class UtilTest {
	private static final Logger log = LoggerFactory.getLogger(UtilTest.class);
	private static final String NAME_FILE = "/resource/configuration.yaml";

	static void write(BeanYaml bean) {
		try {

			ObjectMapper om = new ObjectMapper(new YAMLFactory().enable(Feature.MINIMIZE_QUOTES));

			om.writeValue(new File(System.getProperty("user.dir") + NAME_FILE), bean);
			
		} catch (Exception e) {
			log.error("write", e);
		}
	}

	static BeanYaml read() {
		try {
//
//			Yaml yaml = new Yaml(new Constructor(BeanYaml.class));
//			return yaml.load(new FileInputStream(System.getProperty("user.dir") + "/resource/file.yaml"));
			File file = new File(System.getProperty("user.dir") + NAME_FILE);
			// Instantiating a new ObjectMapper as a YAMLFactory
			ObjectMapper om = new ObjectMapper(new YAMLFactory());

			// Mapping the employee from the YAML file to the Employee class
			return om.readValue(file, BeanYaml.class);

		} catch (Exception e) {
			log.error("write", e);
			return null;
		}
	}
}
