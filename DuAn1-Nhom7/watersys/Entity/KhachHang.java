package watersys.Entity;

/**
 *
 * @author ADMIN
 */
import java.util.Date;

public class KhachHang {
    private String maKH;
    private String hoTenKH;
    private boolean gioiTinh;
    private String SDT;
    private Date ngayMua;
    private String danhGia;

     public String toString() {
        return hoTenKH;
    }
    
    public KhachHang(String maKH, String hoTenKH, boolean gioiTinh, String SDT, Date ngayMua, String danhGia) {
        this.maKH = maKH;
        this.hoTenKH = hoTenKH;
        this.gioiTinh = gioiTinh;
        this.SDT = SDT;
        this.ngayMua = ngayMua;
        this.danhGia = danhGia;
    }

    public KhachHang() {
        // Không có tham số
    }

    // Getters and Setters
    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getHoTenKH() {
        return hoTenKH;
    }

    public void setHoTenKH(String hoTenKH) {
        this.hoTenKH = hoTenKH;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String sdt) {
        this.SDT = sdt;
    }

    public Date getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(Date ngayMua) {
        this.ngayMua = ngayMua;
    }

    public String getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(String danhGia) {
        this.danhGia = danhGia;
    }
}
