/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package watersys.UI;

import com.toedter.calendar.JTextFieldDateEditor;
import java.io.File;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import watersys.DAO.NhanVienDAO;
import watersys.Entity.NhanVien;
import watersys.Utilities.Auth;
import watersys.Utilities.MsgBox;
import watersys.Utilities.XDate;
import watersys.Utilities.XImage;
import java.util.Date;
import java.util.Locale;
import javax.swing.JOptionPane;

/**
 *
 * @author xuanc
 */
public class NhanVienForm extends javax.swing.JInternalFrame {

    JFileChooser fileChooser = new JFileChooser();
    NhanVienDAO dao = new NhanVienDAO();
    int row = 0;

    /**
     * Creates new form SanPham
     */
    public NhanVienForm() {
        initComponents();
        this.setComponentPopupMenu(null);
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        fillTable();

        dtNgaySinh.setLocale(new Locale("vi", "VN")); // Đặt locale thành tiếng Việt
        JTextFieldDateEditor editor2 = (JTextFieldDateEditor) dtNgaySinh.getDateEditor();
        editor2.setDateFormatString("dd/MM/yyyy");
    }

    public boolean validete() {
        String sdt = txtSDT.getText();
        String luongText = txtLuong.getText().trim();
        
        if (txtMaNV.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập mã nhân viên");
            txtMaNV.requestFocus();
            return false;
        }
        else if (txtMatKhau.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập Password");
            txtMatKhau.requestFocus();
            return false;
        }
        else if (txtHoTen.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập họ tên");
            txtHoTen.requestFocus();
            return false;
        }
        else if (sdt.length() < 10 || !sdt.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ,vui lòng nhập lại");
            return false;
        }
        else if (txtLuong.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập Lương");
            txtLuong.requestFocus();
            return false;
        } else {
            try {
                Float.parseFloat(txtLuong.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Lương phải là số");
                return false;
            }
        }
        if (txtEmail.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập email");
            txtEmail.requestFocus();
            return false;
        }
        else if (!txtEmail.getText().matches("\\w+@\\w+(\\.\\w+){1,2}")) {
            MsgBox.alert(this, "Email không đúng định dạng!");
            txtEmail.requestFocus();
            return false;
        }
        else if (dtNgaySinh.getDate().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa chọn Ngày sinh");
            dtNgaySinh.requestFocus();
            return false;
        }
        else if (lblHinh.getIcon().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa chọn hình");
            lblHinh.requestFocus();
            return false;
        }

        return true;
    }

    void edit() {
        String maNV = (String) tblNhanVien.getValueAt(this.row, 0);
        NhanVien nh = dao.selectById(maNV);
        this.setForm(nh);
    }

    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        try {

            List<NhanVien> list = dao.selectAll();
            for (NhanVien b : list) {
                Object[] row = {
                    b.getMaNV(),
                    b.getMatKhau(),
                    b.isVaiTro() ? "Quản lý" : "Nhân Viên",
                    b.getHoTen(),
                    b.getSDT(),
                    b.isGioiTinh() ? "Nam" : "Nữ",
                    XDate.toString(b.getNgaySinh(), "dd-MM-yyyy"),
                    b.getLuong(),
                    b.getEmail(),
                    b.getHinh(),};
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

    void setForm(NhanVien model) {
        txtMaNV.setText(model.getMaNV());
        txtMatKhau.setText(model.getMatKhau());
        rdQL.setSelected(model.isVaiTro());
        rdNV.setSelected(!model.isVaiTro());
        txtHoTen.setText(model.getHoTen());
        txtSDT.setText(String.valueOf(model.getSDT()));
        rdNam1.setSelected(model.isVaiTro());
        rdNu1.setSelected(!model.isVaiTro());
        dtNgaySinh.setDate(model.getNgaySinh());
        txtLuong.setText(String.valueOf(model.getLuong()));
        txtEmail.setText(model.getEmail());

        if (model.getHinh() != null) {
            lblHinh.setIcon(XImage.read(model.getHinh()));
            lblHinh.setToolTipText(model.getHinh());
        }
    }

    NhanVien getForm() {
        NhanVien model = new NhanVien();
        model.setMaNV(txtMaNV.getText());
        model.setMatKhau(new String(txtMatKhau.getPassword()));
        model.setVaiTro(rdQL.isSelected());
        model.setHoTen(txtHoTen.getText());
        model.setSDT(txtSDT.getText());
        model.setGioiTinh(rdNam1.isSelected());
        model.setNgaySinh(dtNgaySinh.getDate());
//        model.setLuong(Float.parseFloat(txtLuong.getText()));
        String luongText = txtLuong.getText();
        if (!luongText.isEmpty()) {
            model.setLuong(Float.parseFloat(luongText));
        } else {
            model.setLuong(0.0f);
            System.out.println("lỗi");
        }
        model.setEmail(txtEmail.getText());
        model.setHinh(lblHinh.getToolTipText());
        return model;

    }

    void insert() {
        NhanVien model = getForm();
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

    void update() {
        NhanVien nv = getForm();
        try {
            dao.update(nv);
            this.fillTable();
            MsgBox.alert(this, "Sửa thành công");
        } catch (Exception e) {
            System.out.println(e);
            MsgBox.alert(this, "Sửa thất bại");
        }
    }

    void delete() {
        if (!Auth.isManager()) {
            MsgBox.alert(this, "bạn không có quyền xóa bàn");
        } else {
            if (MsgBox.confirm(this, "bạn thục sự muốn xóa Bàn này")) {
                String maNV = txtMaNV.getText();
                try {
                    dao.delete(maNV);
                    this.fillTable();
                    this.clearForm();
                    MsgBox.alert(this, "xóa thành công");
                } catch (Exception e) {
                    MsgBox.alert(this, "xóa thất bại");
                }
            }
        }
    }

    void clearForm() {
        this.setForm(new NhanVien());
        this.updateStatus();
        row = -1;
        updateStatus();
    }

    void updateStatus() {
        boolean edit = this.row >= 0;
        boolean first = this.row == 0;
        boolean last = this.row == tblNhanVien.getRowCount() - 1;
        txtMaNV.setEditable(!edit);
        //Khi insert thì không update, delete
        btnThem.setEnabled(!edit);
        btnSua1.setEnabled(!edit);
        btnXoa1.setEnabled(!edit);
        btnMoi.setEnabled(!edit);
        btnTimKiem.setEnabled(!edit);
        btnLast.setEnabled(!edit && !first);
        btnnext.setEnabled(!edit && !first);

    }

    void next() {
        if (row < tblNhanVien.getRowCount() - 1) {
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        txtHoTen = new javax.swing.JTextField();
        txtLuong = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        rdQL = new javax.swing.JRadioButton();
        rdNV = new javax.swing.JRadioButton();
        rdNam1 = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        rdNu1 = new javax.swing.JRadioButton();
        btnLast = new javax.swing.JButton();
        lblHinh = new javax.swing.JLabel();
        btnnext = new javax.swing.JButton();
        btnTimKiem = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnSua1 = new javax.swing.JButton();
        btnXoa1 = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        txtMatKhau = new javax.swing.JPasswordField();
        dtNgaySinh = new com.toedter.calendar.JDateChooser();

        setBackground(new java.awt.Color(102, 255, 204));
        setOpaque(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "MãNV", "Mật khẩu", "Vai trò", "Họ tên", "SDT", "Giới tính", "Ngày sinh", "Lương", "Email", "Hinh"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanVien);

        jLabel9.setBackground(new java.awt.Color(0, 0, 0));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Mã nhân viên:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Mật khẩu");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Họ tên");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Ngày sinh:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Lương");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Email");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Số ĐT");

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("Vai trò");

        buttonGroup1.add(rdQL);
        rdQL.setSelected(true);
        rdQL.setText("Quản lý");

        buttonGroup1.add(rdNV);
        rdNV.setText("Nhân viên");

        buttonGroup2.add(rdNam1);
        rdNam1.setSelected(true);
        rdNam1.setText("Nam");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText("Giới tính");

        buttonGroup2.add(rdNu1);
        rdNu1.setText("Nữ");

        btnLast.setBackground(new java.awt.Color(255, 255, 255));
        btnLast.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnLast.setForeground(new java.awt.Color(0, 51, 153));
        btnLast.setText("<");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        lblHinh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHinh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblHinhMousePressed(evt);
            }
        });

        btnnext.setBackground(new java.awt.Color(255, 255, 255));
        btnnext.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnnext.setForeground(new java.awt.Color(0, 51, 153));
        btnnext.setText(">");
        btnnext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnextActionPerformed(evt);
            }
        });

        btnTimKiem.setBackground(new java.awt.Color(0, 0, 153));
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnTimKiem.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        btnThem.setBackground(new java.awt.Color(0, 0, 153));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua1.setBackground(new java.awt.Color(0, 0, 153));
        btnSua1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSua1.setForeground(new java.awt.Color(255, 255, 255));
        btnSua1.setText("Sửa");
        btnSua1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSua1ActionPerformed(evt);
            }
        });

        btnXoa1.setBackground(new java.awt.Color(0, 0, 153));
        btnXoa1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnXoa1.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa1.setText("Xóa");
        btnXoa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoa1ActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnThem)
                                .addGap(31, 31, 31)
                                .addComponent(btnSua1)
                                .addGap(26, 26, 26)
                                .addComponent(btnXoa1)
                                .addGap(28, 28, 28)
                                .addComponent(btnMoi)
                                .addGap(31, 31, 31)
                                .addComponent(btnTimKiem)
                                .addGap(63, 63, 63))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(76, 76, 76)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel10))
                                        .addGap(27, 27, 27)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(rdQL)
                                                .addGap(49, 49, 49)
                                                .addComponent(rdNV))
                                            .addComponent(txtMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                                            .addComponent(txtMatKhau))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(21, 21, 21)
                                                .addComponent(jLabel5))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel2))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel3))))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel7))
                                        .addGap(78, 78, 78)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtHoTen, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                                            .addComponent(txtSDT))
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel6)))
                                .addGap(31, 31, 31)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(rdNam1)
                                        .addGap(49, 49, 49)
                                        .addComponent(rdNu1))
                                    .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                                    .addComponent(txtLuong)
                                    .addComponent(dtNgaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(btnLast)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnnext))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1311, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(btnnext, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(144, 144, 144))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(124, 124, 124)
                                .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)
                                    .addComponent(rdNam1)
                                    .addComponent(rdNu1))
                                .addGap(19, 19, 19)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(txtLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)
                                    .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(rdQL)
                                        .addComponent(rdNV)))
                                .addGap(22, 22, 22)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel6))
                                            .addComponent(jLabel4))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel7)))
                                    .addComponent(dtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(56, 56, 56)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnSua1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnXoa1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnnextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnextActionPerformed
        // TODO add your handling code here:
        next();
    }//GEN-LAST:event_btnnextActionPerformed

    void timkiem() {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        try {
            String keyword = txtMaNV.getText();
            List<NhanVien> list = dao.selectByKeyword(keyword);
            for (NhanVien b : list) {
                Object[] row = {
                    b.getMaNV(),
                    b.getMatKhau(),
                    b.isVaiTro() ? "Quản lý" : "Nhân Viên",
                    b.getHoTen(),
                    b.getSDT(),
                    b.isGioiTinh() ? "Nam" : "Nữ",
                    XDate.toString(b.getNgaySinh(), "dd-MM-yyyy"),
                    b.getLuong(),
                    b.getEmail(),
                    b.getHinh(),};
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "lỗi truy vẫn dữ liệu");
            System.out.println(e);
        }
        this.clearForm();
        this.row = -1;
        this.updateStatus();
    }
    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        if (txtMaNV.getText().equals("")) {
            MsgBox.alert(this, "ma nhan vien trong");
        }else{
            timkiem();
        }
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        String maNV = txtMaNV.getText();
        if (dao.selectById(maNV) != null) {
            MsgBox.alert(this, "Mã nhân viên đã tồn tại");
            return;
        }
        if (validete()) {
            insert();
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSua1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSua1ActionPerformed
        if (validete()) {
            update();
        }
    }//GEN-LAST:event_btnSua1ActionPerformed

    private void btnXoa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoa1ActionPerformed
        if (validete()) {
            delete();
        }

    }//GEN-LAST:event_btnXoa1ActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            this.row = tblNhanVien.rowAtPoint(evt.getPoint());
            edit();
        }
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void lblHinhMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhMousePressed
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            chonAnh();
        }
    }//GEN-LAST:event_lblHinhMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnSua1;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa1;
    private javax.swing.JButton btnnext;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private com.toedter.calendar.JDateChooser dtNgaySinh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JRadioButton rdNV;
    private javax.swing.JRadioButton rdNam1;
    private javax.swing.JRadioButton rdNu1;
    private javax.swing.JRadioButton rdQL;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtLuong;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtSDT;
    // End of variables declaration//GEN-END:variables
}
