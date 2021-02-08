package by.epam.training.task1.dao;

import java.util.List;

import by.epam.training.task1.bean.Book;

public interface LibraryDao {

	/**
	 * Создает новый объект в хранилище данных и возвращает присвоенный объекту id.
	 * 
	 * @param treasure - данные для записи.
	 * @return 0 - в случае ошибки создания новой записи.
	 */
	int create(Book book);

	/**
	 * Возвращает объект из хранилища по его id.
	 * 
	 * @param id
	 * @return
	 */
	Book read(int id);

	/**
	 * Обновляет запись об объекте в хранилище.
	 * 
	 * @param treasure
	 * @return false в случае ошибки обновления.
	 */
	boolean update(Book book);

	/**
	 * Удаляет запись из хранилища.
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(int id);
	
	/**
	 * Возвращает список всех объектов хранилища.
	 * 
	 * @return
	 */
	List<Book> getAll();
	
}
