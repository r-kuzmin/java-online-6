package by.epam.training.task1.bean;

public class ElectronicBook extends Book {

	private static final long serialVersionUID = -8265742112704453223L;

	private String path;
	
	public ElectronicBook() {
		this("");
	}

	public ElectronicBook(String path) {
		super();
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ElectronicBook other = (ElectronicBook) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ElectronicBook [path=" + path + ", book=" + super.toString() + "]";
	}
		
}
