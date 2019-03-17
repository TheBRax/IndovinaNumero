package it.brax.indovinanumero;
	
import it.brax.indovinanumero.model.IndovinaNumeroModel;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			IndovinaNumeroModel model = new IndovinaNumeroModel();
			FXMLLoader loader = new FXMLLoader();
			BorderPane root = (BorderPane)loader.load(getClass().getResource("Sample.fxml"));
			SampleController controller = loader.getController();
			controller.setModel(model);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
