import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class LoginDemo1 {

    public void temp(login_interface lo){
        boolean flag_1;
        flag_1 = lo.mMain();
        if (flag_1){
            System.out.println("登录成功" + "\nflag = " + flag_1);
        }else {
            System.out.println("失败");
        }
    }

    public static void main(String[] args) { //主方法
        LoginDemo1 LOGINS = new LoginDemo1();
        LOGINS.temp(new login_interface(false));
    }
}


class EmailVerification {
    public static String rand(){
        StringBuilder str = new StringBuilder();
        str.append((int) (Math.random() * 9 + 1));
        for (int i = 0; i < 5; i++) {
            str.append((int) (Math.random() * 10));
        }
        return str.toString();
    }

    public static String sendEmail(String email){
        HtmlEmail mailUtil = new HtmlEmail();
        mailUtil.setHostName("pop.qq.com");
        mailUtil.setAuthentication("emailvarification@qq.com", "cltooflnmhjwchde");
        mailUtil.setCharset("utf-8");
        String randomNumber = rand();
        try {
            mailUtil.addTo(email);
            mailUtil.setFrom("emailvarification@qq.com");
            mailUtil.setSubject("邮箱验证码【"+randomNumber+"】");
            mailUtil.setMsg("亲爱的用户：\n\n\n" +
                    "您好！您正在进行邮箱验证，本次请求的验证码为：\n【" + randomNumber + "】\n"
                    + "请在十分钟内使用，请勿提供给他人"
                    + "\n\n\n\t\tBy TrumpeT" );
            mailUtil.send();
        } catch (EmailException e) {
            System.out.println("验证码发送失败，请勿频繁提交请求");
        }
        return randomNumber;
    }
}


class login_interface{

    static int METERING = 0;

    private static boolean flag = false;

    login_interface(boolean flag){
        login_interface.flag = flag;
    }

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/userinfo?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    static final String USER = "root";
    static final String PASS = "111111";

    static String usernameTemp;
    static String pwdTemp;
    static String VERIFICATIONCODE;

    boolean mMain(){
        if (METERING == 0){
            METERING += 1;
            LOGIN();
        }
        return flag;
    }

    public static void MySQL_Insert(String INSERT_ID,String INSERT_PWD,String Email){
        Connection conn = null;
        Statement stmt = null;

        try{
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();

            String sql, sql_insert, sql_2, sql_3;
            sql_2 = "create database if not exists userinfo character set utf8";
            sql_3 = """
                    create table if not exists info(
                        id varchar(20) not null unique ,
                        password varchar(20) not null
                    )""";
            sql = "SELECT id, password FROM userinfo.info";

            ResultSet rs = stmt.executeQuery(sql);
            stmt.execute(sql_2);
            stmt.execute(sql_3);

            PreparedStatement INSERT;
            sql_insert = "INSERT INTO userinfo.info VALUES (?,?,?,?)";
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            java.util.Date date = new Date(System.currentTimeMillis());
            String registerDate = formatter.format(date);
            INSERT = conn.prepareStatement(sql_insert);
            INSERT.setString(1, INSERT_ID);
            INSERT.setString(2, INSERT_PWD);
            INSERT.setString(3, registerDate);
            INSERT.setString(4, Email);
            try {
                INSERT.executeUpdate();
            }catch (SQLIntegrityConstraintViolationException ignored){}

            rs.close();
            stmt.close();
            conn.close();
        } catch(Exception se){
            se.printStackTrace();
        }
        finally{
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException ignored){
            }
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }

    public static void LOGIN(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sWidth = (int) screenSize.getWidth();
        int sHeight = (int) screenSize.getHeight();
        int jWIDTH = 400;
        int jHEIGHT = 254;
        String GeShi = "                                                                                                     ";
        final int[] clickNum = {0};

        JFrame jf = new JFrame("Login");
        jf.setBounds(sWidth/2-jWIDTH/2,sHeight/2-jHEIGHT/2,jWIDTH,jHEIGHT);//设置位置及宽高
        jf.setLayout(new FlowLayout(FlowLayout.CENTER));//中心布局

        JLabel label1 = new JLabel("             ID:");//=====================
        JTextField usernameText = new JTextField(33);//窗体零件声明
        JLabel label2  = new JLabel("password:");
        JPasswordField pwdText = new JPasswordField(33);
        JTextArea out = new JTextArea(7,40);
        out.setBackground(Color.decode("#EEEEEE"));
        JButton button = new JButton("login");
        JButton register = new JButton("register");
        JLabel developInfo = new JLabel(GeShi+"Developed by TrumpeT");
        developInfo.setFont(new Font("Dialog", Font.ITALIC,9));//========


        register.addActionListener(new AbstractAction() {//注册按钮事件监听
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame rjf = new JFrame("register");
                rjf.setBounds((sWidth/2-jWIDTH/2)+50,(sHeight/2-jHEIGHT/2)+50,jWIDTH,jHEIGHT);
                rjf.setVisible(true);
                rjf.setLayout(new FlowLayout(FlowLayout.CENTER));

                JLabel label1_1 = new JLabel("             ID:");
                JTextField usernameText_1 = new JTextField(33);

                JLabel label2_1  = new JLabel("password:");
                JPasswordField pwdText_1 = new JPasswordField(33);

                JLabel emailLabel = new JLabel("          邮箱:");
                JTextField emailText = new JTextField(33);

                JLabel verificationCode = new JLabel("验证码：");
                JTextField verificationText = new JTextField(8);

                JButton verificationButton = new JButton("点击发送");
                verificationButton.setBackground(Color.decode("#b8e3e9"));
                verificationButton.setFont(new Font("Dialog", Font.PLAIN,11));

                JTextArea MeiYong = new JTextArea(3,40);
                MeiYong.setBackground(Color.decode("#EEEEEE"));

                String RegularExpression = "[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)";
                // 用于匹配邮箱的正则表达式

                verificationButton.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        clickNum[0] += 1;
                        String email1 = emailText.getText();
                        if (email1.matches(RegularExpression)) {
                            MeiYong.setText("");
                            if (clickNum[0] <= 2){
                                VERIFICATIONCODE = EmailVerification.sendEmail(email1);
                            }
                            if (clickNum[0] >= 2){
                                MeiYong.setText("     请勿频繁点击发送按钮，可能会导致验证码功能暂时无法使用");
                            }
                        }else {
                            MeiYong.setText("\n\t               ！！！ 邮箱格式错误 ！！！");
                            MeiYong.setFont(new Font("Dialog", Font.ITALIC,12));
                        }
                    }
                });

                JButton register_1 = new JButton("register");

                JLabel developInfo1 = new JLabel(GeShi+"Developed by TrumpeT");
                developInfo1.setFont(new Font("Dialog", Font.ITALIC,9));

                register_1.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        usernameTemp = usernameText_1.getText();
                        pwdTemp = String.valueOf(pwdText_1.getPassword());
                        String email = emailText.getText();

                        String yzm = verificationText.getText();

                        if (!usernameTemp.isEmpty() && !pwdTemp.isEmpty()){
                            if (yzm.equals(VERIFICATIONCODE)){
                                MySQL_Insert(usernameTemp,pwdTemp,email);

                                java.util.List<String> list_2;//=======================================
                                list_2 = USERINFO();//账号密码验证数据处理中接收mysql返回变量
                                java.util.List<String> id2;
                                java.util.List<String> pwd2;
                                id2 = list_2.subList(0, (list_2.size()/2));
                                pwd2 = list_2.subList(((list_2.size())/2), list_2.size());
                                java.util.List<String> finalId2 = id2;
                                java.util.List<String> finalPwd2 = pwd2;//===============================

                                if (finalId2.contains(usernameTemp) && finalPwd2.contains(pwdTemp)){
                                    JDialog jdSuccess = new JDialog(rjf, "Successful");
                                    jdSuccess.setBounds((sWidth/2-jWIDTH/2)+75,(sHeight/2-jHEIGHT/2)+75,jWIDTH/2+50,jHEIGHT/2);
                                    JLabel SuccessLabel = new JLabel("     注册成功!");

                                    jdSuccess.add(SuccessLabel);
                                    jdSuccess.setVisible(true);
                                    jdSuccess.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                                } else {
                                    JDialog jdFail = new JDialog(rjf, "fail");
                                    jdFail.setBounds((sWidth/2-jWIDTH/2)+75,(sHeight/2-jHEIGHT/2)+75,jWIDTH,jHEIGHT/2);
                                    JLabel failLabel = new JLabel("注册失败，可能为用户名重复，请重试。若再次失败，请联系开发人员");
                                    jdFail.add(failLabel);
                                    jdFail.setVisible(true);
                                    jdFail.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                                }
                            } else {
                                JDialog fail = new JDialog(rjf, "failure");
                                fail.setBounds((sWidth/2-jWIDTH/2)+75,(sHeight/2-jHEIGHT/2)+75,jWIDTH/2,jHEIGHT/2);
                                JLabel failLabel = new JLabel("      验证码不正确！");
                                fail.add(failLabel);
                                fail.setVisible(true);
                                fail.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            }
                        }else {
                            MeiYong.setText("\n\t     ID 或 password 不能为空！");
                        }
                    }
                });

                rjf.add(label1_1);
                rjf.add(usernameText_1);
                rjf.add(label2_1);
                rjf.add(pwdText_1);
                rjf.add(emailLabel);
                rjf.add(emailText);
                rjf.add(verificationCode);
                rjf.add(verificationText);
                rjf.add(verificationButton);
                rjf.add(MeiYong);
                rjf.add(register_1);
                rjf.add(developInfo1);

                rjf.setResizable(false);
            }
        });


        button.addActionListener(new AbstractAction() { //登录按钮事件监听
            @Override
            public void actionPerformed(ActionEvent e) {

                java.util.List<String> list_1;//=======================================
                list_1 = USERINFO();//账号密码验证数据处理中接收mysql返回变量
                java.util.List<String> id;
                java.util.List<String> pwd1;
                id = list_1.subList(0, (list_1.size()/2));
                pwd1 = list_1.subList(((list_1.size())/2), list_1.size());
                java.util.List<String> finalId = id;
                java.util.List<String> finalPwd = pwd1;//===============================

                String username = usernameText.getText();
                String pwd = String.valueOf(pwdText.getPassword());

                if (finalId.contains(username) && finalPwd.contains(pwd)) {
                    out.setText("""
                            
                            \t\t登录成功
                            \t\t请稍等.......""");
                    //****************************************
                    flag = true;
                    LoginDemo1 $ = new LoginDemo1();
                    $.temp(new login_interface(flag));
                    //****************************************
                }else {
                    out.setText("\n\t\t登录失败");
                }
            }
        });

        jf.add(label1);
        jf.add(usernameText);
        jf.add(label2);
        jf.add(pwdText);
        jf.add(out);
        jf.add(button);
        jf.add(register);
        jf.add(developInfo);

        jf.setResizable(false);//禁止改变窗口大小
        button.setSize(40,20);
        register.setSize(40,20);
        jf.setVisible(true);//窗口显示
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    //=================================================================================================================
    public static ArrayList<String> USERINFO(){ // Mysql读取方法
        Connection conn = null;
        Statement stmt = null;

        String id;
        String pwd;

        java.util.List<String> idArr = new ArrayList<>();
        List<String> pwdArr = new ArrayList<>();

        try{
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();

            String sql;
            sql = "SELECT id, password FROM userinfo.info";

            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                id = rs.getString("id");
                idArr.add(id);
                pwd = rs.getString("password");
                pwdArr.add(pwd);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch(Exception se){
            se.printStackTrace();
        }
        finally{
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException ignored){
            }
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        ArrayList<String> AllInfo = new ArrayList<>();
        AllInfo.addAll(idArr);
        AllInfo.addAll(pwdArr);
        return AllInfo;
    }
}