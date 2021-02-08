package by.epam.training.task1.logic;

import java.io.IOException;
import java.util.List;

import by.epam.training.task1.bean.Book;
import by.epam.training.task1.dao.LibraryDao;
import by.epam.training.task1.dao.TextLibraryDao;

public class Library {

	private LibraryDao dao;
	
	public Library(String path) throws IOException {
		this.dao = new TextLibraryDao(path);
	}
	
	public List<Book> getAll() {
		return dao.getAll();
	}
}
