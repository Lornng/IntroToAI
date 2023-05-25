import java.util.ArrayList;

public class Main{
    public static void main(String[] args){
        String filename = "test_HornKB.txt";
        // test_HornKB.txt
        // HornKB hKb = new HornKB(filename);
        // System.out.println(hKb.getSymbols());
        //TT tt_1 = new TT(hKb);

        GeneralKB gKb = new GeneralKB("Try2.txt");
        gKb.createTT();

        ArrayList<ArrayList<String>> results = gKb.getParsedSentences();

        for(ArrayList<String> parsed : results){
            System.out.println(parsed);
        }

        Boolean result = gKb.evaluatePostfix();

        if(result){
            System.out.println("YES: " + gKb.getCounter());
        }else{
            System.out.println("NO");
        }

        // System.out.println(gKb.getSymbols());
        // gKb.getParsedSentences();

        // System.out.println("-----Truth Table-----");
        // TT tt = new TT(kb);

        // boolean entailTrue = tt_1.TT_ENTAILS();
        // if(entailTrue){
        //     int num_true = tt_1.getCounter();
        //     System.out.println("YES: " + num_true);
        // }else{
        //     System.out.println("NO");
        // }

        // // //Test forward chaining
        // System.out.println("-----Forward chaining-----");
        // FC fc = new FC(kb);
        // fc.FC_Check();
        
        // System.out.println("-----Backward chaining-----");
        // BC bc = new BC(kb);
        // bc.BC_Check();
    }
}