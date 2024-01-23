package com.github.kleonaut.network_beetle;

import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class NetworkDialog extends JDialog
{
    private final JTextArea nominatedNetwork;
    private final JList<String> networkList;
    private final ModeDialog returnWindow;
    private static final Timer timer = new Timer(2000, null);

    NetworkDialog(JDialog owner, ModeDialog returnWindow, NetProfile assignedProfile)
    {
        super(owner, "Assign Network Profile", true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        this.returnWindow = returnWindow;

        nominatedNetwork = new JTextArea(" "+assignedProfile.name());
        nominatedNetwork.setEditable(false);
        nominatedNetwork.setFocusable(false);

        networkList = new JList<>(new DefaultListModel<>());
        networkList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        networkList.addListSelectionListener(e -> nominateSelection());
        networkList.setEnabled(false);
        ((DefaultListModel<String>)networkList.getModel()).addElement(" Fetching...");

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

        setContentPane(panel);
        setSize(230, 250);
        setLocationRelativeTo(owner);

        timer.addActionListener(e -> updateNetworkList());
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                timer.stop();
                for (ActionListener listener : timer.getActionListeners())
                    timer.removeActionListener(listener);
            }
        });
        timer.setInitialDelay(400);
        timer.start();

        setVisible(true);
    }

    public void updateNetworkList()
    {
        networkList.setEnabled(true);

        String selectedNetwork = networkList.getSelectedValue();
        int selectedIndex = -1;

        DefaultListModel<String>listModel = (DefaultListModel<String>)networkList.getModel();
        listModel.clear();
        listModel.addElement(" " + NetProfile.STAY.name());
        listModel.addElement(" " + NetProfile.DISCONNECT.name());

        for (NetProfile profile : Networks.fetchAllProfiles())
            listModel.addElement(" " + profile.name());

        for (int i = 0; i < listModel.size(); i++)
            if (listModel.get(i).equals(selectedNetwork))
                selectedIndex = i;

        networkList.setSelectedIndex(selectedIndex);
    }

    public void nominateSelection() { nominatedNetwork.setText(networkList.getSelectedValue()); }
    public void confirmSelection()
    {
        returnWindow.setNetwork(new NetProfile(nominatedNetwork.getText().trim()));
        dispose();
    }
}
