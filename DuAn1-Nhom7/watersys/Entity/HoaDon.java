/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package watersys.Entity;

import java.util.Date;

/**
 *
 * @author xuanc
 */
public class HoaDon {
    private String MaHD;
    private String HoTenKH;
    private Date NgayTao;
    private String TenpSP;
    private int TongSL;
    private float TongTien;
    private String MaNV;
    private String MaKH;
    private String MaBan;
    private String TrangThai;

    
    public String toString() {
        return TrangThai;
    }
    public HoaDon() {
    }

    public HoaDon(String MaHD, String HoTenKH, Date NgayTao, String TenpSP, float TongTien, int TongSL, String MaNV, String MaKH, String MaBan, String TrangThai) {
        this.MaHD = MaHD;
        this.HoTenKH = HoTenKH;
        this.NgayTao = NgayTao;
        this.TenpSP = TenpSP;
        this.TongTien = TongTien;
        this.TongSL = TongSL;
        this.MaNV = MaNV;
        this.MaKH = MaKH;
        this.MaBan = MaBan;
        this.TrangThai = TrangThai;
    }

    public String getMaHD() {
        return MaHD;
    }

    public void setMaHD(String MaHD) {
        this.MaHD = MaHD;
    }

    public String getHoTenKH() {
        return HoTenKH;
    }

    public void setHoTenKH(String HoTenKH) {
        this.HoTenKH = HoTenKH;
    }

    public Date getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(Date NgayTao) {
        this.NgayTao = NgayTao;
    }

    public String getTenpSP() {
        return TenpSP;
    }

    public void setTenpSP(String TenpSP) {
        this.TenpSP = TenpSP;
    }

    public float getTongTien() {
        return TongTien;
    }

    public void setTongTien(float TongTien) {
        this.TongTien = TongTien;
    }

    public int getTongSL() {
        return TongSL;
    }

    public void setTongSL(int TongSL) {
        this.TongSL = TongSL;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String MaKH) {
        this.MaKH = MaKH;
    }

    public String getMaBan() {
        return MaBan;
    }

    public void setMaBan(String MaBan) {
        this.MaBan = MaBan;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }

    
}
