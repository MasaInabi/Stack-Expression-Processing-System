package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//Masa Inabi
//1231024

public class FxMainWindow {

    private Stage stage;

    public FxMainWindow() {

        stage = new Stage();
        stage.setTitle("Expression Processing System");

        Label TITLEExpresionProcesing = new Label("Expression Processing System");
        TITLEExpresionProcesing.setStyle( "-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #FFF8DC;");

        HBox titleBox = new HBox(TITLEExpresionProcesing);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(25, 0, 25, 0));

        Label modeLabel = new Label("Select Mode:");
        modeLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        ComboBox<String> MODECOMBO = new ComboBox<>();
        MODECOMBO.getItems().addAll("Custom Notation Mode","Conventional Mode"
        );
        MODECOMBO.setPrefWidth(250);

        MODECOMBO.setOnAction(e -> {
            String selected = MODECOMBO.getValue();

            if (selected == null) return;

            if (selected.equals("Custom Notation Mode")) {
                FxCustomWindow wnd = new FxCustomWindow();
                wnd.show();
            } 
            else if (selected.equals("Conventional Mode")) {
                FxConventionalWindow wnd = new FxConventionalWindow();
                wnd.show();
            }

            MODECOMBO.setValue(null); 
        });

        VBox MODEBOX = new VBox(10, modeLabel, MODECOMBO);
        MODEBOX.setAlignment(Pos.CENTER);

        Button CMPRBTNN = new Button("Compare Expressions");
        CMPRBTNN.setPrefSize(250, 60);
        CMPRBTNN.setStyle(
                "-fx-background-color: #C77DFF; -fx-text-fill: black; -fx-font-weight: bold;");
        CMPRBTNN.setOnAction(e -> {
            FxCompareWindow CMPRWNDW = new FxCompareWindow();
            CMPRWNDW.show();
        });

        Button RPRTBTNN = new Button("Generate Report");
        RPRTBTNN.setPrefSize(250, 60);
        RPRTBTNN.setStyle(
                "-fx-background-color: #FF1744; -fx-text-fill: black; -fx-font-weight: bold;");
        RPRTBTNN.setOnAction(e -> {
            FxReportWindow wnd = new FxReportWindow();
            wnd.show();
        });

        VBox VBXXALLBTNN = new VBox(25, MODEBOX, CMPRBTNN, RPRTBTNN);
        VBXXALLBTNN.setAlignment(Pos.CENTER);
        VBXXALLBTNN.setPadding(new Insets(20));

        BorderPane BRDRPNE = new BorderPane();
        BRDRPNE.setTop(titleBox);
        BRDRPNE.setCenter(VBXXALLBTNN);
        BRDRPNE.setStyle("-fx-background-color: #111111;");

        Scene scenFxManWdow = new Scene(BRDRPNE, 700, 600);
        stage.setScene(scenFxManWdow);
    }

    public void show() {
        stage.show();
    }
}
