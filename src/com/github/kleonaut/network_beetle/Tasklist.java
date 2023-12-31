package com.github.kleonaut.network_beetle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Tasklist {
    boolean hasProcess(MicrosoftProcess process) throws IOException
    {
        String command = "tasklist /fi \"imagename eq "+process.imageName()+"\" /fo csv /nh";
        Process tasklistProcess = Runtime.getRuntime().exec(command);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(tasklistProcess.getInputStream()));
             Scanner scanner = new Scanner(in.readLine()))
        {
            if (scanner.next().equals("INFO:"))
                return false;
        }
        return true;
    }
}