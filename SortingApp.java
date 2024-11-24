import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class SortingApp extends JFrame {
    private JTextField inputField;
    private JButton sortButton;
    private JComboBox<String> sortOptions;
    private JTextArea resultArea;

    public SortingApp() {
        // Set up frame
        setTitle("Sorting App");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for input and options
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        // Input field
        JLabel inputLabel = new JLabel("Masukkan angka (pisahkan dengan koma):");
        inputField = new JTextField(20);

        // Sorting options
        sortOptions = new JComboBox<>(new String[]{"Ascending", "Descending"});
        
        // Sort button
        sortButton = new JButton("Sort");
        sortButton.addActionListener(new SortButtonListener());

        // Add components to top panel
        topPanel.add(inputLabel);
        topPanel.add(inputField);
        topPanel.add(sortOptions);
        topPanel.add(sortButton);

        // Text area for result
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Add panels to frame
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Event listener for sort button
    private class SortButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = inputField.getText();
            if (input.isEmpty()) {
                resultArea.setText("Input tidak boleh kosong!");
                return;
            }

            try {
                // Parse input to array of integers
                String[] numberStrings = input.split(",");
                int[] numbers = Arrays.stream(numberStrings).mapToInt(Integer::parseInt).toArray();

                // Get selected sort order
                String sortOrder = (String) sortOptions.getSelectedItem();
                if (sortOrder.equals("Ascending")) {
                    Arrays.sort(numbers);
                } else {
                    Arrays.sort(numbers);
                    reverseArray(numbers);
                }

                // Display result
                resultArea.setText("Hasil Sorting: " + Arrays.toString(numbers));
            } catch (NumberFormatException ex) {
                resultArea.setText("Masukkan hanya angka yang valid, pisahkan dengan koma.");
            }
        }

        // Reverse array for descending order
        private void reverseArray(int[] array) {
            int start = 0, end = array.length - 1;
            while (start < end) {
                int temp = array[start];
                array[start] = array[end];
                array[end] = temp;
                start++;
                end--;
            }
        }
    }

    public static void main(String[] args) {
        // Create and display the GUI
        SwingUtilities.invokeLater(() -> {
            SortingApp app = new SortingApp();
            app.setVisible(true);
        });
    }
}
