package ai.fuzzy_rulebased_system;

import ai.fuzzy_rulebased_system.Files.FileManager;
import ai.fuzzy_rulebased_system.Fuzzifier.Fuzzifier;
import ai.fuzzy_rulebased_system.Fuzzifier.Line;
import ai.fuzzy_rulebased_system.Fuzzifier.LinguisticTag;
import ai.fuzzy_rulebased_system.SystemIO.FuzzyVariable;
import ai.fuzzy_rulebased_system.SystemIO.RealVariable;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        FileManager fileManager = new FileManager();
        Scanner scanner = new Scanner(System.in);
        Fuzzifier fuzzifier = new Fuzzifier();
        List<RealVariable> realVariableList=null;
        int i = 1;
//        fileManager.loadLinguisticVariablesDataFromTextFile("linguistic_variables");
//        fileManager.printLinguisticVariablesFiles();
        System.out.println("Ingresa el valor real de cada variable");
        try {
            realVariableList = fileManager.getRealVariables();
            for(RealVariable r : realVariableList) {
                System.out.printf(i+". Variable: "+ r.getName()+"\tVALOR: ");
                    r.setValue(scanner.nextDouble());
                    i++;
            }
        } catch (InputMismatchException e) {
            System.out.println("ERROR!, Se requiere un tipo de dato double: "+e.toString());
        }
        System.out.println("VARIABLES DIFUSAS");
        for(FuzzyVariable f :fuzzifier.fuzzify(realVariableList)) {
            System.out.println(f.getVarID());
            for(String tag : f.getMembershipByTag().keySet()) {
                System.out.println("ETIQUETA: "+tag+"\tMEMBRESIA: "+f.getMembershipByTag().get(tag));
            }
        }
    }
}
