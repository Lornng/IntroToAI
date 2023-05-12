public class Main{
    public static void main(String[] args){
        String filename = "test_HornKB.txt";
        KnowledgeBase KB = new KnowledgeBase(filename);
        TT tt = new TT();



        tt.TT_ENTAILS(KB.getSymbols(), KB.getQuery());
    }
}