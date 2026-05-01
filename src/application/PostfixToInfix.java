package application;

import java.util.Stack;
import java.util.StringTokenizer;

//Masa Inabi
//1231024

public class PostfixToInfix {

    private static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("^");
    }

    public static String convert(String expr) {
        Stack<String> stack = new Stack<>();
        StringTokenizer st = new StringTokenizer(expr);

        while(st.hasMoreTokens()) {
            String token = st.nextToken();
            if(!isOperator(token))
            	stack.push(token);
            else {
                String b = stack.pop();
                String a = stack.pop();
                stack.push("(" + a + " " + token + " " + b + ")");
            }
        }
        return stack.pop();
    }
}
