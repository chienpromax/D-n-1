/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package watersys.BanHang;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import watersys.DAO.BanDAO;
import watersys.DAO.SanPhamDAO;
import watersys.DAO.ChiTietSPDAO;
import watersys.Entity.ChiTietSP;
import watersys.Utilities.MsgBox;
import watersys.Utilities.XImage;
import watersys.DAO.HoaDonDAO;
import watersys.DAO.KhachHangDAO;
import watersys.Entity.SanPham;

/**
 *
 * @author xuanc
 */
public class ChiTietSPFrom1 extends javax.swing.JDialog {

    SanPhamDAO daoSP = new SanPhamDAO();
    ChiTietSPDAO daoCTSP = new ChiTietSPDAO();
    KhachHangDAO daoKH = new KhachHangDAO();
    HoaDonDAO daoHD = new HoaDonDAO();
    BanDAO daoBan = new BanDAO();
    int row = 0;

    /**
     * Creates new form ThemSP
     */
    public ChiTietSPFrom1(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        init();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                // Gọi phương thức khi form đóng
            }
        });
    }
    
    void init() {
        setIconImage(XImage.getAppIcon());
        setLocationRelativeTo(null);
        setTitle("Thêm sản phẩm vào bàn");
        fillTableSP();
        fillTableChiTiet();
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Định dạng ngày theo ý muốn
        String formattedDate = dateFormat.format(currentDate);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtTenSP = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtDonGia = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtSoluong = new javax.swing.JTextField();
        txtMaSP = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtLoai = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblThemSp = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblChiTietSP = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel4.setBackground(new java.awt.Color(255, 204, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(0, 51, 153))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Đơn giá:");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("Tên sản phẩm:");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setText("Loại SP");

        txtSoluong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoluongActionPerformed(evt);
            }
        });

        jLabel16.setBackground(new java.awt.Color(0, 0, 0));
        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Mã sản phẩm:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Số lượng");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("MaHD");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtDonGia)
                        .addComponent(txtSoluong, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(137, 137, 137))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtMaSP)
                                .addGap(2, 2, 2))
                            .addComponent(jLabel16))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel10))
                            .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)
                        .addComponent(txtSoluong, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        tblThemSp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã SP", "Loai", "Tên SP", "Đơn giá ", "Số Lương"
            }
        ));
        tblThemSp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThemSpMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblThemSp);

        tblChiTietSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "MaHD", "Mã SP", "Tên SP", "Loại", "Số lượng", "Giá"
            }
        ));
        tblChiTietSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChiTietSPMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblChiTietSP);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 153));
        jLabel1.setText("Chọn sản phẩm");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 153));
        jLabel2.setText("Chi tiêt hóa đơn");

        btnThem.setBackground(new java.awt.Color(0, 0, 153));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Thêm Sản phẩm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 500, Short.MAX_VALUE))
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(btnThem)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addGap(5, 5, 5)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private boolean validateFields() {
        // Kiểm tra txtSoluong
        try {
            int soLuong = Integer.parseInt(txtSoluong.getText());
            if (soLuong <= 0) {
                MsgBox.alert(this, "Số lượng phải lớn hơn 0");
                return false;
            }
        } catch (NumberFormatException e) {
            MsgBox.alert(this, "Nhập số lượng không hợp lệ");
            return false;
        }

        // Kiểm tra các trường khác
        if (txtMaSP.getText().trim().isEmpty()
                || txtMaHD.getText().trim().isEmpty()
                || txtTenSP.getText().trim().isEmpty()
                || txtDonGia.getText().trim().isEmpty()
                || txtLoai.getText().trim().isEmpty()) {
            MsgBox.alert(this, "Vui lòng nhập đầy đủ thông tin");
            return false;
        }

        // Các trường khác đều hợp lệ
        return true;
    }

    void insert() {
        ChiTietSP model = getFormChiTiet();
        try {
            daoCTSP.insert(model);
            this.fillTableChiTiet();
            this.clear();
            MsgBox.alert(this, "Thêm mới thành công!");
        } catch (Exception e) {
            MsgBox.alert(this, "Thêm mới thất bại!");
            System.out.println(e);
        }
    }
    
    void editChiTiet() {
        int ChiTietSP = (int) tblChiTietSP.getValueAt(this.row, 0);
        ChiTietSP ctsp = daoCTSP.selectById(ChiTietSP);
        this.setFormCT(ctsp);
    }

    void setFormCT(ChiTietSP model) {
        txtMaSP.setText(model.getMaSP());
        txtMaHD.setText(model.getMaHD());
        txtTenSP.setText(model.getTenMuoc());
        txtLoai.setText(model.getLoai());
        txtSoluong.setText((String.valueOf(model.getSoLuong())));
        txtDonGia.setText((String.valueOf(model.getDonGia())));
    }

    ChiTietSP getFormChiTiet() {
        ChiTietSP model = new ChiTietSP();
        model.setMaHD(txtMaHD.getText());
        model.setMaSP(txtMaSP.getText());
        model.setTenMuoc(txtTenSP.getText());
        model.setLoai(txtLoai.getText());

        try {
            int soLuong = Integer.parseInt(txtSoluong.getText());

            // Kiểm tra xem có hàng nào được chọn không
            int selectedRow = tblThemSp.getSelectedRow();
            if (selectedRow == -1) {
                MsgBox.alert(this, "Vui lòng chọn một hàng trong bảng SP");
                return null; // hoặc thực hiện xử lý phù hợp
            }

            // Kiểm tra giá trị trong cột soLuong của hàng được chọn
            int soLuongTrongBang = Integer.parseInt(tblThemSp.getValueAt(tblThemSp.getSelectedRow(), 4).toString());

            if (soLuong <= 0) {
                MsgBox.alert(this, "Số lượng phải lớn hơn 0");
                return null;
            } else if (soLuong > soLuongTrongBang) {
                MsgBox.alert(this, "Không đủ số lượng sản phẩm trong kho");
                return null;
            } else {
                float gia = Float.parseFloat(txtDonGia.getText());
                float donGia = soLuong * gia;
                model.setSoLuong(soLuong);
                model.setDonGia(donGia);

                // Cập nhật số lượng sản phẩm trong bảng SanPham
                daoSP.updateSoLuong(txtMaSP.getText(), soLuong);

                fillTableSP();
            }
        } catch (NumberFormatException e) {
            // Xử lý lỗi chuyển đổi số
            MsgBox.alert(this, "Nhập số lượng không hợp lệ");
            e.printStackTrace();
        }

        return model;
    }

    void setForm(SanPham model) {
        txtMaSP.setText(model.getMaSP());
        txtLoai.setText(model.getLoai());
        txtTenSP.setText(model.getTenNuoc());
        txtDonGia.setText((String.valueOf(model.getDonGia())));

    }

    void fillTableSP() {
        DefaultTableModel model = (DefaultTableModel) tblThemSp.getModel();
        model.setRowCount(0);
        try {
            List<SanPham> list = daoSP.selectAll();
            for (SanPham b : list) {
                Object[] row = {
                    b.getMaSP(),
                    b.getLoai(),
                    b.getTenNuoc(),
                    b.getDonGia(),
                    b.getSoLuong()};
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "lỗi truy vẫn dữ liệu");
            System.out.println(e);
        }
    }

    void fillTableChiTiet() {
        DefaultTableModel model = (DefaultTableModel) tblChiTietSP.getModel();
        model.setRowCount(0);
        try {
            List<ChiTietSP> list = daoCTSP.selectAll();
            for (ChiTietSP b : list) {
                Object[] row = {
                    b.getID(),
                    b.getMaHD(),
                    b.getMaSP(),
                    b.getTenMuoc(),
                    b.getLoai(),
                    b.getSoLuong(),
                    b.getDonGia(),};
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "lỗi truy vẫn dữ liệu");
            System.out.println(e);
        }
    }

    void edit() {
        String maSP = (String) tblThemSp.getValueAt(this.row, 0);
        SanPham sp = daoSP.selectById(maSP);
        this.setForm(sp);
    }

    private void tblThemSpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThemSpMouseClicked
    if (evt.getClickCount() == 1) {
            this.row = tblThemSp.rowAtPoint(evt.getPoint());
            edit();
            txtSoluong.setText("");
        }
    }//GEN-LAST:event_tblThemSpMouseClicked

    private void tblChiTietSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChiTietSPMouseClicked
    
    if (evt.getClickCount() == 1) {
            this.row = tblChiTietSP.rowAtPoint(evt.getPoint());
            editChiTiet();
        }
    }//GEN-LAST:event_tblChiTietSPMouseClicked
    
    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (validateFields()) {
            insert();
        }
    }//GEN-LAST:event_btnThemActionPerformed

    void delete() {
        int selectedRow = tblChiTietSP.getSelectedRow();

        if (selectedRow >= 0) {
            try {
                // Lấy giá trị từ cột đầu tiên
                Object objMaSP = tblChiTietSP.getValueAt(selectedRow, 0);

                // Chuyển đổi giá trị sang kiểu int
                if (objMaSP instanceof Integer) {
                    int maSP = (int) objMaSP;

                    if (MsgBox.confirm(this, "Bạn thực sự muốn xóa sản phẩm này?")) {
                        try {
                            // Lấy thông tin chi tiết sản phẩm trước khi xóa
                            ChiTietSP chiTietSP = daoCTSP.selectById(maSP);

                            // Cộng lại số lượng sản phẩm
                            int soLuongHienTai = chiTietSP.getSoLuong();
                            daoSP.updateXoaSoLuong(chiTietSP.getMaSP(), soLuongHienTai);

                            // Xóa chi tiết hóa đơn
                            daoCTSP.delete(maSP);

                            this.fillTableChiTiet();
                            this.fillTableSP();
                            this.clear();
                            MsgBox.alert(this, "Xóa thành công");
                        } catch (Exception e) {
                            MsgBox.alert(this, "Xóa thất bại");
                            e.printStackTrace();
                        }
                    }
                } else {
                    MsgBox.alert(this, "Dữ liệu cột đầu tiên không phải kiểu int");
                }
            } catch (Exception e) {
                MsgBox.alert(this, "Lỗi khi xóa sản phẩm");
                e.printStackTrace();
            }
        } else {
            MsgBox.alert(this, "Vui lòng chọn sản phẩm để xóa");
        }
    }
    
    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    void clear() {
        txtDonGia.setText("");
        txtLoai.setText("");
        txtMaSP.setText("");
        txtTenSP.setText("");
        txtSoluong.setText("");
        this.row = -1;
    }
    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clear();
        txtMaHD.setText("");
    }//GEN-LAST:event_btnMoiActionPerformed

    private void txtSoluongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoluongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoluongActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChiTietSP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChiTietSP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChiTietSP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChiTietSP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ChiTietSPFrom1 dialog = new ChiTietSPFrom1(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblChiTietSP;
    private javax.swing.JTable tblThemSp;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtLoai;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextField txtSoluong;
    private javax.swing.JTextField txtTenSP;
    // End of variables declaration//GEN-END:variables
}
