import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;

public class Parser {
    private ArrayList<String> operators;
    private ArrayList<String> precedence = new ArrayList<>(Arrays.asList("&", "||"));

    public Parser(){
        operators = new ArrayList<>();
        operators.addAll(Arrays.asList("&", "=>", "<=>", "~", "||", "(", ")"));
    }

    public ArrayList<String> parseKB(String sentence){
        Stack<String> stack = new Stack<>();
        LinkedList<String> queue = new LinkedList<>();

        for(int index = 0; index < sentence.length(); index++){
            char c = sentence.charAt(index);
            String current = "" + c;

                if(operators.contains(c)){
                    //check for precedence 
                    if(current == "~"){
                        queue.add(current);
                    }
                    else if(precedence.contains(stack.peek()) && !precedence.contains(current)){
                        queue.add(stack.pop());
                        stack.push(current);
                    }else{
                        stack.push(current);
                    }

                    //Check if the operator is more than a single character long
                    if(current == "=" || current == "|"){
                        index ++;
                    }

                    if(current == "<"){
                        index += 2;
                    }
                }else if(operators == ){

                }
            }
        }
        
                
        
        return;
    }

    //TRUE means change, FALSE means no change 
    public boolean checkPrecedence(char current, char stack){

        boolean stack_pop = false;
    
        //Operand in the stack has a higher precedence 
        if(!precedence.contains(current) && precedence.contains(stack)){
            return false;
        }

        //Current operand has a higher precedence than the one on the stack 
        if(precedence.contains(current) && !precedence.contains(stack)){
            return false;
        }

        //Both operands are of the same precedence
        if(precedence.contains(current) && precedence.contains(stack)){
            return false;
        }

        //Both operands are the lower precedence
        if(!precedence.contains(current) && !precedence.contains(stack)){
            return false;
        }
    }
}
