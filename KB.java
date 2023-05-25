import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class KB {
    private String[] sentences;
    private String kbString = "";
    private String queryString = "";
    protected ArrayList<String> symbols = new ArrayList<>(); 

    public KB(String filename){
        initialiseKB(filename);
    }

    public void initialiseKB(String filename){
        File file = new File(filename);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                // System.out.println(line);

                if(line.equals("TELL")) {
                    line = scanner.nextLine().trim();
                    // System.out.println(line);

                    kbString += line;
                    kbString = kbString.replace(" ", "");

                }else if(line.equals("ASK")) {
                    // System.out.println("detected");

                    queryString = scanner.nextLine();
                }
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        sentences = kbString.split(";");

        for(String sentence : sentences){
            // Extract symbols from the sentence
            Pattern pattern = Pattern.compile("\\b[a-zA-Z0-9]+\\b");
            Matcher matcher = pattern.matcher(sentence);
    
            while (matcher.find()) {
                String symbol = matcher.group();
                if (!symbols.contains(symbol)) {
                    symbols.add(symbol);
                }
            }
        }

    }

    public ArrayList<String> getSymbols(){
        return symbols;
    }

    public String getQuery(){
        return queryString;
    }

    public String[] getSentences(){
        return sentences;
    }
}
