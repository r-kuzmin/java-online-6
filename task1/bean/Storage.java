package by.epam.training.task1.bean;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Физическое место хранения книг - помещение, шкаф, полка.
 * 
 * @author R. Kuzmin
 *
 */
public class Storage implements Serializable {
	
	private static final long serialVersionUID = -3778558251366508795L;
	
	private String name;
	private Storage parent;
	private Storage[] childs;
	
	public Storage() {
		this("", null);
	}

	public Storage(String name, Storage parent) {
		super();
		this.name = name;
		this.parent = parent;
		this.childs = new Storage[] {};
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Storage getParent() {
		return parent;
	}

	public void setParent(Storage parent) {
		this.parent = parent;
	}

	public Storage[] getChilds() {
		return childs;
	}

	public void setChilds(Storage[] childs) {
		this.childs = childs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(childs);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
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
		Storage other = (Storage) obj;
		if (!Arrays.equals(childs, other.childs))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Storage [name=" + name + ", parent=" + parent.toString() + ", childs=" + Arrays.toString(childs) + "]";
	}
		
}
