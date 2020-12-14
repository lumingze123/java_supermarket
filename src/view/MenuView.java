package view;

import Util.GetFilePath;
import Util.SwingUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuView {
    //选择所需功能界面
    public MenuView(String ID,String name){
        JFrame menuFrame = new JFrame("菜单");
        //员工信息
        JTextArea user = new JTextArea("\n工号："+ID+"\n\n姓名："+name+"\n");
        user.setFont(new Font("宋体",Font.BOLD,20));
        user.setEditable(false);
        user.setBackground(Color.white);
        //处理头像
        GetFilePath getFilePath = new GetFilePath();
        String path = getFilePath.getFilePath("photo.jpg");
        ImageIcon imageIcon = new ImageIcon(path);
        Image image =imageIcon.getImage();
        SwingUtil swing = new SwingUtil();
        imageIcon=swing.createAutoAdjustIcon(image,true);
        JLabel photo = new JLabel(imageIcon,JLabel.CENTER);
        photo.setBorder(BorderFactory.createLineBorder(Color.white));
        //选项卡
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("文件");
        JMenuItem menuItem = new JMenuItem("退出登录");
        menuItem.setAccelerator(KeyStroke.getKeyStroke('Q', InputEvent.CTRL_DOWN_MASK));
        menu.add(menuItem);
        menuBar.add(menu);
        menuFrame.setJMenuBar(menuBar);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new LoginView();
                menuFrame.dispose();
            }
        });
        //面板设计
        JPanel down = new JPanel();
        JButton in = new JButton("商品信息录入");
        JButton find = new JButton("商品信息查询");
        JButton cash = new JButton("结账");
        JButton cashCard = new JButton("购物卡管理");
        user.setBounds(10,10,200,100);
        photo.setBounds(300,10,100,100);
        in.setBounds(80,200,120,22);
        find.setBounds(240,200,120,22);
        cash.setBounds(80,240,120,22);
        cashCard.setBounds(240,240,120,22);
        down.setLayout(null);
        menuFrame.add(down);
        down.add(user);
        down.add(photo);
        down.add(in);
        down.add(find);
        down.add(cash);
        down.add(cashCard);
        menuFrame.setLocation(550,300);
        menuFrame.setSize(500,400);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setVisible(true);

        //功能监听
        menuFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        //商品录入
        in.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                       new ProductIforamationInView().init();

                    }
                });
            }
        });
        //商品查询
        find.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new ProductSelectView().init();
                    }
                });
            }
        });
        //结账
        cash.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new CashView(ID);
                    }
                });
            }
        });
        //购物卡管理
        cashCard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
//                        menuFrame.setState(Frame.ICONIFIED);
                        new CashCardView().init();

                    }
                });
            }
        });

    }


}
