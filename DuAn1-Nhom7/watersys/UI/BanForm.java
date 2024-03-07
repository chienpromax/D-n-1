/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package watersys.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import watersys.DAO.ChiTietSPDAO;
import watersys.DAO.BanDAO;
import watersys.DAO.HoaDonDAO;
import watersys.DAO.KhachHangDAO;
import watersys.DAO.SanPhamDAO;
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
public class BanForm extends javax.swing.JInternalFrame {

    BanDAO dao = new BanDAO();
    HoaDonDAO daoHD = new HoaDonDAO();
    KhachHangDAO daoKH = new KhachHangDAO();
    ChiTietSPDAO daoCTSP = new ChiTietSPDAO();
    SanPhamDAO DaoSP = new SanPhamDAO();
    private Properties config;
    int row = 0;

    private List<JLabel> lblBanList;

    public BanForm() {
        this.setComponentPopupMenu(null);
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);

        fillTableSP();
        fillTableChiTiet();
        fillTableHD();
        fillComboBoxKH();

        txtMaNV.setText(Auth.user.getMaNV());
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Định dạng ngày theo ý muốn
        String formattedDate = dateFormat.format(currentDate);
        txtNgayMua.setText(formattedDate);

        initLblTTBanArray();
        for (int i = 0; i < lblTTBanArray.length; i++) {
            String maBan = "Ban" + (i + 1);
            updateMauSac(maBan);
        }
    }

    void fillComboBoxKH() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboKH.getModel();
        model.removeAllElements();
        List<KhachHang> list = daoKH.selectAll();
        for (KhachHang kh : list) {
            model.addElement(kh);
        }
    }

    void clearHD() {
        txtMaHD.setText("");
        txtMaKH.setText("");
        txtSDT.setText("");
        txtMaBan.setText("");
        fillComboBoxKH();

        txtMaNV.setText(Auth.user.getMaNV());
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Định dạng ngày theo ý muốn
        String formattedDate = dateFormat.format(currentDate);
        txtNgayMua.setText(formattedDate);
        this.row = -1;

        for (int i = 0; i < lblTTBanArray.length; i++) {
            String maBan = "Ban" + (i + 1);
            updateMauSac(maBan);
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
                DaoSP.updateSoLuong(txtMaSP.getText(), soLuong);

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
            List<SanPham> list = DaoSP.selectAll();
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
        try {
            DefaultTableModel model = (DefaultTableModel) tblChiTietSP.getModel();
            model.setRowCount(0);
            List<Object[]> list = daoCTSP.getChiTietSP();
            for (Object[] row : list) {
                model.addRow(new Object[]{row[0], row[1], row[2], row[3], row[4], row[5], row[6]});
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Lỗi", ex);
        }
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
        model.setMaBan(txtMaBan.getText());
        model.setTrangThai(cboTrangThai.getSelectedItem().toString());

        if (isValid) {
            return model;
        } else {
            return null; // Trả về null nếu dữ liệu không hợp lệ
        }
    }

    void fillTableHD() {
        DefaultTableModel model = (DefaultTableModel) tblHD.getModel();
        model.setRowCount(0);
            List<HoaDon> list = daoHD.selectAllTT();
            for (HoaDon b : list) {
                Object[] row = {
                    b.getMaHD(),
                    b.getNgayTao(),
                    b.getTenpSP(),
                    b.getMaNV(),
                    b.getMaBan(),
                    b.getTrangThai()};
                model.addRow(row);
            }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblBan1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblBan2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblBan3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblBan4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblBan6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblBan7 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblBan8 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lblBan9 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        lblBan5 = new javax.swing.JLabel();
        lblTTBan1 = new javax.swing.JPanel();
        lblTTBan2 = new javax.swing.JPanel();
        lblTTBan3 = new javax.swing.JPanel();
        lblTTBan4 = new javax.swing.JPanel();
        lblTTBan5 = new javax.swing.JPanel();
        lblTTBan6 = new javax.swing.JPanel();
        lblTTBan7 = new javax.swing.JPanel();
        lblTTBan8 = new javax.swing.JPanel();
        lblTTBan9 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblChiTietBan = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        txtTenSP = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txtDonGia = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txtSoluong = new javax.swing.JTextField();
        txtMaSP = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        txtLoai = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblThemSp = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblChiTietSP = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        txtMaKH = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtNgayMua = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        cboKH = new javax.swing.JComboBox<>();
        txtMaBan = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblHD = new javax.swing.JTable();
        jLabel34 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        btnThanhToan = new javax.swing.JButton();
        txtTienDu = new javax.swing.JTextField();
        txtTongTien = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        txtThanhToan = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        cboTrangThai = new javax.swing.JComboBox<>();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        btnThemHD = new javax.swing.JButton();
        btnXoaHD = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(102, 255, 204));
        setOpaque(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        lblBan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/watersys/Icon/dinner-table (1).png"))); // NOI18N
        lblBan1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblBan1MousePressed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 204));
        jLabel2.setText("Bàn 1");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 204));
        jLabel3.setText("Bàn 2");

        lblBan2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/watersys/Icon/dinner-table (1).png"))); // NOI18N
        lblBan2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblBan2MousePressed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 204));
        jLabel5.setText("Bàn 3");

        lblBan3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/watersys/Icon/dinner-table (1).png"))); // NOI18N
        lblBan3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblBan3MousePressed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 204));
        jLabel7.setText("Bàn 4");

        lblBan4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/watersys/Icon/dinner-table (1).png"))); // NOI18N
        lblBan4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblBan4MousePressed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 204));
        jLabel9.setText("Bàn 6");

        lblBan6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/watersys/Icon/dinner-table (1).png"))); // NOI18N
        lblBan6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblBan6MousePressed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 51, 204));
        jLabel11.setText("Bàn 7");

        lblBan7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/watersys/Icon/dinner-table (1).png"))); // NOI18N
        lblBan7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblBan7MousePressed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 51, 204));
        jLabel13.setText("Bàn 8");

        lblBan8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/watersys/Icon/dinner-table (1).png"))); // NOI18N
        lblBan8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblBan8MousePressed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 51, 204));
        jLabel15.setText("Bàn 9");

        lblBan9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/watersys/Icon/dinner-table (1).png"))); // NOI18N
        lblBan9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblBan9MousePressed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 204));
        jLabel22.setText("Bàn 5");

        lblBan5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/watersys/Icon/dinner-table (1).png"))); // NOI18N
        lblBan5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblBan5MousePressed(evt);
            }
        });

        lblTTBan1.setBackground(new java.awt.Color(51, 0, 255));

        javax.swing.GroupLayout lblTTBan1Layout = new javax.swing.GroupLayout(lblTTBan1);
        lblTTBan1.setLayout(lblTTBan1Layout);
        lblTTBan1Layout.setHorizontalGroup(
            lblTTBan1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 31, Short.MAX_VALUE)
        );
        lblTTBan1Layout.setVerticalGroup(
            lblTTBan1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 19, Short.MAX_VALUE)
        );

        lblTTBan2.setBackground(new java.awt.Color(51, 0, 255));

        javax.swing.GroupLayout lblTTBan2Layout = new javax.swing.GroupLayout(lblTTBan2);
        lblTTBan2.setLayout(lblTTBan2Layout);
        lblTTBan2Layout.setHorizontalGroup(
            lblTTBan2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        lblTTBan2Layout.setVerticalGroup(
            lblTTBan2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 19, Short.MAX_VALUE)
        );

        lblTTBan3.setBackground(new java.awt.Color(51, 0, 255));

        javax.swing.GroupLayout lblTTBan3Layout = new javax.swing.GroupLayout(lblTTBan3);
        lblTTBan3.setLayout(lblTTBan3Layout);
        lblTTBan3Layout.setHorizontalGroup(
            lblTTBan3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );
        lblTTBan3Layout.setVerticalGroup(
            lblTTBan3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 19, Short.MAX_VALUE)
        );

        lblTTBan4.setBackground(new java.awt.Color(51, 0, 255));

        javax.swing.GroupLayout lblTTBan4Layout = new javax.swing.GroupLayout(lblTTBan4);
        lblTTBan4.setLayout(lblTTBan4Layout);
        lblTTBan4Layout.setHorizontalGroup(
            lblTTBan4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 31, Short.MAX_VALUE)
        );
        lblTTBan4Layout.setVerticalGroup(
            lblTTBan4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        lblTTBan5.setBackground(new java.awt.Color(51, 0, 255));

        javax.swing.GroupLayout lblTTBan5Layout = new javax.swing.GroupLayout(lblTTBan5);
        lblTTBan5.setLayout(lblTTBan5Layout);
        lblTTBan5Layout.setHorizontalGroup(
            lblTTBan5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 31, Short.MAX_VALUE)
        );
        lblTTBan5Layout.setVerticalGroup(
            lblTTBan5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        lblTTBan6.setBackground(new java.awt.Color(51, 0, 255));

        javax.swing.GroupLayout lblTTBan6Layout = new javax.swing.GroupLayout(lblTTBan6);
        lblTTBan6.setLayout(lblTTBan6Layout);
        lblTTBan6Layout.setHorizontalGroup(
            lblTTBan6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        lblTTBan6Layout.setVerticalGroup(
            lblTTBan6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 19, Short.MAX_VALUE)
        );

        lblTTBan7.setBackground(new java.awt.Color(51, 0, 255));

        javax.swing.GroupLayout lblTTBan7Layout = new javax.swing.GroupLayout(lblTTBan7);
        lblTTBan7.setLayout(lblTTBan7Layout);
        lblTTBan7Layout.setHorizontalGroup(
            lblTTBan7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );
        lblTTBan7Layout.setVerticalGroup(
            lblTTBan7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 19, Short.MAX_VALUE)
        );

        lblTTBan8.setBackground(new java.awt.Color(51, 0, 255));

        javax.swing.GroupLayout lblTTBan8Layout = new javax.swing.GroupLayout(lblTTBan8);
        lblTTBan8.setLayout(lblTTBan8Layout);
        lblTTBan8Layout.setHorizontalGroup(
            lblTTBan8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );
        lblTTBan8Layout.setVerticalGroup(
            lblTTBan8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 19, Short.MAX_VALUE)
        );

        lblTTBan9.setBackground(new java.awt.Color(51, 0, 255));

        javax.swing.GroupLayout lblTTBan9Layout = new javax.swing.GroupLayout(lblTTBan9);
        lblTTBan9.setLayout(lblTTBan9Layout);
        lblTTBan9Layout.setHorizontalGroup(
            lblTTBan9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );
        lblTTBan9Layout.setVerticalGroup(
            lblTTBan9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 19, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 204));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nước trong bàn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(0, 51, 204))); // NOI18N
        jPanel5.setForeground(new java.awt.Color(0, 51, 204));

        tblChiTietBan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "MaHD", "TenNuoc", "Loai", "SoLuong", "Đơn giá"
            }
        ));
        tblChiTietBan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChiTietBanMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblChiTietBan);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(255, 204, 204));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(0, 51, 153))); // NOI18N

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 0));
        jLabel26.setText("Đơn giá:");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 0, 0));
        jLabel27.setText("Tên sản phẩm:");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 0));
        jLabel28.setText("Loại SP");

        jLabel29.setBackground(new java.awt.Color(0, 0, 0));
        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 0));
        jLabel29.setText("Mã sản phẩm:");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 0, 0));
        jLabel30.setText("Số lượng");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29)
                            .addComponent(jLabel28))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtTenSP, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                            .addComponent(txtMaSP, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtLoai))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSoluong, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                    .addComponent(txtDonGia))
                .addGap(226, 226, 226))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26)
                    .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30))
                        .addGap(13, 13, 13)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(txtLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSoluong, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        jScrollPane5.setViewportView(tblChiTietSP);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 153));
        jLabel1.setText("Chọn Sản phẩm thêm vào hóa đơn chi tiết");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 153));
        jLabel4.setText("Chi tiêt hóa đơn");

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

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(57, 57, 57))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(157, 157, 157)
                        .addComponent(jLabel4))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(3, 3, 3)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        jPanel10.setBackground(new java.awt.Color(255, 153, 102));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(0, 51, 153))); // NOI18N

        jLabel31.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 0, 0));
        jLabel31.setText("MaHD");

        jLabel32.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 0, 0));
        jLabel32.setText("Ngày mua");

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

        jLabel33.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 0, 0));
        jLabel33.setText("SDT");

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

        txtMaBan.setEnabled(false);
        txtMaBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaBanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel31)))
                        .addGap(43, 43, 43)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtNgayMua, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                    .addComponent(txtMaHD, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaNV)
                    .addComponent(txtMaBan, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel33))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaKH)
                            .addComponent(txtSDT)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboKH, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(cboKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel33)
                                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel14)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31)
                            .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel32)
                            .addComponent(txtNgayMua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(txtMaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 51, 153));
        jLabel34.setText("Hóa đơn");

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

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(0, 0, 0));
        jLabel35.setText("Tiền dư");

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(0, 0, 0));
        jLabel36.setText("Tiền thanh toán");

        jLabel37.setBackground(new java.awt.Color(0, 0, 0));
        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(0, 0, 0));
        jLabel37.setText("tổng tiền");

        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chưa thanh toán", "Đã thanh toán" }));

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(0, 0, 0));
        jLabel38.setText("Trạng thái");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addComponent(jLabel37)
                                .addGap(18, 18, 18)
                                .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel36)
                                .addGap(18, 18, 18)
                                .addComponent(txtThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel35, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboTrangThai, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTienDu))
                .addGap(0, 16, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel35)
                            .addComponent(txtTienDu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel38)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel37)
                            .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(txtThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel36)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(0, 51, 153));
        jLabel39.setText("Thanh toán");

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

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel39)
                .addGap(190, 190, 190))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                .addComponent(btnThemHD)
                                .addGap(18, 18, 18)
                                .addComponent(btnXoaHD)
                                .addGap(75, 75, 75))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel34)
                                .addGap(205, 205, 205))))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemHD, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        jPanel12.setBackground(new java.awt.Color(204, 204, 204));

        jPanel4.setBackground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 204));
        jLabel20.setText("Đang dùng");

        jPanel2.setBackground(new java.awt.Color(0, 0, 204));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 23, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 204));
        jLabel19.setText("Trống");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel19)
                .addGap(12, 12, 12)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addGap(40, 40, 40))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblBan4)
                                            .addComponent(lblBan7)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(13, 13, 13)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel7)
                                                    .addComponent(lblTTBan1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGap(30, 30, 30)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblBan5)
                                            .addComponent(lblBan8)
                                            .addComponent(lblBan2))
                                        .addGap(25, 25, 25)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblBan9)
                                    .addComponent(lblBan6)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addGap(16, 16, 16))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(lblTTBan7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(61, 61, 61)
                                .addComponent(lblTTBan8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(61, 61, 61)
                                .addComponent(lblTTBan9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel11))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(lblBan1)
                                        .addGap(120, 120, 120))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(15, 15, 15)
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel3)
                                        .addGap(39, 39, 39)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblBan3)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(11, 11, 11)
                                        .addComponent(jLabel5))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(105, 105, 105)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel22)
                                    .addComponent(lblTTBan2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(60, 60, 60)
                                        .addComponent(lblTTBan3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGap(51, 51, 51)
                                        .addComponent(jLabel9))))
                            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(lblTTBan4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(63, 63, 63)
                                .addComponent(lblTTBan5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(58, 58, 58)
                                .addComponent(lblTTBan6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblBan3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(34, 34, 34)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(29, 29, 29)
                                                .addComponent(lblBan2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(jLabel3)
                                                    .addComponent(jLabel2))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblBan1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTTBan2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblTTBan3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblTTBan1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(24, 24, 24)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblBan4, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblBan5, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblBan6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(241, 241, 241)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblTTBan4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblTTBan5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblTTBan6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblBan8, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblBan9, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                                .addComponent(lblBan7, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTTBan8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTTBan7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTTBan9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 78, Short.MAX_VALUE))
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

    private void tblChiTietBanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChiTietBanMouseClicked

    }//GEN-LAST:event_tblChiTietBanMouseClicked

    private void lblBan5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBan5MousePressed
        lblBanMousePressed(evt, "Ban5");
        txtMaBan.setText("Ban5");
    }//GEN-LAST:event_lblBan5MousePressed

// Phương thức được gọi khi form ChiTietSPFrom đóng
    private void onChiTietSPFormClosed() {
        for (int i = 0; i < lblTTBanArray.length; i++) {
            String maBan = "Ban" + (i + 1);
            updateMauSac(maBan);
        }
    }
    private JPanel[] lblTTBanArray = new JPanel[9];

    private void initLblTTBanArray() {
        lblTTBanArray[0] = lblTTBan1;
        lblTTBanArray[1] = lblTTBan2;
        lblTTBanArray[2] = lblTTBan3;
        lblTTBanArray[3] = lblTTBan4;
        lblTTBanArray[4] = lblTTBan5;
        lblTTBanArray[5] = lblTTBan6;
        lblTTBanArray[6] = lblTTBan7;
        lblTTBanArray[7] = lblTTBan8;
        lblTTBanArray[8] = lblTTBan9;
    }

    private void updateMauSac(String maBan) {
        int index = Integer.parseInt(maBan.substring(3)) - 1; // Chuyển đổi maBan thành chỉ số mảng

        // Kiểm tra nếu bàn có nước
        List<Object[]> list = dao.getHoaDonByMaBan(maBan);
        if (!list.isEmpty()) {
            lblTTBanArray[index].setBackground(Color.RED); // Đặt màu đỏ
        } else {
            lblTTBanArray[index].setBackground(Color.BLUE); // Đặt màu xanh
        }
        // Yêu cầu vẽ lại
        lblTTBanArray[index].repaint();
    }

    private void lblBan9MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBan9MousePressed
        lblBanMousePressed(evt, "Ban9");
        txtMaBan.setText("Ban9");
        txtMaHD.setText("");
    }//GEN-LAST:event_lblBan9MousePressed

    private void lblBan8MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBan8MousePressed
        lblBanMousePressed(evt, "Ban8");
        txtMaBan.setText("Ban8");
        txtMaHD.setText("");
    }//GEN-LAST:event_lblBan8MousePressed

    private void lblBan7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBan7MousePressed
        lblBanMousePressed(evt, "Ban7");
        txtMaBan.setText("Ban7");
        txtMaHD.setText("");
    }//GEN-LAST:event_lblBan7MousePressed

    private void lblBan6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBan6MousePressed
        lblBanMousePressed(evt, "Ban6");
        txtMaBan.setText("Ban6");
        txtMaHD.setText("");
    }//GEN-LAST:event_lblBan6MousePressed

    private void lblBan4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBan4MousePressed
        lblBanMousePressed(evt, "Ban4");
        txtMaBan.setText("Ban4");
        txtMaHD.setText("");
    }//GEN-LAST:event_lblBan4MousePressed

    private void lblBan3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBan3MousePressed
        lblBanMousePressed(evt, "Ban3");
        txtMaBan.setText("Ban3");
        txtMaHD.setText("");
    }//GEN-LAST:event_lblBan3MousePressed

    private void lblBan2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBan2MousePressed
        lblBanMousePressed(evt, "Ban2");
        txtMaBan.setText("Ban2");
        txtMaHD.setText("");
    }//GEN-LAST:event_lblBan2MousePressed

    private void lblBan1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBan1MousePressed
        lblBanMousePressed(evt, "Ban1");
        txtMaBan.setText("Ban1");
        txtMaHD.setText("");
    }//GEN-LAST:event_lblBan1MousePressed

    void edit() {
        String maSP = (String) tblThemSp.getValueAt(this.row, 0);
        SanPham sp = DaoSP.selectById(maSP);
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

    private void txtMaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaNVActionPerformed

    private void cboKHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboKHMouseClicked
        //        fillComboBoxKH();
    }//GEN-LAST:event_cboKHMouseClicked

    private void cboKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboKHActionPerformed

        KhachHang selectedKhachHang = (KhachHang) cboKH.getSelectedItem();

        // Kiểm tra xem selectedKhachHang có khác null hay không
        if (selectedKhachHang != null) {
            // Hiển thị MaHK và SDT tương ứng lên các JLabel
            txtMaKH.setText(selectedKhachHang.getMaKH());
            txtSDT.setText(selectedKhachHang.getSDT());
        }
    }//GEN-LAST:event_cboKHActionPerformed

    private void txtMaBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaBanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaBanActionPerformed

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

    boolean updateHD() {
        HoaDon model = getTenSPGia();
        try {
            daoHD.update(model);
            this.fillTableHD();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

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

    void deleteAll(String maHD) {

        try {
            daoCTSP.deleteAll(maHD);
            this.fillTableChiTiet();
        } catch (Exception e) {
            MsgBox.alert(this, "xóa thất bại");
        }

    }

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        String maHD = txtMaHD.getText();
        String tongTien = txtTongTien.getText(); // Assuming txtTongTien is a JTextField or similar

        // Check if maHD and tongTien are not empty
        if (maHD.isEmpty() && tongTien.isEmpty()) {
            MsgBox.alert(this, "Mã hóa đơn và Tổng tiền không được để trống");
        } else {
            if (updateTongDonGia(maHD)) {
                if (updateHD()) {
                    deleteAll(maHD);
                    if (MsgBox.confirm(this, "Bạn có muốn in hóa đơn không?")) {
                        inHoaDon();
                    } else {
                        System.out.println("kết thúc");
                    }
                    clearHD();
                } else {
                    MsgBox.alert(this, "Thanh Toán thất bại");
                }
            }
            for (int i = 0; i < lblTTBanArray.length; i++) {
                String maBan = "Ban" + (i + 1);
                updateMauSac(maBan);
            }
        }
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void txtTongTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTongTienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTongTienActionPerformed

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
        for (int i = 0; i < lblTTBanArray.length; i++) {
            String maBan = "Ban" + (i + 1);
            updateMauSac(maBan);
        }
    }//GEN-LAST:event_btnThemActionPerformed

    void delete() {
        int selectedRow = tblChiTietSP.getSelectedRow();

        if (selectedRow >= 0) {
            try {
                // Lấy giá trị từ cột đầu tiên
                Object objMaSP = tblChiTietSP.getValueAt(selectedRow, 0);

                // Chuyển đổi giá trị sang kiểu int
                int maSP = (int) objMaSP;

                if (MsgBox.confirm(this, "Bạn thực sự muốn xóa sản phẩm này?")) {
                    try {
                        // Lấy thông tin chi tiết sản phẩm trước khi xóa
                        ChiTietSP chiTietSP = daoCTSP.selectById(maSP);

                        // Cộng lại số lượng sản phẩm
                        int soLuongHienTai = chiTietSP.getSoLuong();
                        DaoSP.updateXoaSoLuong(chiTietSP.getMaSP(), soLuongHienTai);

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
        for (int i = 0; i < lblTTBanArray.length; i++) {
            String maBan = "Ban" + (i + 1);
            updateMauSac(maBan);
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    void clear() {
        txtDonGia.setText("");
        txtLoai.setText("");
        txtMaSP.setText("");
        txtTenSP.setText("");
        txtSoluong.setText("");
        txtMaHD.setText("");
        fillComboBoxKH();
        txtMaBan.setText("");
        txtTongTien.setText("");
        txtThanhToan.setText("");
        txtTienDu.setText("");
        cboTrangThai.setSelectedIndex(0);
        this.row = -1;
    }
    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clear();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnThemHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemHDActionPerformed
        insertHD();
    }//GEN-LAST:event_btnThemHDActionPerformed

    private void btnXoaHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaHDActionPerformed
        deleteHD();
    }//GEN-LAST:event_btnXoaHDActionPerformed

    private Date NgayMua;
    private Float TongTien;
    Double bHeight = 0.0;

    ArrayList<String> maHD = new ArrayList<>();
    ArrayList<String> hoTenKH = new ArrayList<>();
    ArrayList<String> tenSP = new ArrayList<>();
    ArrayList<String> maNV = new ArrayList<>();
    ArrayList<String> maKH = new ArrayList<>();
    ArrayList<String> maBan = new ArrayList<>();

    public class BillPrintable implements Printable {

        private List<Object[]> billData;

        public BillPrintable(List<Object[]> billData) {
            this.billData = billData;
        }

        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
                throws PrinterException {
            int r = maHD.size();
//            ImageIcon icon = new ImageIcon("E:\\hoc\\kì 4\\Du_an_1_(UDPM-Java)\\WaterSys\\src\\watersys\\Icon\\yea.jpg");
            int result = NO_SUCH_PAGE;
            if (pageIndex == 0) {
                Graphics2D g2d = (Graphics2D) graphics;
                double width = pageFormat.getImageableWidth();
                g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());

                try {
                    int y = 20;
                    int yShift = 10;
                    int headerRectHeight = 15;
                    g2d.setFont(new Font("Monospaced", Font.PLAIN, 12));
//                    g2d.drawImage(icon.getImage(), 70, 60, 20, 20, rootPane);
                    y += yShift + 30;
                    g2d.drawString("--------------------------------------", 10, y);
                    y += yShift;
                    g2d.drawString("            shopchienpromax           ", 10, y);
                    y += yShift;
                    g2d.drawString("             SDT 0359690062           ", 10, y);
                    y += yShift;
                    g2d.drawString("--------------------------------------", 10, y);
                    y += headerRectHeight;
                    g2d.drawString("--------------------------------------", 10, y);
                    y += yShift;
                    g2d.drawString("Mã hóa đơn: " + billData.get(0)[0] + "  ", 10, y);
                    y += yShift;
                    g2d.drawString("                                      ", 10, y);
                    y += yShift;
                    g2d.drawString("Tên khách hàng: " + billData.get(0)[1] + "  ", 10, y);
                    y += yShift;
                    g2d.drawString("                                      ", 10, y);
                    y += yShift;
                    g2d.drawString("Ngày mua: " + billData.get(0)[2] + "  ", 10, y);
                    y += yShift;
                    g2d.drawString("                                      ", 10, y);
                    y += yShift;
                    g2d.drawString("Tên Sản phẩm: " + billData.get(0)[3] + "  ", 10, y);
                    y += yShift;
                    g2d.drawString("                                      ", 10, y);
                    y += yShift;
                    g2d.drawString("Tổng số lượng: " + billData.get(0)[4] + "  ", 10, y);
                    y += yShift;
                    g2d.drawString("                                      ", 10, y);
                    y += yShift;
                    g2d.drawString("Tổng tiền: " + billData.get(0)[5] + "  ", 10, y);
                    y += yShift;
                    g2d.drawString("                                      ", 10, y);
                    y += yShift;
                    g2d.drawString("Bàn: " + billData.get(0)[6] + "  ", 10, y);
                    y += yShift;
                    g2d.drawString("--------------------------------------", 10, y);
                    y += yShift;
                    g2d.drawString("**************************************", 10, y);
                    y += yShift;
                    g2d.drawString("        Cảm ơn bạn đã mua hàng        ", 10, y);
                    y += yShift;
                    g2d.drawString("**************************************", 10, y);
                    y += yShift;
                    g2d.drawString("         Hãy ghé lại lần sau          ", 10, y);
                    y += yShift;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                result = PAGE_EXISTS;
            }
            return result;
        }
    }

    public void inHoaDon() {
        if (txtMaHD.getText().equals("")) {
            MsgBox.alert(this, "Hãy chọn hóa đơn");
        } else {
            // Lấy thông tin hóa đơn từ CSDL
            List<Object[]> result = daoHD.getXuatHoaDon(txtMaHD.getText());

            // Kiểm tra xem có dữ liệu hay không
            if (result.isEmpty()) {
                MsgBox.alert(this, "Không tìm thấy hóa đơn");
                return;
            }

            // Lấy thông tin từ dòng đầu tiên của danh sách (giả sử chỉ có một hóa đơn)
            Object[] firstRow = result.get(0);

            // Gán thông tin hóa đơn vào biến
            maHD.add(firstRow[0].toString());
            hoTenKH.add(firstRow[1].toString());
            tenSP.add(firstRow[3].toString());
            TongTien = Float.parseFloat(firstRow[5].toString());

            // Tạo đối tượng BillPrintable và truyền dữ liệu vào
            BillPrintable billPrintable = new BillPrintable(result);

            // Xử lý in hóa đơn
            PrinterJob pj = PrinterJob.getPrinterJob();
            try {
                pj.setPrintable(billPrintable, getPageFormat(pj));
                pj.print();
                MsgBox.alert(this, "In hóa đơn thành công");
            } catch (PrinterException ex) {
                ex.printStackTrace();
                MsgBox.alert(this, "Lỗi khi in hóa đơn");
            }
        }
    }

    public PageFormat getPageFormat(PrinterJob pj) {
        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();
        double bodyHeight = bHeight;
        double headerHeight = 30.0;
        double footerHeight = 30.0;
        double width = cm_to_pp(20);//chiều rộng giấy
        double height = cm_to_pp(headerHeight + bodyHeight);
        paper.setSize(width, height);
        paper.setImageableArea(0, 10, width, height - cm_to_pp(1));
        pf.setOrientation(PageFormat.PORTRAIT);
        pf.setPaper(paper);
        return pf;
    }

    protected static double cm_to_pp(double cm) {
        return toPPI(cm * 0.393600787);
    }

    protected static double toPPI(double inch) {
        return inch * 72d;
    }

    private void lblBanMousePressed(java.awt.event.MouseEvent evt, String maBan) {
        if (evt.getClickCount() == 1) {
            try {
                DefaultTableModel model = (DefaultTableModel) tblChiTietBan.getModel();
                model.setRowCount(0);
                List<Object[]> list = dao.getHoaDonByMaBan(maBan);
                for (Object[] row : list) {
                    model.addRow(new Object[]{row[0], row[1], row[2], row[3], row[4], row[5]});
                }
                updateMauSac(maBan);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException("Lỗi", ex);
            }
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThemHD;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaHD;
    private javax.swing.JComboBox<String> cboKH;
    private javax.swing.JComboBox<String> cboTrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblBan1;
    private javax.swing.JLabel lblBan2;
    private javax.swing.JLabel lblBan3;
    private javax.swing.JLabel lblBan4;
    private javax.swing.JLabel lblBan5;
    private javax.swing.JLabel lblBan6;
    private javax.swing.JLabel lblBan7;
    private javax.swing.JLabel lblBan8;
    private javax.swing.JLabel lblBan9;
    private javax.swing.JPanel lblTTBan1;
    private javax.swing.JPanel lblTTBan2;
    private javax.swing.JPanel lblTTBan3;
    private javax.swing.JPanel lblTTBan4;
    private javax.swing.JPanel lblTTBan5;
    private javax.swing.JPanel lblTTBan6;
    private javax.swing.JPanel lblTTBan7;
    private javax.swing.JPanel lblTTBan8;
    private javax.swing.JPanel lblTTBan9;
    private javax.swing.JTable tblChiTietBan;
    private javax.swing.JTable tblChiTietSP;
    private javax.swing.JTable tblHD;
    private javax.swing.JTable tblThemSp;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtLoai;
    private javax.swing.JTextField txtMaBan;
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
