package com.github.kleonaut.network_beetle;

import javax.management.InstanceAlreadyExistsException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import java.awt.Toolkit;
import java.util.NoSuchElementException;

public class CustomJComboBox extends JComboBox<String>
{
    private final CustomJList finalizedField;

    CustomJComboBox()
    {
        super(new DefaultComboBoxModel<>());
        finalizedField = new CustomJList(this);

        setEditable(true);
        setText("");
        getEditor().addActionListener(e -> addItem()); // Listener for pressing Enter
    }

    public CustomJList getJList() { return finalizedField; }

    public void setText(String text) { getEditor().setItem(text); }
    private void selectText() { getEditor().selectAll(); }

    public void addItem()
    {
        try {
            String text = (String)getEditor().getItem();
            text = text.trim();
            finalizedField.add(text);
            setText("");
        } catch (InstanceAlreadyExistsException e) {
            Toolkit.getDefaultToolkit().beep();
            selectText();
        }
    }

    public void removeItem()
    {
        String text = (String)getEditor().getItem();
        try {
            finalizedField.remove(text);
            setText("");
        } catch (NoSuchElementException e) {
            Toolkit.getDefaultToolkit().beep();
            selectText();
        }
    }

}
