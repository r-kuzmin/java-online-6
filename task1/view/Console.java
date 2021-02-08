package by.epam.training.task1.view;

import java.util.List;

import by.epam.training.task1.bean.Book;
import by.epam.training.task1.bean.Person;

public class Console {

	public void print(String string) {
		System.out.println(string);
	}

	public void printItem(Book book) {
		System.out.println("Book:");
		System.out.println("\t(" + book.getId() + ") " + book.getName() + " - " + autorsToString(book));
	}

	public void printList(List<Book> list, String title) {
		System.out.println(title);
		for (Book book : list) {
			System.out.println("\t(" + book.getId() + ") " + book.getName() + " - " + autorsToString(book));
		}
	}
	
	/**
	 * Формирует строковое представление списка авторов книги.
	 * 
	 * @param book
	 * @return
	 */
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
	
}
