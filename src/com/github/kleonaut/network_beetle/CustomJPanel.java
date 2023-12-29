package com.github.kleonaut.network_beetle;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

public class CustomJPanel extends JPanel {

    private Color backgroundColor = Color.RED;

    public void setIsGreen(boolean value) {
        backgroundColor = value ? Color.GREEN : Color.RED;
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(backgroundColor);
    }
}
