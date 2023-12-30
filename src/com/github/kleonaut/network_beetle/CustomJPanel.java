package com.github.kleonaut.network_beetle;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

public class CustomJPanel extends JPanel {

    private Color backgroundColor = Color.GRAY;

    public void setColor(Color c) {
        backgroundColor = c;
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(backgroundColor);
    }
}
