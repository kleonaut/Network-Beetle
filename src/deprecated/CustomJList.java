package deprecated;

import javax.management.InstanceAlreadyExistsException;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.NoSuchElementException;

public class CustomJList extends JList<String> implements ListSelectionListener
{
    private final DefaultListModel<String> list;
    private final CustomJComboBox editField;

    public CustomJList(CustomJComboBox editField)
    {
        super(new DefaultListModel<>());
        list = (DefaultListModel<String>) super.getModel();
        this.editField = editField;

        super.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        super.setLayoutOrientation(JList.VERTICAL);
        super.addListSelectionListener(this);
    }

    public void add(String item) throws InstanceAlreadyExistsException
    {
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).equals(item))
                throw new InstanceAlreadyExistsException("Process provided is already in a condition list");
        list.addElement(item);
    }

    public void remove(String item) throws NoSuchElementException
    {
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).equals(item))
            {
                list.removeElementAt(i);
                return;
            }
        throw new NoSuchElementException("Process provided is not in a condition list");
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) // another event is sent when user lets go of mouse, this lets me ignore it
            editField.setText(getSelectedValue());
    }
}
