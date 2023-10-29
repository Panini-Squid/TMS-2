import java.util.ArrayList;
import java.util.HashMap;

public class Shop {
    private int sID;
    private String shopName;

    private String ownerCard;
    public HashMap<Integer, Commodity>commodityList = new HashMap<>();//商品编号与商品变量相对应
    public HashMap<Integer, Integer>commodityState = new HashMap<>();//商品编号与商品上下架相对应

    public int getsID() {
        return sID;
    }

    public void setsID(int sID) {
        this.sID = sID;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getOwnerCard() {
        return ownerCard;
    }

    public void setOwnerCard(String ownerCard) {
        this.ownerCard = ownerCard;
    }

}
