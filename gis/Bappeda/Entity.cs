using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.Serialization;
using dkakunsi.bappeda.accessor;
using dkakunsi.bappeda.util;

namespace dkakunsi.bappeda.entity
{
    public class AsetJalanModel : IAsetJalanModel
    {
        public String NamaJalan
        {
            get;
            set;
        }

        public String TitikAwal
        {
            get;
            set;
        }

        public String TitikAkhir
        {
            get;
            set;
        }

        public int PanjangJalan
        {
            get;
            set;
        }

        public int LebarJalan
        {
            get;
            set;
        }

        public IPetaModel Peta
        {
            get;
            set;
        }
    }

    public class PetaModel : IPetaModel
    {
        private String server;

        public String NamaPeta
        {
            get;
            set;
        }

        public String Path
        {
            get;
            set;
        }

        public String Server
        {
            get { return server; }
            set { setServer(value); }
        }

        public String getView()
        {
            String pa = Server + Path;

            return pa;
        }

        private void setServer(String param)
        {
            if (param.Equals("localhost") || param.Equals("127.0.0.1"))
            {
                server = @"D:\";
            }
            else
            {
                server = @"\\" + param + @"\";
            }
        }
    }

    public class LoginModel : ILoginModel
    {
        public String Username
        {
            get;
            set;
        }

        public String Password
        {
            get;
            set;
        }

        public bool doOtentikasi(String password)
        {
            if (Password.Equals(password))
            {
                return true;
            }
            else
            {
                throw new BappedaException("Login Gagal");
            }
        }

    }
}
