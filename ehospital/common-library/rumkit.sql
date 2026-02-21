-- CREATE DATABASE rumkit;
USE rumkit;

CREATE TABLE unit (
	id int(10) auto_increment,
	nama varchar(255) not null,
	bobot float(10) not null,
	tipe int(1) not null,
	primary key (id)
) ENGINE = InnoDB;

CREATE TABLE operator (
	id int(10) auto_increment,
	nama varchar(255) not null,
	username varchar(255) not null,
	password varchar(255) not null,
	role int(1) not null,
	unit int(10) not null,
	unique (username),
	primary key (id)
) ENGINE = InnoDB;

CREATE TABLE token (
	kode varchar(255),
	tanggal_buat date not null,
	tanggal_expire date not null,
	status int(1) not null,
	operator int(10) not null,
	primary key (kode)
) ENGINE = InnoDB;

CREATE TABLE penduduk (
	id int(10) auto_increment,
	kode varchar(255) not null,
	nik varchar(255) not null, -- menjadi nomor tanggungan BPJS --
	nama varchar(255) not null,
	kelamin int(1) not null,
	tanggal_lahir date not null,
	darah varchar(255) not null,
	agama varchar(255) not null,
	telepon varchar(255) not null,
	unique(kode),
	primary key (id)
) ENGINE = InnoDB;

CREATE TABLE pasien (
	id int(10) auto_increment,
	tipe int(1) not null,
	kode varchar(255) not null,
	status int(1) not null,
	penanggung int(1) not null,
	kelas int(1),
	tanggal_masuk date not null,
	tanggal_rawat_inap date,
	tanggal_keluar date,
	pendaftaran int(1) not null,
	total_tagihan int(10),
	cicilan int(10),
	keadaan int(1),
	penduduk int(10) not null,
	perawatan int(10),
	tujuan int(10),
	primary key (id),
	unique (kode)
) ENGINE = InnoDB;

CREATE TABLE pegawai (
	id int(10) auto_increment,
	nip varchar(255),
	spesialisasi int(1),
	tipe varchar(255) not null,
	penduduk int(10) not null,
	unique(nip),
	primary key (id)
) ENGINE = InnoDB;

CREATE TABLE kategori_tindakan (
	id int(10) auto_increment,
	nama varchar(255) not null,
	parent int(10),
	primary key (id)
) ENGINE = InnoDB;

CREATE TABLE tindakan (
	id int(10) auto_increment,
	kode varchar(255) not null,
	nama varchar(255) not null,
	tarif int(10) not null,
	keterangan varchar(255),
	satuan int(1) not null,
	kelas int(1) not null,
	penanggung int(1) not null,
	kategori int(10),
	unique(kode),
	primary key (id)
) ENGINE = InnoDB;

CREATE TABLE pembayaran (
	kode varchar(255),
	pasien int(10) not null,
	tanggal date not null,
	jam time not null,
	jumlah int(10) not null,
	primary key (kode)
) ENGINE = InnoDB;

CREATE TABLE pelayanan (
	id int(10) auto_increment,
	tanggal date not null,
	jumlah int(10) not null,
	biaya_tambahan int(10),
	keterangan varchar(255),
	status_tagihan int(1) default 0,
	tipe varchar(255) not null,
	tanggal_selesai date,
	jam_masuk time(6),
	jam_keluar time(6),
	pasien int(10) not null,
	tindakan int(10) not null,
	pelaksana int(10),
	unit int(10) not null,
	pembayaran varchar(255),
	primary key (id)
) ENGINE = InnoDB;

CREATE TABLE barang (
	id int(10) auto_increment,
	kode varchar(255) not null,
	nama varchar(255) not null,
	jumlah int(10) not null,
	satuan varchar(255) not null,
	harga int(10) not null,
	penanggung int(1) not null,
	tipe varchar(255) not null,
	keterangan varchar(255),
	unique(kode),
	primary key (id)
) ENGINE = InnoDB;

CREATE TABLE stok (
	id int(10) auto_increment,
	jumlah int(10) not null,
	tanggal date not null,
	jam time(6) not null,
	tipe varchar(255) not null,
	barang int(10) not null,
	jenis int(1) not null,
	nomor_kembali varchar(255),
	unit int(10),
	pasien int(10),
	primary key (id)
) ENGINE = InnoDB;

CREATE TABLE pemakaian (
	id int(10) auto_increment,
	tanggal date not null,
	jumlah int(10) not null,
	nomor_resep varchar(255), 
	biaya_tambahan int(10),
	keterangan varchar(255),
	status_tagihan int(1) default 0,
	barang int(10) not null,
	pasien int(10) not null,
	unit int(10),
	pembayaran varchar(255),
	primary key (id)
) ENGINE = InnoDB;

ALTER TABLE operator ADD CONSTRAINT fk_operator_unit FOREIGN KEY (unit) REFERENCES unit (id);

ALTER TABLE token ADD CONSTRAINT fk_token_operator FOREIGN KEY (operator) REFERENCES operator (id);

ALTER TABLE pasien ADD CONSTRAINT fk_pasien_penduduk FOREIGN KEY (penduduk) REFERENCES penduduk (id);
ALTER TABLE pasien ADD CONSTRAINT fk_pasien_pelayanan FOREIGN KEY (perawatan) REFERENCES pelayanan (id);
ALTER TABLE pasien ADD CONSTRAINT fk_pasien_unit FOREIGN KEY (tujuan) REFERENCES unit (id);

ALTER TABLE pegawai ADD CONSTRAINT fk_pegawai_penduduk FOREIGN KEY (penduduk) REFERENCES penduduk (id);

ALTER TABLE kategori_tindakan ADD CONSTRAINT fk_kategori_kategori FOREIGN KEY (parent) REFERENCES kategori_tindakan (id);

ALTER TABLE tindakan ADD CONSTRAINT fk_tindakan_kategori FOREIGN KEY (kategori) REFERENCES kategori_tindakan (id);

ALTER TABLE pembayaran ADD CONSTRAINT fk_pembayaran_pasien FOREIGN KEY (pasien) REFERENCES pasien (id);

ALTER TABLE pelayanan ADD CONSTRAINT fk_pelayanan_pasien FOREIGN KEY (pasien) REFERENCES pasien (id);
ALTER TABLE pelayanan ADD CONSTRAINT fk_pelayanan_tindakan FOREIGN KEY (tindakan) REFERENCES tindakan (id);
ALTER TABLE pelayanan ADD CONSTRAINT fk_pelayanan_pegawai FOREIGN KEY (pelaksana) REFERENCES pegawai (id);
ALTER TABLE pelayanan ADD CONSTRAINT fk_pelayanan_unit FOREIGN KEY (unit) REFERENCES unit (id);
ALTER TABLE pelayanan ADD CONSTRAINT fk_pelayanan_pembayaran FOREIGN KEY (pembayaran) REFERENCES pembayaran (kode);

ALTER TABLE stok ADD CONSTRAINT fk_stok_barang FOREIGN KEY (barang) REFERENCES barang (id);
ALTER TABLE stok ADD CONSTRAINT fk_stok_unit FOREIGN KEY (unit) REFERENCES unit (id);
ALTER TABLE stok ADD CONSTRAINT fk_stok_pasien FOREIGN KEY (pasien) REFERENCES pasien (id);

ALTER TABLE pemakaian ADD CONSTRAINT fk_pemakaian_pasien FOREIGN KEY (pasien) REFERENCES pasien (id);
ALTER TABLE pemakaian ADD CONSTRAINT fk_pemakaian_barang FOREIGN KEY (barang) REFERENCES barang (id);
ALTER TABLE pemakaian ADD CONSTRAINT fk_pemakaian_unit FOREIGN KEY (unit) REFERENCES unit (id);
ALTER TABLE pemakaian ADD CONSTRAINT fk_pemakaian_pembayaran FOREIGN KEY (pembayaran) REFERENCES pembayaran (kode);

-- USE mysql;
-- CREATE USER 'rumkit_user'@'localhost' IDENTIFIED BY 'liun';
-- GRANT ALL PRIVILEGES ON rumkit.* TO 'rumkit_user'@'localhost' WITH GRANT OPTION;
