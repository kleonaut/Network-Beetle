package com.github.kleonaut.network_beetle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// immutable / unmodifiable
public class NoveltyList
{
    private final List<String> allItems;
    private final List<String> novelItems;

    public NoveltyList()
    {
        this.allItems = new ArrayList<>();
        this.novelItems = new ArrayList<>();
    }

    private NoveltyList(List<String> allItems, List<String> novelItems)
    {
        this.allItems = List.copyOf(allItems);
        this.novelItems = List.copyOf(novelItems);
    }

    public NoveltyList updated(Iterator<String> collection)
    {
        List<String> allItems = new ArrayList<>(this.allItems.size()+10);
        List<String> novelItems = new ArrayList<>();
        Iterator<String> oldItems = this.allItems.iterator();

        // iterate through items in collection
        while (collection.hasNext())
        {
            // cache one item from collection
            String cachedItem = collection.next();
            // add it to both lists; we assume the cached item is novel
            allItems.add(cachedItem);
            novelItems.add(cachedItem);
            // check if previous allItems list (aka oldItems) had the same item at this position
            if (oldItems.hasNext() && oldItems.next().equals(cachedItem)) {
                // if so, the item we added was not novel, so remove it
                novelItems.removeLast();
            }
        }
        return new NoveltyList(allItems, novelItems);
    }

    public List<String> novelItems() { return novelItems; }
}
