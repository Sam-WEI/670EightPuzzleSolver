package ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Stack;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import core.AStarSearch;
import core.BFSearch;
import core.DFSearch;
import core.EightPuzzleState;
import core.SearchAlgorithm;
import core.SearchAlgorithm.SearchListener;
import core.SearchResult;
import core.SearchNode;

public class UI extends JFrame {
	private static final long serialVersionUID = -7316827103620049173L;

	JTextField tfInput;
	JScrollPane jsRight;
	
	JPanel pRight = new JPanel();
	JPanel pRightPath = new JPanel();
	
	JPanel pRightTop;
	
	final String DEFAULT_NUMS = "037168542";
	
	public UI() {
		super();
		initUI();
	}
	
	void initUI() {
		setLayout(new BorderLayout());
		
		
		JPanel pLeft = new JPanel();
		add(pLeft, BorderLayout.WEST);
		
		pRight.setLayout(new BorderLayout());
		
		add(pRight, BorderLayout.CENTER);
		
		pRightPath.setBackground(Color.DARK_GRAY);
		pRightPath.setLayout(new WrapLayout(FlowLayout.LEFT));
		jsRight = new JScrollPane(pRightPath, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants. HORIZONTAL_SCROLLBAR_NEVER);
		
		pRight.add(jsRight, BorderLayout.CENTER);
		
		pRightTop = new JPanel(new GridLayout(4, 1));
		
		pRightTop.add(getResultPanel("BFS"));
		pRightTop.add(getResultPanel("DFS"));
		pRightTop.add(getResultPanel("A* M"));
		pRightTop.add(getResultPanel("A* H"));
		
		pRight.add(pRightTop, BorderLayout.NORTH);
			
		
		pLeft.setLayout(new BorderLayout());
		pLeft.setBackground(Color.GREEN);
		
//		pLeft.setSize(300, 600);
		pLeft.setBackground(Color.green);
		
		tfInput = new JTextField(9);
		tfInput.setText(DEFAULT_NUMS);
		JPanel pLeftUp = new JPanel(new BorderLayout());
		pLeftUp.add(tfInput, BorderLayout.NORTH);
		
		
		pLeft.add(pLeftUp, BorderLayout.NORTH);
		tfInput.setFont(new Font(null, 0, 30));
		
		JPanel pButton1 = new JPanel(new GridLayout(1, 2));
		
		JButton btnRandom = new JButton("Random");
		JButton btnSet = new JButton("Set");
		
		pButton1.add(btnRandom);
		pButton1.add(btnSet);
		
		pLeftUp.add(pButton1, BorderLayout.SOUTH);
		
		JPanel pLeftMid = new JPanel();
		pLeft.add(pLeftMid, BorderLayout.CENTER);
		
		pLeftMid.setLayout(new BoxLayout(pLeftMid, BoxLayout.Y_AXIS));
		pLeftMid.setAlignmentY(CENTER_ALIGNMENT);
		
		pLeftMid.setBackground(Color.GRAY);
		
		pLeftMid.add(Box.createRigidArea(new Dimension(0, 20)));
		
		final GridPanel grid = new GridPanel("");
		Dimension d1 = new Dimension(180, 180);
		grid.setMinimumSize(d1);
		grid.setMaximumSize(d1);
		pLeftMid.add(grid);
		
		pLeftMid.add(Box.createRigidArea(new Dimension(0, 20)));
		
		JLabel labelGoal = new JLabel("Goal State");
		
		labelGoal.setAlignmentX(RIGHT_ALIGNMENT);//I don't know why setting this can finally make the "Goal" move a little bit leftward
		
		labelGoal.setForeground(Color.WHITE);
		
		pLeftMid.add(labelGoal);
		
		final GridPanel goalGrid = new GridPanel(EightPuzzleState.GOAL);
		Dimension d2 = new Dimension(120, 120);
		grid.setMinimumSize(d2);
		goalGrid.setMaximumSize(d2);
		pLeftMid.add(goalGrid);
		
		pLeftMid.add(Box.createRigidArea(new Dimension(0, 20)));
		
		final JLabel labelSolvable = new JLabel("");
		labelSolvable.setFont(new Font(null, 0, 20));
		labelSolvable.setAlignmentX(CENTER_ALIGNMENT);//I don't know why setting this can finally make the "Goal" move a little bit leftward
		labelSolvable.setForeground(Color.WHITE);
		pLeftMid.add(labelSolvable);
		
		
		JPanel pLeftDown = new JPanel(new GridLayout(4, 1));
		
		pLeft.add(pLeftDown, BorderLayout.SOUTH);
		
		final JButton btnBFS = new JButton("Breadth First Search");
		final JButton btnDFS = new JButton("Depth First Search");
		final JButton btnASM = new JButton("A* Search Manhattan");
		final JButton btnASH = new JButton("A* Search Hamming");
		
		pLeftDown.add(btnBFS);
		pLeftDown.add(btnDFS);
		pLeftDown.add(btnASM);
		pLeftDown.add(btnASH);
		
		btnRandom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = randomGenerate();
				
				tfInput.setText(s);
				labelSolvable.setText("");
			}
		});
		
		
		btnSet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				grid.setPuzzle(tfInput.getText());
				clearResultPanel();
				
				btnBFS.setEnabled(true);
				btnDFS.setEnabled(true);
				btnASM.setEnabled(true);
				btnASH.setEnabled(true);
				
				int[] num = SearchAlgorithm.convertToArray(tfInput.getText());
				boolean solvable = SearchAlgorithm.isSolvable(num);
				if(solvable) {
					labelSolvable.setForeground(Color.GREEN);
					labelSolvable.setText("Solvable");
				} else {
					labelSolvable.setForeground(Color.ORANGE);
					labelSolvable.setText("Not Solvable");
				}
			}
		});
		
		
		
		btnBFS.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final BFSearch s = new BFSearch(tfInput.getText());
				
				searchButtonClicked(btnBFS, s, 0);
			}
		});
		
		btnDFS.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DFSearch s = new DFSearch(tfInput.getText());
				searchButtonClicked(btnDFS, s, 1);
			}
		});
		
		btnASM.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AStarSearch s = new AStarSearch(tfInput.getText());
				searchButtonClicked(btnASM, s, 2, "m");
			}
		});
		
		btnASH.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AStarSearch s = new AStarSearch(tfInput.getText());
				searchButtonClicked(btnASH, s, 3, "h");
			}
		});
	}
	
	private void searchButtonClicked(final JButton btn, final SearchAlgorithm search, final int index, final String...params) {
		btn.setEnabled(false);
		
		JPanel p = (JPanel) pRightTop.getComponent(index);
		
		final JButton stop = (JButton) p.getComponent(10);
		
		for( ActionListener al : stop.getActionListeners() ) {
			stop.removeActionListener( al );
	    }
		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				search.stop();
			}
		});
		
		final JButton show = (JButton) p.getComponent(9);
		for( ActionListener al : show.getActionListeners() ) {
			show.removeActionListener( al );
	    }
		show.setEnabled(false);
		show.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showResult(search.result, index);
			}
		});
		
		search.setSearchListener(new SearchListener() {
			@Override
			public void searchCompleted(SearchResult r) {
				search.result = r;
				showResult(r, index);
				btn.setEnabled(true);
				stop.setEnabled(false);
				if(r.completed && r.hasSolution) {
					show.setEnabled(true);
				}
			}
		});
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				stop.setEnabled(true);
				search.search(params);
			}
		}).start();
		
		
	}
	
	private void showResult(SearchResult r, int index) {
		if(r == null) return;
		
		System.out.println(r);
		JPanel p = (JPanel) pRightTop.getComponent(index);
		
		JTextField tfSolvable = (JTextField) p.getComponent(2);
		JTextField tfTime = (JTextField) p.getComponent(4);
		JTextField tfCost = (JTextField) p.getComponent(6);
		JTextField tfNodeExamined = (JTextField) p.getComponent(8);
		
		tfSolvable.setText(r.hasSolution + "");
		
		if(!r.hasSolution) {
			return;
		}
		
		if(!r.completed) {
			tfTime.setText("stopped");
			return;
		}
		
		tfTime.setText("" + r.timeConsumed);
		tfCost.setText("" + r.cost);
		tfNodeExamined.setText("" + r.searchCount);
		
		
		ArrayList<SearchNode> solutionPath = r.solutionPath;
		int loopSize = solutionPath.size();
		pRightPath.removeAll();
		
		SearchNode tempNode;
		SearchNode parent;
		GridPanel gp;
		for (int i = loopSize-1; i >= 0; i--) {
			tempNode = solutionPath.get(i);
			
			gp = new GridPanel(((EightPuzzleState)tempNode.getCurState()).getCurBoard());
			
			//highlight number which just moved
			parent = tempNode.getParent();
			if(parent != null) {
				int newlyMoved = ((EightPuzzleState)parent.getCurState()).getBlankPile();
				gp.setHighlighted(newlyMoved);
			}
			
			
			gp.setPreferredSize(new Dimension(65, 65));
			pRightPath.add(gp);
		}
		pRightPath.updateUI();
	}
	
	private JPanel getResultPanel(String searchName) {
		
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		p.add(new JLabel("[" + searchName + "]  "));
		
		p.add(new JLabel("Solvable:"));
		p.add(new JTextField(4));
		
		p.add(new JLabel("Time:"));
		p.add(new JTextField(6));
		
		p.add(new JLabel("Cost:"));
		p.add(new JTextField(6));
		
		p.add(new JLabel("Examined Nodes:"));
		p.add(new JTextField(7));
		
		JButton show = new JButton("Show");
		JButton stop = new JButton("Stop");
	
		p.add(show);
		p.add(stop);
		
		show.setEnabled(false);
		stop.setEnabled(false);
		
		return p;
	}
	
	private void clearResultPanel() {
		int N = pRightTop.getComponentCount();
		for(int i = 0; i < N; i++) {
			JPanel p = (JPanel) pRightTop.getComponent(i);
			JTextField tfSolvable = (JTextField) p.getComponent(2);
			JTextField tfTime = (JTextField) p.getComponent(4);
			JTextField tfCost = (JTextField) p.getComponent(6);
			JTextField tfNodeExamined = (JTextField) p.getComponent(8);
			final JButton stop = (JButton) p.getComponent(10);
			final JButton show = (JButton) p.getComponent(9);
			
			if(stop.isEnabled()) {
				stop.doClick();
			}
			stop.setEnabled(false);
			show.setEnabled(false);
			
			tfSolvable.setText("");
			tfTime.setText("");
			tfCost.setText("");
			tfNodeExamined.setText("");
			
		}
	}
	
	private String randomGenerate() {
		Random random = new Random(System.currentTimeMillis());
		LinkedHashSet<Integer> set = new LinkedHashSet<>();
		
		String result = "";
		
		while(set.size() < 9) {
			int r = random.nextInt(9);
			if(set.add(r)) {
				result += r;
			}
		}
		
		System.out.println(result);
		
		return result;
	}

}
