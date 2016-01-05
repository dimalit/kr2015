
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;
/**
 *
 * @author Серега
 */
public class Main 
{
    public static void main(String[] args) throws InterruptedException,
			InvocationTargetException, ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
        
		EventQueue.invokeAndWait(new Runnable() {
			public void run() {	
		        GameFrame gameframe = new GameFrame();
                                
                         gameframe.setDefaultCloseOperation(GameFrame.DO_NOTHING_ON_CLOSE);
                         gameframe.addWindowListener(new WindowAdapter() {
             @Override
            public void windowClosing(WindowEvent e) {
                
                Object options [] = {"Yes","No"};
                                
                int answerExit =  JOptionPane.showOptionDialog(gameframe, "Are you sure?", "Exit", 
                          JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    
                  if (answerExit == JOptionPane.YES_OPTION)
                  {
                      gameframe.dispose();
                  }
                 
            }
        });
				gameframe.setTitle("Tic-Tac-Toe");
				gameframe.setResizable(false);
				gameframe.setVisible(true);
                                Toolkit kit = Toolkit.getDefaultToolkit();
		                Dimension screen = kit.getScreenSize();
		                int w = screen.width;
		                int h = screen.height;
                                gameframe.setLocation(w / 4, h / 4);
			}
		});

	}

}
