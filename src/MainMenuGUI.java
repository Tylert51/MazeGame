import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuGUI extends JFrame implements ActionListener {

    private JPanel mainPanel;
    private JPanel buttons;
    private JPanel mazeGame;
    private JLabel mazeTitle;
    private JButton play;
    private JButton create;
    private JButton watch;

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

        //actions listeners

        play.addActionListener(this);
        create.addActionListener(this);
        watch.addActionListener(this);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        JButton buttonClicked = (JButton) source;
        String text = buttonClicked.getText();

        if(text.equals("Play")) {

            LevelChooser levelChooser = new LevelChooser(1, this);


        } else if (text.equals("Create")) {

            instantiateGamePanel(2, "");

        } else if (text.equals("Watch")) {


        }
    }


    public void instantiateGamePanel(int gameMode, String level) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Maze");

        GamePanel gamePanel = new GamePanel(gameMode, level);
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        if (gameMode == 1) {

            gamePanel.startGameThread();

        } else if (gameMode == 2) {

            JFrame tilePick = new JFrame();
            tilePick.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            tilePick.setResizable(false);
            tilePick.setTitle("Tile Picker");

            TilePickerPanel drawingPanel = gamePanel.getDrawingPanel();
            tilePick.add(drawingPanel);
            tilePick.pack();
            tilePick.setLocation(40, 175);
            tilePick.setVisible(true);

            drawingPanel.startGameThread();
            gamePanel.startGameThread();

        }
    }
}
