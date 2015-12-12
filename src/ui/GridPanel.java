package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GridPanel extends JPanel {
	private static final long serialVersionUID = 7133640156685996411L;

	String order;
	
	int WIDTH;
	
	int UNIT_WIDTH;
	
	int highlighted = -1;
	
	public GridPanel() {
		super();
	}
	
	public GridPanel(String order) {
		this();
		this.order = order;
	}
	
	public GridPanel(int[] board){
		this();
		String s = "";
		for(int i : board) {
			s += i;
		}
		this.order = s;
	}
	
	public void setPuzzle(String order) {
		this.order = order;
		repaint();
	}
	
	public void setHighlighted(int h) {
		highlighted = h;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		WIDTH = getWidth();
		UNIT_WIDTH = WIDTH / 3;
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, WIDTH);
		g.setColor(Color.BLACK);
		for(int i = 1; i < 3; i++) {
			g.drawLine(UNIT_WIDTH * i, 0, UNIT_WIDTH * i, WIDTH);
			g.drawLine(0, UNIT_WIDTH * i, WIDTH, UNIT_WIDTH * i);
		}
		
		g.setColor(Color.BLACK);
		g.setFont(new Font(null, 0, UNIT_WIDTH));
		
		if(order != null && !order.equals("")) {
			for(int i = 0; i < order.length(); i++) {
				String c = order.substring(i, i+1);
				if(!c.equals("0")) {
					int col = i / 3;
					int row = i % 3;
					if(i == highlighted) {
						g.setColor(Color.RED);
					} else {
						g.setColor(Color.BLACK);
					}
					g.drawString(c, UNIT_WIDTH / 4 + UNIT_WIDTH * row, UNIT_WIDTH * 6 / 7 + UNIT_WIDTH * col);
				}
			}
		}
	}
}
