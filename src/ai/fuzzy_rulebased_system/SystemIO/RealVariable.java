package ai.fuzzy_rulebased_system.SystemIO;

import java.util.List;

public class RealVariable {
    private int varID;
    private String name;
    private double value;

    public int getVarID() {
        return varID;
    }

    public void setVarID(int varID) {
        this.varID = varID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
