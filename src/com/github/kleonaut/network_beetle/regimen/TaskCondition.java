package com.github.kleonaut.network_beetle.regimen;

// immutable
public class TaskCondition
{
    private final String taskName;
    private final Regimen regimen;

    public TaskCondition(Regimen regimen, String taskName)
    {
        this.regimen = regimen;
        this.taskName = taskName;
    }

    public String taskName() { return taskName; }
    public Regimen regimen() { return regimen; }
}
