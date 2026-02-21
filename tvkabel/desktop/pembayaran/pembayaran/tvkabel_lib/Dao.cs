using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using MySql.Data.MySqlClient;
using com.gtd.tvkabel.data.entity;
using com.gtd.tvkabel.data.util.exception;
using com.gtd.tvkabel.data.util;

namespace com.gtd.tvkabel.data.dao
{
    public abstract class Dao
    {
        protected ConnectionManager conn = new util.ConnectionManager();
        public abstract IList<Data> getAll();
        public abstract void delete(Data data);
        public abstract void add(Data data);
        public abstract void update(Data data);
    }

    public class PelangganDao : Dao
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

        private PelangganDao() : base() { }

        public override IList<Data> getAll()
        {
            MySqlDataReader result = null;

            try
            {
                IList<Data> list = null;

                MySqlCommand cmd = new MySqlCommand(Pelanggan.SELECT_ALL, conn.Connection);
                result = cmd.ExecuteReader();

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
                        pelanggan.Kode = result.GetInt32("KODE");
                        pelanggan.Keterangan = result.GetString("KETERANGAN");

                        list.Add(pelanggan);
                    }
                    result.Close();
                }
                else
                {
                    throw new DataNotFoundException("Tidak ada data yang ditemukan pada tabel Pelanggan");
                }
                return list;
            }
            catch (DataNotFoundException E)
            {
                throw E;
            }
            finally
            {
                conn.close();
            }
        }

        public Pelanggan get(String id)
        {
            MySqlDataReader result = null;

            try
            {
                Pelanggan pelanggan = null;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = Pelanggan.SELECT;
                cmd.Parameters.Add("?ID", MySqlDbType.VarChar, 255).Value = id;

                result = cmd.ExecuteReader();

                if (result.HasRows)
                {
                    pelanggan = new Pelanggan();

                    result.Read();

                    pelanggan.Id = id;
                    pelanggan.Nama = result.GetString("NAMA");
                    pelanggan.TglMulai = result.GetString("TGL_MULAI");
                    pelanggan.JumlahTv = result.GetInt16("JUMLAH_TV");
                    pelanggan.Alamat = result.GetString("ALAMAT");
                    pelanggan.Status = result.GetInt16("STATUS");
                    pelanggan.Kode = result.GetInt32("KODE");
                    pelanggan.Keterangan = result.GetString("KETERANGAN");

                    result.Close();
                }
                else
                {
                    throw new DataNotFoundException("Tidak ada data yang ditemukan pada tabel Pelanggan untuk id '" + id + "'");
                }

                return pelanggan;
            }
            catch (DataNotFoundException E)
            {
                throw E;
            }
            finally
            {
                conn.close();
            }
        }

        public Pelanggan getByKode(Int32 kode)
        {
            MySqlDataReader result = null;

            try
            {
                Pelanggan pelanggan = null;

                MySqlCommand cmd = new MySqlCommand(Pelanggan.SELECTBYKODE, conn.Connection);
                cmd.Parameters.Add("?KODE", MySqlDbType.Int32, 11).Value = kode;

                result = cmd.ExecuteReader();

                if (result.HasRows)
                {
                    pelanggan = new Pelanggan();

                    result.Read();

                    pelanggan.Id = result.GetString("ID");
                    pelanggan.Nama = result.GetString("NAMA");
                    pelanggan.TglMulai = result.GetString("TGL_MULAI");
                    pelanggan.JumlahTv = result.GetInt16("JUMLAH_TV");
                    pelanggan.Alamat = result.GetString("ALAMAT");
                    pelanggan.Status = result.GetInt16("STATUS");
                    pelanggan.Keterangan = result.GetString("KETERANGAN");
                    pelanggan.Kode = kode;

                    result.Close();
                }
                else
                {
                    throw new DataNotFoundException("Tidak ada data yang ditemukan pada tabel Pelanggan untuk kode '" + kode + "'");
                }
                return pelanggan;
            }
            catch (DataNotFoundException E)
            {
                throw E;
            }
            finally
            {
                conn.close();
            }
        }

        public IList<Pelanggan> getByNama(String nama)
        {
            MySqlDataReader result = null;

            try
            {
                IList<Pelanggan> list = null;

                MySqlCommand cmd = new MySqlCommand(Pelanggan.SELECTBYNAME, conn.Connection);
                cmd.Parameters.Add("?NAMA", MySqlDbType.VarChar, 255).Value = "%" + nama + "%";
                result = cmd.ExecuteReader();

                if (result.HasRows)
                {
                    list = new List<Pelanggan>();

                    while (result.Read())
                    {
                        Pelanggan pelanggan = new Pelanggan();

                        pelanggan.Id = result.GetString("ID");
                        pelanggan.Nama = result.GetString("NAMA");
                        pelanggan.TglMulai = result.GetString("TGL_MULAI");
                        pelanggan.JumlahTv = result.GetInt16("JUMLAH_TV");
                        pelanggan.Alamat = result.GetString("ALAMAT");
                        pelanggan.Status = result.GetInt16("STATUS");
                        pelanggan.Kode = result.GetInt32("KODE");
                        pelanggan.Keterangan = result.GetString("KETERANGAN");

                        list.Add(pelanggan);
                    }
                    result.Close();
                }
                else
                {
                    throw new DataNotFoundException("Tidak ada data yang ditemukan pada tabel Pelanggan untuk nama '" + nama + "'");
                }

                return list;
            }
            catch (DataNotFoundException E)
            {
                throw E;
            }
            finally
            {
                conn.close();
            }
        }

        public IList<Pelanggan> getByStatus(short status)
        {
            MySqlDataReader result = null;

            try
            {
                IList<Pelanggan> list = null;

                MySqlCommand cmd = new MySqlCommand(Pelanggan.GETBYSTATUS, conn.Connection);
                cmd.Parameters.Add("?STATUS", MySqlDbType.Int16, 1).Value = status;
                result = cmd.ExecuteReader();

                if (result.HasRows)
                {
                    list = new List<Pelanggan>();

                    while (result.Read())
                    {
                        Pelanggan pelanggan = new Pelanggan();

                        pelanggan.Id = result.GetString("ID");
                        pelanggan.Nama = result.GetString("NAMA");
                        pelanggan.TglMulai = result.GetString("TGL_MULAI");
                        pelanggan.JumlahTv = result.GetInt16("JUMLAH_TV");
                        pelanggan.Alamat = result.GetString("ALAMAT");
                        pelanggan.Status = status;
                        pelanggan.Kode = result.GetInt32("KODE");
                        pelanggan.Keterangan = result.GetString("KETERANGAN");

                        list.Add(pelanggan);
                    }
                    result.Close();
                }
                else
                {
                    throw new DataNotFoundException("Tidak ada data yang ditemukan pada tabel Pelanggan untuk status '" + status + "'");
                }
                return list;
            }
            catch (DataNotFoundException E)
            {
                throw E;
            }
            finally
            {
                conn.close();
            }
        }

        public void deleteByKode(Pelanggan pelanggan)
        {
            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = Pelanggan.DELETEBYKODE;
            cmd.Parameters.Add("?KODE", MySqlDbType.Int32, 11).Value = pelanggan.Kode;

            MySqlDataReader result = cmd.ExecuteReader();

            result.Close();
            conn.close();
        }

        public override void delete(Data data)
        {
            Pelanggan pelanggan = (Pelanggan)data;

            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = Pelanggan.DELETE;
            cmd.Parameters.Add("?ID", MySqlDbType.VarChar, 255).Value = pelanggan.Id;

            MySqlDataReader result = cmd.ExecuteReader();

            result.Close();
            conn.close();
        }

        public override void add(Data data)
        {
            Pelanggan pelanggan = (Pelanggan)data;

            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = Pelanggan.INSERT;
            cmd.Parameters.Add("?ID", MySqlDbType.VarChar, 255).Value = pelanggan.Id;
            cmd.Parameters.Add("?NAMA", MySqlDbType.VarChar, 255).Value = pelanggan.Nama;
            cmd.Parameters.Add("?TGL_MULAI", MySqlDbType.VarChar, 10).Value = pelanggan.TglMulai;
            cmd.Parameters.Add("?JUMLAH_TV", MySqlDbType.Int32, 11).Value = pelanggan.JumlahTv;
            cmd.Parameters.Add("?ALAMAT", MySqlDbType.VarChar, 255).Value = pelanggan.Alamat;
            cmd.Parameters.Add("?STATUS", MySqlDbType.Int16, 1).Value = pelanggan.Status;
            cmd.Parameters.Add("?KETERANGAN", MySqlDbType.VarChar, 255).Value = pelanggan.Keterangan;

            MySqlDataReader result = cmd.ExecuteReader();

            result.Close();
            conn.close();
        }

        public override void update(Data data)
        {
            Pelanggan pelanggan = (Pelanggan)data;

            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = Pelanggan.UPDATE;
            cmd.Parameters.Add("?ID", MySqlDbType.VarChar, 255).Value = pelanggan.Id;
            cmd.Parameters.Add("?NAMA", MySqlDbType.VarChar, 255).Value = pelanggan.Nama;
            cmd.Parameters.Add("?TGL_MULAI", MySqlDbType.VarChar, 255).Value = pelanggan.TglMulai;
            cmd.Parameters.Add("?JUMLAH_TV", MySqlDbType.Int32, 255).Value = pelanggan.JumlahTv;
            cmd.Parameters.Add("?ALAMAT", MySqlDbType.VarChar, 255).Value = pelanggan.Alamat;
            cmd.Parameters.Add("?STATUS", MySqlDbType.Int16, 1).Value = pelanggan.Status;
            cmd.Parameters.Add("?KODE", MySqlDbType.Int32, 255).Value = pelanggan.Kode;
            cmd.Parameters.Add("?KETERANGAN", MySqlDbType.VarChar, 255).Value = pelanggan.Keterangan;

            MySqlDataReader result = cmd.ExecuteReader();

            result.Close();
            conn.close();
        }
    }

    public class KontakDao : Dao
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

        private KontakDao() : base() { }

        public override IList<Data> getAll()
        {
            MySqlDataReader result = null;

            try
            {
                IList<Data> list = null;

                MySqlCommand cmd = new MySqlCommand(Kontak.SELECT_ALL, conn.Connection);
                result = cmd.ExecuteReader();

                if (result.HasRows)
                {
                    list = new List<Data>();

                    while (result.Read())
                    {
                        Kontak kontak = new Kontak();

                        kontak.Pelanggan = (Pelanggan)PelangganDao.Instance.getByKode(result.GetInt32("KODE_PELANGGAN"));
                        kontak.TipeKontak = (TipeKontak)TipeKontakDao.Instance.get(result.GetString("TIPE"));
                        kontak.Detail = result.GetString("DETAIL");

                        list.Add(kontak);
                    }
                }
                else
                {
                    throw new DataNotFoundException("Tidak ada data yang ditemukan pada tabel Kontak");
                }

                return list;
            }
            catch (DataNotFoundException E)
            {
                throw E;
            }
            finally
            {
                result.Close();
                conn.close();
            }
        }

        public Kontak get(Pelanggan Pelanggan, TipeKontak TipeKontak)
        {
            MySqlDataReader result = null;

            try
            {
                Kontak kontak = null;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = Kontak.SELECT;
                cmd.Parameters.Add("?KODE_PELANGGAN", MySqlDbType.Int32, 11).Value = Pelanggan.Kode;
                cmd.Parameters.Add("?TIPE", MySqlDbType.VarChar, 255).Value = TipeKontak.Kode;

                result = cmd.ExecuteReader();

                if (result.HasRows)
                {
                    kontak = new Kontak();

                    result.Read();

                    kontak.Pelanggan = Pelanggan;
                    kontak.TipeKontak = TipeKontak;
                    kontak.Detail = result.GetString("DETAIL");
                }
                else
                {
                    throw new DataNotFoundException("Tidak ada data yang ditemukan pada tabel Kontak untuk pelanggan '" + Pelanggan.Nama + "' dan tipe kontak '" + TipeKontak.Detail + "'");
                }

                return kontak;
            }
            catch (DataNotFoundException E)
            {
                throw E;
            }
            finally
            {
                result.Close();
                conn.close();
            }
        }

        public IList<Data> getByPelanggan(Pelanggan Pelanggan)
        {
            MySqlDataReader result = null;

            try
            {
                IList<Data> list = null;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = Kontak.GETBYPELANGGAN;
                cmd.Parameters.Add("?KODE_PELANGGAN", MySqlDbType.Int32, 11).Value = Pelanggan.Kode;

                result = cmd.ExecuteReader();

                if (result.HasRows)
                {
                    list = new List<Data>();

                    while (result.Read())
                    {
                        Kontak kontak = new Kontak();

                        kontak.Pelanggan = Pelanggan;
                        kontak.TipeKontak = (TipeKontak)TipeKontakDao.Instance.get(result.GetString("TIPE"));
                        kontak.Detail = result.GetString("DETAIL");

                        list.Add(kontak);
                    }
                }
                else
                {
                    throw new DataNotFoundException("Tidak ada data yang ditemukan pada tabel Kontak untuk pelanggan '" + Pelanggan.Nama + "'");
                }

                return list;
            }
            catch (DataNotFoundException E)
            {
                throw E;
            }
            finally
            {
                result.Close();
                conn.close();
            }
        }

        public IList<Data> getByTipeKontak(TipeKontak TipeKontak)
        {
            MySqlDataReader result = null;

            try
            {
                IList<Data> list = null;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = Kontak.GETBYPELANGGAN;
                cmd.Parameters.Add("?TIPE", MySqlDbType.VarChar, 255).Value = TipeKontak.Kode;

                result = cmd.ExecuteReader();

                if (result.HasRows)
                {
                    list = new List<Data>();

                    while (result.Read())
                    {
                        Kontak kontak = new Kontak();

                        kontak.Pelanggan = (Pelanggan)PelangganDao.Instance.getByKode(result.GetInt32("KODE_PELANGGAN"));
                        kontak.TipeKontak = TipeKontak;
                        kontak.Detail = result.GetString("DETAIL");

                        list.Add(kontak);
                    }
                }
                else
                {
                    throw new DataNotFoundException("Tidak ada data yang ditemukan pada tabel Kontak untuk tipe kontak '" + TipeKontak.Detail + "'");
                }

                return list;
            }
            catch (DataNotFoundException E)
            {
                throw E;
            }
            finally
            {
                result.Close();
                conn.close();
            }
        }

        public void deleteByPelanggan(Pelanggan pelanggan)
        {
            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = Kontak.DELETEBYPELANGGAN;
            cmd.Parameters.Add("?KODE_PELANGGAN", MySqlDbType.Int32, 11).Value = pelanggan.Kode;

            MySqlDataReader result = cmd.ExecuteReader();

            result.Close();
            conn.close();
        }

        public void deleteByTipe(TipeKontak tipe)
        {
            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = Kontak.DELETEBYTIPE;
            cmd.Parameters.Add("?TIPE", MySqlDbType.VarChar, 255).Value = tipe.Kode;

            MySqlDataReader result = cmd.ExecuteReader();

            result.Close();
            conn.close();
        }

        public override void delete(Data data)
        {
            Kontak kontak = (Kontak)data;

            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = Kontak.DELETE;
            cmd.Parameters.Add("?KODE_PELANGGAN", MySqlDbType.Int32, 11).Value = kontak.Pelanggan.Kode;
            cmd.Parameters.Add("?TIPE", MySqlDbType.VarChar, 255).Value = kontak.TipeKontak.Kode;

            MySqlDataReader result = cmd.ExecuteReader();

            result.Close();
            conn.close();
        }

        public override void add(Data data)
        {
            Kontak kontak = (Kontak)data;

            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = Kontak.INSERT;
            cmd.Parameters.Add("?KODE_PELANGGAN", MySqlDbType.Int32, 11).Value = kontak.Pelanggan.Kode;
            cmd.Parameters.Add("?TIPE", MySqlDbType.VarChar, 255).Value = kontak.TipeKontak.Kode;
            cmd.Parameters.Add("?DETAIL", MySqlDbType.VarChar, 255).Value = kontak.Detail;

            MySqlDataReader result = cmd.ExecuteReader();

            result.Close();
            conn.close();
        }

        public override void update(Data data)
        {
            Kontak kontak = (Kontak)data;

            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = Kontak.UPDATE;
            cmd.Parameters.Add("?KODE_PELANGGAN", MySqlDbType.Int32, 11).Value = kontak.Pelanggan.Kode;
            cmd.Parameters.Add("?TIPE", MySqlDbType.VarChar, 255).Value = kontak.TipeKontak.Kode;
            cmd.Parameters.Add("?DETAIL", MySqlDbType.VarChar, 255).Value = kontak.Detail;

            MySqlDataReader result = cmd.ExecuteReader();

            result.Close();
            conn.close();
        }
    }

    public class TipeKontakDao : Dao
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

        private TipeKontakDao() : base() { }

        public override IList<Data> getAll()
        {
            MySqlDataReader result = null;

            try
            {
                IList<Data> list = null;

                MySqlCommand cmd = new MySqlCommand(TipeKontak.SELECT_ALL, conn.Connection);
                result = cmd.ExecuteReader();

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
                else
                {
                    throw new DataNotFoundException("Tidaka ada data pada tabel Tipe Kontak");
                }

                return list;
            }
            catch (DataNotFoundException E)
            {
                throw E;
            }
            finally
            {
                result.Close();
                conn.close();
            }
        }

        public TipeKontak get(String kode)
        {
            MySqlDataReader result = null;

            try
            {
                TipeKontak tipeKontak = null;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = TipeKontak.SELECT;
                cmd.Parameters.Add("?KODE", MySqlDbType.VarChar, 255).Value = kode;

                result = cmd.ExecuteReader();

                if (result.HasRows)
                {
                    tipeKontak = new TipeKontak();

                    result.Read();

                    tipeKontak.Kode = kode;
                    tipeKontak.Detail = result.GetString("DETAIL");
                }
                else
                {
                    throw new DataNotFoundException("Tidak ada data pada tabel Tipe Kontak untuk kode '" + kode + "'");
                }

                return tipeKontak;
            }
            catch (DataNotFoundException E)
            {
                throw E;
            }
            finally
            {
                result.Close();
                conn.close();
            }
        }

        public override void delete(Data data)
        {
            TipeKontak tipeKontak = (TipeKontak)data;

            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = TipeKontak.DELETE;
            cmd.Parameters.Add("?KODE", MySqlDbType.VarChar, 255).Value = tipeKontak.Kode;

            MySqlDataReader result = cmd.ExecuteReader();

            result.Close();
            conn.close();
        }

        public override void add(Data data)
        {
            TipeKontak tipeKontak = (TipeKontak)data;

            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = TipeKontak.INSERT;
            cmd.Parameters.Add("?KODE", MySqlDbType.VarChar, 255).Value = tipeKontak.Kode;
            cmd.Parameters.Add("?DETAIL", MySqlDbType.VarChar, 255).Value = tipeKontak.Detail;

            MySqlDataReader result = cmd.ExecuteReader();

            result.Close();
            conn.close();
        }

        public override void update(Data data)
        {
            TipeKontak tipeKontak = (TipeKontak)data;

            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = TipeKontak.UPDATE;
            cmd.Parameters.Add("?KODE", MySqlDbType.VarChar, 255).Value = tipeKontak.Kode;
            cmd.Parameters.Add("?DETAIL", MySqlDbType.VarChar, 255).Value = tipeKontak.Detail;

            MySqlDataReader result = cmd.ExecuteReader();

            result.Close();
            conn.close();
        }
    }

    public class PembayaranDao : Dao
    {
        private static PembayaranDao instance;
        
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

        private PembayaranDao() : base() { }

        public override IList<Data> getAll()
        {
            MySqlDataReader result = null;

            try
            {
                IList<Data> list = null;

                MySqlCommand cmd = new MySqlCommand(Pembayaran.SELECT_ALL, conn.Connection);
                result = cmd.ExecuteReader();

                if (result.HasRows)
                {
                    list = new List<Data>();

                    while (result.Read())
                    {
                        Pembayaran pembayaran = new Pembayaran();

                        pembayaran.Pelanggan = (Pelanggan)PelangganDao.Instance.getByKode(result.GetInt32("KODE_PELANGGAN"));
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

                return list;
            }
            catch (DataNotFoundException E)
            {
                throw E;
            }
            finally
            {
                result.Close();
                conn.close();
            }
        }

        public Pembayaran get(Pelanggan Pelanggan, int Tahun, int Bulan)
        {
            MySqlDataReader result = null;

            try
            {
                Pembayaran pembayaran = null;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = Pembayaran.SELECT;
                cmd.Parameters.Add("?KODE_PELANGGAN", MySqlDbType.Int32, 11).Value = Pelanggan.Kode;
                cmd.Parameters.Add("?TAHUN", MySqlDbType.Int32, 4).Value = Tahun;
                cmd.Parameters.Add("?BULAN", MySqlDbType.Int32, 2).Value = Bulan;

                result = cmd.ExecuteReader();

                if (result.HasRows)
                {
                    pembayaran = new Pembayaran();

                    result.Read();

                    pembayaran.Pelanggan = Pelanggan;
                    pembayaran.Tahun = Tahun;
                    pembayaran.Bulan = Bulan;
                    pembayaran.Biaya = result.GetDouble("BIAYA");
                    pembayaran.TanggalBayar = result.GetString("TANGGAL_BAYAR");
                    pembayaran.Keterangan = result.GetString("KET");
                }
                else
                {
                    throw new DataNotFoundException("Tidak ada data yang ditemukan pada tabel Pembayaran untuk pelanggan '" + Pelanggan.Nama + "' pada bulan '" + Bulan + " " + Tahun + "'");
                }

                return pembayaran;
            }
            catch (DataNotFoundException E)
            {
                throw E;
            }
            finally
            {
                result.Close();
                conn.close();
            }
        }

        public IList<Pembayaran> getByPelanggan(Pelanggan pelanggan)
        {
            MySqlDataReader result = null;

            try
            {
                IList<Pembayaran> list = null;
                MySqlCommand cmd = conn.Connection.CreateCommand();

                cmd.CommandText = Pembayaran.GETBYPELANGGAN;
                cmd.Parameters.Add("?KODE_PELANGGAN", MySqlDbType.Int32, 11).Value = pelanggan.Kode;

                result = cmd.ExecuteReader();

                if (result.HasRows)
                {
                    list = new List<Pembayaran>();

                    while (result.Read())
                    {
                        Pembayaran pembayaran = new Pembayaran();

                        pembayaran.Pelanggan = pelanggan;
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
                    throw new DataNotFoundException("Tidak ada data yang ditemukan pada tabel Pembayaran untuk pelanggan '" + pelanggan.Nama + "'");
                }

                return list;
            }
            catch (DataNotFoundException E)
            {
                throw E;
            }
            finally
            {
                result.Close();
                conn.close();
            }
        }

        public IList<Pembayaran> getByTanggal(int tahun, int bulan)
        {
            MySqlDataReader result = null;

            try
            {
                IList<Pembayaran> list = null;
                MySqlCommand cmd = conn.Connection.CreateCommand();

                cmd.CommandText = Pembayaran.GETBYTANGGAL;
                cmd.Parameters.Add("?TAHUN", MySqlDbType.Int32, 4).Value = tahun;
                cmd.Parameters.Add("?BULAN", MySqlDbType.Int32, 2).Value = bulan;

                result = cmd.ExecuteReader();

                if (result.HasRows)
                {
                    list = new List<Pembayaran>();

                    while (result.Read())
                    {
                        Pembayaran pembayaran = new Pembayaran();

                        pembayaran.Pelanggan = (Pelanggan)PelangganDao.Instance.getByKode(result.GetInt32("KODE_PELANGGAN"));
                        pembayaran.Tahun = tahun;
                        pembayaran.Bulan = bulan;
                        pembayaran.Biaya = result.GetDouble("BIAYA");
                        pembayaran.TanggalBayar = result.GetString("TANGGAL_BAYAR");
                        pembayaran.Keterangan = result.GetString("KET");

                        list.Add(pembayaran);
                    }
                }
                else
                {
                    throw new DataNotFoundException("Tidak ada data yang ditemukan pada tabel Pembayaran untuk bulan '" + bulan + " " + tahun + "'");
                }

                return list;
            }
            catch (DataNotFoundException E)
            {
                throw E;
            }
            finally
            {
                result.Close();
                conn.close();
            }
        }

        public IList<Pembayaran> getByNama(String Nama)
        {
            MySqlDataReader result = null;

            try
            {
                IList<Pembayaran> list = null;

                MySqlCommand cmd = new MySqlCommand(Pembayaran.GETBYNAMA, conn.Connection);
                cmd.Parameters.Add("?NAMA", MySqlDbType.VarChar, 255).Value = "%" + Nama + "%";
                result = cmd.ExecuteReader();

                if (result.HasRows)
                {
                    list = new List<Pembayaran>();

                    while (result.Read())
                    {
                        Pembayaran pembayaran = new Pembayaran();

                        pembayaran.Pelanggan = (Pelanggan)PelangganDao.Instance.getByKode(result.GetInt32("KODE_PELANGGAN"));
                        pembayaran.Tahun = result.GetInt32("TAHUN");
                        pembayaran.Bulan = result.GetInt32("BULAN");
                        pembayaran.Biaya = result.GetDouble("BIAYA");
                        pembayaran.TanggalBayar = result.GetString("TANGGAL_BAYAR");
                        pembayaran.Keterangan = result.GetString("KET");

                        list.Add(pembayaran);
                    }
                    result.Close();
                }
                else
                {
                    throw new DataNotFoundException("Tidak ada data yang ditemukan pada tabel Pembayaran untuk nama '" + Nama + "'");
                }

                return list;
            }
            catch (DataNotFoundException E)
            {
                throw E;
            }
            finally
            {
                conn.close();
            }
        }

        public void deleteByPelanggan(Pelanggan pelanggan)
        {
            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = Pembayaran.DELETEBYPELANGGAN;
            cmd.Parameters.Add("?KODE_PELANGGAN", MySqlDbType.Int32, 11).Value = pelanggan.Kode;

            MySqlDataReader result = cmd.ExecuteReader();

            result.Close();
            conn.close();
        }

        public override void delete(Data data)
        {
            Pembayaran pembayaran = (Pembayaran)data;

            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = Pembayaran.DELETE;
            cmd.Parameters.Add("?KODE_PELANGGAN", MySqlDbType.Int32, 11).Value = pembayaran.Pelanggan.Kode;
            cmd.Parameters.Add("?TAHUN", MySqlDbType.Int32, 4).Value = pembayaran.Tahun;
            cmd.Parameters.Add("?BULAN", MySqlDbType.Int32, 2).Value = pembayaran.Bulan;

            MySqlDataReader result = cmd.ExecuteReader();

            result.Close();
            conn.close();
        }

        public override void add(Data data)
        {
            Pembayaran pembayaran = (Pembayaran)data;

            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = Pembayaran.INSERT;
            cmd.Parameters.Add("?KODE_PELANGGAN", MySqlDbType.Int32, 11).Value = pembayaran.Pelanggan.Kode;
            cmd.Parameters.Add("?TAHUN", MySqlDbType.Int16, 4).Value = pembayaran.Tahun;
            cmd.Parameters.Add("?BULAN", MySqlDbType.Int16, 2).Value = pembayaran.Bulan;
            cmd.Parameters.Add("?BIAYA", MySqlDbType.Double, 255).Value = pembayaran.Biaya;
            cmd.Parameters.Add("?TANGGAL_BAYAR", MySqlDbType.VarChar, 255).Value = pembayaran.TanggalBayar;
            cmd.Parameters.Add("?KET", MySqlDbType.VarChar, 255).Value = pembayaran.Keterangan;

            MySqlDataReader result = cmd.ExecuteReader();

            result.Close();
            conn.close();
        }

        public override void update(Data data)
        {
            Pembayaran pembayaran = (Pembayaran)data;

            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = Pembayaran.UPDATE;
            cmd.Parameters.Add("?KODE_PELANGGAN", MySqlDbType.Int32, 11).Value = pembayaran.Pelanggan.Kode;
            cmd.Parameters.Add("?TAHUN", MySqlDbType.Int16, 4).Value = pembayaran.Tahun;
            cmd.Parameters.Add("?BULAN", MySqlDbType.Int16, 2).Value = pembayaran.Bulan;
            cmd.Parameters.Add("?BIAYA", MySqlDbType.Double, 255).Value = pembayaran.Biaya;
            cmd.Parameters.Add("?TANGGAL_BAYAR", MySqlDbType.VarChar, 255).Value = pembayaran.TanggalBayar;
            cmd.Parameters.Add("?KET", MySqlDbType.VarChar, 255).Value = pembayaran.Keterangan;

            MySqlDataReader result = cmd.ExecuteReader();

            result.Close();
            conn.close();
        }
    }

    public class LoginDao : Dao
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

        private LoginDao() : base() { }

        public override IList<Data> getAll()
        {
            MySqlDataReader result = null;

            try
            {
                IList<Data> list = null;

                MySqlCommand cmd = new MySqlCommand(Login.SELECT_ALL, conn.Connection);
                result = cmd.ExecuteReader();

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
                else
                {
                    throw new DataNotFoundException("Tidak ada data pada tabel Login");
                }

                return list;
            }
            catch (DataNotFoundException E)
            {
                throw E;
            }
            finally
            {
                result.Close();
                conn.close();
            }
        }

        public Login get(String username)
        {
            MySqlDataReader result = null;

            try
            {
                Login login = null;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = Login.SELECT;

                cmd.Parameters.Add("?USERNAME", MySqlDbType.VarChar, 255).Value = username;

                result = cmd.ExecuteReader();

                if (result.HasRows)
                {
                    login = new Login();

                    result.Read();

                    login.Username = username;
                    login.Password = result.GetString("PASSWORD");
                    login.Nama = result.GetString("NAMA");
                    login.Role = result.GetString("ROLE");
                }

                return login;
            }
            catch (DataNotFoundException E)
            {
                throw E;
            }
            finally
            {
                result.Close();
                conn.close();
            }
        }

        public override void delete(Data data)
        {
            Login login = (Login)data;

            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = Login.DELETE;
            cmd.Parameters.Add("?USERNAME", MySqlDbType.VarChar, 255).Value = login.Username;

            MySqlDataReader result = cmd.ExecuteReader();

            result.Close();
            conn.close();
        }

        public override void add(Data data)
        {
            Login login = (Login)data;

            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = Login.INSERT;
            cmd.Parameters.Add("?USERNAME", MySqlDbType.VarChar, 255).Value = login.Username;
            cmd.Parameters.Add("?PASSWORD", MySqlDbType.VarChar, 255).Value = login.Password;
            cmd.Parameters.Add("?NAMA", MySqlDbType.VarChar, 255).Value = login.Nama;
            cmd.Parameters.Add("?ROLE", MySqlDbType.VarChar, 255).Value = login.Role;

            MySqlDataReader result = cmd.ExecuteReader();

            result.Close();
            conn.close();
        }

        public override void update(Data data)
        {
            Login login = (Login)data;

            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = Login.UPDATE;
            cmd.Parameters.Add("?USERNAME", MySqlDbType.VarChar, 255).Value = login.Username;
            cmd.Parameters.Add("?PASSWORD", MySqlDbType.VarChar, 255).Value = login.Password;
            cmd.Parameters.Add("?NAMA", MySqlDbType.VarChar, 255).Value = login.Nama;
            cmd.Parameters.Add("?ROLE", MySqlDbType.VarChar, 255).Value = login.Role;

            MySqlDataReader result = cmd.ExecuteReader();

            result.Close();
            conn.close();
        }
    }

    public class DetailDao : Dao
    {
        private static DetailDao instance;

        private DetailDao() : base() { }
        
        public static DetailDao Instance
        {
            get
            {
                if (instance == null)
                {
                    instance = new DetailDao();
                }
                return instance;
            }
        }

        public override IList<Data> getAll()
        {
            MySqlDataReader result = null;

            try
            {
                IList<Data> list = null;

                MySqlCommand cmd = new MySqlCommand(Details.SELECT_ALL, conn.Connection);
                result = cmd.ExecuteReader();

                if (result.HasRows)
                {
                    list = new List<Data>();

                    while (result.Read())
                    {
                        Details details = new Details();

                        details.Pelanggan = (Pelanggan)PelangganDao.Instance.getByKode(result.GetInt32("KODE_PELANGGAN"));
                        details.Pendaftaran = result.GetDouble("PENDAFTARAN");
                        details.Iuran = result.GetDouble("IURAN");

                        list.Add(details);
                    }
                }
                else
                {
                    throw new DataNotFoundException("Tidak ada data yang ditemukan pada tabel Detail");
                }

                return list;
            }
            catch (DataNotFoundException E)
            {
                throw E;
            }
            finally
            {
                result.Close();
                conn.close();
            }
        }

        public Details get(Pelanggan Pelanggan)
        {
            MySqlDataReader result = null;

            try
            {
                Details details = null;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = Details.SELECT;
                cmd.Parameters.Add("?KODE_PELANGGAN", MySqlDbType.Int32, 11).Value = Pelanggan.Kode;

                result = cmd.ExecuteReader();

                if (result.HasRows)
                {
                    details = new Details();

                    result.Read();

                    details.Pelanggan = Pelanggan;
                    details.Pendaftaran = result.GetDouble("PENDAFTARAN");
                    details.Iuran = result.GetDouble("IURAN");
                }
                else
                {
                    throw new DataNotFoundException("Tidak ada data yang ditemukan pada tabel Detail untuk pelanggan '" + Pelanggan.Nama + "'");
                }

                return details;
            }
            catch (DataNotFoundException E)
            {
                throw E;
            }
            finally
            {
                result.Close();
                conn.close();
            }
        }

        public override void add(Data data)
        {
            Details details = (Details)data;

            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = Details.INSERT;
            cmd.Parameters.Add("?PENDAFTARAN", MySqlDbType.Double, 255).Value = details.Pendaftaran;
            cmd.Parameters.Add("?IURAN", MySqlDbType.Double, 255).Value = details.Iuran;
            cmd.Parameters.Add("?KODE_PELANGGAN", MySqlDbType.Int32, 11).Value = details.Pelanggan.Kode;

            MySqlDataReader result = cmd.ExecuteReader();

            result.Close();
            conn.close();
        }

        public override void update(Data data)
        {
            Details details = (Details)data;

            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = Details.UPDATE;
            cmd.Parameters.Add("?PENDAFTARAN", MySqlDbType.Double, 255).Value = details.Pendaftaran;
            cmd.Parameters.Add("?IURAN", MySqlDbType.Double, 255).Value = details.Iuran;
            cmd.Parameters.Add("?KODE_PELANGGAN", MySqlDbType.Int32, 11).Value = details.Pelanggan.Kode;

            MySqlDataReader result = cmd.ExecuteReader();

            result.Close();
            conn.close();
        }

        public override void delete(Data data)
        {
            Details details = (Details)data;

            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = Details.DELETE;
            cmd.Parameters.Add("?KODE_PELANGGAN", MySqlDbType.Int32, 11).Value = details.Pelanggan.Kode;

            MySqlDataReader result = cmd.ExecuteReader();

            result.Close();
            conn.close();
        }
    }

    public class TunggakanDao : Dao
    {
        private static TunggakanDao instance;

        private TunggakanDao() : base() { }

        public static TunggakanDao Instance
        {
            get
            {
                if (instance == null)
                {
                    instance = new TunggakanDao();
                }
                return instance;
            }
        }

        public override IList<Data> getAll()
        {
            MySqlDataReader result = null;

            try
            {
                IList<Data> list = null;

                MySqlCommand cmd = new MySqlCommand(Tunggakan.SELECT_ALL, conn.Connection);
                result = cmd.ExecuteReader();

                if (result.HasRows)
                {
                    list = new List<Data>();

                    while (result.Read())
                    {
                        Tunggakan tunggakan = new Tunggakan();

                        tunggakan.Pelanggan = (Pelanggan)PelangganDao.Instance.getByKode(result.GetInt32("KODE_PELANGGAN"));
                        tunggakan.JumlahBulan = result.GetInt32("JUMLAH_BULAN");

                        list.Add(tunggakan);
                    }
                }
                else
                {
                    throw new DataNotFoundException("Tidak ada data yang ditemukan pada tabel Tunggakan");
                }

                return list;
            }
            catch (DataNotFoundException E)
            {
                throw E;
            }
            finally
            {
                result.Close();
                conn.close();
            }
        }

        public Tunggakan get(Pelanggan Pelanggan)
        {
            MySqlDataReader result = null;

            try
            {
                Tunggakan Tunggakan = null;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = Tunggakan.SELECT;
                cmd.Parameters.Add("?KODE_PELANGGAN", MySqlDbType.Int32, 11).Value = Pelanggan.Kode;

                result = cmd.ExecuteReader();

                if (result.HasRows)
                {
                    Tunggakan = new Tunggakan();

                    result.Read();

                    Tunggakan.Pelanggan = Pelanggan;
                    Tunggakan.JumlahBulan = result.GetInt32("JUMLAH_BULAN");
                }
                else
                {
                    throw new DataNotFoundException("Tidak ada data yang ditemukan pada tabel Tunggakan untuk pelanggan '" + Pelanggan.Nama + "'");
                }

                return Tunggakan;
            }
            catch (DataNotFoundException E)
            {
                throw E;
            }
            finally
            {
                result.Close();
                conn.close();
            }
        }

        public IList<Data> getByJumlah()
        {
            MySqlDataReader result = null;

            try
            {
                IList<Data> list = null;
                MySqlCommand cmd = conn.Connection.CreateCommand();

                cmd.CommandText = Tunggakan.SORTBYJUMLAH;

                result = cmd.ExecuteReader();

                if (result.HasRows)
                {
                    list = new List<Data>();

                    while (result.Read())
                    {
                        Tunggakan tunggakan = new Tunggakan();

                        tunggakan.Pelanggan = (Pelanggan)PelangganDao.Instance.getByKode(result.GetInt32("KODE_PELANGGAN"));
                        tunggakan.JumlahBulan = result.GetInt32("JUMLAH_BULAN");

                        list.Add(tunggakan);
                    }
                }
                else
                {
                    throw new DataNotFoundException("Tidak ada data yang ditemukan pada tabel Tunggakan");
                }

                return list;
            }
            catch (DataNotFoundException E)
            {
                throw E;
            }
            finally
            {
                result.Close();
                conn.close();
            }
        }

        public override void delete(Data data)
        {
            Tunggakan Tunggakan = (Tunggakan)data;

            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = Tunggakan.DELETE;
            cmd.Parameters.Add("?KODE_PELANGGAN", MySqlDbType.Int32, 11).Value = Tunggakan.Pelanggan.Kode;

            MySqlDataReader result = cmd.ExecuteReader();

            result.Close();
            conn.close();
        }

        public override void add(Data data)
        {
            Tunggakan Tunggakan = (Tunggakan)data;

            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = Tunggakan.INSERT;
            cmd.Parameters.Add("?JUMLAH_BULAN", MySqlDbType.Int32, 11).Value = Tunggakan.JumlahBulan;
            cmd.Parameters.Add("?KODE_PELANGGAN", MySqlDbType.Int32, 11).Value = Tunggakan.Pelanggan.Kode;

            MySqlDataReader result = cmd.ExecuteReader();

            result.Close();
            conn.close();
        }

        public override void update(Data data)
        {
            Tunggakan Tunggakan = (Tunggakan)data;

            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = Tunggakan.UPDATE;
            cmd.Parameters.Add("?JUMLAH_BULAN", MySqlDbType.Int32, 11).Value = Tunggakan.JumlahBulan;
            cmd.Parameters.Add("?KODE_PELANGGAN", MySqlDbType.Int32, 11).Value = Tunggakan.Pelanggan.Kode;

            MySqlDataReader result = cmd.ExecuteReader();

            result.Close();
            conn.close();
        }
    }

    public class ListDao
    {
        public static PelangganDao pelangganDao = PelangganDao.Instance;
        public static KontakDao kontakDao = KontakDao.Instance;
        public static TipeKontakDao tipeKontakDao = TipeKontakDao.Instance;
        public static PembayaranDao pembayaranDao = PembayaranDao.Instance;
        public static LoginDao loginDao = LoginDao.Instance;
        public static DetailDao detailDao = DetailDao.Instance;
        public static TunggakanDao tunggakanDao = TunggakanDao.Instance;
    }
}

