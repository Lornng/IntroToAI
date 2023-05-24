import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GeneralKB {
    private String[] sentences;
    private ArrayList<String> symbols = new ArrayList<>();
    private String kbString;
    private String queryString;

    public GeneralKB(String filename){
        initialiseKB(filename);
    }

    public void initialiseKB(String filename){
        File file = new File(filename);
        try{
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String line = scanner.nextLine().trim();

                if(line.equals("TELL")){
                    line = scanner.nextLine().trim();
                    kbString += line;
                    kbString = kbString.replace(" ", "");

                }else if(line.equals("ASK")){
                    queryString = scanner.nextLine();
                }
            }

            scanner.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }  

        sentences = kbString.split(";");
    }

}
