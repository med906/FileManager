package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class ClassTableView {

    @FXML
    private TableView<FileInfo> folderView;
    private TableColumn<FileInfo, ImageView> fileIcon;
    private TableColumn<FileInfo,String> fileName;
    private TableColumn<FileInfo,String> fileDate;
    private TableColumn<FileInfo,String> fileSize;
    private ObservableList<FileInfo> list;
    private Label locationLb;

    private ClassTreeItem treeCreator;
    private Desktop desktop;

    public void setLocationLb(Label locationLb) {
        this.locationLb = locationLb;
    }

    public void setFileDate(TableColumn<FileInfo, String> fileDate) {
        this.fileDate = fileDate;
    }

    public void setFileIcon(TableColumn<FileInfo, ImageView> fileIcon) {
        this.fileIcon = fileIcon;
    }

    public void setFileName(TableColumn<FileInfo, String> fileName) {
        this.fileName = fileName;
    }

    public void setDesktop(Desktop desktop) {
        this.desktop = desktop;
    }

    public void setFileSize(TableColumn<FileInfo, String> fileSize) {
        this.fileSize = fileSize;
    }

    public void setFolderView(TableView<FileInfo> folderView) {
        this.folderView = folderView;
    }

    public void setList(ObservableList<FileInfo> list) {
        this.list = list;
    }

    public void setTreeCreator(ClassTreeItem treeCreator) {
        this.treeCreator = treeCreator;
    }


    public Label getLocationLb() {
        return locationLb;
    }

    public ClassTreeItem getTreeCreator() {
        return treeCreator;
    }

    public Desktop getDesktop() {
        return desktop;
    }

    public ObservableList<FileInfo> getList() {
        return list;
    }

    public TableColumn<FileInfo, ImageView> getFileIcon() {
        return fileIcon;
    }

    public TableColumn<FileInfo, String> getFileDate() {
        return fileDate;
    }

    public TableColumn<FileInfo, String> getFileName() {
        return fileName;
    }

    public TableColumn<FileInfo, String> getFileSize() {
        return fileSize;
    }

    public TableView<FileInfo> getFolderView() {
        return folderView;
    }


    public void fillTable(){


        String path = locationLb.getText();
//        System.out.println("from Here : "+path);

        //add files to folder view
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        File selected = new File(path);
        if (selected.isDirectory()){
            folderView.getItems().clear();
            locationLb.setText(path);
            File[] currentFiles = treeCreator.getSubFiles(path);

            if (currentFiles == null || currentFiles.length==0){
                folderView.getItems().clear();
                folderView.setPlaceholder(new Label("Folder is empty"));
                return;
            }

            FileInfo data[] = new FileInfo[currentFiles.length];
            for (int i=0;i<currentFiles.length;i++) {
                String name = null;String currentDirPath = null;ImageView icon = null;

                try {
                    currentDirPath = currentFiles[i].getAbsolutePath();
                    name = (currentFiles[i].getName().equals("") ? currentFiles[i].toString() : currentFiles[i].getName());
                    icon = new ImageView( treeCreator.getIconImageFX(currentFiles[i]));

                } catch (Exception ignored){}

                data[i] = new FileInfo(icon,currentFiles[i].getAbsolutePath(),name,treeCreator.calculateSize(currentFiles[i]),sdf.format(currentFiles[i].lastModified()));
            }
            list = FXCollections.observableArrayList(data);
            fileIcon.setCellValueFactory(new PropertyValueFactory<>("image"));
            fileName.setCellValueFactory(new PropertyValueFactory<>("name"));
            fileDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            fileSize.setCellValueFactory(new PropertyValueFactory<>("size"));


            folderView.setItems(list);


        } else if(selected.isFile()){

            desktop = Desktop.getDesktop();
            try{desktop.open(selected);}
            catch(IOException x){System.out.println(x.getMessage());}
        }


    }

    public void addTableFiled(ObservableList<FileInfo> list){

        fileIcon.setCellValueFactory(new PropertyValueFactory<>("image"));
        fileName.setCellValueFactory(new PropertyValueFactory<>("name"));
        fileDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        fileSize.setCellValueFactory(new PropertyValueFactory<>("size"));


        folderView.setItems(list);

    }

    public FileInfo createRow(String path){
        try {
            File fil = new File(path);
            ImageView icon = new ImageView( getTreeCreator().getIconImageFX(fil));
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
            return new FileInfo(icon,getLocationLb().getText(),fil.getName(),getTreeCreator().calculateSize(fil),sdf.format(fil.lastModified()));


        }catch (Exception e){

        }
        return null;
    }


}
