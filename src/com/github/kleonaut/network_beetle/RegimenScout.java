package com.github.kleonaut.network_beetle;

import javax.swing.SwingWorker;
import java.util.List;
import java.util.concurrent.ExecutionException;

// multithreaded solution, deprecated after much faster ProcessHandler replaced tasklist.exe
public class RegimenScout extends SwingWorker<Regimen, Void>
{
    private final Tasklist tasklist = new Tasklist();
    private final List<Regimen> regimens;
    private final App app;

    RegimenScout(List<Regimen> regimens, App app)
    {
        this.regimens = regimens;
        this.app = app;
    }

    @Override
    protected Regimen doInBackground() throws Exception
    {
        for (String task : tasklist.novelTasks())
        {
            for (Regimen regimen : regimens)
                for (String condition : regimen.conditions())
                    if (task.equals(condition))
                        return regimen;
        }
        return regimens.getLast(); // last regimen is default
    }

    @Override
    protected void done()
    {
        try {
            app.setRegimen(get());
        } catch (InterruptedException | ExecutionException e) { throw new RuntimeException(e); }

    }

}
