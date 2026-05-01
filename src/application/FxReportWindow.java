package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

//Masa Inabi
//1231024

public class FxReportWindow {

    private Stage stage;
    private TextArea INPTAREA;
    private TextArea OUTPUTAREA;
    private ComboBox<String> MODECOMBOBXX;

    private NotationDomain domain = new NotationDomain();
    private OperatorPrecedence precedence = new OperatorPrecedence();

    private Button loadDomainBtn;
    private Button loadPrecedenceBtn;
    private Label DOMAINLBL;
    private Label PRECEDENCELBL;

    public FxReportWindow() {

        stage = new Stage();
        stage.setTitle("Generate Report");

        Label TITLEReportGenrator = new Label("Report Generator");
        TITLEReportGenrator.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #FFF8DC;");
        HBox HBXXTITL = new HBox(TITLEReportGenrator);
        HBXXTITL.setAlignment(Pos.CENTER);
        HBXXTITL.setPadding(new Insets(15));

        Label MODELBLE = new Label("Mode:");
        MODELBLE.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
        MODECOMBOBXX = new ComboBox<>();
        MODECOMBOBXX.getItems().addAll("Conventional Mode", "Custom Mode");
        MODECOMBOBXX.setValue("Conventional Mode");

        VBox modeBoxRow = new VBox(5, MODELBLE, MODECOMBOBXX);
        modeBoxRow.setAlignment(Pos.CENTER);

        loadDomainBtn = new Button("Load Domain File");
        loadDomainBtn.setStyle("-fx-background-color: #FF1744; -fx-text-fill: white; -fx-font-weight: bold;");

        loadDomainBtn.setPrefWidth(150);
        loadPrecedenceBtn = new Button("Load Precedence File");
        loadPrecedenceBtn.setStyle("-fx-background-color: #FF1744; -fx-text-fill: white; -fx-font-weight: bold;");

        loadPrecedenceBtn.setPrefWidth(150);

        DOMAINLBL = new Label("Domain: Not Loaded");
        DOMAINLBL.setStyle("-fx-text-fill: white; -fx-wrap-text: true;");
        DOMAINLBL.setMaxWidth(250);

        PRECEDENCELBL = new Label("Precedence: Not Loaded");
        PRECEDENCELBL.setStyle("-fx-text-fill: white; -fx-wrap-text: true;");
        PRECEDENCELBL.setMaxWidth(250);

        VBox loadBox = new VBox(5,
                loadDomainBtn, DOMAINLBL,
                loadPrecedenceBtn, PRECEDENCELBL
        );
        loadBox.setAlignment(Pos.TOP_LEFT);
        loadBox.setPadding(new Insets(5));

        Label EXPRNLBLE = new Label("Expressions:");
        EXPRNLBLE.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
        INPTAREA = new TextArea();
        INPTAREA.setPromptText("One expression per line");
        INPTAREA.setPrefHeight(200);
        VBox EXPRVBXX = new VBox(5, EXPRNLBLE, INPTAREA);
        EXPRVBXX.setAlignment(Pos.CENTER);

        Label PREVIWLBLE = new Label("Preview:");
        PREVIWLBLE.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
        OUTPUTAREA = new TextArea();
        OUTPUTAREA.setEditable(false);
        OUTPUTAREA.setPrefHeight(200);
        VBox PRVIWVBXX = new VBox(5, PREVIWLBLE, OUTPUTAREA);
        PRVIWVBXX.setAlignment(Pos.CENTER);

        Button GNRTBTNN = new Button("Generate Report");
        GNRTBTNN.setStyle("-fx-background-color: #32CD32; -fx-text-fill: white; -fx-font-weight: bold;");
        Button CLEARBTNN = new Button("Clear");
        Button CLOSEBTNN = new Button("Close");
        HBox ALLBTNNHBXX = new HBox(30, GNRTBTNN, CLEARBTNN, CLOSEBTNN);
        ALLBTNNHBXX.setAlignment(Pos.CENTER);

        VBox CNTRVBXX = new VBox(15, loadBox, modeBoxRow, EXPRVBXX, PRVIWVBXX, ALLBTNNHBXX);
        CNTRVBXX.setAlignment(Pos.TOP_CENTER);
        CNTRVBXX.setPadding(new Insets(15));

        BorderPane BRDRPNE = new BorderPane();
        BRDRPNE.setTop(HBXXTITL);
        BRDRPNE.setCenter(CNTRVBXX);
        BRDRPNE.setStyle("-fx-background-color: #111111;");

        stage.setScene(new Scene(BRDRPNE, 800, 700));

        GNRTBTNN.setOnAction(e -> generateReport());
        CLEARBTNN.setOnAction(e -> { INPTAREA.clear(); OUTPUTAREA.clear(); });
        CLOSEBTNN.setOnAction(e -> stage.close());

        loadDomainBtn.setOnAction(e -> loadDomain());
        loadPrecedenceBtn.setOnAction(e -> loadPrecedence());

        MODECOMBOBXX.setOnAction(e -> {
            boolean customMode = MODECOMBOBXX.getValue().equals("Custom Mode");
            loadDomainBtn.setDisable(!customMode);
            loadPrecedenceBtn.setDisable(!customMode);
        });

        boolean initialCustomMode = MODECOMBOBXX.getValue().equals("Custom Mode");
        loadDomainBtn.setDisable(!initialCustomMode);
        loadPrecedenceBtn.setDisable(!initialCustomMode);
    }

    private void loadDomain() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fc.showOpenDialog(stage);
        if (file == null) return;

        domain.clear();
        StringBuilder content = new StringBuilder();

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                content.append(line).append("\n");
                Scanner lineScanner = new Scanner(line);
                while (lineScanner.hasNext()) {
                    domain.addOperand(lineScanner.next());
                }
                lineScanner.close();
            }
            DOMAINLBL.setText(content.toString());
            DOMAINLBL.setStyle("-fx-text-fill: #00FF6A; -fx-font-weight: bold;");
            showMsg("Success", "Domain file loaded.");
        } catch (Exception e) {
            showMsg("Error", "Failed to load domain file.");
        }
    }

    private void loadPrecedence() {
        FileChooser FILCHZR = new FileChooser();
        FILCHZR.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = FILCHZR.showOpenDialog(stage);
        if (file == null) return;

        precedence.clear();
        StringBuilder content = new StringBuilder();

        try (Scanner SCR = new Scanner(file)) {
            while (SCR.hasNextLine()) {
                String line = SCR.nextLine().trim();
                if (line.isEmpty()) continue;
                content.append(line).append("\n");

                Scanner lineScanner = new Scanner(line);
                String operators = "";
                int prec = -1;
                while (lineScanner.hasNext()) {
                    String token = lineScanner.next();
                    try { prec = Integer.parseInt(token); }
                    catch (Exception e) { operators += token; }
                }
                for (char op : operators.toCharArray()) precedence.addOperator(op, prec);
                lineScanner.close();
            }
            PRECEDENCELBL.setText(content.toString());
            PRECEDENCELBL.setStyle("-fx-text-fill: #00FF6A; -fx-font-weight: bold;");
            showMsg("Success", "Precedence file loaded.");
        } catch (Exception e) {
            showMsg("Error", "Failed to load precedence file.");
        }
    }

    private void generateReport() {
        if (INPTAREA.getText().trim().isEmpty()) {
            showMsg("Error", "Enter expressions first.");
            return;
        }

        LinkedList EXPRSNLNKDLST = new LinkedList();
        Scanner SCNR = new Scanner(INPTAREA.getText());

        while (SCNR.hasNextLine()) {
            String line = SCNR.nextLine().trim();
            if (!line.isEmpty()) EXPRSNLNKDLST.addLast(line);
        }
        SCNR.close();

        String MODESRG = MODECOMBOBXX.getValue();
        String RPRTSTRG = "";
        int count = 1;

        CustomConverter.setPrecedence(this.precedence);

        while (!EXPRSNLNKDLST.isEmpty()) {
            String EXPRSTRNG = (String) EXPRSNLNKDLST.removeFirst();
            RPRTSTRG += "Expression #" + count++ + "\n";
            RPRTSTRG += "Original: " + EXPRSTRNG + "\n";

            if (MODESRG.equals("Custom Mode")) {
                if (!CustomValidator.validate(EXPRSTRNG, this.domain, this.precedence)) {
                    RPRTSTRG += "ERROR: " + CustomValidator.getErrorMessage() + "\n";
                } else {
                    RPRTSTRG += "Postfix: " + CustomConverter.infixToPostfix(EXPRSTRNG) + "\n";
                    RPRTSTRG += "Prefix: " + CustomConverter.infixToPrefix(EXPRSTRNG) + "\n";
                }
            } else {
                if (!ExpressionValidator.validateBrackets(EXPRSTRNG)) {
                    RPRTSTRG += "ERROR: Unbalanced parentheses\n";
                } else {
                    String POSTFXX = InfixToPostFix.convert(EXPRSTRNG);
                    String PREFXX  = InfixToPrefix.convert(EXPRSTRNG);
                    String SPACESTRG = spacePostfix(POSTFXX);
                    int VALU = new ConventionalEvaluator().EvaluatePostfix(SPACESTRG);

                    RPRTSTRG += "Postfix: " + POSTFXX + "\n";
                    RPRTSTRG += "Prefix: " + PREFXX + "\n";
                    RPRTSTRG += "Evaluation Result: " + VALU + "\n";
                }
            }

            RPRTSTRG += "------------------------------------\n\n";
        }

        OUTPUTAREA.setText(RPRTSTRG);

        FileChooser FILECHOSER = new FileChooser();
        FILECHOSER.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        FILECHOSER.setInitialFileName("report.txt");

        try {
            File file = FILECHOSER.showSaveDialog(stage);
            if(file == null) return;

            FileWriter FILEWRITRRPRT = new FileWriter(file);
            FILEWRITRRPRT.write(RPRTSTRG);
            FILEWRITRRPRT.close();
            showMsg("Success", "Report generated successfully!!");
        } catch (IOException EXCPTN) {
            showMsg("Error", "Failed to write file!");
        }
    }

    private String spacePostfix(String SPACESTRG) {
        String STRNG = "";
        for (int i = 0; i < SPACESTRG.length(); i++) STRNG += SPACESTRG.charAt(i) + " ";
        return STRNG.trim();
    }

    private void showMsg(String TITLERPRT, String MSGRPRT) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(TITLERPRT);
        a.setHeaderText(null);
        a.setContentText(MSGRPRT);
        a.showAndWait();
    }

    public void show() {
        stage.show();
    }
}
