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
	 * ����Ա�˵Ľ���
	 */
	JTable table;
	JLabel label1,label2,label3,label4;
	Object a[][];	//�����Ϣ
	Object name[] = {"���","�������","�����","�۸�(Ԫ/��)","��ɫ","�Ƿ�����","���õ��û�"};
	JButton buttonOfXinxiluru,buttonOfXinxiliulan,buttonOfDelete,buttonOfLogout,buttonOfXiangXi,buttonOfXiugai;
	Box box1,box2;
	JTextField field,field2,field3;
	JPanel jPanel4,jPanel5;
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	public Guanliyuan(Boolean success)	//����Ա���棬true����չʾ���
	{
		init();
		setVisible(true);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 200, 625, 490);
		setTitle("����Ա����");
		if(success)//successs��һ��boolean���ͣ����Ϊtrue���򿪴˴���ֱ����Ϣ�����false������û����Ϣ����Ҫ�����Ϣ�����
		{
			xinXiLiuLan();
		}
	}
	
	public void init()	//����ԱϵͳGUI
	{
		label1 = new JLabel("���������Ϣ����ϵͳ");
		buttonOfXinxiluru = new JButton("  �����Ϣ¼��  ");
		buttonOfXinxiluru.addActionListener(this);
		buttonOfXinxiliulan = new JButton("  �����Ϣ���  ");
		buttonOfXinxiliulan.addActionListener(this);
		buttonOfDelete = new JButton("    ɾ	            ��      ");
		buttonOfDelete.addActionListener(this);
		buttonOfLogout = new JButton("  ��   ��   ��   ¼  ");
		buttonOfLogout.addActionListener(this);
		buttonOfXiugai = new JButton("    ��	           ��      ");
		buttonOfXiugai.addActionListener(this);
		buttonOfXiangXi = new JButton("  ��   ϸ   ��   Ϣ  ");
		buttonOfXiangXi.addActionListener(this);
		label2 = new JLabel("��ɾ����Ϣ��ţ�");
		label3 = new JLabel("���޸���Ϣ�ı�ţ�");
		label4 = new JLabel("����ѯ����ı�ţ�");
		field = new JTextField();
		field2 = new JTextField();
		field3 = new JTextField();
		
		a = new Object[50][7];	//������ʾ�ı��
		table = new JTable(a, name);//����Ĵ���
		table.setEnabled(false);
		JScrollPane scrollPane = new JScrollPane(table);	//�������ø��ڴ�С����Ӧ
		
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
		box2.add(box1);   //��ߵİ�ť������ box����
		
		jPanel4 = new JPanel();
		jPanel5 = new JPanel();
		jPanel4.setLayout(new BorderLayout());
		jPanel4.add(box2,BorderLayout.NORTH);//����ߵİ�ť���ַŵ�jpanel4�С�


		jPanel5.setLayout(new BorderLayout());
		jPanel5.add(label1,BorderLayout.NORTH);
		jPanel5.add(scrollPane,BorderLayout.CENTER);//�ѱ���jpanel5��
	
		this.setLayout(new BorderLayout());
		add(jPanel5,BorderLayout.EAST);
		add(jPanel4,BorderLayout.WEST);//���������panel�ŵ���������

		
		
	}

	public void connDB() { // �������ݿ�
		try {
			Class.forName("com.mysql.jdbc.Driver");//ע������
			//����Class���󣬲�û�л�ȡ����ʵ��
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {//��������
			con = DriverManager.getConnection(DbUtil.dbUrlString, DbUtil.dbUser, DbUtil.dbpassword);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void closeDB() // �ر�����
	{
		try {
			stmt.close();
			con.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void 																																																xinXiLiuLan()//��Ϣ����ķ�������Ϊɾ�����ݺ��ˢ��һ�£��Զ����ô˺�����
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
		 }	//��ȫ����Ϊ��
		 i=0;
		 this.connDB();
		 try {	//����sql���
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
				 
			 }	//���������Ϣ���룬select��������������
			 this.closeDB();
			 repaint();	//�ػ棬��Ȼ�޷�չʾ
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 this.closeDB();
	}
	@Override
	public void actionPerformed(ActionEvent e) {	//��ť����
		Object source = e.getSource();
		if(source == buttonOfXinxiluru)//�����Ϣ�޸İ�ť
		{
			this.dispose();
			new Luru();
		}
		else if(source == buttonOfXinxiliulan)//�����Ϣ�����ť
		{
			xinXiLiuLan();
			
		}
		else if(source == buttonOfXiugai)//����޸İ�ť
		{
			
			if(field2.getText().equals(""))
			{
				 JOptionPane.showMessageDialog(null, "�����޸���ߵı�ţ�");
			}
			else
			{
				this.dispose();
				new Xiugai(field2.getText());
			}
		}
		else if(source == buttonOfXiangXi)//�����ϸ��Ϣ��ť
		{
			this.dispose();
			new Xiangxi(field3.getText());
		}
		else if(source == buttonOfDelete)//���ɾ����ť
		{
			if(field.getText().equals(""))
			{
				JOptionPane.showMessageDialog(null, "������ɾ����ߵı�ţ�");
			}
			else
			{
				this.connDB();
				String sql;
				try {
					stmt = con.createStatement();
					sql = "select * from toy_information  where number='"+field.getText()+"'";//�����ҵ���Ҫɾ���������Ϣ
					rs = stmt.executeQuery(sql);
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				try {
					if(rs.next())//�ж��Ƿ��� �����ŵ����
					{
						
						int n = JOptionPane.showConfirmDialog(this, "ȷ��ɾ���������Ϣ��","ȷ�϶Ի���",JOptionPane.YES_NO_OPTION);//ȷ���ı���
						if(n == JOptionPane.YES_OPTION)
						{	
							String hire2 = rs.getString("hire");
							if(hire2.equals("��"))
							{
								int m = JOptionPane.showConfirmDialog(this, "��������ڱ����ã��Ƿ�ɾ����","ȷ�϶Ի���",JOptionPane.YES_NO_OPTION);//ȷ���ı���
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
									JOptionPane.showMessageDialog(null,"ɾ���ɹ���");
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
							JOptionPane.showMessageDialog(null,"ɾ���ɹ���");
							xinXiLiuLan();
							
						}
						else if(n == JOptionPane.NO_OPTION)
						{
							
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, "û�д˱�ŵ������Ϣ��");
					}
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				
				
				
			}
			
		}
		else if(source == buttonOfLogout)//�˳�
		{
			this.dispose();
			new Login();
			
		}
		
	}

}
