import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import tictactoe.common.BoardItem;
import tictactoe.common.GameBoard;
/**
 * 
 * @author Серёга.
 *
 */
class GameFrame extends JFrame 
{
    
	private  final int BOARD_SIZE = 3;
	private  final int FIELD_WIDTH = 34;

	private  final int WIN_COUNT = 3;

	private final int FRAME_DEFAULT_WIDH = 360;
	private final int FRAME_DEFAULT_HEIGTH = 360;

	private boolean playerIsX = true;
	private JPanel gamePanel;
        
        private final String playerX = "Player 1: X";
        private final String playerO = "Player 2: O";
        
        private final String nameBGM = "";
        
	private int xCount = 0;
	private int oCount = 0;
        private int PARITY_X_O = 0;
        
	private GameBoard board;
        private Font font = new Font("TimesRoman", Font.BOLD, 15);  
       
        
	public GameFrame() {

		board = new GameBoard(BOARD_SIZE);

		JMenuBar mainMenu = new JMenuBar();
		setJMenuBar(mainMenu);
		JMenu fileMenu = new JMenu("File");
                JMenu gameMenu = new JMenu("Game");
                JMenu menuUI = new JMenu("View");
		JMenu helpMenu = new JMenu("About");
                  
                JMenuItem Theme = new JMenu("Look & Feel");
                JMenuItem itemui1 = new JRadioButtonMenuItem("Windows Look & Feel");
                JMenuItem itemui2 = new JRadioButtonMenuItem("Metal Look & Feel");
                itemui2.setSelected(true);
                JMenuItem itemui3 = new JRadioButtonMenuItem("Motif Look & Feel");
                ButtonGroup bg = new ButtonGroup();
                bg.add(itemui1);
                bg.add(itemui2);
                bg.add(itemui3);
                
                
		JMenuItem exitItem = new JMenuItem("Exit");
		JMenuItem clearItem = new JMenuItem("New Game");
                JMenuItem aboutAccount = new JMenuItem("Score");
                JMenuItem instruction = new JMenuItem("Instructions");
                
                gameMenu.add(clearItem);
                gameMenu.add(aboutAccount);
		fileMenu.add(exitItem);
                helpMenu.add(instruction);
                
		JMenuItem aboutItem = new JMenuItem("Tic-Tac-Toe");
		helpMenu.add(aboutItem);
		mainMenu.add(fileMenu);
                mainMenu.add(gameMenu);
                mainMenu.add(menuUI);
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

		aboutAccount.addActionListener(new AboutCurrnetAccount());
		exitItem.addActionListener(new ExitActionListener());
		aboutItem.addActionListener(new AboutActionListener());
		clearItem.addActionListener(new ClearActionListener());
                instruction.addActionListener(new AboutInstructions());
                
		SwingUtilities.updateComponentTreeUI(mainMenu);
		SwingUtilities.updateComponentTreeUI(gamePanel);
                
                
                itemui1.addActionListener(new ChangeTheme1());
                itemui2.addActionListener(new ChangeTheme2()); 
                itemui3.addActionListener(new ChangeTheme3());
                
                menuUI.add(itemui1);
                menuUI.add(itemui2);
                menuUI.add(itemui3);
      
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

		private int i, j;

		public ButtonClickAction(int i, int j) {
			this.i = i;
			this.j = j;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			JButton source = (JButton) e.getSource();
                        source.setFont(font);      
			
			if (!board.getItemAt(i, j).equals(BoardItem.UNDEFINED))
				return;

			board.setItemAt(playerIsX ? BoardItem.X : BoardItem.O, i, j);
			source.setText(board.getItemAt(i, j).getName());
			source.setEnabled(false);
                        PARITY_X_O++;
			
			playerIsX = !playerIsX;

			BoardItem winner = board.getNextWinner(WIN_COUNT);

			if (winner != null && !winner.equals(BoardItem.UNDEFINED)) {

				String msg;

				if (winner.equals(BoardItem.X)) {
					msg = "Player 1";
					xCount++;
				} else {
					msg = "Player 2";
					oCount++;
				}

				renderGameField();
                                
                                PARITY_X_O = 0; 
                                
				JOptionPane.showMessageDialog(GameFrame.this, msg + " won"
						+ "! Score: " + playerX +  "(" + xCount + ")" + "-" + playerO + "("
						+ oCount + ")", "Winner",
						JOptionPane.INFORMATION_MESSAGE);
                                
				clearGamePanel();
 
			}    else { if (PARITY_X_O == 9) { 
                            
                            
                  PARITY_X_O = 0;          
                            
                  Object arr [] = {"Yes","No"};
                    
                  int answerExit =  JOptionPane.showOptionDialog(GameFrame.this, "Parity.Start new game?", "Parity", JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE, null, arr, arr[0]);
                    
                  if (answerExit == JOptionPane.YES_OPTION)
                  {
                      clearGamePanel();
                  } else if (answerExit == JOptionPane.NO_OPTION)
                  {
                      answerExit = JOptionPane.CLOSED_OPTION; 
                   }     
                  }
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

       
        private void setLookAndFeel (int type)
        {
          
        String laf= "javax.swing.plaf.metal.MetalLookAndFeel";
         switch(type)
                 {
           case 1: laf = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
                 break;
           case 2: laf = "javax.swing.plaf.metal.MetalLookAndFeel";
                 break;
           case 3: laf = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
                 break;
                }
          try
            {
           //Меняем
        UIManager.setLookAndFeel(laf);
        //Обновляем
       SwingUtilities.updateComponentTreeUI(this);
        }
       catch(Exception e)
        {
       e.printStackTrace();
            }    
      }
        
                
	
	private class ExitActionListener implements ActionListener {
           
		public void actionPerformed(ActionEvent event) 
                {
                    
                  Object arr [] = {"Yes","No"};
                    
                  int answerExit =  JOptionPane.showOptionDialog(GameFrame.this, "Exit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, arr, arr[0]);
                    
                  if (answerExit == JOptionPane.YES_OPTION)
                  {
                      System.exit(0);
                  } else if (answerExit == JOptionPane.NO_OPTION)
                  {
                      answerExit = JOptionPane.CLOSED_OPTION;
                  }
	
		}
	}

	
	private class ClearActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
                    
                  Object arr [] = {"Yes","No"};
                    
                  int answerExit =  JOptionPane.showOptionDialog(GameFrame.this, "Start new game?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, arr, arr[0]);
                    
                  if (answerExit == JOptionPane.YES_OPTION)
                  {
                     clearGamePanel();
		     xCount = 0;
	             oCount = 0;
                     PARITY_X_O = 0;
                      
                  } else if (answerExit == JOptionPane.NO_OPTION)
                  {
                      answerExit = JOptionPane.CLOSED_OPTION;
                  }
	
		}
	}

	
	private class AboutActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			JOptionPane.showMessageDialog(GameFrame.this, "Tic-Tac-Toe "
					+ "(" + "3" + "x" + "3" + ")"
                                        + " v_1.3" , "About", JOptionPane.INFORMATION_MESSAGE);
		}
	}
        
        private class AboutCurrnetAccount implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
           JOptionPane.showMessageDialog(GameFrame.this, "Score " + " - X "
                   + "(" + xCount + "): O ("
			+ oCount + ")", "Score", JOptionPane.INFORMATION_MESSAGE);
           }
       }
      
        
        private class AboutInstructions implements ActionListener {

        public void actionPerformed(ActionEvent e) {
           JOptionPane.showMessageDialog(GameFrame.this, "Your goal is to be the first player to get 3 X's or O's in a\n" +
             "row. (horizontally, diagonally, or vertically)\n" +
                       "Player 1: X\n" + "Player 2: O","Instruction",JOptionPane.INFORMATION_MESSAGE);
             }       
        }
        
        
        private class ChangeTheme1 implements ActionListener {
            
          public void actionPerformed(ActionEvent e) {
            setLookAndFeel(1);
          }      
        }
        
          private class ChangeTheme2 implements ActionListener {
            
          public void actionPerformed(ActionEvent e) {
            setLookAndFeel(2);
          }      
        }
        
          
          private class ChangeTheme3 implements ActionListener {
            
          public void actionPerformed(ActionEvent e) {
            setLookAndFeel(3);
          }      
        }  
        
     
}