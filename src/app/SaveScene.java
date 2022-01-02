package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.zip.ZipOutputStream;

public class SaveScene implements Initializable {

    @FXML
    private TextField fileName;
    @FXML
    private Button saveFileBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private ComboBox<String> fileType;

    MessageView newMsg = new MessageView();





    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<String> options = FXCollections.observableArrayList(
                "Text File (.txt)","Folder","Zip File (.zip)","All Files"
//                ,"Portable Document Format (.pdf)","Microsoft Word (.docx)","Image File (.png)","Microsoft excel (.xlsx)"
        );

        fileType.getItems().addAll(options);

    }

    public void closeWindow(){
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }


    public void saveFile(ActionEvent event){
        Stage stage = (Stage) saveFileBtn.getScene().getWindow();
        String path = null;
        DataTransfer transfer = null;
        try{

            transfer =(DataTransfer) stage.getUserData();

            path = transfer.getLocationLb().getText();




        } catch (Exception e){
            System.out.println("cant Find Scene "+e);

        }
        String choice = fileType.getSelectionModel().getSelectedItem();
        if (!fileType.equals("")){


            if (choice != null){

                switch (choice){
                    case "Text File (.txt)":
                        makeFile(path,fileName.getText(),".txt");
                        break;
                    case "Folder":
                        File dir = new File(path+"\\"+fileName.getText());
                        boolean bool = dir.mkdir();
                        if(bool){
                            System.out.println("Directory created successfully");
                        }else{
                            System.out.println("Sorry couldn’t create specified directory");
                        }
                        break;
                    case "Portable Document Format (.pdf)":
                        makeFile(path,fileName.getText(),".pdf");
                        break;
                    case "Microsoft Word (.docx)":
                        makeFile(path,fileName.getText(),".docx");
                        break;
                    case "Image File (.png)":
                        makeFile(path,fileName.getText(),".png");
                        break;
                    case "Zip File (.zip)":

                        makeZip(path,fileName.getText(),".zip");
                        break;
                    case "Microsoft excel (.xlsx)":
                        makeFile(path,fileName.getText(),".xlsx");
                        break;
                    case "All Files":
                        makeFile(path,fileName.getText(),"");
                        break;
                }

            } else{
//                System.out.println("text File");
                makeFile(path,fileName.getText(),".txt");
            }
//            System.out.println("Transfer "+transfer.getTableV);
            transfer.getTableView().fillTable();

        }

        stage.close();


    }

    private void makeFile(String path,String name,String type){

        try {
            System.out.println("name : "+name);
            File myFile = new File(path+"\\"+name+type);
            if (myFile.createNewFile()){
                System.out.println("Done");
            }
            else{
                newMsg.loadView();
            }


        }catch (Exception e){
            System.out.println(e);

        }
    }


    private void makeZip(String path,String name,String type){

        try {
            File f = new File(path+"\\"+name+type);
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));
            out.close();
        } catch (Exception e){

        }
    }



}
