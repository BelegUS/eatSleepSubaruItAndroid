package pl.cendrzak.szymon.SubaruSsm;

public class NavigationItem {
    String id;
    String ItemName;
    int imgResID;

    public NavigationItem(String identificator, String itemName, int imgResID) {
        super();
        id = identificator;
        ItemName = itemName;
        this.imgResID = imgResID;
    }

    public String getId() { return id; }
    public String getItemName() {
        return ItemName;
    }
    public void setItemName(String itemName) {
        ItemName = itemName;
    }
    public int getImgResID() {
        return imgResID;
    }
    public void setImgResID(int imgResID) {
        this.imgResID = imgResID;
    }
}
