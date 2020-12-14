package view;

import Dao.CardDao;
import Dao.CashCardDao;
import Dao.CashDao;
import Dao.GoodsDao;

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
		//cashFrame.setResizable(false);//禁止改变界面大小
		//查询面板===============================
		JPanel top=new JPanel();
		JLabel vipID=new JLabel("会员卡ID:");
		JTextField vipCard_t = new JTextField(10);
		vipCard_t.setEditable(false);
		JRadioButton isVip_y =new JRadioButton("是");
		JRadioButton isVip_n = new JRadioButton("否");
		ButtonGroup bg = new ButtonGroup();
		bg.add(isVip_n);
		bg.add(isVip_y);
		//添加单选按钮的点击事件
		isVip_y.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						vipCard_t.setEditable(true);
					}
				});
			}
		});
		isVip_n.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						vipCard_t.setEditable(false);
						vipCard_t.setText("");
					}
				});
			}
		});


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

		JLabel integal_l=new JLabel("积分:");
		JTextField integal_t=new JTextField(5);//用来输出积分的输入框
		integal_t.setEditable(false);
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
		top.add(vipID);
		top.add(vipCard_t);
		top.add(isVip_y);
		top.add(isVip_n);
		top.add(cashCard_l);
		top.add(cashCard_t);
		top.add(iscashCard_y);
		top.add(iscashCard_n);
		top.add(integal_l);
		top.add(integal_t);
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
		Object[] columnName = {"ID","名称","单价","数量","小计"};

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
						String vipID = vipCard_t.getText();
						String cashCardID = cashCard_t.getText();
						try {
							if (!vipID.equals("") && vipCard_t.isEditable()){
								Object[] vipID_db = new CardDao().selectCashCard(vipID);

								if (vipID_db[0].equals(vipID)){
									integal_t.setText((String) vipID_db[2]);//如果有vip卡就显示积分
								}else {
									JOptionPane.showMessageDialog(null,"会员卡号不能为空");
								}
							}else {
								integal_t.setText("");
							}
							if (!cashCardID.equals("") && cashCard_t.isEditable()){
								Object[] cashCard_db = new CashCardDao().selectCashCard(cashCardID);
								if (cashCard_db.equals(cashCard_db)){
									money_t.setText((String) cashCard_db[2]);//如果有购物卡就显示余额
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
							Object[] product_db = new Object[5];
							try {
								product_db = new GoodsDao().selectProductID(productID);
							} catch (SQLException ex) {
								ex.printStackTrace();
							}

							if (product_db[0]!=null){
								if ((Integer.parseInt(product_db[3].toString())-Integer.parseInt(productNum))>=0){
									//"ID","名称","单价","数量","小计"
									product_db[3]=productNum; //数量=输入数量
									int num = Integer.parseInt(productNum);
									double money =Double.parseDouble(product_db[2].toString());
									double a_money=num*money;//算出总价
									product_db[4]=a_money;//小计=数量*单价
									tableModel.addRow(product_db);//添加到tableModel
									allMoney += Double.parseDouble(productTable.getValueAt(productTable.getRowCount()-1,4).toString());
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
				//super.mouseClicked(e);
			}
		});

		//结账
		block.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String mess = "";
				for (int i = 0;i <= productTable.getRowCount()-1;i++){
					//mess="物品1 ￥100*1=100.0\n"+...
					mess += productTable.getValueAt(i,1).toString() + " ￥" + productTable.getValueAt(i,2).toString() + "*" + productTable.getValueAt(i,3).toString() + "="+productTable.getValueAt(i,4).toString()+"\n";

				}
				mess +="是否确认结账";
				int enter = JOptionPane.showConfirmDialog(null,mess);//弹窗显示
				if (enter==0){//是
					String vipID  = vipCard_t.getText();
					String cashID = cashCard_t.getText();
					Object[] objects = new Object[3];
					try {
						objects = new CashCardDao().selectCashCard(cashID);//查找购物卡
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
					//算总价钱还有判断是否办会员卡
					if(objects[1] != null && objects[0] != null){ //有购物卡且是会员
						double discount = 0.9;
						allMoney*=discount;//打九折
						if (Double.parseDouble(objects[2].toString())>=allMoney){
							double cardMoney = Double.parseDouble(objects[2].toString())-allMoney;//购物卡减去价钱
							try {
								new CashCardDao().updateMoney(cashID,String.valueOf(cardMoney));
							} catch (SQLException ex) {
								ex.printStackTrace();
							}
						}else {
							JOptionPane.showMessageDialog(null,"余额不足");
							return;
						}
						CashDao cashDao = new CashDao();
						CardDao cardDao = new CardDao();
						try{
							cashDao.insertCard(ID,vipID,productID_t.getText(),String.valueOf(productNum_t.getText()),String.valueOf(allMoney),String.valueOf(discount),cashID);
							String integal = integal_t.getText();
							integal = integal+(int)allMoney;
							cardDao.updateIntegral(String.valueOf(objects[1]),integal);
						}catch (SQLException ex){
							ex.printStackTrace();
						}

					}else if(objects[0] != null && vipID.equals("")){ //有购物卡，不是会员
						System.out.println("222");
						double discount = 1.0;
						allMoney*=discount;
						if (Double.parseDouble(objects[2].toString())>=allMoney){
							double cardMoney = Double.parseDouble(objects[2].toString())-allMoney;//购物卡减去价钱
							try {
								new CashCardDao().updateMoney(cashID,String.valueOf(cardMoney));
							} catch (SQLException ex) {
								ex.printStackTrace();
							}
						}else {
							JOptionPane.showMessageDialog(null,"余额不足");
							return;

						}
						CashDao cashDao = new CashDao();
						CardDao cardDao = new CardDao();
						try{
							cashDao.insertCard(ID,vipID,productID_t.getText(),String.valueOf(productNum_t.getText()),String.valueOf(allMoney),String.valueOf(discount),cashID);
							String integal = integal_t.getText();
							integal = integal+(int)allMoney;
							cardDao.updateIntegral(String.valueOf(objects[1]),integal);
						}catch (SQLException ex){
							ex.printStackTrace();
						}
						if (allMoney>=200){
							try{
								String newVipID = JOptionPane.showInputDialog("消费满200办理会员卡","请输入会员卡号");
								String newVipName = JOptionPane.showInputDialog("消费满200办理会员卡","请输入会员姓名");
								cardDao.insertCashCard(newVipID,newVipName); //新建会员卡
								cashDao.insertCard(ID,null,productID_t.getText(),String.valueOf(productNum_t.getText()),String.valueOf(allMoney),String.valueOf(discount),cashID);
							}catch (SQLException ex){
								ex.printStackTrace();
							}
						}
					}else {  //没卡没会员
						System.out.println("333");
						double discount = 1.0;  //不打折
						allMoney *= discount;
						CashDao cashDao = new CashDao();
						CardDao cardDao = new CardDao();
						if (allMoney>=200){ //如果消费大于200
							try{
								String newVipID = JOptionPane.showInputDialog("消费满200办理会员卡","请输入会员卡号");
								String newVipName = JOptionPane.showInputDialog("消费满200办理会员卡","请输入会员姓名");
								if (!newVipID.equals("")&&!newVipName.equals("")){ //输入不为空
									int insertResult = cardDao.insertCashCard(newVipID, newVipName);
									if (insertResult == 1) {
										JOptionPane.showMessageDialog(null, "会员卡办理成功！");
									} else {
										JOptionPane.showMessageDialog(null, "会员卡办理失败！");
									}
									//添加账单记录
									cashDao.insertCard(ID, newVipID, productID_t.getText(), String.valueOf(productNum_t.getText()), String.valueOf(allMoney), String.valueOf(discount), null);
									String integal = integal_t.getText();
									integal = integal+(int)allMoney;
									cardDao.updateIntegral(String.valueOf(objects[1]),integal);
								}else {//输入为空
									JOptionPane.showMessageDialog(null, "会员卡办理失败！");
								}
							}catch (SQLException ex){
								ex.printStackTrace();
							}
						}
					}
					//库存更新
					for (int i = 0;i <= productTable.getRowCount()-1;i++) {
						String name = (String) tableModel.getValueAt(i, 1);
						String id = (String) tableModel.getValueAt(i, 0);
						String stock = (String) tableModel.getValueAt(i, 3);
						GoodsDao goodsDao = new GoodsDao();
						try {
							Object[] obi = goodsDao.selectProduct(name);
							int newStock = Integer.parseInt(obi[3].toString()) - Integer.parseInt(stock);
							System.out.println(newStock);
							if (newStock > 0) {
								goodsDao.updateProduct(id, String.valueOf(newStock));
							} else {

							}

						} catch (SQLException ex) {
							ex.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, "结算成功！");
						for (int r = 0; r < tableModel.getRowCount(); i++) {
							tableModel.removeRow(r);
						}
					}
				}else if (enter==1){//否
					JOptionPane.showMessageDialog(null,"请您继续选购商品！");
				}else {//取消
					JOptionPane.showMessageDialog(null,"已取消本次购物！");
					for (int i = 0;i <= productTable.getRowCount()-1;i++) {
						for (int r = 0; r < tableModel.getRowCount(); i++) {
							tableModel.removeRow(r);
						}
					}

				}

			}

		});



	}






}
