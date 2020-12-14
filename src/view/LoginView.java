package view;

import Dao.UserDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;


public class LoginView {

    public Object[] result = null;
    //超市收银系统主界面————登录界面
    public LoginView(){
        //定义控件
        JFrame mainFrame = new JFrame("桥底股份有限公司——收银管理系统");
        JButton login = new JButton("登录");
        JLabel register = new JLabel("注册");
        JLabel findPassward = new JLabel("修改密码");
        JTextField jobNumber = new JTextField(20);//工号输入框JTextField
        JPasswordField password = new JPasswordField(20);//密码输入框JPasswordField
        JLabel jobNumber_l = new JLabel("工号:");
        JLabel passward_l = new JLabel("密码：");
        JLabel superMarket = new JLabel("超市收银管理系统");
        superMarket.setFont(new Font("宋体",Font.BOLD,20));
        //定义控件格式
        login.setBounds(210,260,80,25);
        register.setBounds(330,190,50,20);
        findPassward.setBounds(320,220,100,20);
        jobNumber.setBounds(190,190,120,20);
        password.setBounds(190,220,120,20);
        jobNumber_l.setBounds(150,190,100,20);
        passward_l.setBounds(150,220,100,20);
        superMarket.setBounds(160,100,400,20);
        //添加控件
        JPanel center = new JPanel();//新建面板
        center.setLayout(null);//设置绝对布局
        mainFrame.add(center,BorderLayout.CENTER);//将center加入到容器中采用边界布局方式,将center居中
        center.add(superMarket);
        center.add(jobNumber_l);
        center.add(jobNumber);
        center.add(passward_l);
        center.add(password);
        center.add(login);
        center.add(register);
        center.add(findPassward);
        mainFrame.setLocation(550,300); //打开界面的位置
        mainFrame.setSize(500,400); //界面大小
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//在此窗体上发起 "close" 时默认执行的操作,使用 System exit 方法退出应用程序。
        mainFrame.setVisible(true);//窗口显示

        //监听事件
        mainFrame.addWindowListener(new WindowAdapter() { //窗口监听
            @Override
            public void windowClosing(WindowEvent e) { //关闭
                System.exit(0);
            }
        });
        register.addMouseListener(new MouseAdapter() {  //注册监听
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater((new Runnable() {
                    @Override
                    public void run() {
                    new RegisterView().init();
                    mainFrame.dispose();//关闭
                    }
                }));
            }

            @Override
            public void mouseEntered(MouseEvent e) {//指针停留时候蓝色
                register.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                register.setForeground(Color.blue);
            }

            @Override
            public void mouseExited(MouseEvent e) {//指针不在的时候黑色
                register.setCursor(Cursor.getDefaultCursor());
                register.setForeground(Color.BLACK);
            }
        });

        findPassward.addMouseListener(new MouseAdapter() { //修改密码监听
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater((new Runnable() {
                    @Override
                    public void run() {
                        new FindPasswardView().init();
                        mainFrame.dispose();//关闭
                    }
                }));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                findPassward.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                findPassward.setForeground(Color.blue);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                findPassward.setCursor(Cursor.getDefaultCursor());
                findPassward.setForeground(Color.BLACK);
            }
        });

        login.addMouseListener(new MouseAdapter() {  //登录监听
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        String jobnum = jobNumber.getText();//获取工号
                        String pwd = String.valueOf(password.getPassword());//获取密码
                        if (!jobnum.equals("") && !pwd.equals("")){ //当账号或密码不为空时
                            UserDao user = new UserDao();
                            try {
                                result = user.selectUser(jobnum,pwd); //根据用户查询信息
                                if (result[0] != null){
                                    JOptionPane.showMessageDialog(null,"登录成功！");
                                    jobNumber.setText("");
                                    password.setText("");
                                    mainFrame.dispose();//关闭
                                    new MenuView(jobnum, (String) result[1]);
                                }
                                else{ //当账号或密码错误时
                                    JOptionPane.showMessageDialog(null,"用户名或密码错误！");
                                }
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        }
                        else{//当账号或密码不为空时
                            JOptionPane.showMessageDialog(null,"账号或密码不能为空！");
                        }

                    }
                });
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                login.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                login.setCursor(Cursor.getDefaultCursor());
            }
        });
    }
}
