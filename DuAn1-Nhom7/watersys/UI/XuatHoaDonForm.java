/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package watersys.UI;

import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import watersys.Utilities.MsgBox;
import javax.swing.table.DefaultTableModel;
import watersys.DAO.HoaDonDAO;
import watersys.DAO.ThongKeDAO;
import watersys.Entity.HoaDon;

/**
 *
 * @author xuanc
 */
public class XuatHoaDonForm extends javax.swing.JInternalFrame {

    HoaDonDAO daoHD = new HoaDonDAO();
    ThongKeDAO daoTK = new ThongKeDAO();
    int row = 0;

    Double bHeight = 0.0;

    private Date NgayMua;
    private Float TongTien;

    ArrayList<String> maHD = new ArrayList<>();
    ArrayList<String> hoTenKH = new ArrayList<>();
    ArrayList<String> tenSP = new ArrayList<>();
    ArrayList<String> maNV = new ArrayList<>();
    ArrayList<String> maKH = new ArrayList<>();
    ArrayList<String> maBan = new ArrayList<>();

    public XuatHoaDonForm() {
        initComponents();
        this.setComponentPopupMenu(null);
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        txtNgayTao.setLocale(new Locale("vi", "VN")); // Đặt locale thành tiếng Việt

        // Tùy chỉnh định dạng ngày hiển thị (nếu cần)
        JTextFieldDateEditor editor = (JTextFieldDateEditor) txtNgayTao.getDateEditor();
        editor.setDateFormatString("dd/MM/yyyy");
        fillTableHD();
    }

    void fillTableTT() {
        String trangThai = String.valueOf(cboTrangThai.getSelectedItem());
        if (trangThai.equals("Tất cả")) {
            fillTableHD();
        } else {
            try {
                DefaultTableModel model = (DefaultTableModel) tblHD.getModel();
                model.setRowCount(0);
                List<Object[]> list = daoTK.getTrangThai(trangThai);
                for (Object[] row : list) {
                    model.addRow(new Object[]{row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7], row[8], row[9]});
                }
            } catch (Exception e) {
                throw new RuntimeException("Lỗi", e);
            }
        }
    }

    void fillTableTK() {
        Date ngayBD = txtNgayTao.getDate();
        if (ngayBD == null) {
            fillTableHD();
        } else {
            try {
                DefaultTableModel model = (DefaultTableModel) tblHD.getModel();
                model.setRowCount(0);

                List<Object[]> list = daoTK.getHoaDon(ngayBD);

                for (Object[] row : list) {
                    model.addRow(new Object[]{row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7], row[8], row[9]});
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Lỗi", e);
            }
        }
    }

    void setForm(HoaDon model) {
        txtMaHD.setText(model.getMaHD());
        txtTenKH.setText(model.getHoTenKH());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date ngayTao = model.getNgayTao();
        String ngayMua = dateFormat.format(ngayTao);
        txtNgayMua.setText(ngayMua);
        txtTenSP.setText(model.getTenpSP());
        txtSoLuong.setText(String.valueOf(model.getTongSL()));
        txtTongTien.setText(String.valueOf(model.getTongTien()));
        txtMaNV.setText(model.getMaNV());
        txtMaKH.setText(model.getMaKH());
        txtMaBan.setText(model.getMaBan());

    }

    HoaDon getForm() {
        boolean isValid = true;
        HoaDon model = new HoaDon();
        model.setMaHD(txtMaHD.getText());
        model.setHoTenKH(txtTenKH.getText());
        try {
            Date ngayTao = new SimpleDateFormat("dd/MM/yyyy").parse(txtNgayMua.getText());
            model.setNgayTao(ngayTao);
        } catch (ParseException e) {
            e.printStackTrace();
            MsgBox.alert(this, "Ngày tạo không hợp lệ");
            isValid = false; // Không hợp lệ nếu ngày tạo không đúng định dạng
        }

        model.setTenpSP(txtTenSP.getText());
        int soLuong = Integer.parseInt(txtSoLuong.getText());
        model.setTongSL(soLuong);
        model.setTongTien(Float.parseFloat(txtTongTien.getText()));
        model.setMaNV(txtMaNV.getText());
        model.setMaKH(txtMaNV.getText());
        model.setMaBan(txtMaBan.getText());
        return model;

    }

    void fillTableHD() {
        DefaultTableModel model = (DefaultTableModel) tblHD.getModel();
        model.setRowCount(0);
        try {
            List<HoaDon> list = daoHD.selectAll();
            for (HoaDon b : list) {
                Object[] row = {
                    b.getMaHD(),
                    b.getHoTenKH(),
                    b.getNgayTao(),
                    b.getTenpSP(),
                    b.getTongSL(),
                    b.getTongTien(),
                    b.getMaNV(),
                    b.getMaKH(),
                    b.getMaBan(),
                    b.getTrangThai()};
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "lỗi truy vẫn dữ liệu");
            System.out.println(e);
        }
    }

    public class BillPrintable implements Printable {

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
                    g2d.drawString("Mã hóa đơn: " + txtMaHD.getText() + "  ", 10, y);
                    y += yShift;
                    g2d.drawString("                                      ", 10, y);
                    y += yShift;
                    g2d.drawString("Tên khách hàng: " + txtTenKH.getText() + "  ", 10, y);
                    y += yShift;
                    g2d.drawString("                                      ", 10, y);
                    y += yShift;
                    g2d.drawString("Ngày mua: " + txtNgayMua.getText() + "  ", 10, y);
                    y += yShift;
                    g2d.drawString("                                      ", 10, y);
                    y += yShift;
                    g2d.drawString("Tên Sản phẩm: " + txtTenSP.getText() + "  ", 10, y);
                    y += yShift;
                    g2d.drawString("                                      ", 10, y);
                    y += yShift;
                    g2d.drawString("Tổng số lượng: " + txtSoLuong.getText() + "  ", 10, y);
                    y += yShift;
                    g2d.drawString("                                      ", 10, y);
                    y += yShift;
                    g2d.drawString("Tổng tiền: " + txtTongTien.getText() + "  ", 10, y);
                    y += yShift;
                    g2d.drawString("                                      ", 10, y);
                    y += yShift;
                    g2d.drawString("Bàn: " + txtMaBan.getText() + "  ", 10, y);
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

    public void clear() {
        this.setForm(new HoaDon());
    }

    @SuppressWarnings("unchecked")


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtNgayTao = new com.toedter.calendar.JDateChooser();
        btnTimKiem = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JTextField();
        txtTenKH = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        txtMaKH = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtMaBan = new javax.swing.JTextField();
        txtTenSP = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        txtNgayMua = new javax.swing.JTextField();
        btnXacNhan = new javax.swing.JButton();
        cboTrangThai = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblHD = new javax.swing.JTable();
        btnXoaHD1 = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();

        setBackground(new java.awt.Color(102, 255, 204));
        setOpaque(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btnTimKiem.setBackground(new java.awt.Color(0, 51, 153));
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTimKiem.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(204, 204, 255));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("MaHD");

        jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Tên khách hàng:");

        jLabel11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Ngày mua");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("MaNV");

        jLabel14.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("MaKH");

        jLabel15.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("MaBan");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("Tên sản phẩm:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Tổng tiền");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Số lượng");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaHD, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                            .addComponent(txtTenKH)
                            .addComponent(txtNgayMua)))
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel9)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSoLuong)
                    .addComponent(txtTenSP)
                    .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(69, 69, 69)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaNV)
                            .addComponent(txtMaKH)
                            .addComponent(txtMaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(21, 21, 21))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(38, 38, 38)
                                        .addComponent(jLabel14))
                                    .addComponent(jLabel6)
                                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(35, 35, 35)
                                        .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addGap(0, 11, Short.MAX_VALUE))
                                    .addComponent(txtMaBan)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel17)
                                    .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel12))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel9)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addComponent(txtTongTien))))))
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtNgayMua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        btnXacNhan.setBackground(new java.awt.Color(0, 51, 153));
        btnXacNhan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXacNhan.setForeground(new java.awt.Color(255, 255, 255));
        btnXacNhan.setText("xác nhận");
        btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanActionPerformed(evt);
            }
        });

        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Đã thanh toán", "Chưa thanh toán" }));

        tblHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã HD", "Tên khách hàng", "Ngày tạo", "Tên SP", "So Luong", "Tổng tiền", "MaNV", "MaKH", "MaBan", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, false, true, true, true, true, true, true, true
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

        btnXoaHD1.setBackground(new java.awt.Color(0, 0, 153));
        btnXoaHD1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnXoaHD1.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaHD1.setText("Xóa hóa đơn");
        btnXoaHD1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaHD1ActionPerformed(evt);
            }
        });

        btnMoi.setBackground(new java.awt.Color(0, 0, 153));
        btnMoi.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnMoi.setText("Làm mới");
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
                .addGap(148, 148, 148)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(98, 98, 98))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTimKiem)
                        .addGap(18, 18, 18)
                        .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnXacNhan)
                        .addGap(70, 70, 70))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1274, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnMoi)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoaHD1)
                        .addGap(110, 110, 110))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoaHD1)
                    .addComponent(btnMoi))
                .addGap(229, 229, 229))
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

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        fillTableTK();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed
        fillTableTT();
    }//GEN-LAST:event_btnXacNhanActionPerformed

    void clearForm() {
        txtMaBan.setText("");
        txtMaHD.setText("");
        txtMaKH.setText("");
        txtMaNV.setText("");
        txtNgayMua.setText("");
        txtSoLuong.setText("");
        txtTenKH.setText("");
        txtTenSP.setText("");
        txtTongTien.setText("");
        row = -1;
    }

    void edit() {
        String maHD = (String) tblHD.getValueAt(this.row, 0);
        HoaDon nh = daoHD.selectById(maHD);
        this.setForm(nh);
    }

    private void tblHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDMouseClicked
        if (evt.getClickCount() == 1) {
            this.row = tblHD.rowAtPoint(evt.getPoint());
            edit();
        }
    }//GEN-LAST:event_tblHDMouseClicked

    void deleteHD() {
        if (txtMaHD.getText().equals("")) {
            MsgBox.alert(this, "ban phải nhập MaHD");
        } else if (MsgBox.confirm(this, "bạn thục sự muốn xóa hóa đơn này")) {
            String maBan = txtMaHD.getText();
            try {
                daoHD.delete(maBan);
                this.fillTableHD();
                this.clear();
                MsgBox.alert(this, "xóa thành công");
            } catch (Exception e) {
                MsgBox.alert(this, "xóa thất bại");
                System.out.println(e);
            }
        }
    }
    private void btnXoaHD1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaHD1ActionPerformed
        deleteHD();
    }//GEN-LAST:event_btnXoaHD1ActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMoiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXacNhan;
    private javax.swing.JButton btnXoaHD1;
    private javax.swing.JComboBox<String> cboTrangThai;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tblHD;
    private javax.swing.JTextField txtMaBan;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtNgayMua;
    private com.toedter.calendar.JDateChooser txtNgayTao;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables

}
