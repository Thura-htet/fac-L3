package datamining;

import java.util.HashSet;
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
        for (Itemset itemSet : itemSets)
            if (itemSet.getItems().equals(items))
                return itemSet.getFrequency();

        throw new IllegalArgumentException("Given set of items is not in the set of Itemsets.");
    }
    
    public static float confidence(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion, Set<Itemset> itemSet)
    {
        Set<BooleanVariable> union = new HashSet<>(premise);
        union.addAll(conclusion);

        return AbstractAssociationRuleMiner.frequency(union, itemSet) 
            / AbstractAssociationRuleMiner.frequency(premise, itemSet);
    }
}