package com.github.kleonaut.network_beetle;

import javax.swing.*;
import java.awt.GridBagLayout;
import java.util.Enumeration;

public class OverviewWindow implements Powerable, Disposable, Tunable
{
    // TODO: figure out how to handle JDK bug 8221452: setMinimumSize() does not take into account display scaling %
        // current solution: have no miminum size
    private final JFrame frame = new JFrame("Network Beetle");
    private final JButton powerButton = new JButton("Enable");
    private static JTextArea logArea;
    private final Record record;
    private final ButtonGroup tuneToggles = new ButtonGroup();

    public OverviewWindow(PowerableGroup powerableGroup, Record record)
    {
        this.record = record;

        logArea = new JTextArea(3, 25);
        logArea.setEditable(false);
        logArea.setFocusable(false);
        powerButton.addActionListener(e -> powerableGroup.toggle());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        for (int i = 0, y = record.tunes.size()-1; i < record.tunes.size(); i++, y--)
        {
            Tune tune = record.tunes.get(i);

            JToggleButton toggleButton = new JToggleButton(tune.name);
            toggleButton.setEnabled(false);
            tuneToggles.add(toggleButton);
            panel.add(toggleButton,
                      new Constraints(0, y).stretch().get());

            JButton viewButton = new JButton("View");
            viewButton.addActionListener(e -> openTuneDetails(tune));
            panel.add(viewButton,
                      new Constraints(1, y).get());
        }

        panel.add(logArea,
                  new Constraints(0, record.tunes.size()).width(2).stretch().grow().get());
        panel.add(powerButton,
                  new Constraints(0, record.tunes.size()+1).width(2).insets(10).get());

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

    public void openTuneDetails(Tune tune) { new TuneDetailsWindow(frame, tune); }

    public static void addToLog(String text) { logArea.setText(text+"\n"+logArea.getText()); }

    public void setVisible(boolean flag) { frame.setVisible(flag); }

    @Override
    public void setTune(Tune tune)
    {
        for(Enumeration<AbstractButton> e = tuneToggles.getElements(); e.hasMoreElements();)
        {
            AbstractButton button = e.nextElement();
            if (button.getText().equals(tune.name))
            {
                button.setSelected(true);
                return;
            }
            setNoTune();
        }
    }

    @Override
    public void setNoTune() { tuneToggles.clearSelection(); }
}
