import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

class Konten {
    String deadline;
    String mataKuliah;
    String isi;

    public Konten(String tanggal, String judul, String isi) {
        this.deadline = tanggal;
        this.mataKuliah = judul;
        this.isi = isi;
    }
}

public class AplikasiListTugas extends JFrame {
    private ArrayList<Konten> daftarTugas = new ArrayList<>();
    private DefaultTableModel tableModel;

    public AplikasiListTugas() {
        // Setup frame
        setTitle("List Tugas");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tabel untuk menampilkan konten
        tableModel = new DefaultTableModel(new String[] { "Deadline", "Mata Kuliah", "Detail Tugas" }, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel bawah untuk tombol
        JPanel bottomPanel = new JPanel();
        JButton addButton = new JButton("Tambah Tugas");
        JButton bubbleSortDateButton = new JButton("Urutkan Deadline");
        JButton selectionSortTitleButton = new JButton("Urutkan Mata Kuliah");
        JButton deleteButton = new JButton("Hapus Tugas"); 

        bottomPanel.add(addButton);
        bottomPanel.add(bubbleSortDateButton);
        bottomPanel.add(selectionSortTitleButton);
        bottomPanel.add(deleteButton); 
        add(bottomPanel, BorderLayout.SOUTH);

        // Action Listener untuk tombol
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tambahTugas();
            }
        });

        bubbleSortDateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bubbleSortDeadline();
            }
        });

        selectionSortTitleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectionSortMK();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hapusTugas(table);
            }
        });

        setVisible(true);
    }

    

    private void tambahTugas() {
        // Dialog untuk menambahkan konten
        JTextField tanggalField = new JTextField();
        JTextField mkField = new JTextField();
        JTextField isiField = new JTextField();

        Object[] message = {
                "Deadline (yyyy-mm-dd):", tanggalField,
                "Mata Kuliah:", mkField,
                "Detail Tugas:", isiField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Tambah Tugas", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String deadline = tanggalField.getText();
            String mataKuliah = mkField.getText();
            String isi = isiField.getText();

            if (!deadline.isEmpty() && !mataKuliah.isEmpty() && !isi.isEmpty()) {
                daftarTugas.add(new Konten(deadline, mataKuliah, isi));
                updateTable();
            } else {
                JOptionPane.showMessageDialog(null, "Semua bidang harus diisi!");
            }
        }
    }

    private void updateTable() {
        tableModel.setRowCount(0); // Hapus semua baris
        for (Konten konten : daftarTugas) {
            tableModel.addRow(new Object[] { konten.deadline, konten.mataKuliah, konten.isi });
        }
    }

    // Bubble Sort - Deadline
    private void bubbleSortDeadline() {
        int n = daftarTugas.size(); // Panjang daftar tugas
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                // Membandingkan elemen saat ini dengan elemen berikut nya
                if (daftarTugas.get(j).deadline.compareTo(daftarTugas.get(j + 1).deadline) > 0) {
                    Collections.swap(daftarTugas, j, j + 1); // Tukar posisi jika belum berurutan
                }
            }
        }
        updateTable(); // Perbarui tabel
        JOptionPane.showMessageDialog(null, "Daftar tugas berhasil diurutkan berdasarkan deadline terdekat.");
    }

    // Selection Sort - Mata Kuliah
    private void selectionSortMK() {
        int n = daftarTugas.size(); // Panjang daftar tugas
        for (int j = 0; j < n - 1; j++) { 
            int iMin = j; // Indeks elemen terkecil
            for (int i = j + 1; i < n; i++) { 
                // Membandingkan elemen saat ini dengan elemen terkecil
                if (daftarTugas.get(i).mataKuliah.compareTo(daftarTugas.get(iMin).mataKuliah) < 0) {
                    iMin = i;
                }
            }
            // Menukar elemen jika elemen terkecil tidak berada di posisi awal
            if (iMin != j) {
                Konten temp = daftarTugas.get(j); // Simpan elemen di posisi j
                daftarTugas.set(j, daftarTugas.get(iMin)); // Pindahkan elemen terkecil ke posisi j
                daftarTugas.set(iMin, temp); // Pindahkan elemen awal ke posisi elemen terkecil
            }
        }
        updateTable(); // Perbarui tabel
        JOptionPane.showMessageDialog(null, "Daftar tugas berhasil diurutkan berdasarkan Abjad Mata Kuliah.");
    }
    

    private void hapusTugas(JTable table) {
        int selectedRow = table.getSelectedRow(); // Mengambil baris yang dipilih
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(null, 
                "Apakah Anda yakin ingin menghapus tugas ini?", 
                "Konfirmasi Hapus", 
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                daftarTugas.remove(selectedRow); // Hapus dari daftarTugas
                updateTable(); // Perbarui tabel
                JOptionPane.showMessageDialog(null, "Tugas berhasil dihapus!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Pilih tugas yang ingin dihapus!");
        }
    }    

    public static void main(String[] args) {
        new AplikasiListTugas();
    }
}
