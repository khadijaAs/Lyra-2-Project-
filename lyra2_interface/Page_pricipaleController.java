
package lyra2_interface;

import Model.LYRA2;
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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Page_pricipaleController implements Initializable {

    @FXML
    private TextField passwd;

    @FXML
    private CheckBox faible;

    @FXML
    private CheckBox moyen;

    @FXML
    private CheckBox fort;

    @FXML
    private Button go;

    @FXML
    private TextArea result;

    @FXML
    private Button help;
@FXML
    private Button back;
@FXML
    private CheckBox use_par;
  


    public void en_arriere(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("App.fxml"));
        Scene scene = new Scene(root);
        Stage next = new Stage();
        next.setScene(scene);
        next.show();
        root.getStylesheets().add(this.getClass().getResource("stylexcc.css").toExt‌​ernalForm());
        Stage appStage=(Stage)((Node) event.getSource()).getScene().getWindow();
        
        appStage.close();
    }
    
    
     public void handelFaible(ActionEvent event)
    {
        if(faible.isSelected()){
            moyen.setSelected(false);
            fort.setSelected(false);
        }     
    }
     public void handelMoyen(ActionEvent event)
    {
        if(moyen.isSelected()){
            faible.setSelected(false);
            fort.setSelected(false);
        }     
    }
     
    public void handelFort(ActionEvent event)
    {
        if(fort.isSelected()){
            faible.setSelected(false);
            moyen.setSelected(false);
        }     
    }
    
    
    public String appelLyra(String pwd, int niveau){
        LYRA2 t=new LYRA2();
        String salt="saltsaltsalt";
        int t_cost=1;
        int R=5;
        int C=128;
        int outlen=5;
        return t.LYRA2(pwd,salt,t_cost,R,C,outlen,niveau);
       }
    
    
   
    
    public void help(ActionEvent event) throws IOException{
        
       Parent help_page = FXMLLoader.load(getClass().getResource("help.fxml"));
        
        Scene scene2 = new Scene(help_page);
        Stage helpStage=new Stage();///(Stage)((Node) event.getSource()).getScene().getWindow();
        
        helpStage.setScene(scene2);
        helpStage.show();
      
    }
    
    public void lancer(ActionEvent event) throws IOException
    {
        if("".equals(passwd.getText()))
            result.setText("Write the password");
        
        
        else if(faible.isSelected())
        {
           
              result.setText(appelLyra(passwd.getText(),0)); 
        }
        else if(moyen.isSelected())
        {
            
            result.setText(appelLyra(passwd.getText(),2));
        }
        else if(fort.isSelected())
        {
           
                result.setText(appelLyra(passwd.getText(),1));
        }
        else
        result.setText("You must choose the level of Security");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    
}
