
CREATE DATABASE QL_BanNuoc;

USE QL_BanNuoc;

create table NhanVien(
	MaNV nvarchar (20) primary key NOT NULL,
	MatKhau nvarchar (50) not null,
	VaiTro bit default 0 not null,
	HoTen nvarchar(50) not null,
	SDT nvarchar(24) not null,
	GioiTinh bit default 0,
	NgaySinh date not null,
	Luong float not null,
	Hinh nvarchar(20) not null,
	Email nvarchar(50) not null,
)
create table SanPham(
	MaSP nvarchar(20) primary key NOT NULL,
	Loai nvarchar(50) not null,
	TenNuoc nvarchar(50) not null,
	Anh nvarchar(50) not null,
	DonGia float not null,
	NgayThem date not null,
	MoTa nvarchar(250),
	MaNV nvarchar(20) not null,
	SoLuong int not null
	foreign key(MaNV) references NhanVien(MaNV) on update cascade
)

create table KhachHang(
	MaKH nvarchar(20) primary key NOT NULL,
	HoTenKH nvarchar(50) not null,
	GioiTinh bit default 1,
	SDT nvarchar(20) not null,
	NgayMua date not null,
	DanhGia nvarchar(50),
)
create table Ban(
	MaBan nvarchar(20) primary key,
	TrangThai nvarchar(50)
)
-- Tạo bảng HoaDon
CREATE TABLE HoaDon (
    MaHD NVARCHAR(20) PRIMARY KEY  NOT NULL,
    NgayTao DATE NOT NULL,
    TatCaSP NVARCHAR(100),
    TongSL INT ,
    TongTien FLOAT ,
	TrangThai nvarchar(20),
    MaNV NVARCHAR(20) NOT NULL,
    MaKH NVARCHAR(20) ,
    MaBan NVARCHAR(20),
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV),
    FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH),
    FOREIGN KEY (MaBan) REFERENCES Ban(MaBan)
);
--drop table ChiTietHD
--drop table HoaDon
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
	MaSP nvarchar(20),
	MaHD nvarchar(20),
	foreign key(MaSP) references SanPham(MaSP),
	foreign key(MaHD) references HoaDon(MaHD)
)

GO
CREATE PROC sp_chiTietSP
AS 
BEGIN
    SELECT
        ID, MaHD, ChiTietHD.MaSP, TenNuoc, Loai, ChiTietHD.SoLuong, (ChiTietHD.SoLuong * SanPham.DonGia) as'Don gia'
    FROM ChiTietHD
    INNER JOIN SanPham ON SanPham.MaSP = ChiTietHD.MaSP
END;
exec sp_chiTietSP
GO
CREATE PROC sp_XuatHoaDon
AS 
BEGIN
    SELECT
        HoaDon.MaHD,HoTenKH,NgayMua,MaBan,
        SUM(ChiTietHD.SoLuong) AS TongSoLuong,
		STRING_AGG(SanPham.TenNuoc + ' (' + CAST(ChiTietHD.SoLuong AS VARCHAR) + ')', ', ') AS SanPham_SoLuong,
        SUM(TongTien) AS TongTien,
		TrangThai
    FROM HoaDon
    INNER JOIN ChiTietHD ON ChiTietHD.MaHD = HoaDon.MaHD
    INNER JOIN KhachHang ON HoaDon.MaKH = KhachHang.MaKH
    INNER JOIN SanPham ON SanPham.MaSP = ChiTietHD.MaSP
    GROUP BY HoaDon.MaHD,HoTenKH,NgayMua,MaBan,TrangThai
END;
exec sp_XuatHoaDon
drop proc sp_chiTietSP
select * from ChiTietHD
delete ChiTietHD where id = 1

INSERT NhanVien ([MaNV], MatKhau, VaiTro, [HoTen], SDT, [NgaySinh], [GioiTinh], Luong, [Hinh], Email) VALUES ('NV01', '12345', 0, N'Trần xuân chiến', '0359690062', '07-11-2023', 1, 9000000, N'yea.jpg',N'xuanchient033@gmail.com')
INSERT NhanVien ([MaNV], MatKhau, VaiTro, [HoTen], SDT, [NgaySinh], [GioiTinh], Luong, [Hinh], Email) VALUES ('NV02', '12345', 1, N'LỮ HUY CƯỜNG','0359690062', '07-11-2023', 0, 9000000, N'Image.jpg',N'chientxpd08548@fpt.edu.vn')
INSERT NhanVien ([MaNV], MatKhau, VaiTro, [HoTen], SDT, [NgaySinh], [GioiTinh], Luong, [Hinh], Email) VALUES ('NV03', '12345', 1, N'ĐỖ VĂN MINH','0359690062', '07-11-2023', 0, 9000000, N'GAME.png',N'xuanchient033@gmail.com')
INSERT NhanVien ([MaNV], MatKhau, VaiTro, [HoTen], SDT, [NgaySinh], [GioiTinh], Luong, [Hinh], Email) VALUES ('NV04', '12345', 1, N'NGUYỄN TẤN HIẾU','0359690062', '07-11-2023', 1, 9000000, N'GAME.png',N'xuanchient033@gmail.com')
INSERT NhanVien ([MaNV], MatKhau, VaiTro, [HoTen], SDT, [NgaySinh], [GioiTinh], Luong, [Hinh], Email) VALUES ('NV05', '12345', 0, N'TRẦN VĂN NAM','0359690062', '07-11-2023', 1, 9000000, N'GAME.png',N'xuanchient033@gmail.com')


INSERT SanPham ([MaSP], Loai, [TenNuoc], Anh, DonGia, [NgayThem], MoTa, MaNV, SoLuong) VALUES ('SP01', N'Nước ngọt', N'Pesi', N'GAME.png', 10000, '07-11-2023', N'nước uống ngon tuyệt vời', N'NV01', 9)
INSERT SanPham ([MaSP], Loai, [TenNuoc], Anh, DonGia, [NgayThem], MoTa, MaNV, SoLuong) VALUES ('SP02', N'Nước ngọt', N'coca cola', N'GAME.png', 11000, '07-11-2023', N'nước uống ngon tuyệt vời', N'NV01', 9)
INSERT SanPham ([MaSP], Loai, [TenNuoc], Anh, DonGia, [NgayThem], MoTa, MaNV, SoLuong) VALUES ('SP03', N'Nước khoáng', N'aqua vina', N'GAME.png', 4000, '07-11-2023', N'nước uống ngon tuyệt vời', N'NV01', 9)
INSERT SanPham ([MaSP], Loai, [TenNuoc], Anh, DonGia, [NgayThem], MoTa, MaNV, SoLuong) VALUES ('SP04', N'Nước ép', N'nước cam', N'GAME.png', 13000, '07-11-2023', N'nước uống ngon tuyệt vời', N'NV01', 9)
INSERT SanPham ([MaSP], Loai, [TenNuoc], Anh, DonGia, [NgayThem], MoTa, MaNV, SoLuong) VALUES ('SP05', N'Nước ngọt', N'7 up', N'GAME.png', 14000, '07-11-2023', N'nước uống ngon tuyệt vời', N'NV01', 9)

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

INSERT HoaDon ( [NgayTao], TatCaSP, TongSL, TongTien, [MaNV], MaKH, MaBan, TrangThai)
VALUES ( '07-1-2023', N'pesi, coca', 3, 50000, 'NV01', 'KH01', 'Ban1', N'chưa thanh toán')
INSERT HoaDon ( [NgayTao], TatCaSP, TongSL, TongTien, [MaNV], MaKH, MaBan, TrangThai)
VALUES ( '07-11-2023', N'pesi', 3, 50000, 'NV02', 'KH02', 'Ban2', N'chưa thanh toán')
INSERT ChiTietHD ( [SoLuong], MaSP, MaHD) VALUES (5, 'SP02', 'HD00001')
INSERT ChiTietHD ( [SoLuong], MaSP, MaHD) VALUES (5, 'SP03', 'HD00001')




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
		 SoLuong as N'Hàng tồn kho',
		 CASE 
            WHEN DATEDIFF(MONTH, NgayThem, GETDATE()) <= 6 THEN N'Còn hạn sử dụng'
            ELSE N'Hết hạn sử dụng' 
         END AS TrangThai
    FROM SanPham
	ORDER BY SoLuong DESC
END;
go

create PROCEDURE sp_GetByMaBan
    @MaBan NVARCHAR(20)
AS
BEGIN
    SELECT ChiTietHD.MaHD, TenNuoc, loai, ChiTietHD.SoLuong, (ChiTietHD.SoLuong * SanPham.DonGia) as'Dongia', MaBan
    FROM HoaDon
    JOIN ChiTietHD ON HoaDon.MaHD = ChiTietHD.MaHD
	JOIN SanPham ON SanPham.MaSP = ChiTietHD.MaSP

    WHERE MaBan = @MaBan;
END;
go
drop proc sp_GetByMaBan
SELECT SUM(DonGia) AS TongDonGia FROM ChiTietHD WHERE MaHD = 'HD0001'
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



