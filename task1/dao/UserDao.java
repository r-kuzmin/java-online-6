package by.epam.training.task1.dao;

import java.io.IOException;
import java.util.List;

import by.epam.training.task1.bean.User;

public interface UserDao {

	/**
	 * Создает новый объект в хранилище данных и возвращает присвоенный объекту id.
	 * 
	 * @param user - данные для записи.
	 * @return 0 - в случае ошибки формирования нового id.  
	 * @throws IOException
	 */
	int create(User user) throws IOException;

	/**
	 * Возвращает объект из хранилища по его id.
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 */
	User read(int id) throws IOException;

	/**
	 * Обновляет запись об объекте в хранилище.
	 * 
	 * @param user 
	 * @return true, если пользователь был найден и успешно обновлён; false, если
	 *         пользователь не найден.
	 * @throws IOException 
	 */
	boolean update(User user) throws IOException;

	/**
	 * Удаляет запись из хранилища.
	 * 
	 * @param id
	 * @return true, если пользователь был найден и успешно удалён; false, если
	 *         пользователь не найден.
	 * @throws IOException
	 */
	boolean delete(int id) throws IOException;

	/**
	 * Возвращает список всех объектов хранилища.
	 * 
	 * @return
	 */
	List<User> getAll();
	
}
