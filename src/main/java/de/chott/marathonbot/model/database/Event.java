package de.chott.marathonbot.model.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(name = Event.FIND_BY_NAME, query = "SELECT e FROM Event e WHERE e.name = :paramName")
	, @NamedQuery(name = Event.FIND_ALL, query = "SELECT e FROM Event e")
})
public class Event extends AbstractEntity {

	public static final String FIND_BY_NAME = "Event.findByName";
	public static final String FIND_ALL = "Event.findAll";

	@Column(unique = true)
	private String name;

	public Event() {
	}

	public Event(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
