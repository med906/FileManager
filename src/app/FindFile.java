package app;

import javafx.collections.FXCollections;


import java.io.*;

import java.util.ArrayList;

class FindFile
{
    private DataTransfer data;

    public void setData(DataTransfer data) {
        this.data = data;
    }

    public DataTransfer getData() {
        return data;
    }

    public void findFile(String name, File file)
    {
        ArrayList<FileInfo> matches = new ArrayList<FileInfo>();
        File[] list = file.listFiles();

        if(list!=null)
            for (File fil : list)
            {
                if (fil.isDirectory())
                {
                    findFile(name,fil);
                }
                else if (fil.getName().toLowerCase().contains(name.toLowerCase()))
                {

                    FileInfo f = data.getTableView().createRow(fil.getAbsolutePath());
                    if(f!=null){
                        matches.add(f);
                    }
                    data.getTableView().addTableFiled(FXCollections.observableArrayList(matches));

                }
            }

    }




}