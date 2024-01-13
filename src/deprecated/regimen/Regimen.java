package deprecated.regimen;

import java.util.List;

// immutable
public class Regimen
{
    private final String name;
    private final String netProfile;
    private final List<TaskCondition> conditions;
    private final int enterDelay = 0;
    private final int exitDelay = 0;
    private final boolean willNotifyOnEnter = false;

    public Regimen(String name, String profile, List<TaskCondition> conditions)
    {
        this.name = name;
        this.netProfile = profile;
        this.conditions = conditions;
    }

    public String name() { return name; }
    public String netProfile() { return netProfile; }
    public List<TaskCondition> conditions() { return List.copyOf(conditions); }


}
