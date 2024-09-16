package airlinemanagementsystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.*;

public class AddCustomer extends JFrame implements ActionListener {
    JTextField tfname, tfnationality, tfaadhar, tfadd, tfphone;
    JRadioButton rbmale, rbfemale;
    JComboBox<String> cbFlightCodes; // For selecting a flight code

    public AddCustomer() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel heading = new JLabel("ADD CUSTOMER DETAILS");
        heading.setBounds(220, 20, 500, 35);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 32));
        heading.setForeground(Color.BLUE);
        add(heading);

        JLabel lblname = new JLabel("Name");
        lblname.setBounds(60, 80, 150, 25);
        lblname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblname.setForeground(Color.BLACK);
        add(lblname);

        tfname = new JTextField();
        tfname.setBounds(220, 80, 150, 25);
        add(tfname);

        JLabel lblnationality = new JLabel("Nationality");
        lblnationality.setBounds(60, 130, 150, 25);
        lblnationality.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblnationality.setForeground(Color.BLACK);
        add(lblnationality);

        tfnationality = new JTextField();
        tfnationality.setBounds(220, 130, 150, 25);
        add(tfnationality);

        JLabel lblaadhar = new JLabel("Aadhar Number.");
        lblaadhar.setBounds(60, 180, 150, 25);
        lblaadhar.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblaadhar.setForeground(Color.BLACK);
        add(lblaadhar);

        tfaadhar = new JTextField();
        tfaadhar.setBounds(220, 180, 150, 25);
        add(tfaadhar);

        JLabel lbladd = new JLabel("Address");
        lbladd.setBounds(60, 230, 150, 25);
        lbladd.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lbladd.setForeground(Color.BLACK);
        add(lbladd);

        tfadd = new JTextField();
        tfadd.setBounds(220, 230, 150, 25);
        add(tfadd);

        JLabel lblgender = new JLabel("Gender");
        lblgender.setBounds(60, 280, 150, 25);
        lblgender.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblgender.setForeground(Color.BLACK);
        add(lblgender);

        ButtonGroup bg = new ButtonGroup();

        rbmale = new JRadioButton("Male");
        rbmale.setBounds(220, 280, 70, 25);
        rbmale.setBackground(Color.WHITE);
        add(rbmale);
        bg.add(rbmale);

        rbfemale = new JRadioButton("Female");
        rbfemale.setBounds(300, 280, 70, 25);
        rbfemale.setBackground(Color.WHITE);
        add(rbfemale);
        bg.add(rbfemale);

        JLabel lblphone = new JLabel("Phone");
        lblphone.setBounds(60, 330, 150, 25);
        lblphone.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblphone.setForeground(Color.BLACK);
        add(lblphone);

        tfphone = new JTextField();
        tfphone.setBounds(220, 330, 150, 25);
        add(tfphone);

        JLabel lblFlight = new JLabel("Flight Code");
        lblFlight.setBounds(60, 380, 150, 25);
        lblFlight.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblFlight.setForeground(Color.BLACK);
        add(lblFlight);

        cbFlightCodes = new JComboBox<>();
        cbFlightCodes.setBounds(220, 380, 150, 25);
        add(cbFlightCodes);

        // Populate flight codes from database
        try (Conn conn = new Conn();
             Statement stmt = conn.conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT f_code FROM flight")) {
            while (rs.next()) {
                cbFlightCodes.addItem(rs.getString("f_code"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JButton jb = new JButton("SAVE");
        jb.setBackground(Color.BLACK);
        jb.setForeground(Color.WHITE);
        jb.setBounds(220, 430, 150, 30);
        jb.addActionListener(this);
        add(jb);

        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/emp.png"));
        JLabel lblimage = new JLabel(ii);
        lblimage.setBounds(450, 80, 280, 400);
        add(lblimage);

        setSize(900, 600);
        setLocation(300, 150);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String name = tfname.getText();
        String nationality = tfnationality.getText();
        String phone = tfphone.getText();
        String address = tfadd.getText();
        String aadhar = tfaadhar.getText();
        String gender = rbmale.isSelected() ? "Male" : "Female";
        String flightCode = (String) cbFlightCodes.getSelectedItem();

        try (Conn conn = new Conn()) {
            String query = "INSERT INTO passenger (pnr_no, address, nationality, name, gender, ph_no, passport_no, fl_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.conn.prepareStatement(query)) {
                pstmt.setString(1, aadhar); // Assuming aadhar is used as pnr_no for this example
                pstmt.setString(2, address);
                pstmt.setString(3, nationality);
                pstmt.setString(4, name);
                pstmt.setString(5, gender);
                pstmt.setString(6, phone);
                pstmt.setString(7, aadhar); // Ensure passport_no is set correctly
                pstmt.setString(8, flightCode); // Use selected flight code

                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Customer Details Added Successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error while adding customer details.");
        }
    }

    public static void main(String[] args) {
        new AddCustomer();
    }
}
