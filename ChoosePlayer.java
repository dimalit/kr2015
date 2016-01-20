
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Серега
 */
public class ChoosePlayer extends JFrame {

    private final JButton ButtonPlayerX;
    private final JButton ButtonPlayerO;
    private boolean vision = true; 

    public static boolean choose;
    
    private final String pathPicture = "res\\pic\\morpheus.jpg";
    
    public ChoosePlayer() throws HeadlessException {
        
        ButtonPlayerX = new JButton("X");
        ButtonPlayerO = new JButton("O");
        setSize(300, 300);
        setTitle("Choose player");
        Toolkit kit = Toolkit.getDefaultToolkit();
	Dimension screen = kit.getScreenSize();
	int w = screen.width;
	int h = screen.height;
        setLocation(w / 4, h / 4);
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        setResizable(false);
        
        JPanel panelX = new JPanel();
        add(panelX,BorderLayout.WEST);
        panelX.add(ButtonPlayerX,BorderLayout.SOUTH);
         
        JPanel panelO = new JPanel();
        add(panelO,BorderLayout.EAST);
        panelO.add(ButtonPlayerO,BorderLayout.SOUTH);
        
        
        JLabel picture = new JLabel();
        picture.setHorizontalAlignment(SwingConstants.CENTER);
        add(picture);
        picture.setIcon(new ImageIcon(pathPicture));
        
//        pack();
        
        ListenChange();
        setSetVisible(vision);
    }
    
  
    public void setSetVisible (boolean vision) {
        setVisible(vision);
    }
    
    
    public void ListenChange (){
        
            ButtonPlayerX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             ThreadMainFrame tmf = new ThreadMainFrame();
             choose = true;
             setSetVisible(false);
            }
        });
       
        
        ButtonPlayerO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {  
            ThreadMainFrame tmf = new ThreadMainFrame();
            choose = false;
            setSetVisible(false);
            }
        });
    }
 
}
