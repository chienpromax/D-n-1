package watersys.DAO;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import watersys.Entity.NhanVien;
import watersys.Utilities.XJdbc;

/**
 *
 * @author balis
 */
public class NhanVienDAO extends waterSysDAO<NhanVien, String> {

    String INSERT_SQL = "INSERT NhanVien (MaNV, MatKhau, VaiTro, HoTen, SDT, GioiTinh, NgaySinh, Luong, Hinh, Email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    String UPDATE_SQL = "UPDATE NhanVien SET MatKhau=?, VaiTro=?, HoTen=?, SDT=?, GioiTinh=?, NgaySinh=?, Luong=?, Hinh=?, Email=? WHERE MaNV=?";
    String DELETE_SQL = "DELETE FROM NhanVien WHERE MaNV=?";
    String SELECT_ALL_SQL = "SELECT * FROM NhanVien";
    String SELECT_BY_ID_SQL = "SELECT * FROM NhanVien WHERE MaNV=?";

    @Override
    public void insert(NhanVien entity) {
        XJdbc.executeUpdate(INSERT_SQL,
                entity.getMaNV(),
                entity.getMatKhau(),
                entity.isVaiTro(),
                entity.getHoTen(),
                entity.getSDT(),
                entity.isGioiTinh(),
                entity.getNgaySinh(),
                entity.getLuong(),
                entity.getHinh(),
                entity.getEmail());
    }

    @Override
    public void update(NhanVien entity) {
        XJdbc.executeUpdate(UPDATE_SQL,
                entity.getMatKhau(),
                entity.isVaiTro(),
                entity.getHoTen(),
                entity.getSDT(),
                entity.isGioiTinh(),
                entity.getNgaySinh(),
                entity.getLuong(),
                entity.getHinh(),
                entity.getEmail(),
                entity.getMaNV());
    }

    @Override
    public void delete(String id) {
        XJdbc.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public NhanVien selectById(String id) {
        List<NhanVien> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<NhanVien> selectByKeyword(String keyword) {
        String SQL = "SELECT * FROM NhanVien WHERE MaNV LIKE ?";
        return this.selectBySQL(SQL, "%" + keyword + "%");
    }
    public NhanVien selectByEmail(String email) {
        String sql = "SELECT * FROM NhanVien WHERE Email=?";
        List<NhanVien> list = this.selectBySQL(sql, email);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public NhanVien selectByTaiKhoan(String id) {
        String sql = "SELECT * FROM NhanVien WHERE MaNV=?";
        List<NhanVien> list = this.selectBySQL(sql, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NhanVien> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    protected List<NhanVien> selectBySQL(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                NhanVien entity = new NhanVien();
                entity.setMaNV(rs.getString("MaNV"));
                entity.setMatKhau(rs.getString("MatKhau"));
                entity.setVaiTro(rs.getBoolean("VaiTro"));
                entity.setHoTen(rs.getString("HoTen"));
                entity.setSDT(rs.getString("SDT"));
                entity.setGioiTinh(rs.getBoolean("GioiTinh"));
                entity.setNgaySinh(rs.getDate("NgaySinh"));
                entity.setLuong(rs.getFloat("Luong"));
                entity.setHinh(rs.getString("Hinh"));
                entity.setEmail(rs.getString("Email"));

                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
