package RatMaze;

import javax.swing.*;
import java.awt.*;

public class SolutionFrame extends JFrame{

    public SolutionFrame(char[][] indexes) throws InterruptedException {
        //set icon
        try {
            Image image = new ImageIcon(this.getClass().getResource("RatMazeIcon.png")).getImage();
            this.setIconImage(image);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "App icon not found!");
        }
        //Frame settings
        this.setTitle("Simple Maze Solver");
        this.setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.setResizable(true);
        this.setVisible(true);
        this.setLayout(new BorderLayout());
        this.setLocation(0, 0);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SolutionView solutionView = new SolutionView(indexes);
        this.add(solutionView);
    }
}
