package com.github.kleonaut.network_beetle;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TuneDetailsWindow
{
    private final JDialog dialog;
    private final JTextArea networkArea;
    private final JTextArea conditionArea;
    private final Tune tune;

    TuneDetailsWindow(Frame owner, Tune tune)
    {
        this.tune = tune;

        dialog = new JDialog(owner,tune.name+" Details", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        networkArea = new JTextArea(" "+tune.netProfile.name());
        networkArea.setEditable(false);
        networkArea.setFocusable(false);

        JButton networkEditButton = new JButton("Edit");
        networkEditButton.addActionListener(e -> openNetworkEdit());

        conditionArea = new JTextArea();
        StringBuilder builder = new StringBuilder();
        for (String condition : tune.conditions)
            builder.append(" ").append(condition).append("\n");
        conditionArea.setText(builder.toString());
        conditionArea.setEditable(false);
        conditionArea.setFocusable(false);

        JButton conditionEditButton = new JButton("Edit");
        if (tune.isDefault)
            conditionEditButton.setEnabled(false);
        else
            conditionEditButton.addActionListener(e -> openConditionEdit());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        panel.add(networkArea,
                  new Constraints(0, 0).stretch().get());
        panel.add(networkEditButton,
                  new Constraints(1, 0).get());
        panel.add(conditionArea,
                  new Constraints(0, 1).stretch().grow().get());
        panel.add(conditionEditButton,
                  new Constraints(1, 1).anchor(8).get());

        dialog.setContentPane(panel);
        dialog.setSize(270, 280);
        dialog.setLocationRelativeTo(owner);
        dialog.setVisible(true);
    }

    public void openNetworkEdit() { new NetworkEditWindow(dialog, this, tune.netProfile); }
    public void setNetwork(NetProfile netProfile)
    {
        tune.netProfile = netProfile;
        networkArea.setText(" "+netProfile.name());
        //save
    }

    public void openConditionEdit() { new ConditionEditWindow(dialog, this, tune.conditions); }
    public void setConditions(List<String> conditions)
    {
        tune.conditions = conditions;
        StringBuilder builder = new StringBuilder();
        for (String condition : conditions)
            builder.append(" ").append(condition).append("\n");
        conditionArea.setText(builder.toString());
    }
}
