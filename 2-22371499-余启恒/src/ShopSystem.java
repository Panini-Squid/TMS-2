import java.util.HashMap;
import java.util.Map;

public class ShopSystem {
    private static int shopAmount = 0;

    public static int getShopAmount() {
        return shopAmount;
    }

    public static void setShopAmount(int shopAmount) {
        ShopSystem.shopAmount = shopAmount;
    }

    public static HashMap<Integer, Shop>shopList = new HashMap<>();//编号——店铺对象

    public static HashMap<Integer, Integer>shopExist = new HashMap<>();//编号——存在1注销-1

    public static void listAllShop(String identity) {//由顾客或管理员调用
        switch (identity) {
            case "Customer":
                for (Map.Entry<Integer, Shop>entry : shopList.entrySet()) {
                    if (shopExist.get(entry.getKey()) == 1) {
                        System.out.println("S-" + entry.getKey() + " " + entry.getValue().getShopName());//店铺id 店铺名称
                    }
                }
                break;
            case "Administrator":
                for (Shop s : shopList.values()) {
                    if (shopExist.get(s.getsID()) == 1) {
                        System.out.printf("%s S-%d %s\n", s.getOwnerCard(), s.getsID(), s.getShopName());
                    }
                }
                break;
        }
    }
}
