
import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
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
        
		setNimbusLookAndFeel();

		
		EventQueue.invokeAndWait(new Runnable() {
			public void run() {
				
				GameFrame wnd = new GameFrame();
				wnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				wnd.setTitle("Крестики-нолики");
				wnd.setLocation(100, 100);
				wnd.setResizable(false);
				wnd.setVisible(true);
			}
		});

	}


	private static void setNimbusLookAndFeel() {
	
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

    
    
    
}
