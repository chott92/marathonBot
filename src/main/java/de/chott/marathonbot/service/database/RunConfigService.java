package de.chott.marathonbot.service.database;

import de.chott.marathonbot.model.database.RunConfig;
import de.chott.marathonbot.service.SingletonService;
import de.chott.marathonbot.service.SingletonServiceFactory;
import java.util.List;
import javax.persistence.EntityManager;

public class RunConfigService implements SingletonService {

	private EntityManager em;

	public RunConfigService() {
		em = SingletonServiceFactory.getInstance(DatabaseService.class).getEntityManager();
	}

	public List<RunConfig> loadAll() {
		return em.createNamedQuery(RunConfig.FIND_ALL, RunConfig.class)
				.getResultList();
	}

	public RunConfig save(RunConfig rc) {
		return em.merge(rc);
	}

}
