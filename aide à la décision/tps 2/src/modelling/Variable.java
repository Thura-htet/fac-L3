package modelling;

import java.util.Set;
import java.util.Objects;

public class Variable 
{
    protected String name;
    protected Set<Object> domain;    

    public Variable(String name, Set<Object> domain)
    {
        this.name = name;
        this.domain = domain;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;

        if (!(obj instanceof Variable))
            return false;

        final Variable var = (Variable) obj;
        if (!this.name.equals(var.name))
            return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name);
    }

    public String getName()
    {
        return this.name;
    }

    public Set<Object> getDomain()
    {
        return this.domain;
    }
}
