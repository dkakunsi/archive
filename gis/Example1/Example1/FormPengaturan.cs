using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Net;
using System.Collections;
using System.IO;
using dkakunsi.bappeda.adapter;
using dkakunsi.bappeda.accessor;
using dkakunsi.bappeda.util;

namespace dkakunsi.bappeda.client
{
    public partial class FormPengaturan : Form
    {

        public FormPengaturan()
        {
            InitializeComponent();
            resetPeta();
        }

        //GENERAL
        private void keluarToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void btnMap_Click(object sender, EventArgs e)
        {
            resetPeta();
        }

        private void btnAkun_Click(object sender, EventArgs e)
        {
            resetAkun();
        }

        private void btnServer_Click(object sender, EventArgs e)
        {
            resetServer();
        }

        //panel Server
        private void btnSimpan_Click(object sender, EventArgs e)
        {

            try
            {
                new ServerController().simpan(txtCredServer.Text, txtCredUsername.Text, txtCredPassword.Text, txtDatabase.Text, txtDbUsername.Text, txtDbPassword.Text, txtFileKonfigurasi.Text);
                MessageBox.Show("Konfigurasi berhasil disimpan", "Informasi", MessageBoxButtons.OK, MessageBoxIcon.Information);
            }
            catch (SystemException exc)
            {
                MessageBox.Show("System Error\nHubungi Pengembang Aplikasi", "Kesalahan", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            finally
            {
                resetServer();
            }
        }

        private void btnTestKoneksi_Click(object sender, EventArgs e)
        {

            try
            {
                new ServerController().testKoneksi(this.txtCredServer.Text, this.txtDatabase.Text, this.txtDbUsername.Text, this.txtDbPassword.Text);
                MessageBox.Show("Konekasi Berhasil", "Informasi", MessageBoxButtons.OK, MessageBoxIcon.Information);
            }
            catch (BappedaException exc)
            {
                MessageBox.Show(exc.Message, "Kesalahan", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private void txtFileKonfigurasi_Click(object sender, EventArgs e)
        {
            String xmlFile = "";

            dialogCari.InitialDirectory = @"D:\";
            dialogCari.Filter = "XML File|*.xml";
            dialogCari.FilterIndex = 1;
            if (dialogCari.ShowDialog() == DialogResult.OK)
            {
                xmlFile = dialogCari.FileName;
                this.txtFileKonfigurasi.Text = xmlFile;
                ServerSetting.read(xmlFile);
                this.txtCredServer.Text = ServerSetting.SERVER;
                this.txtCredUsername.Text = ServerSetting.CREDUSERNAME;
                this.txtCredPassword.Text = ServerSetting.CREDPASSWORD;
                this.txtDatabase.Text = ServerSetting.DATABASE;
                this.txtDbUsername.Text = ServerSetting.DBUSERNAME;
                this.txtDbPassword.Text = ServerSetting.DBPASSWORD;
            }

        }

        private void resetServer()
        {
            this.txtCredPassword.Text = "";
            this.txtCredServer.Text = "";
            this.txtCredUsername.Text = "";
            this.txtDatabase.Text = "";
            this.txtDbPassword.Text = "";
            this.txtDbUsername.Text = "";

            this.pnlAkun.Visible = false;
            this.pnlPeta.Visible = false;
            this.pnlServer.Visible = true;
        }

        //panel Peta
        private void cbNamaAset_SelectedIndexChanged(object sender, EventArgs e)
        {
            String param = cbListAset.SelectedItem.ToString();

            if (!param.Equals("--Pilih Peta--"))
            {
                IPetaModel peta = (IPetaModel)new PetaController().ambilData("PETA", param);

                this.txtNamaAset.Text = peta.NamaPeta;
                this.txtPath.Text = peta.Path;

                this.btnPetaHapus.Enabled = true;
                this.btnPetaSimpan.Enabled = false;
                this.btnPetaTambah.Enabled = false;
                this.btnPetaUbah.Enabled = true;
                this.btnPilih.Enabled = false;
            }
            else
            {
                this.txtUsername.Text = "";
                this.txtPassword.Text = "";
            }
        }

        private void btnSimpanPeta_Click(object sender, EventArgs e)
        {
            PetaController.NamaFile = this.txtNamaFile.Text;
            PetaController.NamaAset = this.txtNamaAset.Text;

            try
            {
                new PetaController().simpan();
            }
            catch (BappedaException exc)
            {
                MessageBox.Show(exc.Message, "Kesalahan", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            catch (Exception exc)
            {
                MessageBox.Show(exc.Message, "Kesalahan", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            finally
            {
                resetPeta();
            }
        }

        private void btnPetaUbah_Click(object sender, EventArgs e)
        {
            this.btnPetaHapus.Enabled = false;
            this.btnPetaSimpan.Enabled = true;
            this.btnPetaTambah.Enabled = false;
            this.btnPetaUbah.Enabled = false;
            this.btnPilih.Enabled = true;
            this.txtNamaAset.Enabled = true;
            this.txtNamaFile.Enabled = true;

            PetaController.State = "UBAH";
        }

        private void btnPetaTambah_Click(object sender, EventArgs e)
        {
            resetPeta();
            this.btnPetaHapus.Enabled = false;
            this.btnPetaSimpan.Enabled = true;
            this.btnPetaTambah.Enabled = false;
            this.btnPetaUbah.Enabled = false;
            this.btnPilih.Enabled = true;
            this.txtNamaAset.Enabled = true;
            this.txtNamaFile.Enabled = true;

            PetaController.State = "TAMBAH";
        }

        private void btnPetaHapus_Click(object sender, EventArgs e)
        {
            DialogResult result = MessageBox.Show("Apakah Anda akan menghapus data ini?", "Persetujuan", MessageBoxButtons.YesNo, MessageBoxIcon.Question);
            if (result == DialogResult.Yes)
            {
                try
                {
                    new PetaController().hapus();
                    MessageBox.Show("Data berhasil dihapus", "Informasi", MessageBoxButtons.OK, MessageBoxIcon.Information);
                }
                catch (BappedaException exc)
                {
                    MessageBox.Show("Hapus Data Gagal\n" + exc.Message, "Kesalahan", MessageBoxButtons.OK, MessageBoxIcon.Information);
                }
                finally
                {
                    resetPeta();
                }
            }
        }

        private void btnPilih_Click(object sender, EventArgs e)
        {
            if (PetaController.State.Equals("TAMBAH"))
            {
                dialogCari.InitialDirectory = @"D:";
                dialogCari.Filter = "Shape File|*.shp";
                dialogCari.FilterIndex = 1;
                if (dialogCari.ShowDialog() == DialogResult.OK)
                {
                    PetaController.Map = dialogCari.FileName;
                    this.txtPath.Text = PetaController.Map;
                }
            }
            else if(PetaController.State.Equals("UBAH"))
            {
                dialogCari.InitialDirectory = @"\\" + ServerSetting.SERVER + @"\BAPPEDA\JALAN";
                dialogCari.Filter = "Shape File|*.shp";
                dialogCari.FilterIndex = 1;
                if (dialogCari.ShowDialog() == DialogResult.OK)
                {
                    PetaController.Map = dialogCari.FileName;
                    this.txtPath.Text = PetaController.Map;
                }
            }
        }

        private void resetPeta()
        {
            this.txtNamaAset.Enabled = false;
            this.txtNamaFile.Enabled = false;

            this.txtNamaAset.Text = "";
            this.txtNamaFile.Text = "";
            this.txtPath.Text = "";

            this.btnPetaHapus.Enabled = false;
            this.btnPetaSimpan.Enabled = false;
            this.btnPetaTambah.Enabled = true;
            this.btnPetaUbah.Enabled = false;
            this.btnPilih.Enabled = false;

            try
            {
                new PetaController().loadList(this.cbListAset);
            }
            catch (BappedaException exc)
            {
                MessageBox.Show("Kesalahan Koneksi ke Server\nAtur file konfigurasi", "Pemberitahuan", MessageBoxButtons.OK, MessageBoxIcon.Warning);
            }
            finally
            {
                this.pnlAkun.Visible = false;
                this.pnlPeta.Visible = true;
                this.pnlServer.Visible = false;
            }

        }

        //panel AKun
        private void cbListAkun_SelectedIndexChanged(object sender, EventArgs e)
        {
            String param = cbListAkun.SelectedItem.ToString();

            if (!param.Equals("--Pilih Akun--"))
            {
                ILoginModel login = (ILoginModel) new AkunController().ambilData("LOGIN", param);

                this.txtUsername.Text = login.Username;
                this.txtPassword.Text = login.Password;

                this.btnAkunHapus.Enabled = true;
                this.btnAkunSimpan.Enabled = false;
                this.btnAkunTambah.Enabled = false;
                this.btnAkunUbah.Enabled = true;
            }
            else
            {
                this.btnAkunHapus.Enabled = false;
                this.btnAkunSimpan.Enabled = false;
                this.btnAkunTambah.Enabled = true;
                this.btnAkunUbah.Enabled = false;
                this.txtUsername.Text = "";
                this.txtPassword.Text = "";
            }
        }

        private void btnAkunTambah_Click(object sender, EventArgs e)
        {
            resetAkun();
            this.txtUsername.Enabled = true;
            this.txtPassword.Enabled = true;
            this.btnAkunSimpan.Enabled = true;
            this.btnAkunTambah.Enabled = false;
            this.cbListAkun.Enabled = false;

            AkunController.State = "TAMBAH";
        }

        private void btnAkunUbah_Click(object sender, EventArgs e)
        {
            this.txtUsername.Enabled = true;
            this.txtPassword.Enabled = true;
            this.btnAkunSimpan.Enabled = true;
            this.btnAkunUbah.Enabled = false;
            this.btnAkunTambah.Enabled = false;
            this.cbListAkun.Enabled = false;

            AkunController.State = "UBAH";
        }

        private void btnAkunSimpan_Click(object sender, EventArgs e)
        {
            try
            {
                AkunController.Username = this.txtUsername.Text;
                AkunController.Password = this.txtPassword.Text;
                new AkunController().simpan();
                MessageBox.Show("Data berhasil disimpan", "Informasi", MessageBoxButtons.OK, MessageBoxIcon.Information);
            }
            catch (BappedaException exc)
            {
                MessageBox.Show("Simpan Data Gagal\n" + exc.Message, "Kesalahan", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            finally
            {
                resetAkun();
            }
        }

        private void btnAkunHapus_Click(object sender, EventArgs e)
        {
            DialogResult result = MessageBox.Show("Apakah Anda akan menghapus data ini?", "Persetujuan", MessageBoxButtons.YesNo, MessageBoxIcon.Question);
            if (result == DialogResult.Yes)
            {
                try
                {
                    new AkunController().hapus();
                    MessageBox.Show("Data berhasil dihapus", "Informasi", MessageBoxButtons.OK, MessageBoxIcon.Information);
                }
                catch (BappedaException exc)
                {
                    MessageBox.Show("Hapus Data Gagal\n" + exc.Message, "Kesalahan", MessageBoxButtons.OK, MessageBoxIcon.Information);
                }
                finally
                {
                    resetAkun();
                }
            }
        }

        private void resetAkun()
        {
            this.txtUsername.Text = "";
            this.txtPassword.Text = "";
            this.txtUsername.Enabled = false;
            this.txtPassword.Enabled = false;
            this.btnAkunHapus.Enabled = false;
            this.btnAkunSimpan.Enabled = false;
            this.btnAkunTambah.Enabled = true;
            this.btnAkunUbah.Enabled = false;
            this.cbListAkun.Enabled = true;

            try
            {
                new AkunController().loadList(this.cbListAkun);
            }
            catch (BappedaException exc)
            {
                MessageBox.Show("Kesalahan Koneksi ke Server\nAtur file konfigurasi", "Pemberitahuan", MessageBoxButtons.OK, MessageBoxIcon.Warning);
            }
            finally
            {
                this.pnlAkun.Visible = true;
                this.pnlPeta.Visible = false;
                this.pnlServer.Visible = false;
            }
        }

        //inner class
        private class ServerController
        {
            public void simpan(String credServer, String credUsername, String credPassword, String database, String dbUsername, String dbPassword, String file)
            {
                try
                {
                    ServerSetting.SERVER = credServer;
                    ServerSetting.CREDUSERNAME = credUsername;
                    ServerSetting.CREDPASSWORD = credPassword;
                    ServerSetting.DATABASE = database;
                    ServerSetting.DBUSERNAME = dbUsername;
                    ServerSetting.DBPASSWORD = dbPassword;

                    ServerSetting.write(file);
                }
                catch (SystemException exc)
                {
                    throw new BappedaException("System Error\nHubungi Pengembang Aplikasi");
                }
            }

            public void testKoneksi(String server, String database, String username, String password)
            {

                try
                {
                    ConnectionManager conn = new ConnectionManager(server, database, username, password);
                }
                catch (BappedaException exc)
                {
                    throw exc;
                }
            }
        }

        private class PetaController : Controller
        {
            private static String map;
            private static String namaFile;
            private static String namaAset;

            public static String Map
            {
                get { return map; }
                set { map = value; }
            }

            public static String NamaFile
            {
                get { return namaFile; }
                set { namaFile = value; }
            }

            public static String NamaAset
            {
                get { return namaAset; }
                set { namaAset = value; }
            }

            private void uploadFile(String url, String filePath)
            {
                try
                {
                    WebClient client = new WebClient();
                    Uri addy = new Uri(url);
                    NetworkCredential nc = new NetworkCredential(ServerSetting.CREDUSERNAME, ServerSetting.CREDPASSWORD);

                    client.Credentials = nc;
                    client.Headers.Add(HttpRequestHeader.UserAgent, "anything");
                    client.UploadFile(addy, filePath);
                }
                catch (FileNotFoundException exc)
                {
                    throw new BappedaException(exc.Message);
                }
                catch (WebException exc)
                {
                    // throw new BappedaException(exc.Message);
                }
            }

            public override void loadList(ComboBox cbList)
            {
                try
                {
                    cbList.Items.Clear();
                    cbList.Items.Add("--Pilih Peta--");
                    cbList.SelectedIndex = 0;

                    ArrayList list = DataManager.getAll("PETA");
                    foreach (IPetaModel peta in list)
                    {
                        if (!cbList.Items.Contains(peta.NamaPeta))
                        {
                            cbList.Items.Add(peta.NamaPeta);
                        }
                    }
                }
                catch (BappedaException exc)
                {
                    throw exc;
                }
            }

            public override void tambah()
            {
                IPetaModel peta = null;

                if (map != null)
                {
                    String TMP = "";
                    int iTMP = 0;
                    String path = "";

                    // daftar file format yang akan di-upload
                    String[] list = { "dbf", "sbn", "sbx", "shp", "shx" };

                    try
                    {
                        // set file path (tanpa file format)
                        TMP = map;
                        iTMP = TMP.Length;

                        path = TMP.Substring(0, iTMP - 3);

                        // set nama file (tanpa file format)
                        TMP = namaFile;
                        iTMP = TMP.Length;

                        String file = TMP;

                        if (TMP.Substring(iTMP - 4, 4).Contains("."))
                        {
                            file = TMP.Substring(0, iTMP - 4);
                        }

                        // set path untuk server
                        String server = @"\\" + ServerSetting.SERVER;
                        String fPath = @"\BAPPEDA\JALAN\" + file + ".";

                        foreach (String str in list)
                        {
                            String filePath = path + str;
                            String url = server + fPath + str;

                            uploadFile(url, filePath);
                        }

                        MessageBox.Show("Penambahan file berhasil", "Informasi", MessageBoxButtons.OK, MessageBoxIcon.Information);

                        peta = (IPetaModel)DataManager.getEmpty("PETA");

                        peta.NamaPeta = namaAset;
                        peta.Server = ServerSetting.SERVER;
                        peta.Path = fPath + "shp";

                        DataManager.tambah(peta);
                    }
                    catch (BappedaException exc)
                    {
                        throw exc;
                    }
                    catch (SystemException exc)
                    {
                        throw new BappedaException(exc.Message);
                    }
                    catch (Exception exc)
                    {
                        throw exc;
                    }
                }
                else
                {
                    try
                    {
                        peta = (IPetaModel)DataManager.getEmpty("PETA");

                        peta.NamaPeta = namaAset;
                        peta.Server = ServerSetting.SERVER;
                        peta.Path = "";

                        DataManager.tambah(peta);
                    }
                    catch (BappedaException exc)
                    {
                        throw exc;
                    }
                }
            }

            public override void ubah()
            {
                IPetaModel peta = (IPetaModel)data;

                try
                {
                    String fPath = @"\BAPPEDA\JALAN\" + namaFile;
                    peta.Path = fPath;

                    DataManager.ubah(peta);

                    MessageBox.Show("Data berhasil diubah", "Informasi", MessageBoxButtons.OK, MessageBoxIcon.Information);
                }
                catch (BappedaException exc)
                {
                    throw exc;
                }
            }

        }

        private class AkunController : Controller
        {
            public static String Username
            {
                get;
                set;
            }

            public static String Password
            {
                get;
                set;
            }

            public override void tambah()
            {
                ILoginModel login = (ILoginModel)DataManager.getEmpty("LOGIN");
                login.Username = Username;
                login.Password = Password;
                DataManager.tambah(login);
            }

            public override void ubah()
            {
                ILoginModel login = (ILoginModel)data;
                login.Username = Username;
                login.Password = Password;
                DataManager.ubah(login);
            }

            public override void loadList(ComboBox cbList)
            {
                try
                {
                    cbList.Items.Clear();
                    cbList.Items.Add("--Pilih Akun--");
                    cbList.SelectedIndex = 0;

                    ArrayList list = DataManager.getAll("LOGIN");
                    foreach (ILoginModel login in list)
                    {
                        if (!cbList.Items.Contains(login.Username))
                        {
                            cbList.Items.Add(login.Username);
                        }
                    }
                }
                catch (BappedaException exc)
                {
                    throw exc;
                }
            }
        }

    }
}
