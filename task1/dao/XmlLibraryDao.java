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
import by.epam.training.task1.bean.ElectronicBook;
import by.epam.training.task1.bean.FisicalBook;
import by.epam.training.task1.bean.Storage;

public class XmlLibraryDao implements LibraryDao {

	// Путь к файлу библиотеки.
	private String path;
	
	// Кэш. Хранит копии объектов БД.
	private Set<Book> cache;

	public XmlLibraryDao(String path) throws IOException {
		
		this.path = path;
		this.cache = new HashSet<>();
		
		File file = new File(path);
		
		if (!file.exists()) {
			file.createNewFile();
			writeFile();
		}
		
		readFile();
	}

	@Override
	public int create(Book book) throws IOException {

		int id = getNextId();
		
		if (id == 0) {
			// Закончилось свободные id.
			return 0;
		}

		// Копия объекта для кэша.
		Book image;
		
		if (book instanceof FisicalBook) {
			image = ((FisicalBook) book).clone();
		} else {
			image = ((ElectronicBook) book).clone();
		}
		
		image.setId(id);

		if (cache.add(image)) {
			try {
				writeFile();
			} catch (IOException e) {
				// Восстанавливаем кэш.
				cache.remove(image);
				throw new IOException(e);
			}
		} else {
			return 0;
		}

		return id;
	}

	@Override
	public Book read(int id) {

		for (Book image : cache) {
			if (image.getId() == id) {
				if (image instanceof FisicalBook) {
					return ((FisicalBook) image).clone();
				} else {
					return ((ElectronicBook) image).clone();
				}
			}
		}
		
		return null;
	}

	@Override
	public boolean update(Book book) throws IOException {
		
		for (Book image : cache) {
			if (image.getId() == book.getId()) {
				
				// Копия данных для восстановления в случае ошибки.
				Book temp;
				
				if (image instanceof FisicalBook) {
					temp = ((FisicalBook) image).clone();
				} else {
					temp = ((ElectronicBook) image).clone();
				}
				
				// Изменяем данные в кеше и пробуем сбросить на диск.
				image.setId(book.getId());
				image.setName(book.getName());
				image.setAuthors(book.getAuthors());
				
				if (image instanceof FisicalBook) {
					Storage str = ((FisicalBook) book).getStorage();
					((FisicalBook) image).setStorage(str);
				} else {
					String path = ((ElectronicBook) book).getPath();
					((ElectronicBook) image).setPath(path);
				}
				
				try {
					writeFile();
					return true;
				} catch (FileNotFoundException e) {
					// Восстанавливаем кэш и сообщаем об ошибке.
					image.setId(temp.getId());
					image.setName(temp.getName());
					image.setAuthors(temp.getAuthors());
					
					if (image instanceof FisicalBook) {
						Storage str = ((FisicalBook) temp).getStorage();
						((FisicalBook) image).setStorage(str);
					} else {
						String path = ((ElectronicBook) temp).getPath();
						((ElectronicBook) image).setPath(path);
					}
					throw e;
				}
			}
		}
		
		return false;
	}

	@Override
	public boolean delete(int id) throws IOException {
		
		for (Book book : cache) {
			if (book.getId() == id && cache.remove(book)) {
				try {
					writeFile();
					return true;
				} catch (FileNotFoundException e) {
					cache.add(book);
					throw new IOException(e);
				}
			}
		}
		
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
