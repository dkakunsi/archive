use sangihe_monev;

create table skpd(
	id int(11) auto_increment,
	kode varchar(255) not null,
	nama varchar(255) not null,
	primary key(id),
	unique(kode),
	unique(nama)
) engine = InnoDb;

create table kegiatan(
	id int(11) auto_increment,
	skpd int(11) not null,
	nama varchar(255) not null,
	anggaran bigint not null,
	awal int(4) not null,
	akhir int(4) not null,
	primary key (id),
	unique(skpd, nama, awal, akhir),
	foreign key(skpd) references skpd(id) on delete cascade
) engine = InnoDb;

create table realisasi(
	id int(11) auto_increment,
	kegiatan int(11) not null,
	tahun int(4) not null,
	bulan int(2) not null,
	fisik float not null,
	anggaran bigint not null,
	primary key(id),
	unique (kegiatan, tahun, bulan),
	foreign key (kegiatan) references kegiatan(id) on delete cascade
) engine = InnoDb;

create table operator(
	id int(11) auto_increment,
	username varchar(255) not null,
	password varchar(255) not null,
	skpd int(11) not null,
	role int(1) not null,
	primary key(id),
	unique(username),
	foreign key (skpd) references skpd(id) on delete cascade
) engine = InnoDb;
