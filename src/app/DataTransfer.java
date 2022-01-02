package app;

import javafx.scene.control.Label;

public class DataTransfer {

    private Label locationLb;
    private ClassTableView tableView;
    private ClassTreeItem treeCreator;


    public DataTransfer( Label locationLb,ClassTableView tableView) {
        this.locationLb = locationLb;
        this.tableView = tableView;

    }


    public void setTreeCreator(ClassTreeItem treeCreator) {
        this.treeCreator = treeCreator;
    }

    public ClassTreeItem getTreeCreator() {
        return treeCreator;
    }

    public void setLocationLb(Label locationLb) {
        this.locationLb = locationLb;
    }

    public void setTableView(ClassTableView tableView) {
        this.tableView = tableView;
    }


    public Label getLocationLb() {
        return locationLb;
    }

    public ClassTableView getTableView() {
        return tableView;
    }


}




