package Test;

import javax.swing.UIManager;

import View.Test;

public class TestTest {
public static void main(String[] args) {
	try {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new Test();
	} catch (Exception e) {
		e.printStackTrace();
	}
}
}
