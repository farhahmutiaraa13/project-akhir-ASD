import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Ticket {
    String airline;
    String departureTime;
    double price;

    public Ticket(String airline, String departureTime, double price) {
        this.airline = airline;
        this.departureTime = departureTime;
        this.price = price;
    }

    public String getAirline() {
        return airline;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public double getPrice() {
        return price;
    }
}

public class TicketSortingApp extends JFrame {
    private JTable ticketTable;
    private DefaultTableModel tableModel;
    private List<Ticket> tickets;

    public TicketSortingApp() {
        setTitle("Sistem Pemesanan Tiket");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Data awal tiket
        tickets = new ArrayList<>();
        tickets.add(new Ticket("Garuda Indonesia", "08:00", 1200000));
        tickets.add(new Ticket("Lion Air", "10:30", 850000));
        tickets.add(new Ticket("Air Asia", "06:15", 650000));
        tickets.add(new Ticket("Batik Air", "09:45", 900000));
        tickets.add(new Ticket("Citilink", "07:20", 700000));

        // Panel utama
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Tabel tiket
        String[] columnNames = {"Maskapai", "Waktu Keberangkatan", "Harga"};
        tableModel = new DefaultTableModel(columnNames, 0);
        ticketTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(ticketTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel bawah untuk tombol sorting
        JPanel buttonPanel = new JPanel();
        JButton sortByPriceAsc = new JButton("Harga: Murah ke Mahal");
        JButton sortByPriceDesc = new JButton("Harga: Mahal ke Murah");
        JButton sortByTimeAsc = new JButton("Waktu: Terawal");
        JButton sortByTimeDesc = new JButton("Waktu: Terakhir");
        JButton sortByAirlineAsc = new JButton("Maskapai: A-Z");
        JButton sortByAirlineDesc = new JButton("Maskapai: Z-A");

        buttonPanel.add(sortByPriceAsc);
        buttonPanel.add(sortByPriceDesc);
        buttonPanel.add(sortByTimeAsc);
        buttonPanel.add(sortByTimeDesc);
        buttonPanel.add(sortByAirlineAsc);
        buttonPanel.add(sortByAirlineDesc);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Listener tombol
        sortByPriceAsc.addActionListener(e -> {
            sortTicketsByPriceAscending();
            refreshTable();
        });

        sortByPriceDesc.addActionListener(e -> {
            sortTicketsByPriceDescending();
            refreshTable();
        });

        sortByTimeAsc.addActionListener(e -> {
            sortTicketsByTimeAscending();
            refreshTable();
        });

        sortByTimeDesc.addActionListener(e -> {
            sortTicketsByTimeDescending();
            refreshTable();
        });

        sortByAirlineAsc.addActionListener(e -> {
            sortTicketsByAirlineAscending();
            refreshTable();
        });

        sortByAirlineDesc.addActionListener(e -> {
            sortTicketsByAirlineDescending();
            refreshTable();
        });

        // Tambahkan panel ke frame
        add(mainPanel);
        refreshTable();
    }

    // Method untuk refresh tabel dengan data terkini
    private void refreshTable() {
        tableModel.setRowCount(0); // Hapus data lama
        for (Ticket ticket : tickets) {
            tableModel.addRow(new Object[]{
                ticket.getAirline(),
                ticket.getDepartureTime(),
                String.format("Rp %,d", (int) ticket.getPrice())
            });
        }
    }

    // Sorting harga (Murah ke Mahal)
    private void sortTicketsByPriceAscending() {
        tickets.sort(Comparator.comparingDouble(Ticket::getPrice));
    }

    // Sorting harga (Mahal ke Murah)
    private void sortTicketsByPriceDescending() {
        tickets.sort(Comparator.comparingDouble(Ticket::getPrice).reversed());
    }

    // Sorting waktu keberangkatan (Terawal)
    private void sortTicketsByTimeAscending() {
        tickets.sort(Comparator.comparing(Ticket::getDepartureTime));
    }

    // Sorting waktu keberangkatan (Terakhir)
    private void sortTicketsByTimeDescending() {
        tickets.sort(Comparator.comparing(Ticket::getDepartureTime).reversed());
    }

    // Sorting maskapai (A-Z)
    private void sortTicketsByAirlineAscending() {
        tickets.sort(Comparator.comparing(Ticket::getAirline));
    }

    // Sorting maskapai (Z-A)
    private void sortTicketsByAirlineDescending() {
        tickets.sort(Comparator.comparing(Ticket::getAirline).reversed());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicketSortingApp app = new TicketSortingApp();
            app.setVisible(true);
        });
    }
}
