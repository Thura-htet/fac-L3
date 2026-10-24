package datamining;

import java.util.Collection;
import java.util.Set;
import java.util.SortedSet;
import modelling.BooleanVariable;


public class Aprori extends AbstractItemsetMiner
{
    public Aprori(BooleanDatabase db)
    {
        super(db);
    }

    Set<Itemset> frequentSingletons(float frequency)
    {
        return null;
    }

    public static SortedSet<BooleanVariable> combine(SortedSet<BooleanVariable> set1, SortedSet<BooleanVariable> set2)
    {
        return null;
    }

    public static boolean allSubsetsFrequent(Set<BooleanVariable> items, Collection<SortedSet<BooleanVariable>> itemSets)
    {
        return true;
    }

    @Override 
    public Set<Itemset> extract(float minFrequency)
    {
        return null;
    }

}