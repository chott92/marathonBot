package de.chott.marathonbot.service.database;

import de.chott.marathonbot.model.database.AbstractEntity;
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

	public <T extends AbstractEntity> T merge(T t) {
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction()
				.begin();

		t = em.merge(t);

		em.getTransaction()
				.commit();

		em.close();

		return t;
	}

	public <T extends AbstractEntity> void remove(T t) {
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction()
				.begin();

		em.remove(em.find(t.getClass(), t.getId()));

		em.getTransaction()
				.commit();

		em.close();

	}

	@Override
	public void close() {
		entityManager.close();
		entityManagerFactory.close();
	}

	@Override
	public int getClosingIndex() {
		return 1000;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

}
