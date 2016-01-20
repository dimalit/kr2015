
import com.sun.glass.ui.Cursor;
import java.util.logging.Level;
import java.util.logging.Logger;


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
     ChoosePlayer cp = new ChoosePlayer();
        
    } 

    
    @Override
    public void run() {
        
    CreateChoosePlayerFrame(); 

    } 

}    
    
    

