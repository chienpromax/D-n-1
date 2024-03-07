/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package watersys.Entity;

/**
 *
 * @author xuanc
 */
public class ChiTietSP {
    private int ID;
    private String TenMuoc;
    private String Loai;
    private int SoLuong;
    private float DonGia;
    private String MaSP;
    private String MaHD;

    public ChiTietSP() {
    }

    public ChiTietSP(int ID, String TenMuoc, String Loai, int SoLuong, float DonGia, String MaSP, String MaHD) {
        this.ID = ID;
        this.TenMuoc = TenMuoc;
        this.Loai = Loai;
        this.SoLuong = SoLuong;
        this.DonGia = DonGia;
        this.MaSP = MaSP;
        this.MaHD = MaHD;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTenMuoc() {
        return TenMuoc;
    }

    public void setTenMuoc(String TenMuoc) {
        this.TenMuoc = TenMuoc;
    }

    public String getLoai() {
        return Loai;
    }

    public void setLoai(String Loai) {
        this.Loai = Loai;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public float getDonGia() {
        return DonGia;
    }

    public void setDonGia(float DonGia) {
        this.DonGia = DonGia;
    }

    public String getMaSP() {
        return MaSP;
    }

    public void setMaSP(String MaSP) {
        this.MaSP = MaSP;
    }

    public String getMaHD() {
        return MaHD;
    }

    public void setMaHD(String MaHD) {
        this.MaHD = MaHD;
    }

    
    
}
