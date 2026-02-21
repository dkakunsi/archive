using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using dkakunsi.bappeda.accessor;
using dkakunsi.bappeda.adapter;
using dkakunsi.bappeda.util;

namespace dkakunsi.bappeda.client
{
    public class ViewGenerator
    {

        private static void isNull(Form frm)
        {
            if (frm != null)
            {
                frm.Close();
            }
        }

        public static void generateFormUtama(String param, Form frm, Form frmBefore)
        {
            isNull(frm);
            isNull(frmBefore);
            new FormUtama(param).Show();
        }

        public static void generateFormData(Form frm)
        {
            isNull(frm);
            new FormData().Show();
        }

        public static void generateFormPengaturan(Form frm)
        {
            isNull(frm);
            new FormPengaturan().Show();
        }

        public static void generateFormLogin(Form frm)
        {
            new FormLogin(frm).Show();
        }

        public static void generateFormReport(Form frm)
        {
            isNull(frm);
            new FormReport().Show();
        }
    }

    public abstract class Controller
    {
        protected static String state;
        protected static IDataModel data;

        public static String State
        {
            get { return state; }
            set { state = value; }
        }

        public virtual IDataModel ambilData(String type, String param)
        {
            data = (IDataModel)DataManager.getData(type, param);

            return data;
        }

        public virtual void simpan()
        {
            try
            {
                switch (state)
                {
                    case "UBAH":
                        ubah();
                        break;
                    case "TAMBAH":
                        tambah();
                        break;
                    default: break;
                }

            }
            catch (BappedaException exc)
            {
                throw exc;
            }
        }

        public virtual void hapus()
        {
            try
            {
                DataManager.hapus(data);
            }
            catch (BappedaException exc)
            {
                throw exc;
            }
        }

        public abstract void tambah();

        public abstract void ubah();

        public abstract void loadList(ComboBox cbList);

    }
}
