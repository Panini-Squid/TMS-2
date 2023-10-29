import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Merchant extends User {
    public HashMap<Integer, Shop>shopList = new HashMap<>();//商家店铺列表，店铺编号——店铺对象
    private int shopAmount;//商家店铺数量
    public HashMap<Integer, Commodity>commodityList = new HashMap<>();//商品编号与商品变量相对应
    public HashMap<Integer, Integer>commodityState = new HashMap<>();//商品编号与商品上下架相对应

    public int getShopAmount() {
        return shopAmount;
    }

    public void setShopAmount(int shopAmount) {
        this.shopAmount = shopAmount;
    }

    public void registerShopCommand(ArrayList<String> commands, User u1) {
        // 拥有店铺已达最大数量
        if (shopLimit()) {
            System.out.println("Shop count reached limit");
            return;
        }
        // 店铺名称不合法
        if (!Tool.shopNameFormat(commands.get(1))) {
            System.out.println("Illegal shop name");
            return;
        }
        // 店铺名称已存在
        if (shopNameExist(commands.get(1))) {
            System.out.println("Shop name already exists");
            return;
        }
        Shop s1 = new Shop();
        ShopSystem.setShopAmount(ShopSystem.getShopAmount()+1);
        s1.setsID(ShopSystem.getShopAmount());
        s1.setShopName(commands.get(1));
        s1.setOwnerCard(getCard());
        shopAmount += 1;
        shopList.put(s1.getsID(), s1);
        ShopSystem.shopList.put(s1.getsID(), s1);
        ShopSystem.shopExist.put(s1.getsID(), 1);
        System.out.println("Register shop success (shopId: S-" + s1.getsID() + ")");
    }

    public boolean shopLimit() {//判断店铺数量是否满5
        return shopAmount >= 5;
    }

    public boolean shopNameExist(String name) {
        for (Shop s : shopList.values()) {
            if (ShopSystem.shopExist.get(s.getsID()) == 1) {
                if (s.getShopName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void putCommodityCommand(ArrayList<String> commands, User u1) {//商家上架商品
        //店铺编号和商品编号带前缀，不是只有数字
        if (commands.size() == 5) {//如果4个参数，参数1,2,3,4分别为店铺编号，商品名称，商品单价，商品数量
            // 店铺编号不合法
            if (!Tool.shopIDValid(commands.get(1))) {
                System.out.println("Illegal shop id");
                return;
            }
            //把带前缀的编号转化为数字，方便后面传参数
            String shopID = commands.get(1).split("-")[1];
            int sID = Integer.parseInt(shopID);
            //店铺未注册（ID不存在）
            if (!ShopSystem.shopExist.containsKey(sID)) {
                System.out.println("Shop id not exists");
                return;
            }
            Shop s1 = ShopSystem.shopList.get(sID);
            // 店铺不属于该商家
            if (!s1.getOwnerCard().equals(u1.getCard())) {
                System.out.println("Shop id not exists");
                return;
            }
            // 店铺已注销
            if (ShopSystem.shopExist.get(sID) != 1) {
                System.out.println("Shop id not exists");
                return;
            }
            //商品名称不合法
            if (!Tool.commodityNameFormat(commands.get(2))) {
                System.out.println("Illegal commodity name");
                return;
            }
            //商品单价不合法
            if (!Tool.commodityPriceValid(commands.get(3))) {
                System.out.println("Illegal commodity price");
                return;
            }
            //商品数量不合法
            if (!Tool.commodityAmountValid(commands.get(4))) {
                System.out.println("Illegal commodity quantity");
                return;
            }
            //成功上架
            double price = Double.parseDouble(commands.get(3));
            int amount = Integer.parseInt(commands.get(4));

            Commodity c1 = new Commodity();//存放在Merchant对象和CommoditySystem类中，全局
            Commodity c2 = new Commodity();//存放在店铺，单一
            CommoditySystem.setCommodityCategory(CommoditySystem.getCommodityCategory() + 1);
            c1.setcID(CommoditySystem.getCommodityCategory());
            c2.setcID(CommoditySystem.getCommodityCategory());
            c1.setCommodityName(commands.get(2));
            c2.setCommodityName(commands.get(2));
            c1.setCommodityPrice(price);
            c2.setCommodityPrice(price);
            c1.setCommodityAmount(amount);
            c2.setCommodityAmount(amount);
            commodityList.put(c1.getcID(), c1);//存放在Merchant对象
            CommoditySystem.commodityList.put(c1.getcID(), c1);//存放在CommoditySystem类
            CommoditySystem.commodityState.put(c1.getcID(), 1);//系统状态设置为上架
            commodityState.put(c1.getcID(), 1);//商家状态设置为上架

            s1.commodityList.put(c2.getcID(), c2);//存放在店铺（上架）
            s1.commodityState.put(c2.getcID(), 1);//店铺内状态设置为上架

            System.out.println("Put commodity success (commodityId: C-" + c1.getcID() + ")");

        }
        else if(commands.size() == 4) {//如果3个参数，参数1,2,3分别为店铺编号，商品编号，商品数量
            // 店铺编号不合法
            if (!Tool.shopIDValid(commands.get(1))) {
                System.out.println("Illegal shop id");
                return;
            }
            String shopID = commands.get(1).split("-")[1];
            int sID = Integer.parseInt(shopID);
            // 店铺未注册（ID不存在）
            if (!ShopSystem.shopExist.containsKey(sID)) {
                System.out.println("Shop id not exists");
                return;
            }
            Shop s1 = ShopSystem.shopList.get(sID);
            // 店铺不属于该店家
            if (!s1.getOwnerCard().equals(u1.getCard())) {
                System.out.println("Shop id not exists");
                return;
            }
            // 店铺已注销
            if (ShopSystem.shopExist.get(sID) != 1) {
                System.out.println("Shop id not exists");
                return;
            }
            // 商品编号不合法
            if (!Tool.commodityIDValid(commands.get(2))) {
                System.out.println("Illegal commodity id");
                return;
            }
            String commodityID = commands.get(2).split("-")[1];
            int cID = Integer.parseInt(commodityID);
            // 商品编号未注册
            if (!CommoditySystem.commodityList.containsKey(cID)) {
                System.out.println("Commodity id not exists");
                return;
            }
            // 商品编号不属于该商家
            if (!commodityList.containsKey(cID)) {
                System.out.println("Commodity id not exists");
                return;
            }
            // 商品已在全局下架
            if (commodityState.get(cID) != 1 || CommoditySystem.commodityState.get(cID) != 1) {
                System.out.println("Commodity id not exists");
                return;
            }
            // 商品数量不合法
            if (!Tool.commodityAmountValid(commands.get(3))) {
                System.out.println("Illegal commodity quantity");
                return;
            }

            int amount = Integer.parseInt(commands.get(3));

            if (s1.commodityList.containsKey(cID)) {//如果在这间店铺曾经或已经上架
                Commodity c1 = CommoditySystem.commodityList.get(cID);
                c1.setCommodityAmount(c1.getCommodityAmount() + amount);

                Commodity c2 = s1.commodityList.get(cID);
                c2.setCommodityAmount(c2.getCommodityAmount() + amount);

                s1.commodityState.put(c2.getcID(), 1);//店铺内状态设置为上架

                System.out.println("Put commodity success (commodityId: C-" + cID + ")");
            }
            else {//在商家的其他店铺上架过，但是没有在这间店铺上架过
                Commodity c1 = CommoditySystem.commodityList.get(cID);
                c1.setCommodityAmount(c1.getCommodityAmount() + amount);

                Commodity c2 = new Commodity();
                c2.setcID(cID);
                c2.setCommodityName(CommoditySystem.commodityList.get(cID).getCommodityName());
                c2.setCommodityPrice(CommoditySystem.commodityList.get(cID).getCommodityPrice());
                c2.setCommodityAmount(amount);
                s1.commodityList.put(c2.getcID(), c2);//存放在店铺
                s1.commodityState.put(c2.getcID(), 1);//店铺内状态设置为上架
                System.out.println("Put commodity success (commodityId: C-" + cID + ")");
            }
        }
    }

    public void listShopCommand() {//查看店铺信息
        for (Shop s : shopList.values()) {
            if (ShopSystem.shopExist.get(s.getsID()) == 1) {
                System.out.println("S-" + s.getsID() + " " + s.getShopName());
            }
        }
    }

    public void listCommodityCommand(ArrayList<String> commands, User u1) {//查看商品
        if (commands.size() == 1) {//无参，查看自己的所有商品
            boolean flag = false;
            for (Shop s : shopList.values()) {
                if (ShopSystem.shopExist.get(s.getsID()) == 1) {
                    for (Commodity c : s.commodityList.values()) {
                        if (s.commodityState.get(c.getcID()) == 1 && CommoditySystem.commodityState.get(c.getcID()) == 1) {//判断上架/下架
                            flag = true;
                            System.out.printf("S-%d: C-%d %s %.2fyuan %d\n", s.getsID(), c.getcID(), c.getCommodityName(), c.getCommodityPrice(), c.getCommodityAmount());
                        }
                    }
                }
            }
            // 当前系统无商品、商家名下店铺无商品
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
            // 店铺未注册
            if (!ShopSystem.shopList.containsKey(sID)) {
                System.out.println("Shop id not exists");
                return;
            }
            Shop s1 = ShopSystem.shopList.get(sID);
            // 店铺不属于该商家
            if (!s1.getOwnerCard().equals(u1.getCard())) {
                System.out.println("Shop id not exists");
                return;
            }
            // 店铺已注销
            if (ShopSystem.shopExist.get(sID) != 1) {
                System.out.println("Shop id not exists");
                return;
            }
            // 当前店铺无商品
            if (s1.commodityList.isEmpty()) {
                System.out.println("Commodity not exists");
                return;
            }
            boolean flag = false;
            for (Commodity c : s1.commodityList.values()) {
                if (s1.commodityState.get(c.getcID()) == 1 && CommoditySystem.commodityState.get(c.getcID()) == 1 && commodityState.get(c.getcID()) == 1) {//判断上架/下架
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
        for (Shop s : shopList.values()) {
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
            // 商品不属于该商家，或者已被下架
            if (!commodityList.containsKey(cID) || commodityState.get(cID) != 1 || CommoditySystem.commodityState.get(cID) != 1) {
                System.out.println("Commodity id not exists");
                return;
            }
            // 下架商品成功
            commodityState.put(cID, -1);
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
            // 店铺不属于该商家
            if (!s1.getOwnerCard().equals(u1.getCard())) {
                System.out.println("Shop id not exists");
                return;
            }
            // 商品编号不存在
            if (cID > CommoditySystem.getCommodityCategory()) {
                System.out.println("Commodity id not exists");
                return;
            }
            // 商品不属于该商家（不用判断，这条件太低优先级）
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
        // 已注销
        if (ShopSystem.shopExist.get(sID) != 1) {
            System.out.println("Shop id not exists");
            return;
        }
        Shop s1 = ShopSystem.shopList.get(sID);
        // 不属于自己
        if (!s1.getOwnerCard().equals(u1.getCard())) {
            System.out.println("Shop id not exists");
            return;
        }
        // 注销店铺成功
        System.out.println("Cancel shop success");
        //ShopSystem.shopList.remove(sID);
        ShopSystem.shopExist.put(sID, -1);
        shopAmount -= 1;
    }
}
