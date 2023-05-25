import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Stack;

public class BC1 {
    private String query;
    private String[] sentences;
    private LinkedHashSet<String> facts, visited, backups, checkrightsymbols;
    private ArrayList<String> queue;
    private Stack<String> toChecks;
    private boolean error, answer;

    public BC1 (HornKB kb){
        this.query = kb.getQuery();
        this.sentences = kb.getSentences();
        facts = new LinkedHashSet<>();
        backups = new LinkedHashSet<>();
        visited = new LinkedHashSet<>();
        checkrightsymbols = new LinkedHashSet<>();
        queue = new ArrayList<>();
        toChecks = new Stack<>();
        error = true;
        answer = false;
    }

    public void  BC1_Check(){
        facts = getFacts(sentences, facts);
        toChecks.push(query);

        //if the facts contain the query
        if (facts.contains(query)){
            System.out.println("Facts contain query");
            queue.add(query);
            error = false;
            answer = true;
            printQueue();
        }

        while(!toChecks.isEmpty() && error){
            String toCheck = toChecks.pop();
            if (!queue.contains(toCheck)){
                queue.add(toCheck);
            }

            for (String sentence : sentences){
                if (sentence.contains("=>")){
                    String[] splitlist = sentence.split("=>");
                    String leftside = splitlist[0];
                    String rightside = splitlist[1];
                    checkrightsymbols.add(rightside);
                    if (toCheck.equals(rightside) && !visited.contains(rightside)){
                        visited.add(rightside);
                        String[] leftsplit = leftside.split("&");
                        if(checkLeft(leftsplit)){
                            facts.add(rightside);
                            backups.add(rightside);
                            if(toChecks.isEmpty()){
                                printQueue();
                                System.out.println("xxxx");
                                error = false;
                                answer = true;
                                break;
                            }
                        }
                    }
                    checkValidRight(leftside.split("&"), rightside);
                }
            }
            if (!checkrightsymbols.contains(toCheck)){
                System.out.println("Rightside no this symbol");
                break;
            }
        }

            //this is a way to backup prevention 
            if (toChecks.isEmpty() && !answer && backups.contains(query)) {
                // Check if facts contain all visited elements
                boolean allVisited = true;
                for (String visitedSymbol : visited) {
                    if (!backups.contains(visitedSymbol)) {
                        allVisited = false;
                        break;
                    }
                }
                if (allVisited) {
                    printQueue();
                    System.out.println("All visited");
                    error = false;
                }
            }
        
        if (error){
            System.out.println("NO");
        }
    }   
    
    public LinkedHashSet<String> getFacts(String[] sentences, LinkedHashSet<String> facts){
        for (String sentence : sentences){
            if (!sentence.contains("=>") && !sentence.contains("&")) {
                facts.add(sentence);
                backups.add(sentence);
            }
        }
        return facts;
    }

    public boolean checkLeft(String[] leftsplit){
        boolean contains = true;
        for (String symbol :leftsplit){
            if (!facts.contains(symbol)){
                contains = false;
                toChecks.add(symbol);
            } else {
                if(!queue.contains(symbol)){
                    queue.add(symbol);
                }
            }
        }
        return contains;
    }

    public boolean checkValidRight(String[] leftsplit, String rightside){
        boolean containsAllLeft = true;
        for (String symbol : leftsplit) {
            if (!backups.contains(symbol)) {
                containsAllLeft = false;
                break;
            }
        }
        if(containsAllLeft){
            backups.add(rightside);
        }
        return containsAllLeft;
    }

    public void printQueue(){
        System.out.println("YES: " + queue);
    }
}
