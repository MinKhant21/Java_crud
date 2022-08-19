import javax.swing.*;
import java.awt.*;
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
    private JButton button1;
    private JButton btndelete;
    private JButton btnsearch;
    private JTextField txtsearch;


    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setContentPane(new logincrud().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,900);
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


    public logincrud() {
        connect();
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
    }
}
