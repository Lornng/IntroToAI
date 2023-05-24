import java.util.ArrayList;
import java.util.LinkedHashSet;

public class BC {
    private String query;
    private String[] sentences;
    private LinkedHashSet<String> facts, checkrightsymbols;
    private ArrayList<String> goals;

    private ArrayList<String> result;

    public BC (KnowledgeBase kb){
        this.query = kb.getQuery();
        this.sentences = kb.getSentences();
        facts = new LinkedHashSet<>();
        checkrightsymbols = new LinkedHashSet<>();
        goals = new ArrayList<>();
        result = new ArrayList<>();
    }

    public void BC_Check(){
        goals.add(0, query);
        facts = Check_Facts(sentences, facts);
        boolean cont = true;
        
        //check if facts contain the query
        if (facts.contains(query)){
            result.add(0, query);
            // System.out.println("Facts contain query");
            printResult();
            cont = false;
        }
        int previousSize = -1;
        while (!goals.isEmpty() && cont){
            String goal = goals.remove(0);

            //stop infinite loop
            // if(result.contains(goal)){
            //     System.out.println("Infinite loop");
            //     cont = true;
            //     break;
            // }

            result.add(goal);
            
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
                        if (Check_Left(left_symbols)){
                            facts.add(goal);
                            if(goals.isEmpty()){
                                printResult();
                                cont=false;
                                break;
                            }
                        }
                    //if rightside doesnt contain the symbol
                    }
                }
            }
            if (!checkrightsymbols.contains(goal)){
                System.out.println("Rightside no this symbol");
                break;
            }
            //stop infinite loop
            // if (result.size() == previousSize){
            //     System.out.println("Infinite loop!! NO");
            // }
            // previousSize = result.size();
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
    public boolean Check_Left(ArrayList<String> left_symbols){
        boolean leftSymbolsCheck = true;
        //add the left symbols that not part of facts to goals for check
        for (String left : left_symbols){
            if (!facts.contains(left)){
                leftSymbolsCheck = false;
                goals.add(0, left);
            }
            else{
                //left symbols part of facts add into result
                result.add(left);
            }
        }

        if(leftSymbolsCheck){
            for(String left : left_symbols){
                if(!result.contains(left))
                result.add(left);
            }
        }

        return leftSymbolsCheck;
    }

    public void printResult(){
        System.out.println("YES: " + result);
    }
}
