package ai.fuzzy_rulebased_system;

import ai.fuzzy_rulebased_system.Files.FileManager;
import ai.fuzzy_rulebased_system.Fuzzification.Fuzzifier;
import ai.fuzzy_rulebased_system.SystemIO.FuzzyVariable;
import ai.fuzzy_rulebased_system.SystemIO.RealVariable;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        FileManager fileManager = new FileManager();
        Scanner scanner = new Scanner(System.in);
        Fuzzifier fuzzifier = new Fuzzifier();
		Inference inference = new Inference();
        List<RealVariable> realVariableList=null;
        int i = 1;
//        fileManager.loadLinguisticVariablesDataFromTextFile("linguistic_variables");
//        fileManager.printLinguisticVariablesFiles();
       //fileManager.loadTagPathXDataFromTextFile("tag_path_text_X");
       //fileManager.loadTagPathYDataFromTextFile("tag_path_text_Y");
      //fileManager.loadResulDataFromTextFile("resultsFam");
            
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

        inference.deduce(fuzzifier.fuzzify(realVariableList));

/*        for(LinguisticTag lt : fileManager.getTagsOfOutputVariable()) {
            System.out.println("NOMBRE: "+lt.getName());
            for(Line l : lt.getLinesList()) {
                System.out.println("L: ("+l.getPoint_a().getX()+" , "+l.getPoint_a().getY()+") - ("+l.getPoint_b().getX()+" , "+l.getPoint_b().getY()+")");
            }
        }
*/
    }
}
