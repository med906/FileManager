package app;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.ImageView;

import java.io.File;


public class FileInfo {



    private ImageView image;
    private SimpleStringProperty currentDirPath;
    private SimpleStringProperty name;
    private SimpleStringProperty size;
    private SimpleStringProperty date;

//    ImageView image,
    public FileInfo(  ImageView image, String path,String name, String size, String date){
        super();
        this.image = image;
        this.currentDirPath = new SimpleStringProperty(path);
        this.name = new SimpleStringProperty(name);
        this.size = new SimpleStringProperty(size);
        this.date = new SimpleStringProperty(date);
    }



    public String getDate(){ return date.get();}
    public String getCurrentDirPath() {return currentDirPath.get();}
    public String getSize(){return size.get();}
    public String getName(){return name.get();}
    public void setImage(ImageView value) {image = value;}
    public ImageView getImage() {return image;}






}
