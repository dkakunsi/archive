using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Collections;
using dkakunsi.bappeda.entity;
using dkakunsi.bappeda.accessor;
using dkakunsi.bappeda.adapter;
using dkakunsi.bappeda.util;

namespace dkakunsi.bappeda.client
{
    public partial class FormData : Form
    {
        public FormData()
        {
            InitializeComponent();

            // Set up the ToolTip text for the Control.
            toolTip1.SetToolTip(this.btnCari, "Klik untuk mencari data");
            toolTip1.SetToolTip(this.btnHapus, "Klik untuk menghapus data");
            toolTip1.SetToolTip(this.btnKeluar, "Klik untuk keluar");
            toolTip1.SetToolTip(this.btnReset, "Klik untuk me-reset tampilan");
            toolTip1.SetToolTip(this.btnSimpan, "Klik untuk menyimpan data");
            toolTip1.SetToolTip(this.btnTambah, "Klik untuk menambah data");
            toolTip1.SetToolTip(this.btnUbah, "Klik untuk mengubah data");

            resetView();
        }

        private void btnCari_Click(object sender, EventArgs e)
        {
            String param = this.txtNamaJalan.Text;

            try
            {
                // ambil data
                IAsetJalanModel asetJalan = (IAsetJalanModel)new DataController().ambilData("ASET", param);

                // tampilkan ke dalam text box
                this.txtTitikAwal.Text = asetJalan.TitikAwal;
                this.txtTitikAkhir.Text = asetJalan.TitikAkhir;
                this.txtPanjangJalan.Text = asetJalan.PanjangJalan.ToString();
                this.txtLebarJalan.Text = asetJalan.LebarJalan.ToString();
                this.cbPeta.SelectedItem = asetJalan.Peta.NamaPeta;

                this.btnCari.Enabled = false;
                this.btnTambah.Enabled = false;
                this.btnUbah.Enabled = true;
                this.btnSimpan.Enabled = false;
                this.btnHapus.Enabled = true;
                this.btnReset.Enabled = true;
                this.btnKeluar.Enabled = true;
            }
            catch (BappedaException exc)
            {
                MessageBox.Show(exc.Message, "Informasi", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private void btnTambah_Click(object sender, EventArgs e)
        {
            this.txtNamaJalan.Enabled = true;
            this.txtTitikAwal.Enabled = true;
            this.txtTitikAkhir.Enabled = true;
            this.txtPanjangJalan.Enabled = true;
            this.txtLebarJalan.Enabled = true;
            this.cbPeta.Enabled = true;

            this.btnCari.Enabled = false;
            this.btnTambah.Enabled = false;
            this.btnUbah.Enabled = false;
            this.btnSimpan.Enabled = true;
            this.btnHapus.Enabled = false;
            this.btnReset.Enabled = true;
            this.btnKeluar.Enabled = true;

            DataController.State = "TAMBAH";
        }

        private void btnUbah_Click(object sender, EventArgs e)
        {
            this.txtNamaJalan.Enabled = true;
            this.txtTitikAwal.Enabled = true;
            this.txtTitikAkhir.Enabled = true;
            this.txtPanjangJalan.Enabled = true;
            this.txtLebarJalan.Enabled = true;
            this.cbPeta.Enabled = true;

            this.btnCari.Enabled = false;
            this.btnTambah.Enabled = false;
            this.btnUbah.Enabled = false;
            this.btnSimpan.Enabled = true;
            this.btnHapus.Enabled = false;
            this.btnReset.Enabled = true;
            this.btnKeluar.Enabled = true;

            DataController.State = "UBAH";
        }

        private void btnSimpan_Click(object sender, EventArgs e)
        {
            try
            {
                DataController.NamaJalan = this.txtNamaJalan.Text;
                DataController.TitikAwal = this.txtTitikAwal.Text;
                DataController.TitikAkhir = this.txtTitikAkhir.Text;
                DataController.PanjangJalan = Int32.Parse(this.txtPanjangJalan.Text);
                DataController.LebarJalan = Int32.Parse(this.txtLebarJalan.Text);
                DataController.Peta = this.cbPeta.SelectedItem.ToString();

                new DataController().simpan();
                
                MessageBox.Show("Data berhasil disimpan", "Informasi", MessageBoxButtons.OK, MessageBoxIcon.Information);
                resetView();
            }
            catch (BappedaException exc)
            {
                MessageBox.Show("Simpan Data Gagal\n" + exc.Message, "Kesalahan", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            catch (FormatException exc)
            {
                MessageBox.Show("Kesalahan Format pada field Panjang Jalan atau Lebar Jalan", "Peringatan", MessageBoxButtons.OK, MessageBoxIcon.Warning);
            }
        }

        private void btnHapus_Click(object sender, EventArgs e)
        {
            DialogResult result = MessageBox.Show("Apakah Anda akan menghapus data ini?", "Persetujuan", MessageBoxButtons.YesNo, MessageBoxIcon.Question);
            if (result == DialogResult.Yes)
            {
                // hapus data
                try
                {
                    new DataController().hapus();
                    MessageBox.Show("Data berhasil dihapus", "Informasi", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    resetView();
                }
                catch (SystemException exc)
                {
                    MessageBox.Show("Data gagal dihapus" + exc.Message, "Kesalahan", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }

            }

        }

        private void btnReset_Click(object sender, EventArgs e)
        {
            resetView();
        }

        private void btnKeluar_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void resetView()
        {
            this.txtNamaJalan.Text = "";
            this.txtTitikAwal.Text = "";
            this.txtTitikAkhir.Text = "";
            this.txtPanjangJalan.Text = "";
            this.txtLebarJalan.Text = "";
            this.cbPeta.SelectedIndex = 0;

            this.txtNamaJalan.Enabled = true;
            this.txtTitikAwal.Enabled = false;
            this.txtTitikAkhir.Enabled = false;
            this.txtPanjangJalan.Enabled = false;
            this.txtLebarJalan.Enabled = false;
            this.cbPeta.Enabled = false;

            this.btnCari.Enabled = true;
            this.btnTambah.Enabled = true;
            this.btnUbah.Enabled = false;
            this.btnSimpan.Enabled = false;
            this.btnHapus.Enabled = false;
            this.btnReset.Enabled = false;
            this.btnKeluar.Enabled = true;

            DataController dataCon = new DataController();
            dataCon.loadList(cbPeta);
            dataCon.loadDataToTable(dgData);
        }

        private class DataController : Controller
        {
            public static String NamaJalan
            {
                get;
                set;
            }
            public static String TitikAwal
            {
                get;
                set;
            }
            public static String TitikAkhir
            {
                get;
                set;
            }
            public static Int32 PanjangJalan
            {
                get;
                set;
            }
            public static Int32 LebarJalan
            {
                get;
                set;
            }
            public static String Peta
            {
                get;
                set;
            }

            public override void loadList(ComboBox cbList)
            {
                ArrayList list = DataManager.getAll("PETA");

                foreach (IPetaModel peta in list)
                {
                    if (!cbList.Items.Contains(peta.NamaPeta))
                    {
                        cbList.Items.Add(peta.NamaPeta);
                    }
                }
            }

            public override void tambah()
            {
                IAsetJalanModel asetJalan = null;

                // buat object baru
                asetJalan = new AsetJalanModel();

                // masukan nilai dari textField ke dalam object
                asetJalan.NamaJalan = NamaJalan;
                asetJalan.TitikAwal = TitikAwal;
                asetJalan.TitikAkhir = TitikAkhir;
                asetJalan.PanjangJalan = PanjangJalan;
                asetJalan.LebarJalan = LebarJalan;
                asetJalan.Peta = (IPetaModel)DataManager.getData("PETA", Peta); // buat object PetaModel

                // simpan data
                DataManager.tambah(asetJalan);
            }

            public override void ubah()
            {
                IAsetJalanModel asetJalan = (IAsetJalanModel)data;

                // masukan nilai dari textField ke dalam object
                asetJalan.NamaJalan = NamaJalan;
                asetJalan.TitikAwal = TitikAwal;
                asetJalan.TitikAkhir = TitikAkhir;
                asetJalan.PanjangJalan = PanjangJalan;
                asetJalan.LebarJalan = LebarJalan;
                asetJalan.Peta = (IPetaModel)DataManager.getData("PETA", Peta); // buat object PetaModel

                // simpan data
                DataManager.ubah(asetJalan);
            }

            public void loadDataToTable(DataGridView control)
            {
                ArrayList list;
                try
                {
                    list = DataManager.getAll("ASET");
                    int data = control.RowCount;
                    int loop = list.Count;

                    control.Rows.Clear();

                    foreach (IAsetJalanModel aset in list)
                    {
                        object[] obj = new object[] { aset.NamaJalan, aset.TitikAwal, aset.TitikAkhir, aset.PanjangJalan, aset.LebarJalan };
                        control.Rows.Add(obj);
                    }
                }
                catch (BappedaException exc)
                {
                    MessageBox.Show(exc.Message, "Informasi", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
        }
    }

}
