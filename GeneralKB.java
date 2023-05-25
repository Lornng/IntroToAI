import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class GeneralKB extends KB{
    private ArrayList<ArrayList<String>> parsed_sentences = new ArrayList<>(); 
    private Parser parser;
    private HashMap<String, Boolean> symbolMap = new HashMap<>();
    private Boolean[][] TT;
    private int numSymbols, rows;
    private int counter;


    public GeneralKB(String filename){
        super(filename);
        parser = new Parser();
        parsedSentences();
    }

    public ArrayList<ArrayList<String>> getParsedSentences(){
        return parsed_sentences;
    }

    public void createTT(){
        numSymbols = getSymbols().size();
        rows = (int) Math.pow(2, numSymbols);

        TT = new Boolean[rows][numSymbols];

        for(int row = 0; row < rows; row++){
            for(int col = 0; col < numSymbols; col++){
                String symbolString = symbols.get(col);
                //calculate the truth value for the current variable 
                boolean truthValue = ((row >> (numSymbols - col - 1)) & 1) == 1;

                TT[row][col] = truthValue;

                //assign truthValue to the symbol in the map
                symbolMap.put(symbolString + "[" + row + "]", truthValue);
            }
        }
    }

    public void parsedSentences(){
        for(String sentence : super.getSentences()){
            ArrayList<String> parsed_sentence = parser.parseKB(sentence);
            parsed_sentences.add(parsed_sentence);
        }
    }

    public boolean evaluatePostfix(){
        counter = 0;
        //loop through all rows of the TT 
        for(int row = 0; row < this.rows; row++){
            boolean kbTrue = true;
            //loop all the parsed sentences of this KB
            for(ArrayList<String> parsed : parsed_sentences){
                Stack<Boolean> stack = new Stack<>();

                System.out.println(parsed);

                //loop through the individual parsed sentences
                for(int i = 0; i < parsed.size(); i++){
                    String current = parsed.get(i);

                    System.out.println(current);

                    //if current is a symbol
                    if(symbols.contains(current)){
                        String toGet = current + "[" + row + "]";
                        Boolean truth_value = symbolMap.get(toGet);
                        stack.push(truth_value);
                    }
                    //if current is a negation, get it's truth value and negate it. skip the next index
                    else if(current.equals("~")){
                        String toGet = parsed.get(i + 1) + "[" + row + "]";
                        Boolean truth_value = symbolMap.get(toGet);
                        stack.push(!truth_value);

                        i++;
                    }
                    //current is an operator
                    else{
                        boolean operand1 = stack.pop();
                        boolean operand2 = stack.pop();
                        boolean result = evaluateOperation(operand1, operand2, current);
                        stack.push(result);
                    }

                    System.out.print(stack);
                }

                boolean sentence_result = stack.pop();
                if(!sentence_result){
                    kbTrue = false;
                    break;
                }
            }

            boolean query_tv = checkQuery(row);

            if(kbTrue && query_tv){
                counter++;
            }
        }

        if(counter > 0){
            //the KB has true configurations where the query is true
            return true;
        }else{
            //the KB does not have any true configurations
            return false;
        }
    }

    public boolean checkQuery(int curr_row){
        ArrayList<String> parsed_query = parser.parseKB(super.getQuery());
        Stack<Boolean> stack = new Stack<>();

        for(int index = 0; index < parsed_query.size(); index++){
            String c = parsed_query.get(index);

            if(symbols.contains(c)){
                //get its boolean value from TT and push onto stack
                String toGet = c + "[" + curr_row + "]";
                Boolean truth_value = symbolMap.get(toGet);
                stack.push(truth_value);
            }else if(c.equals("~")){
                //negate the next char and store in stack + index++
                String toGet = parsed_query.get(index + 1) + "[" + curr_row + "]";
                Boolean truth_value = symbolMap.get(toGet);
                stack.push(!truth_value);

                index++;
            }else{
                //it is an operator
                boolean operand1 = stack.pop();
                boolean operand2 = stack.pop();
                boolean result = evaluateOperation(operand1, operand2, c);
                stack.push(result);
            }
        }

        boolean result = stack.pop();
        return result;
    }
    

    public boolean evaluateOperation(boolean operand1, boolean operand2, String current){
        boolean result = false;

        switch(current){
            case("&"):
                result = operand1 && operand2;
                break;
            case("|"):
                result = operand1 || operand2;
                break;
            case("<"):
                result = (operand1 && operand2) || (!operand1 && !operand2);
                break;
            case("="):
                result = !operand2 || operand1;
                break;
            default:
                return false;
        }
        return result;
    }

    public int getCounter(){
        return this.counter;
    }
    // public void evaluatePostfix(String postfix){
    //     Stack<Boolean> stack = new Stack<>();

    //     for(int index = 0; index < postfix.length(); index++){
    //         char c = postfix.charAt(index);
    //         // String current = "" + c;  

    //         if(Character.isLetter(c)){
    //             //get its boolean value from TT and push onto stack

    //         }else if(c == '~'){
    //             //negate the next char and store in stack + index++

    //         }else{
    //             //it is an operator
    //             boolean operand1 = stack.pop();
    //             boolean operand2 = stack.pop();
    //             boolean result = evaluateOperation(operand1, operand2, c);
    //         }
    //     }
    // }

   
}
