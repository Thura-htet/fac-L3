package datamining;

import java.util.Comparator;
import java.util.Set;

import modelling.BooleanVariable;



public abstract class AbstractItemsetMiner implements ItemsetMiner
{
    public static final Comparator<BooleanVariable> COMPARATOR =
        (var1, var2) -> var1.getName().compareTo(var2.getName());
    protected BooleanDatabase db;


    public AbstractItemsetMiner(BooleanDatabase db)
    {
        this.db = db;
    }

    @Override
    public BooleanDatabase getDatabase()
    {
        return this.db;
    }

    public float frequency(Set<BooleanVariable> items)
    {
        float count = 0;
        for (Set<BooleanVariable> transaction : this.db.getTransactions())
            if (transaction.containsAll(items))
                count++;
        return count;
    }
}