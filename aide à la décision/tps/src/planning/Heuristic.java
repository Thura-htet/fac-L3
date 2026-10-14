package planning;

import modelling.Variable;

import java.util.Map;

public interface Heuristic
{
    public Float estimate(Map<Variable, Object> state);
}
