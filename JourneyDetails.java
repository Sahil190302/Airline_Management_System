package airlinemanagementsystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

@SuppressWarnings("serial")
public class JourneyDetails extends JFrame implements ActionListener {
    JTable table;
    JTextField pnr;
    JButton show;

    public JourneyDetails() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel lblpnr = new JLabel("PNR Details");
        lblpnr.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblpnr.setBounds(50, 50, 100, 25);
        add(lblpnr);

        pnr = new JTextField();
        pnr.setBounds(160, 50, 120, 25);
        add(pnr);

        show = new JButton("Show Details");
        show.setBackground(Color.BLACK);
        show.setForeground(Color.GREEN);
        show.addActionListener(this);
        show.setBounds(290, 50, 120, 25);
        add(show);

        table = new JTable();
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 100, 800, 150);
        jsp.setBackground(Color.WHITE);
        add(jsp);

        setSize(800, 500);
        setLocation(400, 150);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String pnrNo = pnr.getText().trim();

        // Check if PNR field is empty
        if (pnrNo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a valid PNR number");
            return;
        }

        try (Conn c = new Conn(); 
             PreparedStatement pstmt = c.conn.prepareStatement("SELECT * FROM reservation WHERE pnr_no = ?")) {
            
            pstmt.setString(1, pnrNo); // Use parameterized query
            try (ResultSet rs = pstmt.executeQuery()) {
                if (!rs.isBeforeFirst()) {
                    JOptionPane.showMessageDialog(null, "No Information Found");
                    return;
                }

                // Set the ResultSet to the table model
                table.setModel(DbUtils.resultSetToTableModel(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new JourneyDetails().setVisible(true);
        });
    }
}
