package airlinemanagementsystem;

import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

@SuppressWarnings("serial")
public class FlightInfo extends JFrame {

    private JTable jt;

    public FlightInfo() {
        // Set the frame properties
        setTitle("Flight Information");
        getContentPane().setBackground(Color.BLUE);
        setLayout(null);
        setSize(800, 500);
        setLocation(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose of frame when closed

        // Initialize the JTable and JScrollPane
        jt = new JTable();
        JScrollPane jsp = new JScrollPane(jt);
        jsp.setBounds(0, 0, 800, 500);
        add(jsp);

        // Fetch and display data from the database
        fetchData();
    }

    private void fetchData() {
        String query = "SELECT * FROM flight"; // Define your query here
        try (Conn c = new Conn(); // Use Conn class for the connection
             PreparedStatement pstmt = c.conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            // Set the table model with data from the ResultSet
            jt.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching flight data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Run the application
        SwingUtilities.invokeLater(() -> {
            new FlightInfo().setVisible(true);
        });
    }
}
