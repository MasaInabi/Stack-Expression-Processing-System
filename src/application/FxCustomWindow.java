package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

//Masa Inabi
//1231024

public class FxCustomWindow {

    private Stage stage;
    private TextArea INPTAREA;
    private TextArea OUTPTAREA;

    private Label DMAINLBLE;
    private Label PRECDNCELBLE;
    private Label DOMAINCONTENTLBL;
    private Label PRECEDENCECONTENTLBL;

    private NotationDomain NTIONDMAIN = new NotationDomain();
    private OperatorPrecedence OPRTRPRCDNC = new OperatorPrecedence();

    public FxCustomWindow() {

        stage = new Stage();
        stage.setTitle("Custom Notation Mode");

        Label TITLCstomNotaton = new Label("Custom Notation System");
        TITLCstomNotaton.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #FFF8DC;");
        HBox TITLEHBXX = new HBox(TITLCstomNotaton);
        TITLEHBXX.setAlignment(Pos.CENTER);
        TITLEHBXX.setPadding(new Insets(15));

        DMAINLBLE = new Label("Operands: [Not Loaded]");
        PRECDNCELBLE = new Label("Operators: [Not Loaded]");
        DMAINLBLE.setStyle("-fx-text-fill: white;");
        PRECDNCELBLE.setStyle("-fx-text-fill: white;");

        DOMAINCONTENTLBL = new Label("");
        PRECEDENCECONTENTLBL = new Label("");
        DOMAINCONTENTLBL.setStyle("-fx-text-fill: #00E5FF;");
        PRECEDENCECONTENTLBL.setStyle("-fx-text-fill: #00E5FF;");

        VBox CNFGVBXX = new VBox(5, DMAINLBLE, DOMAINCONTENTLBL, PRECDNCELBLE, PRECEDENCECONTENTLBL);
        CNFGVBXX.setPadding(new Insets(10));

        Label INPTLBLE = new Label("Enter Expression:");
        INPTLBLE.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");
        INPTAREA = new TextArea();
        INPTAREA.setPrefHeight(120);
        VBox inputBox = new VBox(5, INPTLBLE, INPTAREA);

        Label OUTTLBLE = new Label("Result:");
        OUTTLBLE.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");
        OUTPTAREA = new TextArea();
        OUTPTAREA.setEditable(false);
        OUTPTAREA.setPrefHeight(100);
        OUTPTAREA.setWrapText(true);
        VBox outputBox = new VBox(5, OUTTLBLE, OUTPTAREA);

        Button LOdDOMNBOOTNN = new Button("Load Domain File");
        LOdDOMNBOOTNN.setStyle("-fx-background-color: #FF1744; -fx-text-fill: white; -fx-font-weight: bold;");

        Button LOdPRCDNCEBTNN = new Button("Load Precedence File");
        LOdPRCDNCEBTNN.setStyle("-fx-background-color: #FF1744; -fx-text-fill: white; -fx-font-weight: bold;");

        LOdDOMNBOOTNN.setPrefWidth(180);
        LOdPRCDNCEBTNN.setPrefWidth(180);
        LOdDOMNBOOTNN.setOnAction(e -> loadDomain());
        LOdPRCDNCEBTNN.setOnAction(e -> loadPrecedence());

        VBox LOADBOX = new VBox(5, LOdDOMNBOOTNN, LOdPRCDNCEBTNN);

        Button INFIX2POSTFIX = new Button("Infix → Postfix");
        INFIX2POSTFIX.setStyle("-fx-background-color: #00E5FF; -fx-text-fill: black; -fx-font-weight: bold;");

        Button INFIX2PREFIX = new Button("Infix → Prefix");
        INFIX2PREFIX.setStyle("-fx-background-color: #00E5FF; -fx-text-fill: black; -fx-font-weight: bold;");

        Button POSTFIX2INFIX = new Button("Postfix → Infix");
        POSTFIX2INFIX.setStyle("-fx-background-color: #00E5FF; -fx-text-fill: black; -fx-font-weight: bold;");

        Button POSTFIX2PREFIX = new Button("Postfix → Prefix");
        POSTFIX2PREFIX.setStyle("-fx-background-color: #00E5FF; -fx-text-fill: black; -fx-font-weight: bold;");

        Button PREFIX2INFIX = new Button("Prefix → Infix");
        PREFIX2INFIX.setStyle("-fx-background-color: #00E5FF; -fx-text-fill: black; -fx-font-weight: bold;");

        Button PREFIX2POSTFIX = new Button("Prefix → Postfix");
        PREFIX2POSTFIX.setStyle("-fx-background-color: #00E5FF; -fx-text-fill: black; -fx-font-weight: bold;");


        INFIX2POSTFIX.setPrefWidth(180);
        
        INFIX2PREFIX.setPrefWidth(180);
        POSTFIX2INFIX.setPrefWidth(180);
        POSTFIX2PREFIX.setPrefWidth(180);
        PREFIX2INFIX.setPrefWidth(180);
        PREFIX2POSTFIX.setPrefWidth(180);

        HBox CONVERTBTNBOX = new HBox(10, INFIX2POSTFIX, INFIX2PREFIX, POSTFIX2INFIX, POSTFIX2PREFIX, PREFIX2INFIX, PREFIX2POSTFIX);
        CONVERTBTNBOX.setAlignment(Pos.CENTER);
        CONVERTBTNBOX.setPadding(new Insets(10));

        Button CLEARBOTNN = new Button("Clear");
        Button CLOSEBOTNN = new Button("Close");
        Button VALIDTBOTNN = new Button("Validate Expression");
        Button COMPAREBTN = new Button("Compare Expressions");

        Button REPORTBTN = new Button("Generate Report");

        VALIDTBOTNN.setStyle("-fx-background-color: #32CD32; -fx-text-fill: white; -fx-font-weight: bold;");
        COMPAREBTN.setStyle("-fx-background-color: #FFA500; -fx-text-fill: black; -fx-font-weight: bold;");
        REPORTBTN.setStyle("-fx-background-color: #9C27B0; -fx-text-fill: white; -fx-font-weight: bold;");

        HBox bottomBox = new HBox(20, CLEARBOTNN, CLOSEBOTNN, VALIDTBOTNN, COMPAREBTN, REPORTBTN);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(15));

        VBox centerBox = new VBox(15, CNFGVBXX, LOADBOX, inputBox, CONVERTBTNBOX, outputBox);
        centerBox.setPadding(new Insets(10));

        BorderPane BRDRPNE = new BorderPane();
        BRDRPNE.setTop(TITLEHBXX);
        BRDRPNE.setCenter(centerBox);
        BRDRPNE.setBottom(bottomBox);
        BRDRPNE.setStyle("-fx-background-color: #111111;");

        Label EDITLBL = new Label("Edit Operands / Precedence");
        EDITLBL.setStyle("-fx-text-fill: #FFF8DC; -fx-font-weight: bold;");

        TextField OPERANDINPUT = new TextField();
        OPERANDINPUT.setPromptText("Operand");

        TextField OPERATORINPUT = new TextField();
        OPERATORINPUT.setPromptText("Operator(s)");

        TextField PRECEDENCEINPUT = new TextField();
        PRECEDENCEINPUT.setPromptText("Precedence (integer)");

        Button ADDOPERAND = new Button("Add Operand");
        ADDOPERAND.setStyle("-fx-background-color: #F5F5DC; -fx-text-fill: black; -fx-font-weight: bold;");

        Button REMOVEOPERAND = new Button("Remove Operand");
        REMOVEOPERAND.setStyle("-fx-background-color: #F5F5DC; -fx-text-fill: black; -fx-font-weight: bold;");

        Button ADDOPERATOR = new Button("Add Operator");
        ADDOPERATOR.setStyle("-fx-background-color: #F5F5DC; -fx-text-fill: black; -fx-font-weight: bold;");

        Button REMOVEOPERATOR = new Button("Remove Operator");
        REMOVEOPERATOR.setStyle("-fx-background-color: #F5F5DC; -fx-text-fill: black; -fx-font-weight: bold;");

        Button MODIFYOPERATOR = new Button("Modify Precedence");
        MODIFYOPERATOR.setStyle("-fx-background-color: #F5F5DC; -fx-text-fill: black; -fx-font-weight: bold;");


        ADDOPERAND.setPrefWidth(150);
        REMOVEOPERAND.setPrefWidth(150);
        ADDOPERATOR.setPrefWidth(150);
        REMOVEOPERATOR.setPrefWidth(150);
        MODIFYOPERATOR.setPrefWidth(150);

        VBox EDITBOX = new VBox(5, EDITLBL,
                new Label("Operand:"), OPERANDINPUT, ADDOPERAND, REMOVEOPERAND,
                new Label("Operator & Precedence:"), OPERATORINPUT, PRECEDENCEINPUT, ADDOPERATOR, REMOVEOPERATOR, MODIFYOPERATOR);
        EDITBOX.setPadding(new Insets(10));
        EDITBOX.setAlignment(Pos.TOP_CENTER);

        // ضعها على الجانب الأيمن بشكل مستقل
        BRDRPNE.setRight(EDITBOX);

        // ---------------- Actions ----------------
        CLEARBOTNN.setOnAction(e -> { INPTAREA.clear(); OUTPTAREA.clear(); });
        CLOSEBOTNN.setOnAction(e -> stage.close());
        VALIDTBOTNN.setOnAction(e -> validateExpression());
        COMPAREBTN.setOnAction(e -> new FxCompareWindow().show());
        REPORTBTN.setOnAction(e -> new FxReportWindow().show());

        // Conversion buttons
        INFIX2POSTFIX.setOnAction(e -> convertExpression("INFIX2POSTFIX"));
        INFIX2PREFIX.setOnAction(e -> convertExpression("INFIX2PREFIX"));

        POSTFIX2INFIX.setOnAction(e -> {
            String expr = INPTAREA.getText().trim();
            if (expr.isEmpty()) return;
            if (!CustomValidator.validate(expr, NTIONDMAIN, OPRTRPRCDNC)) {
                OUTPTAREA.setText("INVALID ❌\n" + CustomValidator.getErrorMessage());
                return;
            }
            CustomConverter.setPrecedence(OPRTRPRCDNC);
            String postfixFull = CustomConverter.infixToPostfix(expr);
            String postfix = postfixFull.split("POSTFIX:")[1].trim();
            String infixFull = CustomConverter.postfixToInfix(postfix);
            String infix = infixFull.split("INFIX:")[1].trim();
            OUTPTAREA.setText(
                    "EXPRESSION: " + expr + "\n" +
                            "POSTFIX: " + postfix + "\n" +
                            "INFIX: " + infix
            );
        });

        PREFIX2INFIX.setOnAction(e -> {
            String expr = INPTAREA.getText().trim();
            if (expr.isEmpty()) return;
            if (!CustomValidator.validate(expr, NTIONDMAIN, OPRTRPRCDNC)) {
                OUTPTAREA.setText("INVALID ❌\n" + CustomValidator.getErrorMessage());
                return;
            }
            CustomConverter.setPrecedence(OPRTRPRCDNC);
            String prefixFull = CustomConverter.infixToPrefix(expr);
            String prefix = prefixFull.split("PREFIX:")[1].trim();
            String infixFull = CustomConverter.prefixToInfix(prefix);
            String infix = infixFull.split("INFIX:")[1].trim();
            OUTPTAREA.setText(
                    "EXPRESSION: " + expr + "\n" +
                            "PREFIX: " + prefix + "\n" +
                            "INFIX: " + infix
            );
        });

        POSTFIX2PREFIX.setOnAction(e -> {
            String expr = INPTAREA.getText().trim();
            if (expr.isEmpty()) return;
            if (!CustomValidator.validate(expr, NTIONDMAIN, OPRTRPRCDNC)) {
                OUTPTAREA.setText("INVALID ❌\n" + CustomValidator.getErrorMessage());
                return;
            }
            CustomConverter.setPrecedence(OPRTRPRCDNC);
            String postfixFull = CustomConverter.infixToPostfix(expr);
            String postfix = postfixFull.split("POSTFIX:")[1].trim();
            String prefix = CustomConverter.postfixToPrefix(postfix);
            OUTPTAREA.setText(
                    "EXPRESSION: " + expr + "\n" +
                            "POSTFIX: " + postfix + "\n" +
                            "PREFIX: " + prefix
            );
        });

        PREFIX2POSTFIX.setOnAction(e -> {
            String expr = INPTAREA.getText().trim();
            if (expr.isEmpty()) return;
            if (!CustomValidator.validate(expr, NTIONDMAIN, OPRTRPRCDNC)) {
                OUTPTAREA.setText("INVALID ❌\n" + CustomValidator.getErrorMessage());
                return;
            }
            CustomConverter.setPrecedence(OPRTRPRCDNC);
            String prefixFull = CustomConverter.infixToPrefix(expr);
            String prefix = prefixFull.split("PREFIX:")[1].trim();
            String postfixFull = CustomConverter.prefixToPostfix(prefix);
            String postfix = postfixFull.split("POSTFIX:")[1].trim();
            OUTPTAREA.setText(
                    "EXPRESSION: " + expr + "\n" +
                            "PREFIX: " + prefix + "\n" +
                            "POSTFIX: " + postfix
            );
        });

        // ---------------- Edit Operands & Precedence Actions ----------------
        ADDOPERAND.setOnAction(e -> {
            String op = OPERANDINPUT.getText().trim();
            if(!op.isEmpty()){
                NTIONDMAIN.addOperand(op);
                DOMAINCONTENTLBL.setText(NTIONDMAIN.getOperandsString());
            }
        });

        REMOVEOPERAND.setOnAction(e -> {
            String op = OPERANDINPUT.getText().trim();
            if(!op.isEmpty()){
                NTIONDMAIN.removeOperand(op);
                DOMAINCONTENTLBL.setText(NTIONDMAIN.getOperandsString());
            }
        });

        ADDOPERATOR.setOnAction(e -> {
            String ops = OPERATORINPUT.getText().trim();
            String prcStr = PRECEDENCEINPUT.getText().trim();
            if(!ops.isEmpty() && !prcStr.isEmpty()){
                try{
                    int prc = Integer.parseInt(prcStr);
                    for(char c : ops.toCharArray()){
                        OPRTRPRCDNC.addOperator(c, prc);
                    }
                    PRECEDENCECONTENTLBL.setText(OPRTRPRCDNC.getOperatorsString());
                } catch(Exception ex){
                    showMsg("Error", "Precedence must be an integer.");
                }
            }
        });

        REMOVEOPERATOR.setOnAction(e -> {
            String ops = OPERATORINPUT.getText().trim();
            if(!ops.isEmpty()){
                for(char c : ops.toCharArray()){
                    OPRTRPRCDNC.removeOperator(c);
                }
                PRECEDENCECONTENTLBL.setText(OPRTRPRCDNC.getOperatorsString());
            }
        });

        MODIFYOPERATOR.setOnAction(e -> {
            String ops = OPERATORINPUT.getText().trim();
            String prcStr = PRECEDENCEINPUT.getText().trim();
            if(!ops.isEmpty() && !prcStr.isEmpty()){
                try{
                    int prc = Integer.parseInt(prcStr);
                    for(char c : ops.toCharArray()){
                    	OPRTRPRCDNC.updateOperator(c, prc);
                    }
                    PRECEDENCECONTENTLBL.setText(OPRTRPRCDNC.getOperatorsString());
                } catch(Exception ex){
                    showMsg("Error", "Precedence must be an integer.");
                }
            }
        });

        Scene scenFxCstomWndow = new Scene(BRDRPNE, 950, 750);
        stage.setScene(scenFxCstomWndow);
    }

    private void convertExpression(String type) {
        String expr = INPTAREA.getText().trim();
        if(expr.isEmpty()){ showMsg("Error", "Enter an expression."); return; }
        if(!CustomValidator.validate(expr, NTIONDMAIN, OPRTRPRCDNC)){
            OUTPTAREA.setText("INVALID ❌\n" + CustomValidator.getErrorMessage());
            return;
        }
        CustomConverter.setPrecedence(OPRTRPRCDNC);
        String result = "";
        switch(type){
            case "INFIX2POSTFIX": result = CustomConverter.infixToPostfix(expr); break;
            case "INFIX2PREFIX": result = CustomConverter.infixToPrefix(expr); break;
            case "POSTFIX2INFIX": result = CustomConverter.postfixToInfix(expr); break;
            case "POSTFIX2PREFIX": result = CustomConverter.postfixToPrefix(expr); break;
            case "PREFIX2INFIX": result = CustomConverter.prefixToInfix(expr); break;
            case "PREFIX2POSTFIX": result = CustomConverter.prefixToPostfix(expr); break;
        }
        OUTPTAREA.setText(result);
    }

    private void validateExpression() {
        String EXPRSTRG = INPTAREA.getText().trim();
        if(EXPRSTRG.isEmpty()) { showMsg("Error", "No expression entered."); return; }
        if(CustomValidator.validate(EXPRSTRG, NTIONDMAIN, OPRTRPRCDNC)) { OUTPTAREA.setText("VALID EXPRESSION ✔"); }
        else { OUTPTAREA.setText("INVALID ❌\n" + CustomValidator.getErrorMessage()); }
    }

    private void showMsg(String t, String m) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(t);
        a.setHeaderText(null);
        a.setContentText(m);
        a.showAndWait();
    }

    private void loadDomain() {
        FileChooser FILCHZR = new FileChooser();
        FILCHZR.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File f = FILCHZR.showOpenDialog(stage);
        if(f != null){ 
            NTIONDMAIN.loadDomainFile(f.getAbsolutePath()); 
            DOMAINCONTENTLBL.setText(NTIONDMAIN.getOperandsString()); 
            DMAINLBLE.setText("Operands Loaded ✔"); 
        }
    }

    private void loadPrecedence() {
        FileChooser FILCHZR = new FileChooser();
        FILCHZR.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File f = FILCHZR.showOpenDialog(stage);
        if(f != null){ 
            OPRTRPRCDNC.loadPrecedenceFile(f.getAbsolutePath()); 
            PRECEDENCECONTENTLBL.setText(OPRTRPRCDNC.getOperatorsString()); 
            PRECDNCELBLE.setText("Operators Loaded ✔"); 
        }
    }

    public void show() { stage.show(); }
}
