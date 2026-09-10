import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ScientificCalculator extends JFrame {
    private JTextField display;
    private JLabel historyLabel;
    private double firstNumber = 0;
    private String operator = "";
    private boolean startNewNumber = true;
    private boolean isScientificMode = true;
    
    public ScientificCalculator() {
        // Setup window
        setTitle("Scientific Calculator");
        setSize(500, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(240, 240, 240));
        
        // Create display panel
        JPanel displayPanel = createDisplayPanel();
        
        // Create button panel
        JPanel buttonPanel = createButtonPanel();
        
        // Add to main panel
        mainPanel.add(displayPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private JPanel createDisplayPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        
        // History label
        historyLabel = new JLabel(" ");
        historyLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        historyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        historyLabel.setForeground(Color.GRAY);
        panel.add(historyLabel, BorderLayout.NORTH);
        
        // Main display
        display = new JTextField("0");
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setFont(new Font("Arial", Font.BOLD, 32));
        display.setEditable(false);
        display.setBackground(Color.WHITE);
        display.setBorder(null);
        panel.add(display, BorderLayout.CENTER);
        
        panel.setPreferredSize(new Dimension(480, 100));
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 5, 5, 5));
        
        // Scientific buttons
        String[] buttons = {
            "sin", "cos", "tan", "C", "←",
            "log", "ln", "π", "e", "x²",
            "√", "^", "!", "(", ")",
            "7", "8", "9", "÷", "%",
            "4", "5", "6", "×", "1/x",
            "1", "2", "3", "-", "=",
            "0", ".", "=", "+", "Mode"
        };
        
        // Create buttons
        for (String text : buttons) {
            JButton button = createButton(text);
            panel.add(button);
        }
        
        return panel;
    }
    
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        
        // Set button colors based on function
        if (text.equals("C")) {
            button.setBackground(Color.RED);
            button.setForeground(Color.WHITE);
        } else if (text.equals("=")) {
            button.setBackground(new Color(0, 150, 0));
            button.setForeground(Color.WHITE);
        } else if (text.equals("Mode")) {
            button.setBackground(new Color(70, 130, 180));
            button.setForeground(Color.WHITE);
        } else if (text.equals("←") || text.equals("÷") || text.equals("×") || 
                   text.equals("-") || text.equals("+") || text.equals("%") ||
                   text.equals("^") || text.equals("!")) {
            button.setBackground(new Color(200, 200, 200));
            button.setForeground(Color.BLACK);
        } else if (text.equals("sin") || text.equals("cos") || text.equals("tan") ||
                   text.equals("log") || text.equals("ln") || text.equals("√") ||
                   text.equals("x²") || text.equals("1/x") || text.equals("π") ||
                   text.equals("e") || text.equals("(") || text.equals(")")) {
            button.setBackground(new Color(173, 216, 230)); // Light blue
            button.setForeground(Color.BLACK);
        } else {
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLACK);
        }
        
        // Set button font and style
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        
        // Add action listener
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleButtonClick(text);
            }
        });
        
        return button;
    }
    
    private void handleButtonClick(String buttonText) {
        switch (buttonText) {
            case "C":
                clearCalculator();
                break;
                
            case "←":
                handleBackspace();
                break;
                
            case "=":
                calculateResult();
                break;
                
            case "+":
            case "-":
            case "×":
            case "÷":
            case "%":
            case "^":
                handleOperator(buttonText);
                break;
                
            case ".":
                handleDecimal();
                break;
                
            case "sin":
            case "cos":
            case "tan":
                handleTrigFunction(buttonText);
                break;
                
            case "log":
            case "ln":
            case "√":
            case "x²":
            case "!":
            case "1/x":
                handleMathFunction(buttonText);
                break;
                
            case "π":
                handlePi();
                break;
                
            case "e":
                handleE();
                break;
                
            case "(":
            case ")":
                handleParenthesis(buttonText);
                break;
                
            case "Mode":
                toggleMode();
                break;
                
            default: // Numbers 0-9
                handleNumber(buttonText);
                break;
        }
    }
    
    private void handleNumber(String number) {
        if (startNewNumber) {
            display.setText(number);
            startNewNumber = false;
        } else {
            display.setText(display.getText() + number);
        }
    }
    
    private void handleDecimal() {
        String currentText = display.getText();
        if (!currentText.contains(".")) {
            if (startNewNumber) {
                display.setText("0.");
                startNewNumber = false;
            } else {
                display.setText(currentText + ".");
            }
        }
    }
    
    private void handleOperator(String op) {
        if (!operator.isEmpty()) {
            calculateResult();
        }
        
        try {
            firstNumber = Double.parseDouble(display.getText());
            operator = op;
            historyLabel.setText(firstNumber + " " + operator);
            startNewNumber = true;
        } catch (NumberFormatException e) {
            display.setText("Error");
        }
    }
    
    private void handleTrigFunction(String func) {
        try {
            double number = Double.parseDouble(display.getText());
            double result = 0;
            
            // Convert degrees to radians (for sin, cos, tan)
            double radians = Math.toRadians(number);
            
            switch (func) {
                case "sin":
                    result = Math.sin(radians);
                    break;
                case "cos":
                    result = Math.cos(radians);
                    break;
                case "tan":
                    result = Math.tan(radians);
                    break;
            }
            
            // Format result
            String formattedResult;
            if (Math.abs(result) < 0.000001) {
                formattedResult = "0";
            } else {
                formattedResult = String.format("%.8f", result).replaceAll("0*$", "").replaceAll("\\.$", "");
            }
            
            historyLabel.setText(func + "(" + number + "°) =");
            display.setText(formattedResult);
            startNewNumber = true;
            
        } catch (NumberFormatException e) {
            display.setText("Error");
        }
    }
    
    private void handleMathFunction(String func) {
        try {
            double number = Double.parseDouble(display.getText());
            double result = 0;
            
            switch (func) {
                case "log":
                    if (number > 0) {
                        result = Math.log10(number);
                    } else {
                        display.setText("Error");
                        return;
                    }
                    break;
                    
                case "ln":
                    if (number > 0) {
                        result = Math.log(number);
                    } else {
                        display.setText("Error");
                        return;
                    }
                    break;
                    
                case "√":
                    if (number >= 0) {
                        result = Math.sqrt(number);
                    } else {
                        display.setText("Error");
                        return;
                    }
                    break;
                    
                case "x²":
                    result = number * number;
                    break;
                    
                case "!":
                    if (number >= 0 && number == (int) number) {
                        result = factorial((int) number);
                    } else {
                        display.setText("Error");
                        return;
                    }
                    break;
                    
                case "1/x":
                    if (number != 0) {
                        result = 1 / number;
                    } else {
                        display.setText("Error");
                        return;
                    }
                    break;
            }
            
            historyLabel.setText(func + "(" + number + ") =");
            
            // Format result
            String formattedResult;
            if (result == (int) result) {
                formattedResult = String.valueOf((int) result);
            } else {
                formattedResult = String.format("%.8f", result).replaceAll("0*$", "").replaceAll("\\.$", "");
            }
            
            display.setText(formattedResult);
            startNewNumber = true;
            
        } catch (NumberFormatException e) {
            display.setText("Error");
        }
    }
    
    private int factorial(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        int result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
    
    private void handlePi() {
        if (startNewNumber) {
            display.setText("3.14159265");
            startNewNumber = false;
        } else {
            display.setText(display.getText() + "3.14159265");
        }
    }
    
    private void handleE() {
        if (startNewNumber) {
            display.setText("2.71828183");
            startNewNumber = false;
        } else {
            display.setText(display.getText() + "2.71828183");
        }
    }
    
    private void handleParenthesis(String parenthesis) {
        if (startNewNumber) {
            display.setText(parenthesis);
            startNewNumber = false;
        } else {
            display.setText(display.getText() + parenthesis);
        }
    }
    
    private void toggleMode() {
        isScientificMode = !isScientificMode;
        if (isScientificMode) {
            setTitle("Scientific Calculator");
        } else {
            setTitle("Basic Calculator");
        }
        JOptionPane.showMessageDialog(this, 
            "Mode switched to: " + (isScientificMode ? "Scientific" : "Basic"),
            "Mode Changed", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void calculateResult() {
        if (operator.isEmpty()) return;
        
        try {
            double secondNumber = Double.parseDouble(display.getText());
            double result = 0;
            
            switch (operator) {
                case "+":
                    result = firstNumber + secondNumber;
                    break;
                case "-":
                    result = firstNumber - secondNumber;
                    break;
                case "×":
                    result = firstNumber * secondNumber;
                    break;
                case "÷":
                    if (secondNumber != 0) {
                        result = firstNumber / secondNumber;
                    } else {
                        display.setText("Error");
                        return;
                    }
                    break;
                case "%":
                    result = firstNumber % secondNumber;
                    break;
                case "^":
                    result = Math.pow(firstNumber, secondNumber);
                    break;
            }
            
            // Display result
            historyLabel.setText(firstNumber + " " + operator + " " + secondNumber + " =");
            
            // Format result
            String formattedResult;
            if (result == (int) result) {
                formattedResult = String.valueOf((int) result);
            } else {
                formattedResult = String.format("%.8f", result).replaceAll("0*$", "").replaceAll("\\.$", "");
            }
            
            display.setText(formattedResult);
            operator = "";
            startNewNumber = true;
            
        } catch (NumberFormatException e) {
            display.setText("Error");
        }
    }
    
    private void handleBackspace() {
        String currentText = display.getText();
        if (currentText.length() > 1) {
            display.setText(currentText.substring(0, currentText.length() - 1));
        } else {
            display.setText("0");
            startNewNumber = true;
        }
    }
    
    private void clearCalculator() {
        display.setText("0");
        historyLabel.setText(" ");
        firstNumber = 0;
        operator = "";
        startNewNumber = true;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ScientificCalculator calculator = new ScientificCalculator();
                calculator.setVisible(true);
            }
        });
    }
}