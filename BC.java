import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Stack;

public class BC {
    private String query;
    private String[] sentences;
    private LinkedHashSet<String> facts;
    private Stack<String> goals;

    private ArrayList<String> result;

    public BC (KnowledgeBase kb){
        this.query = kb.getQuery();
        this.sentences = kb.getSentences();
        facts = new LinkedHashSet<>();
        goals = new Stack<>();
        result = new ArrayList<>();
    }

    public void BC_Check(){
        goals.add(query);
        facts = Check_Facts(sentences, facts);

        while (!goals.isEmpty()){
            System.out.println("Facts: " +facts);
            System.out.println("Goals: " +goals);

            String goal = goals.pop();
            result.add(goal);

            for (String sentence : sentences){

                ArrayList<String> left_symbols = new ArrayList<>();
                
                if (sentence.contains("=>")) {
                    String[] senSplit = sentence.split("=>");
                    String leftside = senSplit[0];
                    String rightside = senSplit[1];
    
                    if (leftside.contains("&")) {
                        String[] clauses = leftside.split("&");
                        String clause_1 = clauses[0];
                        String clause_2 = clauses[1];
    
                        left_symbols.add(clause_1);
                        left_symbols.add(clause_2);
                    } else {
                        left_symbols.add(leftside);
                    }
                    if (rightside.equals(goal)){
                        if (Check_Left(left_symbols)){
                            facts.add(goal);
                            System.out.println("Added: " + goal);

                            System.out.print("sequence: ");
                            System.out.println(result);

                            goals.clear();
                        }
                    }
                }
            }
        }
        System.out.println("Query not found!");
    }

    public LinkedHashSet<String> Check_Facts(String[] sentences, LinkedHashSet<String> facts){
        for (String sentence : sentences){
            if (!sentence.contains("=>") && !sentence.contains("&")) {
                facts.add(sentence);
            }
        }
        return facts;
    }

    public boolean Check_Left(ArrayList<String> left_symbols){
        boolean leftSymbolsCheck = true;
        for (String left : left_symbols){
            if (!facts.contains(left)){
                leftSymbolsCheck = false;
                goals.push(left);
                System.out.println("Pushed: " + left);
                // System.out.println("2. " + goals);
            }
        }

        if(leftSymbolsCheck){
            for(String left : left_symbols){
                result.add(left);
            }
        }

        return leftSymbolsCheck;
    }

}
