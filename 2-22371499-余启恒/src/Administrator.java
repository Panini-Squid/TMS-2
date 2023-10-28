import java.util.ArrayList;
import java.util.Map;

public class Administrator extends User{

    public void listShopCommand(ArrayList<String> commands) {//查看指定商家的店铺
        // Kakafee卡号不合法
        if (!Tool.cardFormat(commands.get(1))) {
            System.out.println("Illegal Kakafee number");
            return;
        }
        // Kakafee卡号对应的用户未注册
        if (!Tool.isRegistered(commands.get(1))) {
            System.out.println("Kakafee number not exists");
            return;
        }
        // Kakafee卡号对应的用户身份不是Merchant
        if (!Tool.userIdentity(commands.get(1)).equals("Merchant")) {
            System.out.println("Kakafee number does not belong to a Merchant");
            return;
        }
        Merchant m1 = MerchantSystem.merchantList.get(commands.get(1));
        // Kakafee卡号对应的商家名下无店铺
        if (m1.getShopAmount() == 0) {
            System.out.println("Shop not exists");
            return;
        }
        for (Shop s : m1.shopList.values()) {
            if (ShopSystem.shopExist.get(s.getsID()) == 1) {
                System.out.printf("%s S-%d %s\n", s.getOwnerCard(), s.getsID(), s.getShopName());
            }
        }
    }

    public void listCommodityCommand(ArrayList<String> commands) {//查看商品
        if (commands.size() == 1) {//无参，查看所有商品
            // 当前系统无商品，或全部被批量下架
            if (!CommoditySystem.commodityState.containsValue(1)) {
                System.out.println("Commodity not exists");
                return;
            }
            boolean flag = false;
            for (Shop s : ShopSystem.shopList.values()) {
                if (ShopSystem.shopExist.get(s.getsID()) == 1) {
                    for (Commodity c : s.commodityList.values()) {
                        if (s.commodityState.get(c.getcID()) == 1 && CommoditySystem.commodityState.get(c.getcID()) == 1) {//判断上架/下架
                            flag = true;
                            System.out.printf("S-%d: C-%d %s %.2fyuan %d\n", s.getsID(), c.getcID(), c.getCommodityName(), c.getCommodityPrice(), c.getCommodityAmount());
                        }
                    }
                }
            }
            // 当前系统无商品（考虑被单个下架及商店被注销的情况）
            if (!flag) {
                System.out.println("Commodity not exists");
            }
        }
        else if (commands.size() == 2) {//参数为店铺编号
            // 店铺编号不合法
            if (!Tool.shopIDValid(commands.get(1))) {
                System.out.println("Illegal shop id");
                return;
            }
            String shopID = commands.get(1).split("-")[1];
            int sID = Integer.parseInt(shopID);
            // 店铺不存在或店铺已注销
            if (!ShopSystem.shopList.containsKey(sID) || ShopSystem.shopExist.get(sID) != 1) {//店铺未注册或已注销
                System.out.println("Shop id not exists");
                return;
            }
            Shop s1 = ShopSystem.shopList.get(sID);
            Merchant m1 = MerchantSystem.merchantList.get(s1.getOwnerCard());
            // 当前店铺无商品
            if (s1.commodityList.isEmpty()) {
                System.out.println("Commodity not exists");
                return;
            }
            boolean flag = false;
            for (Commodity c : s1.commodityList.values()) {
                if (s1.commodityState.get(c.getcID()) == 1 && CommoditySystem.commodityState.get(c.getcID()) == 1 && m1.commodityState.get(c.getcID()) == 1) {//判断上架/下架
                    flag = true;
                    System.out.printf("S-%d: C-%d %s %.2fyuan %d\n", s1.getsID(), c.getcID(), c.getCommodityName(), c.getCommodityPrice(), c.getCommodityAmount());
                }
            }
            // 全被下架的情况
            if (!flag) {
                System.out.println("Commodity not exists");
            }
        }
    }

    public void searchCommodityCommand(String commodityName) {//查找商品
        // 商品名称不合法
        if (!Tool.commodityNameFormat(commodityName)) {
            System.out.println("Illegal commodity name");
            return;
        }
        boolean flag = false;
        for (Shop s : ShopSystem.shopList.values()) {
            if (ShopSystem.shopExist.get(s.getsID()) == 1) {//店铺未注销
                for (Commodity c : s.commodityList.values()) {
                    if (c.getCommodityName().equals(commodityName) && c.getCommodityAmount() != 0 && CommoditySystem.commodityState.get(c.getcID()) == 1 && s.commodityState.get(c.getcID()) == 1) {
                        flag = true;
                        System.out.printf("S-%d: C-%d %s %.2fyuan %d\n",s.getsID(), c.getcID(), c.getCommodityName(), c.getCommodityPrice(), c.getCommodityAmount());
                    }
                }
            }
        }
        // 商品不存在或商品数量均为零
        if (!flag) {
            System.out.println("Commodity not exists");
        }
    }

    public void removeCommodityCommand(ArrayList<String> commands, User u1) {//下架商品（注意区分不同下架方式）
        // 无可选参数
        if (commands.size() == 2) {
            // 商品编号不合法
            if (!Tool.commodityIDValid(commands.get(1))) {
                System.out.println("Illegal commodity id");
                return;
            }
            String commodityID = commands.get(1).split("-")[1];
            int cID = Integer.parseInt(commodityID);
            // 商品编号不存在
            if (cID > CommoditySystem.getCommodityCategory()) {
                System.out.println("Commodity id not exists");
                return;
            }
            // 商品已被批量下架
            if (CommoditySystem.commodityState.get(cID) != 1) {
                System.out.println("Commodity id not exists");
                return;
            }
            // 下架商品成功
            CommoditySystem.commodityState.put(cID, -1);
            System.out.println("Remove commodity success");
        }
        // 可选参数为店铺编号
        else {
            // 商品编号不合法
            if (!Tool.commodityIDValid(commands.get(1))) {
                System.out.println("Illegal commodity id");
                return;
            }
            String commodityID = commands.get(1).split("-")[1];
            int cID = Integer.parseInt(commodityID);
            // 店铺编号不合法
            if (!Tool.shopIDValid(commands.get(2))) {
                System.out.println("Illegal shop id");
                return;
            }
            String shopID = commands.get(2).split("-")[1];
            int sID = Integer.parseInt(shopID);
            // 店铺编号不存在，或已被注销
            if (sID > ShopSystem.getShopAmount() || ShopSystem.shopExist.get(sID) != 1) {
                System.out.println("Shop id not exists");
                return;
            }
            Shop s1 = ShopSystem.shopList.get(sID);
            // 商品编号不存在
            if (cID > CommoditySystem.getCommodityCategory()) {
                System.out.println("Commodity id not exists");
                return;
            }
            // 商品不属于该店铺，或已被下架
            if (!s1.commodityList.containsKey(cID) || s1.commodityState.get(cID) != 1 || CommoditySystem.commodityState.get(cID) != 1) {
                System.out.println("Commodity id not exists");
                return;
            }
            // 下架商品成功
            System.out.println("Remove commodity success");
            Commodity c1 = s1.commodityList.get(cID);
            c1.setCommodityAmount(0);
            s1.commodityState.put(cID, -1);
        }
    }

    public void cancelShopCommand(ArrayList<String> commands, User u1) {//注销店铺
        // 店铺编号不合法
        if (!Tool.shopIDValid(commands.get(1))) {
            System.out.println("Illegal shop id");
            return;
        }
        String shopID = commands.get(1).split("-")[1];
        int sID = Integer.parseInt(shopID);
        // 店铺编号未注册
        if (sID > ShopSystem.getShopAmount()) {
            System.out.println("Shop id not exists");
            return;
        }
        // 店铺已注销
        if (ShopSystem.shopExist.get(sID) != 1) {
            System.out.println("Shop id not exists");
            return;
        }
        Shop s1 = ShopSystem.shopList.get(sID);
        Merchant m1 = MerchantSystem.merchantList.get(s1.getOwnerCard());
        // 注销店铺成功
        System.out.println("Cancel shop success");
        //ShopSystem.shopList.remove(sID);
        ShopSystem.shopExist.put(sID, -1);
        m1.setShopAmount(m1.getShopAmount() - 1);
    }
}
