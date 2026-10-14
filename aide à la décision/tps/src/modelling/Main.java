package modelling;

import modellingtests.VariableTests;
import modellingtests.BooleanVariableTests;
import modellingtests.DifferenceConstraintTests;

public class Main {
    public static void main(String[] args) throws Exception 
    {
        boolean ok = true;
        ok = ok && VariableTests.testGetName();
        ok = ok && VariableTests.testGetDomain();
        ok = ok && VariableTests.testEquals();
        ok = ok && VariableTests.testHashCode();
        ok = ok && BooleanVariableTests.testConstructor();
        ok = ok && BooleanVariableTests.testEquals();
        ok = ok && BooleanVariableTests.testHashCode();
        ok = ok && DifferenceConstraintTests.testGetScope();
        ok = ok && DifferenceConstraintTests.testIsSatisfiedBy();
        
        System.out.println(ok ? "All tests OK" : "At least one test KO");
    }
}
