import javax.swing.*;
import java.awt.*;

public class MainMenuGUI extends JFrame {

    private JPanel mainPanel;
    private JPanel buttons;
    private JPanel mazeGame;
    private JLabel mazeTitle;
    private JButton play;
    private JButton create;
    private JButton Watch;


    private ImageIcon background;


    public MainMenuGUI() {

        setContentPane(mainPanel);
        setTitle("Main Menu");
        setSize(128 * 6, 96 * 6);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        mazeGame.setBackground(Color.BLACK);
        buttons.setBackground(Color.BLACK);
        mainPanel.setBackground(Color.BLACK);
        mazeTitle.setBackground(Color.WHITE);




    }
}
