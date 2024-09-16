package airlinemanagementsystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.*;

public class BoardingPass extends JFrame implements ActionListener {
    JTextField tfpnr;
    JLabel tfname, tfnationality, lblsrc, lbldest, labelfname, cbFlightCodes, labeldate;
    JButton fetch;

    public BoardingPass() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel heading = new JLabel("AIR INDIA");
        heading.setBounds(380, 10, 450, 35);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 32));
        add(heading);

        JLabel subheading = new JLabel("Boarding Pass");
        subheading.setBounds(360, 50, 300, 37);
        subheading.setFont(new Font("Tahoma", Font.PLAIN, 32));
        subheading.setForeground(Color.BLUE);
        add(subheading);

        JLabel lblaadhar = new JLabel("PNR DETAILS");
        lblaadhar.setBounds(60, 100, 150, 25);
        lblaadhar.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblaadhar.setForeground(Color.BLACK);
        add(lblaadhar);

        tfpnr = new JTextField();
        tfpnr.setBounds(220, 100, 150, 25);
        add(tfpnr);

        fetch = new JButton("Enter");
        fetch.setBackground(Color.BLACK);
        fetch.setForeground(Color.WHITE);
        fetch.addActionListener(this);
        fetch.setBounds(380, 100, 120, 25);
        add(fetch);

        JLabel lblname = new JLabel("NAME");
        lblname.setBounds(60, 140, 150, 25);
        lblname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblname.setForeground(Color.BLACK);
        add(lblname);

        tfname = new JLabel();
        tfname.setBounds(220, 140, 150, 25);
        add(tfname);

        JLabel lblnationality = new JLabel("NATIONALITY");
        lblnationality.setBounds(60, 180, 150, 25);
        lblnationality.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblnationality.setForeground(Color.BLACK);
        add(lblnationality);

        tfnationality = new JLabel();
        tfnationality.setBounds(220, 180, 150, 25);
        add(tfnationality);

        JLabel lblsrcLabel = new JLabel("SRC");
        lblsrcLabel.setBounds(60, 220, 150, 25);
        lblsrcLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblsrcLabel.setForeground(Color.BLACK);
        add(lblsrcLabel);

        lblsrc = new JLabel();
        lblsrc.setBounds(220, 220, 150, 25);
        add(lblsrc);

        JLabel lblgender = new JLabel("DEST");
        lblgender.setBounds(380, 220, 150, 25);
        lblgender.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblgender.setForeground(Color.BLACK);
        add(lblgender);

        lbldest = new JLabel();
        lbldest.setBounds(540, 220, 150, 25);
        add(lbldest);

        JLabel lblFlight = new JLabel("FLIGHT CODE");
        lblFlight.setBounds(60, 260, 150, 25);
        lblFlight.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblFlight.setForeground(Color.BLACK);
        add(lblFlight);

        cbFlightCodes = new JLabel();
        cbFlightCodes.setBounds(220, 260, 150, 25);
        add(cbFlightCodes);

        JLabel lblfname = new JLabel("FLIGHT NAME");
        lblfname.setBounds(60, 300, 150, 25);
        lblfname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblfname);

        labelfname = new JLabel();
        labelfname.setBounds(220, 300, 150, 25);
        add(labelfname);

        JLabel lbldate = new JLabel("DATE OF JOURNEY");
        lbldate.setBounds(60, 340, 150, 25);
        lbldate.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lbldate);

        labeldate = new JLabel();
        labeldate.setBounds(220, 340, 150, 25);
        add(labeldate);

        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/airindia.png"));
        Image i2 = ii.getImage().getScaledInstance(300, 230, Image.SCALE_DEFAULT);
        ImageIcon image = new ImageIcon(i2);
        JLabel lblimage = new JLabel(image);
        lblimage.setBounds(600, 0, 300, 300);
        add(lblimage);

        setSize(1000, 450);
        setLocation(300, 150);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == fetch) {
            String pnr = tfpnr.getText();

            try (Conn conn = new Conn()) {
                String query = "SELECT * FROM reservation WHERE pnr_no = ?";
                PreparedStatement pstmt = conn.conn.prepareStatement(query);
                pstmt.setString(1, pnr);
                ResultSet rs = pstmt.executeQuery();
                
                if (rs.next()) {
                    tfname.setText(rs.getString("name"));
                    tfnationality.setText(rs.getString("nationality"));
                    lblsrc.setText(rs.getString("src"));
                    lbldest.setText(rs.getString("dst"));
                    cbFlightCodes.setText(rs.getString("f_code"));
                    labelfname.setText(rs.getString("flight_name"));
                    labeldate.setText(rs.getString("jny_date"));
                } else {
                    JOptionPane.showMessageDialog(null, "Enter a valid PNR Number!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new BoardingPass();
    }
}
