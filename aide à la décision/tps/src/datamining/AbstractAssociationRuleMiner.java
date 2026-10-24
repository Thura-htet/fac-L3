package datamining;

import java.util.Set;

import modelling.BooleanVariable;

public abstract class AbstractAssociationRuleMiner implements AssociationRuleMiner
{
    private BooleanDatabase db;

    public AbstractAssociationRuleMiner(BooleanDatabase db)
    {
        this.db = db;
    }

    @Override
    public BooleanDatabase getDatabase()
    {
        return this.db;
    }

    public static float frequency(Set<BooleanVariable> items, Set<Itemset> itemSets) throws IllegalArgumentException
    {
        return 0;
    }

    public static float confidence(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion, Set<Itemset> itemSet)
    {
        return 0;
    }
}