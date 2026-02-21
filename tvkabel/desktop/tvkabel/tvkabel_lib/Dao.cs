using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using com.gtd.tvkabel.data.entity;
using MySql.Data.MySqlClient;
using com.gtd.tvkabel.data.util.exception;
using System.Collections;
using com.gtd.tvkabel.data.util;

namespace com.gtd.tvkabel.data.dao
{
    public class PelangganDao : DataDao
    {
        private static PelangganDao instance;

        public static PelangganDao Instance
        {
            get
            {
                if (instance == null)
                {
                    instance = new PelangganDao();
                }
                return instance;
            }
        }

        private PelangganDao() : base()
        {
            conn = new util.ConnectionManager();
            INSERT = "insert into PELANGGAN values(?ID, ?NAMA, ?TGL_MULAI, ?JUMLAH_TV, ?ALAMAT, ?STATUS)";
            UPDATE = "update PELANGGAN set NAMA=?NAMA, TGL_MULAI=?TGL_MULAI, JUMLAH_TV=?JUMLAH_TV, ALAMAT=?ALAMAT, STATUS=?STATUS where ID=?ID";
            DELETE = "delete from PELANGGAN where ID=?ID";
            SELECT = "select * from PELANGGAN where ID=?ID";
            SELECT_ALL = "select * from PELANGGAN order by ID";
            SEARCH = "select * from PELANGGAN where STATUS=?STATUS order by ID";
        }

        public override IList<Data> getAll()
        {
            IList<Data> list = null;

            MySqlCommand cmd = new MySqlCommand(SELECT_ALL, conn.Connection);
            MySqlDataReader result = cmd.ExecuteReader();

            if (result.HasRows)
            {
                list = new List<Data>();

                while (result.Read())
                {
                    Pelanggan pelanggan = new Pelanggan();

                    pelanggan.Id = result.GetString("ID");
    	            pelanggan.Nama = result.GetString("NAMA");
	                pelanggan.TglMulai = result.GetString("TGL_MULAI");
	                pelanggan.JumlahTv = result.GetInt16("JUMLAH_TV");
	                pelanggan.Alamat = result.GetString("ALAMAT");
                    pelanggan.Status = result.GetInt16("STATUS");

                    list.Add(pelanggan);
                }
            }
            else
            {
                // throw new DataNotFoundException("Tidak ada data yang ditemukan pada tabel Pelanggan");
            }


            result.Close();
            conn.close();

            return list;
        }

        public override Data get(object id)
        {
            Pelanggan pelanggan = null;

            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = SELECT;
            cmd.Parameters.Add("?ID", MySqlDbType.VarChar, 255).Value = id;

            MySqlDataReader result = cmd.ExecuteReader();

            if (result.HasRows)
            {
                pelanggan = new Pelanggan();

                result.Read();

                pelanggan.Id = (String)id;
                pelanggan.Nama = result.GetString("NAMA");
                pelanggan.TglMulai = result.GetString("TGL_MULAI");
                pelanggan.JumlahTv = result.GetInt16("JUMLAH_TV");
                pelanggan.Alamat = result.GetString("ALAMAT");
                pelanggan.Status = result.GetInt16("STATUS");
            }

            result.Close();
            conn.close();

            return pelanggan;
        }

        public override IList<Data> search(object criteria)
        {
            IList<Data> list = null;

            MySqlCommand cmd = new MySqlCommand(SEARCH, conn.Connection);
            cmd.Parameters.Add("?STATUS", MySqlDbType.VarChar, 255).Value = criteria;
            MySqlDataReader result = cmd.ExecuteReader();

            if (result.HasRows)
            {
                list = new List<Data>();

                while (result.Read())
                {
                    Pelanggan pelanggan = new Pelanggan();

                    pelanggan.Id = result.GetString("ID");
                    pelanggan.Nama = result.GetString("NAMA");
                    pelanggan.TglMulai = result.GetString("TGL_MULAI");
                    pelanggan.JumlahTv = result.GetInt16("JUMLAH_TV");
                    pelanggan.Alamat = result.GetString("ALAMAT");
                    pelanggan.Status = result.GetInt16("STATUS");

                    list.Add(pelanggan);
                }
            }
            else
            {
                // throw new DataNotFoundException("Tidak ada data yang ditemukan pada tabel Pelanggan");
            }


            result.Close();
            conn.close();

            return list;
        }

        public override void delete(Data data)
        {
            try
            {
                Pelanggan pelanggan = (Pelanggan)data;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = DELETE;
                cmd.Parameters.Add("?ID", MySqlDbType.VarChar, 255).Value = pelanggan.Id;

                MySqlDataReader result = cmd.ExecuteReader();

                result.Close();
                conn.close();
            }
            catch (SystemException exc)
            {
                throw new DataException(exc.Message);
            }
        }

        public override void add(Data data)
        {
            try
            {
                Pelanggan pelanggan = (Pelanggan)data;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = INSERT;
                cmd.Parameters.Add("?ID", MySqlDbType.VarChar, 255).Value = pelanggan.Id;
                cmd.Parameters.Add("?NAMA", MySqlDbType.VarChar, 255).Value = pelanggan.Nama;
                cmd.Parameters.Add("?TGL_MULAI", MySqlDbType.VarChar, 10).Value = pelanggan.TglMulai;
                cmd.Parameters.Add("?JUMLAH_TV", MySqlDbType.Int32, 11).Value = pelanggan.JumlahTv;
                cmd.Parameters.Add("?ALAMAT", MySqlDbType.VarChar, 255).Value = pelanggan.Alamat;
                cmd.Parameters.Add("?STATUS", MySqlDbType.Int16, 1).Value = pelanggan.Status;

                MySqlDataReader result = cmd.ExecuteReader();

                result.Close();
                conn.close();
            }
            catch (SystemException exc)
            {
                throw new DataException(exc.Message);
            }
        }

        public override void update(Data data)
        {
            try
            {
                Pelanggan pelanggan = (Pelanggan)data;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = UPDATE;
                cmd.Parameters.Add("?ID", MySqlDbType.VarChar, 255).Value = pelanggan.Id;
                cmd.Parameters.Add("?NAMA", MySqlDbType.VarChar, 255).Value = pelanggan.Nama;
                cmd.Parameters.Add("?TGL_MULAI", MySqlDbType.VarChar, 255).Value = pelanggan.TglMulai;
                cmd.Parameters.Add("?JUMLAH_TV", MySqlDbType.Int32, 255).Value = pelanggan.JumlahTv;
                cmd.Parameters.Add("?ALAMAT", MySqlDbType.VarChar, 255).Value = pelanggan.Alamat;
                cmd.Parameters.Add("?STATUS", MySqlDbType.Int16, 1).Value = pelanggan.Status;

                MySqlDataReader result = cmd.ExecuteReader();

                result.Close();
                conn.close();
            }
            catch (SystemException exc)
            {
                throw new DataException(exc.Message);
            }
        }
    }

    public class KontakDao : DataDao
    {
        private static KontakDao instance;

        public static KontakDao Instance
        {
            get
            {
                if (instance == null)
                {
                    instance = new KontakDao();
                }
                return instance;
            }
        }

        private KontakDao() : base()
        {
            conn = new util.ConnectionManager();
            INSERT = "insert into KONTAK values(?ID_PELANGGAN, ?TIPE, ?DETAIL)";
            UPDATE = "update KONTAK set DETAIL=?DETAIL where ID_PELANGGAN=?ID_PELANGGAN and TIPE=?TIPE";
            DELETE = "delete from KONTAK where ID_PELANGGAN=?ID_PELANGGAN and TIPE=?TIPE";
            SELECT = "select * from KONTAK where ID_PELANGGAN=?ID_PELANGGAN and TIPE=?TIPE";
            SELECT_ALL = "select * from KONTAK order by ID_PELANGGAN";
            SEARCH = "select * from KONTAK where ID_PELANGGAN=?ID_PELANGGAN";
        }

        public override IList<Data> getAll()
        {
            IList<Data> list = null;

            MySqlCommand cmd = new MySqlCommand(SELECT_ALL, conn.Connection);
            MySqlDataReader result = cmd.ExecuteReader();

            if (result.HasRows)
            {
                list = new List<Data>();

                while (result.Read())
                {
                    Kontak kontak = new Kontak();

                    kontak.Pelanggan = (Pelanggan)PelangganDao.Instance.get(result.GetString("ID_PELANGGAN"));
                    kontak.TipeKontak = (TipeKontak)TipeKontakDao.Instance.get(result.GetString("TIPE"));
                    kontak.Detail = result.GetString("DETAIL");

                    list.Add(kontak);
                }
            }
            else
            {
                throw new DataNotFoundException("Tidak ada data yang ditemukan pada tabel Kontak");
            }


            result.Close();
            conn.close();

            return list;
        }

        public override Data get(object id)
        {
            Kontak kontak = null;
            KontakPk primaryKey = (KontakPk)id;

            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = SELECT;
            cmd.Parameters.Add("?ID_PELANGGAN", MySqlDbType.VarChar, 255).Value = primaryKey.Pelanggan;
            cmd.Parameters.Add("?TIPE", MySqlDbType.VarChar, 255).Value = primaryKey.TipeKontak;

            MySqlDataReader result = cmd.ExecuteReader();

            if (result.HasRows)
            {
                kontak = new Kontak();

                result.Read();

                kontak.Pelanggan = (Pelanggan)PelangganDao.Instance.get(primaryKey.Pelanggan);
                kontak.TipeKontak = (TipeKontak)TipeKontakDao.Instance.get(primaryKey.TipeKontak);
                kontak.Detail = result.GetString("DETAIL");
            }
            else
            {
                throw new DataNotFoundException("Data dengan ID '" + primaryKey.Pelanggan + ", " + primaryKey.TipeKontak + "' tidak ditemukan");
            }

            result.Close();
            conn.close();

            return kontak;
        }

        public override IList<Data> search(object criteria)
        {
            IList<Data> list = null;

            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = SEARCH;
            cmd.Parameters.Add("?ID_PELANGGAN", MySqlDbType.VarChar, 255).Value = criteria;

            MySqlDataReader result = cmd.ExecuteReader();

            if (result.HasRows)
            {
                list = new List<Data>();

                while (result.Read())
                {
                    Kontak kontak = new Kontak();

                    kontak.Pelanggan = (Pelanggan)PelangganDao.Instance.get(criteria);
                    kontak.TipeKontak = (TipeKontak)TipeKontakDao.Instance.get(result.GetString("TIPE"));
                    kontak.Detail = result.GetString( "DETAIL" );

                    list.Add(kontak);
                }
            }

            result.Close();
            conn.close();

            return list;
        }

        public override void delete(Data data)
        {
            try
            {
                Kontak kontak = (Kontak)data;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = DELETE;
                cmd.Parameters.Add("?ID_PELANGGAN", MySqlDbType.VarChar, 255).Value = kontak.Pelanggan.Id;
                cmd.Parameters.Add("?TIPE", MySqlDbType.VarChar, 255).Value = kontak.TipeKontak.Kode;

                MySqlDataReader result = cmd.ExecuteReader();

                result.Close();
                conn.close();
            }
            catch (SystemException exc)
            {
                throw new DataException(exc.Message);
            }
        }

        public override void add(Data data)
        {
            try
            {
                Kontak kontak = (Kontak)data;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = INSERT;
                cmd.Parameters.Add("?ID_PELANGGAN", MySqlDbType.VarChar, 255).Value = kontak.Pelanggan.Id;
                cmd.Parameters.Add("?TIPE", MySqlDbType.VarChar, 255).Value = kontak.TipeKontak.Kode;
                cmd.Parameters.Add("?DETAIL", MySqlDbType.VarChar, 255).Value = kontak.Detail;

                MySqlDataReader result = cmd.ExecuteReader();

                result.Close();
                conn.close();
            }
            catch (SystemException exc)
            {
                throw new DataException(exc.Message);
            }
        }

        public override void update(Data data)
        {
            try
            {
                Kontak kontak = (Kontak)data;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = UPDATE;
                cmd.Parameters.Add("?ID_PELANGGAN", MySqlDbType.VarChar, 255).Value = kontak.Pelanggan.Id;
                cmd.Parameters.Add("?TIPE", MySqlDbType.VarChar, 255).Value = kontak.TipeKontak.Kode;
                cmd.Parameters.Add("?DETAIL", MySqlDbType.VarChar, 255).Value = kontak.Detail;

                MySqlDataReader result = cmd.ExecuteReader();

                result.Close();
                conn.close();
            }
            catch (SystemException exc)
            {
                throw new DataException(exc.Message);
            }
        }
    }

    public class TipeKontakDao : DataDao
    {
        private static TipeKontakDao instance;

        public static TipeKontakDao Instance
        {
            get
            {
                if (instance == null)
                {
                    instance = new TipeKontakDao();
                }
                return instance;
            }
        }

        private TipeKontakDao() : base()
        {
            conn = new util.ConnectionManager();
            INSERT = "insert into TIPE_KONTAK values(?KODE, ?DETAIL)";
            UPDATE = "update TIPE_KONTAK set DETAIL=?DETAIL where KODE=?KODE";
            DELETE = "delete from TIPE_KONTAK where KODE=?KODE";
            SELECT = "select * from TIPE_KONTAK where KODE=?KODE";
            SELECT_ALL = "select * from TIPE_KONTAK order by KODE";
        }

        public override IList<Data> getAll()
        {
            IList<Data> list = null;

            MySqlCommand cmd = new MySqlCommand(SELECT_ALL, conn.Connection);
            MySqlDataReader result = cmd.ExecuteReader();

            if (result.HasRows)
            {
                list = new List<Data>();

                while (result.Read())
                {
                    TipeKontak tipeKontak = new TipeKontak();

                    tipeKontak.Kode = result.GetString("KODE");
                    tipeKontak.Detail = result.GetString("DETAIL");

                    list.Add(tipeKontak);
                }
            }

            result.Close();
            conn.close();

            return list;
        }

        public override Data get(object id)
        {
            TipeKontak tipeKontak = null;

            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = SELECT;
            cmd.Parameters.Add("?KODE", MySqlDbType.VarChar, 255).Value = id;

            MySqlDataReader result = cmd.ExecuteReader();

            if (result.HasRows)
            {
                tipeKontak = new TipeKontak();

                result.Read();

                tipeKontak.Kode = (String)id;
                tipeKontak.Detail = result.GetString("DETAIL");
            }

            result.Close();
            conn.close();

            return tipeKontak;
        }

        public override IList<Data> search(object criteria)
        {
            IList<Data> lsData = new List<Data>();

            lsData.Add(get(criteria));

            return lsData;
        }

        public override void delete(Data data)
        {
            try
            {
                TipeKontak tipeKontak = (TipeKontak)data;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = DELETE;
                cmd.Parameters.Add("?KODE", MySqlDbType.VarChar, 255).Value = tipeKontak.Kode;

                MySqlDataReader result = cmd.ExecuteReader();

                result.Close();
                conn.close();
            }
            catch (SystemException exc)
            {
                throw new DataException(exc.Message);
            }
        }

        public override void add(Data data)
        {
            try
            {
                TipeKontak tipeKontak = (TipeKontak)data;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = INSERT;
                cmd.Parameters.Add("?KODE", MySqlDbType.VarChar, 255).Value = tipeKontak.Kode;
                cmd.Parameters.Add("?DETAIL", MySqlDbType.VarChar, 255).Value = tipeKontak.Detail;

                MySqlDataReader result = cmd.ExecuteReader();

                result.Close();
                conn.close();
            }
            catch (SystemException exc)
            {
                throw new DataException(exc.Message);
            }
        }

        public override void update(Data data)
        {
            try
            {
                TipeKontak tipeKontak = (TipeKontak)data;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = UPDATE;
                cmd.Parameters.Add("?KODE", MySqlDbType.VarChar, 255).Value = tipeKontak.Kode;
                cmd.Parameters.Add("?DETAIL", MySqlDbType.VarChar, 255).Value = tipeKontak.Detail;

                MySqlDataReader result = cmd.ExecuteReader();

                result.Close();
                conn.close();
            }
            catch (SystemException exc)
            {
                throw new DataException(exc.Message);
            }
        }
    }

    public class PembayaranDao : DataDao
    {
        private static PembayaranDao instance;
        private String SEARCH_BY;
        
        public static PembayaranDao Instance
        {
            get 
            {
                if (instance == null)
                {
                    instance = new PembayaranDao();
                }
                return instance;
            }
        }

        private PembayaranDao() : base()
        {
            conn = new util.ConnectionManager();
            INSERT = "insert into PEMBAYARAN values(?ID_PELANGGAN, ?TAHUN, ?BULAN, ?BIAYA, ?TANGGAL_BAYAR, ?KET)";
            UPDATE = "update PEMBAYARAN set BIAYA=?BIAYA, TANGGAL_BAYAR=?TANGGAL_BAYAR, KET=?KET where ID_PELANGGAN=?ID_PELANGGAN and TAHUN=?TAHUN and BULAN=?BULAN";
            DELETE = "delete from PEMBAYARAN where ID_PELANGGAN=?ID_PELANGGAN and TAHUN=?TAHUN and BULAN=?BULAN";
            SELECT = "select * from PEMBAYARAN where ID_PELANGGAN=?ID_PELANGGAN and TAHUN=?TAHUN and BULAN=?BULAN";
            SELECT_ALL = "select * from PEMBAYARAN order by ID";
            SEARCH = "select * from PEMBAYARAN where ID_PELANGGAN=?ID_PELANGGAN order by TAHUN";
            SEARCH_BY = "select * from PEMBAYARAN where TAHUN=?TAHUN and BULAN=?BULAN order by ID_PELANGGAN";
        }

        public override IList<Data> getAll()
        {
            IList<Data> list = null;

            MySqlCommand cmd = new MySqlCommand(SELECT_ALL, conn.Connection);
            MySqlDataReader result = cmd.ExecuteReader();

            if (result.HasRows)
            {
                list = new List<Data>();

                while (result.Read())
                {
                    Pembayaran pembayaran = new Pembayaran();

                    pembayaran.Pelanggan = (Pelanggan)PelangganDao.Instance.get( result.GetString("ID_PELANGGAN") );
                    pembayaran.Tahun = result.GetInt16("TAHUN");
                    pembayaran.Bulan = result.GetInt16("BULAN");
                    pembayaran.Biaya = result.GetDouble("BIAYA");
                    pembayaran.TanggalBayar = result.GetString("TANGGAL_BAYAR");
                    pembayaran.Keterangan = result.GetString("KET");

                    list.Add(pembayaran);
                }
            }
            else
            {
                throw new DataNotFoundException("Tidak ada data yang ditemukan pada tabel Pembayaran");
            }

            result.Close();
            conn.close();

            return list;
        }

        public override Data get(object id)
        {
            Pembayaran pembayaran = null;
            PembayaranPk primaryKey = (PembayaranPk)id;

            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = SELECT;
            cmd.Parameters.Add("?ID_PELANGGAN", MySqlDbType.VarChar, 255).Value = primaryKey.Pelanggan;
            cmd.Parameters.Add("?TAHUN", MySqlDbType.VarChar, 255).Value = primaryKey.Tahun;
            cmd.Parameters.Add("?BULAN", MySqlDbType.VarChar, 255).Value = primaryKey.Bulan;

            MySqlDataReader result = cmd.ExecuteReader();

            if (result.HasRows)
            {
                pembayaran = new Pembayaran();

                result.Read();

                pembayaran.Pelanggan = (Pelanggan)PelangganDao.Instance.get(primaryKey.Pelanggan);
                pembayaran.Tahun = primaryKey.Tahun;
                pembayaran.Bulan = primaryKey.Bulan;
                pembayaran.Biaya = result.GetDouble("BIAYA");
                pembayaran.TanggalBayar = result.GetString("TANGGAL_BAYAR");
                pembayaran.Keterangan = result.GetString("KET");
            }
            else
            {
                throw new DataNotFoundException("Data dengan ID '" + primaryKey.Pelanggan + ", " + primaryKey.Tahun + ", " + primaryKey.Bulan + "' tidak ditemukan");
            }

            result.Close();
            conn.close();

            return pembayaran;
        }

        public override IList<Data> search(object criteria)
        {
            IList<Data> list = null;
            MySqlCommand cmd = conn.Connection.CreateCommand();

            if (criteria is ArrayList)
            {
                ArrayList arrayList = (ArrayList)criteria;
                Array array = arrayList.ToArray();

                cmd.CommandText = SEARCH_BY;
                cmd.Parameters.Add("?TAHUN", MySqlDbType.Int32, 4).Value = array.GetValue(0);
                cmd.Parameters.Add("?BULAN", MySqlDbType.Int32, 2).Value = array.GetValue(1);

                MySqlDataReader result = cmd.ExecuteReader();

                if (result.HasRows)
                {
                    list = new List<Data>();

                    while (result.Read())
                    {
                        Pembayaran pembayaran = new Pembayaran();

                        pembayaran.Pelanggan = (Pelanggan)PelangganDao.Instance.get(result.GetString("ID_PELANGGAN"));
                        pembayaran.Tahun = Int32.Parse(array.GetValue(0).ToString());
                        pembayaran.Bulan = Int32.Parse(array.GetValue(1).ToString());
                        pembayaran.Biaya = result.GetDouble("BIAYA");
                        pembayaran.TanggalBayar = result.GetString("TANGGAL_BAYAR");
                        pembayaran.Keterangan = result.GetString("KET");

                        list.Add(pembayaran);
                    }
                }
                result.Close();
            }
            else
            {
                cmd.CommandText = SEARCH;
                cmd.Parameters.Add("?ID_PELANGGAN", MySqlDbType.VarChar, 255).Value = criteria;

                MySqlDataReader result = cmd.ExecuteReader();

                if (result.HasRows)
                {
                    list = new List<Data>();

                    while (result.Read())
                    {
                        Pembayaran pembayaran = new Pembayaran();

                        pembayaran.Pelanggan = (Pelanggan)PelangganDao.Instance.get(result.GetString("ID_PELANGGAN"));
                        pembayaran.Tahun = result.GetInt16("TAHUN");
                        pembayaran.Bulan = result.GetInt16("BULAN");
                        pembayaran.Biaya = result.GetDouble("BIAYA");
                        pembayaran.TanggalBayar = result.GetString("TANGGAL_BAYAR");
                        pembayaran.Keterangan = result.GetString("KET");

                        list.Add(pembayaran);
                    }
                }
                result.Close();
            }
            conn.close();

            return list;
        }

        public override void delete(Data data)
        {
            try
            {
                Pembayaran pembayaran = (Pembayaran)data;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = DELETE;
                cmd.Parameters.Add("?ID_PELANGGAN", MySqlDbType.VarChar, 255).Value = pembayaran.Pelanggan.Id;
                cmd.Parameters.Add("?TAHUN", MySqlDbType.VarChar, 255).Value = pembayaran.Tahun;
                cmd.Parameters.Add("?BULAN", MySqlDbType.VarChar, 255).Value = pembayaran.Bulan;

                MySqlDataReader result = cmd.ExecuteReader();

                result.Close();
                conn.close();
            }
            catch (SystemException exc)
            {
                throw new DataException(exc.Message);
            }
        }

        public override void add(Data data)
        {
            try
            {
                Pembayaran pembayaran = (Pembayaran)data;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = INSERT;
                cmd.Parameters.Add("?ID_PELANGGAN", MySqlDbType.VarChar, 255).Value = pembayaran.Pelanggan.Id;
                cmd.Parameters.Add("?TAHUN", MySqlDbType.Int16, 255).Value = pembayaran.Tahun;
                cmd.Parameters.Add("?BULAN", MySqlDbType.Int16, 255).Value = pembayaran.Bulan;
                cmd.Parameters.Add("?BIAYA", MySqlDbType.Double, 255).Value = pembayaran.Biaya;
                cmd.Parameters.Add("?TANGGAL_BAYAR", MySqlDbType.VarChar, 255).Value = pembayaran.TanggalBayar;
                cmd.Parameters.Add("?KET", MySqlDbType.VarChar, 255).Value = pembayaran.Keterangan;

                MySqlDataReader result = cmd.ExecuteReader();

                result.Close();
                conn.close();
            }
            catch (SystemException exc)
            {
                throw new DataException(exc.Message);
            }
        }

        public override void update(Data data)
        {
            try
            {
                Pembayaran pembayaran = (Pembayaran)data;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = UPDATE;
                cmd.Parameters.Add("?ID_PELANGGAN", MySqlDbType.VarChar, 255).Value = pembayaran.Pelanggan.Id;
                cmd.Parameters.Add("?TAHUN", MySqlDbType.Int16, 255).Value = pembayaran.Tahun;
                cmd.Parameters.Add("?BULAN", MySqlDbType.Int16, 255).Value = pembayaran.Bulan;
                cmd.Parameters.Add("?BIAYA", MySqlDbType.Double, 255).Value = pembayaran.Biaya;
                cmd.Parameters.Add("?TANGGAL_BAYAR", MySqlDbType.VarChar, 255).Value = pembayaran.TanggalBayar;
                cmd.Parameters.Add("?KET", MySqlDbType.VarChar, 255).Value = pembayaran.Keterangan;

                MySqlDataReader result = cmd.ExecuteReader();

                result.Close();
                conn.close();
            }
            catch (SystemException exc)
            {
                throw new DataException(exc.Message);
            }
        }
    }

    public class LoginDao : DataDao
    {
        private static LoginDao instance;

        public static LoginDao Instance
        {
            get 
            {
                if (instance == null)
                {
                    instance = new LoginDao();
                }
                return instance;
            }
        }

        private LoginDao() : base()
        {
            conn = new util.ConnectionManager();
            INSERT = "insert into LOGIN values(?USERNAME, ?PASSWORD, ?NAMA, ?ROLE)";
            UPDATE = "update LOGIN set PASSWORD=?PASSWORD, NAMA=?NAMA, ROLE=?ROLE where USERNAME=?USERNAME";
            DELETE = "delete from LOGIN where USERNAME=?USERNAME";
            SELECT = "select * from LOGIN where USERNAME=?USERNAME";
            SELECT_ALL = "select * from LOGIN order by USERNAME";
        }

        public override IList<Data> getAll()
        {
            IList<Data> list = null;

            MySqlCommand cmd = new MySqlCommand(SELECT_ALL, conn.Connection);
            MySqlDataReader result = cmd.ExecuteReader();

            if (result.HasRows)
            {
                list = new List<Data>();

                while (result.Read())
                {
                    Login login = new Login();

                    login.Username = result.GetString("USERNAME");
                    login.Password = result.GetString("PASSWORD");
                    login.Nama = result.GetString("NAMA");
                    login.Role = result.GetString("ROLE");

                    list.Add(login);
                }
            }

            result.Close();
            conn.close();

            return list;
        }

        public override Data get(object id)
        {
            Login login = null;

            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = SELECT;
            cmd.Parameters.Add("?USERNAME", MySqlDbType.VarChar, 255).Value = id;

            MySqlDataReader result = cmd.ExecuteReader();

            if (result.HasRows)
            {
                login = new Login();

                result.Read();

                login.Username = (String)id;
                login.Password = result.GetString("PASSWORD");
                login.Nama = result.GetString("NAMA");
                login.Role = result.GetString("ROLE");
            }

            result.Close();
            conn.close();

            return login;
        }

        public override IList<Data> search(object criteria)
        {
            IList<Data> lsData = new List<Data>();

            lsData.Add(get(criteria));

            return lsData;
        }

        public override void delete(Data data)
        {
            try
            {
                Login login = (Login)data;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = DELETE;
                cmd.Parameters.Add("?USERNAME", MySqlDbType.VarChar, 255).Value = login.Username;

                MySqlDataReader result = cmd.ExecuteReader();

                result.Close();
                conn.close();
            }
            catch (SystemException exc)
            {
                throw new DataException(exc.Message);
            }
        }

        public override void add(Data data)
        {
            try
            {
                Login login = (Login)data;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = INSERT;
                cmd.Parameters.Add("?USERNAME", MySqlDbType.VarChar, 255).Value = login.Username;
                cmd.Parameters.Add("?PASSWORD", MySqlDbType.VarChar, 255).Value = login.Password;
                cmd.Parameters.Add("?NAMA", MySqlDbType.VarChar, 255).Value = login.Nama;
                cmd.Parameters.Add("?ROLE", MySqlDbType.VarChar, 255).Value = login.Role;

                MySqlDataReader result = cmd.ExecuteReader();

                result.Close();
                conn.close();
            }
            catch (SystemException exc)
            {
                throw new DataException(exc.Message);
            }
        }

        public override void update(Data data)
        {
            try
            {
                Login login = (Login)data;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = UPDATE;
                cmd.Parameters.Add("?USERNAME", MySqlDbType.VarChar, 255).Value = login.Username;
                cmd.Parameters.Add("?PASSWORD", MySqlDbType.VarChar, 255).Value = login.Password;
                cmd.Parameters.Add("?NAMA", MySqlDbType.VarChar, 255).Value = login.Nama;
                cmd.Parameters.Add("?ROLE", MySqlDbType.VarChar, 255).Value = login.Role;

                MySqlDataReader result = cmd.ExecuteReader();

                result.Close();
                conn.close();
            }
            catch (SystemException exc)
            {
                throw new DataException(exc.Message);
            }
        }
    }

    public class DetailsDao : DataDao
    {
        private static DetailsDao instance;

        private DetailsDao() : base()
        {
            conn = new util.ConnectionManager();
            INSERT = "insert into DETAILS values(?ID_PELANGGAN, ?PENDAFTARAN, ?IURAN)";
            UPDATE = "update DETAILS set PENDAFTARAN=?PENDAFTARAN, IURAN=?IURAN where ID_PELANGGAN=?ID_PELANGGAN";
            DELETE = "delete from DETAILS where ID_PELANGGAN=?ID_PELANGGAN";
            SELECT = "select * from DETAILS where ID_PELANGGAN=?ID_PELANGGAN";
            SELECT_ALL = "select * from DETAILS order by ID_PELANGGAN";
        }
        
        public static DetailsDao Instance
        {
            get
            {
                if (instance == null)
                {
                    instance = new DetailsDao();
                }
                return instance;
            }
        }

        public override IList<Data> getAll()
        {
            IList<Data> list = null;

            MySqlCommand cmd = new MySqlCommand(SELECT_ALL, conn.Connection);
            MySqlDataReader result = cmd.ExecuteReader();

            if (result.HasRows)
            {
                list = new List<Data>();

                while (result.Read())
                {
                    Details details = new Details();

                    details.Pelanggan = (Pelanggan)PelangganDao.Instance.get(result.GetString("ID_PELANGGAN"));
                    details.Pendaftaran = Double.Parse(result.GetString("PENDAFTARAN"));
                    details.Iuran = Double.Parse(result.GetString("IURAN"));

                    list.Add(details);
                }
            }

            result.Close();
            conn.close();

            return list;
        }

        public override Data get(object id)
        {
            Details details = null;

            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = SELECT;
            cmd.Parameters.Add("?ID_PELANGGAN", MySqlDbType.VarChar, 255).Value = id;

            MySqlDataReader result = cmd.ExecuteReader();

            if (result.HasRows)
            {
                details = new Details();

                result.Read();

                details.Pelanggan = (Pelanggan)PelangganDao.Instance.get((String)id);
                details.Pendaftaran = Double.Parse(result.GetString("PENDAFTARAN"));
                details.Iuran = Double.Parse(result.GetString("IURAN"));
            }

            result.Close();
            conn.close();

            return details;
        }

        public override IList<Data> search(object criteria)
        {
            IList<Data> lsData = new List<Data>();

            lsData.Add(get(criteria));

            return lsData;
        }

        public override void add(Data data)
        {
            try
            {
                Details details = (Details)data;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = INSERT;
                cmd.Parameters.Add("?ID_PELANGGAN", MySqlDbType.VarChar, 255).Value = details.Pelanggan.Id;
                cmd.Parameters.Add("?PENDAFTARAN", MySqlDbType.VarChar, 255).Value = details.Pendaftaran;
                cmd.Parameters.Add("?IURAN", MySqlDbType.VarChar, 255).Value = details.Iuran;

                MySqlDataReader result = cmd.ExecuteReader();

                result.Close();
                conn.close();
            }
            catch (SystemException exc)
            {
                throw new DataException(exc.Message);
            }
        }

        public override void update(Data data)
        {
            try
            {
                Details details = (Details)data;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = UPDATE;
                cmd.Parameters.Add("?ID_PELANGGAN", MySqlDbType.VarChar, 255).Value = details.Pelanggan.Id;
                cmd.Parameters.Add("?PENDAFTARAN", MySqlDbType.VarChar, 255).Value = details.Pendaftaran;
                cmd.Parameters.Add("?IURAN", MySqlDbType.VarChar, 255).Value = details.Iuran;

                MySqlDataReader result = cmd.ExecuteReader();

                result.Close();
                conn.close();
            }
            catch (SystemException exc)
            {
                throw new DataException(exc.Message);
            }
        }

        public override void delete(Data data)
        {
            try
            {
                Details details = (Details)data;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = DELETE;
                cmd.Parameters.Add("?ID_PELANGGAN", MySqlDbType.VarChar, 255).Value = details.Pelanggan.Id;

                MySqlDataReader result = cmd.ExecuteReader();

                result.Close();
                conn.close();
            }
            catch (SystemException exc)
            {
                throw new DataException(exc.Message);
            }
        }
    }
}

