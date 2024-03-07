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
import watersys.Entity.KhachHang;
import watersys.Entity.HoaDon;
import watersys.Utilities.XJdbc;

/**
 *
 * @author xuanc
 */
public class HoaDonDAO extends waterSysDAO<HoaDon, String> {

    String INSERT_SQL = "INSERT HoaDon ([HoTenKH], [NgayTao], TenSP, TongSL, TongTien, [MaNV], MaKH, MaBan, TrangThai) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
//    String UPDATE_SQL = "UPDATE HoaDon SET HoTenKH=?, NgayTao=?, TenSP=?, TongSL=?, TongTien=?, MaNV=?, MaKH=?, MaBan=?, TrangThai=? WHERE MaHD=?";
    String UPDATE_SQL = "UPDATE HoaDon SET TenSP=?, TongSL=?, TongTien=?, TrangThai=? WHERE MaHD=?";
    String DELETE_SQL = "DELETE FROM HoaDon WHERE MaHD=?";
    String SELECT_ALL_SQL = "SELECT * FROM HoaDon";
    String SELECT_BY_TT_SQL = "SELECT * FROM HoaDon where trangThai like N'Chưa thanh toán'";
    String SELECT_BY_ID_SQL = "SELECT * FROM HoaDon WHERE MaHD=?";
    String SELECT_BY_TrangThai_SQL = "SELECT * FROM HoaDon WHERE TrangThai=?";
    String SELECT_NAME_BY_ID_SQL = "SELECT HoTenKH FROM HoaDon WHERE MaHD=?";

    public void updateTenSPVaTongTien(String maHD, String tenSanPham, float tongTien) {
        String UPDATE_SQL = "UPDATE ChiTietSP SET TenMuoc=?, DonGia=? WHERE MaHD=?";
        XJdbc.executeUpdate(UPDATE_SQL, tenSanPham, tongTien, maHD);
    }

    @Override
    public void insert(HoaDon entity) {
        XJdbc.executeUpdate(INSERT_SQL,
                entity.getHoTenKH(),
                entity.getNgayTao(),
                entity.getTenpSP(),
                entity.getTongSL(),
                entity.getTongTien(),
                entity.getMaNV(),
                entity.getMaKH(),
                entity.getMaBan(),
                entity.getTrangThai());
    }

    @Override
    public void update(HoaDon entity) {
        XJdbc.executeUpdate(UPDATE_SQL,
                //                entity.getHoTenKH(),
                //                entity.getNgayTao(),
                entity.getTenpSP(),
                entity.getTongSL(),
                entity.getTongTien(),
                //                entity.getMaNV(),
                //                entity.getMaKH(),
                //                entity.getMaBan(),
                entity.getTrangThai(),
                entity.getMaHD());
    }

    @Override
    public void delete(String id) {
        XJdbc.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public HoaDon selectById(String id) {
        List<HoaDon> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    public HoaDon getHoaDonByMaHD(String maHD) {
    String SELECT_BY_ID_SQL = "SELECT * FROM HoaDon WHERE MaHD=?";
    List<HoaDon> list = selectBySQL(SELECT_BY_ID_SQL, maHD);

    if (!list.isEmpty()) {
        return list.get(0);
    }

    return null;
}


    public float getTongDonGiaByMaHD(String maHD) {
        String SELECT_TONG_DON_GIA_SQL = "SELECT SUM(DonGia) AS TongDonGia FROM ChiTietHD WHERE MaHD = ?";
        try {
            ResultSet rs = XJdbc.executeQuery(SELECT_TONG_DON_GIA_SQL, maHD);
            if (rs.next()) {
                return rs.getFloat("TongDonGia");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Trả về 0 nếu có lỗi hoặc không có dữ liệu
    }

    public HoaDon getTenByMaHD(String maHD) {
        List<HoaDon> list = this.selectBySQL(SELECT_NAME_BY_ID_SQL, maHD);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<HoaDon> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }
    public List<HoaDon> selectAllTT() {
        return this.selectBySQL(SELECT_BY_TT_SQL);
    }

    @Override
    protected List<HoaDon> selectBySQL(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                HoaDon entity = new HoaDon();
                entity.setMaHD(rs.getString("MaHD"));
                entity.setNgayTao(rs.getDate("NgayTao"));
                entity.setTenpSP(rs.getString("TatCaSP"));
                entity.setTongSL(rs.getInt("TongSL"));
                entity.setTongTien(rs.getFloat("TongTien"));
                entity.setMaNV(rs.getString("MaNV"));
                entity.setMaKH(rs.getString("MaKH"));
                entity.setMaBan(rs.getString("MaBan"));
                entity.setTrangThai(rs.getString("TrangThai"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected List<HoaDon> selectBySQL1(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                HoaDon entity = new HoaDon();
                entity.setMaHD(rs.getString("MaHD"));
                entity.setNgayTao(rs.getDate("NgayTao"));
                entity.setMaNV(rs.getString("MaNV"));
                entity.setMaBan(rs.getString("MaBan"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
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

    public List<Object[]> getXuatHoaDon(String maHD) {
        String sql = "{call sp_xuatHD(?)}";
        String[] cols = {"MaHD", "HoTenKH", "NgayTao", "TenSP", "TongSL", "TongTien" , "MaBan"};
        return this.getListOfArray(sql, cols, maHD);
    }
}
