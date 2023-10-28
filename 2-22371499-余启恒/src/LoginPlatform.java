import java.util.ArrayList;
import java.util.HashMap;

public class LoginPlatform {
    private static boolean Logged = false;
    public static HashMap<String, ArrayList<String>> infoMap = new HashMap<>();
//存的是注册成功的卡号和注册信息，卡号——整行命令
    public static boolean isLogged() {
        return Logged;
    }

    public static void setLogged(boolean logged) {
        Logged = logged;
    }

    public void registerCommand(ArrayList<String> commands) {
        if (commands.size() != 6) {
            System.out.println("Illegal argument count");
            return;
        }
        if (Logged) {
            System.out.println("Already logged in");
            return;
        }
        if (!Tool.cardFormat(commands.get(1))) {
            System.out.println("Illegal Kakafee number");
            return;
        }
        if (Tool.isRegistered(commands.get(1))) {
            System.out.println("Kakafee number exists");
            return;
        }
        if (!Tool.nameFormat(commands.get(2))) {
            System.out.println("Illegal name");
            return;
        }
        if (!Tool.passwordFormat(commands.get(3))) {
            System.out.println("Illegal password");
            return;
        }
        if (!Tool.passwordMatch(commands.get(3), commands.get(4))) {
            System.out.println("Passwords do not match");
            return;
        }
        if (!Tool.identityLegal(commands.get(5))) {
            System.out.println("Illegal identity");
            return;
        }
        System.out.println("Register success");
        if (commands.get(5).equals("Merchant")) {//这里新建对象的时候尽量把信息都放进去
            Merchant m1 = new Merchant();
            m1.setIdentity(commands.get(5));
            m1.setCard(commands.get(1));
            m1.setName(commands.get(2));
            m1.setPassword(commands.get(3));
            m1.setShopAmount(0);
            MerchantSystem.merchantList.put(commands.get(1), m1);
        }
        else if (commands.get(5).equals("Customer")) {
            Customer c1 = new Customer();
            c1.setIdentity(commands.get(5));
            c1.setCard(commands.get(1));
            c1.setName(commands.get(2));
            c1.setPassword(commands.get(3));
            CustomerSystem.customerList.put(commands.get(1), c1);
        }
        else if (commands.get(5).equals("Administrator")) {
            Administrator a1 = new Administrator();
            a1.setIdentity(commands.get(5));
            a1.setCard(commands.get(1));
            a1.setName(commands.get(2));
            a1.setPassword(commands.get(3));
            AdministratorSystem.administratorList.put(commands.get(1), a1);
        }
        infoMap.put(commands.get(1), commands);//先笼统放进去，一会再处理
    }
}
