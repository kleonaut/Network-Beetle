package com.github.kleonaut.network_beetle;

import javax.swing.*;
import java.awt.GridBagLayout;
import java.util.Enumeration;
import java.util.List;

public class OverviewFrame implements Powerable, Disposable, ModeAware
{
    // TODO: figure out how to handle JDK bug 8221452: setMinimumSize() does not take into account display scaling %
        // current solution: have no miminum size
    private final JFrame frame = new JFrame("Network Beetle");
    private final JButton powerButton = new JButton("Enable");
    private static JTextArea logField;
    private final Record record;
    private final ButtonGroup modeToggles = new ButtonGroup();
    private final ModeAwareGroup modeAwareGroup;

    public OverviewFrame(PowerableGroup powerableGroup, ModeAwareGroup modeAwareGroup, Record record)
    {
        this.record = record;
        this.modeAwareGroup = modeAwareGroup;

        logField = new JTextArea(3, 25);
        logField.setEditable(false);
        logField.setFocusable(false);
        powerButton.addActionListener(e -> powerableGroup.toggle());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        List<Mode> modes = record.modes(true);
        for (int i = 0; i < modes.size(); i++)
        {
            Mode mode = modes.get(i);

            JToggleButton toggleButton = new JToggleButton(mode.name());
            toggleButton.setEnabled(false);
            modeToggles.add(toggleButton);
            panel.add(toggleButton,
                      new Constraints(0, i).stretch().get());

            JButton viewButton = new JButton("View");
            if (i == modes.size() - 1)
                viewButton.addActionListener(e -> openModeDialog(mode, true));
            else
                viewButton.addActionListener(e -> openModeDialog(mode, false));
            panel.add(viewButton,
                      new Constraints(1, i).get());
        }

        panel.add(logField,
                  new Constraints(0, modes.size()).width(2).stretch().grow().get());
        panel.add(powerButton,
                  new Constraints(0, modes.size()+1).width(2).insets(10).get());

        frame.setContentPane(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    @Override
    public void setPowered(boolean flag)
    {
        String text = flag ? "Disable" : "Enable";
        powerButton.setText(text);
    }

    @Override
    public void dispose() { frame.dispose(); }

    public void openModeDialog(Mode mode, boolean isDefault) { new ModeDialog(frame, mode, isDefault, modeAwareGroup); }

    public static void addToLog(String text) { logField.setText(" " + text + "\n" + logField.getText()); }

    public void setVisible(boolean flag) { frame.setVisible(flag); }

    @Override
    public void setMode(Mode mode)
    {
        for(Enumeration<AbstractButton> e = modeToggles.getElements(); e.hasMoreElements();)
        {
            AbstractButton button = e.nextElement();
            if (button.getText().equals(mode.name()))
            {
                button.setSelected(true);
                return;
            }
            setModeless();
        }
    }

    @Override
    public void setModeless() { modeToggles.clearSelection(); }
}
