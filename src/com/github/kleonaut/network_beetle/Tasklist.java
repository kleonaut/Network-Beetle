package com.github.kleonaut.network_beetle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Tasklist
{
    private final String command = "tasklist /fo csv /nh";
    // dilimiter explanation
        // any string of text that starts with ", and ends with \r\n"
        // or any remaining string that starts with ", and ends with \r\n
        // or any remaining " character
    private final Pattern dilimiter = Pattern.compile("(\",.*\r\n\")|(\",.*\r\n)|(\")");

    public boolean isMatchingCriteria(String[] criteria)
    {
        List<String> processList = new ArrayList<>();
        Process tasklistProcess = null;
        try {
            tasklistProcess = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (
            InputStream inSt = tasklistProcess.getInputStream();
            Scanner scanner = new Scanner(inSt);
            ) {
            scanner.useDelimiter(dilimiter);
            while (scanner.hasNext())
                processList.add(scanner.next());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        //for (int i = 0; i < processList.size(); i++)
        //    System.out.println("Item "+i+": "+processList.get(i));
        return false;
    }

    private void safeClose(Closeable closable)
    {
        if (closable != null)
            try {
                closable.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
}
