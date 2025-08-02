import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ExpenseTrackerGUI extends JFrame {
    private DefaultListModel<String> expenseListModel;
    private JList<String> expenseList;
    private JTextField amountField, categoryField;

    public ExpenseTrackerGUI() {
        setTitle("Expense Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 250, 255)); // Light background
        setLocationRelativeTo(null); // Center on screen

        // 🔶 Heading at the top
        JLabel heading = new JLabel("💰 Expense Tracker 💰", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 22));
        heading.setForeground(new Color(33, 37, 41));
        heading.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(heading, BorderLayout.NORTH);

        // 🔹 Center: Expense List
        expenseListModel = new DefaultListModel<>();
        expenseList = new JList<>(expenseListModel);
        JScrollPane scrollPane = new JScrollPane(expenseList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Your Expenses"));
        add(scrollPane, BorderLayout.CENTER);

        // 🔸 South: Input Fields and Buttons
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.setBackground(new Color(250, 250, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Amount Label & Field
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Amount (Rs):"), gbc);
        gbc.gridx = 1;
        amountField = new JTextField(10);
        inputPanel.add(amountField, gbc);

        // Category Label & Field
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        categoryField = new JTextField(10);
        inputPanel.add(categoryField, gbc);

        // Add Button
        gbc.gridx = 0; gbc.gridy = 2;
        JButton addButton = new JButton("➕ Add Expense");
        addButton.setBackground(new Color(144, 238, 144));
        addButton.addActionListener(e -> addExpense());
        inputPanel.add(addButton, gbc);

        // Save Button
        gbc.gridx = 1;
        JButton saveButton = new JButton("💾 Save to File");
        saveButton.setBackground(new Color(255, 204, 153));
        saveButton.addActionListener(e -> saveToFile());
        inputPanel.add(saveButton, gbc);

        add(inputPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void addExpense() {
        String amount = amountField.getText().trim();
        String category = categoryField.getText().trim();

        if (amount.isEmpty() || category.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        try {
            Double.parseDouble(amount);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Amount must be a number.");
            return;
        }

        String entry = "Rs. " + amount + " - " + category;
        expenseListModel.addElement(entry);
        amountField.setText("");
        categoryField.setText("");
    }

    private void saveToFile() {
        String filePath = System.getProperty("user.home") + File.separator + "expenses.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < expenseListModel.size(); i++) {
                writer.write(expenseListModel.getElementAt(i));
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Expenses saved to:\n" + filePath);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ExpenseTrackerGUI::new);
    }
}
