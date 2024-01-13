package com.github.kleonaut.network_beetle;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class Constraints
{
    private GridBagConstraints c = new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0);

    public Constraints(int x, int y) { c.gridx = x; c.gridy = y; }
    public GridBagConstraints get() { return c; }
    public Constraints width(int w) { c.gridwidth = w; return this; }
    public Constraints height(int h) { c.gridheight = h; return this; }
    public Constraints stretch()
    {
        c.weightx = 1;
        if (c.fill == GridBagConstraints.NONE)
            c.fill = GridBagConstraints.HORIZONTAL;
        else
            c.fill = GridBagConstraints.BOTH;
        return this;
    }
    public Constraints grow()
    {
        c.weighty = 1;
        if (c.fill == GridBagConstraints.NONE)
            c.fill = GridBagConstraints.VERTICAL;
        else
            c.fill = GridBagConstraints.BOTH;
        return this;
    }
    public Constraints anchor(int numpad)
    {
        switch (numpad)
        {
            case 1: c.anchor = GridBagConstraints.SOUTHWEST; break;
            case 2: c.anchor = GridBagConstraints.SOUTH; break;
            case 3: c.anchor = GridBagConstraints.SOUTHEAST; break;
            case 4: c.anchor = GridBagConstraints.WEST; break;
            case 5: c.anchor = GridBagConstraints.CENTER; break;
            case 6: c.anchor = GridBagConstraints.EAST; break;
            case 7: c.anchor = GridBagConstraints.NORTHWEST; break;
            case 8: c.anchor = GridBagConstraints.NORTH; break;
            case 9: c.anchor = GridBagConstraints.NORTHEAST; break;
        }
        return this;
    }

    public Constraints insets(int px)
    {
        c.insets = new Insets(px, px, px, px);
        return this;
    }

    public Constraints insets(int top, int bottom, int left, int right)
    {
        c.insets = new Insets(top, left, bottom, right);
        return this;
    }
}
