package deprecated;

import com.github.kleonaut.network_beetle.App;

import javax.swing.*;
import java.awt.*;

public class Window {

    // TODO: make fields inaccessible while app is runnning using setEnabled()
    // TODO: add network profile picker
    // TODO: use an image for power button

    private final App app;
    private final JFrame frame;
    private final CustomJComboBox conditionEditor;
    private final JButton powerButton;
    private boolean isPowered;

    public Window(App app)
    {
        this.app = app;

        // ---------------- Define GUI
        frame = new JFrame(app.NAME);

        conditionEditor = new CustomJComboBox();

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> conditionEditor.addItem());
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(e -> conditionEditor.removeItem());

        powerButton = new JButton("Enable");
        powerButton.addActionListener(e -> app.setPowered(!app.isPowered()));

        // ---------------- Lay Out GUI
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.gridy = 0;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;  c.weightx = 1;
                panel.add(conditionEditor, c);
            c.gridx = 1; c.weightx = 0;
                panel.add(addButton, c);
            c.gridx = 2;
                panel.add(removeButton, c);
        c.gridy = 1;
            c.fill = GridBagConstraints.BOTH;
            c.gridx = 0; c.weighty = 1; c.gridwidth = 3;
                panel.add(conditionEditor.getJList(), c);
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

    public void togglePower()
    {
        isPowered = !isPowered;
        String buttonText = isPowered ? "Disable" : "Enable";
        powerButton.setText(buttonText);
    }

    public void reveal() { frame.setVisible(true); }
    public void kill() { frame.dispose(); }
}
