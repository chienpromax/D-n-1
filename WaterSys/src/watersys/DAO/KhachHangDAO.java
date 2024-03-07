package watersys.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import watersys.Entity.KhachHang;
import watersys.Utilities.XJdbc;

public class KhachHangDAO extends waterSysDAO<KhachHang, String> {

    String INSERT_SQL = "INSERT INTO KhachHang(MaKH, HoTenKH, GioiTinh, SDT, NgayMua, DanhGia) VALUES(?, ?, ?, ?, ?, ?)";
    String UPDATE_SQL = "UPDATE KhachHang SET HoTenKH=?, GioiTinh=?, SDT=?, NgayMua=?, DanhGia=? WHERE MaKH=?";
    String DELETE_SQL = "DELETE FROM KhachHang WHERE MaKH=?";
    String SELECT_ALL_SQL = "SELECT * FROM KhachHang";
    String SELECT_BY_ID_SQL = "SELECT * FROM KhachHang WHERE MaKH=?";

    @Override
    public void insert(KhachHang entity) {
        XJdbc.executeUpdate(INSERT_SQL,
                entity.getMaKH(),
                entity.getHoTenKH(),
                entity.isGioiTinh(),
                entity.getSDT(),
                entity.getNgayMua(),
                entity.getDanhGia());
    }

    @Override
    public void update(KhachHang entity) {
        XJdbc.executeUpdate(UPDATE_SQL,
                entity.getHoTenKH(),
                entity.isGioiTinh(),
                entity.getSDT(),
                entity.getNgayMua(),
                entity.getDanhGia(),
                entity.getMaKH());
    }

    @Override
    public void delete(String id) {
        XJdbc.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public KhachHang selectById(String id) {
        List<KhachHang> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<KhachHang> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    /**
     *
     * @param sql
     * @param args
     * @return
     */
    @Override
    protected List<KhachHang> selectBySQL(String sql, Object... args) {
        List<KhachHang> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                KhachHang entity = new KhachHang();
                entity.setMaKH(rs.getString("MaKH"));
                entity.setHoTenKH(rs.getString("HoTenKH"));
                entity.setGioiTinh(rs.getBoolean("GioiTinh"));
                entity.setSDT(rs.getString("SDT"));
                entity.setNgayMua(rs.getDate("NgayMua"));
                entity.setDanhGia(rs.getString("DanhGia"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
