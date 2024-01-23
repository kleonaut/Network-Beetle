package com.github.kleonaut.network_beetle;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ModeDialog extends JDialog
{
    private final JTextArea networkField;
    private final JTextArea conditionField;
    private final Mode mode;
    private final ModeAwareGroup modeGroup;

    ModeDialog(Frame owner, Mode mode, boolean isDefaultMode, ModeAwareGroup modeGroup)
    {
        super(owner, mode.name()+" Details", true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        this.mode = mode;
        this.modeGroup = modeGroup;

        networkField = new JTextArea(" "+ mode.netProfile().name());
        networkField.setEditable(false);
        networkField.setFocusable(false);

        JButton networkEditButton = new JButton("Edit");
        networkEditButton.addActionListener(e -> openNetworkDialog());

        conditionField = new JTextArea();
        conditionField.setText(stringFromList(mode.conditions()));
        conditionField.setEditable(false);
        conditionField.setFocusable(false);
        JScrollPane conditionPane = new JScrollPane(conditionField);
        conditionPane.getVerticalScrollBar().setUnitIncrement(5);
        conditionPane.getHorizontalScrollBar().setUnitIncrement(5);

        JButton conditionEditButton = new JButton("Edit");
        if (isDefaultMode)
            conditionEditButton.setEnabled(false);
        else
            conditionEditButton.addActionListener(e -> openConditionDialog());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        panel.add(networkField,
                  new Constraints(0, 0).stretch().get());
        panel.add(networkEditButton,
                  new Constraints(1, 0).get());
        panel.add(conditionPane,
                  new Constraints(0, 1).stretch().grow().get());
        panel.add(conditionEditButton,
                  new Constraints(1, 1).anchor(8).get());

        setContentPane(panel);
        setSize(270, 280);
        setLocationRelativeTo(owner);
        setVisible(true);
    }

    public void openNetworkDialog() { new NetworkDialog(this, mode.netProfile()); }
    public void setNetwork(NetProfile netProfile)
    {
        mode.setNetProfile(netProfile);
        networkField.setText(" "+netProfile.name());
        modeGroup.setModeless();
    }

    public void openConditionDialog() { new ConditionDialog(this, mode.conditions()); }
    public void setConditions(List<String> conditions)
    {
        mode.setConditions(conditions);
        conditionField.setText(stringFromList(conditions));
        modeGroup.setModeless();
    }

    private String stringFromList(List<String> list)
    {
        StringBuilder builder = new StringBuilder();
        for (String item : list)
            builder.append(" ").append(item).append("\n");
        return builder.toString();
    }
}
