package RatMaze;

import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {

    private MainView mainView = new MainView();

    public MainFrame() {
        //set icon
        try {
            Image image = new ImageIcon(this.getClass().getResource("RatMazeIcon.png")).getImage();
            this.setIconImage(image);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "App icon not found!");
        }
        //Frame settings
        this.setTitle("Rat Maze Game");
        this.setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.setResizable(true);
        this.setVisible(true);
        this.setLayout(new BorderLayout());
        this.setLocation(0, 0);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(mainView);
    }

}
