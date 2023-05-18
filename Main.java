public class Main{
    public static void main(String[] args){
        String filename = "test_HornKB.txt";
        KnowledgeBase kb = new KnowledgeBase(filename);
        // TT tt = new TT(kb);

        // boolean entailTrue = tt.TT_ENTAILS();
        // if(entailTrue){
        //     int num_true = tt.getCounter();
        //     System.out.println("YES: " + num_true);
        // }else{
        //     System.out.println("NO");
        // }

        //Test forward chaining
        // FC fc = new FC(kb);
        // fc.FC_Check();

        BC bc = new BC(kb);
        bc.BC_Check();
    }
}