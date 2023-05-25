    import java.util.ArrayList;
    import java.util.LinkedHashSet;

    public class FC{

        private LinkedHashSet<String> queue;
        private String query;
        private String[] sentences;
        
        public FC (HornKB kb){
            this.query = kb.getQuery();
            this.sentences = kb.getSentences();
            queue = new LinkedHashSet<>();
        }

        public void FC_Check() {
            int previousSize = -1; // Previous queue size
            while (!queue.contains(query)) {
                for (String sentence : sentences) {
                    // Check if query found
                    if (queue.contains(query)) {
                        break;
                    }
    
                    // Check the symbols
                    ArrayList<String> left_symbols = new ArrayList<>();
                    if (!sentence.contains("=>") && !sentence.contains("&")) {
                        queue.add(sentence);
                    } 
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
                        Check_symbols(left_symbols, rightside, sentence);
                    }
                }
    
                // Check if the current queue size is the same as the previous size
                if (queue.size() == previousSize) {
                    System.out.println("NO");
                    break;
                }
    
                previousSize = queue.size(); // Update the previous queue size
            }
    
            // Check if query found after the loop
            if (queue.contains(query)) {
                System.out.print("YES: ");
                System.out.println(queue);
                // for (String item : queue) {
                //     System.out.print(item + "; ");
                // }
            }
        }
        
        public void Check_symbols(ArrayList<String> left_symbols, String rightside, String sentence){
            boolean allSymbolsContained = true;
            for(String left : left_symbols){
                if (!queue.contains(left)){
                    allSymbolsContained = false;
                    break;
                }
            }
            if (allSymbolsContained){
                queue.add(rightside);
            }
        }
        
    }