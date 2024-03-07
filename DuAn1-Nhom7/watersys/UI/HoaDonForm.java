package watersys.UI;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
import watersys.UI.*;
import java.util.List;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import watersys.DAO.ChiTietSPDAO;
import java.awt.Frame;
import java.awt.event.ItemEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import watersys.DAO.BanDAO;
import watersys.DAO.HoaDonDAO;
import watersys.DAO.KhachHangDAO;
import watersys.DAO.SanPhamDAO;
import watersys.Entity.Ban;
import watersys.Entity.ChiTietSP;
import watersys.Entity.HoaDon;
import watersys.Entity.KhachHang;
import watersys.Entity.SanPham;
import watersys.Utilities.Auth;
import watersys.Utilities.MsgBox;

/**
 *
 * @author xuanc
 */
public class HoaDonForm extends javax.swing.JInternalFrame {

    SanPhamDAO daoSP = new SanPhamDAO();
    ChiTietSPDAO daoCTSP = new ChiTietSPDAO();
    KhachHangDAO daoKH = new KhachHangDAO();
    HoaDonDAO daoHD = new HoaDonDAO();
    BanDAO daoBan = new BanDAO();
    int row = 0;

    public HoaDonForm() {
        this.setComponentPopupMenu(null);
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        fillTableSP();
        fillTableChiTiet();
        fillTableHD();

        fillComboBoxKH();
        fillComboBoxBan();

        txtMaNV.setText(Auth.user.getMaNV());
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Định dạng ngày theo ý muốn
        String formattedDate = dateFormat.format(currentDate);
        txtNgayMua.setText(formattedDate);
    }

    boolean updateTongDonGia(String maHD) {
        try {
            float tongDonGia = daoHD.getTongDonGiaByMaHD(maHD);
            // Lấy giá trị từ txtThanhToan
            String thanhToanText = txtThanhToan.getText();
            // Kiểm tra xem chuỗi có rỗng không
            if (!thanhToanText.isEmpty()) {
                // Chuyển đổi giá trị của txtThanhToan thành số
                float tienThanhToan = Float.parseFloat(thanhToanText);
                // Tính toán số tiền dư
                float tienDu = (tienThanhToan - tongDonGia);
                txtTongTien.setText(String.valueOf(tongDonGia));
                txtTienDu.setText(String.valueOf(tienDu));
                if (tienDu >= 0) {
                    MsgBox.alert(this, "Thanh toán thành công!");
                    txtTongTien.setText("");
                    txtThanhToan.setText("");
                    txtTienDu.setText("");
                    return true;
                } else {
                    MsgBox.alert(this, "Thanh toán không đủ tiền!");
                    return false;
                }
            } else {
                // Thông báo nếu chuỗi rỗng
                MsgBox.alert(this, "Vui lòng nhập giá trị cho Thanh toán.");
                return false;
            }
        } catch (NumberFormatException e) {
            // Xử lý ngoại lệ khi người dùng nhập không đúng định dạng số
            MsgBox.alert(this, "Vui lòng nhập một số hợp lệ cho Thanh toán.");
            e.printStackTrace();
        }
        return true;
    }

    void fillComboBoxKH() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboKH.getModel();
        model.removeAllElements();
        List<KhachHang> list = daoKH.selectAll();
        for (KhachHang kh : list) {
            model.addElement(kh);
        }
    }

    void fillComboBoxBan() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboBan.getModel();
        model.removeAllElements();
        List<Ban> list = daoBan.selectAll();
        for (Ban b : list) {
            model.addElement(b);
        }
    }

    void edit() {
        String maSP = (String) tblThemSp.getValueAt(this.row, 0);
        SanPham sp = daoSP.selectById(maSP);
        this.setForm(sp);
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        btnThemHD = new javax.swing.JButton();
        btnXoaHD = new javax.swing.JButton();
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
        jScrollPane2 = new javax.swing.JScrollPane();
        tblThemSp = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblChiTietSP = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        txtMaKH = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtNgayMua = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        cboKH = new javax.swing.JComboBox<>();
        cboBan = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblHD = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        btnThanhToan = new javax.swing.JButton();
        txtTienDu = new javax.swing.JTextField();
        txtTongTien = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtThanhToan = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        cboTrangThai = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(102, 255, 204));
        setOpaque(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

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

        btnThemHD.setBackground(new java.awt.Color(0, 0, 153));
        btnThemHD.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnThemHD.setForeground(new java.awt.Color(255, 255, 255));
        btnThemHD.setText("Thêm hóa đơn");
        btnThemHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemHDActionPerformed(evt);
            }
        });

        btnXoaHD.setBackground(new java.awt.Color(0, 0, 153));
        btnXoaHD.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnXoaHD.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaHD.setText("Xóa hóa đơn");
        btnXoaHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaHDActionPerformed(evt);
            }
        });

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

        jLabel16.setBackground(new java.awt.Color(0, 0, 0));
        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Mã sản phẩm:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Số lượng");

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
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDonGia)
                    .addComponent(txtSoluong, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(137, 137, 137))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(16, 16, 16)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtSoluong, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(txtLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblChiTietSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChiTietSPMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblChiTietSP);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 153));
        jLabel1.setText("Chọn Sản phẩm thêm vào hóa đơn chi tiết");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 153));
        jLabel2.setText("Chi tiêt hóa đơn");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                        .addComponent(jScrollPane3)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 500, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(jLabel1))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(194, 194, 194)
                        .addComponent(jLabel2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 153, 102));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(0, 51, 153))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("MaHD");

        jLabel11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Ngày mua");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("MaNV");

        txtMaNV.setEnabled(false);
        txtMaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaNVActionPerformed(evt);
            }
        });

        txtMaKH.setEnabled(false);

        jLabel14.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("MaKH");

        jLabel15.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("SDT");

        txtSDT.setEnabled(false);

        jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Tên KH:");

        txtNgayMua.setEnabled(false);

        jLabel12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("MaBan");

        cboKH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboKH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboKHMouseClicked(evt);
            }
        });
        cboKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboKHActionPerformed(evt);
            }
        });

        cboBan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboBanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3))
                        .addGap(49, 49, 49))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtMaHD)
                    .addComponent(txtMaNV, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNgayMua, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboKH, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(26, 26, 26)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboBan, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(cboBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10)
                                .addComponent(cboKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel11)
                                            .addComponent(txtNgayMua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel15)
                                        .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6)
                                        .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jLabel14))))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        tblHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã HD", "Ngày tạo", "MaNV", "MaBan", "TenKH", "Trang thai"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHDMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblHD);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 153));
        jLabel4.setText("Hóa đơn");

        btnThanhToan.setBackground(new java.awt.Color(0, 0, 153));
        btnThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        btnThanhToan.setText("Thanh toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        txtTongTien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTongTienActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 0));
        jLabel23.setText("Tiền dư");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 0));
        jLabel22.setText("Tiền thanh toán");

        jLabel21.setBackground(new java.awt.Color(0, 0, 0));
        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 0));
        jLabel21.setText("tổng tiền");

        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chưa thanh toán", "Đã thanh toán" }));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 0));
        jLabel24.setText("Trạng thái");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addGap(65, 65, 65)
                                .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addGap(18, 18, 18)
                                .addComponent(txtThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(228, 228, 228)
                        .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboTrangThai, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTienDu, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(90, 90, 90))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(txtTienDu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(txtThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel22)))))
                .addGap(29, 29, 29)
                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 153));
        jLabel5.setText("Thanh toán");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(318, 318, 318))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(309, 309, 309))))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 15, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnThemHD)
                .addGap(18, 18, 18)
                .addComponent(btnXoaHD)
                .addGap(216, 216, 216))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnThemHD, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnXoaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(62, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
        txtMaHD.setText("");
        fillComboBoxKH();
        fillComboBoxBan();
        txtTongTien.setText("");
        txtThanhToan.setText("");
        txtTienDu.setText("");
        cboTrangThai.setSelectedIndex(0);
        this.row = -1;
    }

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clear();
    }//GEN-LAST:event_btnMoiActionPerformed

//    void editHD() {
//        String MaHD = (String) tblHD.getValueAt(this.row, 0);
//        HoaDon hd = daoHD.selectById(MaHD);
//        this.setFormHD(hd);
//    }
//
    void setFormHD(HoaDon model) {
        txtMaHD.setText(model.getMaHD());
        cboKH.setSelectedItem(model.getHoTenKH());
        //chuyển đổi thành chuỗi trước khi hiển thị
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date ngayTao = model.getNgayTao();
        if (ngayTao != null) {
            String ngayMua = dateFormat.format(ngayTao);
            txtNgayMua.setText(ngayMua);
        } else {
            MsgBox.alert(this, "lỗi ngày");
        }
        txtMaNV.setText(model.getMaNV());
        txtMaKH.setText(model.getMaKH());
        cboBan.setSelectedItem(model.getMaBan());
    }

    HoaDon getFormHD() {
        HoaDon model = new HoaDon();
        boolean isValid = true; // Biến để kiểm tra tính hợp lệ của dữ liệu

        Object selectedItem = cboKH.getSelectedItem();
        if (selectedItem != null) {
            String hoTenKH = selectedItem.toString();
            model.setHoTenKH(hoTenKH);
        } else {
            MsgBox.alert(this, "Vui lòng chọn một khách hàng");
            isValid = false; // Không hợp lệ nếu không chọn khách hàng
        }

        try {
            Date ngayTao = new SimpleDateFormat("dd/MM/yyyy").parse(txtNgayMua.getText());
            model.setNgayTao(ngayTao);
        } catch (ParseException e) {
            e.printStackTrace();
            MsgBox.alert(this, "Ngày tạo không hợp lệ");
            isValid = false; // Không hợp lệ nếu ngày tạo không đúng định dạng
        }
        model.setMaNV(txtMaNV.getText());
        model.setMaKH(txtMaKH.getText());
        model.setMaBan(cboBan.getSelectedItem().toString());
        model.setTrangThai(cboTrangThai.getSelectedItem().toString());

        if (isValid) {
            return model;
        } else {
            return null; // Trả về null nếu dữ liệu không hợp lệ
        }
    }

    void clearHD() {
        txtMaHD.setText("");
        txtMaKH.setText("");
        txtSDT.setText("");
        fillComboBoxKH();
        fillComboBoxBan();

        txtMaNV.setText(Auth.user.getMaNV());
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Định dạng ngày theo ý muốn
        String formattedDate = dateFormat.format(currentDate);
        txtNgayMua.setText(formattedDate);
        txtSoluong.setText("");
        this.row = -1;
    }

    void fillTableHD() {
        DefaultTableModel model = (DefaultTableModel) tblHD.getModel();
        model.setRowCount(0);
        try {
            List<HoaDon> list = daoHD.selectAllTT();
            for (HoaDon b : list) {
                Object[] row = {
                    b.getMaHD(),
                    b.getNgayTao(),
                    b.getMaNV(),
                    b.getMaBan(),
                    b.getHoTenKH(),
                    b.getTrangThai()};
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "lỗi truy vẫn dữ liệu");
            System.out.println(e);
        }
    }

    void insertHD() {
        HoaDon model = getFormHD();
        try {
            daoHD.insert(model);
            this.fillTableHD();
            clearHD();
            MsgBox.alert(this, "Thêm mới thành công!");
        } catch (Exception e) {
            MsgBox.alert(this, "Thêm mới thất bại!");
            System.out.println(e);
        }
    }

    private void btnThemHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemHDActionPerformed
        insertHD();
    }//GEN-LAST:event_btnThemHDActionPerformed

    void deleteHD() {
        if (txtMaHD.getText().equals("")) {
            MsgBox.alert(this, "ban phải nhập MaHD");
        } else if (MsgBox.confirm(this, "bạn thục sự muốn xóa hóa đơn này")) {
            String maBan = txtMaHD.getText();
            try {
                daoHD.delete(maBan);
                this.fillTableHD();
                this.clearHD();
                MsgBox.alert(this, "xóa thành công");
            } catch (Exception e) {
                MsgBox.alert(this, "xóa thất bại");
                System.out.println(e);
            }
        }
    }


    private void btnXoaHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaHDActionPerformed
        deleteHD();
    }//GEN-LAST:event_btnXoaHDActionPerformed

    private void tblThemSpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThemSpMouseClicked
        if (evt.getClickCount() == 1) {
            this.row = tblThemSp.rowAtPoint(evt.getPoint());
            edit();
            txtSoluong.setText("");
        }
    }//GEN-LAST:event_tblThemSpMouseClicked

    private void txtMaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaNVActionPerformed

    private void tblChiTietSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChiTietSPMouseClicked
        if (evt.getClickCount() == 1) {
            this.row = tblChiTietSP.rowAtPoint(evt.getPoint());
            editChiTiet();
        }
    }//GEN-LAST:event_tblChiTietSPMouseClicked

    private void cboKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboKHActionPerformed

        KhachHang selectedKhachHang = (KhachHang) cboKH.getSelectedItem();

        // Kiểm tra xem selectedKhachHang có khác null hay không
        if (selectedKhachHang != null) {
            // Hiển thị MaHK và SDT tương ứng lên các JLabel
            txtMaKH.setText(selectedKhachHang.getMaKH());
            txtSDT.setText(selectedKhachHang.getSDT());
        }
    }//GEN-LAST:event_cboKHActionPerformed

    private void cboBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboBanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboBanActionPerformed

    private void cboKHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboKHMouseClicked
//        fillComboBoxKH();
    }//GEN-LAST:event_cboKHMouseClicked
    void fillDonGia(String maHD) {
        float tongDonGia = daoHD.getTongDonGiaByMaHD(maHD);
        txtTongTien.setText(String.valueOf(tongDonGia));
    }


    private void tblHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDMouseClicked
        if (evt.getClickCount() == 1) {
            int selectedRow = tblHD.getSelectedRow();
            if (selectedRow >= 0) {
                // Lấy mã HD từ dòng được chọn
                String maHD = tblHD.getValueAt(selectedRow, 0).toString();

                // Lấy thông tin hóa đơn từ mã HD
                HoaDon hoaDon = daoHD.selectById(maHD);

                if (hoaDon != null) {
                    txtMaHD.setText(maHD);
                    fillDonGia(maHD);
                    txtTienDu.setText("");
                    txtThanhToan.setText("");
                    txtMaKH.setText("");
                    txtSDT.setText("");
                }
            }
        }
    }//GEN-LAST:event_tblHDMouseClicked

    HoaDon getTenSPGia() {
        HoaDon model = new HoaDon();
        boolean isValid = true; // Biến để kiểm tra tính hợp lệ của dữ liệu

        model.setMaHD(txtMaHD.getText());

        Map<String, Integer> mapTenSPSoLuong = new HashMap<>();
        float gia = 0;

        for (int i = 0; i < tblChiTietSP.getRowCount(); i++) {
            String maHDChiTiet = tblChiTietSP.getValueAt(i, 1).toString();
            if (model.getMaHD().equals(maHDChiTiet)) {
                // Lấy thông tin sản phẩm và số lượng theo MaHD
                String tenSP = tblChiTietSP.getValueAt(i, 3).toString();
                int soLuong = Integer.parseInt(tblChiTietSP.getValueAt(i, 5).toString());
                float donGia = Float.parseFloat(tblChiTietSP.getValueAt(i, 6).toString());

                // Cập nhật Map
                mapTenSPSoLuong.put(tenSP, soLuong);

                // Cập nhật tổng giá
                gia += donGia;
            }
        }

        // Gán thông tin từ Map vào model
        model.setTenpSP(mapTenSPSoLuong.keySet().stream().collect(Collectors.joining(", ")));
        model.setTongSL(mapTenSPSoLuong.values().stream().mapToInt(Integer::intValue).sum());
        model.setTongTien(gia);
        model.setTrangThai(cboTrangThai.getSelectedItem().toString());
        if (isValid) {
            return model;
        } else {
            return null; // Trả về null nếu dữ liệu không hợp lệ
        }

    }

    void thanhToan() {
        String maHD = txtMaHD.getText();

        ChiTietSP ttSP = daoCTSP.getThongTinTenSPVaTongTien(maHD);
        if (ttSP != null) {
            daoHD.updateTenSPVaTongTien(maHD, ttSP.getTenMuoc(), ttSP.getDonGia());
        }
    }

    void deleteAll(String maHD) {

        try {
            daoCTSP.deleteAll(maHD);
            this.fillTableChiTiet();
            this.clear();
            MsgBox.alert(this, "Đã xóa tất cả nước");
        } catch (Exception e) {
            MsgBox.alert(this, "xóa thất bại");
        }

    }

    boolean updateHD() {
        HoaDon model = getTenSPGia();
        try {
            daoHD.update(model);
            this.fillTableHD();
            clearHD();
            MsgBox.alert(this, "Thanh toán thành công!");
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        String maHD = txtMaHD.getText();

        if (updateTongDonGia(maHD)) {
            if (updateHD()) {
                deleteAll(maHD);
            } else {
                MsgBox.alert(this, "Thất bại khi cập nhật hóa đơn!");
            }
        }
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void txtTongTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTongTienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTongTienActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThemHD;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaHD;
    private javax.swing.JComboBox<String> cboBan;
    private javax.swing.JComboBox<String> cboKH;
    private javax.swing.JComboBox<String> cboTrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tblChiTietSP;
    private javax.swing.JTable tblHD;
    private javax.swing.JTable tblThemSp;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtLoai;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextField txtNgayMua;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtSoluong;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtThanhToan;
    private javax.swing.JTextField txtTienDu;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables
}
