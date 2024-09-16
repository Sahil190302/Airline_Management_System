package airlinemanagementsystem;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;

public class Login extends JFrame implements ActionListener {
    private JButton submit, reset, close;
    private JTextField tfusername;
    private JPasswordField tfpassword;
    
    public Login() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        JLabel lblusername = new JLabel("Username");
        lblusername.setBounds(20, 20, 100, 20);
        add(lblusername);
        
        tfusername = new JTextField();
        tfusername.setBounds(130, 20, 200, 20);
        add(tfusername);
        
        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(20, 60, 100, 20);
        add(lblpassword);
        
        tfpassword = new JPasswordField();
        tfpassword.setBounds(130, 60, 200, 20);
        add(tfpassword);
        
        reset = new JButton("Reset");
        reset.setBounds(40, 120, 120, 20);
        reset.addActionListener(this);
        add(reset);
        
        submit = new JButton("Submit");
        submit.setBounds(190, 120, 120, 20);
        submit.addActionListener(this);
        add(submit);
        
        close = new JButton("Close");
        close.setBounds(120, 160, 120, 20);
        close.addActionListener(this);
        add(close);
        
        setSize(400, 250);
        setLocation(600, 250);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE); 
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            String username = tfusername.getText();
            String password = tfpassword.getText() ;
            
            try {
                Conn c = new Conn();
                
               
                String query = "SELECT * FROM login WHERE username = ? AND password = ?";
                PreparedStatement pstmt = c.conn.prepareStatement(query);
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                
                ResultSet rs = pstmt.executeQuery();
                
                if (rs.next()) {
                    new Home();
                    setVisible(false);
                
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password");
                }
                

                rs.close();
                pstmt.close();
                c.conn.close();
                
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Database error. Please try again later.");
            }
        } else if (ae.getSource() == reset) {
            tfusername.setText("");
            tfpassword.setText("");
        } else if (ae.getSource() == close) {
            setVisible(false);
        }
    }
    
    public static void main(String[] args) {
        new Login();
    }
}
