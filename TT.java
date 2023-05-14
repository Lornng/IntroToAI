import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class TT {
    HashSet<String> symbols;
    HashMap<String, Boolean> symbolMap = new HashMap<>();
    Boolean[][] TT;
    String query;

    public void TT_ENTAILS(HashSet<String> symbols, String query){
        
        this.symbols = symbols;
        this.query = query;
        createTT(symbols);

        //return TT_CHECK_ALL(kb, query, symbols, model);
    }

    public void createTT(HashSet<String> symbols){
        int numSymbols = symbols.size();
        int rows = (int) Math.pow(2, numSymbols);

        TT = new Boolean[rows][numSymbols];

        // Create a list of symbols to iterate over (to maintain order)
        List<String> symbolList = new ArrayList<>(symbols);
        
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < numSymbols; col++){
                String symbolString = symbolList.get(col);
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
            System.out.println("");
            ValidateValue();
        }
        System.out.println(symbolList);
        System.out.println(symbolMap.get("h[2046]"));
    }

    public void ValidateValue(){

    }

    // public boolean TT_CHECK_ALL(String kb, String query, HashSet<String> symbols, HashMap<String, Boolean> model){
    //     if(symbols.isEmpty()){
    //         if(){

    //         }
    //     }
    //     return true;
    // }
}
