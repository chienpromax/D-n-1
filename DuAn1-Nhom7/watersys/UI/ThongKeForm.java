package watersys.UI;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import watersys.DAO.ThongKeDAO;

/**
 *
 * @author xuanc
 */
public class ThongKeForm extends javax.swing.JInternalFrame {

    ThongKeDAO daoTk = new ThongKeDAO();
    private DefaultPieDataset pieDataset = new DefaultPieDataset();

    /**
     * Creates new form SanPham
     */
    public ThongKeForm() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        // Đặt ngày hiện tại cho JDateChooser
        txtThongKe.setDate(currentDate);
        txtThongKe.setLocale(new Locale("vi", "VN")); // Đặt locale thành tiếng Việt

        JTextFieldDateEditor editor2 = (JTextFieldDateEditor) txtThongKe.getDateEditor();
        editor2.setDateFormatString("yyyy/MM/dd");

        filLBLTongSL();
        fillDTNgay();
        fillDTThang();
        fillDTNam();

        bieuDoCot();
        bieuDoTron();

    }

    public void bieuDoTron() {
        List<Object[]> sanPhamData = daoTk.getSanPham();
        for (Object[] rowData : sanPhamData) {
            String tenNuoc = (String) rowData[0];
            int soLuongTonKho = (int) rowData[3]; // Giả sử số hàng tồn kho ở cột thứ tư
            pieDataset.setValue(tenNuoc, soLuongTonKho);
        }
        JFreeChart pieChart = ChartFactory.createPieChart("THỐNG KÊ SẢN PHẨM", pieDataset, false, true, false);
        PiePlot piePlot = (PiePlot) pieChart.getPlot();
        Font labelFont = new Font("SansSerif", Font.BOLD, 14); // Điều chỉnh font và kích thước tùy ý
        piePlot.setLabelFont(labelFont);

        ChartPanel pieChartPanel = new ChartPanel(pieChart);
        lblTron.removeAll();
        lblTron.add(pieChartPanel, BorderLayout.CENTER);
        lblTron.validate();
        lblTron.setBackground(Color.WHITE);
    }

    public void bieuDoCot() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        // Gọi stored procedure để lấy dữ liệu từ cơ sở dữ liệu
        List<Object[]> doanhThuData = daoTk.getDoanhThuBang();
        for (int thang = 1; thang <= 12; thang++) {
            double doanhThuKho = 0.0; // Giả sử giá trị doanh thu mặc định là 0
            // Kiểm tra xem tháng có trong doanhThuData hay không
            for (Object[] rowData : doanhThuData) {
                int thangTrongData = (int) rowData[0];
                if (thang == thangTrongData) {
                    doanhThuKho = (double) rowData[1];
                    break;
                }
            }
            dataset.setValue(doanhThuKho, "DoanhThuKho", "T" + thang);
        }
        JFreeChart chart = ChartFactory.createBarChart("THỐNG KÊ DOANH THU", "THÁNG", "DOANH THU",
                dataset, PlotOrientation.VERTICAL, false, true, false);
        // Lấy đối tượng plot từ biểu đồ
        CategoryPlot categoryPlot = chart.getCategoryPlot();
        // Thiết lập giá trị cho trục y
        NumberAxis yAxis = (NumberAxis) categoryPlot.getRangeAxis();
        yAxis.setRange(0.0d, 500000.0d);  // Đặt giá trị tối thiểu và tối đa của trục y
        yAxis.setNumberFormatOverride(new DecimalFormat("###,##0.0")); // Sử dụng NumberFormat
        // Thiết lập màu sắc và giao diện của biểu đồ
        categoryPlot.setBackgroundPaint(Color.WHITE);
        BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
//        Color clr3 = new Color(204, 0, 51);
        Color clr3 = new Color(0, 51, 204);
        renderer.setSeriesPaint(0, clr3);
        // Hiển thị biểu đồ trên giao diện
        ChartPanel barpChartPanel = new ChartPanel(chart);
        lblCot.removeAll();
        lblCot.add(barpChartPanel, BorderLayout.CENTER);
        lblCot.validate();
    }

    void fillDTNam() {
        try {
            Date ngayDoanhThu = txtThongKe.getDate();
            int namDoanhThu = (ngayDoanhThu != null) ? ngayDoanhThu.getYear() + 1900 : 0;
            List<Object[]> list = daoTk.getDTNam(namDoanhThu);

            if (!list.isEmpty()) {
                Object[] row = list.get(0);
                Object tongDoanhThuValue = row[0];

                if (tongDoanhThuValue != null) {
                    double displayValue = (double) tongDoanhThuValue;
                    DecimalFormat decimalFormat = new DecimalFormat("###,##0.0");

                    // Thiết lập định dạng cho giá trị hiển thị
                    lblNam.setText(decimalFormat.format(displayValue));
                } else {
                    lblNam.setText("0");
                }
            } else {
                lblNam.setText("0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi", e);
        }
    }

    void fillDTThang() {
        try {
            Date ngayDoanhThu = txtThongKe.getDate();
            int namDoanhThu = (ngayDoanhThu != null) ? ngayDoanhThu.getYear() + 1900 : 0;
            int thangDoanhThu = (ngayDoanhThu != null) ? ngayDoanhThu.getMonth() + 1 : 0;

            List<Object[]> list = daoTk.getDTThang(namDoanhThu, thangDoanhThu);

            if (!list.isEmpty()) {
                Object[] row = list.get(0);
                Object tongDoanhThuValue = row[0];

                if (tongDoanhThuValue != null) {
                    double displayValue = (double) tongDoanhThuValue;
                    DecimalFormat decimalFormat = new DecimalFormat("###,##0.0");
                    lblThang.setText(decimalFormat.format(displayValue));
                } else {
                    lblThang.setText("0");
                }
            } else {
                lblThang.setText("0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi", e);
        }
    }

    void fillDTNgay() {
        try {
            Date ngayDoanhThu = txtThongKe.getDate();

            String strNgayDoanhThu = (ngayDoanhThu != null) ? new SimpleDateFormat("yyyy-MM-dd").format(ngayDoanhThu) : "1900-01-01";

            List<Object[]> list = daoTk.getDTNgay(strNgayDoanhThu);

            if (!list.isEmpty()) {
                Object[] row = list.get(0);
                Object tongDoanhThuValue = row[0];

                if (tongDoanhThuValue != null) {
                    String displayValue = tongDoanhThuValue.toString();
                    lblNgay.setText((displayValue));
                } else {
                    lblNgay.setText("0");
                }
            } else {
                lblNgay.setText("0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi", e);
        }
    }

    void filLBLTongSL() {
        List<Object[]> tongSoLuong = daoTk.getTongSL();

        // Kiểm tra nếu danh sách không rỗng
        if (!tongSoLuong.isEmpty()) {
            Object[] row = tongSoLuong.get(0);
            Object tongSoLuongValue = row[0];

            // Chuyển đổi giá trị thành chuỗi hoặc kiểu dữ liệu phù hợp
            String displayValue = (tongSoLuongValue != null) ? tongSoLuongValue.toString() : "0";

            lblTongSL.setText("" + displayValue);
        } else {
            lblTongSL.setText("Không có dữ liệu");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblTongSL = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lblNgay = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblThang = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        lblNam = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtThongKe = new com.toedter.calendar.JDateChooser();
        btnXacNhan = new javax.swing.JButton();
        lblCot = new javax.swing.JPanel();
        lblTron = new javax.swing.JPanel();

        setBackground(new java.awt.Color(102, 255, 204));
        setOpaque(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel9.setBackground(new java.awt.Color(204, 204, 204));
        jPanel9.setForeground(new java.awt.Color(204, 204, 204));

        jPanel10.setBackground(new java.awt.Color(255, 255, 102));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 255));
        jLabel5.setText("Tổng số hàng bán ra");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblTongSL.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        lblTongSL.setForeground(new java.awt.Color(51, 51, 51));
        lblTongSL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTongSL.setText("0");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(lblTongSL, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTongSL, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(984, 47, -1, -1));

        jPanel12.setBackground(new java.awt.Color(255, 153, 0));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 14, -1, -1));

        jPanel13.setBackground(new java.awt.Color(204, 204, 204));
        jPanel13.setForeground(new java.awt.Color(204, 204, 204));

        jPanel14.setBackground(new java.awt.Color(0, 51, 153));
        jPanel14.setForeground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Doanh thu ngày");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(50, 50, 50))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblNgay.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        lblNgay.setForeground(new java.awt.Color(51, 51, 51));
        lblNgay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNgay.setText("0");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(lblNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(164, 46, -1, -1));

        jPanel15.setBackground(new java.awt.Color(204, 204, 204));
        jPanel15.setForeground(new java.awt.Color(204, 204, 204));

        jPanel16.setBackground(new java.awt.Color(0, 51, 153));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("doanh thu tháng");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(58, 58, 58))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblThang.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        lblThang.setForeground(new java.awt.Color(51, 51, 51));
        lblThang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblThang.setText("0");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addComponent(lblThang, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblThang, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 46, -1, -1));

        jPanel17.setBackground(new java.awt.Color(204, 204, 204));
        jPanel17.setForeground(new java.awt.Color(204, 204, 204));

        jPanel18.setBackground(new java.awt.Color(0, 51, 153));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("doanh thu năm");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblNam.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        lblNam.setForeground(new java.awt.Color(51, 51, 51));
        lblNam.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNam.setText("0");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(lblNam, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNam, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(694, 46, -1, 124));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 204));
        jLabel12.setText("Chọn Thời gian thống kê");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(395, 14, -1, -1));
        jPanel1.add(txtThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 12, 161, -1));

        btnXacNhan.setBackground(new java.awt.Color(0, 0, 153));
        btnXacNhan.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnXacNhan.setForeground(new java.awt.Color(255, 255, 255));
        btnXacNhan.setText("Xác nhận");
        btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanActionPerformed(evt);
            }
        });
        jPanel1.add(btnXacNhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(749, 6, -1, -1));

        lblCot.setLayout(new java.awt.BorderLayout());
        jPanel1.add(lblCot, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 730, 600));

        lblTron.setLayout(new java.awt.BorderLayout());
        jPanel1.add(lblTron, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 190, 610, 560));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed
        fillDTNgay();
        fillDTThang();
        fillDTNam();
    }//GEN-LAST:event_btnXacNhanActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnXacNhan;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel lblCot;
    private javax.swing.JLabel lblNam;
    private javax.swing.JLabel lblNgay;
    private javax.swing.JLabel lblThang;
    private javax.swing.JLabel lblTongSL;
    private javax.swing.JPanel lblTron;
    private com.toedter.calendar.JDateChooser txtThongKe;
    // End of variables declaration//GEN-END:variables
}
