package core;
import javax.swing.JFrame;

import ui.UI;

public class EightPuzzleSolver {
	
	public static void main(String[] args) {
		UI ui = new UI();
		ui.setSize(1000, 650);
		ui.setResizable(false);
		ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ui.setLocationRelativeTo(null);
		ui.setVisible(true);
	}
}
