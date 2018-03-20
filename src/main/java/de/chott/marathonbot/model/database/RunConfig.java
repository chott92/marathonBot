package de.chott.marathonbot.model.database;

import de.chott.marathonbot.model.ui.RunConfigTableEntry;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(name = RunConfig.FIND_ALL, query = "SELECT rc FROM RunConfic rc")
})
public class RunConfig implements Serializable {

	private static final long serialVersionUID = 273490827349082374L;

	public static final String FIND_ALL = "RunConfig.findAll";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String game;
	private String runnerName;
	private String wrTime;
	private String runnerPB;
	private String wrHolderName;
	private String category;
	private String speedrunComLink;

	public RunConfig() {
	}

	public RunConfig(String game, String runnerName, String wrTime, String runnerPB, String wrHolderName, String category, String speedrunComLink) {
		this.game = game;
		this.runnerName = runnerName;
		this.wrTime = wrTime;
		this.runnerPB = runnerPB;
		this.wrHolderName = wrHolderName;
		this.category = category;
		this.speedrunComLink = speedrunComLink;
	}

	public RunConfig(RunConfigTableEntry entry) {
		this.game = entry.getGame();
		this.runnerName = entry.getRunnerName();
		this.wrTime = entry.getWrTime();
		this.wrHolderName = entry.getWrHolderName();
		this.runnerPB = entry.getRunnerPB();
		this.category = entry.getCategory();
		this.speedrunComLink = entry.getSpeedrunComLink();
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getGame() {
		return game;
	}

	public String getRunnerName() {
		return runnerName;
	}

	public String getWrTime() {
		return wrTime;
	}

	public String getRunnerPB() {
		return runnerPB;
	}

	public String getWrHolderName() {
		return wrHolderName;
	}

	public String getCategory() {
		return category;
	}

	public String getSpeedrunComLink() {
		return speedrunComLink;
	}

}
