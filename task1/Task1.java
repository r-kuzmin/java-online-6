package by.epam.training.task1;

import java.io.IOException;

import by.epam.training.task1.logic.Library;
import by.epam.training.task1.view.Console;

/**
 * Учет книг в домашней библиотеке.
 * 
 * @author R. Kuzmin
 *
 */
public class Task1 {

	public static void main(String[] args) {
		
		Library lb;
		
		String path = System.getProperty("user.dir") + "\\library.xml";
		
		Console con = new Console();
		
		try {
			lb = new Library(path);
		} catch (IOException e) {
			con.print(e.toString());
			return;
		}
		
		con.printList(lb.getAll(), "Все книги в библиотеке:");
		
	}

}
