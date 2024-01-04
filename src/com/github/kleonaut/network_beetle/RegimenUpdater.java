package com.github.kleonaut.network_beetle;

import com.github.kleonaut.network_beetle.regimen.Regimen;
import com.github.kleonaut.network_beetle.regimen.TaskCondition;

import javax.swing.Timer;
import java.util.List;

public class RegimenUpdater
{
    private final Timer timer = new Timer(2000, e -> update());
    private final List<Regimen> regimens;
    private Regimen nowRegimen;

    public RegimenUpdater(List<Regimen> regimens, App app) {
        this.regimens = regimens;
    }

    public void togglePower()
    {
        if (timer.isRunning()) timer.stop();
        else timer.restart();
    }

    public void update()
    {
        System.out.println(NetShell.fetchInfo(NetShell.Inquery.NEARBY_NETWORKS));
        for (String task : Tasks.fetch())
            for (Regimen regimen : regimens)
                for (TaskCondition condition : regimen.conditions())
                    if (task.equals(condition.taskName()))
                    {
                        //try {
                        //    setRegimen(regimen);
                        //} catch (Exception e) {
                        //    // ...
                        //}
                        //return;
                    }
        //setRegimen(regimens.getLast()); // last regimen is default
    }

    private void setRegimen(Regimen regimen)
    {
//        if(regimen != nowRegimen)
//        {
//
//        }
    }
}
