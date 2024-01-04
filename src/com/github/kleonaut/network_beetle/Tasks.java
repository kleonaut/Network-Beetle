package com.github.kleonaut.network_beetle;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// not thread safe because of Matcher
// do not call from worker threads
public class Tasks
{
    private static final Pattern SYS_PATTERN = Pattern.compile("C:\\\\Windows\\\\");
    private static final Pattern EXE_PATTERN = Pattern.compile("[^\\\\]*\\.exe");
    private static final Matcher exeMatcher = EXE_PATTERN.matcher("");
    private static final Matcher sysMatcher = SYS_PATTERN.matcher("");

    private Tasks() {};

    public static List<String> fetch()
    {
        List<String> taskNames = new ArrayList<>();
        ProcessHandle[] handles = getHandles();
        for (ProcessHandle handle : handles)
            taskNames.add(taskNameOf(handle));
        return taskNames;
    }

    private static ProcessHandle[] getHandles()
    {
        return ProcessHandle.allProcesses()
                // processes that have a 'command' property (executable path)
                .filter(handle -> handle.info().command().isPresent())
                // processes that are not in 'C:\Windows\' directory
                .filter(handle -> !sysMatcher.reset(handle.info().command().get()).find())
                // convert stream to array
                .toArray(ProcessHandle[]::new);
    }

    private static String taskNameOf(ProcessHandle handle)
    {
        // supply full executable path to matcher to parse out just the executable name
        exeMatcher.reset(handle.info().command().get());
        if (exeMatcher.find())
            // matcher.group() is the matching string
            return exeMatcher.group().toLowerCase(Locale.ENGLISH);
        throw new MatchException("Match not found", null);
    }

    private static ProcessHandle handleOf(String task) throws Exception
    {
        ProcessHandle[] handles = getHandles();
        for (ProcessHandle handle : handles)
            if (taskNameOf(handle).equals(task))
                return handle;
        throw new Exception("Handle with executable name "+task+" not found");
    }
}
