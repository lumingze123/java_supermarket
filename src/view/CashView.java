package view;

import Dao.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class CashView{
	double allMoney = 0;
    //结账管理界面
	public CashView(String ID){
		JFrame cashFrame=new JFrame();
		cashFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);//窗口最大化
		cashFrame.setSize(500,400);
		cashFrame.setLocation(0,0);
		cashFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		cashFrame.setLayout(new BorderLayout());
		cashFrame.setResizable(false);//禁止改变界面大小
		//查询面板===============================
		JPanel top=new JPanel();
		JLabel cashCard_l = new JLabel("购物卡:");
		JTextField cashCard_t = new JTextField(10);
		cashCard_t.setEditable(false);
		JRadioButton iscashCard_y =new JRadioButton("是");
		JRadioButton iscashCard_n = new JRadioButton("否");
		ButtonGroup bg_cashCard = new ButtonGroup();
		bg_cashCard.add(iscashCard_y);
		bg_cashCard.add(iscashCard_n);

		//添加单选按钮的点击事件
		iscashCard_y.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					cashCard_t.setEditable(true);
				}
			});
			}
		});
		iscashCard_n.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					cashCard_t.setEditable(false);
					cashCard_t.setText("");
				}
			});
			}
		});

		JLabel money_l = new JLabel("余额");
		JTextField money_t = new JTextField(5);
		money_t.setEditable(false);
		JButton find=new JButton("查询");
		JLabel productID_l=new JLabel("商品号");
		JTextField productID_t=new JTextField(5);//用来输入商品号的输入框
		JLabel productNum_l =new JLabel("数量");
		JTextField productNum_t = new JTextField(5);
		JButton add=new JButton("添加");
		JButton del=new JButton("取消");

		//从上到下→从左到右
		top.add(cashCard_l);
		top.add(cashCard_t);
		top.add(iscashCard_y);
		top.add(iscashCard_n);
		top.add(money_l);
		top.add(money_t);
		top.add(find);
		top.add(productID_l);
		top.add(productID_t);
		top.add(productNum_l);
		top.add(productNum_t);
		top.add(add);
		top.add(del);

		cashFrame.add(top,BorderLayout.PAGE_START);//将查询面板添加到就frame上面
		//===============================================

		//显示面板========================================
		JTable productTable = new JTable();
		Object[] columnName = {"ID","名称","单价","数量","小计","折后价"};

		DefaultTableModel tableModel = (DefaultTableModel) productTable.getModel();

		for (int i=0;i<columnName.length;i++){
			tableModel.addColumn(columnName[i]);
		}
		JScrollPane jScrollPane = new JScrollPane(productTable);
		cashFrame.add(jScrollPane,BorderLayout.CENTER);//将查询面板添加到就frame中间
		//==============================================

		//结账面板========================================
		JPanel bottom = new JPanel();
		JLabel sum_l = new JLabel("共计");
		JTextField all_money_t=new JTextField(20);//显示金额
		all_money_t.setHorizontalAlignment(JTextField.CENTER);//居中
		all_money_t.setEditable(false);
		JLabel jLabel2=new JLabel("元");//最后点缀元
		JButton block=new JButton("结账");
		block.setSize(10,20);
		Dimension preferredSize = new Dimension(60,25);
		block.setPreferredSize(preferredSize);
		bottom.add(sum_l);
		bottom.add(all_money_t);
		bottom.add(jLabel2);
		bottom.add(block);
		cashFrame.add(bottom,BorderLayout.PAGE_END);//将查询面板添加到就frame下面
		//===================================================
		cashFrame.setVisible(true);//界面可视

		//查询监听
		find.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					String cashCardID = cashCard_t.getText();
					try {
						if (cashCard_t.isEditable()){
							Object[] cashCard_db = new CashCardDao().selectCashCard(cashCardID);
							if (cashCard_db.equals(cashCard_db)){
								money_t.setText(cashCard_db[3].toString());//如果有购物卡就显示余额
							}else{
								JOptionPane.showMessageDialog(null,"购物卡号不能为空");
							}
						}else {
							money_t.setText("");
						}
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
				}
			});
			}
		});
		//添加商品监听
		add.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					String productID = productID_t.getText();//商品id
					String productNum = productNum_t.getText();//商品数量
					if (!productID.equals("")&&!productNum.equals("")&&Integer.parseInt(productNum)!=0){//不为空且数量不为0
						Object[] product_db = new Object[6];
						try {
							product_db = new GoodsDao().selectProductID(productID);
						} catch (SQLException ex) {
							ex.printStackTrace();
						}

						if (product_db[0]!=null){
							if ((Integer.parseInt(product_db[3].toString())-Integer.parseInt(productNum))>=0){
								//"ID","名称","单价","数量","小计,“折后价"
								product_db[3]=productNum; //数量=输入数量
								int num = Integer.parseInt(productNum);
								double money =Double.parseDouble(product_db[2].toString());
								double a_money=num*money;//算出总价
								product_db[4]=a_money;//小计=数量*单价
								String cashID = cashCard_t.getText();
								Object[] objects = new Object[4];
								try {
									objects = new CashCardDao().selectCashCard(cashID);//查找购物卡
									if(!(objects[0]==null) && !(objects[1]==null)){
										product_db[5]=a_money*0.9;//小计=数量*单价
									}else{
										product_db[5]=a_money*1;
									}
								} catch (SQLException ex) {
									ex.printStackTrace();
								}

								tableModel.addRow(product_db);//添加到tableModel
								allMoney += Double.parseDouble(productTable.getValueAt(productTable.getRowCount()-1,5).toString());
								all_money_t.setText(String.valueOf(allMoney));
							}else {
								JOptionPane.showMessageDialog(null,"库存不足！");
							}
						}else{
							JOptionPane.showMessageDialog(null,"商品不存在！");
						}
					}else{
						JOptionPane.showMessageDialog(null,"商品号和数量不能为空");
					}
				}
			});
			}
		});

		//取消添加
		del.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				double money=0;
				if(productTable.getSelectedRow()<=0){
					JOptionPane.showMessageDialog(null,"请选中要取消的商品！");
					return;
				}
				money = Double.parseDouble(productTable.getValueAt(productTable.getSelectedRow(),5).toString());
				allMoney -= money;
				all_money_t.setText(String.valueOf(allMoney));
				int row = productTable.getSelectedRow();
				tableModel.removeRow(row);
			}
		});

		//结账
		block.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			if (allMoney == 0){
				JOptionPane.showMessageDialog(null,"请您继续选购商品！");
				return;
			};

			String mess = "";
			for (int i = 0;i <= productTable.getRowCount()-1;i++){
				//mess="物品1 ￥100*1=100.0\n"+...
				mess += productTable.getValueAt(i,1).toString() + " ￥" + productTable.getValueAt(i,2).toString() + "*" + productTable.getValueAt(i,3).toString() + "="+productTable.getValueAt(i,5).toString()+"\n";
			}
			mess +="实际消费:"+allMoney+"\n是否确认结账";
			int enter = JOptionPane.showConfirmDialog(null,mess);//弹窗显示
			if (enter==0) {//是
				String cashID = cashCard_t.getText();
				Object[] objects = new Object[4];
				try {
					objects = new CashCardDao().selectCashCard(cashID);//查找购物卡
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				//算总价钱还有判断是否办会员卡
				if (!(objects[0] == null) && !(objects[1] == null)) { //有购物卡且是会员
					System.out.println("111");
					double discount = 0.9;
					if (Double.parseDouble(objects[3].toString()) >= allMoney) {
						double cardMoney = Double.parseDouble(objects[3].toString()) - allMoney;//购物卡减去价钱
						try {
							new CashCardDao().updateMoney(cashID, cardMoney);
						} catch (SQLException ex) {
							ex.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null, "余额不足");
						return;
					}
					CashDao cashDao = new CashDao();
					VipCardDao cardDao = new VipCardDao();
					try {
						cashDao.insertCard(ID, objects[1].toString(), allMoney, discount, cashID);
					} catch (SQLException ex) {
						ex.printStackTrace();
					}

				} else if (!(objects[0] == null) && (objects[1] == null)) { //有购物卡，不是会员
					System.out.println("222");
					double discount = 1;
					if (Double.parseDouble(objects[3].toString()) >= allMoney) {
						double cardMoney = Double.parseDouble(objects[3].toString()) - allMoney;//购物卡减去价钱
						try {
							new CashCardDao().updateMoney(cashID, cardMoney);
						} catch (SQLException ex) {
							ex.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null, "余额不足");
						return;

					}
					CashDao cashDao = new CashDao();
					VipCardDao cardDao = new VipCardDao();
					if (allMoney >= 200) { //如果消费大于200
						try {
							String newVipName = JOptionPane.showInputDialog("消费满200办理会员卡\n请输入会员姓名", "");
							if (!newVipName.equals("")) { //输入不为空
								int insertResult = cardDao.updatevipCard(cashID, newVipName);
								if (insertResult == 1) {
									JOptionPane.showMessageDialog(null, "会员卡办理成功！");
								} else {
									JOptionPane.showMessageDialog(null, "会员卡办理失败！");
								}
								//添加账单记录
								cashDao.insertCard(ID, cashID, allMoney, discount, cashID);
							} else {//输入为空
								JOptionPane.showMessageDialog(null, "输入为空，会员卡办理失败！");
							}
						} catch (SQLException ex) {
							ex.printStackTrace();
						}
					}
				} else {  //没卡没会员
					System.out.println("333");
					CashDao cashDao = new CashDao();
					CashCardDao cardDao = new CashCardDao();
					double discount = 1;
					if (allMoney >= 200) { //如果消费大于200
						try {
							String newVipID = JOptionPane.showInputDialog("消费满200办理会员卡\n请输入会员卡号", "");
							String newVipName = JOptionPane.showInputDialog("消费满200办理会员卡\n请输入会员姓名", "");
							if (!newVipID.equals("") && !newVipName.equals("")) { //输入不为空
								int insertResult = cardDao.insertCashCard(newVipID,newVipName,newVipID);
								if (insertResult == 1) {
									JOptionPane.showMessageDialog(null, "会员卡办理成功！");
								} else {
									JOptionPane.showMessageDialog(null, "会员卡办理失败！");
								}
								//添加账单记录
								cashDao.insertCard(ID, newVipID, allMoney, discount, newVipID);
							} else {//输入为空
								JOptionPane.showMessageDialog(null, "输入为空，会员卡办理失败！");
							}
						} catch (SQLException ex) {
							ex.printStackTrace();
						}
					}
				}
				//库存更新
				GoodsDao goodsDao = new GoodsDao();
				SaleDao saleDao = new SaleDao();
				try {

					Double u_s = 0.0;
					for (int i = 0; i <= productTable.getRowCount() - 1; i++) {
						String id = (String) tableModel.getValueAt(i, 0);
						String name = (String) tableModel.getValueAt(i, 1);
						String stock = (String) tableModel.getValueAt(i, 3);
						Double qian = (Double) tableModel.getValueAt(i, 5);
						Object[] u_sale = saleDao.selectusale(ID);
						Object[] obi = goodsDao.selectProductID(name);
						u_s = Double.parseDouble(u_sale[1].toString()) + qian;
						System.out.println("销售额："+u_s);
						saleDao.updatesale(ID, u_s);

						int newStock = Integer.parseInt(obi[3].toString()) - Integer.parseInt(stock);
						int sale = Integer.parseInt(obi[5].toString()) + Integer.parseInt(stock);
						System.out.println(newStock);
						System.out.println(sale);
						if (newStock > 0 && sale >= 0) {
							goodsDao.updateProduct(id, newStock, sale);
						}
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}

				JOptionPane.showMessageDialog(null, "结算成功！\n实际消费:" + allMoney);
				for (int r = 0, i = 0; r < tableModel.getRowCount(); i++) {
					tableModel.removeRow(r);
				}

				all_money_t.setText("");
				allMoney = 0;

			}else if (enter==1){//否
				JOptionPane.showMessageDialog(null,"请您继续选购商品！");
			}else {//取消
				JOptionPane.showMessageDialog(null,"已取消本次购物！");
				for (int i = 0;i <= productTable.getRowCount()-1;i++) {
					for (int r = 0; r < tableModel.getRowCount(); i++) {
						tableModel.removeRow(r);
					}
				}
				all_money_t.setText("");
				allMoney=0;
			}
			}

		});
	}
}
