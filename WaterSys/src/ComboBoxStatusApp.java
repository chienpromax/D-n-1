import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Properties;

public class ComboBoxStatusApp {
    private JComboBox<String> comboBox1;
    private JComboBox<String> comboBox2;
    private Properties config;

    public ComboBoxStatusApp() {
        JFrame frame = new JFrame("ComboBox Status App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        
        JPanel panel = new JPanel(new GridLayout(0, 1));

        comboBox1 = new JComboBox<>(new String[]{"Đang dùng", "Trống", "Đặt bàn"});
        comboBox2 = new JComboBox<>(new String[]{"Đang dùng", "Trống", "Đặt bàn"});

        panel.add(new JLabel("ComboBox 1:"));
        panel.add(comboBox1);
        panel.add(new JLabel("ComboBox 2:"));
        panel.add(comboBox2);

        frame.add(panel, BorderLayout.CENTER);

        loadConfig(); // Khôi phục trạng thái khi khởi động ứng dụng

        JButton saveButton = new JButton("Lưu trạng thái");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveConfig(); // Lưu trạng thái khi nhấn nút "Lưu trạng thái"
            }
        });
        frame.add(saveButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void saveConfig() {
        config.setProperty("comboBox1", (String) comboBox1.getSelectedItem());
        config.setProperty("comboBox2", (String) comboBox2.getSelectedItem());

        try (OutputStream outputStream = new FileOutputStream("config.properties")) {
            config.store(outputStream, "ComboBox Status Config");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadConfig() {
        config = new Properties();

        try (InputStream inputStream = new FileInputStream("config.properties")) {
            config.load(inputStream);

            // Khôi phục trạng thái của từng ComboBox từ cấu hình
            comboBox1.setSelectedItem(config.getProperty("comboBox1", "Đang dùng"));
            comboBox2.setSelectedItem(config.getProperty("comboBox2", "Đang dùng"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ComboBoxStatusApp();
            }
        });
    }
}
