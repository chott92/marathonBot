package de.chott.marathonbot.model.database;

import de.chott.marathonbot.model.ui.RunConfigTableEntry;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
	@NamedQuery(name = RunConfig.FIND_ALL, query = "SELECT rc FROM RunConfig rc")
})
@Table(name = "run_config")
public class RunConfig extends AbstractEntity {

	private static final long serialVersionUID = 273490827349082374L;

	public static final String FIND_ALL = "RunConfig.findAll";

	@Column(name = "game")
	private String game;
	@Column(name = "runner_name")
	private String runnerName;
	@Column(name = "wr_time")
	private String wrTime;
	@Column(name = "runner_pb")
	private String runnerPB;
	@Column(name = "wr_holder_name")
	private String wrHolderName;
	@Column(name = "category")
	private String category;
	@Column(name = "speedrun_com_link")
	private String speedrunComLink;

	public RunConfig() {
	}

	public RunConfig(String game, String runnerName, String wrTime, String runnerPB,
			String wrHolderName, String category, String speedrunComLink) {
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

		entry.getId().ifPresent(entryId -> setId(entryId));
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
