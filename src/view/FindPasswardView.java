package view;

import Dao.UserDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class FindPasswardView {
    //修改密码界面
    public void init(){
        JFrame passwardFrame = new JFrame("收银员账号密码修改");
        JLabel message = new JLabel("请输入信息...");
        JLabel no_l = new JLabel("工号:");
        JLabel passward_l_old = new JLabel("旧密码:");
        JLabel passward_l_new = new JLabel("新密码:");
        JTextField name_t = new JTextField(20);
        JPasswordField passward_old = new JPasswordField(20);
        JPasswordField passward_new = new JPasswordField(20);
        JButton enter = new JButton("确定");
        JButton cancel = new JButton("取消");
        //设置控件格式
        message.setFont(new Font("宋体",Font.BOLD,15));
        message.setBounds(10,30,200,25);
        no_l.setBounds(30,100,100,25);
        name_t.setBounds(80,100,200,25);
        passward_l_old.setBounds(30,130,100,25);
        passward_old.setBounds(80,130,200,25);
        passward_l_new.setBounds(30,160,100,25);
        passward_new.setBounds(80,160,200,25);
        enter.setBounds(100,250,100,22);
        cancel.setBounds(230,250,100,22);
        //添加控件
        JPanel panel = new JPanel();
        panel.setLayout(null);
        passwardFrame.add(panel);
        panel.add(message);
        panel.add(no_l);
        panel.add(name_t);
        panel.add(passward_l_old);
        panel.add(passward_old);
        panel.add(passward_l_new);
        panel.add(passward_new);
        panel.add(enter);
        panel.add(cancel);
        passwardFrame.setResizable(false);
        passwardFrame.setSize(500,400);
        passwardFrame.setVisible(true);
        passwardFrame.setLocation(550,300);
        passwardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        enter.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    String jobnum = name_t.getText();
                    String pwd_old = String.valueOf(passward_old.getPassword());
                    String pwd_new = String.valueOf(passward_new.getPassword());
                    UserDao user = new UserDao();
                    try {
                        Object[] result = null;
                        System.out.println(jobnum+pwd_old);
                        result  =user.selectUser(jobnum,pwd_old); //根据用户查询信息
                        if (result[0] != null){
                            int res = user.updateUser(jobnum,pwd_new);
                            if (res>0){
                                JOptionPane.showMessageDialog(null,"修改成功！");
                                name_t.setText("");
                                passward_old.setText("");
                                passward_new.setText("");
                                passwardFrame.dispose();//关闭
                                new LoginView();
                            }
                            else{
                                JOptionPane.showMessageDialog(null,"修改失败！");
                            }
                        }
                        else{ //当账号或密码错误时
                            JOptionPane.showMessageDialog(null,"用户名或密码错误！");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
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
                    passwardFrame.dispose();
                    new LoginView();
                }
            });
            }
        });

    }

}
