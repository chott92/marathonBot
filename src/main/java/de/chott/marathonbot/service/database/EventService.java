package de.chott.marathonbot.service.database;

import de.chott.marathonbot.model.database.Event;
import de.chott.marathonbot.service.SingletonService;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;

public class EventService implements SingletonService {

	private EntityManager em;
	private DatabaseService databaseService;

	public EventService(EntityManager em, DatabaseService databaseService) {
		this.em = em;
		this.databaseService = databaseService;
	}

	public List<String> loadEventNames() {
		return em.createNamedQuery(Event.FIND_ALL, Event.class)
				.getResultList()
				.stream()
				.map(Event::getName)
				.collect(Collectors.toList());
	}

	public Event findOrCreateByName(String name) {
		List<Event> resultList = em.createNamedQuery(Event.FIND_BY_NAME, Event.class)
				.setParameter("paramName", name)
				.getResultList();

		if (!resultList.isEmpty()) {
			return resultList.iterator().next();
		} else {
			return save(new Event(name));
		}
	}

	public Event save(Event e) {
		return databaseService.merge(e);
	}
}
