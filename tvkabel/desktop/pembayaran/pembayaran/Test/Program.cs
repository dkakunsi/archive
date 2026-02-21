using com.gtd.tvkabel.data.dao;
using com.gtd.tvkabel.data.entity;
using com.gtd.tvkabel.data.service;
using com.gtd.tvkabel.data.util;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Test
{
    class Program
    {
        static void Main(string[] args)
        {
            ServerSetting.read(ServerSetting.DefaultSetting);
            //LoginTest test = new LoginTest();
            PelangganTest test = new PelangganTest();

            //test.TestGet();
            //test.TestAdd();
            //test.TestUpdate();
            //test.TestDelete();
            test.TestGetAll();
        }
    }

    abstract class Test
    {
        public abstract void TestGet();
        public abstract void TestAdd();
        public abstract void TestUpdate();
        public abstract void TestDelete();
        public abstract void TestGetAll();
    }

    class LoginTest : Test
    {
        private LoginDao dao = LoginDao.Instance;

        public override void TestGet()
        {
            String username = "user_test";
            Login login = dao.get(username);

            Console.WriteLine(login);
        }

        public override void TestAdd()
        {
            Login login = new Login();
            login.Username = "user_test";
            login.Password = "pass_test";
            login.Nama = "Test";
            login.Role = "Admin";

            dao.add(login);
        }

        public override void TestUpdate()
        {
            Login login = new Login();
            login.Username = "user_test";
            login.Password = "pass_test";
            login.Nama = "Test";
            login.Role = "Operator";

            dao.update(login);
        }

        public override void TestDelete()
        {
            Login login = new Login();
            login.Username = "user_test";
            login.Password = "pass_test";
            login.Nama = "Test";
            login.Role = "Operator";

            dao.delete(login);
        }

        public override void TestGetAll()
        {
            IList<Data> listLogin = dao.getAll();
            Console.WriteLine(listLogin);
        }
    }

    class PelangganTest : Test
    {
        private PelangganService service;

        public PelangganTest()
        {
            service = new PelangganService();
        }

        public override void TestGet()
        {
            try
            {
                Pelanggan pelanggan = service.Get("PLG0001");
                Console.WriteLine(pelanggan.Nama);
            }
            catch (Exception E)
            {
                Console.WriteLine(E.Message);
            }
        }

        public override void TestAdd()
        {
            try
            {
                Pelanggan pelanggan = new Pelanggan();
                pelanggan.Id = "test1";
                pelanggan.Nama = "Test Pelanggan";
                pelanggan.Alamat = "Alamat Pelanggan";
                pelanggan.JumlahTv = 2;
                pelanggan.Status = 1;
                pelanggan.TglMulai = "01-01-2013";

                service.Add(pelanggan);
                Console.WriteLine("Test Success");
            }
            catch (Exception E)
            {
                Console.WriteLine(E.Message);
            }
        }

        public override void TestUpdate()
        {
            try
            {
                Pelanggan pelanggan = new Pelanggan();
                pelanggan.Id = "test1";
                pelanggan.Nama = "Test Pelanggan";
                pelanggan.Alamat = "Alamat Pindah Pelanggan";
                pelanggan.JumlahTv = 2;
                pelanggan.Status = 1;
                pelanggan.TglMulai = "01-01-2013";

                service.Update(pelanggan);
                Console.WriteLine("Test Success");
            }
            catch (Exception E)
            {
                Console.WriteLine(E.Message);
            }
        }

        public override void TestDelete()
        {
            try
            {
                Pelanggan pelanggan = new Pelanggan();
                pelanggan.Id = "test1";
                pelanggan.Nama = "Test Pelanggan";
                pelanggan.Alamat = "Alamat Pelanggan";
                pelanggan.JumlahTv = 2;
                pelanggan.Status = 1;
                pelanggan.TglMulai = "01-01-2013";

                service.Delete(pelanggan);
                Console.WriteLine("Test Success");
            }
            catch (Exception E)
            {
                Console.WriteLine(E.Message);
            }
        }

        public override void TestGetAll()
        {
            IList<Pelanggan> list = service.GetAll();
            Console.WriteLine(list);
        }
    }


}
