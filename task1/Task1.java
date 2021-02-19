package by.epam.training.task1;

import java.util.ArrayList;
import java.util.List;

import by.epam.training.task1.bean.Book;
import by.epam.training.task1.bean.ElectronicBook;
import by.epam.training.task1.bean.FisicalBook;
import by.epam.training.task1.bean.Person;
import by.epam.training.task1.bean.User;
import by.epam.training.task1.logic.Library;
import by.epam.training.task1.logic.LibraryException;
import by.epam.training.task1.view.Console;

/**
 * Учет книг в домашней библиотеке.
 * 
 * @author R. Kuzmin
 *
 */
public class Task1 {

	public static void main(String[] args) {

		Console ui = new Console();

		Library library;

		try {
			library = new Library(System.getProperty("user.dir"));
		} catch (LibraryException e) {
			ui.print(e.getMessage());
			return;
		}

		if (!authorize(library, ui)) {
			return;
		}

		ui.showMenu(library.getUser().getRole());

		int action = 0;

		do {
			action = ui.getInteger("Действие >>");

			switch (action) {
			case 1:
				ui.printBooks(library.getAllBooks(), null);
				break;

			case 2:
				String name = ui.getString("Введите название книги или часть названия >>");
				ui.printBooks(library.getBooksByName(name), null);
				break;

			case 3:
				String author = ui.getString("Введите имя (часть имени) автора книги >>");
				ui.printBooks(library.getBooksByAuthor(author), null);
				break;

			case 4:
				addBook(library, ui);
				break;

			case 5:
				updateBook(library, ui);
				break;

			case 6:
				deleteBook(library, ui);
				break;

			case 11:
				if (library.getUser().getRole() == 1) {
					try {
						ui.printUsers(library.getAllUsers(), "Зарегистрированные пользователи:");
					} catch (LibraryException e) {
						ui.print(e.getMessage());
					}
				}
				break;

			case 12:
				addUser(library, ui);
				break;

			case 13:
				updateUser(library, ui);
				break;

			case 14:
				deleteUser(library, ui);
				break;

			default:
				break;
			}
		} while (action != 0);

		ui.print("Завершение работы с библиотекой.");

	}

	private static boolean authorize(Library library, Console ui) {

		String login;
		String password;

		List<User> users = null;

		try {
			users = library.getAllUsers();
		} catch (LibraryException e) {
			ui.print(e.getMessage());
		}

		if (users.size() == 0) {
			login = "Admin";
			password = ui.getString("Введите пароль для пользователя Admin >>");

			while (!password.matches("(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}")) {
				System.out.println(
						"Пароль должен быть длиннее 6 знаков, включать символы в разном регистре, цифры и спецсимволы.");
				password = ui.getString("Введите пароль для пользователя Admin >>");
			}

			User user = new User(login, login, password, 1);

			try {
				library.createUser(user);
			} catch (LibraryException e) {
				ui.print(e.getMessage());
				return false;
			}

		} else {
			login = ui.getString("Введите логин >>");
			password = ui.getString("Введите пароль >>");
		}

		if (!library.setUser(login, password)) {
			ui.print("\nОшибка авторизации пользователя " + login);
			return false;
		} else {
			ui.print("\nЗдравствуйте, " + library.getUser().getName());
		}

		return true;
	}

	private static void addUser(Library library, Console ui) {

		if (library.getUser().getRole() != 1) {
			return;
		}

		User user = new User();

		user.setLogin(ui.getString("Введите логин >>"));
		user.setName(ui.getString("Введите отображаемое имя >>"));
		user.setPassword(ui.getString("Введите пароль >>"));
		user.setEmail(ui.getString("Введите e-mail >>", User.EMAIL_REGEX));
		user.setRole(ui.getInteger("Администратор (0 - нет, 1 - да) >>"));

		try {
			if (library.createUser(user)) {
				ui.print("Новый пользователь зарегистрирован.");
			} else {
				ui.print("Не удалось зарегистрировать нового пользователя.");
			}
		} catch (LibraryException e) {
			ui.print(e.getMessage());
		}

	}

	private static void updateUser(Library library, Console ui) {

		if (library.getUser().getRole() != 1) {
			return;
		}

		int id = ui.getInteger("Введите ID редактируемого пользователя >>");

		User user;

		try {
			user = library.getUserById(id);
		} catch (LibraryException e) {
			ui.print(e.getMessage());
			return;
		}

		if (user == null) {
			ui.print("Пользователь с id=" + id + " не найден.");
		} else {
			user.setLogin(ui.getString("Введите новый логин >>"));
			user.setName(ui.getString("Введите отображаемое имя >>"));
			user.setPassword(ui.getString("Введите новый пароль >>"));
			user.setEmail(ui.getString("Введите новый e-mail >>", User.EMAIL_REGEX));
			user.setRole(ui.getInteger("Администратор (0 - нет, 1 - да) >>"));

			try {
				if (library.updateUser(user)) {
					ui.print("Данные успешно обновлены.");
				} else {
					ui.print("Данные не изменены.");
				}
			} catch (LibraryException e) {
				ui.print(e.getMessage());
			}
		}
	}

	private static void deleteUser(Library library, Console ui) {

		if (library.getUser().getRole() != 1) {
			return;
		}

		int id = ui.getInteger("Введите id удаляемого пользователя >>");

		try {
			if (library.deleteUser(id)) {
				ui.print("Пользователь успешно удалён.");
			} else {
				ui.print("Не найден пользователь с указанным id.");
			}
		} catch (LibraryException e) {
			ui.print(e.getMessage());
		}

	}

	private static void addBook(Library library, Console ui) {

		if (library.getUser().getRole() != 1) {
			return;
		}

		Book book;

		int type = ui.getInteger("Введите вид книги (0 - бумажная, 1 - электронная) >>");

		if (type == 0) {
			book = new FisicalBook();
		} else if (type == 1) {
			book = new ElectronicBook();
		} else {
			ui.print("Неизвестный тип книг.");
			return;
		}

		book.setName(ui.getString("Введите название книги >>"));

		List<Person> authors = new ArrayList<>();
		int nextAuthor = 0;

		do {
			String author = ui.getString("Введите автора книги >>");
			authors.add(new Person(author));
			nextAuthor = ui.getInteger("Добавить ещё одного автора (0 - нет, 1 - да) >>");
		} while (nextAuthor == 1);

		book.setAuthors(authors.toArray(new Person[authors.size()]));

		try {
			if (library.createBook(book)) {
				ui.print("Новая книга зарегистрирована.");
			} else {
				ui.print("Не удалось зарегистрировать новую книгу.");
			}
		} catch (LibraryException e) {
			ui.print(e.getMessage());
		}

	}

	private static void updateBook(Library library, Console ui) {

		if (library.getUser().getRole() != 1) {
			return;
		}

		int id = ui.getInteger("Введите ID редактируемой книги >>");

		Book book;

		try {
			book = library.getBookById(id);
		} catch (LibraryException e) {
			ui.print(e.getMessage());
			return;
		}

		if (book == null) {
			ui.print("Книга с id=" + id + " не найдена.");
		} else {
			book.setName(ui.getString("Введите новое название >>"));

			try {
				if (library.updateBook(book)) {
					ui.print("Данные успешно обновлены.");
				} else {
					ui.print("Данные не изменены.");
				}
			} catch (LibraryException e) {
				ui.print(e.getMessage());
			}
		}
	}

	private static void deleteBook(Library library, Console ui) {

		if (library.getUser().getRole() != 1) {
			return;
		}

		int id = ui.getInteger("Введите id удаляемой книги >>");

		try {
			if (library.deleteUser(id)) {
				ui.print("Книга успешно удалена.");
			} else {
				ui.print("Не найдена книга с указанным id.");
			}
		} catch (LibraryException e) {
			ui.print(e.getMessage());
		}

	}

}
