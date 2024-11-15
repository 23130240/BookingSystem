package Test;

import javax.swing.UIManager;

import View.BookingSystem;

public class Test {
public static void main(String[] args) {
	try {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new BookingSystem();
	} catch (Exception e) {
		e.printStackTrace();
	}
}
}
