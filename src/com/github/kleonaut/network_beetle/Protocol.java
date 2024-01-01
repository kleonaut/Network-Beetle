package com.github.kleonaut.network_beetle;

public class Protocol
{
    private final NetProfile target;
    private final NetProfile fallback;
    private final String[] criteria;

    public Protocol(Proposal proposal)
    {
        target = proposal.getTarget();
        fallback = proposal.getFallback();
        criteria = new String[proposal.criteriaSize()];
        proposal.criteriaModel().copyInto(criteria);
    }

    public Protocol()
    {
        target = NetProfile.OFFLINE;
        fallback = NetProfile.OFFLINE;
        criteria = new String[4];
        for (int i = 0; i < criteria.length; i++)
            criteria[i] = "aces"+i+".exe";
    }

    public NetProfile getTarget() { return target; }
    public NetProfile getFallback() { return fallback; }
    public String[] criteria() { return criteria.clone(); }
}
