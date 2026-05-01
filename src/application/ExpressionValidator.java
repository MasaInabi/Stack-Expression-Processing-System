package application;

//Masa Inabi
//1231024

public class ExpressionValidator {

    public static boolean validateBrackets(String EXPR) {
        Stack stakkExpresonValdatr  = new Stack();

        for (int i = 0; i < EXPR.length(); i++) {
            char CHARCT = EXPR.charAt(i);

            switch (CHARCT) {
                case '(':
                case '{':
                case '[':
                	stakkExpresonValdatr.push(CHARCT);
                    break;

                case ')':
                    if (stakkExpresonValdatr.isEmpty() || (char) stakkExpresonValdatr.pop() != '(') return false;
                    break;

                case '}':
                    if (stakkExpresonValdatr.isEmpty() || (char) stakkExpresonValdatr.pop() != '{') return false;
                    break;

                case ']':
                    if (stakkExpresonValdatr.isEmpty() || (char) stakkExpresonValdatr.pop() != '[') return false;
                    break;

                default:
                    break;
            }
        }
        return stakkExpresonValdatr.isEmpty();
    }
}
