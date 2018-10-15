package ai.fuzzy_rulebased_system;

import ai.fuzzy_rulebased_system.Files.FileManager;
import ai.fuzzy_rulebased_system.Fuzzifier.Fuzzifier;
import ai.fuzzy_rulebased_system.Fuzzifier.Line;
import ai.fuzzy_rulebased_system.Fuzzifier.LinguisticTag;
import ai.fuzzy_rulebased_system.SystemIO.FuzzyVariable;
import ai.fuzzy_rulebased_system.SystemIO.RealVariable;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        FileManager fileManager = new FileManager();
        Scanner scanner = new Scanner(System.in);
        Fuzzifier fuzzifier = new Fuzzifier();
        List<RealVariable> realVariableList=null;
        int i = 1;
//        fileManager.loadLinguisticVariablesDataFromTextFile("linguistic_variables");
//        fileManager.printLinguisticVariablesFiles();
       //fileManager.loadTagPathXDataFromTextFile("tag_path_text_X");
       //fileManager.loadTagPathYDataFromTextFile("tag_path_text_Y");
      //fileManager.loadResulDataFromTextFile("resultsFam");
       
       RandomAccessFile fileTraza = new RandomAccessFile("file_fam", "r");
       String traza = "1,0,2,2,0,1,2,2";
       String coord = fileManager.getCoordTagPath(traza, fileTraza);
       System.out.println("COORDENADAS "+coord);
       String coordSplit [] = coord.split(",");
       
        RandomAccessFile fileRand = new RandomAccessFile("results_fam", "r");
        Character letra_resultado = fileManager.readResultsFam(Integer.valueOf(coordSplit[0]),Integer.valueOf(coordSplit[1]), fileRand);
        System.out.println("LETRA RESULTADO "+letra_resultado);
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
