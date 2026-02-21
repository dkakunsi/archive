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
    public class Pelanggan : Data, IKeterangan
    {
        public Pelanggan() : base()
        {
            dao = DataManager.Instance;
        }

        public String Id { get; set; }
        public String Nama { get; set; }
        public String TglMulai { get; set; }
        public int JumlahTv { get; set; }
        public String Alamat { get; set; }
        public Int16 Status { get; set; }

        public IList<Kontak> ListKontak
        {
            get
            {
                IList<Kontak> Kontak;
                try
                {
                    if (Id != null)
                    {
                        Kontak = (IList<Kontak>)dao.search(EntityList.KONTAK, this);
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
                        Pembayaran = (IList<Pembayaran>)dao.search(EntityList.PEMBAYARAN, this);
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

                if (this.Id.Equals(pelanggan.Id))
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
        public Pelanggan Pelanggan { get; set; }
        public double Pendaftaran { get; set; }
        public Double Iuran { get; set; }

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
        public Pelanggan Pelanggan { get; set; }
        public TipeKontak TipeKontak { get; set; }
        public String Detail { get; set; }

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
    public class TipeKontak : Type
    {
        public TipeKontak() : base()
        {
            dao = DataManager.Instance;
        }

        public IList<Kontak> ListKontak
        {
            get
            {
                IList<Kontak> Kontak;
                if (Kode != null)
                {
                    Kontak = (IList<Kontak>)dao.search(EntityList.KONTAK, this);
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
    public class Pembayaran : Data, IKeterangan
    {
        public Pelanggan Pelanggan { get; set; }
        public int Tahun { get; set; }
        public int Bulan { get; set; }
        public double Biaya { get; set; }
        public String TanggalBayar { get; set; }

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
        public String Username { get; set; }
        public String Password { get; set; }
        public String Nama { get; set; }
        public String Role { get; set; }

        bool isAuthenticated(String password)
        {
            return (Password.Equals(password));
        }
    }
}
