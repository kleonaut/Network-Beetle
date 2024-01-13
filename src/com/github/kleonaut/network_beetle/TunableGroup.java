package com.github.kleonaut.network_beetle;

import java.util.ArrayList;
import java.util.List;

public class TunableGroup
{
    private final List<Tunable> items = new ArrayList<>();
    private final Record record;
    private Tune nowTune = null;

    public TunableGroup(Record record)
    {
        this.record = record;
    }

    public void add(Tunable item) {items.add(item); }

    public void setTune(int index)
    {
        if(index >= record.tunes.size() || index < 0)
            setNoTune();
        setTune(record.tunes.get(index));
    }

    public void setTune(Tune tune)
    {
        if (!record.tunes.contains(tune))
            setNoTune();
        nowTune = tune;
        for (Tunable item : items) item.setTune(tune);
    }

    public void setNoTune()
    {
        nowTune = null;
        for (Tunable item : items) item.setNoTune();
    }
    public Tune nowTune() { return nowTune; }
    public int nowTuneIndex() { return record.tunes.indexOf(nowTune); }
}
