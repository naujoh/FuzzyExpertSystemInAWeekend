package ai.fuzzy_rulebased_system.SystemIO;

import java.util.HashMap;

public class FuzzyOutput {
	private int varID;
    private HashMap<String, Double> membershipByTag;

    public FuzzyOutput() {
        membershipByTag = new HashMap<>();
    }

    public int getVarID() {
        return varID;
    }

    public void setVarID(int varID) {
        this.varID = varID;
    }

    public HashMap<String, Double> getMembershipByTag() {
        return membershipByTag;
    }

    public void setMembershipByTag(HashMap<String, Double> membershipByTag) {
        this.membershipByTag = membershipByTag;
    }

}
