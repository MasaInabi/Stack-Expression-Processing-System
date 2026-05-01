package application;


//Masa Inabi
//1231024

import java.io.File;
import java.util.Scanner;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FxCompareWindow {

    private Stage stage;

    private TextArea EXPR1AREA;
    private TextArea EXPR2AREA;
    private TextArea RESLTAREA;

    private ComboBox<String> NOTATONCOMBOBX;
    private ComboBox<String> MODEECOMBOBX;

    private Label DOMAINLBL;
    private Label PRECEDENCELBL;

    private NotationDomain NTIONDMAIN = new NotationDomain();
    private OperatorPrecedence OPRTRPRCDNC = new OperatorPrecedence();

    public FxCompareWindow() {

        stage = new Stage();
        stage.setTitle("Compare Expressions");

        Label title = new Label("Expression Comparison Tool");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #FFF8DC;");
        HBox titleBox = new HBox(title);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(15));

        MODEECOMBOBX = new ComboBox<>();
        MODEECOMBOBX.getItems().addAll("Conventional Mode", "Custom Mode");
        MODEECOMBOBX.setValue("Conventional Mode");

        NOTATONCOMBOBX = new ComboBox<>();
        NOTATONCOMBOBX.getItems().addAll("Infix", "Postfix", "Prefix");
        NOTATONCOMBOBX.setValue("Infix");

        final Button LODDMNBITNN = new Button("Load Domain File");
        LODDMNBITNN.setStyle("-fx-background-color: #FF1744; -fx-text-fill: white; -fx-font-weight: bold;");

        final Button LODPRCNTGBOTNN = new Button("Load Precedence File");
        LODPRCNTGBOTNN.setStyle("-fx-background-color: #FF1744; -fx-text-fill: white; -fx-font-weight: bold;");

        LODDMNBITNN.setDisable(true);
        LODPRCNTGBOTNN.setDisable(true);

        DOMAINLBL = new Label("Domain: Not Loaded");
        PRECEDENCELBL = new Label("Precedence: Not Loaded");

        DOMAINLBL.setStyle("-fx-text-fill: white;");
        PRECEDENCELBL.setStyle("-fx-text-fill: white;");
        DOMAINLBL.setWrapText(true);
        PRECEDENCELBL.setWrapText(true);

        DOMAINLBL.setMaxWidth(200);
        PRECEDENCELBL.setMaxWidth(200);

        LODDMNBITNN.setOnAction(e -> loadDomain());
        LODPRCNTGBOTNN.setOnAction(e -> loadPrecedence());

        VBox loadBox = new VBox(8,
        		LODDMNBITNN, DOMAINLBL,
        		LODPRCNTGBOTNN, PRECEDENCELBL
        );
        loadBox.setPadding(new Insets(0, 0, 10, 0));
        loadBox.setAlignment(Pos.TOP_LEFT);

        EXPR1AREA = new TextArea();
        EXPR2AREA = new TextArea();
        RESLTAREA = new TextArea();
        RESLTAREA.setEditable(false);

        Button compareBtn = new Button("Compare");
        compareBtn.setStyle("-fx-background-color: #32CD32; -fx-text-fill: white; -fx-font-weight: bold;");

        Button clearBtn = new Button("Clear");
        Button closeBtn = new Button("Close");

        compareBtn.setOnAction(e -> compareExpressions());
        clearBtn.setOnAction(e -> {
            EXPR1AREA.clear();
            EXPR2AREA.clear();
            RESLTAREA.clear();
        });
        closeBtn.setOnAction(e -> stage.close());

        Label expr1Label = new Label("Expression 1");
        expr1Label.setStyle("-fx-text-fill: white;");

        Label expr2Label = new Label("Expression 2");
        expr2Label.setStyle("-fx-text-fill: white;");

        Label resultLabel = new Label("Result");
        resultLabel.setStyle("-fx-text-fill: white;");

        VBox center = new VBox(12,
                loadBox,                 
                MODEECOMBOBX,
                NOTATONCOMBOBX,
                expr1Label, EXPR1AREA,
                expr2Label, EXPR2AREA,
                resultLabel, RESLTAREA,
                new HBox(20, compareBtn, clearBtn, closeBtn)
        );


        MODEECOMBOBX.setOnAction(e -> {
            if (MODEECOMBOBX.getValue().equals("Conventional Mode")) {
            	LODDMNBITNN.setDisable(true);
            	LODPRCNTGBOTNN.setDisable(true);
            } else {
            	LODDMNBITNN.setDisable(false);
            	LODPRCNTGBOTNN.setDisable(false);
            }
        });

        center.setPadding(new Insets(15));
        center.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setTop(titleBox);
        root.setCenter(center);
        root.setStyle("-fx-background-color: #111111;");

        stage.setScene(new Scene(root, 700, 700));
    }

 
    private void compareExpressions() {

        String EXPR1 = EXPR1AREA.getText().trim();
        String EXPR2 = EXPR2AREA.getText().trim();

        if (EXPR1.isEmpty() || EXPR2.isEmpty()) {
            showMsg("Error", "Both expressions must be filled!");
            return;
        }

        String mode = MODEECOMBOBX.getValue();
        String notation = NOTATONCOMBOBX.getValue();

        String result1, result2;

        if (mode.equals("Conventional Mode")) {
            result1 = convertConventional(EXPR1, notation);
            result2 = convertConventional(EXPR2, notation);
        } else {
            result1 = convertCustom(EXPR1, notation);
            result2 = convertCustom(EXPR2, notation);
        }

        RESLTAREA.setText("Expression 1 : " + result1 + "\n" + "Expression 2 : " + result2 + "\n\n" +"Match Result: " + (result1.equals(result2) ? "SAME" : "DIFFERENT")
        );
    }

 
    private String convertConventional(String EXPRCNVRT, String NTIONCNVRT) {

        ExpressionValidator v = new ExpressionValidator();
        if (!v.validateBrackets(EXPRCNVRT))
            return "[Invalid Expression]";

        return EXPRCNVRT;
    }

   
    private String convertCustom(String EXPRCSTM, String NTIONCNVRTCSTM) {

        CustomConverter.setPrecedence(OPRTRPRCDNC);

        if (!CustomValidator.validate(EXPRCSTM, NTIONDMAIN, OPRTRPRCDNC))
            return "[Invalid Expression]";

        return EXPRCSTM;
    }


    private void loadDomain() {
        FileChooser FILCHSR = new FileChooser();
        FILCHSR.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File FILDMN = FILCHSR.showOpenDialog(stage);
        if (FILDMN == null) return;

        NTIONDMAIN.clear();
        StringBuilder content = new StringBuilder();

        try (Scanner SCNR = new Scanner(FILDMN)) {
            while (SCNR.hasNextLine()) {
                String line = SCNR.nextLine();
                content.append(line).append("\n");  
                Scanner lineScanner = new Scanner(line);
                while (lineScanner.hasNext()) {
                    String operand = lineScanner.next();
                    NTIONDMAIN.addOperand(operand);
                }
                lineScanner.close();
            }
            DOMAINLBL.setText(content.toString());  
            DOMAINLBL.setStyle("-fx-background-color: transparent; -fx-text-fill: #00FF6A; -fx-font-weight: bold;");
            showMsg("Success", "Domain file loaded.");
        } catch (Exception e) {
            showMsg("Error", "Failed to load domain file.");
        }
    }

    private void loadPrecedence() {
        FileChooser FILCHZR = new FileChooser();
        FILCHZR.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File FILEPRCNDNC = FILCHZR.showOpenDialog(stage);
        if (FILEPRCNDNC == null) return;

        OPRTRPRCDNC.clear();
        StringBuilder content = new StringBuilder();

        try (Scanner SCN = new Scanner(FILEPRCNDNC)) {
            while (SCN.hasNextLine()) {
                String line = SCN.nextLine().trim();
                if (line.isEmpty()) continue;
                content.append(line).append("\n");  

                Scanner SCNRLINE = new Scanner(line);
                String OPRTR = "";
                int precedence = -1;
                while (SCNRLINE.hasNext()) {
                    String token = SCNRLINE.next();
                    try {
                        precedence = Integer.parseInt(token);
                    } catch (Exception EXCPTN) {
                    	OPRTR += token;
                    }
                }

                for (char op : OPRTR.toCharArray()) {
                    OPRTRPRCDNC.addOperator(op, precedence);
                }

                SCNRLINE.close();
            }

            PRECEDENCELBL.setText(content.toString());  
            PRECEDENCELBL.setStyle("-fx-background-color: transparent; -fx-text-fill: #00FF6A; -fx-font-weight: bold;");
            showMsg("Success", "Precedence file loaded.");
        } catch (Exception EXCPN) {
            showMsg("Error", "Failed to load precedence file.");
        }
    }



    private void showMsg(String TITLE, String MSGG) {
        Alert ALRTT = new Alert(Alert.AlertType.INFORMATION);
        ALRTT.setTitle(TITLE);
        ALRTT.setHeaderText(null);
        ALRTT.setContentText(MSGG);
        ALRTT.showAndWait();
    }

    public void show() {
        stage.show();
    }
}
