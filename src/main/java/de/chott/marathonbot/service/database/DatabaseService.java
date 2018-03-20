package de.chott.marathonbot.service.database;

import de.chott.marathonbot.service.SingletonService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseService implements SingletonService {

	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;

	public DatabaseService() {
		entityManagerFactory = Persistence.createEntityManagerFactory("marathonBot");
		entityManager = entityManagerFactory.createEntityManager();
	}

	@Override
	public void close() {
		entityManager.close();
		entityManagerFactory.close();
	}

}
