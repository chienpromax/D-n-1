/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package watersys.UI;

import java.io.File;
import java.util.List;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import watersys.DAO.SanPhamDAO;
import watersys.Entity.SanPham;
import watersys.Utilities.Auth;
import watersys.Utilities.MsgBox;
import watersys.Utilities.XDate;
import watersys.Utilities.XImage;

/**
 *
 * @author xuanc
 */
public class SanPhamForm extends javax.swing.JInternalFrame {

    JFileChooser fileChooser = new JFileChooser();
    SanPhamDAO dao = new SanPhamDAO();

    public SanPhamForm() {
        initComponents();
        this.setComponentPopupMenu(null);
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        fillTable();
        txtNgayThem.setLocale(new Locale("vi", "VN")); // Đặt locale thành tiếng Việt

        txtMaNV.setText(Auth.user.getMaNV());
    }

    public boolean validete() {
        if (txtMaSP.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập MaSP");
            txtMaSP.requestFocus();
            return false;
        }
        if (txtTenSP.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập TênSP");
            txtTenSP.requestFocus();
            return false;
        }
        if (txtMaNV.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập mã nhân viên");
            txtMaNV.requestFocus();
            return false;
        }
        if (txtDonGia.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập Đơn giá");
            txtDonGia.requestFocus();
            return false;
        } else {
            try {
                Float.parseFloat(txtDonGia.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Đơn giá phải là số");
                return false;
            }
        }
        if (txtSoLuong.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập Số Lượng");
            txtSoLuong.requestFocus();
            return false;
        } else {
            try {
                Integer.parseInt(txtSoLuong.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Số lượng phải là số");
                return false;
            }
        }
        if (txtNgayThem.getDate().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa Nhập Ngày");
            txtNgayThem.requestFocus();
            return false;
        }
        if (lblHinh.getIcon().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa chọn hình");
            lblHinh.requestFocus();
            return false;
        }

        return true;
    }

    void delete() {
        if (MsgBox.confirm(this, "bạn thục sự muốn xóa SP này")) {
            String maSP = txtMaSP.getText();
            try {
                dao.delete(maSP);
                this.fillTable();
                this.clearForm();
                MsgBox.alert(this, "xóa thành công");
            } catch (Exception e) {
                MsgBox.alert(this, "xóa thất bại");
            }
        }
    }

    void update() {
        SanPham nv = getForm();
        try {
            dao.update(nv);
            this.fillTable();
            MsgBox.alert(this, "Sửa thành công");
        } catch (Exception e) {
            System.out.println(e);
            MsgBox.alert(this, "Sửa thất bại");
        }
    }

    void insert() {
        String maSP = txtMaSP.getText();
        if (dao.selectById(maSP) != null) {
            MsgBox.alert(this, "Mã sản phẩm đã tồn tại");
        } else {
            SanPham model = getForm();
            try {
                dao.insert(model);
                this.fillTable();
                this.clearForm();
                MsgBox.alert(this, "Thêm mới thành công!");
            } catch (Exception e) {
                System.out.println("Them that bai");
                System.out.println(e);
            }
        }
    }

    void clearForm() {
        this.setForm(new SanPham());
        txtMaNV.setText(Auth.user.getMaNV());
        row = -1;
    }

    void edit() {
        String maSP = (String) tblSanPham.getValueAt(this.row, 0);
        SanPham nh = dao.selectById(maSP);
        this.setForm(nh);
    }

    void setForm(SanPham model) {
        txtMaSP.setText(model.getMaSP());
        String loai = model.getLoai();
        cboLoai.setSelectedItem(loai);
        txtTenSP.setText(model.getTenNuoc());
        if (model.getAnh() != null) {
            lblHinh.setIcon(XImage.read(model.getAnh()));
            lblHinh.setToolTipText(model.getAnh());
        }
        txtDonGia.setText(String.valueOf(model.getDonGia()));
        txtNgayThem.setDate(model.getNgayThem());
        txtGhiChu.setText(model.getMoTa());
        txtMaNV.setText(model.getMaNV());
        txtSoLuong.setText(String.valueOf(model.getSoLuong()));

    }

    SanPham getForm() {
        SanPham model = new SanPham();
        model.setMaSP(txtMaSP.getText());
        String loai = (String) cboLoai.getSelectedItem();
        model.setLoai(loai);
        model.setTenNuoc(txtTenSP.getText());
        model.setAnh(lblHinh.getToolTipText());
        model.setDonGia(Float.parseFloat(txtDonGia.getText()));
        String soLuongText = txtSoLuong.getText();
        try {
            int soLuong = Integer.parseInt(soLuongText);
            model.setSoLuong(soLuong);
        } catch (NumberFormatException e) {
            model.setSoLuong(0);
        }
        model.setNgayThem(txtNgayThem.getDate());
        model.setMoTa(txtGhiChu.getText());
        model.setMaNV(txtMaNV.getText());
        int soLuong = Integer.parseInt(txtSoLuong.getText());
        model.setSoLuong(soLuong);
        return model;

    }

    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        try {
            List<SanPham> list = dao.selectAll();
            for (SanPham b : list) {
                Object[] row = {
                    b.getMaSP(),
                    b.getLoai(),
                    b.getTenNuoc(),
                    b.getAnh(),
                    b.getDonGia(),
                    XDate.toString(b.getNgayThem(), "dd-MM-yyyy"),
                    b.getMoTa(),
                    b.getMaNV(),
                    b.getSoLuong(),};
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "lỗi truy vẫn dữ liệu");
            System.out.println(e);
        }
    }

    void chonAnh() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            XImage.save(file);
            ImageIcon icon = XImage.read(file.getName());
            lblHinh.setIcon(icon);
            lblHinh.setToolTipText(file.getName());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtGhiChu = new javax.swing.JTextField();
        txtMaSP = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTenSP = new javax.swing.JTextField();
        txtMaNV = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lbSL = new javax.swing.JLabel();
        txtDonGia = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        txtSoLuong = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cboLoai = new javax.swing.JComboBox<>();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        btnTimkiem = new javax.swing.JButton();
        lblHinh = new javax.swing.JLabel();
        btnPrive = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        txtNgayThem = new com.toedter.calendar.JDateChooser();

        setBackground(new java.awt.Color(102, 255, 204));
        setOpaque(true);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setBackground(new java.awt.Color(0, 0, 0));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Mã sản phẩm:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("MaNV");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Tên sản phẩm:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Ngày nhập kho:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 204, 0));
        jLabel11.setText("Bảng danh sách sản phẩm ");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Đơn giá:");

        lbSL.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbSL.setText("(0 sản phẩm)");
        lbSL.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                lbSLAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Số lượng:");

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã sản phẩm", "Loại", "Tên Nước", "Anh", "Don Gia", "Ngày thêm", "Mô Tả", "MaNV", "Số Lượng"
            }
        ));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPham);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Ghi chú:");

        jLabel12.setBackground(new java.awt.Color(0, 0, 0));
        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Loại sản phẩm:");

        cboLoai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nước ngọt", "Nước ép", "Nước tăng lực", "Nước có cồn" }));

        btnThem.setBackground(new java.awt.Color(0, 0, 153));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(0, 0, 153));
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(0, 0, 153));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnMoi.setBackground(new java.awt.Color(0, 0, 153));
        btnMoi.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        btnTimkiem.setBackground(new java.awt.Color(0, 0, 153));
        btnTimkiem.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnTimkiem.setForeground(new java.awt.Color(255, 255, 255));
        btnTimkiem.setText("Tìm kiếm");
        btnTimkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimkiemActionPerformed(evt);
            }
        });

        lblHinh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHinh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHinhMouseClicked(evt);
            }
        });

        btnPrive.setBackground(new java.awt.Color(255, 255, 255));
        btnPrive.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnPrive.setForeground(new java.awt.Color(0, 51, 153));
        btnPrive.setText("<");
        btnPrive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPriveActionPerformed(evt);
            }
        });

        btnNext.setBackground(new java.awt.Color(255, 255, 255));
        btnNext.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnNext.setForeground(new java.awt.Color(0, 51, 153));
        btnNext.setText(">");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1290, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel10)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel4))
                                        .addGap(65, 65, 65)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtGhiChu, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(27, 27, 27)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cboLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(8, 8, 8)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                        .addComponent(jLabel3)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addComponent(jLabel5)
                                                        .addGap(49, 49, 49))))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(12, 12, 12)
                                                .addComponent(jLabel7)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtMaNV, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtSoLuong, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtNgayThem, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbSL))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(199, 199, 199)
                                .addComponent(btnThem)
                                .addGap(31, 31, 31)
                                .addComponent(btnSua)
                                .addGap(26, 26, 26)
                                .addComponent(btnXoa)
                                .addGap(28, 28, 28)
                                .addComponent(btnMoi)
                                .addGap(31, 31, 31)
                                .addComponent(btnTimkiem)))
                        .addGap(46, 46, 46)
                        .addComponent(btnPrive)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNext)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(cboLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10)
                                .addComponent(jLabel3))
                            .addComponent(txtNgayThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtGhiChu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(92, 92, 92)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(lbSL)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(114, 114, 114)
                                .addComponent(btnPrive, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(112, 112, 112)
                                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(67, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (validete()) {
            insert();
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if (validete()) {
            update();
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        if (validete()) {
            delete();
        }

    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed
    void timkiem() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        try {
            String keyword = txtMaSP.getText();
            List<SanPham> list = dao.selectByKeyword(keyword);
            for (SanPham b : list) {
                Object[] row = {
                    b.getMaSP(),
                    b.getLoai(),
                    b.getTenNuoc(),
                    b.getAnh(),
                    b.getDonGia(),
                    XDate.toString(b.getNgayThem(), "dd-MM-yyyy"),
                    b.getMoTa(),
                    b.getMaNV(),
                    b.getSoLuong(),};
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "lỗi truy vẫn dữ liệu");
            System.out.println(e);
        }
        this.clearForm();
        this.row = -1;
    }

    void next() {
        if (row < tblSanPham.getRowCount() - 1) {
            row++;
            edit();
        }
    }

    void last() {
        if (row > 0) {
            row--;
            edit();
        }
    }
    private void btnTimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimkiemActionPerformed
        if (txtMaSP.getText().equals("")) {
           MsgBox.alert(this, "masp trong");
        }else{
        timkiem();
        }
    }//GEN-LAST:event_btnTimkiemActionPerformed

    private void btnPriveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPriveActionPerformed
        last();
    }//GEN-LAST:event_btnPriveActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        next();
    }//GEN-LAST:event_btnNextActionPerformed
    int row = 0;
    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        if (evt.getClickCount() == 1) {
            this.row = tblSanPham.rowAtPoint(evt.getPoint());
            edit();
        }
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void lblHinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhMouseClicked
        if (evt.getClickCount() == 2) {
            chonAnh();
        }
    }//GEN-LAST:event_lblHinhMouseClicked

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased

    }//GEN-LAST:event_formMouseReleased

    private void lbSLAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_lbSLAncestorAdded
        int rowCount = tblSanPham.getRowCount();
        lbSL.setText("Số lượng sản phẩm: " + rowCount);
    }//GEN-LAST:event_lbSLAncestorAdded


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrive;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimkiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboLoai;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbSL;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtGhiChu;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtMaSP;
    private com.toedter.calendar.JDateChooser txtNgayThem;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenSP;
    // End of variables declaration//GEN-END:variables
}
