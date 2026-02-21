using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using com.gtd.tvkabel.data.util;
using com.gtd.tvkabel.data.dao;
using com.gtd.tvkabel.data.util.exception;
using com.gtd.tvkabel.data.entity;

namespace com.gtd.tvkabel.data
{
    public abstract class Data
    {
        protected ServiceDao dao;

        public Data() { }

    }

    public abstract class Type : Data
    {
        public String Kode { get; set; }
        public String Detail { get; set; }

        public override bool Equals(object obj)
        {
            Type type = (Type)obj;

            if (this.Kode.Equals(type.Kode))
            {
                return true;
            }
            return false;
        }

        public override int GetHashCode()
        {
            return base.GetHashCode();
        }
    }

    public interface IKeterangan
    {
        String Keterangan { get; set; }
    }

    public abstract class Dao
    {
        public abstract void add(Data data);
        public abstract void update(Data data);
        public abstract void delete(Data data);
    }

    public abstract class DataDao : Dao
    {
        protected String INSERT;
        protected String UPDATE;
        protected String DELETE;
        protected String SELECT;
        protected String SELECT_ALL;
        protected String SEARCH;
        protected ConnectionManager conn;

        public abstract IList<Data> getAll();
        public abstract Data get(Object id);
        public abstract IList<Data> search(Object criteria);

        // public abstract override void add(Data data);
        // public abstract override void update(Data data);
        // public abstract override void delete(Data data);
    }

    public abstract class ServiceDao : Dao
    {
        public abstract IList<Data> getAll(EntityList entity);
        public abstract Data get(EntityList entity, Object id);
        public abstract IList<Data> search(EntityList entity, Object criteria);
    }

    public class DataManager : ServiceDao
    {
        private static DataManager instance;

        private DataManager() { }

        public static DataManager Instance
        {
            get
            {
                if (instance == null)
                {
                    instance = new DataManager();
                }
                return instance;
            }
        }

        public override IList<Data> getAll(EntityList entity)
        {
            DataDao dao = null;

            switch(entity) {
                case EntityList.PELANGGAN :
                    dao = PelangganDao.Instance;
                    break;
                case EntityList.DETAIL :
                    dao = DetailsDao.Instance;
                    break;
                case EntityList.PEMBAYARAN :
                    dao = PembayaranDao.Instance;
                    break;
                case EntityList.KONTAK :
                    dao = KontakDao.Instance;
                    break;
                case EntityList.TIPEKONTAK :
                    dao = TipeKontakDao.Instance;
                    break;
                case EntityList.LOGIN :
                    dao = LoginDao.Instance;
                    break;
                default :
                    throw new DataException("entity tidak terdaftar");
            }
    	    return dao.getAll();
        }

        public override Data get(EntityList entity, object id)
        {
            DataDao dao = null;

            switch (entity)
            {
                case EntityList.PELANGGAN:
                    dao = PelangganDao.Instance;
                    break;
                case EntityList.DETAIL:
                    dao = DetailsDao.Instance;
                    break;
                case EntityList.PEMBAYARAN:
                    dao = PembayaranDao.Instance;
                    break;
                case EntityList.KONTAK:
                    dao = KontakDao.Instance;
                    break;
                case EntityList.TIPEKONTAK:
                    dao = TipeKontakDao.Instance;
                    break;
                case EntityList.LOGIN:
                    dao = LoginDao.Instance;
                    break;
                default:
                    throw new DataException("entity tidak terdaftar");
            }

    	    return dao.get(id);
        }

        public override void add(Data data)
        {
            try
            {
                DataDao dao = null;

                if (data is Pelanggan)
                {
                    dao = PelangganDao.Instance;
                }
                else if (data is Details)
                {
                    dao = DetailsDao.Instance;
                }
                else if (data is Pembayaran)
                {
                    dao = PembayaranDao.Instance;
                }
                else if (data is Kontak)
                {
                    dao = KontakDao.Instance;
                }
                else if (data is TipeKontak)
                {
                    dao = TipeKontakDao.Instance;
                }
                else if (data is Login)
                {
                    dao = LoginDao.Instance;
                }
                else
                {
                    throw new DataException("entity tidak terdaftar");
                }

                dao.add(data);
            }
            catch (DataException exc)
            {
                throw exc;
            }
        }

        public override void update(Data data)
        {
            try
            {
                DataDao dao = null;

                if (data is Pelanggan)
                {
                    dao = PelangganDao.Instance;
                }
                else if (data is Details)
                {
                    dao = DetailsDao.Instance;
                }
                else if (data is Pembayaran)
                {
                    dao = PembayaranDao.Instance;
                }
                else if (data is Kontak)
                {
                    dao = KontakDao.Instance;
                }
                else if (data is TipeKontak)
                {
                    dao = TipeKontakDao.Instance;
                }
                else if (data is Login)
                {
                    dao = LoginDao.Instance;
                }
                else
                {
                    throw new DataException("entity tidak terdaftar");
                }

                dao.update(data);
            }
            catch (DataException exc)
            {
                throw exc;
            }
        }

        public override void delete(Data data)
        {
            try
            {
                DataDao dao = null;

                if (data is Pelanggan)
                {
                    dao = PelangganDao.Instance;
                }
                else if (data is Details)
                {
                    dao = DetailsDao.Instance;
                }
                else if (data is Pembayaran)
                {
                    dao = PembayaranDao.Instance;
                }
                else if (data is Kontak)
                {
                    dao = KontakDao.Instance;
                }
                else if (data is TipeKontak)
                {
                    dao = TipeKontakDao.Instance;
                }
                else if (data is Login)
                {
                    dao = LoginDao.Instance;
                }
                else
                {
                    throw new DataException("entity tidak terdaftar");
                }

                dao.delete(data);
            }
            catch (DataException exc)
            {
                throw exc;
            }
        }

        public override IList<Data> search(EntityList entity, object criteria)
        {
            DataDao dao = null;

            switch (entity)
            {
                case EntityList.PELANGGAN:
                    dao = PelangganDao.Instance;
                    break;
                case EntityList.DETAIL:
                    dao = DetailsDao.Instance;
                    break;
                case EntityList.PEMBAYARAN:
                    dao = PembayaranDao.Instance;
                    break;
                case EntityList.KONTAK:
                    dao = KontakDao.Instance;
                    break;
                case EntityList.TIPEKONTAK:
                    dao = TipeKontakDao.Instance;
                    break;
                case EntityList.LOGIN:
                    dao = LoginDao.Instance;
                    break;
                default:
                    throw new DataException("entity tidak terdaftar");
            }

            return dao.search(criteria);
        }
    }
}

