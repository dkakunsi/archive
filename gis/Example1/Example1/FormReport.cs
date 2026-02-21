using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using dkakunsi;
using dkakunsi.bappeda.util.report;
using Microsoft.Reporting.WinForms;

namespace dkakunsi.bappeda.client
{
    public partial class FormReport : Form
    {
        private String[] parameter = new String[5];

        public FormReport()
        {
            InitializeComponent();
        }

        private void createSingleReport()
        {
            String namaJalan = this.txtNamaJalan.Text;

            Report dsSingleReport = MyReport.cretaeSingleReport(namaJalan);
            DataTable dt = dsSingleReport.Tables["Aset Jalan"];

            if (dt.Rows.Count > 0)
            {
                parameter[0] = dt.Rows[0]["NAMA JALAN"].ToString();
                parameter[1] = dt.Rows[0]["TITIK AWAL"].ToString();
                parameter[2] = dt.Rows[0]["TITIK AKHIR"].ToString();
                parameter[3] = dt.Rows[0]["PANJANG JALAN"].ToString();
                parameter[4] = dt.Rows[0]["LEBAR JALAN"].ToString();

                ReportParameter[] param = new ReportParameter[5];
                param[0] = new ReportParameter("namaJalan", parameter[0]);
                param[1] = new ReportParameter("titikAwal", parameter[1]);
                param[2] = new ReportParameter("titikAkhir", parameter[2]);
                param[3] = new ReportParameter("panjangJalan", parameter[3]);
                param[4] = new ReportParameter("lebarJalan", parameter[4]);

                this.reportViewer1.LocalReport.ReportEmbeddedResource = "Bappeda.Report1.rdlc";
                this.reportViewer1.LocalReport.SetParameters(param);
                this.reportViewer1.LocalReport.Refresh();
            }
            else
            {
                MessageBox.Show("Data Tidak Ditemukan", "Kesalahan", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }

            this.reportViewer1.RefreshReport();
        }

        private void bntKeluar_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void btnCari_Click(object sender, EventArgs e)
        {
            createSingleReport();
        }
    }
}
