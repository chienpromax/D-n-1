/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package watersys.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import watersys.DAO.KhachHangDAO;
import watersys.Utilities.MsgBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import watersys.DAO.KhachHangDAO;
import watersys.DAO.NhanVienDAO;
import watersys.Entity.KhachHang;

/**
 *
 * @author xuanc
 */
public class KhachHangForm extends javax.swing.JInternalFrame {

    KhachHangDAO dao = new KhachHangDAO();

    public KhachHangForm() {
        initComponents();
        this.setComponentPopupMenu(null);
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        fillTable();
    }

    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblKhachHang.getModel();
        model.setRowCount(0);
        try {
            List<KhachHang> list = dao.selectAll();
            for (KhachHang b : list) {
                String gioiTinh = b.isGioiTinh() ? "Nam" : "Nữ";
                Object[] row = {
                    b.getMaKH(),
                    b.getHoTenKH(),
                    gioiTinh, // Sửa ở đây để hiển thị "Nam" hoặc "Nữ"
                    b.getSDT(),
                    b.getNgayMua(),
                    b.getDanhGia(),};
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "lỗi truy vẫn dữ liệu");
            System.out.println(e);
        }
    }
    // Tính và hiển thị số lượng khách hàng
    // Tính và hiển thị số lượng khách hàng

    private void capNhatSoLuongKhachHang() {
        int rowCount = tblKhachHang.getRowCount();
        lbSoLuong.setText("Số lượng khách hàng: " + rowCount);
    }

    @SuppressWarnings("unchecked")


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtTenKH = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        rdNam = new javax.swing.JRadioButton();
        rdNu = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        lbSoLuong = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDanhGia = new javax.swing.JTextArea();
        txtMaKH = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnSua1 = new javax.swing.JButton();
        btnXoa1 = new javax.swing.JButton();
        btnMoi1 = new javax.swing.JButton();
        btnTimKiem = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txtNgayMua = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblKhachHang = new javax.swing.JTable();

        setBackground(new java.awt.Color(102, 255, 204));
        setOpaque(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel10.setText("Tên khách hàng:");

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("Giới tính:");

        rdNam.setText("Nam");

        rdNu.setText("Nữ");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 204, 0));
        jLabel6.setText("Bảng khách hàng");

        lbSoLuong.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbSoLuong.setText("(0 thành viên)");
        lbSoLuong.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                lbSoLuongAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setText("SĐT");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Đánh giá khách hàng: ");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel9.setText("Mã khách hàng: ");

        txtDanhGia.setColumns(20);
        txtDanhGia.setRows(5);
        jScrollPane1.setViewportView(txtDanhGia);

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

        btnMoi1.setBackground(new java.awt.Color(0, 0, 153));
        btnMoi1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnMoi1.setForeground(new java.awt.Color(255, 255, 255));
        btnMoi1.setText("Mới");
        btnMoi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoi1ActionPerformed(evt);
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

        jLabel11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel11.setText("Ngày mua");

        tblKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã khách hàng", "Tên khách hàng ", "Giới tính", "SDT", "Ngày mua", "Đánh giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhachHangMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblKhachHang);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnThem)
                .addGap(31, 31, 31)
                .addComponent(btnSua1)
                .addGap(26, 26, 26)
                .addComponent(btnXoa1)
                .addGap(28, 28, 28)
                .addComponent(btnMoi1)
                .addGap(31, 31, 31)
                .addComponent(btnTimKiem)
                .addGap(392, 392, 392))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane3)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(178, 178, 178)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(98, 98, 98)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rdNam)
                                .addGap(49, 49, 49)
                                .addComponent(rdNu))
                            .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(65, 65, 65)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel12)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNgayMua, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jLabel6)
                        .addGap(12, 12, 12)
                        .addComponent(lbSoLuong)))
                .addContainerGap(216, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11)
                        .addComponent(txtNgayMua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel9))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel10))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(rdNam)
                                .addComponent(rdNu)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSua1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMoi1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lbSoLuong))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(142, 142, 142))
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

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        //        insert();                                                                            
        // Lấy thông tin từ các trường nhập liệu
        String maKH = txtMaKH.getText();
        String tenKH = txtTenKH.getText();
        String sdt = txtSDT.getText();
        String danhGia = txtDanhGia.getText();
        boolean gioiTinh = rdNam.isSelected(); // Sử dụng rdNam để xác định giới tính

        // Kiểm tra xem các trường bắt buộc đã được nhập đầy đủ hay không
        if (tenKH.isEmpty() || txtNgayMua.getText().isEmpty() || danhGia.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ các thông tin: Tên khách hàng, Ngày mua, Đánh giá");
            return;
        }

        // Kiểm tra số điện thoại có đúng 10 số không
        if (sdt.length() < 10 || !sdt.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ,vui lòng nhập lại");
            return;
        }

        // Kiểm tra ngày mua có nhỏ hơn ngày hiện tại không
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date ngayMua = null;
        try {
            ngayMua = dateFormat.parse(txtNgayMua.getText());
            Date ngayHienTai = new Date(); // Ngày hiện tại
            if (ngayMua.after(ngayHienTai)) {
                JOptionPane.showMessageDialog(this, "Ngày mua không hợp lệ, vui lòng nhập lại .");
                return;
            }
        } catch (ParseException ex) {
            // Xử lý nếu ngày mua nhập vào không đúng định dạng
            JOptionPane.showMessageDialog(this, "Nhập ngày mua theo định dạng ngày-tháng-năm");
            return;
        }

        // Tạo đối tượng khách hàng mới từ dữ liệu vừa lấy được
        KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinh, sdt, ngayMua, danhGia);

        // Tạo đối tượng DAO để thực hiện thêm thông tin khách hàng vào cơ sở dữ liệu
        KhachHangDAO khachHangDAO = new KhachHangDAO();

        try {
            // Kiểm tra xem khách hàng có tồn tại không trước khi thêm mới
            watersys.Entity.KhachHang existingKhachHang = khachHangDAO.selectById(maKH);
            if (existingKhachHang == null) {
                khachHangDAO.insert(khachHang);
                // Hiển thị thông báo thêm mới thành công cho người dùng
                JOptionPane.showMessageDialog(this, "Khách hàng mới đã được thêm vào thành công.");
            } else {
                // Hiển thị thông báo lỗi nếu khách hàng đã tồn tại
                JOptionPane.showMessageDialog(this, "Khách hàng đã tồn tại với mã: " + maKH);
            }
        } catch (Exception e) {
            // Xử lý lỗi nếu có bằng cách hiển thị thông báo lỗi cho người dùng
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + e.getMessage());
        }

        fillTable(); // Cập nhật bảng hiển thị thông tin khách hàng
        capNhatSoLuongKhachHang(); // Cập nhật số lượng khách hàng
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSua1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSua1ActionPerformed
        //        update();              
        // Lấy thông tin từ các trường nhập liệu
        String maKH = txtMaKH.getText();
        String tenKH = txtTenKH.getText();
        String sdt = txtSDT.getText();
        String danhGia = txtDanhGia.getText();
        boolean gioiTinh = rdNam.isSelected(); // Sử dụng rdNam để xác định giới tính

        // Kiểm tra xem ngày mua đã được nhập đúng định dạng chưa
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date ngayMua = null;
        try {
            ngayMua = dateFormat.parse(txtNgayMua.getText());
        } catch (ParseException ex) {
            // Xử lý nếu ngày mua nhập vào không đúng định dạng
            JOptionPane.showMessageDialog(this, "Nhập ngày mua theo định dạng yyyy-MM-dd");
            return;
        }

        // Tạo đối tượng khách hàng mới từ dữ liệu vừa lấy được
        KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinh, sdt, ngayMua, danhGia);

        // Tạo đối tượng DAO để thực hiện cập nhật thông tin khách hàng
        KhachHangDAO khachHangDAO = new KhachHangDAO();

        try {
            // Kiểm tra xem khách hàng có tồn tại không trước khi cập nhật
            watersys.Entity.KhachHang existingKhachHang = khachHangDAO.selectById(maKH);
            if (existingKhachHang != null) {
                khachHangDAO.update(khachHang);
                // Hiển thị thông báo cập nhật thành công cho người dùng
                JOptionPane.showMessageDialog(this, "Thông tin khách hàng đã được cập nhật thành công.");
            } else {
                // Hiển thị thông báo lỗi nếu khách hàng không tồn tại
                JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng với mã: " + maKH);
            }
        } catch (Exception e) {
            // Xử lý lỗi nếu có bằng cách hiển thị thông báo lỗi cho người dùng
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + e.getMessage());
        }
        fillTable();
    }//GEN-LAST:event_btnSua1ActionPerformed

    private void btnXoa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoa1ActionPerformed
        //        delete();
        String MaKH = txtMaKH.getText();

        KhachHangDAO khachHangDAO = new KhachHangDAO();
//    
        try {
            watersys.Entity.KhachHang khachHang = khachHangDAO.selectById(MaKH);
            if (khachHang != null) {
                khachHangDAO.delete(MaKH);
                JOptionPane.showMessageDialog(this, "Khách hàng đã được xóa thành công.");
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng với mã: " + MaKH);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Bạn phải xóa MaKH: " + MaKH + " ở Trong hóa đơn trước");
        }
        // Reset form
        fillTable();
        resetForm();
        capNhatSoLuongKhachHang();

    }//GEN-LAST:event_btnXoa1ActionPerformed

    private void btnMoi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoi1ActionPerformed
        //        clear();
        resetForm();
    }//GEN-LAST:event_btnMoi1ActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // Lấy mã khách hàng từ trường input (giả sử là txtMaKH)
        String maKhachHangCanTim = txtMaKH.getText();

        // Tạo đối tượng của lớp KhachHangDAO để thực hiện tìm kiếm
        KhachHangDAO khachHangDAO = new KhachHangDAO();

        // Gọi phương thức selectById để tìm khách hàng theo mã
        watersys.Entity.KhachHang khachHang = khachHangDAO.selectById(maKhachHangCanTim);

        if (khachHang != null) {
            // Hiển thị thông tin khách hàng lên giao diện
            txtTenKH.setText(khachHang.getHoTenKH());
            txtSDT.setText(khachHang.getSDT());
            txtDanhGia.setText(khachHang.getDanhGia());
            txtNgayMua.setText(khachHang.getNgayMua().toString());

            // Đặt giá trị cho RadioButton dựa vào giới tính của khách hàng
            if (khachHang.isGioiTinh()) {
                rdNam.setSelected(true);
                rdNu.setSelected(false);
            } else {
                rdNam.setSelected(false);
                rdNu.setSelected(true);
            }
        } else {
            // Hiển thị thông báo nếu không tìm thấy khách hàng
            JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng có mã " + maKhachHangCanTim, "Không tìm thấy", JOptionPane.INFORMATION_MESSAGE);
        }

    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void tblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangMouseClicked
        // TODO add your handling code here:
        int selectedRowIndex = tblKhachHang.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) tblKhachHang.getModel();

// Kiểm tra xem đã chọn dòng nào hay chưa
        if (selectedRowIndex >= 0) {
            // Lấy thông tin từ dòng đã chọn
            String maKH = model.getValueAt(selectedRowIndex, 0).toString();
            String hoTenKH = model.getValueAt(selectedRowIndex, 1).toString();
            String gioiTinhStr = model.getValueAt(selectedRowIndex, 2).toString();
            boolean gioiTinh = gioiTinhStr.equals("Nam"); // Xác định giới tính từ chuỗi

            String sdt = model.getValueAt(selectedRowIndex, 3).toString();
            Date ngayMua = (Date) model.getValueAt(selectedRowIndex, 4);
            String danhGia = model.getValueAt(selectedRowIndex, 5).toString();

            // Hiển thị thông tin lên các components tương ứng
            txtMaKH.setText(maKH);
            txtTenKH.setText(hoTenKH);
            // Cập nhật giới tính dựa trên chuỗi "Nam" hoặc "Nữ"
            if (gioiTinh) {
                rdNam.setSelected(true);
                rdNu.setSelected(false);
            } else {
                rdNam.setSelected(false);
                rdNu.setSelected(true);
            }
            txtSDT.setText(sdt);
            // Cập nhật ngày mua, bạn cần phải chuyển đổi ngày mua từ Date sang chuỗi để hiển thị trên TextField
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String strNgayMua = dateFormat.format(ngayMua);
            txtNgayMua.setText(strNgayMua);

            txtDanhGia.setText(danhGia);
        }

    }//GEN-LAST:event_tblKhachHangMouseClicked

    private void lbSoLuongAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_lbSoLuongAncestorAdded
// Tính và hiển thị số lượng khách hàng
        int rowCount = tblKhachHang.getRowCount();
        lbSoLuong.setText("Số lượng khách hàng: " + rowCount);
        // TODO add your handling code here:
    }//GEN-LAST:event_lbSoLuongAncestorAdded

    private void resetForm() {
        txtMaKH.setText("");
        txtTenKH.setText("");
        txtSDT.setText("");
        txtSDT.setText("");
        txtDanhGia.setText("");
        txtNgayMua.setText("");
        rdNam.setSelected(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMoi1;
    private javax.swing.JButton btnSua1;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbSoLuong;
    private javax.swing.JRadioButton rdNam;
    private javax.swing.JRadioButton rdNu;
    private javax.swing.JTable tblKhachHang;
    private javax.swing.JTextArea txtDanhGia;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtNgayMua;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenKH;
    // End of variables declaration//GEN-END:variables

}
