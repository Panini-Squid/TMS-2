import java.util.ArrayList;

public class User {
    private String identity;
    private String card;
    private String name;
    private String password;

    public User(String identity, String card, String name, String password) {
        this.identity = identity;
        this.card = card;
        this.name = name;
        this.password = password;
    }

    public User() {
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void loginCommand(ArrayList<String> commands, User u1) {
        if (commands.size() != 3) {
            System.out.println("Illegal argument count");
            return;
        }
        if (LoginPlatform.isLogged()) {
            System.out.println("Already logged in");
            return;
        }
        if (!Tool.cardFormat(commands.get(1))) {
            System.out.println("Illegal Kakafee number");
            return;
        }
        if (!Tool.isRegistered(commands.get(1))) {
            System.out.println("Kakafee number not exists");
            return;
        }
        if (!Tool.passwordCorrect(commands.get(1), commands.get(2))) {
            System.out.println("Wrong password");
            return;
        }
        System.out.println("Welcome to TMS");
        LoginPlatform.setLogged(true);
        setInfo(commands, u1);//补充对象的个人信息
    }

    public void logoutCommand(ArrayList<String> commands, User u1) {
        if (commands.size() != 1) {
            System.out.println("Illegal argument count");
            return;
        }
        if (!LoginPlatform.isLogged()) {
            System.out.println("Please log in first");
            return;
        }
        System.out.println("Bye~");
        LoginPlatform.setLogged(false);
        u1.identity = null;
        u1.card = null;
        u1.name = null;
        u1.password = null;//退出后抹除用户信息，看情况，用不上再删
    }

    public void printInfoCommand(ArrayList<String> commands, User u1) {
        if (commands.size() != 1 && commands.size() != 2) {
            System.out.println("Illegal argument count");
            return;
        }
        if (!LoginPlatform.isLogged()) {
            System.out.println("Please log in first");
            return;
        }
        if (commands.size() == 1) {
            Tool.printUserInfo(u1.card);
        }
        else {
            if (!u1.identity.equals("Administrator")) {
                System.out.println("Permission denied");
            }
            else if (!Tool.cardFormat(commands.get(1))) {
                System.out.println("Illegal Kakafee number");
            }
            else if (!Tool.isRegistered(commands.get(1))) {
                System.out.println("Kakafee number not exist");
            }
            else {
                Tool.printUserInfo(commands.get(1));
            }
        }
    }

    public void setInfo(ArrayList<String> commands, User u1) {
        u1.setCard(LoginPlatform.infoMap.get(commands.get(1)).get(1));
        u1.setName(LoginPlatform.infoMap.get(commands.get(1)).get(2));
        u1.setPassword(LoginPlatform.infoMap.get(commands.get(1)).get(3));
        u1.setIdentity(LoginPlatform.infoMap.get(commands.get(1)).get(5));
    }
}
