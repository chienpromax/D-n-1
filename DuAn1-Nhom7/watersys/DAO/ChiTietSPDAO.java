/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package watersys.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import watersys.DAO.waterSysDAO;
import watersys.Entity.ChiTietSP;
import watersys.Entity.NhanVien;
import watersys.Entity.ChiTietSP;
import watersys.Utilities.XJdbc;

/**
 *
 * @author xuanc
 */
public class ChiTietSPDAO extends waterSysDAO<ChiTietSP, Integer> {

    String INSERT_SQL = "INSERT INTO ChiTietHD(TenNuoc, Loai, SoLuong, DonGia, MaSP , MaHD) VALUES(?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE ChiTietHD SET TenNuoc=?, Loai=?, SoLuong=?, DonGia=?, MaSP=?, MaHD=? WHERE ID=?";
    String DELETE_SQL = "DELETE FROM ChiTietHD WHERE ID=?";
    String SELECT_ALL_SQL = "SELECT * FROM ChiTietHD";
    String SELECT_BY_ID_SQL = "SELECT * FROM ChiTietHD WHERE id=?";
    String DELETE_SQL_ALL = "DELETE FROM ChiTietHD where MaHD=?";


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

    public List<Object[]> getChiTietSP() {
        String sql = "{call sp_ChiTietSP()}";
        
        String[] cols = {"ID", "MaHD", "MaSP", "TenNuoc", "Loai", "SoLuong", "Don gia"};
        return this.getListOfArray(sql, cols);
    }
    
     public ChiTietSP getThongTinTenSPVaTongTien(String maHD) {
        String SQL = "SELECT TenMuoc, SUM(DonGia) AS TongTien FROM ChiTietSP WHERE MaHD=? GROUP BY TenMuoc";
        List<ChiTietSP> list = this.selectBySQL(SQL, maHD);

        if (!list.isEmpty()) {
            // Chỉ cần lấy một phần tử vì thông tin Tên SP và Tổng Tiền đã được tổng hợp từ nhiều sản phẩm
            return list.get(0);
        }

        return null;
    }
    
    @Override
    public void insert(ChiTietSP entity) {
        XJdbc.executeUpdate(INSERT_SQL,
                entity.getTenMuoc(),
                entity.getLoai(),
                entity.getSoLuong(),
                entity.getDonGia(),
                entity.getMaSP(),
                entity.getMaHD());
    }

    @Override
    public void update(ChiTietSP entity) {
        if (entity != null) {
        XJdbc.executeUpdate(UPDATE_SQL,
                entity.getTenMuoc(),
                entity.getLoai(),
                entity.getSoLuong(),
                entity.getDonGia(),
                entity.getMaSP(),
                entity.getMaHD());
    } else {
        // Xử lý khi đối tượng entity là null
        System.out.println("Đối tượng entity là null");
    }
    }

    @Override
    public void delete(Integer id) {
        XJdbc.executeUpdate(DELETE_SQL, id);
    }
    
    public void deleteAll(String id) {
        XJdbc.executeUpdate(DELETE_SQL_ALL, id);
    }
    

    @Override
    public ChiTietSP selectById(Integer id) {
        List<ChiTietSP> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    
    
    @Override
    public List<ChiTietSP> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    protected List<ChiTietSP> selectBySQL(String sql, Object... args) {
        List<ChiTietSP> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                ChiTietSP entity = new ChiTietSP();
                entity.setID(rs.getInt("ID"));
                entity.setMaHD(rs.getString("MaHD"));
                entity.setMaSP(rs.getString("MaSP"));
                entity.setTenMuoc(rs.getString("TenNuoc"));
                entity.setLoai(rs.getString("Loai"));
                entity.setSoLuong(rs.getInt("SoLuong"));
                entity.setDonGia(rs.getFloat("DonGia"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
