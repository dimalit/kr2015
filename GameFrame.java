
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.*;
import tictactoe.common.BoardItem;
import tictactoe.common.GameBoard;

/**
 * 
 * @author Серёга.
 *
 */
class GameFrame extends JFrame {

	private static final long serialVersionUID = 345435345345435L;

	
	private  final int BOARD_SIZE = 3;

	private  final int FIELD_WIDTH = 34;

	private  final int WIN_COUNT = 3;

	private final int FRAME_DEFAULT_WIDH = 280;
	private final int FRAME_DEFAULT_HEIGTH = 250;

	
	private boolean playerIsX = true;
	private JPanel gamePanel;

	
	private int xCount = 0;
	private int oCount = 0;

	private GameBoard board;

	public GameFrame() {

		board = new GameBoard(BOARD_SIZE);

		
		JMenuBar mainMenu = new JMenuBar();
		setJMenuBar(mainMenu);
		JMenu fileMenu = new JMenu("Файл");
		JMenu helpMenu = new JMenu("Справка");
		JMenuItem exitItem = new JMenuItem("Выход");
		JMenuItem clearItem = new JMenuItem("Новая игра");
		fileMenu.add(exitItem);
		fileMenu.add(clearItem);
		JMenuItem aboutItem = new JMenuItem("О программе");
		helpMenu.add(aboutItem);
		mainMenu.add(fileMenu);
		mainMenu.add(helpMenu);

	
		gamePanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
		
		JScrollPane scrollFrame = new JScrollPane(gamePanel);
		gamePanel.setAutoscrolls(true);
		scrollFrame.setPreferredSize(new Dimension(FRAME_DEFAULT_WIDH, FRAME_DEFAULT_HEIGTH));

		for (int i = 0; i < board.getSize(); i++) {

			for (int j = 0; j < board.getSize(); j++) {
				
				JButton btn = new JButton(board.getItemAt(i, j).getName());
				btn.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_WIDTH));
				btn.setAction(new ButtonClickAction(i, j));
				gamePanel.add(btn);
			}
		}

		setContentPane(scrollFrame);
		pack();

		// события
		exitItem.addActionListener(new ExitActionListener());
		aboutItem.addActionListener(new AboutActionListener());
		clearItem.addActionListener(new ClearActionListener());

		SwingUtilities.updateComponentTreeUI(mainMenu);
		SwingUtilities.updateComponentTreeUI(gamePanel);
	}

	
	private void clearGamePanel() {

		playerIsX = true;
		int count = 0;
		
		
		for (int i = 0; i < board.getSize(); i++) {
			for (int j = 0; j < board.getSize(); j++) {
			
				BoardItem item = board.getItemAt(i, j);
				if (!item.equals(BoardItem.UNDEFINED)){
					JButton button = (JButton) gamePanel.getComponents()[count];
					button.setText(new String());
					button.setEnabled(true);
					button.setBackground(null);
					button.setForeground(null);
				}
				count++;
			}
		}
		
		board = new GameBoard(BOARD_SIZE);
	}

	
	private class ButtonClickAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

	
		private int i, j;

		public ButtonClickAction(int i, int j) {
			this.i = i;
			this.j = j;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			JButton source = (JButton) e.getSource();

			
			if (!board.getItemAt(i, j).equals(BoardItem.UNDEFINED))
				return;

			
			board.setItemAt(playerIsX ? BoardItem.X : BoardItem.O, i, j);
			source.setText(board.getItemAt(i, j).getName());
			source.setEnabled(false);

			
			playerIsX = !playerIsX;

			
			BoardItem winner = board.getNextWinner(WIN_COUNT);

			if (winner != null && !winner.equals(BoardItem.UNDEFINED)) {

				String msg;

				if (winner.equals(BoardItem.X)) {
					msg = "крестиков";
					xCount++;
				} else {
					msg = "ноликов";
					oCount++;
				}

				renderGameField();

				JOptionPane.showMessageDialog(GameFrame.this, "Победа " + msg
						+ ", счет " + " - крестики (" + xCount + "): нолики ("
						+ oCount + ")", "Победа",
						JOptionPane.INFORMATION_MESSAGE);

				clearGamePanel();

			}
		}
	}

	private void renderGameField() {
		int count = 0;
		for (int i = 0; i < board.getSize(); i++)
			for (int j = 0; j < board.getSize(); j++) {
				JButton buff = (JButton) gamePanel.getComponents()[count++];
				buff.setText(board.getItemAt(i, j).getName());

				if (board.getItemAt(i, j).equals(BoardItem.O_WIN_FIELD)
						|| board.getItemAt(i, j).equals(BoardItem.X_WIN_FIELD)) {
					buff.setOpaque(true);
					buff.setForeground(Color.BLACK);
				}
			}
	}

	
	private class ExitActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.exit(0);
		}
	}

	
	private class ClearActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			clearGamePanel();
			xCount = 0;
			oCount = 0;
		}
	}

	
	private class AboutActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			JOptionPane.showMessageDialog(GameFrame.this, "Крестики-нолики "
					+ BOARD_SIZE + "x" + BOARD_SIZE
					+ ". \nДля победы составьте линию из " + WIN_COUNT
					+ " крестиков или ноликов.", "О программе",
					JOptionPane.INFORMATION_MESSAGE);

		}
	}
}