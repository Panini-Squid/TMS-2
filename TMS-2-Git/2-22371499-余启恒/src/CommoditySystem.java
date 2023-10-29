import java.util.HashMap;

public class CommoditySystem {
    private static int commodityCategory = 0;//已有商品种类

    public static HashMap<Integer, Commodity> commodityList = new HashMap<>();//商品编号与商品变量相对应

    public static HashMap<Integer, Integer> commodityState = new HashMap<>();//记录某一商品的状态,编号对应是否下架（能不能再上架1, 0, -1）

    public static int getCommodityCategory() {
        return commodityCategory;
    }

    public static void setCommodityCategory(int commodityCategory) {
        CommoditySystem.commodityCategory = commodityCategory;
    }


}
