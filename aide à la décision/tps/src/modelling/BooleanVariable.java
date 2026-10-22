package modelling;

import java.util.HashSet;
import java.util.Set;

public class BooleanVariable extends Variable 
{
    public BooleanVariable(String name)
    {
        super(name, new HashSet<>(Set.of(true, false)));
    }
}
