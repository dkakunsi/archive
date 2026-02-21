using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.IO;
using dkakunsi.bappeda.accessor;
using dkakunsi.bappeda.adapter;
using dkakunsi.bappeda.util;

namespace dkakunsi.bappeda.client
{
    public partial class FormUtama : Form
    {
        private String jenisAset;

        public FormUtama(Form frm)
        {
            InitializeComponent();
            clientView();
        }

        public FormUtama(String param)
        {
            InitializeComponent();
            clientView();

            if (param.Equals("ADMIN"))
            {
                adminView();
            }
        }

        private void clientView()
        {
            Controller.Map1 = this.sfMap1;
            Controller.DialogCari = this.dialogCari;

            try
            {
                ServerSetting.read(ServerSetting.DEFAULTSETTING);
            }
            catch (FileNotFoundException exc)
            {
                new Controller().exceptionCatched();
            }
            catch (DirectoryNotFoundException exc)
            {
                new Controller().exceptionCatched();
            }
            finally
            {
                // Set up the ToolTip text for the Control.
                toolTip1.SetToolTip(this.btnCari, "Klik untuk mencari data");
                toolTip1.SetToolTip(this.btnLogin, "Klik untuk login");
                toolTip1.SetToolTip(this.btnLogout, "Klik untuk logout");

                this.btnSetting.Visible = false;
                this.btnLogin.Visible = true;
                this.btnLogout.Visible = false;
                this.btnKeluar.Visible = true;
                this.btnData.Visible = false;

                new Controller().resetPeta();
                this.pnlDataJalan.Visible = false;
                this.txtNamaAset.Enabled = false;
                this.Show();
            }
        }

        public void adminView()
        {
            this.btnSetting.Visible = true;
            this.btnLogin.Visible = false;
            this.btnKeluar.Visible = false;
            this.btnLogout.Visible = true;
            this.btnData.Visible = true;
        }

        private void buttonCari_Click(object sender, EventArgs e)
        {
            String param = this.txtNamaAset.Text;

            try
            {
                if ((jenisAset != null) && (jenisAset.Equals("Aset Jalan")))
                {
                    new Controller().cariJalan(param);

                    // tampilkan ke dalam text box
                    this.txtTtkAwal.Text = Controller.AsetJalan.TitikAwal;
                    this.txtTtkAkhir.Text = Controller.AsetJalan.TitikAkhir;
                    this.txtPanjangJln.Text = Controller.AsetJalan.PanjangJalan.ToString();
                    this.txtLebarJln.Text = Controller.AsetJalan.LebarJalan.ToString();
                    this.txtSumberFile.Text = Controller.AsetJalan.Peta.NamaPeta;
                }
            }
            catch (BappedaException exc)
            {
                MessageBox.Show(exc.Message, "Kesalahan", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }

        }

        private void cbKategoriAset_SelectedIndexChanged(object sender, EventArgs e)
        {
            String tipeAset = this.cbKategoriAset.SelectedItem.ToString();

            if (!tipeAset.Equals("--Pilih Kategori--"))
            {
                this.txtNamaAset.Enabled = true;

                if (tipeAset.Equals("Aset Jalan"))
                {
                    this.pnlDataJalan.Visible = true;
                    jenisAset = tipeAset;
                }
                else
                {
                    new Controller().resetPeta();
                }
            }
            else
            {
                this.txtNamaAset.Enabled = false;
            }
        }

        private void btnLogin_Click(object sender, EventArgs e)
        {
            ViewGenerator.generateFormLogin(this);
        }

        private void btnLogout_Click(object sender, EventArgs e)
        {
            DialogResult result = MessageBox.Show("Apakah Anda akan logout?", "Informasi", MessageBoxButtons.YesNo, MessageBoxIcon.Question);
            if (result == DialogResult.Yes)
                ViewGenerator.generateFormUtama("CLIENT", this, null);
        }

        private void btnSetting_Click(object sender, EventArgs e)
        {
            ViewGenerator.generateFormPengaturan(null);
        }

        private void btnKeluar_Click(object sender, EventArgs e)
        {
            DialogResult result = MessageBox.Show("Apakah Anda akan keluar dari Aplikasi ini?", "Informasi", MessageBoxButtons.YesNo, MessageBoxIcon.Question);
            if (result == DialogResult.Yes)
                Application.Exit();
        }

        private void btnData_Click(object sender, EventArgs e)
        {
            ViewGenerator.generateFormData(null);
        }

        private void btnReport_Click(object sender, EventArgs e)
        {
            ViewGenerator.generateFormReport(null);
        }

        private class Controller
        {
            public static IAsetJalanModel AsetJalan
            {
                get;
                set;
            }

            public static OpenFileDialog DialogCari
            {
                get;
                set;
            }

            public static EGIS.Controls.SFMap Map1
            {
                get;
                set;
            }

            public void cariJalan(String param)
            {
                try
                {
                    // ambil data
                    AsetJalan = (IAsetJalanModel)DataManager.getData("ASET", param);
                    openShapefile(AsetJalan.Peta.getView(), "CARI");
                }
                catch (BappedaException exc)
                {
                    throw exc;
                }
            }

            public void openShapefile(String path, String str)
            {
                EGIS.ShapeFileLib.ShapeFile sf;
                try
                {
                    if (str.Equals("CARI"))
                    {
                        resetPeta();
                        sf = Map1.AddShapeFile(path, "ShapeFile", "");
                        sf.RenderSettings.FillColor = Color.Red;
                        sf.RenderSettings.FieldName = sf.RenderSettings.DbfReader.GetFieldNames()[0];
                        sf.RenderSettings.ToolTipFieldName = sf.RenderSettings.FieldName;
                    }
                    else
                    {
                        sf = Map1.AddShapeFile(path, "ShapeFile", "");
                    }

                    sf.RenderSettings.UseToolTip = false;
                    // sf.RenderSettings.Font = new Font(this.Font.FontFamily, 12);
                    sf.RenderSettings.IsSelectable = true;
                }
                catch (BappedaException exc)
                {
                    throw exc;
                }
                catch (FileNotFoundException exc)
                {
                    throw new BappedaException("File Tidak Ditemukan\n" + exc.Message);
                }
                catch (DirectoryNotFoundException exc)
                {
                    throw new BappedaException("Directory Tidak Ditemukan\n" + exc.Message);
                }
            }

            public void resetPeta()
            {
                Map1.ClearShapeFiles();

                String server = "";

                if (ServerSetting.SERVER.Equals("localhost") || ServerSetting.SERVER.Equals("127.0.0.1"))
                {
                    server = @"D:\";
                }
                else
                {
                    server = @"\\" + ServerSetting.SERVER + @"\";
                }
                
                openShapefile(server + @"\BAPPEDA\kecamatan.shp", "");
                openShapefile(server + @"\BAPPEDA\jalan_manado_ok.shp.shp", "");
            }

            public void exceptionCatched()
            {
                DialogResult result = MessageBox.Show("File ServerSetting.xml tidak ditemukan\n Tambahkan File?", "Peringatan", MessageBoxButtons.YesNo, MessageBoxIcon.Warning);
                switch (result)
                {
                    case DialogResult.Yes:
                        DialogCari.InitialDirectory = @"D:\";
                        DialogCari.Filter = "XML File|*.xml";
                        DialogCari.FilterIndex = 1;
                        if (DialogCari.ShowDialog() == DialogResult.OK)
                        {
                            ServerSetting.DEFAULTSETTING = DialogCari.FileName;
                            ServerSetting.read(ServerSetting.DEFAULTSETTING);
                        }
                        break;
                    case DialogResult.No:
                        MessageBox.Show("Sistem tidak bisa menemukan file konfigurasi!\n Aplikasi akan ditutup sekarang", "Informasi", MessageBoxButtons.OK, MessageBoxIcon.Information);
                        Application.Exit();
                        break;
                    default: break;
                }
            }

        }
    }
}
