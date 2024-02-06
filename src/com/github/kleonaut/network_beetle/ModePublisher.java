package com.github.kleonaut.network_beetle;

import javax.swing.Timer;
import java.util.ArrayList;
import java.util.List;

public class ModePublisher implements PowerObserver
{
    private final List<ModeObserver> observers = new ArrayList<>();
    private final Record record;
    private final Timer timer;
    private Mode nowMode = null;

    ModePublisher(Record record)
    {
        this.record = record;
        timer = new Timer(2000, e -> update());
        timer.setInitialDelay(0);
        timer.setRepeats(false);
    }

    public void add(ModeObserver observer) { observers.add(observer); }

    public void update()
    {
        List<String> tasks = Tasks.fetch();
        search:
        {
            for (Mode mode : record.modes())
                for (String condition : mode.conditions())
                    for (String task : tasks)
                        if (condition.equals(task))
                        {
                            publish(mode);
                            break search;
                        }
            publish(record.defaultMode());
        }
        timer.setInitialDelay(2000);
        timer.start();
    }

    private void publish(Mode mode)
    {
        System.out.println("publishing mode " + ((mode == null) ? "none" : mode.name()));
        if (nowMode != mode)
        {
            System.out.println("Mode is new, replacing " + ((nowMode == null) ? "none" : nowMode.name()));
            nowMode = mode;
            if (mode == null)
                for (ModeObserver observer : observers) observer.setModeless();
            else
                for (ModeObserver observer : observers) observer.setMode(mode);
        }
    }

    @Override
    public void setPowered(boolean flag)
    {
        if (flag)
            timer.start();
        else
        {
            timer.stop();
            publish(null);
        }
    }

    @Override
    public void setPowerBlocked(boolean flag) { }

    //public void setModeless() { publish(null); }
}
