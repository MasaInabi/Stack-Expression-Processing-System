package application;

import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import java.io.File;

//Masa Inabi
//1231024 
public class FxConventionalWindow {

    private Stage stage;
    private OperatorPrecedence operatorPrecedence;

    public FxConventionalWindow() {
        stage = new Stage();
        operatorPrecedence = new OperatorPrecedence();

        Label titleConventonaNotation = new Label("Conventional Notation Mode");
        titleConventonaNotation.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #FFF8DC;");
        HBox titlBXX = new HBox(titleConventonaNotation);
        titlBXX.setAlignment(Pos.CENTER);
        titlBXX.setPadding(new Insets(15));

        Label INPTLBLE = new Label("Enter Expression:");
        INPTLBLE.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
        TextField INPTFLD = new TextField();
        INPTFLD.setPrefWidth(500);

        VBox INPTVBXX = new VBox(5, INPTLBLE, INPTFLD);
        INPTVBXX.setAlignment(Pos.CENTER);

        Button INFXTOPOSFXBOTTN = new Button("Infix --> Postfix");
        Button INFXTOPREFXBOTTN  = new Button("Infix --> Prefix");
        Button POSTFXTOINFXBOTTN = new Button("Postfix --> Infix");
        Button POSFXTOPREFXBOTTN = new Button("Postfix --> Prefix");
        Button PREFXTOINFZBOTTN  = new Button("Prefix --> Infix");
        Button PREFXTOPOSTFXBOTTN = new Button("Prefix --> Postfix");
        Button LOADFILEBTN = new Button("Load Conventional File");

        Button[] allButtons = {INFXTOPOSFXBOTTN, INFXTOPREFXBOTTN, POSTFXTOINFXBOTTN, POSFXTOPREFXBOTTN, PREFXTOINFZBOTTN, PREFXTOPOSTFXBOTTN, LOADFILEBTN};
        for(Button b: allButtons){
            b.setStyle("-fx-background-color: #00E5FF; -fx-text-fill: black; -fx-font-weight: bold;");
            b.setPrefWidth(150);
        }

        HBox HBXINFXTO = new HBox(20, INFXTOPOSFXBOTTN, INFXTOPREFXBOTTN);
        HBox HBXPOSTFXTO = new HBox(20, POSTFXTOINFXBOTTN, POSFXTOPREFXBOTTN);
        HBox HBXPREFXTO = new HBox(20, PREFXTOINFZBOTTN, PREFXTOPOSTFXBOTTN);
        HBox HBXLOADFILE = new HBox(LOADFILEBTN);
        HBXINFXTO.setAlignment(Pos.CENTER);
        HBXPOSTFXTO.setAlignment(Pos.CENTER);
        HBXPREFXTO.setAlignment(Pos.CENTER);
        HBXLOADFILE.setAlignment(Pos.CENTER);

        VBox VBXXALLBTTN = new VBox(15, HBXINFXTO, HBXPOSTFXTO, HBXPREFXTO, HBXLOADFILE);
        VBXXALLBTTN.setAlignment(Pos.CENTER);
        VBXXALLBTTN.setPadding(new Insets(15));

        Label RSLTLBLE = new Label("Results:");
        RSLTLBLE.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
        TextArea RSLTAREAA = new TextArea();
        RSLTAREAA.setEditable(false);
        RSLTAREAA.setPrefSize(600, 240);
        RSLTAREAA.setStyle("-fx-font-size: 14px;");

        VBox RSLTVBXX = new VBox(10, RSLTLBLE, RSLTAREAA);
        RSLTVBXX.setAlignment(Pos.CENTER);
        RSLTVBXX.setPadding(new Insets(10));

        Label labelOperators = new Label("Operators: ");
        labelOperators.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        Button CLEARBOTNN = new Button("Clear");
        Button CLSEBOTNN = new Button("Close");
        CLEARBOTNN.setPrefWidth(120);
        CLSEBOTNN.setPrefWidth(120);

        HBox HBXXFORBOTNNS = new HBox(20, CLEARBOTNN, CLSEBOTNN);
        HBXXFORBOTNNS.setAlignment(Pos.CENTER);
        HBXXFORBOTNNS.setPadding(new Insets(10));

        VBox CNTRVBXX = new VBox(18, titlBXX, INPTVBXX, VBXXALLBTTN, labelOperators, RSLTVBXX, HBXXFORBOTNNS);
        CNTRVBXX.setAlignment(Pos.TOP_CENTER);
        CNTRVBXX.setPadding(new Insets(10));

        BorderPane rootBRDRPNE = new BorderPane();
        rootBRDRPNE.setCenter(CNTRVBXX);
        rootBRDRPNE.setStyle("-fx-background-color: #111111;");

        Scene scenFxConventionalWindow = new Scene(rootBRDRPNE, 800, 680);
        stage.setScene(scenFxConventionalWindow);
        stage.setTitle("Conventional Mode");

        LOADFILEBTN.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);
            if(file != null){
                operatorPrecedence.clear();
                operatorPrecedence.loadPrecedenceFile(file.getAbsolutePath());
                labelOperators.setText("Operators: " + operatorPrecedence.getOperatorsString());
            }
        });

        CLEARBOTNN.setOnAction(e -> {
            INPTFLD.clear();
            RSLTAREAA.clear();
        });

        CLSEBOTNN.setOnAction(e -> stage.close());

        INFXTOPOSFXBOTTN.setOnAction(e -> {
            String expr = INPTFLD.getText().trim();
            if(expr.isEmpty()){ showError("Input Error","Please enter expression!"); return; }
            if(!ExpressionValidator.validateBrackets(expr)){ showError("Validation Error","Parentheses are not balanced."); return; }

            try{
                String postfix = InfixToPostFix.convert(expr);
                int eval = ConventionalEvaluator.EvaluatePostfix(postfix);
                RSLTAREAA.setText(
                    "EXPRESSION: " + expr + "\n" +
                    "INFIX:      " + expr + "\n" +
                    "POSTFIX:    " + postfix + "\n" +
                    "EVALUATION: " + eval
                );
            } catch(Exception ex){
            	showError("Conversion Error", ex.getMessage()); 
            	}
        });

        INFXTOPREFXBOTTN.setOnAction(e -> {
            String expr = INPTFLD.getText().trim();
            if(expr.isEmpty()){ showError("Input Error","Please enter expression!"); return; }
            if(!ExpressionValidator.validateBrackets(expr)){ showError("Validation Error","Parentheses are not balanced."); return; }

            try{
                String prefix = InfixToPrefix.convert(expr);
                int eval = ConventionalEvaluator.EvaluatePostfix(InfixToPostFix.convert(expr));
                RSLTAREAA.setText(
                    "EXPRESSION: " + expr + "\n" +
                    "INFIX:      " + expr + "\n" +
                    "PREFIX:     " + prefix + "\n" +
                    "EVALUATION: " + eval
                );
            } catch(Exception ex){ showError("Conversion Error", ex.getMessage()); }
        });

      
        POSTFXTOINFXBOTTN.setOnAction(e -> {

            String expr = INPTFLD.getText().trim();
            if(expr.isEmpty()){ showError("Input Error","Please enter expression!");
            return;
            }

            try{
            	 String infix = PrefixToInfix.convert(expr);
                 String postfix = InfixToPostFix.convert(infix);
                int eval = ConventionalEvaluator.EvaluatePostfix(postfix);

                RSLTAREAA.setText(
                    "EXPRESSION: " + expr + "\n" +
                    "POSTFIX:    " + postfix + "\n" +
                    "INFIX:      " + infix + "\n" +
                    "EVALUATION: " + eval
                );
            } catch(Exception ex){ showError("Conversion Error", ex.getMessage()); }
        });


     
        POSFXTOPREFXBOTTN.setOnAction(e -> {

            String expr = INPTFLD.getText().trim();
            if(expr.isEmpty()){ showError("Input Error","Please enter expression!"); return; }

            try{
            	 String infix = PrefixToInfix.convert(expr);
                 String postfix = InfixToPostFix.convert(infix);
                 String prefix = InfixToPrefix.convert(expr);

                int eval = ConventionalEvaluator.EvaluatePostfix(postfix);

                RSLTAREAA.setText(
                    "EXPRESSION: " + expr + "\n" +
                    "POSTFIX:    " + postfix + "\n" +
                    "PREFIX:     " + prefix + "\n" +
                    "EVALUATION: " + eval
                );
            } catch(Exception ex){ showError("Conversion Error", ex.getMessage()); }
        });


        PREFXTOINFZBOTTN.setOnAction(e -> {
            String expr = INPTFLD.getText().trim();
            if(expr.isEmpty()){ showError("Input Error","Please enter expression!"); return; }

            try{
                String prefix = InfixToPrefix.convert(expr);

                String infix = PrefixToInfix.convert(expr);
                int eval = ConventionalEvaluator.EvaluatePostfix(InfixToPostFix.convert(infix));
                RSLTAREAA.setText(
                    "EXPRESSION: " + expr + "\n" +
                    "PREFIX:     " + prefix + "\n" +
                    "INFIX:      " + infix + "\n" +
                    "EVALUATION: " + eval
                );
            } catch(Exception ex){ showError("Conversion Error", ex.getMessage()); }
        });

        PREFXTOPOSTFXBOTTN.setOnAction(e -> {
            String expr = INPTFLD.getText().trim();
            if(expr.isEmpty()){ showError("Input Error","Please enter expression!"); return; }

            try{
                String prefix = InfixToPrefix.convert(expr);

                String infix = PrefixToInfix.convert(expr);
                String postfix = InfixToPostFix.convert(infix);
                int eval = ConventionalEvaluator.EvaluatePostfix(postfix);
                RSLTAREAA.setText(
                    "EXPRESSION: " + expr + "\n" +
                    "PREFIX:     " + prefix + "\n" +
                    "POSTFIX:    " + postfix + "\n" +
                    "EVALUATION: " + eval
                );
            } catch(Exception ex){ showError("Conversion Error", ex.getMessage()); }
        });

    }

    private void showError(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public void show() {
        stage.show();
    }
}
