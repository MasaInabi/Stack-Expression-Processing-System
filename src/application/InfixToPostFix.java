package application;

import java.util.Stack;

//Masa Inabi
//1231024

public class InfixToPostFix {

    private static boolean isOperator(char CHRC) {
        return CHRC=='+' || CHRC=='-' || CHRC=='*' || CHRC=='/' || CHRC=='^';
    }

    private static int precedence(char CHRC) {
        if(CHRC=='^') return 3;
        if(CHRC=='*' || CHRC=='/')
        	return 2;
        if(CHRC=='+' || CHRC=='-') 
        	return 1;
        return 0;
    }

    public static String convert(String EXPR) {
        Stack<Character> stack = new Stack<>();
        StringBuilder result = new StringBuilder();

        for(int i=0; i<EXPR.length(); i++) {
            char ch = EXPR.charAt(i);

            if(Character.isDigit(ch)) {
                StringBuilder num = new StringBuilder();
                while(i<EXPR.length() && Character.isDigit(EXPR.charAt(i))) {
                    num.append(EXPR.charAt(i));
                    i++;
                }
                i--;
                result.append(num).append(" ");
            }
            else if(ch=='(') stack.push(ch);
            else if(ch==')') {
                while(!stack.isEmpty() && stack.peek()!='(')
                    result.append(stack.pop()).append(" ");
                stack.pop();
            }
            else if(isOperator(ch)) {
                while(!stack.isEmpty() && precedence(stack.peek()) >= precedence(ch))
                    result.append(stack.pop()).append(" ");
                stack.push(ch);
            }
        }
        while(!stack.isEmpty())
            result.append(stack.pop()).append(" ");
        return result.toString().trim();
    }
}




