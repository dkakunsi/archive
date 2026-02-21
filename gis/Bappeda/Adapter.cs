using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using System.ComponentModel;
using MySql.Data.MySqlClient;
using dkakunsi.bappeda.entity;
using dkakunsi.bappeda.accessor;
using dkakunsi.bappeda.util;

namespace dkakunsi.bappeda.adapter
{

    public abstract class Adapter
    {
        protected String INSERT;
        protected String UPDATE;
        protected String DELETE;
        protected String SELECT;
        protected String SELECT_ALL;
        protected ConnectionManager conn;

        public Adapter()
        {
            conn = new ConnectionManager();
        }

        public abstract IDataModel getEmpty();
        public abstract IDataModel getData(String param);
        public abstract ArrayList getAll();
        public abstract void tambah(IDataModel model);
        public abstract void ubah(IDataModel model);
        public abstract void hapus(IDataModel model);
    }

    class AsetAdapter : Adapter
    {

        public AsetAdapter()
        {
            INSERT = "insert into ASET_JALAN values(?NAMA_JALAN, ?TITIK_AWAL, ?TITIK_AKHIR, ?PANJANG_JALAN, ?LEBAR_JALAN, ?PETA)";
            UPDATE = "update ASET_JALAN set TITIK_AWAL=?TITIK_AWAL, TITIK_AKHIR=?TITIK_AKHIR, PANJANG_JALAN=?PANJANG_JALAN, LEBAR_JALAN=?LEBAR_JALAN, PETA=?PETA where NAMA_JALAN=?NAMA_JALAN";
            DELETE = "delete from ASET_JALAN where NAMA_JALAN=?NAMA_JALAN";
            SELECT = "select * from ASET_JALAN where NAMA_JALAN=?NAMA_JALAN";
            SELECT_ALL = "select * from ASET_JALAN order by NAMA_JALAN asc";
        }

        public override IDataModel getEmpty()
        {
            return new AsetJalanModel();
        }

        public override IDataModel getData(String param)
        {
            IAsetJalanModel aset = null;

            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = SELECT;
            cmd.Parameters.Add("?NAMA_JALAN", MySqlDbType.VarChar, 255).Value = param;

            MySqlDataReader result = cmd.ExecuteReader();

            if (result.HasRows)
            {
                aset = new AsetJalanModel();

                result.Read();

                aset.NamaJalan = param;
                aset.TitikAwal = result.GetString("TITIK_AWAL");
                aset.TitikAkhir = result.GetString("TITIK_AKHIR");
                aset.PanjangJalan = result.GetInt32("PANJANG_JALAN");
                aset.LebarJalan = result.GetInt32("LEBAR_JALAN");
                aset.Peta = (IPetaModel)DataManager.getData("PETA", result.GetString("PETA"));
            }
            else
            {
                throw new BappedaException("Data dengan ID '" + param + "' tidak ditemukan");
            }

            result.Close();
            conn.close();

            return aset;
        }

        public override ArrayList getAll()
        {
            ArrayList list = null;

            MySqlCommand cmd = new MySqlCommand(SELECT_ALL, conn.Connection);
            MySqlDataReader result = cmd.ExecuteReader();

            if (result.HasRows)
            {
                list = new ArrayList();

                while (result.Read())
                {
                    IAsetJalanModel asetJalan = new AsetJalanModel();

                    asetJalan.NamaJalan = result.GetString("NAMA_JALAN");
                    asetJalan.TitikAwal = result.GetString("TITIK_AWAL");
                    asetJalan.TitikAkhir = result.GetString("TITIK_AKHIR");
                    asetJalan.PanjangJalan = result.GetInt32("PANJANG_JALAN");
                    asetJalan.LebarJalan = result.GetInt32("LEBAR_JALAN");
                    asetJalan.Peta = (IPetaModel)DataManager.getData("PETA", result.GetString("PETA"));

                    list.Add(asetJalan);
                }
            }
            else
            {
                throw new BappedaException("Tidak ada data yang ditemukan pada tabel Aset Jalan");
            }


            result.Close();
            conn.close();

            return list;
        }

        public override void tambah(IDataModel model)
        {
            try
            {
                IAsetJalanModel asetJalan = (IAsetJalanModel)model;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = INSERT;
                cmd.Parameters.Add("?NAMA_JALAN", MySqlDbType.VarChar, 255).Value = asetJalan.NamaJalan;
                cmd.Parameters.Add("?TITIK_AWAL", MySqlDbType.VarChar, 255).Value = asetJalan.TitikAwal;
                cmd.Parameters.Add("?TITIK_AKHIR", MySqlDbType.VarChar, 255).Value = asetJalan.TitikAkhir;
                cmd.Parameters.Add("?PANJANG_JALAN", MySqlDbType.Int32, 255).Value = asetJalan.PanjangJalan;
                cmd.Parameters.Add("?LEBAR_JALAN", MySqlDbType.Int32, 255).Value = asetJalan.LebarJalan;
                cmd.Parameters.Add("?PETA", MySqlDbType.VarChar, 255).Value = asetJalan.Peta.NamaPeta;

                MySqlDataReader result = cmd.ExecuteReader();

                result.Close();
                conn.close();
            }
            catch (SystemException exc)
            {
                throw new BappedaException(exc.Message);
            }
        }

        public override void ubah(IDataModel model)
        {
            try
            {
                IAsetJalanModel asetJalan = (IAsetJalanModel)model;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = UPDATE;
                cmd.Parameters.Add("?NAMA_JALAN", MySqlDbType.VarChar, 255).Value = asetJalan.NamaJalan;
                cmd.Parameters.Add("?TITIK_AWAL", MySqlDbType.VarChar, 255).Value = asetJalan.TitikAwal;
                cmd.Parameters.Add("?TITIK_AKHIR", MySqlDbType.VarChar, 255).Value = asetJalan.TitikAkhir;
                cmd.Parameters.Add("?PANJANG_JALAN", MySqlDbType.Int32, 255).Value = asetJalan.PanjangJalan;
                cmd.Parameters.Add("?LEBAR_JALAN", MySqlDbType.Int32, 255).Value = asetJalan.LebarJalan;
                cmd.Parameters.Add("?PETA", MySqlDbType.VarChar, 255).Value = asetJalan.Peta.NamaPeta;

                MySqlDataReader result = cmd.ExecuteReader();

                result.Close();
                conn.close();
            }
            catch (SystemException exc)
            {
                throw new BappedaException(exc.Message);
            }
        }

        public override void hapus(IDataModel model)
        {
            try
            {
                IAsetJalanModel asetJalan = (IAsetJalanModel)model;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = DELETE;
                cmd.Parameters.Add("?NAMA_JALAN", MySqlDbType.VarChar, 255).Value = asetJalan.NamaJalan;

                MySqlDataReader result = cmd.ExecuteReader();

                result.Close();
                conn.close();
            }
            catch (SystemException exc)
            {
                throw new BappedaException(exc.Message);
            }
        }

    }

    class PetaAdapter : Adapter
    {

        public PetaAdapter()
        {
            INSERT = "insert into PETA values(?NAMA_PETA, ?PATH)";
            UPDATE = "update PETA set PATH=?PATH where NAMA_PETA=?NAMA_PETA";
            DELETE = "delete from PETA where NAMA_PETA=?NAMA_PETA";
            SELECT = "select PATH from PETA where NAMA_PETA=?NAMA_PETA";
            SELECT_ALL = "select * from PETA order by NAMA_PETA asc";
        }

        public override IDataModel getEmpty()
        {
            return new PetaModel();
        }

        public override IDataModel getData(String param)
        {
            IPetaModel peta = null;

            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = SELECT;
            cmd.Parameters.Add("?NAMA_PETA", MySqlDbType.VarChar, 255).Value = param;

            MySqlDataReader result = cmd.ExecuteReader();

            if (result.HasRows)
            {
                peta = new PetaModel();

                result.Read();

                peta.NamaPeta = param;
                peta.Path = result.GetString("PATH");
                peta.Server = conn.Server;
            }
            else
            {
                throw new BappedaException("Data dengan ID '" + param + "' tidak ditemukan");
            }

            result.Close();
            conn.close();

            return peta;
        }

        public override ArrayList getAll()
        {
            ArrayList list = null;

            MySqlCommand cmd = new MySqlCommand(SELECT_ALL, conn.Connection);
            MySqlDataReader result = cmd.ExecuteReader();

            if (result.HasRows)
            {
                list = new ArrayList();

                while (result.Read())
                {
                    IPetaModel peta = new PetaModel();
                    peta.NamaPeta = result.GetString("NAMA_PETA");
                    peta.Path = result.GetString("PATH");

                    list.Add(peta);
                }

            }
            else
            {
                throw new BappedaException("Tidak ada data di dalam tabel Peta");
            }

            result.Close();
            conn.close();

            return list;
        }

        public override void tambah(IDataModel model)
        {
            try
            {
                IPetaModel peta = (IPetaModel)model;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = INSERT;
                cmd.Parameters.Add("?NAMA_PETA", MySqlDbType.VarChar, 255).Value = peta.NamaPeta;
                cmd.Parameters.Add("?PATH", MySqlDbType.VarChar, 255).Value = peta.Path;

                MySqlDataReader result = cmd.ExecuteReader();

                result.Close();
                conn.close();
            }
            catch (SystemException exc)
            {
                throw new BappedaException(exc.Message);
            }
        }

        public override void ubah(IDataModel model)
        {
            try
            {
                IPetaModel peta = (IPetaModel)model;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = UPDATE;
                cmd.Parameters.Add("?NAMA_PETA", MySqlDbType.VarChar, 255).Value = peta.NamaPeta;
                cmd.Parameters.Add("?PATH", MySqlDbType.VarChar, 255).Value = peta.Path;

                MySqlDataReader result = cmd.ExecuteReader();

                result.Close();
                conn.close();
            }
            catch (SystemException exc)
            {
                throw new BappedaException(exc.Message);
            }
        }

        public override void hapus(IDataModel model)
        {
            try
            {
                IPetaModel peta = (IPetaModel)model;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = DELETE;
                cmd.Parameters.Add("?NAMA_PETA", MySqlDbType.VarChar, 255).Value = peta.NamaPeta;

                MySqlDataReader result = cmd.ExecuteReader();

                result.Close();
                conn.close();
            }
            catch (SystemException exc)
            {
                throw new BappedaException(exc.Message);
            }
        }

    }

    class LoginAdapter : Adapter
    {

        public LoginAdapter()
        {
            INSERT = "insert into LOGIN values(?USERNAME, ?PASSWORD)";
            UPDATE = "update LOGIN set PASSWORD=?PASSWORD where USERNAME=?USERNAME";
            DELETE = "delete from LOGIN where USERNAME=?USERNAME";
            SELECT = "select PASSWORD from LOGIN where USERNAME=?USERNAME";
            SELECT_ALL = "select * from LOGIN order by USERNAME asc";
        }

        public override IDataModel getEmpty()
        {
            return new LoginModel();
        }

        public override IDataModel getData(String param)
        {
            ILoginModel login = null;

            MySqlCommand cmd = conn.Connection.CreateCommand();
            cmd.CommandText = SELECT;
            cmd.Parameters.Add("?USERNAME", MySqlDbType.VarChar, 255).Value = param;

            MySqlDataReader result = cmd.ExecuteReader();

            if (result.HasRows)
            {
                login = new LoginModel();

                result.Read();

                login.Username = param;
                login.Password = result.GetString("PASSWORD");
            }
            else
            {
                throw new BappedaException("Data dengan ID '" + param + "' tidak ditemukan");
            }

            result.Close();
            conn.close();

            return login;
        }

        public override ArrayList getAll()
        {
            ArrayList list = null;

            MySqlCommand cmd = new MySqlCommand(SELECT_ALL, conn.Connection);
            MySqlDataReader result = cmd.ExecuteReader();

            if (result.HasRows)
            {
                list = new ArrayList();

                while (result.Read())
                {
                    ILoginModel login = new LoginModel();
                    login.Username = result.GetString("USERNAME");
                    login.Password = result.GetString("PASSWORD");

                    list.Add(login);
                }
            }
            else
            {
                throw new BappedaException("Tidak ada data di dalam tabel Login");
            }

            result.Close();
            conn.close();

            return list;
        }

        public override void tambah(IDataModel model)
        {
            try
            {
                ILoginModel login = (ILoginModel)model;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = INSERT;
                cmd.Parameters.Add("?USERNAME", MySqlDbType.VarChar, 255).Value = login.Username;
                cmd.Parameters.Add("?PASSWORD", MySqlDbType.VarChar, 255).Value = login.Password;

                MySqlDataReader result = cmd.ExecuteReader();

                result.Close();
                conn.close();
            }
            catch (SystemException exc)
            {
                throw new BappedaException(exc.Message);
            }
        }

        public override void ubah(IDataModel model)
        {
            try
            {
                ILoginModel login = (ILoginModel)model;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = UPDATE;
                cmd.Parameters.Add("?USERNAME", MySqlDbType.VarChar, 255).Value = login.Username;
                cmd.Parameters.Add("?PASSWORD", MySqlDbType.VarChar, 255).Value = login.Password;

                MySqlDataReader result = cmd.ExecuteReader();

                result.Close();
                conn.close();
            }
            catch (SystemException exc)
            {
                throw new BappedaException(exc.Message);
            }
        }

        public override void hapus(IDataModel model)
        {
            try
            {
                ILoginModel login = (ILoginModel)model;

                MySqlCommand cmd = conn.Connection.CreateCommand();
                cmd.CommandText = DELETE;
                cmd.Parameters.Add("?USERNAME", MySqlDbType.VarChar, 255).Value = login.Username;

                MySqlDataReader result = cmd.ExecuteReader();

                result.Close();
                conn.close();
            }
            catch (SystemException exc)
            {
                throw new BappedaException(exc.Message);
            }
        }

    }

}
