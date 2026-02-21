using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using CrystalDecisions.Windows.Forms;
using CrystalDecisions.Shared;
using com.gtd.tvkabel.data.entity;
using com.gtd.tvkabel.data;
using com.gtd.tvkabel.data.util;

namespace com.gtd.tvkabel.ui.util
{
    public class Report
    {
        public class Tunggakan
        {
            public static int hitungTunggakan(Pelanggan pelanggan)
            {
                int tunggakan = 0;
                DataManager service = DataManager.Instance;

                DateTime now = DateTime.Now;
                String year = DateAndTime.getYear(now);
                String month = DateAndTime.getMonth(now);
                int yearLast;
                int monthLast;

                IList<Data> listPembayaran = service.search(EntityList.PEMBAYARAN, pelanggan.Id);
                if (listPembayaran != null)
                {
                    Pembayaran pembayaran = (Pembayaran)listPembayaran.Last();
                    yearLast = pembayaran.Tahun;
                    monthLast = pembayaran.Bulan;
                }
                else
                {
                    DateTime dt = DateAndTime.stringToDateTime(pelanggan.TglMulai);
                    yearLast = Int16.Parse(DateAndTime.getYear(dt));
                    monthLast = Int16.Parse(DateAndTime.getMonth(dt));
                }
                int yearNow = Int16.Parse(year);
                int monthNow = Int16.Parse(month);

                tunggakan += (monthNow - monthLast);
                tunggakan += ((yearNow - yearLast) * 12);

                return tunggakan;
            }

            public static double hitungJumlahTunggakan(Pelanggan pelanggan)
            {
                DataManager service = DataManager.Instance;
                Details details = (Details)service.get(EntityList.DETAIL, pelanggan.Id);

                return (details.Iuran * Tunggakan.hitungTunggakan(pelanggan));
            }

            public static String pilihKontak(Pelanggan pelanggan)
            {
                DataManager service = DataManager.Instance;
                IList<Data> listKontak = service.search(EntityList.KONTAK, pelanggan.Id);

                if (listKontak != null)
                {
                    Data kontak = listKontak.First();
                    return ((Kontak)kontak).Detail;
                }
                return "-";
            }

            public static IList<Data> sortTunggakan(IList<Data> listPelanggan)
            {
                IList<Data> listPelangganBaru = new List<Data>();

                foreach (Pelanggan pelanggan in listPelanggan)
                {
                    if (listPelangganBaru.Count == 0)
                    {
                        listPelangganBaru.Add(pelanggan);
                    }
                    else
                    {
                        Tunggakan.checkAndAdd(listPelangganBaru, pelanggan);
                    }
                }
                return listPelangganBaru;
            }

            private static void checkAndAdd(IList<Data> listPelanggan, Pelanggan pelanggan)
            {
                int x = listPelanggan.Count;

                foreach (Pelanggan check in listPelanggan)
                {
                    int index = listPelanggan.IndexOf(check);

                    if (Tunggakan.hitungTunggakan(pelanggan) > Tunggakan.hitungTunggakan(check))
                    {
                        listPelanggan.Insert(index, pelanggan);
                        break;
                    }
                    else if (Tunggakan.hitungTunggakan(pelanggan) == Tunggakan.hitungTunggakan(check))
                    {
                        listPelanggan.Insert(index + 1, pelanggan);
                        break;
                    }
                    else
                    {
                        if (index == x)
                        {
                            listPelanggan.Insert(index + 1, pelanggan);
                            break;
                        }
                    }
                }
            }

            private static void sortByMonth() { } //next version

            private static void sortByMoney() { } //next version
        }

        public enum JenisReport{ NONE, STRUK, PELANGGAN_AKTIF, PELANGGAN_NONAKTIF, BULANAN, TUNGGAKAN }
        private CrystalReportViewer reportViewer;

        private JenisReport jenisReport;
        private String reportPathStruk = Environment.CurrentDirectory + "\\" + "Struk.rpt";
        private String reportPathPelangganAktif = Environment.CurrentDirectory + "\\" + "LaporanPelangganAktif.rpt";
        private String reportPathPelangganNonAktif = Environment.CurrentDirectory + "\\" + "LaporanPelangganNonAktif.rpt";
        private String reportPathBulanan = Environment.CurrentDirectory + "\\" + "LaporanBulanan.rpt";
        private String reportPathTunggakan = Environment.CurrentDirectory + "\\" + "LaporanTunggakan.rpt";
        private String reportPathDefault = Environment.CurrentDirectory + "\\" + "LaporanDefault.rpt";

        public Report(CrystalReportViewer reportViewer, JenisReport jenisReport)
        {
            this.reportViewer = reportViewer;
            this.jenisReport = jenisReport;

            switch (jenisReport)
            {
                case JenisReport.STRUK :
                    this.reportViewer.ReportSource = reportPathStruk;
                    break;
                case JenisReport.PELANGGAN_AKTIF:
                    this.reportViewer.ReportSource = reportPathPelangganAktif;
                    break;
                case JenisReport.PELANGGAN_NONAKTIF:
                    this.reportViewer.ReportSource = reportPathPelangganNonAktif;
                    break;
                case JenisReport.BULANAN:
                    this.reportViewer.ReportSource = reportPathBulanan;
                    break;
                case JenisReport.TUNGGAKAN:
                    this.reportViewer.ReportSource = reportPathTunggakan;
                    break;
                default:
                    this.reportViewer.ReportSource = reportPathDefault;
                    break;
            }
        }

        private ParameterFields getParameterFields()
        {
            return this.reportViewer.ParameterFieldInfo;
        }

        public IList<String> getFields()
        {
            List<String> fields = new List<String>();
            fields.Add("id_pelanggan");
            fields.Add("nama_pelanggan");
            fields.Add("alamat");

            switch (jenisReport)
            {
                case JenisReport.BULANAN:
                    fields.Add("tanggal_bayar");
                    fields.Add("jumlah_bayar");
                    fields.Add("bulan");
                    fields.Remove("alamat");
                    break;
                case JenisReport.TUNGGAKAN:
                    fields.Add("tunggakan");
                    fields.Add("jumlah_bayar");
                    fields.Add("kontak");
                    break;
                default: break;
            }
            return fields;
        }

        public void setParameterSingleValue(IDictionary<String, String> values)
        {
            if (jenisReport.Equals(JenisReport.STRUK))
            {
                ParameterFields parameterFields = this.getParameterFields();

                foreach (String submittedValue in values.Keys)
                {
                    ParameterField parameterField = new ParameterField();
                    parameterField.Name = submittedValue;
                    String val;

                    ParameterDiscreteValue parameterDiscreteValue = new ParameterDiscreteValue();
                    values.TryGetValue(submittedValue, out val);
                    parameterDiscreteValue.Value = val;
                    parameterField.CurrentValues.Add(parameterDiscreteValue);

                    parameterFields.Add(parameterField);
                }
            }
            else
            {
                throw new Exception("Jenis report dan method tidak sama.\nGunakan setParameterMultipleValues()");
            }
        }

        public void setParameterMultipleValues(IList<Data> values)
        {
            if (jenisReport.Equals(JenisReport.BULANAN) || jenisReport.Equals(JenisReport.PELANGGAN_AKTIF) || jenisReport.Equals(JenisReport.PELANGGAN_NONAKTIF) || jenisReport.Equals(JenisReport.TUNGGAKAN))
            {
                ParameterFields parameterFields = this.getParameterFields();
                ParameterField parameterField = null;
                ParameterDiscreteValue parameterDiscreteValue = null;

                IList<String> lsFields = this.getFields();
                foreach (String field in lsFields)
                {
                    parameterField = new ParameterField();
                    parameterField.Name = field;

                    foreach (Data submittedValue in values)
                    {
                        switch (jenisReport)
                        {
                            case JenisReport.PELANGGAN_AKTIF:
                                parameterDiscreteValue = this.laporanPelangganAktif((Pelanggan)submittedValue, field);
                                break;
                            case JenisReport.PELANGGAN_NONAKTIF:
                                parameterDiscreteValue = this.laporanPelangganNonAktif((Pelanggan)submittedValue, field);
                                break;
                            case JenisReport.BULANAN:
                                parameterDiscreteValue = this.laporanBulanan((Pembayaran)submittedValue, field);
                                break;
                            case JenisReport.TUNGGAKAN:
                                parameterDiscreteValue = this.laporanTunggakan((Pelanggan)submittedValue, field);
                                break;
                        }
                        parameterField.CurrentValues.Add(parameterDiscreteValue);
                    }
                    parameterFields.Add(parameterField);
                }
            }
            else
            {
                throw new Exception("Jenis report dan method tidak sama.\nGunakan setParameterSingleValue()");
            }
        }

        private ParameterDiscreteValue laporanPelangganAktif(Pelanggan pelanggan, String field)
        {
            ParameterDiscreteValue parameterDiscreteValue = new ParameterDiscreteValue();

            switch (field)
            {
                case "id_pelanggan":
                    parameterDiscreteValue.Value = pelanggan.Id;
                    break;
                case "nama_pelanggan":
                    parameterDiscreteValue.Value = pelanggan.Nama;
                    break;
                case "alamat":
                    parameterDiscreteValue.Value = pelanggan.Alamat;
                    break;
            }
            return parameterDiscreteValue;
        }

        private ParameterDiscreteValue laporanPelangganNonAktif(Pelanggan pelanggan, String field)
        {
            ParameterDiscreteValue parameterDiscreteValue = new ParameterDiscreteValue();

            switch (field)
            {
                case "id_pelanggan":
                    parameterDiscreteValue.Value = pelanggan.Id;
                    break;
                case "nama_pelanggan":
                    parameterDiscreteValue.Value = pelanggan.Nama;
                    break;
                case "alamat":
                    parameterDiscreteValue.Value = pelanggan.Alamat;
                    break;
            }
            return parameterDiscreteValue;
        }

        private ParameterDiscreteValue laporanBulanan(Pembayaran pembayaran, String field)
        {
            ParameterDiscreteValue parameterDiscreteValue = new ParameterDiscreteValue();
            Pelanggan pelanggan = pembayaran.Pelanggan;

            switch (field)
            {
                case "bulan":
                    String bulan = DateAndTime.numberToMonth(pembayaran.Bulan);
                    parameterDiscreteValue.Value = bulan + " " + pembayaran.Tahun;
                    break;
                case "id_pelanggan":
                    parameterDiscreteValue.Value = pelanggan.Id;
                    break;
                case "nama_pelanggan":
                    parameterDiscreteValue.Value = pelanggan.Nama;
                    break;
                case "tanggal_bayar":
                    parameterDiscreteValue.Value = pembayaran.TanggalBayar;
                    break;
                case "jumlah_bayar":
                    parameterDiscreteValue.Value = pembayaran.Biaya;
                    break;
            }
            return parameterDiscreteValue;
        }

        private ParameterDiscreteValue laporanTunggakan(Pelanggan pelanggan, String field)
        {
            ParameterDiscreteValue parameterDiscreteValue = new ParameterDiscreteValue();

            switch (field)
            {
                case "id_pelanggan":
                    parameterDiscreteValue.Value = pelanggan.Id;
                    break;
                case "nama_pelanggan" :
                    parameterDiscreteValue.Value = pelanggan.Nama;
                    break;
                case "tunggakan" :
                    parameterDiscreteValue.Value = Tunggakan.hitungTunggakan(pelanggan);
                    break;
                case "jumlah_bayar":
                    Console.WriteLine("ID: " + pelanggan.Id);
                    parameterDiscreteValue.Value = Tunggakan.hitungJumlahTunggakan(pelanggan);
                    break;
                case "alamat":
                    parameterDiscreteValue.Value = pelanggan.Alamat;
                    break;
                case "kontak":
                    parameterDiscreteValue.Value = Tunggakan.pilihKontak(pelanggan);
                    break;
            }
            return parameterDiscreteValue;
        }
    }
}
