package by.epam.training.task1.logic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import by.epam.training.task1.bean.Book;
import by.epam.training.task1.bean.Person;
import by.epam.training.task1.bean.User;
import by.epam.training.task1.dao.LibraryDao;
import by.epam.training.task1.dao.UserDao;
import by.epam.training.task1.dao.XmlLibraryDao;
import by.epam.training.task1.dao.XmlUserDao;

public class Library {

	private LibraryDao libraryDao;
	private UserDao userDao;
	
	// Текущий авторизванный пользователь.
	private User user;

	/**
	 * Подключение к БД библиотеки.
	 * 
	 * @param path
	 * @throws LibraryException
	 */
	public Library(String path) throws LibraryException {

		try {
			userDao = new XmlUserDao(path + File.separator + "users.xml");
		} catch (IOException e) {
			throw new LibraryException("Ошибка при чтении файла users.xml", e);
		}

		try {
			libraryDao = new XmlLibraryDao(path + File.separator + "library.xml");
		} catch (IOException e) {
			throw new LibraryException("Ошибка при чтении файла library.xml", e);
		}
	}

	/**
	 * Авторизация пользователя библиотеки.
	 * 
	 * @param login
	 * @param password
	 * @return
	 */
	public boolean setUser(String login, String password) {

		for (User user : userDao.getAll()) {
			if (user.getLogin().equals(login) && user.checkPassword(password)) {
				this.user = user;
				return true;
			}
		}

		return false;
	}

	/**
	 * Текущий авторизованный пользователь. Получить текущего пользователя может
	 * толко администратор.
	 * 
	 * @return
	 */
	public User getUser() {
		return this.user;
	}

	/**
	 * Список пользователей может получить только администратор или когда текущий
	 * пользователь null, т. е. не завершена авторизация.
	 * 
	 * @return Список всех пользователей библиотеки.
	 * @throws LibraryException 
	 */
	public List<User> getAllUsers() throws LibraryException {

		if (this.user == null || this.user.getRole() == 1) {
			return userDao.getAll();
		} else {
			throw new LibraryException("Недостаточно прав для чтения списка пользователей.");
		}

	}

	/**
	 * Находит пользователя по его id.
	 * 
	 * @param id
	 * @return
	 * @throws LibraryException
	 */
	public User getUserById(int id) throws LibraryException {

		if (this.user == null || this.user.getRole() != 1) {
			throw new LibraryException("Недостаточно прав для получения пользователя по id.");
		}
		
		try {
			return userDao.read(id);
		} catch (IOException e) {
			throw new LibraryException("Ошибка при чтении пользователя.", e);
		}

	}

	/**
	 * Создаёт нового пользователя библиотеки по переданному описанию. Если
	 * пользователь успешно создан, в переданном объекте будет установлен новый id.
	 * Создание пользователей возможно только администратором или когда список пользователей
	 * вообще пуст.
	 * 
	 * @param user
	 * @return
	 * @throws LibraryException
	 */
	public boolean createUser(User user) throws LibraryException {
		
		if (getAllUsers().size() != 0 && (this.user == null || this.user.getRole() != 1)) {
			throw new LibraryException("Недостаточно прав для создания нового пользователя.");
		}
		
		int id = 0;

		try {
			id = userDao.create(user);
		} catch (IOException e) {
			throw new LibraryException("Ошибка при записи нового пользователя.", e);
		}

		if (id == 0) {
			return false;
		}

		user.setId(id);

		return true;
	}

	/**
	 * Обновление данных о пользователе. Пользователи синхронизируются по id.
	 * Обновлять информацию о пользователях может только администратор.
	 * 
	 * @param user
	 * @return
	 * @throws LibraryException
	 */
	public boolean updateUser(User user) throws LibraryException {
		
		if (this.user == null || this.user.getRole() != 1) {
			throw new LibraryException("Недостаточно прав для изменения информации о пользователе.");
		}
		
		try {
			return userDao.update(user);
		} catch (IOException e) {
			throw new LibraryException("Ошибка при записи изменений пользователя.", e);
		}

	}

	/**
	 * Удаление пользователя библиотеки.
	 * 
	 * @param id
	 * @return
	 * @throws LibraryException
	 */
	public boolean deleteUser(int id) throws LibraryException {
		
		if (this.user == null || this.user.getRole() != 1) {
			throw new LibraryException("Недостаточно прав для удаления пользователя.");
		}
		
		try {
			return userDao.delete(id);
		} catch (IOException e) {
			throw new LibraryException("Ошибка при удалении пользователя.", e);
		}
		
	}

	/**
	 * Возвращает список всех книг, зарегистрированных в библиотеке.
	 * 
	 * @return
	 */
	public List<Book> getAllBooks() {
		return libraryDao.getAll();
	}

	/**
	 * Находит книгу по её id.
	 * 
	 * @param id
	 * @return
	 * @throws LibraryException
	 */
	public Book getBookById(int id) throws LibraryException {
		
		try {
			return libraryDao.read(id);
		} catch (IOException e) {
			throw new LibraryException("Ошибка при чтении книги.", e);
		}

	}

	/**
	 * Ищет по наименованию (части наименования) книги и возвращает их список.
	 * 
	 * @param name
	 * @return
	 */
	public List<Book> getBooksByName(String name) {

		List<Book> result = new ArrayList<Book>();

		for (Book book : getAllBooks()) {
			if (book.getName().contains(name)) {
				result.add(book);
			}
		}

		return result;
	}

	/**
	 * Ищет книги по имени автора.
	 * 
	 * @param author
	 * @return
	 */
	public List<Book> getBooksByAuthor(String author) {

		List<Book> result = new ArrayList<Book>();

		for (Book book : getAllBooks()) {
			for (Person person : book.getAuthors()) {
				if (person.getName().contains(author)) {
					result.add(book);
					break;
				}
			}
		}

		return result;
	}
	
	/**
	 * Добавлять книги в каталог могут только администраторы.
	 * 
	 * @param book
	 * @return
	 * @throws LibraryException
	 */
	public boolean createBook(Book book) throws LibraryException {
		
		if (this.user == null || this.user.getRole() != 1) {
			throw new LibraryException("Недостаточно прав для создания новой записи о книге.");
		}
		
		int id = 0;

		try {
			id = libraryDao.create(book);
		} catch (IOException e) {
			throw new LibraryException("Ошибка при записи новой книги.", e);
		}

		if (id == 0) {
			return false;
		}

		book.setId(id);

		return true;
	}
	
	/**
	 * Обновление данных о книге.
	 * 
	 * @param book
	 * @return
	 * @throws LibraryException
	 */
	public boolean updateBook(Book book) throws LibraryException {
		
		if (this.user == null || this.user.getRole() != 1) {
			throw new LibraryException("Недостаточно прав для изменения записи о книге.");
		}
		
		try {
			return libraryDao.update(book);
		} catch (IOException e) {
			throw new LibraryException("Ошибка при записи изменений книги.", e);
		}

	}
	
	/**
	 * Удаление книги из библиотеки.
	 * 
	 * @param id
	 * @return
	 * @throws LibraryException
	 */
	public boolean deleteBook(int id) throws LibraryException {
		
		if (this.user == null || this.user.getRole() != 1) {
			throw new LibraryException("Недостаточно прав для удаления записи о книге.");
		}
		
		try {
			return libraryDao.delete(id);
		} catch (IOException e) {
			throw new LibraryException("Ошибка при удалении книги.", e);
		}
		
	}
	
}
