/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */


import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import java.awt.Color;
import watersys.BanHang.ChiTietSPFrom;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Properties;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import watersys.DAO.ChiTietSPDAO;
import watersys.DAO.BanDAO;
import watersys.DAO.HoaDonDAO;

/**
 *
 * @author xuanc
 */
public class BanForm2 extends javax.swing.JInternalFrame {

    BanDAO dao = new BanDAO();
    HoaDonDAO daoHD = new HoaDonDAO();
    ChiTietSPDAO daoCTSP = new ChiTietSPDAO();
    private Properties config;
    int row = 0;

    private List<JLabel> lblBanList;

    public BanForm2() {
        this.setComponentPopupMenu(null);
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        initLblTTBanArray();
        for (int i = 0; i < lblTTBanArray.length; i++) {
            String maBan = "Ban" + (i + 1);
            updateMauSac(maBan);
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
        jLabel18 = new javax.swing.JLabel();
        lblBan10 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        lblBan5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblChiTietBan = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        lblTTBan4 = new javax.swing.JPanel();
        lblTTBan3 = new javax.swing.JPanel();
        lblTTBan1 = new javax.swing.JPanel();
        lblTTBan2 = new javax.swing.JPanel();
        lblTTBan5 = new javax.swing.JPanel();
        lblTTBan6 = new javax.swing.JPanel();
        lblTTBan7 = new javax.swing.JPanel();
        lblTTBan8 = new javax.swing.JPanel();
        lblTTBan9 = new javax.swing.JPanel();
        lblTTBan10 = new javax.swing.JPanel();

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

        lblTTBan4.setBackground(new java.awt.Color(51, 0, 255));

        javax.swing.GroupLayout lblTTBan4Layout = new javax.swing.GroupLayout(lblTTBan4);
        lblTTBan4.setLayout(lblTTBan4Layout);
        lblTTBan4Layout.setHorizontalGroup(
            lblTTBan4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 129, Short.MAX_VALUE)
        );
        lblTTBan4Layout.setVerticalGroup(
            lblTTBan4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        lblTTBan3.setBackground(new java.awt.Color(51, 0, 255));

        javax.swing.GroupLayout lblTTBan3Layout = new javax.swing.GroupLayout(lblTTBan3);
        lblTTBan3.setLayout(lblTTBan3Layout);
        lblTTBan3Layout.setHorizontalGroup(
            lblTTBan3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 128, Short.MAX_VALUE)
        );
        lblTTBan3Layout.setVerticalGroup(
            lblTTBan3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        lblTTBan1.setBackground(new java.awt.Color(51, 0, 255));

        javax.swing.GroupLayout lblTTBan1Layout = new javax.swing.GroupLayout(lblTTBan1);
        lblTTBan1.setLayout(lblTTBan1Layout);
        lblTTBan1Layout.setHorizontalGroup(
            lblTTBan1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        lblTTBan1Layout.setVerticalGroup(
            lblTTBan1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        lblTTBan2.setBackground(new java.awt.Color(51, 0, 255));

        javax.swing.GroupLayout lblTTBan2Layout = new javax.swing.GroupLayout(lblTTBan2);
        lblTTBan2.setLayout(lblTTBan2Layout);
        lblTTBan2Layout.setHorizontalGroup(
            lblTTBan2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 128, Short.MAX_VALUE)
        );
        lblTTBan2Layout.setVerticalGroup(
            lblTTBan2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );

        lblTTBan5.setBackground(new java.awt.Color(51, 0, 255));

        javax.swing.GroupLayout lblTTBan5Layout = new javax.swing.GroupLayout(lblTTBan5);
        lblTTBan5.setLayout(lblTTBan5Layout);
        lblTTBan5Layout.setHorizontalGroup(
            lblTTBan5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );
        lblTTBan5Layout.setVerticalGroup(
            lblTTBan5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );

        lblTTBan6.setBackground(new java.awt.Color(51, 0, 255));

        javax.swing.GroupLayout lblTTBan6Layout = new javax.swing.GroupLayout(lblTTBan6);
        lblTTBan6.setLayout(lblTTBan6Layout);
        lblTTBan6Layout.setHorizontalGroup(
            lblTTBan6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 128, Short.MAX_VALUE)
        );
        lblTTBan6Layout.setVerticalGroup(
            lblTTBan6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        lblTTBan7.setBackground(new java.awt.Color(51, 0, 255));

        javax.swing.GroupLayout lblTTBan7Layout = new javax.swing.GroupLayout(lblTTBan7);
        lblTTBan7.setLayout(lblTTBan7Layout);
        lblTTBan7Layout.setHorizontalGroup(
            lblTTBan7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 128, Short.MAX_VALUE)
        );
        lblTTBan7Layout.setVerticalGroup(
            lblTTBan7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        lblTTBan8.setBackground(new java.awt.Color(51, 0, 255));

        javax.swing.GroupLayout lblTTBan8Layout = new javax.swing.GroupLayout(lblTTBan8);
        lblTTBan8.setLayout(lblTTBan8Layout);
        lblTTBan8Layout.setHorizontalGroup(
            lblTTBan8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 128, Short.MAX_VALUE)
        );
        lblTTBan8Layout.setVerticalGroup(
            lblTTBan8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        lblTTBan9.setBackground(new java.awt.Color(51, 0, 255));

        javax.swing.GroupLayout lblTTBan9Layout = new javax.swing.GroupLayout(lblTTBan9);
        lblTTBan9.setLayout(lblTTBan9Layout);
        lblTTBan9Layout.setHorizontalGroup(
            lblTTBan9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 128, Short.MAX_VALUE)
        );
        lblTTBan9Layout.setVerticalGroup(
            lblTTBan9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        lblTTBan10.setBackground(new java.awt.Color(51, 0, 255));

        javax.swing.GroupLayout lblTTBan10Layout = new javax.swing.GroupLayout(lblTTBan10);
        lblTTBan10.setLayout(lblTTBan10Layout);
        lblTTBan10Layout.setHorizontalGroup(
            lblTTBan10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );
        lblTTBan10Layout.setVerticalGroup(
            lblTTBan10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(141, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1051, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel11)
                                .addGap(172, 172, 172)
                                .addComponent(jLabel13)
                                .addGap(158, 158, 158)
                                .addComponent(jLabel15)
                                .addGap(64, 64, 64))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(41, 41, 41)
                                        .addComponent(jLabel2)
                                        .addGap(61, 61, 61)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(141, 141, 141)
                                                .addComponent(jLabel3))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(94, 94, 94)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(lblTTBan2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(lblBan2)
                                                    .addComponent(lblBan7)
                                                    .addComponent(lblTTBan7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                    .addComponent(jLabel4)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(13, 13, 13)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lblBan6)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(lblBan1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lblTTBan1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addComponent(lblTTBan6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(92, 92, 92)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(45, 45, 45)
                                        .addComponent(jLabel5)
                                        .addGap(165, 165, 165)
                                        .addComponent(jLabel7))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lblBan3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblTTBan3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblBan8)
                                            .addComponent(lblTTBan8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(85, 85, 85)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblBan9, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblTTBan9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(lblBan4)
                                                .addComponent(lblTTBan4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                        .addGap(84, 84, 84)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel18)))
                            .addComponent(lblTTBan5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblBan5)
                            .addComponent(lblBan10)
                            .addComponent(lblTTBan10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(174, 174, 174))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblBan4, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblBan3, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblBan2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblBan5, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblBan1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTTBan5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTTBan3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTTBan4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTTBan1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTTBan2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblBan7, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblBan6, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTTBan6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTTBan7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel15)
                            .addComponent(jLabel18)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBan8, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblBan9, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblBan10, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblTTBan8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblTTBan9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(lblTTBan10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(106, 106, 106))
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
    void openThemSP() {
        try {
            Frame parentFrame = new Frame("Parent Frame");
            ChiTietSPFrom chiTietSPForm = new ChiTietSPFrom(parentFrame, true);

            // Thêm WindowListener
            chiTietSPForm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    // Gọi phương thức khi form đóng
                    onChiTietSPFormClosed();
                }
            });

            chiTietSPForm.setVisible(true);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

// Phương thức được gọi khi form ChiTietSPFrom đóng
    private void onChiTietSPFormClosed() {
        for (int i = 0; i < lblTTBanArray.length; i++) {
            String maBan = "Ban" + (i + 1);
            updateMauSac(maBan);
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

    }//GEN-LAST:event_tblChiTietBanMouseClicked
    private JPanel[] lblTTBanArray = new JPanel[10];

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
        lblTTBanArray[9] = lblTTBan10;
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
    private javax.swing.JPanel lblTTBan1;
    private javax.swing.JPanel lblTTBan10;
    private javax.swing.JPanel lblTTBan2;
    private javax.swing.JPanel lblTTBan3;
    private javax.swing.JPanel lblTTBan4;
    private javax.swing.JPanel lblTTBan5;
    private javax.swing.JPanel lblTTBan6;
    private javax.swing.JPanel lblTTBan7;
    private javax.swing.JPanel lblTTBan8;
    private javax.swing.JPanel lblTTBan9;
    private javax.swing.JTable tblChiTietBan;
    // End of variables declaration//GEN-END:variables
}
