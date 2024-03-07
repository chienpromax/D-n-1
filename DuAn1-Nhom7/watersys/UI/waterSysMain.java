/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package watersys.UI;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.Timer;
import watersys.Utilities.Auth;
import watersys.Utilities.MsgBox;
import watersys.Utilities.XImage;

/**
 *
 * @author xuanc
 */
public class waterSysMain extends javax.swing.JFrame {

    private Timer timer;
    private int currentImageIndex;
    private String[] imagePaths = {"/watersys/Icon/slide/xanh.jpg", "/watersys/Icon/slide/bia.jpg", "/watersys/Icon/slide/all.jpg", "/watersys/Icon/slide/nuocEp.jpg"};
    Color DefaultColor, clickedColor;

    public waterSysMain() {
        initComponents();
        init();
        showNextImage();
        setupTimer();

        DefaultColor = new Color(0, 0, 153);
        clickedColor = new Color(255, 153, 51);

        menuNhanVien.setBackground(DefaultColor);
        menuSanPham.setBackground(DefaultColor);
        menuBan.setBackground(DefaultColor);
        menuKhachHang.setBackground(DefaultColor);
        menuThongKe.setBackground(DefaultColor);

    }

    void init() {
        setIconImage(XImage.getAppIcon());
        setTitle("Hệ thống WarterSys");
        setLocationRelativeTo(null);
        // Tạo một SimpleDateFormat khác để định dạng ngày
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy"); // Định dạng ngày theo "Thứ, dd Tháng yyyy"

        Timer timer = new Timer(1000, new ActionListener() {
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

            @Override
            public void actionPerformed(ActionEvent e) {
                lblTime.setText(timeFormat.format(new Date())); // Cập nhật thời gian
                lblDay.setText(dateFormat.format(new Date())); // Cập nhật ngày
            }
        });
        timer.start();
//        this.openWelcom();
        this.openlogin();
        displayUserInfo();
        displayImage();
    }

    private void setupTimer() {
        timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showNextImage();
            }
        });
        timer.start();
    }

    private void showNextImage() {
        if (currentImageIndex < imagePaths.length) {
            URL imageUrl = getClass().getResource(imagePaths[currentImageIndex]);
            ImageIcon imageIcon = new ImageIcon(imageUrl);
            lblSlide.setIcon(imageIcon);
            currentImageIndex++;
        } else {
            currentImageIndex = 0;
        }
    }

    void displayUserInfo() {
        String userID = Auth.user.getHoTen();
        String role = Auth.user.isVaiTro() ? "Quản lý" : "Nhân viên";
        lblUserInfo.setText("<html>Tên: " + userID + "<br>Chức vụ: " + role + "</html>");
    }

    void displayImage() {
//    String imagePath = "/watersys/Icon/yea.jpg";
        try {
            // Lưu đường dẫn vào biến trước khi tạo ImageIcon
            String imageName = Auth.user.getHinh();
            String imagePath = "/watersys/Icon/nhanvien/" + imageName;
            //System.out.println("Đường dẫn ảnh: " + imagePath);
            // Lưu đường dẫn vào biến trước khi tạo ImageIcon
            ImageIcon imageIcon = new ImageIcon(getClass().getResource(imagePath));

            // Kiểm tra xem imageIcon có hợp lệ hay không
            if (imageIcon != null && imageIcon.getImage() != null) {
                Image image = imageIcon.getImage().getScaledInstance(150, 140, Image.SCALE_SMOOTH);

                // Kiểm tra xem image có hợp lệ hay không
                if (image != null) {
                    ImageIcon scaledIcon = new ImageIcon(image);
                    lblAnh.setIcon(scaledIcon);
//                System.out.println("Đã sét ảnh cho lblAnh.");
                } else {
                    System.out.println("Lỗi: image là null.");
                }
            } else {
                System.out.println("Lỗi: không có ảnh.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void openWelcom() {
        new Loading(this, true).setVisible(true);
    }

    void openlogin() {
        new DangNhap(this, true).setVisible(true);
    }

    void openDoiMK() {
        new DoiMatKhau(this, true).setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblAnh = new javax.swing.JLabel();
        btnDangxuat = new javax.swing.JButton();
        btnDoiMK = new javax.swing.JButton();
        menuSanPham = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        menuNhanVien = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        menuKhachHang = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        menuThongKe = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        menuBan = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        lblUserInfo = new javax.swing.JLabel();
        menuMain = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        menuInHD = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        menuName = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lblDay = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jPanel3 = new javax.swing.JPanel();
        lblSlide = new javax.swing.JLabel();

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblAnh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 204), 3));
        jPanel2.add(lblAnh, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 7, 150, 140));

        btnDangxuat.setBackground(new java.awt.Color(0, 0, 153));
        btnDangxuat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDangxuat.setForeground(new java.awt.Color(255, 255, 255));
        btnDangxuat.setText("Đăng xuất");
        btnDangxuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangxuatActionPerformed(evt);
            }
        });
        jPanel2.add(btnDangxuat, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 740, 140, 40));

        btnDoiMK.setBackground(new java.awt.Color(0, 0, 153));
        btnDoiMK.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDoiMK.setForeground(new java.awt.Color(255, 255, 255));
        btnDoiMK.setText("Đổi mật khẩu");
        btnDoiMK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiMKActionPerformed(evt);
            }
        });
        jPanel2.add(btnDoiMK, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 690, 140, 40));

        menuSanPham.setBackground(new java.awt.Color(0, 0, 153));
        menuSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuSanPhamMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menuSanPhamMousePressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Sản phẩm");

        javax.swing.GroupLayout menuSanPhamLayout = new javax.swing.GroupLayout(menuSanPham);
        menuSanPham.setLayout(menuSanPhamLayout);
        menuSanPhamLayout.setHorizontalGroup(
            menuSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuSanPhamLayout.createSequentialGroup()
                .addContainerGap(59, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(54, 54, 54))
        );
        menuSanPhamLayout.setVerticalGroup(
            menuSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.add(menuSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 180, 40));

        menuNhanVien.setBackground(new java.awt.Color(0, 0, 153));
        menuNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuNhanVienMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menuNhanVienMousePressed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Nhân viên");

        javax.swing.GroupLayout menuNhanVienLayout = new javax.swing.GroupLayout(menuNhanVien);
        menuNhanVien.setLayout(menuNhanVienLayout);
        menuNhanVienLayout.setHorizontalGroup(
            menuNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuNhanVienLayout.createSequentialGroup()
                .addContainerGap(56, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(54, 54, 54))
        );
        menuNhanVienLayout.setVerticalGroup(
            menuNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel2.add(menuNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 180, 40));

        menuKhachHang.setBackground(new java.awt.Color(0, 0, 153));
        menuKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuKhachHangMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menuKhachHangMousePressed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Khách hàng");

        javax.swing.GroupLayout menuKhachHangLayout = new javax.swing.GroupLayout(menuKhachHang);
        menuKhachHang.setLayout(menuKhachHangLayout);
        menuKhachHangLayout.setHorizontalGroup(
            menuKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuKhachHangLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel10)
                .addContainerGap(50, Short.MAX_VALUE))
        );
        menuKhachHangLayout.setVerticalGroup(
            menuKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel2.add(menuKhachHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 180, 40));

        menuThongKe.setBackground(new java.awt.Color(0, 0, 153));
        menuThongKe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuThongKeMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menuThongKeMousePressed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Thống kê");

        javax.swing.GroupLayout menuThongKeLayout = new javax.swing.GroupLayout(menuThongKe);
        menuThongKe.setLayout(menuThongKeLayout);
        menuThongKeLayout.setHorizontalGroup(
            menuThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuThongKeLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jLabel12)
                .addContainerGap(59, Short.MAX_VALUE))
        );
        menuThongKeLayout.setVerticalGroup(
            menuThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuThongKeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.add(menuThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 500, 180, 40));

        menuBan.setBackground(new java.awt.Color(0, 0, 153));
        menuBan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuBanMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menuBanMousePressed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Bán hàng");

        javax.swing.GroupLayout menuBanLayout = new javax.swing.GroupLayout(menuBan);
        menuBan.setLayout(menuBanLayout);
        menuBanLayout.setHorizontalGroup(
            menuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuBanLayout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addGap(55, 55, 55))
        );
        menuBanLayout.setVerticalGroup(
            menuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuBanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.add(menuBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 180, 40));

        lblUserInfo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblUserInfo.setForeground(new java.awt.Color(255, 0, 0));
        lblUserInfo.setText("ID");
        lblUserInfo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153), 2));
        jPanel2.add(lblUserInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 160, 40));

        menuMain.setBackground(new java.awt.Color(0, 0, 153));
        menuMain.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuMainMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menuMainMousePressed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Màn hình chính");

        javax.swing.GroupLayout menuMainLayout = new javax.swing.GroupLayout(menuMain);
        menuMain.setLayout(menuMainLayout);
        menuMainLayout.setHorizontalGroup(
            menuMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuMainLayout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addGap(32, 32, 32))
        );
        menuMainLayout.setVerticalGroup(
            menuMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel2.add(menuMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 180, 40));

        menuInHD.setBackground(new java.awt.Color(0, 0, 153));
        menuInHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuInHDMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menuInHDMousePressed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Lịch sử hóa đơn");

        javax.swing.GroupLayout menuInHDLayout = new javax.swing.GroupLayout(menuInHD);
        menuInHD.setLayout(menuInHDLayout);
        menuInHDLayout.setHorizontalGroup(
            menuInHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuInHDLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel16)
                .addContainerGap(36, Short.MAX_VALUE))
        );
        menuInHDLayout.setVerticalGroup(
            menuInHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuInHDLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.add(menuInHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, 180, 40));

        jPanel9.setBackground(new java.awt.Color(255, 153, 51));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuName.setBackground(new java.awt.Color(255, 255, 255));
        menuName.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        menuName.setForeground(new java.awt.Color(255, 255, 255));
        menuName.setText("Màn hình chính");
        jPanel9.add(menuName, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 10, 230, 31));

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("WATERSYS");
        jPanel9.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 92, 31));

        lblDay.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDay.setForeground(new java.awt.Color(0, 0, 0));
        lblDay.setText("00.00.00");
        jPanel9.add(lblDay, new org.netbeans.lib.awtextra.AbsoluteConstraints(1450, 20, 80, 20));

        lblTime.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTime.setForeground(new java.awt.Color(0, 0, 0));
        lblTime.setText("00.00.00");
        jPanel9.add(lblTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(1460, 0, 80, 20));

        jDesktopPane1.setBackground(new java.awt.Color(153, 255, 204));
        jDesktopPane1.setDesktopManager(null);
        jDesktopPane1.setDoubleBuffered(true);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        lblSlide.setText(".");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSlide, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSlide, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jDesktopPane1.setLayer(jPanel3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDesktopPane1))
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDesktopPane1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 792, Short.MAX_VALUE)))
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

    private void btnDoiMKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoiMKActionPerformed
        openDoiMK();
    }//GEN-LAST:event_btnDoiMKActionPerformed

    void dangXuat() {
        if (MsgBox.confirm(this, "Bạn có muốn đăng xuất không?")) {
            Auth.user = null;
            Auth.clear();
            this.dispose();
            new waterSysMain().setVisible(true);
        } else {
        }
    }
    private void btnDangxuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangxuatActionPerformed
        dangXuat();
    }//GEN-LAST:event_btnDangxuatActionPerformed

    private void menuSanPhamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuSanPhamMousePressed
        menuNhanVien.setBackground(DefaultColor);
        menuSanPham.setBackground(clickedColor);
        menuBan.setBackground(DefaultColor);
        menuKhachHang.setBackground(DefaultColor);
        menuThongKe.setBackground(DefaultColor);
        menuMain.setBackground(DefaultColor);
        menuInHD.setBackground(DefaultColor);

        menuName.setText("SẢN PHẨM");
    }//GEN-LAST:event_menuSanPhamMousePressed

    private void menuNhanVienMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuNhanVienMousePressed
        menuNhanVien.setBackground(clickedColor);
        menuSanPham.setBackground(DefaultColor);
        menuBan.setBackground(DefaultColor);
        menuKhachHang.setBackground(DefaultColor);
        menuThongKe.setBackground(DefaultColor);
        menuMain.setBackground(DefaultColor);
        menuInHD.setBackground(DefaultColor);

        menuName.setText("NHÂN VIÊN");
    }//GEN-LAST:event_menuNhanVienMousePressed

    private void menuKhachHangMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuKhachHangMousePressed
        menuNhanVien.setBackground(DefaultColor);
        menuSanPham.setBackground(DefaultColor);
        menuBan.setBackground(DefaultColor);
        menuKhachHang.setBackground(clickedColor);
        menuThongKe.setBackground(DefaultColor);
        menuMain.setBackground(DefaultColor);
        menuInHD.setBackground(DefaultColor);

        menuName.setText("KHÁCH HÀNG");
    }//GEN-LAST:event_menuKhachHangMousePressed

    private void menuBanMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBanMousePressed
        menuNhanVien.setBackground(DefaultColor);
        menuSanPham.setBackground(DefaultColor);
        menuBan.setBackground(clickedColor);
        menuKhachHang.setBackground(DefaultColor);
        menuThongKe.setBackground(DefaultColor);
        menuMain.setBackground(DefaultColor);
        menuInHD.setBackground(DefaultColor);

        menuName.setText("BÁN HÀNG");
    }//GEN-LAST:event_menuBanMousePressed

    private void menuThongKeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuThongKeMousePressed
        menuNhanVien.setBackground(DefaultColor);
        menuSanPham.setBackground(DefaultColor);
        menuBan.setBackground(DefaultColor);
        menuKhachHang.setBackground(DefaultColor);
        menuThongKe.setBackground(clickedColor);
        menuInHD.setBackground(DefaultColor);
        menuMain.setBackground(DefaultColor);

        menuName.setText("THỐNG KÊ");
    }//GEN-LAST:event_menuThongKeMousePressed

    private void menuSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuSanPhamMouseClicked
        SanPhamForm menuSP = new SanPhamForm();
        jDesktopPane1.removeAll();
        jDesktopPane1.add(menuSP).setVisible(true);
    }//GEN-LAST:event_menuSanPhamMouseClicked

    private void menuNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuNhanVienMouseClicked
        if (!Auth.isManager()) {
            MsgBox.alert(this, "bạn không có quyền vào");
        } else {
            NhanVienForm menuNV = new NhanVienForm();
            jDesktopPane1.removeAll();
            jDesktopPane1.add(menuNV).setVisible(true);
        }


    }//GEN-LAST:event_menuNhanVienMouseClicked

    private void menuKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuKhachHangMouseClicked
        KhachHangForm menukH = new KhachHangForm();
        jDesktopPane1.removeAll();
        jDesktopPane1.add(menukH).setVisible(true);
    }//GEN-LAST:event_menuKhachHangMouseClicked

    private void menuBanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBanMouseClicked
        BanForm menuBan = new BanForm();
        jDesktopPane1.removeAll();
        jDesktopPane1.add(menuBan).setVisible(true);
    }//GEN-LAST:event_menuBanMouseClicked

    private void menuMainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuMainMouseClicked
        ManHinhChinh menuMain = new ManHinhChinh();
        jDesktopPane1.removeAll();
        jDesktopPane1.add(menuMain).setVisible(true);
    }//GEN-LAST:event_menuMainMouseClicked

    private void menuMainMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuMainMousePressed
        menuNhanVien.setBackground(DefaultColor);
        menuSanPham.setBackground(DefaultColor);
        menuBan.setBackground(DefaultColor);
        menuKhachHang.setBackground(DefaultColor);
        menuThongKe.setBackground(DefaultColor);
        menuInHD.setBackground(DefaultColor);
        menuMain.setBackground(clickedColor);

        menuName.setText("MÀN HÌNH CHÍNH");
    }//GEN-LAST:event_menuMainMousePressed

    private void menuThongKeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuThongKeMouseClicked
        if (!Auth.isManager()) {
            MsgBox.alert(this, "bạn không có quyền vào");
        } else {
            ThongKeForm menuTK = new ThongKeForm();
            jDesktopPane1.removeAll();
            jDesktopPane1.add(menuTK).setVisible(true);
        }
    }//GEN-LAST:event_menuThongKeMouseClicked

    private void menuInHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuInHDMouseClicked
        XuatHoaDonForm menuInHD = new XuatHoaDonForm();
        jDesktopPane1.removeAll();
        jDesktopPane1.add(menuInHD).setVisible(true);
    }//GEN-LAST:event_menuInHDMouseClicked

    private void menuInHDMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuInHDMousePressed
        menuNhanVien.setBackground(DefaultColor);
        menuSanPham.setBackground(DefaultColor);
        menuBan.setBackground(DefaultColor);
        menuKhachHang.setBackground(DefaultColor);
        menuThongKe.setBackground(DefaultColor);
        menuMain.setBackground(DefaultColor);
        menuInHD.setBackground(clickedColor);

        menuName.setText("Lịch Sử Hóa Đơn");
    }//GEN-LAST:event_menuInHDMousePressed

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
            java.util.logging.Logger.getLogger(waterSysMain.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(waterSysMain.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(waterSysMain.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(waterSysMain.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new waterSysMain().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDangxuat;
    private javax.swing.JButton btnDoiMK;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JLabel lblDay;
    private javax.swing.JLabel lblSlide;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblUserInfo;
    private javax.swing.JPanel menuBan;
    private javax.swing.JPanel menuInHD;
    private javax.swing.JPanel menuKhachHang;
    private javax.swing.JPanel menuMain;
    private javax.swing.JLabel menuName;
    private javax.swing.JPanel menuNhanVien;
    private javax.swing.JPanel menuSanPham;
    private javax.swing.JPanel menuThongKe;
    // End of variables declaration//GEN-END:variables
}
