package watersys.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import watersys.Entity.ChiTietSP;
import watersys.Entity.NhanVien;
import watersys.Entity.SanPham;
import watersys.Utilities.XJdbc;

public class SanPhamDAO extends waterSysDAO<SanPham, String> {

    String INSERT_SQL = "INSERT INTO SanPham(MaSP, Loai, TenNuoc, Anh, DonGia, NgayThem, MoTa, MaNV, SoLuong) "
            + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    String UPDATE_SQL = "UPDATE SanPham "
            + "SET Loai=?, TenNuoc=?, Anh=?, DonGia=?, NgayThem=?, MoTa=?, MaNV=?, SoLuong=? "
            + "WHERE MaSP=?";
    String DELETE_SQL = "DELETE FROM SanPham WHERE MaSP=?";
    String SELECT_ALL_SQL = "SELECT * FROM SanPham";
    String SELECT_BY_ID_SQL = "SELECT * FROM SanPham WHERE MaSP=?";

    public void updateSoLuong(String maSP, int soLuongGiam) {
        String UPDATE_SO_LUONG_SQL = "UPDATE SanPham SET SoLuong = SoLuong - ? WHERE MaSP = ?";
        XJdbc.executeUpdate(UPDATE_SO_LUONG_SQL, soLuongGiam, maSP);
    }

    public void updateXoaSoLuong(String maSP, int soLuongGiam) {
        String UPDATE_SO_LUONG_SQL = "UPDATE SanPham SET SoLuong = SoLuong + ? WHERE MaSP = ?";
        XJdbc.executeUpdate(UPDATE_SO_LUONG_SQL, soLuongGiam, maSP);
    }

    public int selectSoLuong(String maSP) {
        String SELECT_Sl_BY_ID_SQL = "SELECT SoLuong FROM SanPham WHERE MaSP=?";
        List<SanPham> list = this.selectBySQL(SELECT_Sl_BY_ID_SQL, maSP);

        // Kiểm tra nếu danh sách trống trước khi cố gắng truy cập phần tử đầu tiên
        if (list.isEmpty()) {
            return 0; // hoặc giá trị mặc định khác tùy thuộc vào logic của bạn
        }

        // Lấy giá trị SoLuong từ đối tượng SanPham
        SanPham sanPham = list.get(0);
        return sanPham.getSoLuong();
    }

    @Override
    public void insert(SanPham entity) {
        XJdbc.executeUpdate(INSERT_SQL,
                entity.getMaSP(),
                entity.getLoai(),
                entity.getTenNuoc(),
                entity.getAnh(),
                entity.getDonGia(),
                entity.getNgayThem(),
                entity.getMoTa(),
                entity.getMaNV(),
                entity.getSoLuong());
    }

    @Override
    public void update(SanPham entity) {
        XJdbc.executeUpdate(UPDATE_SQL,
                entity.getLoai(),
                entity.getTenNuoc(),
                entity.getAnh(),
                entity.getDonGia(),
                entity.getNgayThem(),
                entity.getMoTa(),
                entity.getMaNV(),
                entity.getSoLuong(),
                entity.getMaSP());
    }

    @Override
    public void delete(String id) {
        XJdbc.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public SanPham selectById(String id) {
        List<SanPham> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<SanPham> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    public List<SanPham> selectByKeyword(String keyword) {
        String SQL = "SELECT * FROM SanPham WHERE MaSP LIKE ?";
        return this.selectBySQL(SQL, "%" + keyword + "%");
    }

    @Override
    protected List<SanPham> selectBySQL(String sql, Object... args) {
        List<SanPham> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                SanPham entity = new SanPham();
                entity.setMaSP(rs.getString("MaSP"));
                entity.setLoai(rs.getString("Loai"));
                entity.setTenNuoc(rs.getString("TenNuoc"));
                entity.setAnh(rs.getString("Anh"));
                entity.setDonGia(rs.getFloat("DonGia"));
                entity.setNgayThem(rs.getDate("NgayThem"));
                entity.setMoTa(rs.getString("MoTa"));
                entity.setMaNV(rs.getString("MaNV"));
                entity.setSoLuong(rs.getInt("SoLuong"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    public watersys.UI.SanPham MaSP(String tenSanPham) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
}
