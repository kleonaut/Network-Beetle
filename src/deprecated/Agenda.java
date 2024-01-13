package deprecated;

import deprecated.regimen.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Agenda
{
    private Regimen nowRegimen;
    private final List<TaskCondition> conditions;

    public Agenda(List<Regimen> regimens)
    {
        List<TaskCondition> conditions = new ArrayList<>();
        for(Regimen regimen : regimens)
            conditions.addAll((Collection<TaskCondition>)regimen.conditions());
        this.conditions = List.copyOf(conditions);
    }

    public void confirmCondition(TaskCondition condition)
    {

    }

    public List<TaskCondition> conditions() { return conditions; }


}
