
package lyra2_interface;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class AppController implements Initializable {

    @FXML
    private Pane pane1;

    @FXML
    private Button lyra;
    
    @FXML
    private Button lyra_parra;

    @FXML
    private Button aboutus;
    
    @FXML
    void page_principale(ActionEvent event) throws IOException {
         
      Parent root = FXMLLoader.load(getClass().getResource("page_pricipale.fxml"));
      Scene scene = new Scene(root);
      
        Stage appStage=(Stage)((Node) event.getSource()).getScene().getWindow();
        
        appStage.setScene(scene);
        appStage.show();
    }
    
    @FXML
    void showInformation(ActionEvent event) throws IOException {
        Parent help_page = FXMLLoader.load(getClass().getResource("about_lyra.fxml"));
        
        Scene scene2 = new Scene(help_page);
        Stage helpStage=new Stage();//(Stage)((Node) event.getSource()).getScene().getWindow();
        
        helpStage.setScene(scene2);
        helpStage.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    
}
