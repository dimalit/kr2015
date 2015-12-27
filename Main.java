
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
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
				wnd.setResizable(false);
				wnd.setVisible(true);
                                Toolkit kit = Toolkit.getDefaultToolkit();
		                Dimension screen = kit.getScreenSize();
		                int w = screen.width;
		                int h = screen.height;
                                wnd.setLocation(w / 4, h / 4);
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
