/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package watersys.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import watersys.Utilities.XJdbc;

/**
 *
 * @author xuanc
 */
public class ThongKeDAO {

    private List<Object[]> getListOfArray(String sql, String[] cols, Object... args) {
        try {
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Object[]> getTongSL() {
        String sql = "{call sp_TongSL()}";
        String[] cols = {"TongSoLuong"};
        return this.getListOfArray(sql, cols);
    }

    public List<Object[]> getDTNgay(String ThongKe) {
        String sql = "{call sp_TongDTNgay(?)}";
        String[] cols = {"TongDoanhThu"};
        return this.getListOfArray(sql, cols, ThongKe);
    }
    
    public List<Object[]> getDTThang(int nam, int thang) {
    String sql = "{call sp_TongDTThang(?, ?)}";
    String[] cols = {"TongDoanhThu"};
    return this.getListOfArray(sql, cols, nam, thang);
}

    public List<Object[]> getDTNam(int nam) {
        String sql = "{call sp_TongDTNam(?)}";
        String[] cols = {"TongDoanhThu"};
        return this.getListOfArray(sql, cols, nam);
    }

    public List<Object[]> getDoanhThu(String ngaybd, String ngaykt) {
        String sql = "{call sp_DoanhThu(?, ?)}";
        String[] cols = {"Ngay", "Doanh thu cao nhat", "Doanh thu thap nhat", "tonghoadon"};
        return this.getListOfArray(sql, cols, ngaybd, ngaykt);
    }

    public List<Object[]> getDoanhThuBang() {
        String sql = "{call sp_DoanhThuBang}";
        String[] cols = {"Thang", "DoanhThuKho", "Tổng Số Lượng", "tonghoadon"};
        return this.getListOfArray(sql, cols);
    }

    public List<Object[]> getSanPham() {
        String sql = "{call sp_SanPham()}";
        String[] cols = {"TenNuoc", "Loai", "NgayThem", "Hàng tồn kho", "TrangThai"};
        return this.getListOfArray(sql, cols);
    }

    public List<Object[]> getHoaDon(Date ngay) {
        String sql = "{call sp_NgayTaoHoaDon(?)}";
        String[] cols = {"MaHD", "HoTenKH", "NgayTao", "TenSP", "TongSL", "TongTien", "TrangThai", "MaNV", "MaKH", "MaBan"};
        return this.getListOfArray(sql, cols, ngay);
    }
    
        public List<Object[]> getTrangThai(String id) {
        String sql = "{call sp_trangThai(?)}";
        String[] cols = {"MaHD", "HoTenKH", "NgayTao", "TenSP", "TongSL", "TongTien", "TrangThai", "MaNV", "MaKH", "MaBan"};
        return this.getListOfArray(sql, cols, id);
    }

}
