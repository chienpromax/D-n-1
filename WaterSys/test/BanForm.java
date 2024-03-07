/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */


import java.awt.Color;
import watersys.BanHang.ChiTietSPFrom;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javax.swing.JLabel;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import watersys.DAO.ChiTietSPDAO;
import watersys.DAO.BanDAO;
import watersys.DAO.HoaDonDAO;
import watersys.Entity.Ban;
import watersys.Entity.ChiTietSP;
import watersys.Entity.HoaDon;
import watersys.Utilities.Auth;
import watersys.Utilities.MsgBox;

/**
 *
 * @author xuanc
 */
public class BanForm extends javax.swing.JInternalFrame {

    BanDAO dao = new BanDAO();
    HoaDonDAO daoHD = new HoaDonDAO();
    ChiTietSPDAO daoCTSP = new ChiTietSPDAO();
    private Properties config;
    int row = 0;

    private List<JLabel> lblBanList;

    public BanForm() {
        this.setComponentPopupMenu(null);
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        loadConfig();

    }

//    void fillTableChiTiet() {
//        DefaultTableModel model = (DefaultTableModel) tblChiTietBan.getModel();
//        model.setRowCount(0);
//        try {
//            List<ChiTietSP> list = daoCTSP.selectAll();
//            for (ChiTietSP b : list) {
//                Object[] row = {
//                    b.getID(),
//                    b.getMaHD(),
//                    b.getMaSP(),
//                    b.getTenMuoc(),
//                    b.getLoai(),
//                    b.getSoLuong(),
//                    b.getDonGia(),};
//                model.addRow(row);
//            }
//        } catch (Exception e) {
//            MsgBox.alert(this, "lỗi truy vẫn dữ liệu");
//            System.out.println(e);
//        }
//    }
//    void insert() {
//        if (!Auth.isManager()) {
//            MsgBox.alert(this, "bạn không có quyền thêm bàn");
//        } else {
//            Ban model = getForm();
//            try {
//                dao.insert(model);
//                this.fillTable();
//                this.clear();
//                MsgBox.alert(this, "Thêm mới thành công!");
//            } catch (Exception e) {
//                MsgBox.alert(this, "Thêm mới thất bại!");
//            }
//        }
//    }
//    void delete() {
//        if (!Auth.isManager()) {
//            MsgBox.alert(this, "bạn không có quyền xóa bàn");
//        } else {
//            if (MsgBox.confirm(this, "bạn thục sự muốn xóa Bàn này")) {
//                String maBan = txtTenBan.getText();
//                try {
//                    dao.delete(maBan);
//                    this.fillTable();
//                    this.clear();
//                    MsgBox.alert(this, "xóa thành công");
//                } catch (Exception e) {
//                    MsgBox.alert(this, "xóa thất bại");
//                }
//            }
//        }
//    }
//    void update() {
//        Ban nh = getForm();
//        try {
//            dao.update(nh);
//            this.fillTable();
//            MsgBox.alert(this, "Sửa thành công");
//        } catch (Exception e) {
//            System.out.println(e);
//            MsgBox.alert(this, "Sửa thất bại");
//        }
//    }
//    void edit() {
//        String maBan = (String) tblBan.getValueAt(this.row, 0);
//        Ban nh = dao.selectById(maBan);
//        this.setForm(nh);
//    }
//    void clear() {
//        Ban nh = new Ban();
//        this.setForm(nh);
//        this.row = -1;
//    }
//    void setForm(Ban model) {
//        txtTenBan.setText(model.getMaBan());
//        txtTenKH.setText(model.getTenKh());
////        String trangThaiValue = model.getTrangThai();
//        // Kiểm tra nếu trangThaiValue không phải là null trước khi gọi equals
////        if (trangThaiValue != null) {
////            // Tìm index của giá trị trong JComboBox
////            for (int i = 0; i < cboTrangThai.getItemCount(); i++) {
////                if (trangThaiValue.equals(cboTrangThai.getItemAt(i))) {
////                    cboTrangThai.setSelectedIndex(i);
////                    break;
////                }
////            }
//
//    }
//    Ban getForm() {
//        Ban model = new Ban();
//        model.setMaBan(txtTenBan.getText());
//        model.setTenKh(txtTenKH.getText());
////        model.setTrangThai((String) cboTrangThai.getSelectedItem());
//        return model;
//    }
//    void fillTable() {
//        DefaultTableModel model = (DefaultTableModel) tblBan.getModel();
//        model.setRowCount(0);
//        try {
//            List<Ban> list = dao.selectAll();
//            for (Ban b : list) {
//                Object[] row = {
//                    b.getMaBan(),
//                    b.getTenKh()};
//                model.addRow(row);
//            }
//        } catch (Exception e) {
//            MsgBox.alert(this, "lỗi truy vẫn dữ liệu");
//            System.out.println(e);
//        }
//    }
    private void saveConfig() {
        config.setProperty("comboBox1", (String) cboB1.getSelectedItem());
        config.setProperty("comboBox2", (String) cboB2.getSelectedItem());
        config.setProperty("comboBox3", (String) cboB3.getSelectedItem());
        config.setProperty("comboBox4", (String) cboB4.getSelectedItem());
        config.setProperty("comboBox5", (String) cboB5.getSelectedItem());
        config.setProperty("comboBox6", (String) cboB6.getSelectedItem());
        config.setProperty("comboBox7", (String) cboB7.getSelectedItem());
        config.setProperty("comboBox8", (String) cboB8.getSelectedItem());
        config.setProperty("comboBox9", (String) cboB9.getSelectedItem());
        config.setProperty("comboBox10", (String) cboB10.getSelectedItem());

        try (OutputStream outputStream = new FileOutputStream("config.properties")) {
            config.store(outputStream, "ComboBox Status Config");
            MsgBox.alert(this, "Cập nhật trạng thái thành công");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadConfig() {
        config = new Properties();

        try (InputStream inputStream = new FileInputStream("config.properties")) {
            config.load(inputStream);

            // Khôi phục trạng thái của từng ComboBox từ cấu hình
            cboB1.setSelectedItem(config.getProperty("comboBox1", "Đang dùng"));
            cboB2.setSelectedItem(config.getProperty("comboBox2", "Đang dùng"));
            cboB3.setSelectedItem(config.getProperty("comboBox3", "Đang dùng"));
            cboB4.setSelectedItem(config.getProperty("comboBox4", "Đang dùng"));
            cboB5.setSelectedItem(config.getProperty("comboBox5", "Đang dùng"));
            cboB6.setSelectedItem(config.getProperty("comboBox6", "Đang dùng"));
            cboB7.setSelectedItem(config.getProperty("comboBox7", "Đang dùng"));
            cboB8.setSelectedItem(config.getProperty("comboBox8", "Đang dùng"));
            cboB9.setSelectedItem(config.getProperty("comboBox9", "Đang dùng"));
            cboB10.setSelectedItem(config.getProperty("comboBox10", "Đang dùng"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblBan1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cboB1 = new javax.swing.JComboBox<>();
        cboB2 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        lblBan2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblBan3 = new javax.swing.JLabel();
        cboB3 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        lblBan4 = new javax.swing.JLabel();
        cboB4 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        lblBan6 = new javax.swing.JLabel();
        cboB5 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        lblBan7 = new javax.swing.JLabel();
        cboB6 = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        lblBan8 = new javax.swing.JLabel();
        cboB7 = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        lblBan9 = new javax.swing.JLabel();
        cboB8 = new javax.swing.JComboBox<>();
        cboB10 = new javax.swing.JComboBox<>();
        cboB9 = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        lblBan10 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        lblBan5 = new javax.swing.JLabel();
        btnSua = new javax.swing.JButton();
        btnDatLai = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblChiTietBan = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(102, 255, 204));
        setOpaque(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        lblBan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/watersys/Icon/dinner-table (1).png"))); // NOI18N
        lblBan1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBan1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblBan1MousePressed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 204));
        jLabel2.setText("Bàn 1");

        cboB1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TRỐNG", "ĐẶT CHỖ", "ĐANG DÙNG" }));

        cboB2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TRỐNG", "ĐẶT CHỖ", "ĐANG DÙNG" }));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 204));
        jLabel3.setText("Bàn 2");

        lblBan2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/watersys/Icon/dinner-table (1).png"))); // NOI18N
        lblBan2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBan2MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblBan2MousePressed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 204));
        jLabel5.setText("Bàn 3");

        lblBan3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/watersys/Icon/dinner-table (1).png"))); // NOI18N
        lblBan3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBan3MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblBan3MousePressed(evt);
            }
        });

        cboB3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TRỐNG", "ĐẶT CHỖ", "ĐANG DÙNG" }));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 204));
        jLabel7.setText("Bàn 4");

        lblBan4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/watersys/Icon/dinner-table (1).png"))); // NOI18N
        lblBan4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBan4MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblBan4MousePressed(evt);
            }
        });

        cboB4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TRỐNG", "ĐẶT CHỖ", "ĐANG DÙNG" }));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 204));
        jLabel9.setText("Bàn 6");

        lblBan6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/watersys/Icon/dinner-table (1).png"))); // NOI18N
        lblBan6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBan6MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblBan6MousePressed(evt);
            }
        });

        cboB5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TRỐNG", "ĐẶT CHỖ", "ĐANG DÙNG" }));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 51, 204));
        jLabel11.setText("Bàn 7");

        lblBan7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/watersys/Icon/dinner-table (1).png"))); // NOI18N
        lblBan7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBan7MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblBan7MousePressed(evt);
            }
        });

        cboB6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TRỐNG", "ĐẶT CHỖ", "ĐANG DÙNG" }));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 51, 204));
        jLabel13.setText("Bàn 8");

        lblBan8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/watersys/Icon/dinner-table (1).png"))); // NOI18N
        lblBan8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBan8MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblBan8MousePressed(evt);
            }
        });

        cboB7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TRỐNG", "ĐẶT CHỖ", "ĐANG DÙNG" }));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 51, 204));
        jLabel15.setText("Bàn 9");

        lblBan9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/watersys/Icon/dinner-table (1).png"))); // NOI18N
        lblBan9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBan9MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblBan9MousePressed(evt);
            }
        });

        cboB8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TRỐNG", "ĐẶT CHỖ", "ĐANG DÙNG" }));

        cboB10.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TRỐNG", "ĐẶT CHỖ", "ĐANG DÙNG" }));

        cboB9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TRỐNG", "ĐẶT CHỖ", "ĐANG DÙNG" }));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 51, 204));
        jLabel18.setText("Bàn 10");

        lblBan10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/watersys/Icon/dinner-table (1).png"))); // NOI18N
        lblBan10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBan10MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblBan10MousePressed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 204));
        jLabel22.setText("Bàn 5");

        lblBan5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/watersys/Icon/dinner-table (1).png"))); // NOI18N
        lblBan5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBan5MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblBan5MousePressed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(0, 0, 153));
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setText("Cập nhật trạng thái");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnDatLai.setBackground(new java.awt.Color(0, 0, 153));
        btnDatLai.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDatLai.setForeground(new java.awt.Color(255, 255, 255));
        btnDatLai.setText("Đặt lại trạng thái");
        btnDatLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDatLaiActionPerformed(evt);
            }
        });

        tblChiTietBan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "MaHD", "TenNuoc", "Loai", "SoLuong", "DonGia", "MaBan"
            }
        ));
        tblChiTietBan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChiTietBanMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblChiTietBan);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 204));
        jLabel4.setText("Nước có trong bàn");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(142, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblBan1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(41, 41, 41)
                                        .addComponent(jLabel2)))
                                .addGap(84, 84, 84)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblBan2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(45, 45, 45)
                                        .addComponent(jLabel3)))
                                .addGap(92, 92, 92)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(45, 45, 45)
                                        .addComponent(jLabel5))
                                    .addComponent(lblBan3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblBan6, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(45, 45, 45)
                                                .addComponent(jLabel9))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(15, 15, 15)
                                                .addComponent(cboB5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(84, 84, 84)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(lblBan7, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addGap(45, 45, 45)
                                                        .addComponent(jLabel11))))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(99, 99, 99)
                                                .addComponent(cboB6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(92, 92, 92)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(lblBan8, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addGap(45, 45, 45)
                                                        .addComponent(jLabel13))))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(106, 106, 106)
                                                .addComponent(cboB7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(14, 14, 14)
                                        .addComponent(cboB1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(131, 131, 131)
                                        .addComponent(cboB2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(137, 137, 137)
                                        .addComponent(cboB3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(84, 84, 84)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblBan9, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblBan4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(45, 45, 45)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel15)
                                                    .addComponent(jLabel7)))))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGap(7, 7, 7)
                                        .addComponent(cboB4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(30, 30, 30))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(98, 98, 98)
                                        .addComponent(cboB8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(97, 97, 97)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBan5, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(jLabel22))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(cboB9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(jLabel18))
                            .addComponent(lblBan10, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(cboB10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(btnSua)
                            .addGap(28, 28, 28)
                            .addComponent(btnDatLai)
                            .addGap(374, 374, 374))
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1091, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4))
                .addGap(123, 123, 123))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblBan4, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboB4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblBan3, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboB3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblBan2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboB2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblBan1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboB1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblBan5, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboB9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(49, 49, 49)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblBan9, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboB8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblBan8, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboB7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblBan7, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboB6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblBan6, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboB5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblBan10, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboB10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDatLai, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(66, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
//        update();
        saveConfig();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnDatLaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDatLaiActionPerformed
        // TODO add your handling code here:
        if (MsgBox.confirm(this, "bạn có chắc muốn Đặt lại")) {
            cboB1.setSelectedIndex(0);
            cboB2.setSelectedIndex(0);
            cboB3.setSelectedIndex(0);
            cboB4.setSelectedIndex(0);
            cboB5.setSelectedIndex(0);
            cboB6.setSelectedIndex(0);
            cboB7.setSelectedIndex(0);
            cboB8.setSelectedIndex(0);
            cboB9.setSelectedIndex(0);
            cboB10.setSelectedIndex(0);
            saveConfig();
        }
    }//GEN-LAST:event_btnDatLaiActionPerformed
    void openThemSP() {
        try {
            // Tạo một đối tượng Frame mới
            Frame parentFrame = new Frame("Parent Frame");
            // Hiển thị HocVienJDialog là một dialog modal trên cửa sổ cha (parentFrame)
            new ChiTietSPFrom(parentFrame, true).setVisible(true);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void lblBan1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBan1MouseClicked
        if (evt.getClickCount() == 2) {
            openThemSP();
        }
    }//GEN-LAST:event_lblBan1MouseClicked

    private void lblBan2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBan2MouseClicked
        if (evt.getClickCount() == 2) {
            openThemSP();
        }
    }//GEN-LAST:event_lblBan2MouseClicked
    
    private void lblBan3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBan3MouseClicked
        if (evt.getClickCount() == 2) {
            openThemSP();
        }
    }//GEN-LAST:event_lblBan3MouseClicked
    
    private void lblBan4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBan4MouseClicked
        if (evt.getClickCount() == 2) {
            openThemSP();
        }
    }//GEN-LAST:event_lblBan4MouseClicked

    private void lblBan5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBan5MouseClicked
        if (evt.getClickCount() == 2) {
            openThemSP();
        }
    }//GEN-LAST:event_lblBan5MouseClicked
    
    private void lblBan6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBan6MouseClicked
        if (evt.getClickCount() == 2) {
            openThemSP();
        }
    }//GEN-LAST:event_lblBan6MouseClicked
    
    private void lblBan7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBan7MouseClicked
        if (evt.getClickCount() == 2) {
            openThemSP();
        }
    }//GEN-LAST:event_lblBan7MouseClicked

    private void lblBan8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBan8MouseClicked
        if (evt.getClickCount() == 2) {
            openThemSP();
        }
    }//GEN-LAST:event_lblBan8MouseClicked

    private void lblBan9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBan9MouseClicked
        if (evt.getClickCount() == 2) {
            openThemSP();
        }
    }//GEN-LAST:event_lblBan9MouseClicked

    private void lblBan10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBan10MouseClicked
        if (evt.getClickCount() == 2) {
            openThemSP();
        }
    }//GEN-LAST:event_lblBan10MouseClicked

    private void tblChiTietBanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChiTietBanMouseClicked
//        if (evt.getClickCount() == 1) {
//            this.row = tblChiTietBan.rowAtPoint(evttblChiTietBan;
////            editChiTiet();
//        }
    }//GEN-LAST:event_tblChiTietBanMouseClicked

    private void lblBanMousePressed(java.awt.event.MouseEvent evt, String maBan) {                                     
    if (evt.getClickCount() == 1) {
        try {
            DefaultTableModel model = (DefaultTableModel) tblChiTietBan.getModel();
            model.setRowCount(0);
            List<Object[]> list = dao.getHoaDonByMaBan(maBan);
            for (Object[] row : list) {
                model.addRow(new Object[]{row[0], row[1], row[2], row[3], row[4], row[5]});
            }
            
            //repaint() sẽ giúp đảm bảo rằng giao diện người dùng được cập nhật ngay lập tức.
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Lỗi", ex);
        }
    }
} 
    
    private void lblBan1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBan1MousePressed
        lblBanMousePressed(evt, "Ban1");
    }//GEN-LAST:event_lblBan1MousePressed

    private void lblBan2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBan2MousePressed
        lblBanMousePressed(evt, "Ban2");
    }//GEN-LAST:event_lblBan2MousePressed

    private void lblBan3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBan3MousePressed
        lblBanMousePressed(evt, "Ban3");
    }//GEN-LAST:event_lblBan3MousePressed

    private void lblBan4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBan4MousePressed
        lblBanMousePressed(evt, "Ban4");
    }//GEN-LAST:event_lblBan4MousePressed

    private void lblBan5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBan5MousePressed
        lblBanMousePressed(evt, "Ban5");
    }//GEN-LAST:event_lblBan5MousePressed

    private void lblBan10MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBan10MousePressed
        lblBanMousePressed(evt, "Ban10");
    }//GEN-LAST:event_lblBan10MousePressed

    private void lblBan9MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBan9MousePressed
        lblBanMousePressed(evt, "Ban9");
    }//GEN-LAST:event_lblBan9MousePressed

    private void lblBan8MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBan8MousePressed
        lblBanMousePressed(evt, "Ban8");
    }//GEN-LAST:event_lblBan8MousePressed

    private void lblBan7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBan7MousePressed
        lblBanMousePressed(evt, "Ban7");
    }//GEN-LAST:event_lblBan7MousePressed

    private void lblBan6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBan6MousePressed
        lblBanMousePressed(evt, "Ban6");
    }//GEN-LAST:event_lblBan6MousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDatLai;
    private javax.swing.JButton btnSua;
    private javax.swing.JComboBox<String> cboB1;
    private javax.swing.JComboBox<String> cboB10;
    private javax.swing.JComboBox<String> cboB2;
    private javax.swing.JComboBox<String> cboB3;
    private javax.swing.JComboBox<String> cboB4;
    private javax.swing.JComboBox<String> cboB5;
    private javax.swing.JComboBox<String> cboB6;
    private javax.swing.JComboBox<String> cboB7;
    private javax.swing.JComboBox<String> cboB8;
    private javax.swing.JComboBox<String> cboB9;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblBan1;
    private javax.swing.JLabel lblBan10;
    private javax.swing.JLabel lblBan2;
    private javax.swing.JLabel lblBan3;
    private javax.swing.JLabel lblBan4;
    private javax.swing.JLabel lblBan5;
    private javax.swing.JLabel lblBan6;
    private javax.swing.JLabel lblBan7;
    private javax.swing.JLabel lblBan8;
    private javax.swing.JLabel lblBan9;
    private javax.swing.JTable tblChiTietBan;
    // End of variables declaration//GEN-END:variables
}
