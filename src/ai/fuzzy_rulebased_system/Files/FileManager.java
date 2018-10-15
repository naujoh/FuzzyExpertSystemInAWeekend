package ai.fuzzy_rulebased_system.Files;

import ai.fuzzy_rulebased_system.Fuzzification.Line;
import ai.fuzzy_rulebased_system.Fuzzification.LinguisticTag;
import ai.fuzzy_rulebased_system.Fuzzification.Point;
import ai.fuzzy_rulebased_system.SystemIO.RealVariable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class FileManager {
    private final int ELEMENTS_INT_LINGUISTIC_TAG = 13;
    public static final int TAGS_BY_VARIABLE = 5;
    private final int REG_SIZE_LINGUISTIC_MASTER = Integer.BYTES+(106*Character.BYTES)+(5*((10*Character.BYTES)+(12*Double.BYTES)));
    private final String LINGUISTIC_VARS_INDEX = "linguistic_variables_index";
    private final String LINGUISTIC_VARS_MASTER = "linguistic_variables_master";

    public void loadLinguisticVariablesDataFromTextFile(String filename) {
        try {
            File file = new File(filename);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringTokenizer stringTokenizer;
            StringBuffer stringBuffer;
            String line;
            int i = 1;

            while((line = bufferedReader.readLine()) != null) {
                stringTokenizer = new StringTokenizer(line);
                writeDataFromLinguisticVariablesFile(Integer.parseInt(stringTokenizer.nextToken()), 'i'); // id
                stringBuffer = new StringBuffer(stringTokenizer.nextToken()); // variable name (6 * char)
                stringBuffer.setLength(6);
                writeDataFromLinguisticVariablesFile(stringBuffer.toString(), 's');
                stringBuffer = new StringBuffer(stringTokenizer.nextToken()); // universe (100 * char)
                stringBuffer.setLength(100);
                writeDataFromLinguisticVariablesFile(stringBuffer.toString(), 's');
                while(stringTokenizer.hasMoreElements()) {
                    if(i == 1) {
                        stringBuffer = new StringBuffer(stringTokenizer.nextToken()); //linguistic tag (10 * char)
                        stringBuffer.setLength(10);
                        writeDataFromLinguisticVariablesFile(stringBuffer.toString(), 's');
                    } else {
                        writeDataFromLinguisticVariablesFile(Double.parseDouble(stringTokenizer.nextToken()), 'd'); //point number
                    }
                    i++;
                    if(i > ELEMENTS_INT_LINGUISTIC_TAG) i = 1;
                }
            }

        } catch (Exception e) { e.printStackTrace(); }

    }

    private void writeDataFromLinguisticVariablesFile(Object data, char dataType) {
        try {
            RandomAccessFile file = new RandomAccessFile(LINGUISTIC_VARS_MASTER, "rw");
            switch (dataType) {
                case 's':
                    file.seek(file.length());
                    file.writeChars((String) data);
                    file.close();
                    break;
                case 'i':
                    long logicAddress = file.length();
                    file.seek(logicAddress);
                    file.writeInt((int) data);
                    file.close();
                    file = new RandomAccessFile(LINGUISTIC_VARS_INDEX, "rw");
                    file.seek(file.length());
                    file.writeInt((int) data);
                    file.writeLong(logicAddress);
                    file.close();
                    break;
                case 'd':
                    file.seek(file.length());
                    file.writeDouble((double) data);
                    file.close();
                    break;
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private String readString(int length, RandomAccessFile file) {
        char [] string = new char[length];
        try {
            for (int i = 0; i < string.length; i++) {
                string[i] = file.readChar();
            }
        } catch (IOException e ) { e.printStackTrace(); }
        return String.valueOf(string);
    }

    public List<RealVariable> getRealVariables() {
        List<RealVariable> variables = new ArrayList<>();
        RealVariable variable;
        int ruleId;
        try {
            long n_registers;
            RandomAccessFile file = new RandomAccessFile(LINGUISTIC_VARS_MASTER, "r");
            n_registers = file.length() / REG_SIZE_LINGUISTIC_MASTER;
            for(int i = 0; i<n_registers; i++) {
                variable = new RealVariable();
                file.seek(REG_SIZE_LINGUISTIC_MASTER*i);
                ruleId = file.readInt();
                if(ruleId != -1) {
                    variable.setVarID(ruleId);
                    variable.setName(readString(6, file).trim());
                    variables.add(variable);
                }
            }
            file.close();
        } catch (Exception e) { e.printStackTrace(); }
        return variables;
    }

    public LinguisticTag getLinguisticTag(int ruleId, int tagIndex) {
        LinguisticTag linguisticTag = null;
        Line line;
        String nameTag;
        double point;
        boolean regFound = false;
        long logicAddress = 0;
        try {
            RandomAccessFile file = new RandomAccessFile(LINGUISTIC_VARS_INDEX, "r");
            file.seek(0);
            while(!regFound && (file.getFilePointer() < file.length())) {
                if(file.readInt()==ruleId) {
                    logicAddress = file.readLong();
                    regFound = true;
                }
            }
            file.close();
            if(regFound) {
                file = new RandomAccessFile(LINGUISTIC_VARS_MASTER, "r");
                file.seek(logicAddress+Integer.BYTES+(106*Character.BYTES)+tagIndex*((10*Character.BYTES)+(12*Double.BYTES)));
                nameTag = readString(10,file).trim();
                if(!nameTag.equals("NULL")) {
                    linguisticTag = new LinguisticTag();
                    linguisticTag.setName(nameTag);
                    for(int i = 0; i < ELEMENTS_INT_LINGUISTIC_TAG-1; i++) {
                        point = file.readDouble();
                        if (point != -1) {
                            line = new Line(new Point(point, file.readDouble()), new Point(file.readDouble(), file.readDouble()));
                            linguisticTag.getLinesList().add(line);
                            i+=3;
                        } else break;
                    }
                }
                file.close();
            }
        } catch (Exception e) { e.printStackTrace(); }
        return linguisticTag;
    }

    public List<LinguisticTag> getTagsOfOutputVariable() {
        List<LinguisticTag> linguisticTags = new ArrayList<>();
        LinguisticTag linguisticTag;
        Line line;
        long logicaddress = 0;
        int i = 0;
        double point;
        try {
            RandomAccessFile file = new RandomAccessFile(LINGUISTIC_VARS_INDEX, "r");
            file.seek(0);
            while (file.getFilePointer()<file.length()) {
                if(file.readInt()==-1) {
                    logicaddress = file.readLong();
                    break;
                }
            }
            file.close();
            file = new RandomAccessFile(LINGUISTIC_VARS_MASTER, "r");
            while(file.getFilePointer()<file.length()) {
                file.seek(logicaddress+Integer.BYTES+(106*Character.BYTES)+i*((10*Character.BYTES)+(12*Double.BYTES)));
                linguisticTag = new LinguisticTag();
                linguisticTag.setName(readString(10, file).trim());
                for(int j = 1; j < ELEMENTS_INT_LINGUISTIC_TAG-1; j++) {
                    point = file.readDouble();
                    if (point != -1) {
                        line = new Line(new Point(point, file.readDouble()), new Point(file.readDouble(), file.readDouble()));
                        linguisticTag.getLinesList().add(line);
                        j+=3;
                    } else {
                        file.seek(logicaddress+Integer.BYTES+(106*Character.BYTES)+(i+1)*((10*Character.BYTES)+(12*Double.BYTES)));
                        break;
                    }
                }
                linguisticTags.add(linguisticTag);
                i++;
            }
            file.close();
        } catch (Exception e) { e.printStackTrace(); }
        return linguisticTags;
    }

    // A test method
    public void printLinguisticVariablesFiles() {
        try {
            int nextElements = 65, i=1;
            System.out.println("MASTER FILE");
            RandomAccessFile file = new RandomAccessFile(LINGUISTIC_VARS_MASTER, "r");
            file.seek(0);
            while (file.getFilePointer() < file.length()) {
                System.out.println("ID: "+file.readInt());
                System.out.println("NAME: "+readString(6, file).trim());
                System.out.println("UNIVERSE: "+readString(100, file).trim());
                while(nextElements > 0) {
                    if(i==1) System.out.println("TAGNAME: "+readString(10,file).trim());
                    else System.out.println("\t"+file.readDouble());
                    i++;
                    if(i>ELEMENTS_INT_LINGUISTIC_TAG) i = 1;
                    nextElements--;
                }
                nextElements = 65;
            }
            file.close();
            System.out.println("INDEX FILE");
            file = new RandomAccessFile(LINGUISTIC_VARS_INDEX, "r");
            file.seek(0);
            while (file.getFilePointer() < file.length()) {
                System.out.println("ID: "+file.readInt() + " LOGIC ADDR: " + file.readLong());
            }
            file.close();
        } catch (Exception e) { e.printStackTrace(); }
    }
}
