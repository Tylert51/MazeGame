import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class LevelChooser extends JFrame implements ActionListener {
    private JPanel mainPanel;
    private JPanel info;
    private JPanel buttons;
    private JLabel presetLevels;
    private JLabel customLevels;
    private JLabel instructions;
    private JTextField inputLvl;
    private JButton okayButton;

    private MainMenuGUI mainMenu;
    private int gameMode;

    public LevelChooser(int gameMode, MainMenuGUI mainMenu) {
        this.mainMenu = mainMenu;
        this.gameMode = gameMode;

        setContentPane(mainPanel);
        setTitle("Main Menu");
        setSize(128 * 4, 96 * 4);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        info.setBackground(Color.BLACK);
        mainPanel.setBackground(Color.BLACK);
        buttons.setBackground(Color.BLACK);
        okayButton.setBackground(Color.BLACK);

        okayButton.addActionListener(this);

        ArrayList<String[][]> listOfCustomMazes = new ArrayList<String[][]>();

        try {
            listOfCustomMazes = FileReadWrite.readFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        customLevels.setText("There are " + listOfCustomMazes.size() + " custom levels");


    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String textLevel = inputLvl.getText();
        inputLvl.setText("");

        setVisible(false);

        try {
            instantiateGamePanel(gameMode, textLevel);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

    }

    public void instantiateGamePanel(int gameMode, String level) throws IOException, ClassNotFoundException {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Maze");

        GamePanel gamePanel = new GamePanel(gameMode, level);
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}
