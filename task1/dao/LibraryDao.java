package by.epam.training.task1.dao;

import java.io.IOException;
import java.util.List;

import by.epam.training.task1.bean.Book;

public interface LibraryDao {

	/**
	 * Создает новый объект в хранилище данных и возвращает присвоенный объекту id.
	 * 
	 * @param book - данные для записи.
	 * @return 0 - в случае ошибки создания новой записи.
	 * @throws IOException
	 */
	int create(Book book) throws IOException;

	/**
	 * Возвращает объект из хранилища по его id.
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 */
	Book read(int id) throws IOException;

	/**
	 * Обновляет запись об объекте в хранилище.
	 * 
	 * @param book
	 * @return false в случае ошибки обновления.
	 * @throws IOException
	 */
	boolean update(Book book) throws IOException;

	/**
	 * Удаляет запись из хранилища.
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 */
	boolean delete(int id) throws IOException;
	
	/**
	 * Возвращает список всех объектов хранилища.
	 * 
	 * @return
	 */
	List<Book> getAll();
	
}
