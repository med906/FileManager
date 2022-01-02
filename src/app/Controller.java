package app;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class Controller implements Initializable {

    @FXML
    public TreeView<String> treeView;
    public TextField searchBar;
    public Button searchBtn;
    public Label locationLb;
    public TableView<FileInfo> folderView;
    public TableColumn<FileInfo,ImageView> fileIcon;
    public TableColumn<FileInfo,String> fileName;
    public TableColumn<FileInfo,String> fileDate;
    public TableColumn<FileInfo,String> fileSize;
    public ObservableList<FileInfo> list;

    private Desktop desktop;
    ClassTreeItem treeCreator = new ClassTreeItem();
    ClassTableView tableView = new ClassTableView();



    File[] fls = File.listRoots();
    Image pcIcon = new Image(getClass().getResourceAsStream("/img/pc.png"));
    ImageView searchIcon = new ImageView( new Image(getClass().getResourceAsStream("/img/search.svg"),30,30,false,false));




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        searchBtn.setGraphic(searchIcon);
        folderView.setPlaceholder(new Label(""));


        TreeItem<String> root = new TreeItem<>("This PC", new ImageView(pcIcon));



        treeCreator.addToBranch(root,fls);

        root.expandedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {


                for (TreeItem<String> item:root.getChildren()){

                    treeCreator.addToBranch(item,treeCreator.getSubFiles(treeCreator.getPathInTree(item)));
                }
            }
        });


        root.setExpanded(true);

        treeView.setRoot(root);

        prepTableView();

    }


    public void mouseClick(MouseEvent mouseEvent) {

        if(mouseEvent.getClickCount()==2){


            TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();


            String path = treeCreator.getPathInTree(selectedItem);
            locationLb.setText(path);
            fillTable();

//
        }//if end
    }

    public void handleTableMouseClicked(MouseEvent mouseEvent){

        if(mouseEvent.getClickCount()==2){
            FileInfo fileInfo = folderView.getSelectionModel().getSelectedItem();
            locationLb.setText(fileInfo.getCurrentDirPath());
            linkTableViewAndTreeView();
            fillTable();

        }

    }

    public void linkTableViewAndTreeView(){


        try {
            System.out.println("linking");
            FileInfo tableItem = folderView.getSelectionModel().getSelectedItem();
            TreeItem<String> treeItem = treeView.getSelectionModel().getSelectedItem();
            ObservableList items = treeItem.getChildren();

            for (int i = 0;i<items.size();i++){
                TreeItem<String> item = (TreeItem<String>) items.get(i);


                if(item.getValue().equals(tableItem.getName())){
                    treeView.getSelectionModel().select(item);
                    treeView.getSelectionModel().getSelectedItem().setExpanded(true);
                    break;

                }

            }

        } catch (Exception e){

        }

    }

    public void back(ActionEvent event){
        String base = "";

        try {
            String newLocation = locationLb.getText().substring(0,locationLb.getText().lastIndexOf("\\"));
            if (!newLocation.contains("\\")){
                newLocation+="\\";
                base = newLocation;
            }
            locationLb.setText(newLocation);
            TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
            TreeItem<String> parent = item.getParent();


            if(!item.getValue().equals(base) ){
                treeView.getSelectionModel().select(parent);

            }

            fillTable();

        } catch (Exception e){

        }


    }

    public void fillTable(){

        prepTableView();
        tableView.fillTable();
    }

    public void prepTableView(){
        tableView.setLocationLb(locationLb);
        tableView.setDesktop(desktop);
        tableView.setFileDate(fileDate);
        tableView.setFileIcon(fileIcon);
        tableView.setFileName(fileName);
        tableView.setFileSize(fileSize);
        tableView.setList(list);
        tableView.setFolderView(folderView);
        tableView.setTreeCreator(treeCreator);

    }


    public void createNewFile(ActionEvent event){


       prepTableView();

        try{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SaveScene.fxml"));
            Parent saveScene = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setUserData(new DataTransfer(locationLb,tableView));
            stage.setTitle("New File");
            stage.setScene(new Scene(saveScene));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            Image pcIcon = new Image(getClass().getResourceAsStream("/img/pcPng.png"));
            stage.getIcons().add(pcIcon);
            stage.show();

        } catch (Exception e){
            System.out.println("cant create File");

        }
    }


    public void searchForFile(ActionEvent event) throws IOException {

        if (!searchBar.getText().equals("")){
            FindFile find = new FindFile();
            find.setData(new DataTransfer(locationLb,tableView));
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
            find.findFile(searchBar.getText(),new File(locationLb.getText()));
        }


    }


}



