import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class GeneralKB {
    private String[] sentences;
    private ArrayList<String> symbols = new ArrayList<>();
    private String kbString = "";
    private String queryString = "";

    Parser parser;

    public GeneralKB(String filename){
        parser = new Parser();

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

        intialiseSymbols(sentences);
    }

    private void intialiseSymbols(String[] sentences) {
        for(String sentence : sentences){
            String[] newSymbols = sentence.replaceAll("[^a-zA-Z]", "").split("");
            
            for(String symbol : newSymbols){
                if(!symbols.contains(symbol)){
                    symbol.trim();
                    symbols.add(symbol);
                }
            }
        }
    }

    public void getParsedSentences(){
        for(String sentence : sentences){
            System.out.println(parser.parseKB(sentence));
        }
    }

    public void evaluatePostfix(String postfix){
        Stack<Boolean> stack = new Stack<>();

        for(int index = 0; index < postfix.length(); index++){
            char c = postfix.charAt(index);
            // String current = "" + c;  

            if(Character.isLetter(c)){
                //get its boolean value from TT and push onto stack

            }else if(c == '~'){
                //negate the next char and store in stack + index++

            }else{
                //it is an operator
                boolean operand1 = stack.pop();
                boolean operand2 = stack.pop();
                boolean result = evaluateOperation(operand1, operand2, c);
            }
        }
    }

    public boolean evaluateOperation(boolean operand1, boolean operand2, char c){
        boolean result = false;

        switch(c){
            case('&'):
                result = operand1 && operand2;
                break;
            case('|'):
                result = operand1 || operand2;
                break;
            case('<'):
                result = (operand1 && operand2) || (!operand1 && !operand2);
                break;
            case('='):
                result = !operand1 || operand2;
                break;
            default:
                return false;
        }
        return result;
    }

}
