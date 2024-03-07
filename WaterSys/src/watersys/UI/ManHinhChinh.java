/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package watersys.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicInternalFrameUI;

/**
 *
 * @author xuanc
 */
public class ManHinhChinh extends javax.swing.JInternalFrame {

    private Timer timer;
    private int currentImageIndex;
    private String[] imagePaths = {"/watersys/Icon/slide/xanh.jpg", "/watersys/Icon/slide/bia.jpg", "/watersys/Icon/slide/all.jpg", "/watersys/Icon/slide/nuocEp.jpg"};
    
    public ManHinhChinh() {
        this.setComponentPopupMenu(null);
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        
        showNextImage();
        setupTimer();
    }


    private void setupTimer() {
        timer = new Timer(3000, new ActionListener() {
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
            currentImageIndex = 0; // Lặp lại từ đầu nếu đã hiển thị tất cả ảnh
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblSlide = new javax.swing.JLabel();

        setBackground(new java.awt.Color(102, 255, 204));
        setOpaque(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        lblSlide.setText(".");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSlide, javax.swing.GroupLayout.DEFAULT_SIZE, 1323, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSlide, javax.swing.GroupLayout.DEFAULT_SIZE, 771, Short.MAX_VALUE)
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblSlide;
    // End of variables declaration//GEN-END:variables
}
