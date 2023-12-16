package RatMaze;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class GridView extends JPanel {
    private JButton[][] buttonGrid;
    private char[][] indexes;
    private int dimensions;

    public GridView(int dimensions) {
        this.dimensions = dimensions;
        indexes = new char[dimensions][dimensions];
        for (char[] arr1 : indexes) //mark all as not dead blocks
            Arrays.fill(arr1, '1');
        indexes[0][0] = '2';
        indexes[dimensions - 1][dimensions - 1] = '2';
        buttonGrid = new JButton[dimensions][dimensions];
        this.setLayout(new GridLayout(dimensions, dimensions));
        this.setBackground(new Color(115, 194, 251));
        drawGrid();

        ///border
        TitledBorder border = new TitledBorder("Grid view");
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        border.setTitleColor(Color.WHITE);
        border.setTitleFont(new Font("", Font.BOLD, 15));
        this.setBorder(border);
    }

    private void drawGrid() {
        ActionListener buttonListener = evt -> {
            JButton selectedBtn = (JButton) evt.getSource();
            for (int row = 0; row < buttonGrid.length; row++) {
                for (int col = 0; col < buttonGrid[row].length; col++) {
                    if (buttonGrid[row][col] == selectedBtn) {
                        if (buttonGrid[0][0] == selectedBtn) {
                            JOptionPane.showMessageDialog(null, "Select the dead blocks, then click 'End' to solve!");
                        } else if (buttonGrid[dimensions - 1][dimensions - 1] == selectedBtn) {
                            GridView.this.removeAll();
                            revalidate();
                            repaint();
                            try {
                                new SolutionFrame(indexes);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            JFrame terminate = (JFrame) SwingUtilities.getWindowAncestor(getParent());
                            terminate.dispose();
                        } else {
                            indexes[row][col] = '0';
                            buttonGrid[row][col].setBackground(new Color(0, 0, 0));
                            buttonGrid[row][col].setForeground(new Color(255, 255, 255));
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
                    buttonGrid[row][col].setBackground(new Color(255,255, 0));
                    buttonGrid[row][col].setForeground(new Color(255, 255, 255));
                } else if (row == col && (row == dimensions - 1)) {
                    text = "End";
                    buttonGrid[row][col] = new JButton(text);
                    buttonGrid[row][col].setBackground(new Color(255,255, 0));
                    buttonGrid[row][col].setForeground(new Color(255, 255, 255));
                } else {
                    text = "";
                    buttonGrid[row][col] = new JButton(text);
                    buttonGrid[row][col].setBackground(new Color(255, 255, 255));
                    buttonGrid[row][col].setForeground(new Color(0, 0, 0));
                }
                buttonGrid[row][col].addActionListener(buttonListener);
                this.add(buttonGrid[row][col]);
            }
        }
    }
}