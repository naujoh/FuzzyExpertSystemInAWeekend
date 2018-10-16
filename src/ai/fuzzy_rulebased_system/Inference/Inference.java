package ai.fuzzy_rulebased_system.Inference;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ai.fuzzy_rulebased_system.Files.FileManager;
import ai.fuzzy_rulebased_system.SystemIO.FuzzyVariable;
import ai.fuzzy_rulebased_system.SystemIO.PreOutputTag;

public class Inference {

	FileManager fileManager = new FileManager();
	List<PreOutputTag> AList = new ArrayList<>();
	List<PreOutputTag> BList = new ArrayList<>();
	List<PreOutputTag> CList = new ArrayList<>();
	List<PreOutputTag> DList = new ArrayList<>();
	List<PreOutputTag> EList = new ArrayList<>();
	List<FuzzyOutput> FuzzyOutputList = new ArrayList<>();
	FuzzyOutput fuzzyOutput;
	PreOutputTag preOutputTag;

	public List<FuzzyOutput> deduce(List<FuzzyVariable> fVariables) throws FileNotFoundException {
		ArrayList<Double> SOLPRO = new ArrayList<Double>();
		ArrayList<Double> CAAPCP = new ArrayList<Double>();
		ArrayList<Double> HABINV = new ArrayList<Double>();
		ArrayList<Double> CADIGP = new ArrayList<Double>();
		ArrayList<Double> CAPAAS = new ArrayList<Double>();
		ArrayList<Double> TRABEQ = new ArrayList<Double>();
		ArrayList<Double> HATRFA = new ArrayList<Double>();
		ArrayList<Double> BUSLOG = new ArrayList<Double>();
		ArrayList<Double> MIN = new ArrayList<Double>();
		String coordenada;
		Character outputTag;

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

		for (int i = 0; i < SOLPRO.size(); i++) {
			for (int j = 0; j < CAAPCP.size(); j++) {
				for (int k = 0; k < HABINV.size(); k++) {
					for (int l = 0; l < CADIGP.size(); l++) {
						for (int m = 0; m < CAPAAS.size(); m++) {
							for (int n = 0; n < TRABEQ.size(); n++) {
								for (int o = 0; o < HATRFA.size(); o++) {
									for (int p = 0; p < BUSLOG.size(); p++) {
										coordenada = "";
										outputTag = '\0';
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
										if (MIN.get(0) != 0.0) {

											coordenada = i + "," + j + "," + k + "," + l + "," + m + "," + n + "," + o
													+ "," + p;
											/*
											 * System.out.println("\t valor de coordenada: " + (i + 4) + "," + (j + 3) +
											 * "," + (k + 2) + "," + (l + 1) + "," + (m + 4) + "," + (n + 3) + "," + (o
											 * + 2) + "," + (p + 1));
											 */
											outputTag = getOutputTag(coordenada);
											preOutputTag = new PreOutputTag();
											preOutputTag.setValue(MIN.get(0));
											preOutputTag.setCoord(coordenada);
											preOutputTag.setTag(outputTag);
											
											switch (outputTag) {
											case 'A':
												AList.add(preOutputTag);
												break;
											case 'B':
												BList.add(preOutputTag);
												break;
											case 'C':
												CList.add(preOutputTag);
												break;
											case 'D':
												DList.add(preOutputTag);
												break;
											case 'E':
												EList.add(preOutputTag);
												break;
											default:
												System.out.println("Entre default");
												break;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		System.out.println("");
		System.out.println("Minimos relevantes-------------------");
		printMins();
		
		System.out.println("");
		System.out.println("Variables de salida difusas----------");
		return printMaxs();
	}

	public Character getOutputTag(String traza) throws FileNotFoundException {
		RandomAccessFile fileTraza = new RandomAccessFile("file_fam", "r");
		String coord = fileManager.getCoordTagPath(traza, fileTraza);
		String coordSplit[] = coord.split(",");
		RandomAccessFile fileRand = new RandomAccessFile("results_fam", "r");
		Character letra_resultado = fileManager.readResultsFam(Integer.valueOf(coordSplit[0]),
		Integer.valueOf(coordSplit[1]), fileRand);
		return letra_resultado;
	}
	
	public void printMins() {
		if (!AList.isEmpty()) {
			for (PreOutputTag r : AList) {
				System.out.println("\tValor: " + r.getValue() + "\tCamino: " + r.getCoord() + "\tEtiqueta salida: " + r.getTag());
			}
		}
		if (!BList.isEmpty()) {
			for (PreOutputTag r : BList) {
				System.out.println("\tValor: " + r.getValue() + "\tCamino: " + r.getCoord() + "\tEtiqueta salida: " + r.getTag());
			}
		}
		if (!CList.isEmpty()) {
			for (PreOutputTag r : CList) {
				System.out.println("\tValor: " + r.getValue() + "\tCamino: " + r.getCoord() + "\tEtiqueta salida: " + r.getTag());
			}
		}
		if (!DList.isEmpty()) {
			for (PreOutputTag r : DList) {
				System.out.println("\tValor: " + r.getValue() + "\tCamino: " + r.getCoord() + "\tEtiqueta salida: " + r.getTag());
			}
		}
		if (!EList.isEmpty()) {
			for (PreOutputTag r : EList) {
				System.out.println("\tValor: " + r.getValue() + "\tCamino: " + r.getCoord() + "\tEtiqueta salida: " + r.getTag());
			}
		}
	}
	
	public List<FuzzyOutput> printMaxs() {
		AList.sort(Comparator.comparing(PreOutputTag::getValue).reversed());
		BList.sort(Comparator.comparing(PreOutputTag::getValue).reversed());
		CList.sort(Comparator.comparing(PreOutputTag::getValue).reversed());
		DList.sort(Comparator.comparing(PreOutputTag::getValue).reversed());
		EList.sort(Comparator.comparing(PreOutputTag::getValue).reversed());
		
		if(AList.isEmpty()) {
			fuzzyOutput = new FuzzyOutput();
			System.out.println("\tA \tMEMBRESIA: 0");
			fuzzyOutput.setVarID(1);
			fuzzyOutput.getMembershipByTag().put("A",0.0);
			FuzzyOutputList.add(fuzzyOutput);
		}else {
			fuzzyOutput = new FuzzyOutput();
			System.out.println("\tA \tMEMBRESIA: " + AList.get(0).getValue());
			fuzzyOutput.setVarID(1);
			fuzzyOutput.getMembershipByTag().put("A",AList.get(0).getValue());
			FuzzyOutputList.add(fuzzyOutput);
		}
		if(BList.isEmpty()) {
			fuzzyOutput = new FuzzyOutput();
			System.out.println("\tB \tMEMBRESIA: 0");
			fuzzyOutput.setVarID(2);
			fuzzyOutput.getMembershipByTag().put("B",0.0);
			FuzzyOutputList.add(fuzzyOutput);
		}else {
			fuzzyOutput = new FuzzyOutput();
			System.out.println("\tB \tMEMBRESIA: " + BList.get(0).getValue());
			fuzzyOutput.setVarID(2);
			fuzzyOutput.getMembershipByTag().put("B",BList.get(0).getValue());
			FuzzyOutputList.add(fuzzyOutput);
		}
		if(CList.isEmpty()) {
			fuzzyOutput = new FuzzyOutput();
			System.out.println("\tC \tMEMBRESIA: 0");
			fuzzyOutput.setVarID(3);
			fuzzyOutput.getMembershipByTag().put("C",0.0);
			FuzzyOutputList.add(fuzzyOutput);
		}else {
			fuzzyOutput = new FuzzyOutput();
			System.out.println("\tC \tMEMBRESIA: " + CList.get(0).getValue());
			fuzzyOutput.setVarID(3);
			fuzzyOutput.getMembershipByTag().put("C",CList.get(0).getValue());
			FuzzyOutputList.add(fuzzyOutput);
		}
		if(DList.isEmpty()) {
			fuzzyOutput = new FuzzyOutput();
			System.out.println("\tD \tMEMBRESIA: 0");
			fuzzyOutput.setVarID(4);
			fuzzyOutput.getMembershipByTag().put("D",0.0);
			FuzzyOutputList.add(fuzzyOutput);
		}else {
			fuzzyOutput = new FuzzyOutput();
			System.out.println("\tD \tMEMBRESIA: " + DList.get(0).getValue());
			fuzzyOutput.setVarID(4);
			fuzzyOutput.getMembershipByTag().put("D",DList.get(0).getValue());
			FuzzyOutputList.add(fuzzyOutput);
		}
		if(EList.isEmpty()) {
			fuzzyOutput = new FuzzyOutput();
			System.out.println("\tE \tMEMBRESIA: 0");
			fuzzyOutput.setVarID(5);
			fuzzyOutput.getMembershipByTag().put("E",0.0);
			FuzzyOutputList.add(fuzzyOutput);
		}else {
			fuzzyOutput = new FuzzyOutput();
			System.out.println("\tE \tMEMBRESIA: " + EList.get(0).getValue());
			fuzzyOutput.setVarID(5);
			fuzzyOutput.getMembershipByTag().put("E",EList.get(0).getValue());
			FuzzyOutputList.add(fuzzyOutput);
		}
		return FuzzyOutputList;
	}
}
