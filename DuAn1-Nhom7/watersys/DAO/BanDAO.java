/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package watersys.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import watersys.Entity.Ban;
import watersys.Entity.NhanVien;
import watersys.Utilities.XJdbc;

/**
 *
 * @author xuanc
 */
public class BanDAO extends waterSysDAO<Ban, String> {

    String INSERT_SQL = "INSERT INTO Ban(MaBan) VALUES(?,?)";
    String UPDATE_SQL = "UPDATE Ban SET  TrangThai=? WHERE MaBan=?";
    String DELETE_SQL = "DELETE FROM Ban WHERE MaBan=?";
    String SELECT_ALL_SQL = "SELECT * FROM Ban";
    String SELECT_BY_ID_SQL = "SELECT * FROM Ban WHERE MaBan=?";

    @Override
    public void insert(Ban entity) {
        XJdbc.executeUpdate(INSERT_SQL,
                entity.getMaBan(),
                entity.getTrangThai());

    }

    @Override
    public void update(Ban entity) {
        XJdbc.executeUpdate(UPDATE_SQL,
                entity.getTrangThai(),
                entity.getMaBan());
    }

    @Override
    public void delete(String id) {
        XJdbc.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public Ban selectById(String id) {
        List<Ban> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<Ban> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    protected List<Ban> selectBySQL(String sql, Object... args) {
        List<Ban> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                Ban entity = new Ban();
                entity.setMaBan(rs.getString("MaBan"));
//                entity.setTrangThai(rs.getString("TrangThai"));
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

    public List<Object[]> getHoaDonByMaBan(String maBan) {
        String sql = "{call sp_GetByMaBan(?)}";
        String[] cols = {"MaHD", "TenNuoc", "Loai", "SoLuong", "DonGia", "MaBan"};
        return this.getListOfArray(sql, cols, maBan);
    }
}
