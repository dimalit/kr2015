package graphic;


import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author Серега
 */
public class ThreadChoosePlayer implements Runnable{

   Thread t;
    
    public ThreadChoosePlayer() {
        t = new Thread(this);
        t.start();   
    }

    private void CreateChoosePlayerFrame(){
     ChoosePlayerFrame cpf = new ChoosePlayerFrame(); 
     Toolkit kit = Toolkit.getDefaultToolkit();
	Dimension screen = kit.getScreenSize();
	int w = screen.width;
	int h = screen.height;
        cpf.setLocation(w / 4, h / 4);
    } 

    @Override
    public void run() {
        CreateChoosePlayerFrame();
    } 
}    
    
    

