import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class KnowledgeBase {
    private String[] sentences;
    private String kbString = "";
    private String queryString = "";
    private HashSet<String> symbols = new HashSet<>();

    public KnowledgeBase(String filename){
        initialiseKB(filename);
    }

    public void initialiseKB(String filename){
        File file = new File(filename);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                System.out.println(line);

                if(line.equals("TELL")) {
                    line = scanner.nextLine().trim();
                    System.out.println(line);

                    kbString += line;
                    kbString = kbString.replace(" ", "");

                }else if(line.equals("ASK")) {
                    System.out.println("detected");

                    queryString = scanner.nextLine();
                }
            }

            scanner.close();
            System.out.println(kbString);
            System.out.println(queryString);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        sentences = kbString.split(";");

        for(String sentence : sentences){
            System.out.println(sentence);
            String[] newSymbols = sentence.split("=>|&");
            
            for(String symbol : newSymbols){
                if(!symbols.contains(symbol)){
                    symbol.trim();
                    symbols.add(symbol);
                }
            }
        }

        System.out.println("Symbols in KB:");
        for(String things : symbols){
            System.out.println(things);
        }
    }

    public HashSet<String> getSymbols(){
        return symbols;
    }

    public String getQuery(){
        return queryString;
    }
}
