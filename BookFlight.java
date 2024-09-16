package airlinemanagementsystem;

import java.awt.Choice;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.*;
import java.util.Random;
import java.text.SimpleDateFormat;

public class BookFlight extends JFrame implements ActionListener {
    JTextField tfaadhar;
    JLabel tfname, tfnationality, tfadd, labelgender, labelfname, cbFlightCodes;
    JButton bookFlight, fetch, Flight;
    Choice source, dest;
    JDateChooser dc;

    public BookFlight() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel heading = new JLabel("Book Flight");
        heading.setBounds(420, 20, 500, 35);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 32));
        heading.setForeground(Color.BLUE);
        add(heading);
        
        JLabel lblaadhar = new JLabel("Aadhar Number");
        lblaadhar.setBounds(60, 80, 150, 25);
        lblaadhar.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblaadhar.setForeground(Color.BLACK);
        add(lblaadhar);

        tfaadhar = new JTextField();
        tfaadhar.setBounds(220, 80, 150, 25);
        add(tfaadhar);
        
        fetch = new JButton("Fetch User");
        fetch.setBackground(Color.BLACK);
        fetch.setForeground(Color.WHITE);
        fetch.addActionListener(this);
        fetch.setBounds(380, 80, 120, 25);
        add(fetch);

        JLabel lblname = new JLabel("Name");
        lblname.setBounds(60, 130, 150, 25);
        lblname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblname.setForeground(Color.BLACK);
        add(lblname);

        tfname = new JLabel();
        tfname.setBounds(220, 130, 150, 25);
        add(tfname);

        JLabel lblnationality = new JLabel("Nationality");
        lblnationality.setBounds(60, 180, 150, 25);
        lblnationality.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblnationality.setForeground(Color.BLACK);
        add(lblnationality);

        tfnationality = new JLabel();
        tfnationality.setBounds(220, 180, 150, 25);
        add(tfnationality);

        JLabel lbladd = new JLabel("Address");
        lbladd.setBounds(60, 230, 150, 25);
        lbladd.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lbladd.setForeground(Color.BLACK);
        add(lbladd);

        tfadd = new JLabel();
        tfadd.setBounds(220, 230, 150, 25);
        add(tfadd);

        JLabel lblgender = new JLabel("Gender");
        lblgender.setBounds(60, 280, 150, 25);
        lblgender.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblgender.setForeground(Color.BLACK);
        add(lblgender);
        
        labelgender = new JLabel("");
        labelgender.setBounds(220, 280, 150, 25);
        labelgender.setFont(new Font("Tahoma", Font.PLAIN, 16));
        labelgender.setForeground(Color.BLACK);
        add(labelgender);

        JLabel lblsource = new JLabel("Source");
        lblsource.setBounds(60, 330, 150, 25);
        lblsource.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblsource);

        source = new Choice();
        source.setBounds(220, 330, 150, 25);
        add(source);
        
        JLabel lbldest = new JLabel("Destination");
        lbldest.setBounds(60, 380, 150, 25);
        lbldest.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lbldest);
        
        dest = new Choice();
        dest.setBounds(220, 380, 150, 25);
        add(dest);

        try {
            Conn c = new Conn();
            String query = "SELECT * FROM flight";
            PreparedStatement pstmt = c.conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                source.add(rs.getString("src"));
                dest.add(rs.getString("dst"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel lblFlight = new JLabel("Flight Code");
        lblFlight.setBounds(60, 430, 150, 25);
        lblFlight.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblFlight.setForeground(Color.BLACK);
        add(lblFlight);

        cbFlightCodes = new JLabel("");
        cbFlightCodes.setBounds(220, 430, 150, 25);
        add(cbFlightCodes);

        Flight = new JButton("Fetch Flights");
        Flight.setBackground(Color.BLACK);
        Flight.setForeground(Color.WHITE);
        Flight.setBounds(380, 380, 150, 25);
        Flight.addActionListener(this);
        add(Flight);

        JLabel lblfname = new JLabel("Flight Name");
        lblfname.setBounds(60, 480, 150, 25);
        lblfname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblfname);
        
        labelfname = new JLabel();
        labelfname.setBounds(220, 480, 150, 25);
        add(labelfname);
        
        JLabel lbldate = new JLabel("Date of Travel");
        lbldate.setBounds(60, 530, 150, 25);
        lbldate.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lbldate);
        
        dc = new JDateChooser();
        dc.setBounds(220, 530, 150, 25);
        add(dc);

        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/details.jpg"));
        Image i2 = ii.getImage().getScaledInstance(450, 320, Image.SCALE_DEFAULT);
        ImageIcon image = new ImageIcon(i2);
        JLabel lblimage = new JLabel(image);
        lblimage.setBounds(550, 80, 500, 410);
        add(lblimage);

        bookFlight = new JButton("Book Flight");
        bookFlight.setBackground(Color.BLACK);
        bookFlight.setForeground(Color.WHITE);
        bookFlight.setBounds(220, 580, 150, 25);
        bookFlight.addActionListener(this);
        add(bookFlight);

        setSize(1100, 700);
        setLocation(200, 50);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == fetch) {
            String aadhar = tfaadhar.getText();

            try (Conn conn = new Conn()) {
                String query = "SELECT * FROM passenger WHERE pnr_no = ?";
                PreparedStatement pstmt = conn.conn.prepareStatement(query);
                pstmt.setString(1, aadhar);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    tfname.setText(rs.getString("name"));
                    tfnationality.setText(rs.getString("nationality"));
                    tfadd.setText(rs.getString("address"));
                    labelgender.setText(rs.getString("gender"));
                } else {
                    JOptionPane.showMessageDialog(null, "Enter a valid Aadhaar Number!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == Flight) {
            String src = source.getSelectedItem();
            String des = dest.getSelectedItem();

            try (Conn conn = new Conn()) {
                String query = "SELECT * FROM flight WHERE src = ? AND dst = ?";
                PreparedStatement pstmt = conn.conn.prepareStatement(query);
                pstmt.setString(1, src);
                pstmt.setString(2, des);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    labelfname.setText(rs.getString("f_name"));
                    cbFlightCodes.setText(rs.getString("f_code"));
                } else {
                    JOptionPane.showMessageDialog(null, "No Flights Found!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == bookFlight) {
            String src = source.getSelectedItem();
            String des = dest.getSelectedItem();
            String fname = tfname.getText();
            String nationality = tfnationality.getText();
            String address = tfadd.getText();
            String flightCode = cbFlightCodes.getText();
            String flightName = labelfname.getText();
            String aadhar = tfaadhar.getText();
            Random random = new Random();
            long pnrNo = Math.abs(random.nextLong() % 90000000L + 10000000L);
            long ticketId = Math.abs(random.nextLong() % 90000000L + 10000000L);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(dc.getDate());

            try (Conn conn = new Conn()) {
                String query = "INSERT INTO reservation(pnr_no, ticket_id, f_code, aadhar_no, name, nationality, flight_name, src, dst, jny_date) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt = conn.conn.prepareStatement(query);
                pstmt.setString(1, String.valueOf(pnrNo));
                pstmt.setString(2, String.valueOf(ticketId));
                pstmt.setString(3, flightCode);
                pstmt.setString(4, aadhar);
                pstmt.setString(5, fname);
                pstmt.setString(6, nationality);
                pstmt.setString(7, flightName);
                pstmt.setString(8, src);
                pstmt.setString(9, des);
                pstmt.setString(10, date);
                pstmt.executeUpdate();
                
                // Display the PNR number along with the success message
                JOptionPane.showMessageDialog(null, "Flight Booked Successfully! Your PNR is: " + pnrNo);
                setVisible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        new BookFlight();
    }
}
