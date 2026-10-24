package datamining;

import java.util.Set;

import modelling.BooleanVariable;

public class BruteForceAssociationRuleMiner extends AbstractAssociationRuleMiner
{
    public BruteForceAssociationRuleMiner(BooleanDatabase db)
    {
        super(db);
    }

    @Override
    public Set<AssociationRule> extract(float frequency, float confidence)
    {
        return null;
    }

    public static Set<Set<BooleanVariable>> allCandidatePremises(Set<BooleanVariable> items)
    {
        return null;
    }
}