import java.util.HashMap;
import java.util.HashSet;

public class TT {
    HashSet<String> symbols;
    HashMap<String, Boolean> model = new HashMap<>();
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

        for(int row = 0; row < rows; row++){
            for(int col = 0; col < numSymbols; col++){
                //calculate the truth value for the current variable 
                boolean truthValue = ((row >> (numSymbols - col - 1)) & 1) == 1;

                TT[row][col] = truthValue;

                if(TT[row][col] == true){
                    System.out.print("T");
                }else if (TT[row][col] == false){
                    System.out.print("F");
                }
            }
            System.out.println("");
        }
    }

    public void findTrueConfigs(){
        boolean 
    }

    // public boolean TT_CHECK_ALL(String kb, String query, HashSet<String> symbols, HashMap<String, Boolean> model){
    //     if(symbols.isEmpty()){
    //         if(){

    //         }
    //     }
    //     return true;
    // }
}
