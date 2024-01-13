package com.github.kleonaut.network_beetle;

import javax.swing.*;
import java.awt.*;

public class NetworkEditWindow
{
    private final JDialog dialog;
    private final JTextArea nominatedNetwork;
    private final JList<String> networkList;
    private final TuneDetailsWindow returnWindow;

    NetworkEditWindow(JDialog owner, TuneDetailsWindow returnWindow, NetProfile nowProfile)
    {
        this.returnWindow = returnWindow;
        dialog = new JDialog(owner, "Assign Network Profile", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        nominatedNetwork = new JTextArea(" "+nowProfile.name());
        nominatedNetwork.setEditable(false);
        nominatedNetwork.setFocusable(false);

        networkList = new JList<>(new DefaultListModel<>());
        networkList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        networkList.addListSelectionListener(e -> nominateSelection());
        DefaultListModel<String>listModel = (DefaultListModel<String>)networkList.getModel();
        listModel.addElement("Option 1");
        listModel.addElement("Option 2");
        listModel.addElement("Option 3");

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> confirmSelection());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        panel.add(nominatedNetwork,
                  new Constraints(0, 0).stretch().insets(5, 10, 5, 5).get());
        panel.add(networkList,
                  new Constraints(0, 1).stretch().grow().get());
        panel.add(confirmButton,
                  new Constraints(0, 2).anchor(6).get());

        dialog.setContentPane(panel);
        dialog.setSize(230, 250);
        dialog.setLocationRelativeTo(owner);
        dialog.setVisible(true);
    }

    public void nominateSelection() { nominatedNetwork.setText(" "+networkList.getSelectedValue()); }
    public void confirmSelection()
    {
        returnWindow.setNetwork(new NetProfile(nominatedNetwork.getText().trim()));
        dialog.dispose();
    }
}
