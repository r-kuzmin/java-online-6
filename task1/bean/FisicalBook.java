package by.epam.training.task1.bean;

public class FisicalBook extends Book {

	private static final long serialVersionUID = -4586658313615566748L;

	private Storage storage;
	
	public FisicalBook() {
		this(null);
	}

	public FisicalBook(Storage storage) {
		super();
		this.storage = storage;
	}
	
	@Override
	public FisicalBook clone() {
		
		FisicalBook image = new FisicalBook(storage);
		
		image.setId(getId());
		image.setName(getName());
		image.setAuthors(getAuthors());
		
		return image;
	}
	
	public Storage getStorage() {
		return storage;
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((storage == null) ? 0 : storage.hashCode());
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
		FisicalBook other = (FisicalBook) obj;
		if (storage == null) {
			if (other.storage != null)
				return false;
		} else if (!storage.equals(other.storage))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FisicalBook [storage=" + storage + ", book=" + super.toString() + "]";
	}
	
}
