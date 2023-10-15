import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class PizzaGUIFrame extends JFrame {
    private JRadioButton thinCrustRadioButton, regularCrustRadioButton, deepDishCrustRadioButton;
    private JComboBox<String> sizeComboBox;
    private JCheckBox[] toppingsCheckBoxes;
    private JTextArea orderTextArea;
    private JButton orderButton, clearButton, quitButton;

    private double baseCost = 0.0;
    private double toppingsCost = 0.0;
    private double subTotal = 0.0;
    private double tax = 0.0;
    private double total = 0.0;

    private String[] toppingNames = {"Onion", "Pepperoni", "Sausage", "Green Peppers", "Ham", "Pineapple"};

    public PizzaGUIFrame() {
        setTitle("Pizza Order");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JPanel crustPanel = new JPanel();
        crustPanel.setBorder(BorderFactory.createTitledBorder("Crust"));
        thinCrustRadioButton = new JRadioButton("Thin");
        regularCrustRadioButton = new JRadioButton("Regular");
        deepDishCrustRadioButton = new JRadioButton("Deep-dish");
        ButtonGroup crustButtonGroup = new ButtonGroup();
        crustButtonGroup.add(thinCrustRadioButton);
        crustButtonGroup.add(regularCrustRadioButton);
        crustButtonGroup.add(deepDishCrustRadioButton);
        crustPanel.add(thinCrustRadioButton);
        crustPanel.add(regularCrustRadioButton);
        crustPanel.add(deepDishCrustRadioButton);
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(crustPanel, gbc);

        JPanel sizePanel = new JPanel();
        sizePanel.setBorder(BorderFactory.createTitledBorder("Size"));
        sizeComboBox = new JComboBox<>(new String[]{"Small", "Medium", "Large", "Super"});
        sizePanel.add(new JLabel("Select Size:"));
        sizePanel.add(sizeComboBox);
        gbc.gridx = 1;
        centerPanel.add(sizePanel, gbc);

        JPanel toppingsPanel = new JPanel();
        toppingsPanel.setBorder(BorderFactory.createTitledBorder("Toppings"));
        toppingsCheckBoxes = new JCheckBox[toppingNames.length];
        for (int i = 0; i < toppingNames.length; i++) {
            toppingsCheckBoxes[i] = new JCheckBox(toppingNames[i]);
        }
        toppingsPanel.setLayout(new GridLayout(2, 3));
        for (JCheckBox checkBox : toppingsCheckBoxes) {
            toppingsPanel.add(checkBox);
        }
        gbc.gridx = 2;
        centerPanel.add(toppingsPanel, gbc);

        orderTextArea = new JTextArea(20, 40);
        orderTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(orderTextArea);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        centerPanel.add(scrollPane, gbc);

        contentPane.add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        orderButton = new JButton("Order");
        clearButton = new JButton("Clear");
        quitButton = new JButton("Quit");
        buttonPanel.add(orderButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateOrder();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit Confirmation", JOptionPane.OK_CANCEL_OPTION);
                if (confirm == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    private void calculateOrder() {
        double crustPrice = 0.0;
        double sizePrice = 0.0;

        if (thinCrustRadioButton.isSelected()) {
            crustPrice = 8.00;
        } else if (regularCrustRadioButton.isSelected()) {
            crustPrice = 10.00;
        } else if (deepDishCrustRadioButton.isSelected()) {
            crustPrice = 12.00;
        }

        String selectedSize = (String) sizeComboBox.getSelectedItem();
        if (selectedSize.equals("Small")) {
            sizePrice = 8.00;
        } else if (selectedSize.equals("Medium")) {
            sizePrice = 12.00;
        } else if (selectedSize.equals("Large")) {
            sizePrice = 16.00;
        } else if (selectedSize.equals("Super")) {
            sizePrice = 20.00;
        }

        toppingsCost = 0.0;
        for (JCheckBox toppingCheckBox : toppingsCheckBoxes) {
            if (toppingCheckBox.isSelected()) {
                toppingsCost += 1.00;
            }
        }

        subTotal = crustPrice + sizePrice + toppingsCost;
        tax = 0.07 * subTotal;
        total = subTotal + tax;

        DecimalFormat df = new DecimalFormat("#.00");
        orderTextArea.setText("=========================================\n");
        orderTextArea.append("Type of Crust & Size\t\tPrice\n");
        orderTextArea.append("Crust: " + getSelectedCrust() + "\t$" + df.format(crustPrice) + "\n");
        orderTextArea.append("Size: " + selectedSize + "\t$" + df.format(sizePrice) + "\n");
        orderTextArea.append("\nIngredient\tPrice\n");
        for (int i = 0; i < toppingsCheckBoxes.length; i++) {
            if (toppingsCheckBoxes[i].isSelected()) {
                orderTextArea.append(toppingNames[i] + "\t$1.00\n");
            }
        }
        orderTextArea.append("\nSub-total:\t$" + df.format(subTotal) + "\n");
        orderTextArea.append("Tax:\t$" + df.format(tax) + "\n");
        orderTextArea.append("-------------------------------------\n");
        orderTextArea.append("Total:\t$" + df.format(total) + "\n");
        orderTextArea.append("=========================================\n");
    }

    private String getSelectedCrust() {
        if (thinCrustRadioButton.isSelected()) {
            return "Thin";
        } else if (regularCrustRadioButton.isSelected()) {
            return "Regular";
        } else if (deepDishCrustRadioButton.isSelected()) {
            return "Deep-dish";
        } else {
            return "Unknown";
        }
    }

    private void clearForm() {
        thinCrustRadioButton.setSelected(false);
        regularCrustRadioButton.setSelected(false);
        deepDishCrustRadioButton.setSelected(false);
        sizeComboBox.setSelectedIndex(0);
        for (JCheckBox toppingCheckBox : toppingsCheckBoxes) {
            toppingCheckBox.setSelected(false);
        }
        orderTextArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PizzaGUIFrame frame = new PizzaGUIFrame();
            frame.setVisible(true);
        });
    }
}
