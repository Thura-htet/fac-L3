package datamining;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import modelling.BooleanVariable;


public class Apriori extends AbstractItemsetMiner
{
    public Apriori(BooleanDatabase db)
    {
        super(db);
    }

    public Set<Itemset> frequentSingletons(float minFrequency)
    {
        Set<Itemset> singletonSet = new HashSet<>();
        float frequency;

        for (BooleanVariable item : this.db.getItems())
            if ((frequency = this.frequency(Set.of(item))) >= minFrequency)
                singletonSet.add(new Itemset(Set.of(item), frequency));
        
        return singletonSet;
    }

    public static SortedSet<BooleanVariable> combine(SortedSet<BooleanVariable> set1, SortedSet<BooleanVariable> set2)
    {
        if (set1.size() != set2.size()
            || set1.isEmpty()
            || set2.isEmpty()
        )
            return null;
        // System.out.println("Item sets have the same size k.");

        int k = set1.size();
        int i = 0;
        Iterator<BooleanVariable> iterator1 = set1.iterator();
        Iterator<BooleanVariable> iterator2 = set2.iterator();

        while (iterator1.hasNext() && iterator2.hasNext() && i < k-1)
        { 
            BooleanVariable var1 = iterator1.next();
            BooleanVariable var2 = iterator2.next();

            if (AbstractItemsetMiner.COMPARATOR.compare(var1, var2) != 0)
                return null;
            i++;
        }
        // System.out.println("Item sets have the same k-1 first elements.");
        if (AbstractItemsetMiner.COMPARATOR.compare(set1.getLast(), set2.getLast()) == 0)
            return null;
        // System.out.println("Item sets have the different kth first elements.");

        SortedSet<BooleanVariable> combined = new TreeSet<>(set1);
        combined.removeLast();
        combined.add(set1.getLast());
        combined.add(set2.getLast());

        return combined;
    }

    public static boolean allSubsetsFrequent(Set<BooleanVariable> items, Collection<SortedSet<BooleanVariable>> itemSets)
    {
        for (BooleanVariable item : items)
        {
            SortedSet<BooleanVariable> subset = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
            subset.addAll(items);
            subset.remove(item);
            if (!itemSets.contains(subset))
                return false;
        }
        return true;
    }

    /* @Override 
    public Set<Itemset> extract(float minFrequency)
    {
        Set<Itemset> res = new HashSet<>();

        System.out.println("item set" + this.getDatabase().getItems());
        System.out.println("transaction" + this.getDatabase().getTransactions());
        Set<Itemset> singletonSet = this.frequentSingletons(minFrequency);
        if (singletonSet.isEmpty())
            return res;

        List<SortedSet<BooleanVariable>> frequentSet = new ArrayList<>();
        Set<SortedSet<BooleanVariable>> exisitingCandidates = new HashSet<>();
        for (Itemset set : singletonSet)
        {
            SortedSet<BooleanVariable> singleton = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
            singleton.addAll(set.getItems());
            frequentSet.add(singleton);
            res.add(set);
        }

        while (!frequentSet.isEmpty())
        {
            List<SortedSet<BooleanVariable>> nextFrequentSet = new ArrayList<>();
            // System.out.println("frequent set" + frequentSet);
            // System.out.println();
            for (int i=0; i<frequentSet.size(); i++)
            {
                for (int j=i+1; j<frequentSet.size(); j++)
                {
                    SortedSet<BooleanVariable> candidate = Apriori.combine(frequentSet.get(i), frequentSet.get(j));
                    System.out.println("frequent set" + frequentSet);
                    System.out.println("Testing: " + i + frequentSet.get(i) + ", " + j + frequentSet.get(j));
                    System.out.println("Candidate: " + candidate);
                    System.out.println();
                    if (candidate == null)
                        continue;
                    float candidateFrequency = this.frequency(new HashSet<>(candidate));

                    if (!allSubsetsFrequent(candidate, frequentSet)
                        || exisitingCandidates.contains(candidate)
                        || candidateFrequency < minFrequency
                    )
                        continue;
                    nextFrequentSet.add(candidate);
                    exisitingCandidates.add(candidate);
                    res.add(new Itemset(new HashSet<>(candidate), candidateFrequency));
                    System.out.println("next frequent set" + nextFrequentSet);

                }
            }
            frequentSet = nextFrequentSet;
        }
        return res;
    } */
    @Override
    public Set<Itemset> extract(float minFrequency)
    {
        Set<Itemset> res = new HashSet<>();
        Set<Itemset> singletonSet = this.frequentSingletons(minFrequency);

        List<SortedSet<BooleanVariable>> frequentSet = new ArrayList<>();
        for (Itemset set : singletonSet)
        {
            SortedSet<BooleanVariable> singleton = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
            singleton.addAll(set.getItems());
            frequentSet.add(singleton);
            res.add(set);
        }
        Set<SortedSet<BooleanVariable>> candidatesSeen = new HashSet<>();

        while (!frequentSet.isEmpty())
        {
            List<SortedSet<BooleanVariable>> nextFrequentSet = new ArrayList<>();

            for (int i=0; i<frequentSet.size(); i++)
            {
                for (int j =i+1; j<frequentSet.size(); j++)
                {
                    SortedSet<BooleanVariable> candidate = Apriori.combine(frequentSet.get(i), frequentSet.get(j));

                    if (candidate == null)
                        continue;
                    float candidateFrequency = this.frequency(new HashSet<>(candidate));
                    
                    if (!allSubsetsFrequent(candidate, frequentSet)
                        || candidateFrequency < minFrequency
                        || !candidatesSeen.add(candidate)
                    )
                        continue;
                    nextFrequentSet.add(candidate);
                    res.add(new Itemset(new HashSet<>(candidate), candidateFrequency));
                }
            }
            frequentSet = nextFrequentSet;
        }
        return res;
    }

    public static Set<SortedSet<BooleanVariable>> generateCandidates(Set<Set<BooleanVariable>> itemSets)
    {
        Set<Set<BooleanVariable>> existingItemSets = new HashSet<>();
        Set<SortedSet<BooleanVariable>> candidates = new HashSet<>();
        for (Set<BooleanVariable> set1 : itemSets)
            for (Set<BooleanVariable> set2 : itemSets)
            {
                if (set1.equals(set2))
                    continue;

                Set<BooleanVariable> combinedSet = new HashSet<>(set1);
                combinedSet.addAll(set2);
                if (existingItemSets.contains(combinedSet))
                    continue;

                existingItemSets.add(combinedSet);
                SortedSet<BooleanVariable> sortedSet1 = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
                SortedSet<BooleanVariable> sortedSet2 = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
                SortedSet<BooleanVariable> combinedSortedSet = Apriori.combine(sortedSet1, sortedSet2);
                if (combinedSortedSet != null)
                    candidates.add(combinedSortedSet);
            }
        return candidates;
    }

}