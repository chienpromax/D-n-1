create database QL_BanNuoc
use QL_BanNuoc
create table NhanVien(
	MaNV varchar (15) primary key NOT NULL,
	MatKhau varchar (15) not null,
	VaiTro bit default 0 not null,
	HoTen nvarchar(25) not null,
	SDT int not null,
	GioiTinh bit default 0,
	NgaySinh date not null,
	Luong float not null,
	Hinh varchar(25) not null,
	Email varchar(50) not null,
)

create table SanPham(
	MaSP varchar(15) primary key NOT NULL,
	Loai nvarchar(25) not null,
	TenNuoc nvarchar(25) not null,
	Anh nvarchar(25) not null,
	DonGia float not null,
	SoLuongSP int not null,
	NgayThem date not null,
	MoTa nvarchar(50),
	MaNV varchar(15) not null
	foreign key(MaNV) references NhanVien(MaNV) on update cascade
)
create table KhachHang(
	MaKH varchar(15) primary key NOT NULL,
	HoTenKH nvarchar(25) not null,
	GioiTinh bit default 1,
	SDT int not null,
	NgayMua date not null,
	DanhGia nvarchar(50),
)
create table Ban(
	MaBan varchar(15) primary key,
	TrangThai nvarchar(15)
)
CREATE TABLE HoaDon (
    MaHD VARCHAR(15) PRIMARY KEY  NOT NULL,
    NgayTao DATE NOT NULL,
	TrangThai nvarchar(15),
	TongTien float,
    MaNV VARCHAR(15) NOT NULL,
    MaKH VARCHAR(15),
    MaBan VARCHAR(15),
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV),
    FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH),
    FOREIGN KEY (MaBan) REFERENCES Ban(MaBan)
)
-- Tạo chuỗi số tự tăng với kiểu dữ liệu NVARCHAR
CREATE SEQUENCE HoaDon_Seq AS INTEGER
    START WITH 1
    INCREMENT BY 1;

-- Kích hoạt chuỗi số tự tăng cho cột MaHD
ALTER TABLE HoaDon
    ADD CONSTRAINT DF_HoaDon_MaHD
    DEFAULT 'HD' + RIGHT('00000' + CAST(NEXT VALUE FOR HoaDon_Seq AS NVARCHAR(5)), 5) FOR MaHD;
GO
create table ChiTietHD(
	ID INT identity(1,1) primary key NOT NULL,
	SoLuong int not null,
	MaSP varchar(15),
	MaHD varchar(15),
	foreign key(MaSP) references SanPham(MaSP),
	foreign key(MaHD) references HoaDon(MaHD)
)
INSERT NhanVien ([MaNV], MatKhau, VaiTro, [HoTen], SDT, [NgaySinh], [GioiTinh], Luong, [Hinh], Email) VALUES ('NV01', '12345', 0, N'Trần xuân chiến', '0359690062', '07-11-2023', 1, 9000000, N'yea.jpg',N'xuanchient033@gmail.com')
INSERT NhanVien ([MaNV], MatKhau, VaiTro, [HoTen], SDT, [NgaySinh], [GioiTinh], Luong, [Hinh], Email) VALUES ('NV02', '12345', 1, N'LỮ HUY CƯỜNG','0359690062', '07-11-2023', 0, 9000000, N'Image.jpg',N'chientxpd08548@fpt.edu.vn')
INSERT NhanVien ([MaNV], MatKhau, VaiTro, [HoTen], SDT, [NgaySinh], [GioiTinh], Luong, [Hinh], Email) VALUES ('NV03', '12345', 1, N'ĐỖ VĂN MINH','0359690062', '07-11-2023', 0, 9000000, N'GAME.png',N'xuanchient033@gmail.com')
INSERT NhanVien ([MaNV], MatKhau, VaiTro, [HoTen], SDT, [NgaySinh], [GioiTinh], Luong, [Hinh], Email) VALUES ('NV04', '12345', 1, N'NGUYỄN TẤN HIẾU','0359690062', '07-11-2023', 1, 9000000, N'GAME.png',N'xuanchient033@gmail.com')
INSERT NhanVien ([MaNV], MatKhau, VaiTro, [HoTen], SDT, [NgaySinh], [GioiTinh], Luong, [Hinh], Email) VALUES ('NV05', '12345', 0, N'TRẦN VĂN NAM','0359690062', '07-11-2023', 1, 9000000, N'GAME.png',N'xuanchient033@gmail.com')


INSERT SanPham ([MaSP], Loai, [TenNuoc], Anh, DonGia, [NgayThem], MoTa, MaNV, SoLuongSP) VALUES ('SP01', N'Nước ngọt', N'Pesi', N'GAME.png', 10000, '07-11-2023', N'nước uống ngon tuyệt vời', N'NV01', 9)
INSERT SanPham ([MaSP], Loai, [TenNuoc], Anh, DonGia, [NgayThem], MoTa, MaNV, SoLuongSP) VALUES ('SP02', N'Nước ngọt', N'coca cola', N'GAME.png', 11000, '07-11-2023', N'nước uống ngon tuyệt vời', N'NV01', 9)
INSERT SanPham ([MaSP], Loai, [TenNuoc], Anh, DonGia, [NgayThem], MoTa, MaNV, SoLuongSP) VALUES ('SP03', N'Nước khoáng', N'aqua vina', N'GAME.png', 4000, '07-11-2023', N'nước uống ngon tuyệt vời', N'NV01', 9)
INSERT SanPham ([MaSP], Loai, [TenNuoc], Anh, DonGia, [NgayThem], MoTa, MaNV, SoLuongSP) VALUES ('SP04', N'Nước ép', N'nước cam', N'GAME.png', 13000, '07-11-2023', N'nước uống ngon tuyệt vời', N'NV01', 9)
INSERT SanPham ([MaSP], Loai, [TenNuoc], Anh, DonGia, [NgayThem], MoTa, MaNV, SoLuongSP) VALUES ('SP05', N'Nước ngọt', N'7 up', N'GAME.png', 14000, '07-11-2023', N'nước uống ngon tuyệt vời', N'NV01', 9)

INSERT KhachHang ([MaKH], HoTenKH, GioiTinh, SDT, NgayMua, DanhGia) VALUES (N'KH01', N'Trần xuân chiến', 1, '0359698875', '07-11-2023', N'tuyệt vời')
INSERT KhachHang ([MaKH], HoTenKH, GioiTinh, SDT, NgayMua, DanhGia) VALUES (N'KH02', N'Trần xuân chiến1', 0, '0359698875', '07-11-2023', N'tuyệt vời')
INSERT KhachHang ([MaKH], HoTenKH, GioiTinh, SDT, NgayMua, DanhGia) VALUES (N'KH03', N'Trần xuân chiến2', 1, '0359698875', '07-11-2023', N'tuyệt vời')
INSERT KhachHang ([MaKH], HoTenKH, GioiTinh, SDT, NgayMua, DanhGia) VALUES (N'KH04', N'Trần xuân chiến3', 1, '0359698875', '07-11-2023', N'tuyệt vời')

INSERT Ban ([MaBan], [TrangThai]) VALUES (N'Ban1', N'Đặt bàn')
INSERT Ban ([MaBan], [TrangThai]) VALUES (N'Ban2', N'Đang dùng')
INSERT Ban ([MaBan], [TrangThai]) VALUES (N'Ban3', N'Trống')
INSERT Ban ([MaBan], [TrangThai]) VALUES (N'Ban4', N'Trống')
INSERT Ban ([MaBan], [TrangThai]) VALUES (N'Ban5', N'Trống')
INSERT Ban ([MaBan], [TrangThai]) VALUES (N'Ban6', N'Trống')
INSERT Ban ([MaBan], [TrangThai]) VALUES (N'Ban7', N'Trống')
INSERT Ban ([MaBan], [TrangThai]) VALUES (N'Ban8', N'Trống')
INSERT Ban ([MaBan], [TrangThai]) VALUES (N'Ban9', N'Trống')
INSERT Ban ([MaBan], [TrangThai]) VALUES (N'Ban10', N'Trống')
INSERT Ban ([MaBan], [TrangThai]) VALUES (N'Pha Chế', N'Trống')

INSERT HoaDon ( [NgayTao], TongTien, [MaNV], MaKH, MaBan, TrangThai) VALUES ( '07-11-2023', 50000, 'NV01', 'KH01', 'Ban1', N'chưa thanh toán')
INSERT HoaDon ( [NgayTao], TongTien, [MaNV], MaKH, MaBan, TrangThai) VALUES ( '07-11-2023', 50000, 'NV02', 'KH02', 'Ban2', N'chưa thanh toán')
INSERT ChiTietHD ( [SoLuong], MaSP, MaHD) VALUES (5, 'SP02', 'HD00001')
INSERT ChiTietHD ( [SoLuong], MaSP, MaHD) VALUES (5, 'SP03', 'HD00001')

select id,ChiTietHD.MaHD, ChiTietHD.MaSP,TenNuoc,Loai,ChiTietHD.SoLuong from ChiTietHD inner join SanPham on ChiTietHD.MaSP = SanPham.MaSP

go
CREATE PROC sp_DoanhThuBang (@start_date DATE, @end_date DATE)
AS 
BEGIN
    SELECT
        CAST(NgayTao AS DATE) as Ngay,
        MAX(TongTien) AS 'Doanh thu cao nhat',
        --SUM(TongSL) as 'Tổng Số Lượng',
        COUNT(MaHD) AS tonghoadon
    FROM HoaDon
    WHERE NgayTao BETWEEN @start_date AND @end_date
    GROUP BY CAST(NgayTao AS DATE);
END;

---- Gọi thủ tục với khoảng thời gian cụ thể
--EXEC sp_DoanhThuBang @start_date = '2023-11-20', @end_date = '2023-11-25';
--drop proc sp_DoanhThuBang

go
--CREATE PROC sp_TongSL
--AS 
--BEGIN
--    SELECT
--		 SUM(TongSL) AS TongSoLuong
--    FROM HoaDon;
--END;

go
CREATE PROC sp_TongDTNgay
    @ngay DATE
AS 
BEGIN
    SELECT
        SUM(TongTien) AS TongDoanhThu
    FROM HoaDon
    WHERE CAST(NgayTao AS DATE) = @ngay;
END;
go

CREATE PROC sp_TongDTThang
    @Thang int
AS 
BEGIN
    SELECT
        SUM(TongTien) AS TongDoanhThu
    FROM HoaDon
    WHERE MONTH(NgayTao) = @Thang;
END;
go

CREATE PROC sp_TongDTNam
    @Nam INT,
    @Thang INT
AS 
BEGIN
    SELECT
        SUM(TongTien) AS TongDoanhThu
    FROM HoaDon
    WHERE YEAR(NgayTao) = @Nam AND MONTH(NgayTao) = @Thang;
END;
go

CREATE PROC sp_SanPham
AS 
BEGIN
    SELECT
		 TenNuoc,
		 Loai,
		 NgayThem,
		 SoLuongSP as N'Hàng tồn kho',
		 CASE 
            WHEN DATEDIFF(MONTH, NgayThem, GETDATE()) <= 6 THEN N'Còn hạn sử dụng'
            ELSE N'Hết hạn sử dụng' 
         END AS TrangThai
    FROM SanPham
	ORDER BY SoLuongSP DESC
END;
go

CREATE PROCEDURE sp_GetByMaBan
    @MaBan NVARCHAR(20)
AS
BEGIN
    SELECT ChiTietHD.MaHD, SoLuong, (ChiTietHD.SoLuong*sanpham.DonGia) as 'DonGia', MaBan
    FROM HoaDon
    inner JOIN ChiTietHD ON HoaDon.MaHD = ChiTietHD.MaHD
	inner JOIN SanPham ON ChiTietHD.MaSP = SanPham.MaSP
    WHERE MaBan = @MaBan;
END;
exec sp_GetByMaBan 'ban1'
go

--select * from HoaDon
CREATE PROC sp_NgayTaoHoaDon
    @ngay DATE = NULL
AS 
BEGIN
    SELECT
        *
    FROM HoaDon
    WHERE @ngay IS NULL OR CAST(NgayTao AS DATE) = @ngay;
END;

go
CREATE PROC sp_trangThai
    @trangThai NVARCHAR(MAX) = NULL
AS 
BEGIN
    SELECT
        *
    FROM HoaDon
    WHERE @trangThai IS NULL OR TrangThai = @trangThai;
END;

exec sp_trangThai N'Chưa Thanh toán';
drop proc sp_trangThai



