package de.chott.marathonbot.service.data;

import de.chott.marathonbot.model.database.RunConfig;
import de.chott.marathonbot.model.ui.RunConfigTableEntry;
import de.chott.marathonbot.service.SingletonService;
import de.chott.marathonbot.service.SingletonServiceFactory;
import de.chott.marathonbot.service.database.RunConfigService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RunConfigDataService implements SingletonService {

	private final ObservableList<RunConfigTableEntry> data;
	private RunConfigService runConfigService;

	public RunConfigDataService() {
		runConfigService = SingletonServiceFactory.getInstance(RunConfigService.class);
		data = FXCollections.observableArrayList();
		loadData();
	}

	private void loadData() {

		runConfigService.loadAll().forEach(rc -> data.add(new RunConfigTableEntry(rc)));

		if (data.isEmpty()) {
			//intentional Mockup for testing;
			data.add(new RunConfigTableEntry("Donkey Kong Country Returns", "MrTiger92, Lukas1337",
					"1:29:39", "1:34:29, 1:34:30", "JXD", "Any%", "speedrun.com/dkcr"));
			data.add(new RunConfigTableEntry("Titan Quest", "MrTiger92", "1:18:22", "1:18:22",
					"MrTiger92", "Any% (AE)", "speedrun.com/Titan_Quest"));
			data.add(new RunConfigTableEntry("Titan Quest", "MrTiger92", "4:20:02", "4:20:02",
					"MrTiger92", "Any% legendary", "speedrun.com/Titan_Quest"));
			data.add(new RunConfigTableEntry("Titan Quest", "MrTiger92", "2:08:01", "2:08:01",
					"MrTiger92", "Any% (IT)", "speedrun.com/Titan_Quest"));
		}

	}

	@Override
	public void close() {
		data.forEach(entry -> runConfigService.save(new RunConfig(entry)));
	}

	public ObservableList<RunConfigTableEntry> getData() {
		return data;
	}

}
