using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using CrystalDecisions.Windows.Forms;
using CrystalDecisions.Shared;
using com.gtd.tvkabel.data.entity;
using com.gtd.tvkabel.data;
using com.gtd.tvkabel.data.util;
using com.gtd.tvkabel.data.dao;
using com.gtd.tvkabel.data.service;

namespace com.gtd.tvkabel.ui.util
{
    public abstract class Report
    {
        protected CrystalReportViewer reportViewer;
        protected String reportPath;

        public Report() { }

        protected ParameterFields getParameterFields()
        {
            return this.reportViewer.ParameterFieldInfo;
        }

        public IList<String> getFields()
        {
            List<String> fields = new List<String>();
            fields.Add("id_pelanggan");
            fields.Add("nama_pelanggan");
            fields.Add("alamat");

            return fields;
        }

        public static String pilihKontak(Pelanggan pelanggan)
        {
            KontakService KontakService = new KontakService();
            IList<Kontak> listKontak = KontakService.GetByPelanggan(pelanggan);

            if ((listKontak != null) && (listKontak.Count > 0))
            {
                Kontak kontak = listKontak.First();
                return kontak.Detail;
            }
            return "-";
        }

        protected abstract ParameterDiscreteValue laporan(Data data, String field);
    }

    public class ReportBlank : Report
    {
        public ReportBlank(CrystalReportViewer reportViewer) : base()
        {
            reportPath = Environment.CurrentDirectory + "\\Report\\" + "LaporanDefault.rpt";
            this.reportViewer = reportViewer;
            this.reportViewer.ReportSource = reportPath;
        }

        public void setParameterMultipleValues(IList<Data> values)
        {
            throw new NotImplementedException();
        }

        protected override ParameterDiscreteValue laporan(Data data, string field)
        {
            throw new NotImplementedException();
        }
    }

    public class ReportStruk : Report
    {
        public ReportStruk(CrystalReportViewer reportViewer) : base()
        {
            reportPath = Environment.CurrentDirectory + "\\Report\\" + "Struk.rpt";
            this.reportViewer = reportViewer;
            this.reportViewer.ReportSource = reportPath;
        }

        public IDictionary<String, String> getFields(Pembayaran pembayaran)
        {
            Dictionary<String, String> values = new Dictionary<String, String>();
            values.Add("nomor", pembayaran.getNomorPembayaran(pembayaran.Tahun, pembayaran.Bulan));
            values.Add("id_pelanggan", pembayaran.Pelanggan.Id);
            values.Add("nama_pelanggan", pembayaran.Pelanggan.Nama);
            values.Add("biaya", pembayaran.Biaya.ToString());
            values.Add("bulan_tagihan", DateAndTime.numberToMonth(pembayaran.Bulan) + " " + pembayaran.Tahun);

            return values;
        }

        public void setParameterSingleValue(IDictionary<String, String> values)
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

        public void setParameterMultipleValues(IList<Data> values)
        {
            throw new NotImplementedException();
        }

        protected override ParameterDiscreteValue laporan(Data data, string field)
        {
            throw new NotImplementedException();
        }
    }

    public class ReportPelangganAktif : Report
    {
        public ReportPelangganAktif(CrystalReportViewer reportViewer) : base()
        {
            reportPath = Environment.CurrentDirectory + "\\Report\\" + "LaporanPelangganAktif.rpt";
            this.reportViewer = reportViewer;
            this.reportViewer.ReportSource = reportPath;
        }

        public new IList<String> getFields()
        {
            IList<String> fields = base.getFields();

            fields.Add("kontak");

            return fields;
        }

        public void setParameterMultipleValues(IList<Pelanggan> values)
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
                    parameterDiscreteValue = this.laporan((Pelanggan)submittedValue, field);
                    parameterField.CurrentValues.Add(parameterDiscreteValue);
                }
                parameterFields.Add(parameterField);
            }
        }

        protected override ParameterDiscreteValue laporan(Data data, String field)
        {
            ParameterDiscreteValue parameterDiscreteValue = new ParameterDiscreteValue();
            Pelanggan pelanggan = (Pelanggan)data;

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
                case "kontak":
                    parameterDiscreteValue.Value = pilihKontak(pelanggan);
                    break;
            }
            return parameterDiscreteValue;
        }
    }

    public class ReportPelangganNonAktif : Report
    {
        public ReportPelangganNonAktif(CrystalReportViewer reportViewer) : base()
        {
            reportPath = Environment.CurrentDirectory + "\\Report\\" + "LaporanPelangganNonAktif.rpt";
            this.reportViewer = reportViewer;
            this.reportViewer.ReportSource = reportPath;
        }

        public new IList<String> getFields()
        {
            return base.getFields();
        }

        public void setParameterMultipleValues(IList<Pelanggan> values)
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
                    parameterDiscreteValue = this.laporan((Pelanggan)submittedValue, field);
                    parameterField.CurrentValues.Add(parameterDiscreteValue);
                }
                parameterFields.Add(parameterField);
            }
        }

        protected override ParameterDiscreteValue laporan(Data data, String field)
        {
            ParameterDiscreteValue parameterDiscreteValue = new ParameterDiscreteValue();
            Pelanggan pelanggan = (Pelanggan)data;

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
    }

    public class ReportBulanan : Report
    {
        public ReportBulanan(CrystalReportViewer reportViewer)
            : base()
        {
            reportPath = Environment.CurrentDirectory + "\\Report\\" + "LaporanBulanan.rpt";
            this.reportViewer = reportViewer;
            this.reportViewer.ReportSource = reportPath;
        }

        public new IList<String> getFields()
        {
            List<String> fields = new List<String>();
            fields.Add("bulan");
            fields.Add("id_pelanggan");
            fields.Add("nama_pelanggan");
            fields.Add("tanggal_bayar");
            fields.Add("jumlah_bayar");

            return fields;
        }

        public void setParameterMultipleValues(IList<Pembayaran> values)
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
                    parameterDiscreteValue = this.laporan((Pembayaran)submittedValue, field);
                    parameterField.CurrentValues.Add(parameterDiscreteValue);
                }
                parameterFields.Add(parameterField);
            }
        }

        protected override ParameterDiscreteValue laporan(Data data, String field)
        {
            ParameterDiscreteValue parameterDiscreteValue = new ParameterDiscreteValue();
            Pembayaran pembayaran = (Pembayaran)data;
            Pelanggan pelanggan = pembayaran.Pelanggan;
            Console.WriteLine("Hey " + pelanggan.Id);

            switch (field)
            {
                case "bulan":
                    Console.WriteLine("Hey Bulan");
                    String bulan = DateAndTime.numberToMonth(pembayaran.Bulan);
                    parameterDiscreteValue.Value = bulan + " " + pembayaran.Tahun;
                    break;
                case "id_pelanggan":
                    Console.WriteLine("Hey ID");
                    parameterDiscreteValue.Value = pelanggan.Id;
                    break;
                case "nama_pelanggan":
                    Console.WriteLine("Hey Nama");
                    parameterDiscreteValue.Value = pelanggan.Nama;
                    break;
                case "tanggal_bayar":
                    Console.WriteLine("Hey Tanggal");
                    parameterDiscreteValue.Value = pembayaran.TanggalBayar;
                    break;
                case "jumlah_bayar":
                    Console.WriteLine("Hey Jumlah");
                    parameterDiscreteValue.Value = pembayaran.Biaya + "";
                    break;
            }
            return parameterDiscreteValue;
        }
    }

    public class ReportTunggakan : Report
    {
        public ReportTunggakan(CrystalReportViewer reportViewer) : base()
        {
            reportPath = Environment.CurrentDirectory + "\\Report\\" + "LaporanTunggakan.rpt";
            this.reportViewer = reportViewer;
            this.reportViewer.ReportSource = reportPath;
        }

        public new IList<String> getFields()
        {
            IList<String> fields = base.getFields();

            fields.Add("tunggakan");
            //fields.Add("jumlah_bayar");
            fields.Add("kontak");

            return fields;
        }

        public void setParameterMultipleValues(IList<Tunggakan> values)
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
                    parameterDiscreteValue = this.laporan(submittedValue, field);
                    parameterField.CurrentValues.Add(parameterDiscreteValue);
                }
                parameterFields.Add(parameterField);
            }
        }

        protected override ParameterDiscreteValue laporan(Data data, String field)
        {
            ParameterDiscreteValue parameterDiscreteValue = new ParameterDiscreteValue();
            Tunggakan tunggakan = (Tunggakan)data;
            Pelanggan pelanggan = tunggakan.Pelanggan;

            switch (field)
            {
                case "id_pelanggan":
                    parameterDiscreteValue.Value = pelanggan.Id;
                    break;
                case "nama_pelanggan":
                    parameterDiscreteValue.Value = pelanggan.Nama;
                    break;
                case "tunggakan":
                    parameterDiscreteValue.Value = tunggakan.JumlahBulan;
                    break;
                case "jumlah_bayar":
                    parameterDiscreteValue.Value = 0;
                    break;
                case "alamat":
                    parameterDiscreteValue.Value = pelanggan.Alamat;
                    break;
                case "kontak":
                    parameterDiscreteValue.Value = pilihKontak(pelanggan);
                    break;
            }
            return parameterDiscreteValue;
        }
    }

}
