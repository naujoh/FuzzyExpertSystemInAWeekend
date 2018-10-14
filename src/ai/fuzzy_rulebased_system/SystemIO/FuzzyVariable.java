package ai.fuzzy_rulebased_system.SystemIO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FuzzyVariable {
    private int varID;
    private List<HashMap<String, Double>> membershipByTag;

    public FuzzyVariable() {
        membershipByTag = new ArrayList<>();
    }

    public int getVarID() {
        return varID;
    }

    public void setVarID(int varID) {
        this.varID = varID;
    }

    public void setMembershipByTag(List<HashMap<String, Double>> membershipByTag) {
        this.membershipByTag = membershipByTag;
    }

    public List<HashMap<String, Double>> getMembershipByTag() {
        return membershipByTag;
    }
}
