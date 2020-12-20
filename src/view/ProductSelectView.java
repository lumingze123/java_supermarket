package view;

import Dao.GoodsDao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
/*
商品信息查询界面
输入商品Id可以查询商品信息
 */

public class ProductSelectView {
    //信息查询界面

    public static void init(){
        JFrame productFrame = new JFrame("商品信息查询");
        JLabel message = new JLabel("请输入商品ID...");
        JLabel label = new JLabel("商品信息：");
        JTable table=new JTable();
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("名称");
        tableModel.addColumn("价格");
        tableModel.addColumn("库存");
        tableModel.addColumn("入库时间");
        JScrollPane jScrollPane = new JScrollPane(table);
        jScrollPane.setBounds(40,130,400,220);
        table.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JTextField id = new JTextField(20);
        JButton del = new JButton("删除");
        JButton select = new JButton("查询");
        label.setFont(new Font("宋体",Font.BOLD,15));
        message.setFont(new Font("宋体",Font.BOLD,15));
        message.setBounds(10,30,200,25);
        id.setBounds(40,60,400,25);
        del.setBounds(300,90,60,20);
        select.setBounds(370,90,60,20);
        label.setBounds(10,110,90,20);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        productFrame.add(panel);
        panel.add(message);
        panel.add(id);
        panel.add(del);
        panel.add(select);
        panel.add(label);
        panel.add(jScrollPane);
        productFrame.setSize(500,400);
        productFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        productFrame.setVisible(true);
        productFrame.setLocation(550,300);

        select.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    if(tableModel.getRowCount() == 1)
                        tableModel.removeRow(tableModel.getRowCount() - 1);
                    //点击查询
                    String name = id.getText();
                    GoodsDao goodsDao = new GoodsDao();
                    try {
                        Object[] data= goodsDao.selectProductID(name);
                        if (data[0] !=null){
                            //将返回的结果添加到显示表
                            tableModel.addRow(data);
                        }else{
                            JOptionPane.showMessageDialog(null,"该商品不存在！");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            }
        });
        del.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        GoodsDao goodsDao = new GoodsDao();
                        String id = (String) table.getValueAt(table.getSelectedRow(),0);
                        try {
                            int result = goodsDao.delProduct(id);
                            if (result != -1){
                                JOptionPane.showMessageDialog(null,"删除成功！");
                                tableModel.setRowCount(0);
                            }else{
                                JOptionPane.showMessageDialog(null,"删除失败！");
                            }
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
