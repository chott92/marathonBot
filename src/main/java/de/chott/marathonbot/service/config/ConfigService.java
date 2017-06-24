/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose
 * Tools | Templates and open the template in the editor.
 */
package de.chott.marathonbot.service.config;

import de.chott.marathonbot.service.SingletonService;
import de.chott.marathonbot.service.SingletonServiceFactory;
import de.chott.marathonbot.service.util.UtilService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author chot
 */
public class ConfigService implements SingletonService {

	private UtilService utilService;

	private File file;

	private JSONObject configJSON;

	public ConfigService() {
		utilService = SingletonServiceFactory.getInstance(UtilService.class);

		String filename = "/config.json";
		try {
			file = new File(utilService.getAppFolderFilepath() + filename);
			if (!file.exists()) {
				file.createNewFile();
			}

			readFile(file);

		} catch (IOException ex) {

			Logger.getLogger(ConfigService.class.getName()).log(Level.SEVERE,
					"Could not initialize CredentialConfigService", ex);
		}
	}

	private void readFile(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		StringBuilder sb = new StringBuilder();
		String s;

		while ((s = reader.readLine()) != null) {
			sb.append(s);
		}
		try {
			configJSON = new JSONObject(sb.toString());
		} catch (JSONException ex) {
			Logger.getLogger(ConfigService.class.getName()).log(Level.WARNING,
					"config JSON not readable, creating empty config.");
			configJSON = new JSONObject();
		}

	}

	public Optional<String> getConfig(String key) {
		try {
			return Optional.of(configJSON.getString(key));
		} catch (JSONException e) {
			return Optional.empty();
		}
	}

	public void setConfig(String key, String value) {
		configJSON.put(key, value);
	}

	public void saveConfigToFile() {
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(configJSON.toString());
			writer.flush();
			writer.close();
		} catch (IOException ex) {
			Logger.getLogger(ConfigService.class.getName()).log(Level.SEVERE,
					"Could not save Config to File.", ex);
		}
	}

    @Override
    public void close() {
        saveConfigToFile();
    }

    

}
