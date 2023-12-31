package com.github.kleonaut.network_beetle;

import javax.swing.DefaultListModel;
import java.util.NoSuchElementException;
import javax.management.InstanceAlreadyExistsException;

public class Dossier
{
    private NetworkProfile targetProfile = NetworkProfile.OFFLINE;
    private NetworkProfile fallbackProfile = NetworkProfile.OFFLINE;
    private final DefaultListModel<MicrosoftProcess> criteria = new DefaultListModel<>();

    public void setTargetProfile(NetworkProfile targetProfile) { this.targetProfile = targetProfile; }
    public void setFallbackProfile(NetworkProfile fallbackProfile) { this.fallbackProfile = fallbackProfile; }

    public NetworkProfile targetProfile() { return targetProfile; }
    public NetworkProfile fallbackProfile() { return fallbackProfile; }
    public DefaultListModel<MicrosoftProcess> getCriteria() { return criteria; }
    public MicrosoftProcess criterion(int index) { return criteria.get(index); }
    public int criteriaSize() { return criteria.getSize(); }

    public void removeCriterion(MicrosoftProcess process) throws NoSuchElementException
    {
        for (int i = 0; i < criteria.size(); i++)
            if (criteria.get(i).isSameImageName(process))
            {
                criteria.removeElementAt(i);
                return;
            }
        throw new NoSuchElementException("Process provided is not in a criteria list");
    }

    public void addCriterion(MicrosoftProcess process) throws InstanceAlreadyExistsException
    {
        for (int i = 0; i < criteria.size(); i++)
            if (criteria.get(i).isSameImageName(process))
                throw new InstanceAlreadyExistsException("Process provided is already in a criteria list");
        criteria.addElement(process);
    }
}
