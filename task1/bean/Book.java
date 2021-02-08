package by.epam.training.task1.bean;

import java.io.Serializable;
import java.util.Arrays;

public abstract class Book implements Serializable {
	
	private static final long serialVersionUID = -4609390115615499866L;
	
	private int id;
	private String name;
	private Person[] authors;
	
	public Book() {
		this(0, "", null);
	}

	public Book(int id, String name, Person[] authors) {
		super();
		this.id = id;
		this.name = name;
		this.authors = authors;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Person[] getAuthors() {
		return authors;
	}

	public void setAuthors(Person[] authors) {
		this.authors = authors;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(authors);
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (!Arrays.equals(authors, other.authors))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", authors=" + Arrays.toString(authors) + "]";
	}
	
}
