/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package watersys.Entity;

/**
 *
 * @author xuanc
 */
public class Ban {
    public String maBan;
    public String trangThai;
    
    public String toString() {
        return maBan;
    }

    public Ban() {
    }

    public Ban(String maBan, String trangThai) {
        this.maBan = maBan;
        this.trangThai = trangThai;
    }

    public String getMaBan() {
        return maBan;
    }

    public void setMaBan(String maBan) {
        this.maBan = maBan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    
    
}
