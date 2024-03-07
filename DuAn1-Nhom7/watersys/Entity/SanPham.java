/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package watersys.Entity;
import java.util.Date;

public class SanPham {
    private String maSP;
    private String loai;
    private String tenNuoc;
    private String anh;
    private float donGia;
    private Date ngayThem;
    private String moTa;
    private String maNV;
    private int SoLuong;
    
    
    public SanPham() {
    }

    public SanPham(String maSP, String loai, String tenNuoc, String anh, float donGia, Date ngayThem, String moTa, String maNV, int SoLuong) {
        this.maSP = maSP;
        this.loai = loai;
        this.tenNuoc = tenNuoc;
        this.anh = anh;
        this.donGia = donGia;
        this.ngayThem = ngayThem;
        this.moTa = moTa;
        this.maNV = maNV;
        this.SoLuong = SoLuong;
    }
    
    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getTenNuoc() {
        return tenNuoc;
    }

    public void setTenNuoc(String tenNuoc) {
        this.tenNuoc = tenNuoc;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public float getDonGia() {
        return donGia;
    }

    public void setDonGia(float donGia) {
        this.donGia = donGia;
    }

    public Date getNgayThem() {
        return ngayThem;
    }

    public void setNgayThem(Date ngayThem) {
        this.ngayThem = ngayThem;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }
    
    
    
    
//    // Constructor
//    public SanPham(String maSP, String loai, String tenNuoc, String anh, float donGia, Date ngayThem, String moTa, String maNV) {
//        this.maSP = maSP;
//        this.loai = loai;
//        this.tenNuoc = tenNuoc;
//        this.anh = anh;
//        this.donGia = donGia;
//        this.ngayThem = ngayThem;
//        this.moTa = moTa;
//        this.maNV = maNV;
//    }
//    public SanPham() {
//    // Không có tham số
//    }
//    // Getters and Setters
//    public String getMaSP() {
//        return maSP;
//    }
//    
//    public void setMaSP(String maSP) {
//        this.maSP = maSP;
//    }
//    
//    public String getLoai() {
//        return loai;
//    }
//    
//    public void setLoai(String loai) {
//        this.loai = loai;
//    }
//    
//    public String getTenNuoc() {
//        return tenNuoc;
//    }
//    
//    public void setTenNuoc(String tenNuoc) {
//        this.tenNuoc = tenNuoc;
//    }
//    
//    public String getAnh() {
//        return anh;
//    }
//    
//    public void setAnh(String anh) {
//        this.anh = anh;
//    }
//    
//    public float getDonGia() {
//        return donGia;
//    }
//    
//    public void setDonGia(float donGia) {
//        this.donGia = donGia;
//    }
//    
//    public Date getNgayThem() {
//        return ngayThem;
//    }
//    
//    public void setNgayThem(Date ngayThem) {
//        this.ngayThem = ngayThem;
//    }
//    
//    public String getMoTa() {
//        return moTa;
//    }
//    
//    public void setMoTa(String moTa) {
//        this.moTa = moTa;
//    }
//    
//    public String getMaNV() {
//        return maNV;
//    }
//    
//    public void setMaNV(String maNV) {
//        this.maNV = maNV;
//    }

    

    
}
