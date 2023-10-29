public class Commodity {
    private int cID;//商品编号
    private String commodityName;//商品名称
    private double commodityPrice;//商品单价
    private int commodityAmount;//商品数量

    //有可能还要加所属商店

    public int getcID() {
        return cID;
    }

    public void setcID(int cID) {
        this.cID = cID;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public double getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(double commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    public int getCommodityAmount() {
        return commodityAmount;
    }

    public void setCommodityAmount(int commodityAmount) {
        this.commodityAmount = commodityAmount;
    }
}
