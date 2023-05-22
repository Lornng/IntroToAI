import java.util.HashMap;
import java.util.ArrayList;

public class TT {
    private ArrayList<String> symbols;
    private HashMap<String, Boolean> symbolMap = new HashMap<>();
    private Boolean[][] TT;
    private String query;
    private KnowledgeBase kb;
    private int numSymbols, rows;
    private int counter;


    public TT(KnowledgeBase kb){
        this.kb = kb;
    }

    public boolean TT_ENTAILS(){
        this.symbols = kb.getSymbols();
        this.query = kb.getQuery();

        createTT(symbols);

        boolean entailTrue = TT_CHECK_ALL(kb.getSentences());

        return entailTrue;
    }

    public boolean TT_CHECK_ALL(String[] sentences){
        counter = 0;

        //checks each row of the truth table 
        for(int i = 0; i < this.rows; i ++){

            boolean kbTrue = true;

            for(String sentence : sentences){

                ArrayList<String> sentence_symbols = new ArrayList<>();

                if(sentence.contains("=>")){
                    String[] separated = sentence.split("=>");
                    String front = separated[0];
                    String back = separated[1];

                    if(front.contains("&")){
                        String[] clauses = front.split("&");
                        String clause_1 = clauses[0];
                        String clause_2 = clauses[1];

                        //three symbols
                        sentence_symbols.add(clause_1);
                        sentence_symbols.add(clause_2);
                        sentence_symbols.add(back);
                    }else{
                        //two symbols
                        sentence_symbols.add(front);
                        sentence_symbols.add(back);
                    }
                }else{
                    //one symbol
                    sentence_symbols.add(sentence);
                }
                //check the truth value of the sentence
                boolean result = sentence_TV(sentence_symbols, i);
                
                //if just one sentence returns false, the kb will be false
                if(!result){
                    kbTrue = false;
                    break;
                }
            }
            boolean queryTV = check_Query_TV(i);

            //if all sentences are true, we must check if the query is true or not
            if(kbTrue && queryTV){
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

    public boolean check_Query_TV(int curr_row){
        String toGet = this.query + "[" + curr_row + "]";
        boolean query_tv = symbolMap.get(toGet);

        return query_tv;
    }

    public boolean sentence_TV(ArrayList<String> sentence_symbols, int curr_row){


        //sentence contains only one symbol
        if(sentence_symbols.size() == 1){
            String toGet = sentence_symbols.get(0) + "[" + curr_row + "]";
            boolean symbol_tv = symbolMap.get(toGet);
            return symbol_tv;
        }
        
        //sentence has implication only
        else if(sentence_symbols.size() == 2){
            String toGet1 = sentence_symbols.get(0) + "[" + curr_row + "]";
            String toGet2 = sentence_symbols.get(1) + "[" + curr_row + "]";

            boolean symbol_1_tv = symbolMap.get(toGet1);
            boolean symbol_2_tv = symbolMap.get(toGet2);

            boolean sentence_tv = !symbol_1_tv || symbol_2_tv;
            return sentence_tv;
        }
        
        //sentence has conjunction and implication 
        else{
            String toGet1 = sentence_symbols.get(0) + "[" + curr_row + "]";
            String toGet2 = sentence_symbols.get(1) + "[" + curr_row + "]";
            String toGet3 = sentence_symbols.get(2) + "[" + curr_row + "]";

            boolean symbol_1_tv = symbolMap.get(toGet1);
            boolean symbol_2_tv = symbolMap.get(toGet2);
            boolean symbol_3_tv = symbolMap.get(toGet3);

            boolean sentence_tv = !(symbol_1_tv && symbol_2_tv) || symbol_3_tv;
            return sentence_tv;
        }
    }

    public void createTT(ArrayList<String> symbols){
        this.numSymbols = symbols.size();
        this.rows = (int) Math.pow(2, numSymbols);

        TT = new Boolean[rows][numSymbols];
        
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < numSymbols; col++){
                String symbolString = symbols.get(col);
                //calculate the truth value for the current variable 
                boolean truthValue = ((row >> (numSymbols - col - 1)) & 1) == 1;

                TT[row][col] = truthValue;

                // if(TT[row][col] == true){
                //     System.out.print("T");
                // }else if (TT[row][col] == false){
                //     System.out.print("F");
                // }

                //assign truthValue to the symbol in the map
                symbolMap.put(symbolString + "[" + row + "]", truthValue);

                // boolean symbolValue = symbolMap.get(symbolString);
                // System.out.println(symbolString + ": ");
                // System.out.println(symbolValue ? "T" : "F");

            }
            // System.out.println("");
        }
        // System.out.println(symbols);
        // System.out.println(symbolMap.get("b[3]"));
    }

    public int getCounter(){
        return counter;
    }
}
