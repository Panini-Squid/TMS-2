public class Tool {
    public static boolean cardFormat(String card) {
        String card_pattern =
                "^((?!.*0000))(((([0-3][0-9])|(40|41|42|43|44))\\d{2})|(4500))((178[5-9])|(179\\d)|(18[0-7]\\d)|(188[0-6]))(([1-2]\\d{3})|(3000))$";
        return card.matches(card_pattern);
    }

    public static boolean nameFormat(String name) {
        String name_pattern = "^[A-Za-z][A-Za-z_]{3,15}$";
        return name.matches(name_pattern);
    }

    public static boolean passwordFormat(String psw) {
        for (int i = 0; i < psw.length(); i++) {
            if ((psw.charAt(i)<'0' || psw.charAt(i) >'9')&&(psw.charAt(i)<'a'||psw.charAt(i)>'z')&&(psw.charAt(i)<'A'||psw.charAt(i)>'Z')
                    &&psw.charAt(i)!='@'&&psw.charAt(i)!='_'&&psw.charAt(i)!='%'&&psw.charAt(i)!='$') {
                return false;
            }
        }
        String password_pattern = "^(?=.*\\d)(?=.*[A-Za-z])(?=.*(@|_|%|\\$)).{8,16}$";
        return psw.matches(password_pattern);
    }

    public static boolean passwordMatch(String psw1, String psw2) {
        return psw1.equals(psw2);
    }

    public static boolean isRegistered(String card) {
        return LoginPlatform.infoMap.containsKey(card);
    }

    public static boolean identityLegal(String id) {
        return switch (id) {
            case "Administrator", "Merchant", "Customer" -> true;
            default -> false;
        };
    }

    public static void printUserInfo(String card) {
        System.out.println("Name: " + LoginPlatform.infoMap.get(card).get(2));
        System.out.println("Kakafee number: " + LoginPlatform.infoMap.get(card).get(1));
        System.out.println("Type: " + LoginPlatform.infoMap.get(card).get(5));
    }

    public static boolean passwordCorrect(String card, String psw) {
        return psw.equals(LoginPlatform.infoMap.get(card).get(3));
    }

    public static boolean shopIDValid(String id) {//判断店铺编号参数合法性
        String shopIDPattern = "^[S][-][1-9][0-9]{0,8}$";
        return id.matches(shopIDPattern);
    }


    public static boolean shopNameFormat(String name) {//判断店铺名称合法性
        String shopNamePattern = "^[A-Za-z][A-Za-z_-]{0,49}$";
        return name.matches(shopNamePattern);
    }


    public static boolean commodityIDValid(String id) {//判断商品编号参数合法性
        String commodityIDPattern = "^[C][-][1-9][0-9]{0,8}$";
        return id.matches(commodityIDPattern);
    }


    public static boolean commodityNameFormat(String name) {//判断商品名称合法性
        String commodityNamePattern = "^[A-Za-z][A-Za-z_-]{0,49}$";
        return name.matches(commodityNamePattern);
    }


    public static boolean commodityPriceValid(String price) {//判断商品单价合法性
        double commodityPrice = Double.parseDouble(price);
        if (commodityPrice <= 0 || commodityPrice > 99999999.99) {//单价必须大于 0，小于等于 99999999.99
            return false;
        }
        if (price.contains(".") && price.length() - (price.indexOf(".") + 1) > 2) {//最高保留到小数点后两位
            return false;
        }
        return true;
        //有可能要考虑前导0.谁知道呢，过不了再改
    }

    public static boolean commodityAmountValid(String amount) {//判断商品数量参数合法性
        double commodityAmount = Double.parseDouble(amount);
        if (commodityAmount <= 0) {//商品数量必须大于 0
            return false;
        }
        if (amount.contains(".") || amount.charAt(0) == '0') {//数量必须为整数，不能有前导零
            return false;
        }
        return true;
    }

    public static String userIdentity(String card) {
        return LoginPlatform.infoMap.get(card).get(5);
    }
}