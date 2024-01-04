package deprecated;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

// deprecated, is for use with original RegimenScout
public class Tasklist
{
    private List<String> allTasks = new ArrayList<>();
    private List<String> novelTasks = new ArrayList<>();
    // dilimiter explanation:
        // any string of text that starts with [",] and ends with [\r\n"]
        // or any remaining string that starts with [",] and ends with [\r\n]
        // or any remaining ["] character
    private final Pattern dilimiter = Pattern.compile("(\",.*\r\n\")|(\",.*\r\n)|(\")");
    // command explanation:
        // [tasklist] returns list of all processes with the first process being the oldest one
        // [/fo csv] converts the output to csv format that is easy to parse
        // [/nh] removes the table header
        // [fi "sessionname ne Services"] filters away many vital processes that run at all times
    private final String command = "tasklist /fo csv /nh /fi \"sessionname ne Services\"";

    public void update()
    {
        Process tasklistProcess = null;

        try {
            tasklistProcess = Runtime.getRuntime().exec(command);
        } catch (IOException e) { throw new RuntimeException(e); }

        try (
        InputStream byteIn = tasklistProcess.getInputStream();
        Scanner scanner = new Scanner(byteIn).useDelimiter(dilimiter);
        ) {
            Iterator<String> oldTasks = this.allTasks.iterator();
            List<String> allTasks = new ArrayList<>(this.allTasks.size() + 10);
            List<String> novelTasks = new ArrayList<>();
            while (scanner.hasNext())
            {
                String cachedTask = scanner.next().toLowerCase(Locale.ENGLISH);
                allTasks.add(cachedTask);
                novelTasks.add(cachedTask);
                if (oldTasks.hasNext() && oldTasks.next().equals(cachedTask))
                    novelTasks.removeLast();
            }
            this.allTasks = allTasks;
            this.novelTasks = novelTasks;
        } catch (IOException e){ throw new RuntimeException(e); }
    }

    public List<String> novelTasks() {return List.copyOf(novelTasks); }
}
