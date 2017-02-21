/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose
 * Tools | Templates and open the template in the editor.
 */
package de.chott.marathonbot.service.config;

import de.chott.marathonbot.service.SingletonService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author chot
 */
public class CredentialConfigService implements SingletonService {

	private File file;

	private JSONObject configJSON;

	public CredentialConfigService() {
		String filepath = "/credentials.json";
		try {
			File file = new File(getClass().getResource(filepath).toURI());
			if (!file.exists()) {
				file.createNewFile();
			}

			readFile(file);

		} catch (IOException | URISyntaxException ex) {

			Logger.getLogger(CredentialConfigService.class.getName()).log(Level.SEVERE,
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

		configJSON = new JSONObject(sb.toString());

	}

	public String getConfig(String key) {
		return configJSON.getString(key);
	}

	public void setConfig(String key, String value) {
		configJSON.append(key, value);
	}

	public void saveConfigToFile() {
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(configJSON.toString());
			writer.flush();
			writer.close();
		} catch (IOException ex) {
			Logger.getLogger(CredentialConfigService.class.getName()).log(Level.SEVERE,
					"Could not save Config to File.", ex);
		}
	}

}
