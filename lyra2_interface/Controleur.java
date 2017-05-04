
package lyra2_interface;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;                          
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controleur extends Application {
   @Override
    public void start(Stage stage) throws Exception 
    {
        Parent root = FXMLLoader.load(getClass().getResource("App.fxml"));
        
      Scene scene = new Scene(root);
      root.getStylesheets().add(this.getClass().getResource("stylexcc.css").toExt‌​ernalForm());//toExt‌​ernalForm()
      stage.setScene(scene);
      stage.show();
    }

    public static void main(String[] args) 
    {
        launch(args);
    }
    
}
