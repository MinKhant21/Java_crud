import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;

public class logincrud {
    private JPanel Main;
    private JTextField txtname;
    private JPasswordField txtpassword;
    private JButton btncreate;
    private JTable table1;
    private JButton btnupdate;
    private JButton btndelete;
    private JButton btnsearch;
    private JTextField id;


    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setContentPane(new logincrud().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800,900);
        frame.setVisible(true);
    }


    Connection con ;
    PreparedStatement stmt;
    public void connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/crud","root","");
            System.out.println("Success");
        }catch (Exception e){
            System.out.println(e);
        }

    }

    void table(){
        try{
            stmt = con.prepareStatement("SELECT  * FROM login ");
            ResultSet rs = stmt.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
            System.out.println("Table Get Data");
        }catch (SQLException ee){
            System.out.println(ee);
        }

    }


    public logincrud() {
        connect();
        table();
        btncreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name , password;
                name = txtname.getText();
                password = String.valueOf(txtpassword.getPassword());

                try{
                    stmt = con.prepareStatement("INSERT INTO login (name,password) VALUES (?,?)");
                    stmt.setString(1,name);
                    stmt.setString(2,password);
                    stmt.executeUpdate();

                    JOptionPane.showMessageDialog(null,"Name And Password Created.");

                    txtname.setText("");
                    txtpassword.setText("");
                    txtname.requestFocus();

                }catch (SQLException se){
                    System.out.println(se);
                }

            }
        });

        btnsearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String userid ;
                userid = id.getText();

                try{
                    stmt = con.prepareStatement("SELECT name,password FROM login WHERE id = ? ");
                    stmt.setString(1,userid);
                    ResultSet rs = stmt.executeQuery();
                    if(rs.next()==true)
                    {
                        String name = rs.getString(1);
                        String password = rs.getString(2);


                        txtname.setText(name);
                        txtpassword.setText(password);

                    }
                    else
                    {
                        txtname.setText("");
                        txtpassword.setText("");

                        JOptionPane.showMessageDialog(null,"Invalid User No");

                    }

                }catch(SQLException sq){
                    System.out.println(sq);
                }

            }
        });
        table();
        btnupdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name, password;
                String userid ;
                userid = id.getText();
                name = txtname.getText();
                password = String.valueOf(txtpassword.getPassword());

                try {
                    stmt = con.prepareStatement("update  login set  name= ? , password = ? where id=?");
                    stmt.setString(1,name);
                    stmt.setString(2,password);
                    stmt.setString(3,userid);
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Updated");
                    txtname.setText("");
                    txtpassword.setText("");
                    txtname.requestFocus();
                }catch (SQLException ul){
                    System.out.println(ul);
                }

            }
        });
        table();
        btndelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userid ;
                userid = id.getText();
                try{
                    stmt = con.prepareStatement("DELETE FROM login WHERE id = ?");
                    stmt.setString(1,userid);
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Deleted From Table");
                }catch (SQLException ds){
                    System.out.println(ds);
                }
            }
        });
    }


}
