package airlinemanagementsystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.*;
import java.util.Random;

@SuppressWarnings("serial")
public class CancelTicket extends JFrame implements ActionListener {
    JTextField tfpnr;
    JLabel tfname, cancellationno, lblfcode, lbltravel;
    JButton fetch, cancel;

    public CancelTicket() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        Random random = new Random();

        JLabel heading = new JLabel("Cancellation");
        heading.setBounds(180, 20, 250, 35);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 32));
        heading.setForeground(Color.DARK_GRAY.darker());
        add(heading);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/cancel.jpg"));
        Image i2 = i1.getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(470, 120, 250, 250);
        add(image);

        JLabel lblpnr = new JLabel("PNR Number");
        lblpnr.setBounds(60, 80, 150, 25);
        lblpnr.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblpnr.setForeground(Color.BLACK);
        add(lblpnr);

        tfpnr = new JTextField();
        tfpnr.setBounds(220, 80, 150, 25);
        add(tfpnr);

        fetch = new JButton("Show Details");
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

        JLabel lblcancellationNo = new JLabel("Cancellation No.");
        lblcancellationNo.setBounds(60, 180, 150, 25);
        lblcancellationNo.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblcancellationNo.setForeground(Color.BLACK);
        add(lblcancellationNo);

        cancellationno = new JLabel("" + random.nextInt(1000000));
        cancellationno.setBounds(220, 180, 150, 25);
        add(cancellationno);

        JLabel lblfcode = new JLabel("Flight Code");
        lblfcode.setBounds(60, 230, 150, 25);
        lblfcode.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblfcode.setForeground(Color.BLACK);
        add(lblfcode);

        this.lblfcode = new JLabel();
        this.lblfcode.setBounds(220, 230, 150, 25);
        add(this.lblfcode);

        JLabel lbldate = new JLabel("Date");
        lbldate.setBounds(60, 280, 150, 25);
        lbldate.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lbldate.setForeground(Color.BLACK);
        add(lbldate);

        lbltravel = new JLabel();
        lbltravel.setBounds(220, 280, 150, 25);
        add(lbltravel);

        cancel = new JButton("Cancel Ticket");
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.RED);
        cancel.setBounds(220, 330, 150, 25);
        cancel.addActionListener(this);
        add(cancel);

        setSize(800, 450);
        setLocation(350, 150);
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
                    lblfcode.setText(rs.getString("f_code"));
                    lbltravel.setText(rs.getString("jny_date"));
                } else {
                    JOptionPane.showMessageDialog(null, "Enter a valid PNR Number!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == cancel) {
            String pnr = tfpnr.getText();
            String flightCode = lblfcode.getText();

            if (pnr.isEmpty() || flightCode.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No details found for cancellation!");
                return;
            }

            try (Conn conn = new Conn()) {
                String query = "DELETE FROM reservation WHERE pnr_no = ?";
                PreparedStatement pstmt = conn.conn.prepareStatement(query);
                pstmt.setString(1, pnr);
                int rows = pstmt.executeUpdate();

                if (rows > 0) {
                    JOptionPane.showMessageDialog(null, "Ticket Canceled Successfully!");
                    // Clear fields after successful cancellation
                    tfpnr.setText("");
                    tfname.setText("");
                    lblfcode.setText("");
                    lbltravel.setText("");
                    cancellationno.setText("" + new Random().nextInt(1000000));
                } else {
                    JOptionPane.showMessageDialog(null, "Cancellation Failed!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new CancelTicket();
    }
}
