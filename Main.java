import java.util.ArrayList;

public class Main{
    public static void main(String[] args){
        String filename = "Try20.txt";
        // test_HornKB.txt
        //HornKB hKb = new HornKB(filename);
        // System.out.println(hKb.getSymbols());
        //TT tt_1 = new TT(hKb);

        GeneralKB gKb = new GeneralKB("Try2.txt");
        // gKb.createTT();

        // // for(ArrayList<String> parsed : results){
        // //     System.out.println(parsed);
        // // }

        Boolean result = gKb.evaluatePostfix();

        if(result){
            System.out.println("YES: " + gKb.getCounter());
        }else{
            System.out.println("NO");
        }

        // System.out.println(gKb.getSymbols());
        // gKb.getParsedSentences();

        // System.out.println("-----Truth Table-----");
        // //TT tt = new TT(hKb);

        // boolean entailTrue = tt_1.TT_ENTAILS();
        // if(entailTrue){
        //     int num_true = tt_1.getCounter();
        //     System.out.println("YES: " + num_true);
        // }else{
        //     System.out.println("NO");
        // }

        // // //Test forward chaining
        // System.out.println("-----Forward chaining-----");
        // FC fc = new FC(hKb);
        // fc.FC_Check();
        
<<<<<<< Updated upstream
        System.out.println("-----Backward chaining-----");
        BC1 bc = new BC1(hKb);
        bc.BC1_Check();
=======
        // System.out.println("-----Backward chaining-----");
        // BC bc = new BC(hKb);
        // bc.BC_Check();
>>>>>>> Stashed changes
    }
}