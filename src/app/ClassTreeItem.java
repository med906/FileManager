package app;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;


import static javafx.embed.swing.SwingFXUtils.toFXImage;

public class ClassTreeItem  {

    public TableView<FileInfo> folderView;
    public TableColumn<FileInfo,TreeItem<String>> columnName;
    public TableColumn<FileInfo,String> columnDate;
    public TableColumn<FileInfo,String> columnSize;

    public String currentDir ="";
    Set<String> foundFiles = new HashSet<String>();


    public void addToBranch(TreeItem<String> root,File[] fl){

        try {


//          for each file and folder in the dir
            for (int i=0;i<fl.length;i++){

//              fix the name issue if it's a hard drive
                String name = (fl[i].getName().equals("") ? fl[i].toString() : fl[i].getName());


                Image icon = getIconImageFX(fl[i]);
                TreeItem<String> tmpNode = new TreeItem<>( name, new ImageView(icon));

//                System.out.println(name);




//                    if the tmp node is expanded
                    tmpNode.expandedProperty().addListener(new ChangeListener<Boolean>() {
                        @Override
                        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//                      load it's children's and do the same to it's children's
                            for (TreeItem<String> item:tmpNode.getChildren()){
                                if (!foundFiles.contains(item.getValue())){
                                    foundFiles.add(item.getValue());
                                    addToBranch(item,getSubFiles(getPathInTree(item)));
                                }


                            }

                        }
                    });
//              add child to root
                    root.getChildren().add(tmpNode);
                }

//


        } catch (Exception ignore){

        }

    }

    public String getPathInTree(TreeItem<String> current){

        String path = getFullItemPath(current);
        if (path.length()>9){
            return path.substring(9).replace("/","\\");
        }

        return current.getValue();
    }


    private String getFullItemPath(TreeItem<String> item){
        StringBuilder pathBuilder = new StringBuilder();

        for (TreeItem<String> it=item;it!=null;it=it.getParent()){
            pathBuilder.insert(0,it.getValue());
            pathBuilder.insert(0,"/");
        }

        return pathBuilder.toString();
    }

    public File[] getSubFiles(String path){
        return new File(path).listFiles();
    }

    public Image getIconImageFX(File f){

        ImageIcon icon = (ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(f);
        java.awt.Image img = icon.getImage();
        BufferedImage bimg = (BufferedImage) img;
        return toFXImage(bimg,null);
    }

    public boolean IsDrive(File f){
        File[] sysroots = File.listRoots();
        for(int i=0; i<sysroots.length;i++)
        {if(f.equals(sysroots[i]) )return true; }
        return false;
    }


    public String calculateSize(File f){
        String s;long sizeInByte=0; Path path;
        if(IsDrive(f)){
            return Long.toString(f.getTotalSpace()/(1024*1024*1024))+"GB";
        }
        if (f.isDirectory()){
            return "";
        }
        path = Paths.get(f.toURI());
        //sizeInByte = f.getTotalSpace(); // terrible idea cz sob subfolder e 199GB, 99GB esob dekhay >_<
        try {
            sizeInByte = Files.size(path);//at least works ^_^
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(sizeInByte<(1024)){s = Long.toString(sizeInByte)+"B"; return s; }
        else if(sizeInByte>=(1024) && sizeInByte<(1024*1024)){ long sizeInKb = sizeInByte/1024; s = Long.toString(sizeInKb)+"KB"; return s; }
        else if(sizeInByte>=(1024*1024) && sizeInByte<(1024*1024*1024)){ long sizeInMb = sizeInByte/(1024*1024); s = Long.toString(sizeInMb)+"MB"; return s; }
        else if(sizeInByte>=(1024*1024*1024)){ long sizeInGb = sizeInByte/(1024*1024*1024); s = Long.toString(sizeInGb)+"GB"; return s; }

        return null;
    }






}
