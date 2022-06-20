package Guanliyuan;
import java.awt.BorderLayout;

import Util.Public_;
import Util.DbUtil;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import Index.*;
@SuppressWarnings({"all"})
public class Guanliyuan extends JFrame implements ActionListener, Public_ {

	/*
	 * 
	 * 管理员端的界面
	 */
	JTable table;
	JLabel label1,label2,label3,label4;
	Object a[][];	//存放信息
	Object name[] = {"编号","玩具类型","玩具主","价格(元/天)","颜色","是否被租用","租用的用户"};
	JButton buttonOfXinxiluru,buttonOfXinxiliulan,buttonOfDelete,buttonOfLogout,buttonOfXiangXi,buttonOfXiugai;
	Box box1,box2;
	JTextField field,field2,field3;
	JPanel jPanel4,jPanel5;
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	public Guanliyuan(Boolean success)	//管理员界面，true即可展示表格
	{
		init();
		setVisible(true);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 200, 625, 490);
		setTitle("管理员界面");
		if(success)//successs是一个boolean类型，如果为true，打开此窗口直接信息浏览，false表里面没有信息，需要点击信息浏览！
		{
			xinXiLiuLan();
		}
	}
	
	public void init()	//管理员系统GUI
	{
		label1 = new JLabel("玩具租赁信息管理系统");
		buttonOfXinxiluru = new JButton("  玩具信息录入  ");
		buttonOfXinxiluru.addActionListener(this);
		buttonOfXinxiliulan = new JButton("  玩具信息浏览  ");
		buttonOfXinxiliulan.addActionListener(this);
		buttonOfDelete = new JButton("    删	            除      ");
		buttonOfDelete.addActionListener(this);
		buttonOfLogout = new JButton("  退   出   登   录  ");
		buttonOfLogout.addActionListener(this);
		buttonOfXiugai = new JButton("    修	           改      ");
		buttonOfXiugai.addActionListener(this);
		buttonOfXiangXi = new JButton("  详   细   信   息  ");
		buttonOfXiangXi.addActionListener(this);
		label2 = new JLabel("待删除信息编号：");
		label3 = new JLabel("待修改信息的编号：");
		label4 = new JLabel("待查询详情的编号：");
		field = new JTextField();
		field2 = new JTextField();
		field3 = new JTextField();
		
		a = new Object[50][7];	//设置显示的表格
		table = new JTable(a, name);//组件的创建
		table.setEnabled(false);
		JScrollPane scrollPane = new JScrollPane(table);	//这里设置格内大小自适应
		
		box1 = Box.createVerticalBox();
		box1.add(Box.createVerticalStrut(20));
		box1.add(buttonOfXinxiluru);
		box1.add(Box.createVerticalStrut(10));
		box1.add(buttonOfXinxiliulan);
		box1.add(Box.createVerticalStrut(15));
		box1.add(label2);
		box1.add(Box.createVerticalStrut(5));
		box1.add(field);
		box1.add(Box.createVerticalStrut(5));
		box1.add(buttonOfDelete);
		box1.add(Box.createVerticalStrut(25));
		box1.add(label3);
		box1.add(Box.createVerticalStrut(5));
		box1.add(field2);
		box1.add(Box.createVerticalStrut(5));
		box1.add(buttonOfXiugai);
		box1.add(Box.createVerticalStrut(25));
		box1.add(label4);
		box1.add(Box.createVerticalStrut(5));
		box1.add(field3);
		box1.add(Box.createVerticalStrut(5));
		box1.add(buttonOfXiangXi);
		box1.add(Box.createVerticalStrut(40));
		box1.add(buttonOfLogout);
		
		box2 = Box.createHorizontalBox();
		box2.add(Box.createHorizontalStrut(10));
		box2.add(box1);   //左边的按钮部分用 box布局
		
		jPanel4 = new JPanel();
		jPanel5 = new JPanel();
		jPanel4.setLayout(new BorderLayout());
		jPanel4.add(box2,BorderLayout.NORTH);//把左边的按钮部分放到jpanel4中。


		jPanel5.setLayout(new BorderLayout());
		jPanel5.add(label1,BorderLayout.NORTH);
		jPanel5.add(scrollPane,BorderLayout.CENTER);//把表格放jpanel5里
	
		this.setLayout(new BorderLayout());
		add(jPanel5,BorderLayout.EAST);
		add(jPanel4,BorderLayout.WEST);//把两个大的panel放到窗口里面

		
		
	}

	public void connDB() { // 连接数据库
		try {
			Class.forName("com.mysql.jdbc.Driver");//注册驱动
			//加载Class对象，并没有获取驱动实例
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {//创建连接
			con = DriverManager.getConnection(DbUtil.dbUrlString, DbUtil.dbUser, DbUtil.dbpassword);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void closeDB() // 关闭连接
	{
		try {
			stmt.close();
			con.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void 																																																xinXiLiuLan()//信息浏览的方法，因为删除数据后会刷新一下，自动调用此函数。
	{
		int i=0;
		 while(i<50)
		 {
			 a[i][0]=" ";
			 a[i][1]=" ";
			 a[i][2]=" ";
			 a[i][3]=" ";
			 a[i][4]=" ";
			 a[i][5]=" ";
			 a[i][6]=" "; 
			 i++;
		 }	//先全部置为空
		 i=0;
		 this.connDB();
		 try {	//发送sql语句
			stmt = con.createStatement();
			 String sql= "select * from toy_information";
			 rs = stmt.executeQuery(sql);
			 while(rs.next())
			 {
				 String number = rs.getString("number");
				 String toytype = rs.getString("toytype");
				 String toyower = rs.getString("toyower");
				 String price = rs.getString("price");
				 String color = rs.getString("color");
				 String  hire= rs.getString("hire");
				 String username= rs.getString("username");
				 a[i][0]=number;
				 a[i][1]=toytype;
				 a[i][2]=toyower;
				 a[i][3]=price;
				 a[i][4]=color;
				 a[i][5]=hire;
				 a[i][6]=username;
				 i++;
				 
			 }	//这里就是信息填入，select遍历并填入数组
			 this.closeDB();
			 repaint();	//重绘，不然无法展示
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 this.closeDB();
	}
	@Override
	public void actionPerformed(ActionEvent e) {	//按钮监听
		Object source = e.getSource();
		if(source == buttonOfXinxiluru)//点击信息修改按钮
		{
			this.dispose();
			new Luru();
		}
		else if(source == buttonOfXinxiliulan)//点击信息浏览按钮
		{
			xinXiLiuLan();
			
		}
		else if(source == buttonOfXiugai)//点击修改按钮
		{
			
			if(field2.getText().equals(""))
			{
				 JOptionPane.showMessageDialog(null, "输入修改玩具的编号！");
			}
			else
			{
				this.dispose();
				new Xiugai(field2.getText());
			}
		}
		else if(source == buttonOfXiangXi)//点击详细信息按钮
		{
			this.dispose();
			new Xiangxi(field3.getText());
		}
		else if(source == buttonOfDelete)//点击删除按钮
		{
			if(field.getText().equals(""))
			{
				JOptionPane.showMessageDialog(null, "请输入删除玩具的编号！");
			}
			else
			{
				this.connDB();
				String sql;
				try {
					stmt = con.createStatement();
					sql = "select * from toy_information  where number='"+field.getText()+"'";//表里找到需要删除的玩具信息
					rs = stmt.executeQuery(sql);
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				try {
					if(rs.next())//判断是否有 输入编号的玩具
					{
						
						int n = JOptionPane.showConfirmDialog(this, "确定删除此玩具信息？","确认对话框",JOptionPane.YES_NO_OPTION);//确认文本框
						if(n == JOptionPane.YES_OPTION)
						{	
							String hire2 = rs.getString("hire");
							if(hire2.equals("是"))
							{
								int m = JOptionPane.showConfirmDialog(this, "此玩具正在被租用，是否删除？","确认对话框",JOptionPane.YES_NO_OPTION);//确认文本框
								if(m == JOptionPane.YES_OPTION)
								{
									try
									{
										stmt = con.createStatement();
										String sql2 = "delete from toy_information where number='"+field.getText()+"';";
										stmt.executeUpdate(sql2);
									}
									catch (SQLException e1)
									{
										e1.printStackTrace();
									}
									this.closeDB();
									repaint();
									field.setText("");
									JOptionPane.showMessageDialog(null,"删除成功！");
									xinXiLiuLan();
									return;
								}
								else 
								{
									return;
								}
							}
							try
							{
								stmt = con.createStatement();
								String sql2 = "delete from toy_information where number='"+field.getText()+"';";
								stmt.executeUpdate(sql2);
							}
							catch (SQLException e1)
							{
								e1.printStackTrace();
							}
							this.closeDB();
							repaint();
							field.setText("");
							JOptionPane.showMessageDialog(null,"删除成功！");
							xinXiLiuLan();
							
						}
						else if(n == JOptionPane.NO_OPTION)
						{
							
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, "没有此编号的玩具信息！");
					}
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				
				
				
			}
			
		}
		else if(source == buttonOfLogout)//退出
		{
			this.dispose();
			new Login();
			
		}
		
	}

}
