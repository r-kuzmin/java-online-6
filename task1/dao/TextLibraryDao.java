package by.epam.training.task1.dao;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import by.epam.training.task1.bean.Book;

public class TextLibraryDao implements LibraryDao {

	private String path;
	private Set<Book> cache;

	public TextLibraryDao(String path) throws IOException {
		
		this.path = path;
		this.cache = new HashSet<>();
		
		// Проверяем существование файла с данными.
		File file = new File(path);
		if (!file.exists()) {
			// Если файла не существует, создаём новый пустой.
			file.createNewFile();
			writeFile();
		}
		
		readFile();
	}

	@Override
	public int create(Book book) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Book read(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Book book) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Book> getAll() {
		return new ArrayList<>(cache);
	}

	@SuppressWarnings("unchecked")
	private void readFile() throws FileNotFoundException {
		
		FileInputStream fileStream = new FileInputStream(this.path);
		BufferedInputStream bufStream = new BufferedInputStream(fileStream); 
		XMLDecoder decoder = new XMLDecoder(bufStream);
		
		this.cache = (Set<Book>) decoder.readObject();
		
		decoder.close();
		
	}
	
	private void writeFile() throws FileNotFoundException {
		
		FileOutputStream fileStream = new FileOutputStream(this.path);
		BufferedOutputStream bufStream = new BufferedOutputStream(fileStream);
		XMLEncoder encoder = new XMLEncoder(bufStream);
		
		encoder.writeObject(cache);
		
		encoder.close();
		
	}
	
	private int getNextId() {

		int id = 0;

		if (cache.size() == 999_999) {
			return id;
		}

		do {
			id = (int) (Math.random() * 1_000_000);
		} while (read(id) != null);

		return id;
	}
	
}
