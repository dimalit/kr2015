
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author Серега
 */
public class ThreadMainFrame implements Runnable{

    Thread t;
   
    public ThreadMainFrame() {
      
    t = new Thread(this);
    t.start();
          
    }

   @Override
    public void run() {  
    CreateFrame();    
    }
    

    private void CreateFrame(){
                 
               GameFrame gameframe = new GameFrame(); 
               
               if (ChoosePlayerFrame.choose == true) {
                   gameframe.playerIsX = true;
               } else { if (ChoosePlayerFrame.choose == false)
                   gameframe.playerIsX = false;
               }
               
               
               gameframe.addWindowListener(new WindowAdapter() {
               @Override
               public void windowClosing(WindowEvent e) {
                
                Object options [] = {"Пути назад уже нету...","Я должен закончить это дело"};
                                
                int answerExit =  JOptionPane.showOptionDialog(gameframe, "Вы уверены?", "Выход", 
                          JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    
                  if (answerExit == JOptionPane.YES_OPTION)
                  {
                      System.exit(0);
                  }
                 
            }
        });
				gameframe.setTitle("Крестики-нолики");
				gameframe.setResizable(false);
                                gameframe.setVisible(true);
                                Toolkit kit = Toolkit.getDefaultToolkit();
		                Dimension screen = kit.getScreenSize();
		                int w = screen.width;
		                int h = screen.height;
                                gameframe.setLocation(w / 4, h / 4);
    }
}
