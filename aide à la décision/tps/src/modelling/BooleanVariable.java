package modelling;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BooleanVariable extends Variable 
{
    public BooleanVariable(String name)
    {
        Set<Object> domain = new HashSet<>() {{
            add(true);
            add(false);
        }};
        super(name, domain);
    }
}
