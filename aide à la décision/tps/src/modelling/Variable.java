package modelling;

import java.util.Objects;
import java.util.Set;

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

    @Override
    public String toString()
    {
/*         String res = this.name + ": {" + this.domain.size() + " | ";
        for (Object o : this.domain)
            res += (o + ", ");
        res += "}"; */
        return this.name;
    }
}
