package datamining;

import java.util.Set;

import modelling.BooleanVariable;

public class Itemset 
{
    private Set<BooleanVariable> items;
    private float frequency;


    public Itemset(Set<BooleanVariable> items, float frequency)
    {
        this.items = items;
        this.frequency = frequency;
    }

    Set<BooleanVariable> getItems()
    {
        return this.items;
    }
    
    float getFrequency()
    {
        return this.frequency;
    }
}