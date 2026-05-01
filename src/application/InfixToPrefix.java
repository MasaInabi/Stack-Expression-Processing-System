package application;

import java.util.Stack;

//Masa Inabi
//1231024


public class InfixToPrefix {

    private static boolean isOperator(char CHRT) {
        return CHRT=='+' || CHRT=='-' || CHRT=='*' || CHRT=='/' || CHRT=='^';
    }

    private static int precedence(char c) {
        if(c=='^') 
        	return 3;
        if(c=='*' || c=='/')
        	return 2;
        if(c=='+' || c=='-')
        	return 1;
        return 0;
    }

    public static String convert(String expr) {
        StringBuilder reversed = new StringBuilder(expr).reverse();
        Stack<Character> stack = new Stack<>();
        StringBuilder prefix = new StringBuilder();

        for(int i=0; i<reversed.length(); i++) {
            char ch = reversed.charAt(i);

            if(Character.isDigit(ch)) {
                StringBuilder num = new StringBuilder();
                int j = i;
                while(j<reversed.length() && Character.isDigit(reversed.charAt(j))) {
                    num.append(reversed.charAt(j));
                    j++;
                }
                i = j - 1;
                prefix.insert(0, num + " ");
            }
            else if(ch==')') stack.push(ch);
            else if(ch=='(') {
                while(!stack.isEmpty() && stack.peek()!=')')
                    prefix.insert(0, stack.pop() + " ");
                stack.pop();
            }
            else if(isOperator(ch)) {
                while(!stack.isEmpty() && precedence(stack.peek()) > precedence(ch))
                    prefix.insert(0, stack.pop() + " ");
                stack.push(ch);
            }
        }
        while(!stack.isEmpty())
            prefix.insert(0, stack.pop() + " ");
        return prefix.toString().trim();
    }
}
