package application;

import java.util.Stack;
// Masa Inabi
// 1231024
public class ConventionalEvaluator {

    public static int EvaluatePostfix(String EXPRR) {
        Stack<Integer> stakEvalutPostFXX = new Stack<>();
        String[] TOKNSS = EXPRR.trim().split("\\s+");

        for(String token : TOKNSS) {
            if(token.matches("\\d+")) {
            	stakEvalutPostFXX.push(Integer.parseInt(token));
            } else {
                int b = stakEvalutPostFXX.pop();
                int a = stakEvalutPostFXX.pop();
                switch(token) {
                    case "+": stakEvalutPostFXX.push(a + b);
                    break;
                    case "-": stakEvalutPostFXX.push(a - b);
                    break;
                    case "*": stakEvalutPostFXX.push(a * b);
                    break;
                    case "/": stakEvalutPostFXX.push(a / b);
                    break;
                    case "^": stakEvalutPostFXX.push((int)Math.pow(a, b));
                    break;
                }
            }
        }
        return stakEvalutPostFXX.pop();
    }

    public static int EvaluateInfix(String EXPRINFXX) {
        String postFXX = InfixToPostFix.convert(EXPRINFXX);
        return EvaluatePostfix(postFXX);
    }

    public static int EvaluatePrefix(String EXPRPREFXX) {
        String inFXX = PrefixToInfix.convert(EXPRPREFXX);
        return EvaluateInfix(inFXX);
    }
}
