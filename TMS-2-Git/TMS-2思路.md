## TMS-2思路

### Merchant类

#### 成员变量：

shopList，店铺列表（map类型，编号和Shop类型的变量对应）

shopAmount，店铺数量，int类型



#### 成员方法：

registerShopCommand()，判断参数数量，店铺数量、名称

shopLimit()，判断店铺数量是否满5，

shopNameExist()，判断店铺名称是否重复，因为只要不和自己重复就行，所以是自己的成员方法

listShopCommand()，查看店铺信息

listCommodityCommand()，查看商品

searchCommodityCommand()，查找商品

removeCommodityCommand()，下架商品（注意区分不同下架方式）

cancelShopCommand()，注销店铺



### MerchantSystem类

用来记录商家信息，以便根据卡号调取商家信息

#### 成员变量：

merchantList，商家列表，map类型，Kakafee卡号与Merchant类型的变量相对应



### Shop类

用来记录单一店铺的编号，名称等属性，以及进行商品上下架相关操作

#### 成员变量：

sID，商店编号

shopName，商店名称

commodityList，商品表，map类型，商品编号与商品变量相对应（这里的商品数量是全局量）

commodityState，商品状态表，map类型，商品编号与商品上下架相对应



#### 成员方法：

putCommodityCommand()，上架商品，要执行包括上架新商品和已有商品的18种检查功能





### ShopSystem类

负责记录和管理所有商家的所有店铺信息，包括店铺编号、是否注销

#### 成员变量：

shopAmount，已有店铺数量，int类型，用来编号

shopBelongs，店铺属于哪个商家，map类型，编号——所属商家

shopExist，店铺还在还是注销，map类型，编号——是否注销



### Tool类（已有）

#### 新增方法：

shopIDValid()，判断店铺编号参数合法性

shopNameFormat()，判断店铺名称合法性

commodityIDValid()，判断商品编号参数合法性

commodityNameFormat()，判断商品名称合法性

commodityPriceValid()，判断商品单价合法性

commodityAmountValid()，判断商品数量参数合法性



### Test类（已有）

#### 新增功能：

加入了新的指令，你懂的……



### Commodity类

用来记录单一商品的编号，数量（包括在店铺内和商家内两种），名称，单价等属性

#### 成员变量：

cID，商品编号

commodityName，商品名称

commodityPrice，商品单价

commodityAmount，商品数量



### CommoditySystem类

用来进行上下架商品，记录商品编号等等操作

#### 成员变量：

commodityCategory，已有商品种类，int类型，用来编号

commodityState，记录某一商品的状态，map类型，编号对应状态，下架（能不能再上架）



### Customer类

#### 成员方法：

listCommodityCommand()，查看商品

searchCommodityCommand()，查找商品

buyCommodityCommand()，购买商品



### Administer类

listCommodityCommand()，查看商品

searchCommodityCommand()，查找商品

removeCommodityCommand()，下架商品（注意区分不同下架方式）

cancelShopCommand()，注销店铺