package RatMaze;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class SolutionView extends JPanel {
    private JButton[][] buttonGrid;
    private char[][] indexes;
    private int dimensions;
    private boolean found;

    public SolutionView(char[][] indexes) {
        this.indexes = indexes;
        this.dimensions = this.indexes[0].length;
        buttonGrid = new JButton[dimensions][dimensions];
        this.setLayout(new GridLayout(dimensions, dimensions));
        this.setBackground(new Color(115,194, 251));
        indexes[0][0] = '0';
        DFS_Class dfs = new DFS_Class(indexes, dimensions);
        this.indexes = dfs.getAnswer();
        this.found = dfs.isFound();
        this.indexes[0][0]='2';
        drawGrid();

        ///border
        TitledBorder border = new TitledBorder("Solution view");
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        border.setTitleColor(Color.WHITE);
        border.setTitleFont(new Font("", Font.BOLD, 15));
        this.setBorder(border);
    }

    private void drawGrid() {
        ActionListener buttonListener = evt -> {
            JButton selectedBtn = (JButton) evt.getSource();
            for (JButton[] jButtons : buttonGrid) {
                for (JButton jButton : jButtons) {
                    if (jButton == selectedBtn) {
                        if (buttonGrid[0][0] == selectedBtn) {
                            JOptionPane.showMessageDialog(null, "Start game again!");
                            SolutionView.this.removeAll();
                            revalidate();
                            repaint();
                            new MainFrame();
                            JFrame terminate = (JFrame) SwingUtilities.getWindowAncestor(getParent());
                            terminate.dispose();
                        } else if (buttonGrid[dimensions - 1][dimensions - 1] == selectedBtn) {
                            JOptionPane.showMessageDialog(null, "See you later!");
                            SolutionView.this.removeAll();
                            revalidate();
                            repaint();
                            JFrame terminate = (JFrame) SwingUtilities.getWindowAncestor(getParent());
                            terminate.dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, found ? "Yellow path is the solution!" : "No path found!");
                        }
                    }
                }
            }
        };
        String text;
        for (int row = 0; row < dimensions; row++) {
            for (int col = 0; col < dimensions; col++) {
                if (row == col && (row == 0)) {
                    text = "Start";
                    buttonGrid[row][col] = new JButton(text);
                } else if (row == col && (row == dimensions - 1)) {
                    text = "End";
                    buttonGrid[row][col] = new JButton(text);
                } else {
                    text = "";
                    buttonGrid[row][col] = new JButton(text);
                }
                if (indexes[row][col] == '0') //dead blocks
                {
                    buttonGrid[row][col].setBackground(new Color(0, 0, 0));
                    buttonGrid[row][col].setForeground(new Color(255, 255, 255));
                } else if (indexes[row][col] == '2') // solution
                {
                    buttonGrid[row][col].setBackground(new Color(255,255,0));
                    buttonGrid[row][col].setForeground(new Color(255, 255, 255));
                } else { // free block
                    buttonGrid[row][col].setBackground(new Color(255, 255, 255));
                    buttonGrid[row][col].setForeground(new Color(0, 0, 0));
                }
                buttonGrid[row][col].addActionListener(buttonListener);
                this.add(buttonGrid[row][col]);
            }
        }
    }
}