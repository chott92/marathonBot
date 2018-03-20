/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose
 * Tools | Templates and open the template in the editor.
 */
package de.chott.marathonbot.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SingletonServiceFactory {

	private static final Map<Class, SingletonService> INSTANCES = new HashMap<>();

	public static <T extends SingletonService> T getInstance(Class<T> clazz) {
		if (INSTANCES.get(clazz) == null) {

			try {
				T service = clazz.newInstance();
				INSTANCES.put(clazz, service);
				return service;

			} catch (InstantiationException | IllegalAccessException ex) {
				Logger.getLogger(SingletonServiceFactory.class.getName()).log(Level.SEVERE, null, ex);
				return null;
			}

		} else {
			return (T) INSTANCES.get(clazz);
		}
	}

	public static void closeServices() {
		INSTANCES.values()
				.stream()
				.sorted(Comparator.comparing(instance -> instance.getClosingIndex()))
				.forEach(SingletonService::close);
	}

}
