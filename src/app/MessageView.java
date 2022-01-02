package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class MessageView{


    @FXML
    public Button okBtn;



    public void close(ActionEvent event){
        Stage stage = (Stage) okBtn.getScene().getWindow();
        stage.close();
    }

    public void loadView(){
        try{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MessageView.fxml"));
            Parent saveScene = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(saveScene));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            Image pcIcon = new Image(getClass().getResourceAsStream("/img/pcPng.png"));
            stage.getIcons().add(pcIcon);
            stage.show();


        } catch (Exception e){
            System.out.println("can't find File "+e);

        }


    }



}
