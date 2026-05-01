package application;
	
import javafx.application.Application;
import javafx.stage.Stage;


//Masa Inabi
//1231024


public class Main extends Application {
	@Override
	public void start(Stage steege) {
		
		FxMainWindow MAINWNDOW=new FxMainWindow();
		MAINWNDOW.show();
	}
	
	public static void main(String[] args) {
		System.out.println("Medical");
		 launch( args);  
	}
}

