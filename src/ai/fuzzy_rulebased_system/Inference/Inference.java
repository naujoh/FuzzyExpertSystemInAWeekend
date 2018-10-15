package ai.fuzzy_rulebased_system.Inference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ai.fuzzy_rulebased_system.SystemIO.FuzzyVariable;

public class Inference {

	public void deduce(List<FuzzyVariable> fVariables) {
		ArrayList<Double> SOLPRO = new ArrayList<Double>();
		ArrayList<Double> CAAPCP = new ArrayList<Double>();
		ArrayList<Double> HABINV = new ArrayList<Double>();
		ArrayList<Double> CADIGP = new ArrayList<Double>();
		ArrayList<Double> CAPAAS = new ArrayList<Double>();
		ArrayList<Double> TRABEQ = new ArrayList<Double>();
		ArrayList<Double> HATRFA = new ArrayList<Double>();
		ArrayList<Double> BUSLOG = new ArrayList<Double>();
		ArrayList<Double> MIN = new ArrayList<Double>();
		ArrayList<Double> MAX = new ArrayList<Double>();

		for (FuzzyVariable f : fVariables) {
			for (String tag : f.getMembershipByTag().keySet()) {
				switch (f.getVarID()) {
				case 1:
					SOLPRO.add(f.getMembershipByTag().get(tag));
					break;
				case 2:
					HABINV.add(f.getMembershipByTag().get(tag));
					break;
				case 3:
					CAAPCP.add(f.getMembershipByTag().get(tag));
					break;
				case 4:
					TRABEQ.add(f.getMembershipByTag().get(tag));
					break;
				case 5:
					HATRFA.add(f.getMembershipByTag().get(tag));
					break;
				case 6:
					CADIGP.add(f.getMembershipByTag().get(tag));
					break;
				case 7:
					BUSLOG.add(f.getMembershipByTag().get(tag));
					break;
				case 8:
					CAPAAS.add(f.getMembershipByTag().get(tag));
					break;

				default:
					break;
				}
			}
		}
		
		System.out.println("Minimos relevantes----------");
		for (int i = 0; i < SOLPRO.size(); i++) {
		    for (int j = 0; j < CAAPCP.size(); j++) {
		        for (int k = 0; k < HABINV.size(); k++) {
		            for (int l = 0; l < CADIGP.size(); l++) {
		                for (int m = 0; m < CAPAAS.size(); m++) {
		                    for (int n = 0; n < TRABEQ.size(); n++) {
		                        for (int o = 0; o < HATRFA.size(); o++) {
		                            for (int p = 0; p < BUSLOG.size(); p++) {
		                            	MIN.clear();
		                            	MIN.add(SOLPRO.get(i));
		                            	MIN.add(CAAPCP.get(j));
		                            	MIN.add(HABINV.get(k));
		                            	MIN.add(CADIGP.get(l));
		                            	MIN.add(CAPAAS.get(m));
		                            	MIN.add(TRABEQ.get(n));
		                            	MIN.add(HATRFA.get(o));
		                            	MIN.add(BUSLOG.get(p));
		                            	Collections.sort(MIN);
		                            	if (MIN.get(0)!= 0.0) {
		                            		System.out.print("\t"+MIN.get(0));
			                            	System.out.print("\t coordenada: "+i+","+j+","+k+","+l+","+m+","+n+","+o+","+p);
			                            	System.out.println("\t valor de coordenada: "+(i+4)+","+(j+3)+","+(k+2)+","+(l+1)+","+(m+4)+","+(n+3)+","+(o+2)+","+(p+1));
			                            	MAX.add(MIN.get(0));
		                            	}
		                            } 
		                        } 
		                    } 
		                } 
		            } 
		        } 
		    } 
		}
		Collections.sort(MAX);
		Collections.reverse(MAX);
		System.out.println("Maximos relevantes----------");
		System.out.println("\t Maximo "+MAX.get(0));
		
	}
	
	// muy mal 0.875
	//
	//
	//
	//meritos 1.25
}
