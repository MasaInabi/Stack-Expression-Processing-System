package application;
import java.io.File;
import java.util.Scanner;

//Masa Inabi
//1231024

public class NotationDomain {

    private LinkedList OPERNDS;   

    public NotationDomain() {
        OPERNDS = new LinkedList();
    }

    public void addOperand(String ADDOPRND) {
        OPERNDS.addLast(ADDOPRND);
    }

    public void removeOperand(String OPRND) {
        Node CURR = OPERNDS.head;
        Node PRV = null;
        while (CURR != null) {
            if (CURR.element.equals(OPRND)) {
                if (PRV == null)
                	OPERNDS.head = CURR.next;
                else PRV.next = CURR.next;
                break;
            }
            PRV = CURR;
            CURR = CURR.next;
        }
    }

    public void updateOperand(String OLDVALU, String NEWVALU) {
        Node curr = OPERNDS.head;
        while (curr != null) {
            if (curr.element.equals(OLDVALU)) {
                curr.element = NEWVALU;
                break;
            }
            curr = curr.next;
        }
    }

    public String getOperandsString() {
        String s = "";
        Node curr = OPERNDS.head;
        while (curr != null) {
            s += curr.element + " ";
            curr = curr.next;
        }
        return s.trim();
    }

    public void clear() {
        OPERNDS = new LinkedList();
    }

    public void loadDomainFile(String filePath) {
        try {
            Scanner SCR = new Scanner(new File(filePath));
            while (SCR.hasNext()) {
                String token = SCR.next();
                OPERNDS.addLast(token);
            }
            SCR.close();
        } catch (Exception EXCPN) {
            System.out.println("Error loading domain file: " + EXCPN.getMessage());
        }
    }

    public boolean isValidOperand(String OPRND) {
        Node CRR = OPERNDS.head;
        while (CRR != null) {
            if (CRR.element.equals(OPRND))
            	return true;
            CRR = CRR.next;
        }
        return false;
    }
}





//Masa Inabi
//1231024

 class OperatorPrecedence {

    private LinkedList operators; 

    private class PairNode {
        public char OPRNDCHAR;
        public int priority;
        public PairNode(char oCH, int p) { OPRNDCHAR = oCH; priority = p; }
    }

    public OperatorPrecedence() {
        operators = new LinkedList();
    }

    public void addOperator(char op, int priority) {
        operators.addLast(new PairNode(op, priority));
    }

    public void removeOperator(char op) {
        Node CRR = operators.head;
        Node prev = null;
        while (CRR != null) {
            PairNode p = (PairNode) CRR.element;
            if (p.OPRNDCHAR == op) {
                if (prev == null) operators.head = CRR.next;
                else prev.next = CRR.next;
                break;
            }
            prev = CRR;
            CRR = CRR.next;
        }
    }

    public void updateOperator(char op, int newPriority) {
        Node curr = operators.head;
        while (curr != null) {
            PairNode p = (PairNode) curr.element;
            if (p.OPRNDCHAR == op) {
                p.priority = newPriority;
                break;
            }
            curr = curr.next;
        }
    }

    public String getOperatorsString() {
        String s = "";
        Node curr = operators.head;
        while (curr != null) {
            PairNode p = (PairNode) curr.element;
            s += p.OPRNDCHAR + "(" + p.priority + ") ";
            curr = curr.next;
        }
        return s.trim();
    }

    public void clear() { operators = new LinkedList(); }

    public int getPriority(char op) {
        Node curr = operators.head;
        while (curr != null) {
            PairNode p = (PairNode) curr.element;
            if (p.OPRNDCHAR == op) return p.priority;
            curr = curr.next;
        }
        return -1;
    }

    public boolean isOperator(char op) {
        Node curr = operators.head;
        while (curr != null) {
            PairNode p = (PairNode) curr.element;
            if (p.OPRNDCHAR == op) return true;
            curr = curr.next;
        }
        return false;
    }

    public void loadPrecedenceFile(String filePath) {
        try {
            Scanner sc = new Scanner(new File(filePath));
            while (sc.hasNext()) {
                String opToken = sc.next();
                if (!sc.hasNext()) break;
                int pr = Integer.parseInt(sc.next());
                addOperator(opToken.charAt(0), pr);
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("Error loading precedence file: " + e.getMessage());
        }
    }
}

 
 

//Masa Inabi
//1231024

 class CustomValidator {

   private static String ERRORRR = "";

   public static boolean validate(String EXpr, NotationDomain domain, OperatorPrecedence precedence) {
       ERRORRR = "";

       if (EXpr == null || EXpr.trim().isEmpty()) {
           ERRORRR = "Expression is empty";
           return false;
       }

       LinkedList toKN = tokenLst(EXpr, domain, precedence);

       if (toKN.size() == 0) {
           ERRORRR = "No token found";
           return false;
       }

       Stack BigStACK = new Stack();
       boolean expectOperand = true;

       for (int i = 0; i < toKN.size(); i++) {
           String tOfVLDT = (String) toKN.get(i);

           if (tOfVLDT.equals("(") || tOfVLDT.equals("{") || tOfVLDT.equals("[")) {
               BigStACK.push(tOfVLDT);
               expectOperand = true;
               continue;
           }

           if (tOfVLDT.equals(")") || tOfVLDT.equals("}") || tOfVLDT.equals("]")) {
               if (BigStACK.isEmpty()) {
                   ERRORRR = "Mismatched closing parenthesis: " + tOfVLDT;
                   return false;
               }

               String OPEN = (String) BigStACK.pop();

               if (!match(OPEN, tOfVLDT)) {
                   ERRORRR = "Mismatched parentheses: " + OPEN + " and " + tOfVLDT;
                   return false;
               }

               expectOperand = false;
               continue;
           }

           if (tOfVLDT.length() == 1 && precedence.isOperator(tOfVLDT.charAt(0))) {
               if (expectOperand) {
                   ERRORRR = "Unexpected operator '" + tOfVLDT + "'";
                   return false;
               }

               expectOperand = true;
               continue;
           }

           if (domain.isValidOperand(tOfVLDT)) {
               if (!expectOperand) {
                   ERRORRR = "Unexpected operand '" + tOfVLDT + "'";
                   return false;
               }
               expectOperand = false;
               continue;
           }

           ERRORRR = "Unknown token: " + tOfVLDT;
           return false;
       }

       if (!BigStACK.isEmpty()) {
           ERRORRR = "Unclosed parentheses.";
           return false;
       }

       if (expectOperand) {
           ERRORRR = "Expression ends unexpectedly with an operator.";
           return false;
       }

       return true;
   }

   public static String getErrorMessage() {
       return ERRORRR;
   }

   private static boolean match(String OPEN, String CLOSE) {
       return (OPEN.equals("(") && CLOSE.equals(")"))
               || (OPEN.equals("{") && CLOSE.equals("}"))
               || (OPEN.equals("[") && CLOSE.equals("]"));
   }

   private static LinkedList tokenLst(String expr, NotationDomain domain, OperatorPrecedence precedence) {
       LinkedList linkedLSTOfTKN = new LinkedList();
       String current = "";

       for (int i = 0; i < expr.length(); i++) {
           char CHARC = expr.charAt(i);

           if (Character.isWhitespace(CHARC)) {
               if (!current.equals("")) {
                   linkedLSTOfTKN.addLast(current);
                   current = "";
               }
               continue;
           }

           if (CHARC == '(' || CHARC == ')' || CHARC == '{' || CHARC == '}' || CHARC == '[' || CHARC == ']') {
               if (!current.equals("")) {
                   linkedLSTOfTKN.addLast(current);
                   current = "";
               }
               linkedLSTOfTKN.addLast("" + CHARC);
               continue;
           }

           if (precedence.isOperator(CHARC)) {
               if (!current.equals("")) {
                   linkedLSTOfTKN.addLast(current);
                   current = "";
               }
               linkedLSTOfTKN.addLast("" + CHARC);
               continue;
           }

           current += CHARC;
       }

       if (!current.equals(""))
           linkedLSTOfTKN.addLast(current);

       return linkedLSTOfTKN;
   }
}

 
 

//Masa Inabi
//1231024

 class CustomConverter {

   private static OperatorPrecedence precdnce;

   public static void setPrecedence(OperatorPrecedence p) {
       precdnce = p;
   }

   private static int precedence(char charCtr) {
       return precdnce.getPriority(charCtr);
   }

   private static boolean isOperator(char charCTr) {
       return precdnce.isOperator(charCTr);
   }

   // ******************************** INFXX -->POSTFXX *********************************************
   
   public static String infixToPostfix(String EXPRINPOS) {
       Stack stak = new Stack();
       String OUTSTK = "";

       for (int i = 0; i < EXPRINPOS.length(); i++) {
           char CHRCT= EXPRINPOS.charAt(i);

           if (CHRCT == ' ' || CHRCT == '\t')
               continue;

           if (Character.isLetterOrDigit(CHRCT))
           	OUTSTK += CHRCT;
           else if (CHRCT == '(') stak.push(CHRCT);
           else if (CHRCT == ')') {
               while (!stak.isEmpty() && (char)
               		stak.top() != '(')
               	OUTSTK += (char) stak.pop();
               stak.pop();
           }
           else if (isOperator(CHRCT)) {
               while (!stak.isEmpty() && isOperator((char)
               		stak.top()) &&
                      precedence((char)
                   		 stak.top()) >= precedence(CHRCT)) {
               	OUTSTK += (char)
               			stak.pop();
               }
               stak.push(CHRCT);
           }
       }

       while (!stak.isEmpty())
       	OUTSTK += (char) stak.pop();

       return "EXPRESSION:   " + EXPRINPOS + "\nINFIX:        " + EXPRINPOS + "\nPOSTFIX:      " + OUTSTK;
   }

   // **************************************** INFXX--->PREFXX *********************************************
   
   public static String infixToPrefix(String EXPRINFXPREFX) {
       String RVRSD = reverse(EXPRINFXPREFX);
       RVRSD = swapBrackets(RVRSD);
       String postFXX = infixToPostfix(RVRSD).split("POSTFIX:")[1].trim(); 
       String preFXX = reverse(postFXX);
       return "EXPRESSION:   " + EXPRINFXPREFX + "\nINFIX:        " + EXPRINFXPREFX + "\nPREFIX:       " + preFXX;
   }

   // **************************************** POSTFXX-->INFXX ******************************************
   
   public static String postfixToInfix(String EXPRPOSINFXX) {
       Stack stak = new Stack();

       for (int i = 0; i < EXPRPOSINFXX.length(); i++) {
           char CHARC = EXPRPOSINFXX.charAt(i);
           if (Character.isLetterOrDigit(CHARC)) {
               stak.push("" + CHARC);
           } else if (isOperator(CHARC)) {
               String b = (String)
               		stak.pop();
               String a = (String)
               		stak.pop();
               stak.push( a + CHARC + b  );
           }
       }
       String result = (String) stak.pop();
       return "EXPRESSION:   " + EXPRPOSINFXX + "\nPOSTFIX:      " + EXPRPOSINFXX + "\nINFIX:        " + result;
   }

//*************************************** POSTFXX --> PREFXX ***********************************************
   
   public static String postfixToPrefix(String EXPRPOSTPREFXX) {
       Stack stak = new Stack();

       for (int i = 0; i < EXPRPOSTPREFXX.length(); i++) {
           char CHART = EXPRPOSTPREFXX.charAt(i);
           if (Character.isLetterOrDigit(CHART))
               stak.push("" + CHART);
           else if (isOperator(CHART)) {
               String b = (String)
               		stak.pop();
               String a = (String)
               		stak.pop();
               stak.push(CHART+ a + b);
           }
       }
       String result = (String) stak.pop();
       return "EXPRESSION:   " + EXPRPOSTPREFXX + "\nPOSTFIX:      " + EXPRPOSTPREFXX + "\nPREFIX:       " + result;
   }

 // *************************************  PREFX --> INFXX **********************************************
   
   public static String prefixToInfix(String EXPRPREFXINFXX) {
       Stack stak = new Stack();

       for (int i = EXPRPREFXINFXX.length() - 1; i >= 0; i--) {
           char CHRT = EXPRPREFXINFXX.charAt(i);
           if (Character.isLetterOrDigit(CHRT))
               stak.push("" + CHRT);
           else if (isOperator(CHRT)) {
               String a = (String) 
               		stak.pop();
               String b = (String)
               		stak.pop();
               stak.push(  a + CHRT + b );
           }
       }
       String result = (String) stak.pop();
       return "EXPRESSION:   " + EXPRPREFXINFXX + "\nPREFIX:       " + EXPRPREFXINFXX + "\nINFIX:        " + result;
   }

   
   //************************************* PREFX --> POSTFXX ************************************************
   
   public static String prefixToPostfix(String EXPRPRFXPOSTFXX) {
       Stack stak = new Stack();

       for (int i = EXPRPRFXPOSTFXX.length() - 1; i >= 0; i--) {
           char CHRC = EXPRPRFXPOSTFXX.charAt(i);
           if (Character.isLetterOrDigit(CHRC))
               stak.push("" + CHRC);
           else if (isOperator(CHRC)) {
               String a = (String) 
               		stak.pop();
               String b = (String)
               		stak.pop();
               stak.push(a + b + CHRC);
           }
       }
       String result = (String) stak.pop();
       return "EXPRESSION:   " + EXPRPRFXPOSTFXX + "\nPREFIX:       " + EXPRPRFXPOSTFXX + "\nPOSTFIX:      " + result;
   }

   private static String reverse(String s) {
       String RVRSTRG = "";
       for (int i = s.length() - 1; i >= 0; i--)
       	RVRSTRG += s.charAt(i);
       return RVRSTRG;
   }

   private static String swapBrackets(String SWPBRKTSSTRG) {
       String SWPSTRNG = "";
       for (int i = 0; i < SWPBRKTSSTRG.length(); i++) {
           char c = SWPBRKTSSTRG.charAt(i);
           if (c == '(') SWPSTRNG += ')';
           else if (c == ')') SWPSTRNG += '(';
           else SWPSTRNG += c;
       }
       return SWPSTRNG;
   }
}


