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

import by.epam.training.task1.bean.User;

public class XmlUserDao implements UserDao {

	// Путь к файлу со списоком пользователей.
	private String path;
	
	// Кэш пользователей. Хранит копии объектов.
	private Set<User> cache;
	
	public XmlUserDao(String path) throws IOException {
		
		this.path = path;
		this.cache = new HashSet<User>();
		
		File file = new File(path);
		
		if (!file.exists()) {
			file.createNewFile();
			writeFile();
		}
		
		readFile();
	}
	
	@Override
	public int create(User user) throws IOException {
		
		int id = getNextId();
		
		if (id == 0) {
			// Закончилось свободные id.
			return 0;
		}
		
		// Копия объекта для кэша.
		User image = user.clone();
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
	public User read(int id) {
		
		for (User image : cache) {
			if (image.getId() == id) {
				return image.clone();
			}
		}
		
		return null;
	}
	
	@Override
	public boolean update(User user) throws IOException {
		
		for (User image : cache) {
			if (image.getId() == user.getId()) {
				
				// Копия данных для восстановления в случае ошибки.
				User temp = image.clone();
				
				// Изменяем данные в кеше и пробуем сбросить на диск.
				image.setName(user.getName());
				image.setId(user.getId());
				image.setLogin(user.getLogin());
				image.setHash(user.getHash());
				image.setSalt(user.getSalt());
				image.setEmail(user.getEmail());
				
				try {
					writeFile();
					return true;
				} catch (FileNotFoundException e) {
					// Восстанавливаем кэш и сообщаем об ошибке.
					image.setName(temp.getName());
					image.setId(temp.getId());
					image.setLogin(temp.getLogin());
					image.setHash(temp.getHash());
					image.setSalt(temp.getSalt());
					image.setEmail(temp.getEmail());
					throw e;
				}
			}
		}
		
		return false;
	}

	@Override
	public boolean delete(int id) throws IOException {
		
		for (User user : cache) {
			if (user.getId() == id && cache.remove(user)) {
				try {
					writeFile();
					return true;
				} catch (FileNotFoundException e) {
					cache.add(user);
					throw new IOException(e);
				}
			}
		}
		
		return false;
	}

	@Override
	public List<User> getAll() {
		
		return new ArrayList<>(cache);
		
	}

	@SuppressWarnings("unchecked")
	private void readFile() throws FileNotFoundException {
		
		FileInputStream fileStream = new FileInputStream(path);
		BufferedInputStream bufStream = new BufferedInputStream(fileStream); 
		XMLDecoder decoder = new XMLDecoder(bufStream);
		
		this.cache = (Set<User>) decoder.readObject();
		
		decoder.close();
	}
	
	private void writeFile() throws FileNotFoundException {
		
		FileOutputStream fileStream = new FileOutputStream(path);
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
