import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Parser {
    private ArrayList<String> operators;
    private ArrayList<String> precedence = new ArrayList<>(Arrays.asList("&", "|"));

    public Parser(){
        operators = new ArrayList<>();
        operators.addAll(Arrays.asList("&", "=", "<", "~", "|", "(", ")"));
    }

    public ArrayList<String> parseKB(String sentence){
        Stack<String> stack = new Stack<>();
        ArrayList<String> queue = new ArrayList<>();

        System.out.println(sentence);

        for(int index = 0; index < sentence.length(); index++){
            char c = sentence.charAt(index);
            String current = "" + c;

            if(operators.contains(current)){

                if(current.equals("(")){
                    stack.add(current);
                    continue;
                }

                if(current.equals(")")){
                    while(!stack.peek().equals("(")){
                        String popped = stack.pop();
                        queue.add(popped);
                    }
                    stack.pop();
                    continue;
                }
            
                //check for precedence 
                if(current.equals("~")){
                    queue.add(current);
                }else if(!stack.isEmpty() && precedence.contains(stack.peek()) && !precedence.contains(current)){
                    String popped = stack.pop();
                    queue.add(popped);
                    stack.push(current);
                }else{
                    stack.push(current);
                }

                //Check if the operator is more than a single character long
                if(current.equals("=") || current.equals("|")){
                    index ++;
                }

                if(current.startsWith("<")){
                    index += 2;
                }
            }else{
                queue.add(current);
            }
            // System.out.println("Queue" + queue);
            // System.out.println("Stack" + stack);
        }

        while(!stack.isEmpty()){
            String popped = stack.pop();
            queue.add(popped);
        } 

        return queue;
    }
}
