package com.github.kleonaut.network_beetle;

import java.util.Locale;

public class MicrosoftProcess
{
    public final String IMAGE_NAME;
    public final int PID;

    public MicrosoftProcess(String imageName) { this(imageName, -1); }
    public MicrosoftProcess(String imageName, int pid)
    {
        this.PID = pid;
        this.IMAGE_NAME = imageName.toLowerCase(Locale.ENGLISH).trim();
    }
    public boolean isSameImageName(MicrosoftProcess other)
    {
        return this.IMAGE_NAME.equals(other.IMAGE_NAME);
    }
    public boolean isSameProcess(MicrosoftProcess other)
    {
        return (this.isSameImageName(other) && this.PID == other.PID);
    }

    @Override
    public String toString() {
        return IMAGE_NAME;
    }
}
