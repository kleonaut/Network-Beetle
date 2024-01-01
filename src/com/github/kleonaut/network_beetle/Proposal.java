package com.github.kleonaut.network_beetle;

import javax.swing.DefaultListModel;
import java.util.NoSuchElementException;
import javax.management.InstanceAlreadyExistsException;

public class Proposal
{
    private NetProfile target = NetProfile.OFFLINE;
    private NetProfile fallback = NetProfile.OFFLINE;
    private final DefaultListModel<String> criteria = new DefaultListModel<>();

    public void setTarget(NetProfile target) { this.target = target; }
    public void setFallback(NetProfile fallback) { this.fallback = fallback; }

    public NetProfile getTarget() { return target; }
    public NetProfile getFallback() { return fallback; }
    public DefaultListModel<String> criteriaModel() { return criteria; }
    public String criterion(int index) { return criteria.get(index); }
    public int criteriaSize() { return criteria.size(); }

    public void removeCriterion(String processName) throws NoSuchElementException
    {
        for (int i = 0; i < criteria.size(); i++)
            if (criteria.get(i).equals(processName))
            {
                criteria.removeElementAt(i);
                return;
            }
        throw new NoSuchElementException("Process provided is not in a criteria list");
    }

    public void addCriterion(String processName) throws InstanceAlreadyExistsException
    {
        for (int i = 0; i < criteria.size(); i++)
            if (criteria.get(i).equals(processName))
                throw new InstanceAlreadyExistsException("Process provided is already in a criteria list");
        criteria.addElement(processName);
    }
}
