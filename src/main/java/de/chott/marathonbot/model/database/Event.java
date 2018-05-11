package de.chott.marathonbot.model.database;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;

@Entity
@NamedQueries({})
public class Event extends AbstractEntity {

	public static final String FIND_BY_NAME = "Event.findByName";

	private String name;
}
