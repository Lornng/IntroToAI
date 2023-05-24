public class Main{
    public static void main(String[] args){
        String filename = "Try2.txt";
        // test_HornKB.txt
        KnowledgeBase kb = new KnowledgeBase(filename);

        // GeneralKB gkb = new GeneralKB("Try2.txt");

        // gkb.getParsedSentences();

        // System.out.println("-----Truth Table-----");
        // TT tt = new TT(kb);

        // boolean entailTrue = tt.TT_ENTAILS();
        // if(entailTrue){
        //     int num_true = tt.getCounter();
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