package sk.kapusta.entity;

import java.util.Collection;

public class CollectionOfElements {

	private Collection<?> elements;

	protected CollectionOfElements() {
		
	}

	public CollectionOfElements(Collection<?> elements) {
		super();
		this.elements = elements;
	}

	public Collection<?> getElements() {
		return elements;
	}
}
