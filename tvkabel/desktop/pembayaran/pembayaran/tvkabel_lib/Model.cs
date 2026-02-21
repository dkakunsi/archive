using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using com.gtd.tvkabel.data.util;
using com.gtd.tvkabel.data.util.exception;
using com.gtd.tvkabel.data.dao;

/**
 * paket untuk menyimpan representasi tabel pada database
 */
namespace com.gtd.tvkabel.data.entity
{
    public abstract class Data { }

    /**
     * representasi tabel pelanggan
     * menyimpan data pelanggan
     * Id [String]
     * Nama [String]
     * Tanggal Mulai [String]
     * Jumlah Tv [Integer]
     * Alamat [String]
     * Status [Integer]
     */
    public class Pelanggan : Data
    {
        public static String INSERT = "insert into PELANGGAN(ID, NAMA, TGL_MULAI, JUMLAH_TV, ALAMAT, STATUS, KETERANGAN) values(?ID, ?NAMA, ?TGL_MULAI, ?JUMLAH_TV, ?ALAMAT, ?STATUS, ?KETERANGAN)";
        public static String UPDATE = "update PELANGGAN set NAMA=?NAMA, TGL_MULAI=?TGL_MULAI, JUMLAH_TV=?JUMLAH_TV, ALAMAT=?ALAMAT, STATUS=?STATUS, ID=?ID, KETERANGAN=?KETERANGAN where KODE=?KODE";
        public static String DELETE = "delete from PELANGGAN where ID=?ID";
        public static String DELETEBYKODE = "delete from PELANGGAN where KODE=?KODE";
        public static String SELECT = "select * from PELANGGAN where ID=?ID";
        public static String SELECT_ALL = "select * from PELANGGAN order by ID";
        public static String SELECTBYKODE = "select * from PELANGGAN where KODE=?KODE";
        public static String SELECTBYNAME = "select * from PELANGGAN where NAMA like ?NAMA";
        public static String GETBYSTATUS = "select * from PELANGGAN where STATUS=?STATUS order by ID";

        private KontakDao KontakDao;
        private PembayaranDao PembayaranDao;

        public Pelanggan() : base()
        {
            KontakDao = KontakDao.Instance;
            PembayaranDao = PembayaranDao.Instance;
        }

        public String Id { get; set; }
        public String Nama { get; set; }
        public String TglMulai { get; set; }
        public int JumlahTv { get; set; }
        public String Alamat { get; set; }
        public Int16 Status { get; set; }
        public Int32 Kode { get; set; }

        public IList<Kontak> ListKontak
        {
            get
            {
                IList<Kontak> Kontak;
                try
                {
                    if (Id != null)
                    {
                        Dao dao = KontakDao.Instance;
                        Kontak = (IList<Kontak>)KontakDao.getByPelanggan(this);
                    }
                    else
                    {
                        throw new DataValidationException("id is not applied to this object");
                    }
                }
                catch (DataException exc)
                {
                    throw exc; ;
                }
                return Kontak;
            }
        }

        public IList<Pembayaran> ListPembayaran
        {
            get
            {
                IList<Pembayaran> Pembayaran;
                try
                {
                    if (Id != null)
                    {
                        Dao dao = PembayaranDao.Instance;
                        Pembayaran = (IList<Pembayaran>)PembayaranDao.getByPelanggan(this);
                    }
                    else
                    {
                        throw new DataValidationException("id is not applied to this object");
                    }
                }
                catch (DataException exc)
                {
                    throw exc; ;
                }
                return Pembayaran;
            }
        }

        #region IKeterangan Members

        public string Keterangan { get; set; }

        #endregion

        public override bool Equals(object obj)
        {
            if (obj is Pelanggan)
            {
                Pelanggan pelanggan = (Pelanggan)obj;

                if (this.Kode.Equals(pelanggan.Kode))
                {
                    return true;
                }
            }
            return false;
        }

        public override int GetHashCode()
        {
            return base.GetHashCode();
        }
    }

    /**
     * representasi tabel details
     * menyimpan data iuran dan data pendaftaran
     * Biaya Pendaftaran [Double]
     * Iuran Bulanan [Double]
     */
    public class Details : Data
    {
        public static String INSERT = "insert into DETAILS values(?PENDAFTARAN, ?IURAN, ?KODE_PELANGGAN)";
        public static String UPDATE = "update DETAILS set PENDAFTARAN=?PENDAFTARAN, IURAN=?IURAN where KODE_PELANGGAN=?KODE_PELANGGAN";
        public static String DELETE = "delete from DETAILS where KODE_PELANGGAN=?KODE_PELANGGAN";
        public static String SELECT = "select * from DETAILS where KODE_PELANGGAN=?KODE_PELANGGAN";
        public static String SELECT_ALL = "select * from DETAILS order by KODE_PELANGGAN";

        public Pelanggan Pelanggan { get; set; }
        public double Pendaftaran { get; set; }
        public Double Iuran { get; set; }

        public Details() : base() { }

        public override bool Equals(object obj)
        {
            if (obj is Details)
            {
                Details other = (Details)obj;
                if ((other.Pelanggan.Equals(this.Pelanggan)) && (other.Pendaftaran.Equals(this.Pendaftaran)) && (other.Iuran.Equals(this.Iuran)))
                {
                    return true;
                }
            }
            return false;
        }

        public override int GetHashCode()
        {
            return base.GetHashCode();
        }
    }

    /**
     * representasi tabel Kontak
     * menyimpan data kontak pelanggan
     * Tipe Kontak [TipeKontak]
     * Detail [String]
     */
    public class Kontak : Data
    {
        public static String INSERT = "insert into KONTAK values(?TIPE, ?DETAIL, ?KODE_PELANGGAN)";
        public static String UPDATE = "update KONTAK set DETAIL=?DETAIL where KODE_PELANGGAN=?KODE_PELANGGAN and TIPE=?TIPE";
        public static String DELETE = "delete from KONTAK where KODE_PELANGGAN=?KODE_PELANGGAN and TIPE=?TIPE";
        public static String SELECT = "select * from KONTAK where KODE_PELANGGAN=?KODE_PELANGGAN and TIPE=?TIPE";
        public static String SELECT_ALL = "select * from KONTAK order by KODE_PELANGGAN";
        public static String DELETEBYPELANGGAN = "delete from KONTAK where KODE_PELANGGAN=?KODE_PELANGGAN";
        public static String DELETEBYTIPE = "delete from KONTAK where TIPE=?TIPE";
        public static String GETBYPELANGGAN = "select * from KONTAK where KODE_PELANGGAN=?KODE_PELANGGAN";
        public static String GETBYTIPEKONTAK = "select * from KONTAK where TIPE=?TIPE";

        public Pelanggan Pelanggan { get; set; }
        public TipeKontak TipeKontak { get; set; }
        public String Detail { get; set; }

        public Kontak() : base() { }

        public override bool Equals(object obj)
        {
            if (obj is Kontak)
            {
                Kontak kontak = (Kontak)obj;

                if ((this.Pelanggan.Equals(kontak.Pelanggan)) && (this.TipeKontak.Equals(kontak.TipeKontak)))
                {
                    return true;
                }
            }
            return false;
        }

        public override int GetHashCode()
        {
            return base.GetHashCode();
        }
    }

    /**
     * representasi tabel TipeKontak
     * menyimpan tipe-tipe kontak
     */
    public class TipeKontak : Data
    {
        public static String INSERT = "insert into TIPE_KONTAK values(?KODE, ?DETAIL)";
        public static String UPDATE = "update TIPE_KONTAK set DETAIL=?DETAIL where KODE=?KODE";
        public static String DELETE = "delete from TIPE_KONTAK where KODE=?KODE";
        public static String SELECT = "select * from TIPE_KONTAK where KODE=?KODE";
        public static String SELECT_ALL = "select * from TIPE_KONTAK order by DETAIL";

        private KontakDao KontakDao;
        public String Kode { get; set; }
        public String Detail { get; set; }

        public TipeKontak()
            : base()
        {
            KontakDao = KontakDao.Instance;
        }

        public override bool Equals(object obj)
        {
            if (obj is TipeKontak)
            {
                TipeKontak type = (TipeKontak)obj;

                if (this.Kode.Equals(type.Kode))
                {
                    return true;
                }
            }
            return false;
        }

        public override int GetHashCode()
        {
            return base.GetHashCode();
        }
        public IList<Kontak> ListKontak
        {
            get
            {
                IList<Kontak> Kontak;
                if (Kode != null)
                {
                    Dao dao = KontakDao.Instance;
                    Kontak = (IList<Kontak>)KontakDao.getByTipeKontak(this);
                }
                else
                {
                    throw new DataValidationException("id not applied to this object");
                }
                return Kontak;
            }
        }
    }

    /**
     * representasi tabel Pembayaran
     * menyimpan data pembayaran pelanggan setiap bulan
     * Tahun [Integer]
     * Bulan [Integer]
     * Biaya [Double]
     * Tanggal Bayar [String]
     */
    public class Pembayaran : Data
    {
        public static String INSERT = "insert into PEMBAYARAN values(?TAHUN, ?BULAN, ?BIAYA, ?TANGGAL_BAYAR, ?KET, ?KODE_PELANGGAN)";
        public static String UPDATE = "update PEMBAYARAN set BIAYA=?BIAYA, TANGGAL_BAYAR=?TANGGAL_BAYAR, KET=?KET where KODE_PELANGGAN=?KODE_PELANGGAN and TAHUN=?TAHUN and BULAN=?BULAN";
        public static String DELETE = "delete from PEMBAYARAN where KODE_PELANGGAN=?KODE_PELANGGAN and TAHUN=?TAHUN and BULAN=?BULAN";
        public static String DELETEBYPELANGGAN = "delete from PEMBAYARAN where KODE_PELANGGAN=?KODE_PELANGGAN";
        public static String SELECT = "select * from PEMBAYARAN where KODE_PELANGGAN=?KODE_PELANGGAN and TAHUN=?TAHUN and BULAN=?BULAN";
        public static String SELECT_ALL = "select * from PEMBAYARAN order by KODE_PELANGGAN";
        public static String GETBYTANGGAL = "select * from PEMBAYARAN where TAHUN=?TAHUN and BULAN=?BULAN order by KODE_PELANGGAN";
        public static String GETBYPELANGGAN = "select * from PEMBAYARAN where KODE_PELANGGAN=?KODE_PELANGGAN order by TAHUN";
        public static String GETBYNAMA = "select TAHUN, BULAN, BIAYA, TANGGAL_BAYAR, KET from PEMBAYARAN bayar inner join PELANGGAN p ON p.KODE=bayar.KODE_PELANGGAN where p.NAMA=?NAMA";

        public Pelanggan Pelanggan { get; set; }
        public int Tahun { get; set; }
        public int Bulan { get; set; }
        public double Biaya { get; set; }
        public String TanggalBayar { get; set; }

        public Pembayaran() : base() { }

        #region IKeterangan Members

        public string Keterangan { get; set; }

        #endregion

        public override bool Equals(object obj)
        {
            if (obj is Pembayaran)
            {
                Pembayaran pembayaran = (Pembayaran)obj;

                if ((this.Pelanggan.Equals(pembayaran.Pelanggan)) && (this.Tahun.Equals(pembayaran.Tahun)) && (this.Bulan.Equals(pembayaran.Bulan)))
                {
                    return true;
                }
            }
            return false;
        }

        public override int GetHashCode()
        {
            return base.GetHashCode();
        }

        public String getNomorPembayaran(int tahun, int bulan)
        {
            return (Pelanggan.Id + tahun + bulan);
        }
    }

    /**
     * representasi tabel Login
     * menyimpan data credential setiap user
     * Username [String]
     * Password [String]
     * Nama [String]
     * Role [String]
     */
    public class Login : Data
    {
        public static String INSERT = "insert into LOGIN values(?USERNAME, ?PASSWORD, ?NAMA, ?ROLE)";
        public static String UPDATE = "update LOGIN set PASSWORD=?PASSWORD, NAMA=?NAMA, ROLE=?ROLE where USERNAME=?USERNAME";
        public static String DELETE = "delete from LOGIN where USERNAME=?USERNAME";
        public static String SELECT = "select * from LOGIN where USERNAME=?USERNAME";
        public static String SELECT_ALL = "select * from LOGIN order by USERNAME";

        public String Username { get; set; }
        public String Password { get; set; }
        public String Nama { get; set; }
        public String Role { get; set; }

        public Login() : base() { }

        bool isAuthenticated(String password)
        {
            return (Password.Equals(password));
        }
    }

    public class Tunggakan : Data
    {
        public static String INSERT = "insert into TUNGGAKAN values(?JUMLAH_BULAN, ?KODE_PELANGGAN)";
        public static String UPDATE = "update TUNGGAKAN set JUMLAH_BULAN=?JUMLAH_BULAN where KODE_PELANGGAN=?KODE_PELANGGAN";
        public static String DELETE = "delete from TUNGGAKAN where KODE_PELANGGAN=?KODE_PELANGGAN";
        public static String SELECT = "select * from TUNGGAKAN where KODE_PELANGGAN=?KODE_PELANGGAN";
        public static String SELECT_ALL = "select * from TUNGGAKAN order by KODE_PELANGGAN";
        public static String SORTBYJUMLAH = "select * from TUNGGAKAN where JUMLAH_BULAN > 0 order by JUMLAH_BULAN";

        public Pelanggan Pelanggan { get; set; }
        public int JumlahBulan { get; set; }

        public Tunggakan() : base() { }
    }
}
