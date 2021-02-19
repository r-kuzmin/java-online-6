package by.epam.training.task1.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import by.epam.training.task1.bean.Book;
import by.epam.training.task1.bean.Person;
import by.epam.training.task1.bean.User;

public class Console {

	// Количество строк на странице консоли.
	private static final int PAGE_SIZE = 5;

	public void showMenu(int role) {

		System.out.println("1. Просмотр каталога.");
		System.out.println("2. Поиск по наименованию.");
		System.out.println("3. Поиск по автору.");

		if (role == 1) {
			System.out.println("4. Добавить книгу.");
			System.out.println("5. Изменить книгу.");
			System.out.println("6. Удалить книгу.");
			System.out.println("------------------------");
			System.out.println("11. Список пользователей.");
			System.out.println("12. Добавить пользователя.");
			System.out.println("13. Изменить пользователя.");
			System.out.println("14. Удалить пользователя.");
		}

		System.out.println("------------------------");
		System.out.println("0. Выход.");
		System.out.println();
	}

	public String getString(String title) {

		System.out.print(title);
		
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);

		return sc.nextLine();
	}

	public String getString(String title, String pattern) {

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);

		System.out.print(title);
		while (!sc.hasNext(pattern)) {
			sc.nextLine();
			System.out.println();
			System.out.print(title);
		};

		return sc.nextLine();
	}

	public int getInteger(String title) {

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);

		System.out.print(title);
		while (!sc.hasNextInt()) {
			sc.next();
			System.out.println();
			System.out.print(title);
		};

		return sc.nextInt();
	}

	public void print(String str) {
		System.out.println(str);
	}

	public void printItem(User user) {
		System.out.println("User:");
		System.out.println("\t(" + user.getId() + ") " + user.getName());
	}

	public void printItem(Book book) {
		System.out.println("Book:");
		System.out.println("\t(" + book.getId() + ") " + book.getName() + " - " + autorsToString(book));
	}
	
	public void printUsers(List<User> list, String title) {

		if (title != null && !title.isBlank()) {
			System.out.println(title);
		}

		List<String> strings = new ArrayList<>();

		for (User user : list) {
			strings.add("\t(" + user.getId() + ") " + user.getName() + ", login:" + user.getLogin() + ", e-mail:"
					+ user.getEmail());
		}

		printList(strings);

	}

	public void printBooks(List<Book> list, String title) {

		if (title != null && !title.isBlank()) {
			System.out.println(title);
		}

		List<String> strings = new ArrayList<>();

		for (Book book : list) {
			strings.add("\t(" + book.getId() + ") " + book.getName() + " - " + autorsToString(book));
		}

		printList(strings);
	}

	private String autorsToString(Book book) {

		if (book == null) {
			return "";
		}

		Person[] bookAutors = book.getAuthors();
		String[] authors = new String[bookAutors.length];

		for (int i = 0; i < bookAutors.length; i++) {
			authors[i] = bookAutors[i].getName();
		}

		return String.join(", ", authors);
	}

	private void printList(List<String> list) {

		int all = list.size();
		int size = Console.PAGE_SIZE;
		int pages = all / size + (all % size > 0 ? 1 : 0);

		for (int i = 0; i < pages; i++) {
			for (int j = 0; (j < size) && (j + i * size < all); j++) {

				System.out.println(list.get(j + i * Console.PAGE_SIZE));
			}

			if (i < pages - 1) {
				print("Press Enter...");
				@SuppressWarnings("resource")
				Scanner sc = new Scanner(System.in);
				sc.nextLine();
			}
		}
	}

}
