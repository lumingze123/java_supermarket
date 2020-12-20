package view;

import Dao.SaleDao;
import Dao.UserDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
/*
注册界面，注册新的收银员信息账号
 */
public class RegisterView {
    //注册页面
    public void init(){
        //定义控件
        JFrame registerFrame = new JFrame("收银员账号注册");
        JLabel message = new JLabel("请输入信息...");
        JLabel no_l = new JLabel("工号:");
        JLabel name_l = new JLabel("姓名:");
        JLabel passward_l = new JLabel("密码:");
        JTextField no_t = new JTextField(20);
        JTextField name_t = new JTextField(20);
        JPasswordField passward = new JPasswordField(20);
        JButton enter = new JButton("确定");
        JButton cancel = new JButton("取消");
        //设置控件格式
        message.setFont(new Font("宋体",Font.BOLD,15));
        message.setBounds(10,30,200,25);
        no_l.setBounds(30,100,100,25);
        no_t.setBounds(80,100,200,25);
        name_l.setBounds(30,130,100,25);
        name_t.setBounds(80,130,200,25);
        passward_l.setBounds(30,160,100,25);
        passward.setBounds(80,160,200,25);
        enter.setBounds(100,220,100,22);
        cancel.setBounds(230,220,100,22);
        //添加控件
        JPanel panel = new JPanel();
        panel.setLayout(null);
        registerFrame.add(panel);
        panel.add(message);
        panel.add(no_l);
        panel.add(no_t);
        panel.add(name_l);
        panel.add(name_t);
        panel.add(passward_l);
        panel.add(passward);
        panel.add(enter);
        panel.add(cancel);
        registerFrame.setResizable(false);
        registerFrame.setSize(500,450);
        registerFrame.setVisible(true);
        registerFrame.setLocation(550,300);
        registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        enter.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    String no = no_t.getText();
                    String name = name_t.getText();
                    String pwd = String.valueOf(passward.getPassword());
                    System.out.println(no);
                    System.out.println(name);
                    System.out.println(pwd);
                    System.out.println(name.equals(""));
                    if (!(name.equals("") && pwd.equals(""))){
                        UserDao user = new UserDao();
                        try {
                            int res = user.insertUser(no,name,pwd);
                            if (res>0){
                                JOptionPane.showMessageDialog(null,"新建账户成功！");
                                new LoginView();
                                registerFrame.dispose();
                            }else{
                                JOptionPane.showMessageDialog(null,"新建账户失败！");
                            }
                        }
                        catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }else {
                        JOptionPane.showMessageDialog(null,"账号或密码不能为空！");
                    }
                }
            });
            }
        });

        cancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new LoginView();
                    registerFrame.dispose();
                }
            });
            }
        });
    }
}
