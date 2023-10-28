import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        LoginPlatform lp1 = new LoginPlatform();
        ShopSystem ss1 = new ShopSystem();
        CommoditySystem cs1 = new CommoditySystem();
        MerchantSystem ms1 = new MerchantSystem();
        User u1 = new User();

        Scanner sc = new Scanner(System.in);

        while (true) {
            String input = sc.nextLine();
//            String password_pattern = "^(((([0-3][0-9])|(40|41|42|43|44))\\d{2})|(4500))((178[5-9])|(179\\d)|(18[0-7]\\d)|(188[0-6]))(([1-2]\\d{3})|(3000))$";
            input = input.trim();//去掉前后空格
            String[] inputs = input.split("\\s+");
            ArrayList<String> commands = readCommand(inputs);
//            System.out.println(commands);
            if (!judgeCommand(commands.get(0))) {//命令符未定义
                System.out.println("Command '"+ commands.get(0) +"' not found");
                continue;
            }
            switch (commands.get(0)) {
                case "quit" -> {
                    if (commands.size() == 1) {
                        quitCommand();
                    }
                    // 参数数量不合法
                    else {
                        System.out.println("Illegal argument count");
                    }
                }
                case "register" -> lp1.registerCommand(commands);
                case "login" -> u1.loginCommand(commands, u1);
                case "logout" -> u1.logoutCommand(commands, u1);
                case "printInfo" -> u1.printInfoCommand(commands, u1);
                case "registerShop" -> {
                    // 参数数量不合法
                    if (commands.size() != 2) {
                        System.out.println("Illegal argument count");
                    }
                    // 未登录
                    else if (!LoginPlatform.isLogged()) {
                        System.out.println("Please log in first");
                    }
                    // 登录用户身份不是Merchant
                    else if (!u1.getIdentity().equals("Merchant")) {
                        System.out.println("Permission denied");
                    }
                    else {
                        Merchant m1 = MerchantSystem.merchantList.get(u1.getCard());
                        m1.registerShopCommand(commands, m1);
                    }
                }
                case "putCommodity" -> {
                    // 参数数量不合法
                    if (commands.size() != 4 && commands.size() != 5) {
                        System.out.println("Illegal argument count");
                    }
                    // 未登录
                    else if (!LoginPlatform.isLogged()) {
                        System.out.println("Please log in first");
                    }
                    // 登录用户身份不是Merchant
                    else if (!u1.getIdentity().equals("Merchant")) {
                        System.out.println("Permission denied");
                    }
                    else {
                        Merchant m1 = MerchantSystem.merchantList.get(u1.getCard());
                        m1.putCommodityCommand(commands, u1);
                    }
                }
                case "listShop" -> {
                    // 参数数量不合法
                    if (commands.size() != 1 && commands.size() != 2) {
                        System.out.println("Illegal argument count");
                    }
                    // 未登录
                    else if (!LoginPlatform.isLogged()) {
                        System.out.println("Please log in first");
                    }
                    else if (commands.size() == 1) {//无参数
                        switch (u1.getIdentity()) {//这一部分有点冗长，有时间的话可以封装到类的成员方法里面
                            case "Customer":
                                // 当前系统无店铺或全部被注销
                                if (!ShopSystem.shopExist.containsValue(1)) {
                                    System.out.println("Shop not exists");
                                } else {
                                    ShopSystem.listAllShop("Customer");
                                }
                                break;
                            case "Administrator":
                                // 当前系统无店铺或全部被注销
                                if (!ShopSystem.shopExist.containsValue(1)) {
                                    System.out.println("Shop not exists");
                                }
                                else {
                                    ShopSystem.listAllShop("Administrator");
                                }
                                break;
                            case "Merchant":
                                Merchant m1 = MerchantSystem.merchantList.get(u1.getCard());
                                if (m1.getShopAmount() == 0) {
                                    System.out.println("Shop not exists");
                                }
                                else {
                                    m1.listShopCommand();
                                }
                                break;
                        }
                    } else {//1个参数（Kakafee卡号）
                        if (u1.getIdentity().equals("Administrator")) {
                            Administrator a1 = AdministratorSystem.administratorList.get(u1.getCard());
                            a1.listShopCommand(commands);
                        }
                        else {
                            System.out.println("Permission denied");
                        }
                    }
                }
                case "listCommodity" -> {
                    if (commands.size() != 1 && commands.size() != 2) {
                        System.out.println("Illegal argument count");
                    } else if (!LoginPlatform.isLogged()) {
                        System.out.println("Please log in first");
                    } else {
                        switch (u1.getIdentity()) {
                            case "Customer" -> {
                                Customer c1 = CustomerSystem.customerList.get(u1.getCard());
                                c1.listCommodityCommand(commands);
                            }
                            case "Administrator" -> {
                                Administrator a1 = AdministratorSystem.administratorList.get(u1.getCard());
                                a1.listCommodityCommand(commands);
                            }
                            case "Merchant" -> {
                                Merchant m1 = MerchantSystem.merchantList.get(u1.getCard());
                                m1.listCommodityCommand(commands, m1);
                            }
                        }
                    }
                }
                case "searchCommodity" -> {
                    // 参数数量不合法
                    if (commands.size() != 2) {
                        System.out.println("Illegal argument count");
                    }
                    // 未登录
                    else if (!LoginPlatform.isLogged()) {
                        System.out.println("Please log in first");
                    } else {
                        switch (u1.getIdentity()) {
                            case "Customer" -> {
                                Customer c1 = CustomerSystem.customerList.get(u1.getCard());
                                c1.searchCommodityCommand(commands.get(1));
                            }
                            case "Administrator" -> {
                                Administrator a1 = AdministratorSystem.administratorList.get(u1.getCard());
                                a1.searchCommodityCommand(commands.get(1));
                            }
                            case "Merchant" -> {
                                Merchant m1 = MerchantSystem.merchantList.get(u1.getCard());
                                m1.searchCommodityCommand(commands.get(1));
                            }
                        }
                    }
                }
                case "buyCommodity" -> {
                    if (commands.size() != 4) {
                        System.out.println("Illegal argument count");
                    } else if (!LoginPlatform.isLogged()) {
                        System.out.println("Please log in first");
                    } else if (!u1.getIdentity().equals("Customer")) {
                        System.out.println("Permission denied");
                    } else {
                        Customer c1 = CustomerSystem.customerList.get(u1.getCard());
                        c1.buyCommodityCommand(commands);
                    }
                }
                case "removeCommodity" -> {
                    // 参数数量不合法
                    if (commands.size() != 2 && commands.size() != 3) {
                        System.out.println("Illegal argument count");
                    }
                    // 未登录
                    else if (!LoginPlatform.isLogged()) {
                        System.out.println("Please log in first");
                    }
                    // 登录用户身份不是Merchant或Administrator
                    else if (!u1.getIdentity().equals("Merchant") && !u1.getIdentity().equals("Administrator")) {
                        System.out.println("Permission denied");
                    } else {
                        switch (u1.getIdentity()) {
                            case "Merchant" -> {
                                Merchant m1 = MerchantSystem.merchantList.get(u1.getCard());
                                m1.removeCommodityCommand(commands, m1);
                            }
                            case "Administrator" -> {
                                Administrator a1 = AdministratorSystem.administratorList.get(u1.getCard());
                                a1.removeCommodityCommand(commands, a1);
                            }
                        }
                    }
                }
                case "cancelShop" -> {
                    // 参数数量不合法
                    if (commands.size() != 2) {
                        System.out.println("Illegal argument count");
                    }
                    // 未登录
                    else if (!LoginPlatform.isLogged()) {
                        System.out.println("Please log in first");
                    }
                    // 登录用户身份不是Merchant或Administrator
                    else if (!u1.getIdentity().equals("Merchant") && !u1.getIdentity().equals("Administrator")) {
                        System.out.println("Permission denied");
                    }
                    switch (u1.getIdentity()) {
                        case "Merchant" -> {
                            Merchant m1 = MerchantSystem.merchantList.get(u1.getCard());
                            m1.cancelShopCommand(commands, m1);
                        }
                        case "Administrator" -> {
                            Administrator a1 = AdministratorSystem.administratorList.get(u1.getCard());
                            a1.cancelShopCommand(commands, a1);
                        }
                    }
                }
            }
        }

    }

    public static ArrayList<String> readCommand(String[] inputs) {
        ArrayList<String> commands = new ArrayList<>();
        for (String str : inputs) {
            commands.add(str);
        }
        return commands;
    }

    public static boolean judgeCommand(String command) {
        return switch (command) {
            case "quit", "register", "login", "logout", "printInfo", "registerShop", "putCommodity", "listShop", "listCommodity", "searchCommodity", "buyCommodity", "removeCommodity", "cancelShop" -> true;
            default -> false;
        };
    }

    public static void quitCommand() {
        System.out.println("----- Good Bye! -----");
        System.exit(0);
    }
}