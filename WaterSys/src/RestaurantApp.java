import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class RestaurantApp extends JFrame {
    private Map<Integer, Table> tables = new HashMap<>();
    private JTextArea tableDetailsTextArea;

    public RestaurantApp() {
        setTitle("Quản lý nhà hàng");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Khởi tạo và thêm dữ liệu cho các bàn
        Table table1 = new Table(1);
        Table table2 = new Table(2);
        tables.put(1, table1);
        tables.put(2, table2);

        // Tạo giao diện
        createUI();

        // Hiển thị cửa sổ
        setVisible(true);
    }

    private void createUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Panel hiển thị chi tiết bàn
        JPanel displayDetailsPanel = new JPanel();
        tableDetailsTextArea = new JTextArea(10, 30);
        tableDetailsTextArea.setEditable(false);
        displayDetailsPanel.add(new JScrollPane(tableDetailsTextArea));

        mainPanel.add(displayDetailsPanel, BorderLayout.CENTER);

        // Tạo JLabel để đại diện cho các bàn
        JLabel table1Label = new JLabel("Bàn 1");
        JLabel table2Label = new JLabel("Bàn 2");

        // Thêm MouseListener để bắt sự kiện click
        table1Label.addMouseListener(new TableMouseListener(1));
        table2Label.addMouseListener(new TableMouseListener(2));

        // Tạo panel chứa các JLabel
        JPanel tablePanel = new JPanel();
        tablePanel.add(table1Label);
        tablePanel.add(table2Label);

        mainPanel.add(tablePanel, BorderLayout.NORTH);

        // Thêm mainPanel vào JFrame
        add(mainPanel);
    }

    private void displayTableDetails(int tableNumber) {
        Table table = tables.get(tableNumber);
        if (table != null) {
            StringBuilder details = new StringBuilder("Chi tiết bàn " + tableNumber + ":\n");
            for (Map.Entry<Product, Integer> entry : table.getProducts().entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();
                details.append(product.getName()).append(" x").append(quantity).append(" - $").append(product.getPrice()).append("\n");
            }
            tableDetailsTextArea.setText(details.toString());
        } else {
            tableDetailsTextArea.setText("Bàn không tồn tại");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RestaurantApp();
            }
        });
    }

    // Lớp bộ lắng nghe sự kiện chuột cho mỗi bàn
    class TableMouseListener extends MouseAdapter {
        private int tableNumber;

        public TableMouseListener(int tableNumber) {
            this.tableNumber = tableNumber;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            displayTableDetails(tableNumber);
        }
    }
}

class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class Table {
    private int number;
    private Map<Product, Integer> products = new HashMap<>();

    public Table(int number) {
        this.number = number;
    }

    public void addProduct(Product product) {
        products.put(product, products.getOrDefault(product, 0) + 1);
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }
}
