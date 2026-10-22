package cp;

import modelling.Variable;

import java.util.Map;


public interface Solver
{
    public Map<Variable, Object> solve();
}