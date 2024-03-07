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
public class NhanVien {
    private String maNV;
    private String matKhau;
    private boolean vaiTro;
    private String hoTen;
    private String SDT;
    private boolean gioiTinh;
    private Date ngaySinh;
    private Float luong;
    private String hinh;
    private String email;

    public NhanVien() {
    }

    public NhanVien(String maNV, String matKhau, boolean vaiTro, String hoTen, String SDT, boolean gioiTinh, Date ngaySinh, Float luong, String hinh, String email) {
        this.maNV = maNV;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
        this.hoTen = hoTen;
        this.SDT = SDT;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.luong = luong;
        this.hinh = hinh;
        this.email = email;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public boolean isVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(boolean vaiTro) {
        this.vaiTro = vaiTro;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public Float getLuong() {
        return luong;
    }

    public void setLuong(Float luong) {
        this.luong = luong;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    

    @Override
    public String toString() {
        return this.hoTen;
    }
    
}
