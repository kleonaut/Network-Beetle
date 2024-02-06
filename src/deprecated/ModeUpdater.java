package deprecated;

import com.github.kleonaut.network_beetle.*;
import com.github.kleonaut.network_beetle.Record;

import javax.swing.Timer;
import java.util.List;

// Observer design pattern
public class ModeUpdater implements PowerObserver
{
    private final Record record;
    private final ModePublisher modePublisher;
    private final Timer timer;

    ModeUpdater(Record record, ModePublisher modePublisher)
    {
        this.record = record;
        this.modePublisher = modePublisher;
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
                            modePublisher.setMode(mode);
                            break search;
                        }
            modePublisher.setMode(record.defaultMode());
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
