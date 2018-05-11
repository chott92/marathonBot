/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.marathonbot.service.data;

import de.chott.marathonbot.model.database.Event;
import de.chott.marathonbot.model.ui.RunConfigTableEntry;
import de.chott.marathonbot.service.SingletonService;
import de.chott.marathonbot.service.SingletonServiceFactory;
import de.chott.marathonbot.service.config.ConfigService;
import de.chott.marathonbot.util.config.ConfigConstants;
import java.util.Optional;

public class DashboardDataService implements SingletonService {

	private RunConfigTableEntry currentEntry;
	private RunConfigTableEntry nextEntry;

	private Event event;

	private ConfigService configService;

	public DashboardDataService() {
		configService = SingletonServiceFactory.getInstance(ConfigService.class);

		currentEntry = SingletonServiceFactory.getInstance(RunConfigDataService.class)
				.getForString(configService.getConfig(ConfigConstants.LAST_GAME_NAME).orElse("")).orElse(null);
	}

	public RunConfigTableEntry getCurrentEntry() {
		return currentEntry;
	}

	public void setCurrentEntry(RunConfigTableEntry currentEntry) {
		this.currentEntry = currentEntry;
	}

	public RunConfigTableEntry getNextEntry() {
		return nextEntry;
	}

	public void setNextEntry(RunConfigTableEntry nextEntry) {
		this.nextEntry = nextEntry;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Event getEvent() {
		return event;
	}

	@Override
	public void close() {
		if (Optional.ofNullable(currentEntry).isPresent()) {
			configService.setConfig(ConfigConstants.LAST_GAME_NAME, currentEntry.toString());
		}
	}

}
