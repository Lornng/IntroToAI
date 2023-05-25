import java.util.ArrayList;
import java.util.LinkedHashSet;

public class BC {
    private String query;
    private String[] sentences;
    private LinkedHashSet<String> facts, checkrightsymbols;
    private ArrayList<String> goals;
    private ArrayList<String> queue;

    public BC (HornKB kb){
        this.query = kb.getQuery();
        this.sentences = kb.getSentences();
        facts = new LinkedHashSet<>();
        checkrightsymbols = new LinkedHashSet<>();
        goals = new ArrayList<>();
        queue = new ArrayList<>();
    }

    public void BC_Check(){
        goals.add(0, query);
        facts = Check_Facts(sentences, facts);
        boolean cont = true;
        
        //check if facts contain the query
        if (facts.contains(query)){
            queue.add(0, query);
            // System.out.println("Facts contain query");
            printQueue();
            cont = false;
        }
        int previousSize = -1;
        while (!goals.isEmpty() && cont){
            String goal = goals.remove(0);
            if(!queue.contains(goal)){
                queue.add(goal);
            }
            
            for (String sentence : sentences){
                ArrayList<String> left_symbols = new ArrayList<>();
                if (sentence.contains("=>")) {
                    String[] senSplit = sentence.split("=>");
                    String leftside = senSplit[0];
                    String rightside = senSplit[1];
                    checkrightsymbols.add(rightside);

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
                        if (checkLeft(left_symbols)){
                            facts.add(goal);
                            if(goals.isEmpty()){
                                printQueue();
                                cont=false;
                                break;
                            }
                        }
                    }
                }
            }
            if (!checkrightsymbols.contains(goal)){
                System.out.println("Rightside no this symbol");
                break;
            }
            //stop infinite loop
            if (queue.size() == previousSize){
                break;
            }
            previousSize = queue.size();
        }
        if(cont){
            System.out.println("NO");
        }
    }

    public LinkedHashSet<String> Check_Facts(String[] sentences, LinkedHashSet<String> facts){
        for (String sentence : sentences){
            if (!sentence.contains("=>") && !sentence.contains("&")) {
                facts.add(sentence);
            }
        }
        return facts;
    }

    //Check the left hand side symbols 
    public boolean checkLeft(ArrayList<String> left_symbols){
        boolean leftSymbolsCheck = true;
        //add the left symbols that not part of facts to goals for check
        for (String left : left_symbols){
            if (!facts.contains(left)){
                leftSymbolsCheck = false;
                goals.add(0, left);
            }
            else{
                //left symbols part of facts add into result
                if(!queue.contains(left)){
                    queue.add(left);
                }
            }
        }
        if(leftSymbolsCheck){
            for(String left : left_symbols){
                if(!queue.contains(left))
                queue.add(left);
            }
        }
        return leftSymbolsCheck;
    }

    public void printQueue(){
        System.out.println("YES: " + queue);
    }
}
