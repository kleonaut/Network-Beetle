package com.github.kleonaut.network_beetle;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ConditionEditWindow
{
    // TODO: list width changes when contents change because of button text update, list width should be fixed

    private final JDialog dialog;
    private final JList<String> nominatedCondtions;
    private final JList<String> possibleConditions;
    private final TuneDetailsWindow returnWindow;

    private final JButton transferButton;
    private JList<String> targetList;
    private JList<String> sourceList;

    private boolean isSelecting;

    ConditionEditWindow(JDialog owner, TuneDetailsWindow returnWindow, List<String> nowConditions)
    {
        this.returnWindow = returnWindow;
        dialog = new JDialog(owner, "Assign Conditions", true);

        nominatedCondtions = new JList<>(new DefaultListModel<>());
        nominatedCondtions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        nominatedCondtions.addListSelectionListener(selectAction);
        DefaultListModel<String>listModel = (DefaultListModel<String>)nominatedCondtions.getModel();
        for (String condition : nowConditions)
            listModel.addElement(condition);

        transferButton = new JButton("=");
        transferButton.setEnabled(false);
        transferButton.addActionListener(e -> performTransfer());

        possibleConditions = new JList<>(new DefaultListModel<>());
        possibleConditions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        possibleConditions.addListSelectionListener(selectAction);
        listModel = (DefaultListModel<String>)possibleConditions.getModel();
        listModel.addElement("one.exe");
        listModel.addElement("two.exe");
        listModel.addElement("three.exe");

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> confirmChanges());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        panel.add(nominatedCondtions,
                  new Constraints(0, 0).stretch().grow().get());
        panel.add(transferButton,
                  new Constraints(1, 0).get());
        panel.add(possibleConditions,
                  new Constraints(2, 0).stretch().grow().get());
        panel.add(confirmButton,
                  new Constraints(0, 1).width(3).anchor(6).get());

        dialog.setContentPane(panel);
        dialog.setSize(450, 300);
        dialog.setLocationRelativeTo(owner);
        dialog.setVisible(true);
    }

    public void performTransfer()
    {
        if (sourceList == null || targetList == null) return;

        String item = sourceList.getSelectedValue();
        DefaultListModel<String> model = (DefaultListModel<String>)sourceList.getModel();
        model.removeElementAt(sourceList.getSelectedIndex());

        model = (DefaultListModel<String>)targetList.getModel();
        model.addElement(item); // NOTE: when target list has no items, this line invokes target's ListSelectionListener

        sourceList = null;
        targetList = null;
        transferButton.setText("=");
        transferButton.setEnabled(false);
    }

    ListSelectionListener selectAction = new ListSelectionListener()
    {
        @Override
        public void valueChanged(ListSelectionEvent e)
        {
            if (e.getValueIsAdjusting()) return;
            if (isSelecting) return;
            isSelecting = true;
            sourceList = (JList<String>)e.getSource();
            if (sourceList == nominatedCondtions)
            {
                targetList = possibleConditions;
                transferButton.setText(">");
            }
            else
            {
                targetList = nominatedCondtions;
                transferButton.setText("<");
            }
            targetList.clearSelection();
            transferButton.setEnabled(true);
            isSelecting = false;
        }
    };

    public void confirmChanges()
    {
        List<String> conditions = new ArrayList<>();
        for (int i = 0; i < nominatedCondtions.getModel().getSize(); i++)
            conditions.add(nominatedCondtions.getModel().getElementAt(i));
        returnWindow.setConditions(conditions);
        dialog.dispose();
    }
}
