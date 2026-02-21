using com.gtd.tvkabel.data.dao;
using com.gtd.tvkabel.data.entity;
using com.gtd.tvkabel.data.util;
using com.gtd.tvkabel.data.util.exception;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace com.gtd.tvkabel.data.service
{
    public class SuperService
    {
        protected Dao Dao;

        protected SuperService(Dao dao)
        {
            this.Dao = dao;
        }

        public void Add(Data arg0)
        {
            try
            {
                Dao.add(arg0);
            }
            catch (DataException E)
            {
                throw E;
            }
        }

        public void Update(Data arg0)
        {
            try
            {
                Dao.update(arg0);
            }
            catch (DataException E)
            {
                throw E;
            }
        }

        public void Delete(Data arg0)
        {
            try
            {
                Dao.delete(arg0);
            }
            catch (DataException E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);
            }
        }
    }

    public class PelangganService : SuperService
    {
        private new PelangganDao Dao;

        public PelangganService() : base(ListDao.pelangganDao)
        {
            Dao = PelangganDao.Instance;
        }

        public Pelanggan Get(String id)
        {
            return Dao.get(id);
        }

        public Pelanggan GetByKode(Int32 kode)
        {
            return Dao.getByKode(kode);
        }

        public IList<Pelanggan> getByNama(String nama)
        {
            return Dao.getByNama(nama);
        }

        public IList<Pelanggan> GetByStatus(short status)
        {
            return Dao.getByStatus(status);
        }

        public IList<Pelanggan> GetAll()
        {
            IList<Pelanggan> list = new List<Pelanggan>();

            foreach (Pelanggan p in Dao.getAll())
            {
                list.Add(p);
            }

            return list;
        }

        public Pelanggan Add(Pelanggan pelanggan)
        {
            Dao.add(pelanggan);

            return Dao.get(pelanggan.Id);
        }

        public void DeleteByKode(Pelanggan pelanggan)
        {
            Dao.deleteByKode(pelanggan);
        }
    }

    public class DetailService : SuperService
    {
        private new DetailDao Dao;

        public DetailService() : base(ListDao.detailDao)
        {
            Dao = DetailDao.Instance;
        }

        public Details Get(Pelanggan Pelanggan)
        {
            try
            {
                return (Details)Dao.get(Pelanggan);
            }
            catch (DataNotFoundException E)
            {
                throw new DataNotFoundException("Periksa kembali Detail Pembayaran untuk Pelanggan '" + Pelanggan.Nama + "'");
            }
        }

        public IList<Details> GetAll()
        {
            try
            {
                IList<Details> list = new List<Details>();

                foreach (Details d in Dao.getAll())
                {
                    list.Add(d);
                }

                return list;
            }
            catch (DataException E)
            {
                throw E;
            }
        }

        public void Delete(Pelanggan arg0)
        {
            try
            {
                Dao.delete(Get(arg0));
            }
            catch (DataException E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);
            }
            catch (NullReferenceException E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);
            }
        }

        public void Update(Details arg0)
        {
            try
            {
                Details details = Get(arg0.Pelanggan);

                Dao.update(arg0);
            }
            catch (DataNotFoundException E)
            {
                Add(arg0);
            }
        }
    }

    public class KontakService : SuperService
    {
        private new KontakDao Dao;

        public KontakService() : base(ListDao.kontakDao)
        {
            Dao = KontakDao.Instance;
        }

        public Kontak Get(Pelanggan Pelanggan, TipeKontak TipeKontak)
        {
            try
            {
                return (Kontak)Dao.get(Pelanggan, TipeKontak);
            }
            catch (DataException E)
            {
                throw E;
            }
        }

        public IList<Kontak> GetByPelanggan(Pelanggan pelanggan)
        {
            IList<Kontak> list = new List<Kontak>();

            try
            {
                foreach (Kontak k in Dao.getByPelanggan(pelanggan))
                {
                    list.Add(k);
                }
            }
            catch (DataException E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);
            }

            return list;
        }

        public IList<Kontak> GetAll()
        {
            try
            {
                IList<Kontak> list = new List<Kontak>();

                foreach (Kontak k in Dao.getAll())
                {
                    list.Add(k);
                }

                return list;
            }
            catch (DataException E)
            {
                throw E;
            }
        }

        public void DeleteByPelanggan(Pelanggan pelanggan)
        {
            try
            {
                Dao.deleteByPelanggan(pelanggan);
            }
            catch (DataException E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);
            }
            catch (NullReferenceException E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);
            }
        }

        public void DeleteByTipe(TipeKontak tipe)
        {
            try
            {
                Dao.deleteByTipe(tipe);
            }
            catch (DataException E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);
            }
            catch (NullReferenceException E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);
            }
        }
    }

    public class TipeKontakService : SuperService
    {
        private new TipeKontakDao Dao;

        public TipeKontakService() : base(ListDao.tipeKontakDao)
        {
            Dao = TipeKontakDao.Instance;
        }

        public TipeKontak Get(String kode)
        {
            try
            {
                return (TipeKontak)Dao.get(kode);
            }
            catch (DataException E)
            {
                throw E;
            }
        }

        public IList<TipeKontak> GetAll()
        {
            try
            {
                IList<TipeKontak> list = new List<TipeKontak>();

                foreach (TipeKontak k in Dao.getAll())
                {
                    list.Add(k);
                }

                return list;
            }
            catch (DataException E)
            {
                throw E;
            }
        }

    }

    public class PembayaranService : SuperService
    {
        private new PembayaranDao Dao;

        public PembayaranService() : base(ListDao.pembayaranDao)
        {
            Dao = PembayaranDao.Instance;
        }

        public Pembayaran Get(Pelanggan Pelanggan, int Bulan, Int16 Tahun)
        {
            try
            {
                return (Pembayaran)Dao.get(Pelanggan, Tahun, Bulan);
            }
            catch (DataNotFoundException E)
            {
                Console.WriteLine("INFO:");
                Console.WriteLine(E.Message);
                Console.WriteLine(E.StackTrace);

                return new Pembayaran();
            }
        }

        public IList<Pembayaran> GetByPelanggan(Pelanggan pelanggan)
        {
            try
            {
                return Dao.getByPelanggan(pelanggan);
            }
            catch (DataNotFoundException E)
            {
                Console.WriteLine("INFO:");
                Console.WriteLine(E.Message);
                Console.WriteLine(E.StackTrace);

                return new List<Pembayaran>();
            }
        }

        public IList<Pembayaran> GetByTanggal(int tahun, int bulan)
        {
            return Dao.getByTanggal(tahun, bulan);
        }

        public IList<Pembayaran> GetByNama(String Nama)
        {
            return Dao.getByNama(Nama);
        }

        public IList<Pembayaran> GetAll()
        {
            IList<Pembayaran> list = new List<Pembayaran>();

            try
            {
                foreach (Pembayaran p in Dao.getAll())
                {
                    list.Add(p);
                }
            }
            catch (DataException E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);
            }

            return list;
        }

        public void DeleteByPelanggan(Pelanggan pelanggan)
        {
            try
            {
                Dao.deleteByPelanggan(pelanggan);
            }
            catch (DataException E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);
            }
            catch (NullReferenceException E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);
            }
        }

    }

    public class LoginService : SuperService
    {
        private new LoginDao Dao;

        public LoginService() : base(ListDao.loginDao)
        {
            Dao = LoginDao.Instance;
        }

        public Login Get(String username)
        {
            try
            {
                return (Login)Dao.get(username);
            }
            catch (DataException E)
            {
                throw E;
            }
        }

        public IList<Login> GetAll()
        {
            try
            {
                IList<Login> list = new List<Login>();

                foreach (Login l in Dao.getAll())
                {
                    list.Add(l);
                }

                return list;
            }
            catch (DataException E)
            {
                throw E;
            }
        }

    }

    public class TunggakanService : SuperService
    {
        private new TunggakanDao Dao;

        public TunggakanService() : base(ListDao.tunggakanDao)
        {
            Dao = TunggakanDao.Instance;
        }

        public Tunggakan Get(Pelanggan Pelanggan)
        {
            try
            {
                return (Tunggakan)Dao.get(Pelanggan);
            }
            catch (DataException E)
            {
                throw E;
            }
        }

        public IList<Tunggakan> GetByJumlah()
        {
            IList<Tunggakan> list = new List<Tunggakan>();

            try
            {
                foreach (Tunggakan t in Dao.getByJumlah())
                {
                    list.Add(t);
                }
            }
            catch (DataException E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);
            }

            return list;
        }

        public IList<Tunggakan> GetAll()
        {
            IList<Tunggakan> list = new List<Tunggakan>();

            try
            {
                foreach (Tunggakan t in Dao.getAll())
                {
                    list.Add(t);
                }
            }
            catch (DataException E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);
            }

            return list;
        }

        public void Add(Pelanggan arg0)
        {
            Tunggakan tunggakan = new Tunggakan();
            tunggakan.Pelanggan = arg0;
            tunggakan.JumlahBulan = HitungTunggakan(arg0);

            try
            {
                Dao.add(tunggakan);
            }
            catch (DataException E)
            {
                throw E;
            }
        }

        public void Update(Pelanggan arg0)
        {
            try
            {
                Tunggakan tunggakan = Get(arg0);
                tunggakan.JumlahBulan = HitungTunggakan(arg0);

                Dao.update(tunggakan);
            }
            catch (DataNotFoundException E)
            {
                Add(arg0);
            }
            catch (DataException E)
            {
                throw E;
            }
        }

        public void Delete(Pelanggan arg0)
        {
            try
            {
                Dao.delete(Get(arg0));
            }
            catch (DataException E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);
            }
            catch (NullReferenceException E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);
            }
        }

        private int HitungTunggakan(Pelanggan arg0)
        {
            PembayaranService pembayaranService = new PembayaranService();
            IList<Pembayaran> daftarPembayaran = pembayaranService.GetByPelanggan(arg0);

            int yearLast, monthLast;
            if (daftarPembayaran != null && daftarPembayaran.Count > 0)
            {
                Pembayaran pembayaran = daftarPembayaran.Last();
                yearLast = pembayaran.Tahun;
                monthLast = pembayaran.Bulan;

            }
            else
            {
                DateTime dt = DateAndTime.stringToDateTime("01/01/2012");
                yearLast = Int16.Parse(DateAndTime.getYear(dt));
                monthLast = Int16.Parse(DateAndTime.getMonth(dt));
            }

            DateTime now = DateTime.Now;
            int yearNow = Int16.Parse(DateAndTime.getYear(now));
            int monthNow = Int16.Parse(DateAndTime.getMonth(now));

            return (monthNow - monthLast) + ((yearNow - yearLast) * 12);
        }
    }
}
