package com.github.kleonaut.network_beetle;

import javax.swing.Timer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegimenScout2
{
    private static final Pattern SYS_PATTERN = Pattern.compile("C:\\\\Windows\\\\");
    private static final Pattern EXE_PATTERN = Pattern.compile("[^\\\\]*\\.exe");
    private final Matcher exeMatcher = EXE_PATTERN.matcher("");
    private final Matcher sysMatcher = SYS_PATTERN.matcher("");
    private final Timer timer = new Timer(2000, e -> updateRegimen());
    private final List<Regimen> regimens;
    private final App app;

    public RegimenScout2(List<Regimen> regimens, App app) {
        this.regimens = regimens;
        this.app = app;
    }

    public void togglePower()
    {
        if (timer.isRunning()) timer.stop();
        else timer.restart();
    }

    public void updateRegimen() {
        for (String task : fetchTasks())
            for (Regimen regimen : regimens)
                for (String condition : regimen.conditions())
                    if (task.equals(condition))
                    {
                        app.setRegimen(regimen);
                        return;
                    }
        app.setRegimen(regimens.getLast()); // last regimen is default
    }

    private List<String> fetchTasks()
    {
        List<String> tasks = new ArrayList<>();
        ProcessHandle[] handles = getHandles();
        for (ProcessHandle handle : handles)
            tasks.add(taskOf(handle));
        return tasks;
    }

    private ProcessHandle[] getHandles()
    {
        return ProcessHandle.allProcesses()
                // processes that have a 'command' property (executable path)
                .filter(handle -> handle.info().command().isPresent())
                // processes that are not in 'C:\Windows\' directory
                .filter(handle -> !sysMatcher.reset(handle.info().command().get()).find())
                // convert stream to array
                .toArray(ProcessHandle[]::new);
    }

    private String taskOf(ProcessHandle handle)
    {
        // supply full executable path to matcher to parse out just the executable name
        exeMatcher.reset(handle.info().command().get());
        if (exeMatcher.find())
            // matcher.group() is the matching string
            return exeMatcher.group().toLowerCase(Locale.ENGLISH);
        throw new MatchException("Match not found", null);
    }

    private ProcessHandle handleOf(String task) throws Exception
    {
        ProcessHandle[] handles = getHandles();
        for (ProcessHandle handle : handles)
            if (taskOf(handle).equals(task))
                return handle;
        throw new Exception("Handle with executable name "+task+" not found");
    }
}
