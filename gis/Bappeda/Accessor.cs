using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace dkakunsi.bappeda.accessor
{
    public interface IDataModel
    {
    }

    public interface IAsetJalanModel : IDataModel
    {
        String NamaJalan { get; set; }
        String TitikAwal { get; set; }
        String TitikAkhir { get; set; }
        int PanjangJalan { get; set; }
        int LebarJalan { get; set; }
        IPetaModel Peta { get; set; }
    }

    public interface IPetaModel : IDataModel
    {
        String NamaPeta { get; set; }
        String Path { get; set; }
        String Server { get; set; }
        String getView();
    }

    public interface ILoginModel : IDataModel
    {
        String Username { get; set; }
        String Password { get; set; }

        bool doOtentikasi(String password);
    }

}
