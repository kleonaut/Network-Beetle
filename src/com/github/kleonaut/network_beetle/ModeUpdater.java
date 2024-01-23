package com.github.kleonaut.network_beetle;

import javax.swing.Timer;
import java.util.List;

public class ModeUpdater implements Powerable
{
    private final Record record;
    private final ModeAwareGroup modeAwareGroup;
    private final Timer timer;

    ModeUpdater(Record record, ModeAwareGroup modeAwareGroup)
    {
        this.record = record;
        this.modeAwareGroup = modeAwareGroup;
        timer = new Timer(2000, e -> updateMode());
        timer.setRepeats(false);
    }

    public void updateMode()
    {
        List<String> tasks = Tasks.fetch();
        search:
        {
            for (Mode mode : record.modes(false))
                for (String condition : mode.conditions())
                    for (String task : tasks)
                        if (condition.equals(task))
                        {
                            modeAwareGroup.setMode(mode);
                            break search;
                        }
            modeAwareGroup.setMode(record.defaultMode());
        }
        timer.start();
    }

    @Override
    public void setPowered(boolean flag)
    {
        if (flag)
            timer.start();
        else
            timer.stop();
    }
}
