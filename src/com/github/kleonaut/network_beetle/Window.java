package com.github.kleonaut.network_beetle;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class Window {

    // TODO: use processes.getSelectedItem() instead of entryField.getItem() if that works
    // TODO: make fields inaccessible while app is runnning using setEnabled();
    // TODO: use an image for power button

    private final App app;
    private final JFrame frame;
    private final ComboBoxEditor entryField;
    private final JList<String> jList;
    private final JButton powerButton;
    private final DefaultListModel<String> patterns;
    private final ComboBoxModel<String> processes;

    public Window(App app)
    {
        this.app = app;
        frame = new JFrame(app.NAME);

        // ---------------- Define GUI
        processes = new DefaultComboBoxModel<>();
        JComboBox<String> jComboBox = new JComboBox<String>(processes);
        jComboBox.setEditable(true);
        entryField = jComboBox.getEditor();
        entryField.setItem("");
        entryField.addActionListener(e -> addAction()); // Listener for pressing Enter

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> addAction());
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(e -> removeAction());

        patterns = new DefaultListModel<String>();
        jList = new JList<String>(patterns);
        jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jList.setLayoutOrientation(JList.VERTICAL);
        jList.addListSelectionListener(selectAction);

        powerButton = new JButton("Enable");
        powerButton.addActionListener(e -> powerAction());

        // ---------------- Lay Out GUI
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.gridy = 0;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;  c.weightx = 1;
                panel.add(jComboBox, c);
            c.gridx = 1; c.weightx = 0;
                panel.add(addButton, c);
            c.gridx = 2;
                panel.add(removeButton, c);
        c.gridy = 1;
            c.fill = GridBagConstraints.BOTH;
            c.gridx = 0; c.weighty = 1; c.gridwidth = 3;
                panel.add(jList, c);
        c.gridy = 2;
            c.fill = GridBagConstraints.NONE;
            c.weighty = 0;
                panel.add(powerButton, c);
        panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        frame.setContentPane(panel);

        // ---------------- Size Up Window
        frame.pack();
        frame.setMinimumSize(frame.getSize());
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);

        reveal();
    }

    private void addAction()
    {
        String pattern = ( (String) entryField.getItem() ).trim();
        if (pattern.isEmpty() || indexOf(pattern) != -1) // if no string or already in list
        {
            Toolkit.getDefaultToolkit().beep();
            entryField.selectAll();
            return;
        }
        patterns.addElement(pattern);
        entryField.setItem("");
    }

    private void removeAction()
    {
        String pattern = ( (String) entryField.getItem() ).trim();
        int index = indexOf(pattern);
        if (index == -1) // if not in list
        {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        patterns.remove(index);
        entryField.selectAll();
    }

    private int indexOf(String pattern) // index in patterns, returns -1 if not in patterns
    {
        for (int i = 0; i < patterns.size(); i++)
            if (pattern.equals(patterns.get(i)))
                return i;
        return -1;
    }

    public void powerAction()
    {
        app.setEnabled(!app.isEnabled());
        String buttonText = app.isEnabled() ? "Disable" : "Enable";
        powerButton.setText(buttonText);
    }

    ListSelectionListener selectAction = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting()) // another event is sent when user lets go of mouse, this lets me ignore it
            {
                String pattern = jList.getSelectedValue();
                entryField.setItem(pattern);
            }
        }
    };


    public void reveal() { frame.setVisible(true); }
}
