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

	public boolean playerIsX;
	private final JPanel gamePanel;
        
        private final String playerX = "X";
        private final String playerO = "O";
        
	private int xCount = 0;
	private int oCount = 0;
        private int score_draws = 0;
        private int PARITY_X_O = 0;
        private int reserve_PARITY_X_O = 0;
        
	private GameBoard board;
        private final Font font = new Font("TimesRoman", Font.BOLD, 15);  

        
	public GameFrame() {

		board = new GameBoard(BOARD_SIZE);

                setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
                               
		JMenuBar mainMenu = new JMenuBar();
		setJMenuBar(mainMenu);
		JMenu fileMenu = new JMenu("Файл");
                JMenu gameMenu = new JMenu("Игра");
                JMenu menuUI = new JMenu("Вид");
		JMenu helpMenu = new JMenu("Справка");
                  
                JMenuItem Theme = new JMenu("Look & Feel");
                JMenuItem itemui1 = new JRadioButtonMenuItem("Windows Look & Feel");
                JMenuItem itemui2 = new JRadioButtonMenuItem("Metal Look & Feel");
                itemui2.setSelected(true);
                JMenuItem itemui3 = new JRadioButtonMenuItem("Motif Look & Feel");
                ButtonGroup groupChangesThemeButtons = new ButtonGroup();
                groupChangesThemeButtons.add(itemui1);
                groupChangesThemeButtons.add(itemui2);
                groupChangesThemeButtons.add(itemui3);
                

		JMenuItem exitItem = new JMenuItem("Выход");
                JMenuItem aboutAccount = new JMenuItem("Текущий счет");
                JMenuItem resetScr = new JMenuItem("Сброс счета");
                
                gameMenu.add(aboutAccount);
                gameMenu.add(resetScr);
		fileMenu.add(exitItem);
                
		JMenuItem aboutItem = new JMenuItem("О программе");
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

		aboutAccount.addActionListener(new AboutCurrentAccount());
		exitItem.addActionListener(new ExitActionListener());
                resetScr.addActionListener(new ResetScore());
		aboutItem.addActionListener(new AboutActionListener());
                
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

		private final int i, j;

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
                        reserve_PARITY_X_O++;
			
			playerIsX = !playerIsX;

			BoardItem winner = board.getNextWinner(WIN_COUNT);

			if (winner != null && !winner.equals(BoardItem.UNDEFINED)) {

				String msg;

				if (winner.equals(BoardItem.X)) {
					msg = "Крестики";
					xCount++;
				} else {
					msg = "Нолики";
					oCount++;
				}

				renderGameField();
                                
                                PARITY_X_O = 0; 
                                reserve_PARITY_X_O = 0;
                                
				JOptionPane.showMessageDialog(GameFrame.this, msg + " победили"
						+ "! Счет: " + playerX +  "(" + xCount + ")" + ", " + playerO + "("
						+ oCount + "), ничьих: " + score_draws , " ",
						JOptionPane.INFORMATION_MESSAGE);
                                
                                clearGamePanel();
				
 
			}    else { if (PARITY_X_O == 9 | reserve_PARITY_X_O == 9) { 
                            
                        PARITY_X_O = 0; 
                        reserve_PARITY_X_O = 0;
                        
                        score_draws++;
                                        
                        JOptionPane.showMessageDialog(GameFrame.this,"Ничья.Счет: " + playerX +  "(" + xCount + ")" + ", " + playerO + "("
						+ oCount + "), ничьих: " + score_draws , "Ничья", JOptionPane.INFORMATION_MESSAGE);             
                        clearGamePanel();                           
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
          
        String nameTheme= "javax.swing.plaf.metal.MetalLookAndFeel";
         switch(type)
                 {
           case 1: nameTheme = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
                 break;
           case 2: nameTheme = "javax.swing.plaf.metal.MetalLookAndFeel";
                 break;
           case 3: nameTheme = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
                 break;
                }
          try
            {
       UIManager.setLookAndFeel(nameTheme);
       SwingUtilities.updateComponentTreeUI(this);
        }
       catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            }    
      }
        
                
	
	private class ExitActionListener implements ActionListener {
           
                @Override
		public void actionPerformed(ActionEvent event) 
                {
                    
                  Object arr [] = {"Этот мир слишком жестокий для меня","Я доведу это дело до конца"};
                    
                  int answerExit =  JOptionPane.showOptionDialog(GameFrame.this, "Вы уверены?", "Выход", 
                          JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,  
                                                    null, arr, arr[0]);
                    
                  if (answerExit == JOptionPane.YES_OPTION)
                  {
                      System.exit(0);
                  } 
	
		}
	}



	private class AboutActionListener implements ActionListener {
                @Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(GameFrame.this, "Крестики-нолики (3x3) 0.1",
                                        "О программе", JOptionPane.INFORMATION_MESSAGE);
		}
	}
        
        
        private class ResetScore implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            
                  Object options [] = {"Да","Нет"};
                    
                  int answerExit =  JOptionPane.showOptionDialog(GameFrame.this, "Сбросить текущий счет?", "Подтверждение выбора", 
                          JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    
                  if (answerExit == JOptionPane.YES_OPTION)
                  {
		     xCount = 0;
	             oCount = 0;
                     PARITY_X_O = 0;
                     score_draws = 0;
                      
                  } else if (answerExit == JOptionPane.NO_OPTION)
                  {
                      answerExit = JOptionPane.CLOSED_OPTION;
                  }
              }  
        }
        
        
        private class AboutCurrentAccount implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
           JOptionPane.showMessageDialog(GameFrame.this, "Счет: X "
                   + "(" + xCount + "), O ("
			+ oCount + "), ничьих: " + score_draws, "Текущий счет", JOptionPane.INFORMATION_MESSAGE);
           }
       }

        
        private class ChangeTheme1 implements ActionListener {
            
          @Override
          public void actionPerformed(ActionEvent e) {
            setLookAndFeel(1);
          }      
        }
        
          private class ChangeTheme2 implements ActionListener {
            
          @Override
          public void actionPerformed(ActionEvent e) {
            setLookAndFeel(2);
          }      
        }
        
          
          private class ChangeTheme3 implements ActionListener {
            
          @Override
          public void actionPerformed(ActionEvent e) {
            setLookAndFeel(3);
          }      
        }  
   
}