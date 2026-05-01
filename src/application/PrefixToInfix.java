package application;

import java.util.Stack;
import java.util.StringTokenizer;
import java.util.LinkedList;

//Masa Inabi
//1231024

public class PrefixToInfix {

    private static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("^");
    }

    public static String convert(String expr) {
        Stack<String> stack = new Stack<>();
        StringTokenizer st = new StringTokenizer(expr);
        LinkedList<String> tokens = new LinkedList<>();

        while(st.hasMoreTokens())
        	tokens.addFirst(st.nextToken());

        for(String token : tokens) {
            if(!isOperator(token)) 
            	stack.push(token);
            else {
                String a = stack.pop();
                String b = stack.pop();
                stack.push("(" + a + " " + token + " " + b + ")");
            }
        }
        return stack.pop();
    }
}



//Masa Inabi
//1231024

 class PrefixToPostfix {

    private static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("^");
    }

    public static String convert(String expr) {
        Stack<String> stack = new Stack<>();
        StringTokenizer st = new StringTokenizer(expr);
        LinkedList<String> tokens = new LinkedList<>();

        while(st.hasMoreTokens())
        	tokens.addFirst(st.nextToken());

        for(String token : tokens) {
            if(!isOperator(token))
            	stack.push(token);
            else {
                String a = stack.pop();
                String b = stack.pop();
                stack.push(a + " " + b + " " + token);
            }
        }
        return stack.pop();
    }
}






