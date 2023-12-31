package com.github.kleonaut.network_beetle;

import javax.management.InstanceAlreadyExistsException;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.NoSuchElementException;

public class Window {

    // FIX: first time power button is pressed has no effect
    // TODO: use processes.getSelectedItem() instead of entryField.getItem() if that works
    // TODO: make fields inaccessible while app is runnning using setEnabled()
    // TODO: add network profile picker
    // TODO: use an image for power button

    private final App app;
    private final Dossier dossier;
    private final JFrame frame;
    private final ComboBoxModel<MicrosoftProcess> processes; // replace with custom class
    private final ComboBoxEditor entryField;
    private final JList<MicrosoftProcess> criteriaList;
    private final JButton powerButton;
    private boolean isPowered;

    public Window(App app, Dossier dossier)
    {
        this.app = app;
        this.dossier = dossier;

        // ---------------- Define GUI
        frame = new JFrame(app.NAME);

        processes = new DefaultComboBoxModel<>();
        JComboBox<MicrosoftProcess> jComboBox = new JComboBox<>(processes);
        jComboBox.setEditable(true);
        entryField = jComboBox.getEditor();
        entryField.setItem("");
        entryField.addActionListener(e -> addAction()); // Listener for pressing Enter

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> addAction());
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(e -> removeAction());

        criteriaList = new JList<>(dossier.getCriteria());
        criteriaList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        criteriaList.setLayoutOrientation(JList.VERTICAL);
        criteriaList.addListSelectionListener(selectAction);

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
                panel.add(criteriaList, c);
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
        String imageName = entryField.getItem().toString();
        MicrosoftProcess process = new MicrosoftProcess(imageName);
        try {
            dossier.addCriterion(process);
        } catch (InstanceAlreadyExistsException e) {
            Toolkit.getDefaultToolkit().beep();
            entryField.selectAll();
            return;
        }
        entryField.setItem("");
    }

    private void removeAction()
    {
        String imageName = entryField.getItem().toString();
        System.out.println("Image Name: "+imageName);
        MicrosoftProcess process = new MicrosoftProcess(imageName);
        try {
            dossier.removeCriterion(process);
        } catch (NoSuchElementException e) {
            Toolkit.getDefaultToolkit().beep();
        } finally {
            entryField.setItem("");
        }
    }

    public void powerAction()
    {
        app.togglePower();
    }

    public void togglePower()
    {
        isPowered = !isPowered;
        String buttonText = isPowered ? "Disable" : "Enable";
        powerButton.setText(buttonText);
    }

    ListSelectionListener selectAction = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting()) // another event is sent when user lets go of mouse, this lets me ignore it
            {
                MicrosoftProcess process = criteriaList.getSelectedValue();
                entryField.setItem(process);
            }
        }
    };

    public void reveal() { frame.setVisible(true); }
}
