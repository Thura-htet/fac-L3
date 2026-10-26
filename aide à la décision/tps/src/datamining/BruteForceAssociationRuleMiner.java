package datamining;

import java.util.HashSet;
import java.util.Set;

import modelling.BooleanVariable;

public class BruteForceAssociationRuleMiner extends AbstractAssociationRuleMiner
{
    public BruteForceAssociationRuleMiner(BooleanDatabase db)
    {
        super(db);
    }

    public static Set<Set<BooleanVariable>> allCandidatePremises(Set<BooleanVariable> items)
    {
        Set<Set<BooleanVariable>> res = new HashSet<Set<BooleanVariable>>();
        if (items.size() <= 1)
            return res;
        
        BooleanVariable pick = items.iterator().next();
        res.add(Set.of(pick));

        Set<BooleanVariable> rest = new HashSet<>(items);
        rest.remove(pick);
        res.add(rest);
        Set<Set<BooleanVariable>> subsets = allCandidatePremises(rest);
        res.addAll(subsets);

        for (Set<BooleanVariable> subset : subsets)
        {
            Set<BooleanVariable> copy = new HashSet<>(subset);
            copy.add(pick);

            if (copy.size() < items.size())
                res.add(copy);
        }

        return res;
    }

    @Override
    public Set<AssociationRule> extract(float minFrequency, float minConfidence)
    {
        Set<AssociationRule> res = new HashSet<>();

        ItemsetMiner apriori = new Apriori(this.getDatabase());
        Set<Itemset> frequenItemsets = apriori.extract(minFrequency);

        for (Itemset itemSet : frequenItemsets)
        {
            Set<BooleanVariable> union = new HashSet<>(itemSet.getItems());
            Set<Set<BooleanVariable>> premiseSet 
                = BruteForceAssociationRuleMiner.allCandidatePremises(union);

            for (Set<BooleanVariable> premise : premiseSet)
            {
                Set<BooleanVariable> conclusion = new HashSet<>(itemSet.getItems());
                conclusion.removeAll(premise);

                float frequency = AbstractAssociationRuleMiner.frequency(union, frequenItemsets);
                float confidence = AbstractAssociationRuleMiner.confidence(premise, conclusion, frequenItemsets);

                if ((frequency >= minFrequency) && (confidence >= minConfidence))
                    res.add(new AssociationRule(premise, conclusion, frequency, confidence));
            }
        }
        return res;
    }
}