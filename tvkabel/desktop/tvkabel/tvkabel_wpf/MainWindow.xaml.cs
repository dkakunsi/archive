using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Windows.Media.Animation;
using com.gtd.tvkabel.data;
using com.gtd.tvkabel.data.entity;
using com.gtd.tvkabel.data.util;
using com.gtd.tvkabel.data.util.exception;
using CrystalDecisions.Shared;
using com.gtd.tvkabel.ui.util;

namespace tvkabel_wpf
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private enum Months { JANUARI, FEBRUARI, MARET, APRIL, MEI, JUNI, JULI, AGUSTUS, SEPTEMBER, OKTOBER, NOVEMBER, DESEMBER };
        public enum Role { admin, op };

        private DataManager service;
        private Role role;

        public MainWindow(Role role)
        {
            try
            {
                InitializeComponent();
                this.testKoneksi();
                this.role = role;
                service = DataManager.Instance;

                resetHeaderView();
                resetAllView();
            }
            catch (Exception ex)
            {
                MessageBox.Show("MESSAGE = " + ex.Message);
            }
        }

        private void testKoneksi()
        {
            try
            {
                ConnectionManager conn = new ConnectionManager();
            }
            catch (ConnectionException ex)
            {
                MessageBox.Show(ex.Message);
                Application.Current.Shutdown();
                this.Close();
            }
        }

        private void resetContainerView()
        {
            //moveOutCurrent(this.containerPelanggan);
            //moveOutCurrent(this.containerPembayaran);
            //moveOutCurrent(this.containerDatabase);
            //moveOutCurrent(this.containerLaporan);
            //moveOutCurrent(this.containerTipeIuran);
            //moveOutCurrent(this.containerTipeKontak);
            //moveOutCurrent(this.containerTipeStatus);
            //moveOutCurrent(this.containerTipeTv);
            //moveOutCurrent(this.containerLogin);
        }

        private void moveOutCurrent(Grid grid)
        {
            if (grid.Visibility == System.Windows.Visibility.Visible)
            {
                Storyboard sb = null;

                switch (grid.Name)
                {
                    case "containerPelanggan" :
                            sb = this.FindResource("containerPelangganFlyingOut") as Storyboard;
                            Storyboard.SetTarget(sb, grid);
                        break;
                    case "containerPembayaran" :
                            sb = this.FindResource("containerPembayaranFlyingOut") as Storyboard;
                            Storyboard.SetTarget(sb, grid);
                        break;
                    case "containerDatabase" :
                            sb = this.FindResource("containerDatabaseFlyingOut") as Storyboard;
                            Storyboard.SetTarget(sb, grid);
                        break;
                    case "containerLaporan" :
                        sb = this.FindResource("containerLaporanFlyingOut") as Storyboard;
                        break;
                    case "containerTipeStatus":
                        sb = this.FindResource("containerTipeStatusFlyingOut") as Storyboard;
                        Storyboard.SetTarget(sb, grid);
                        break;
                    case "containerTipeIuran":
                        sb = this.FindResource("containerTipeIuranFlyingOut") as Storyboard;
                        Storyboard.SetTarget(sb, grid);
                        break;
                    case "containerTipeTv":
                        sb = this.FindResource("containerTipeTvFlyingOut") as Storyboard;
                        Storyboard.SetTarget(sb, grid);
                        break;
                    case "containerTipeKontak":
                        sb = this.FindResource("containerTipeKontakFlyingOut") as Storyboard;
                        break;
                    case "containerLogin":
                        sb = this.FindResource("containerLoginFlyingOut") as Storyboard;
                        Storyboard.SetTarget(sb, grid);
                        break;
                }
                Storyboard.SetTarget(sb, grid);
                sb.Begin();

                if (grid.Opacity == 0)
                {
                    grid.Visibility = System.Windows.Visibility.Hidden;
                }
            }
        }

        private void resetHeaderView()
        {
            this.containerAdmin.Visibility = System.Windows.Visibility.Hidden;
            this.containerTransaksi.Visibility = System.Windows.Visibility.Hidden;
            this.containerPengaturan.Visibility = System.Windows.Visibility.Hidden;
            if (role.Equals(Role.op))
            {
                this.btnAdmin.Visibility = System.Windows.Visibility.Hidden;
                this.lbadmin.Visibility = System.Windows.Visibility.Hidden;
            }

            this.resetAllView();
        }

        private void resetAllView()
        {
            this.containerDatabase.Visibility = System.Windows.Visibility.Hidden;
            this.containerLaporan.Visibility = System.Windows.Visibility.Hidden;
            this.containerPelanggan.Visibility = System.Windows.Visibility.Hidden;
            this.containerPembayaran.Visibility = System.Windows.Visibility.Hidden;
            this.containerTipeKontak.Visibility = System.Windows.Visibility.Hidden;
            this.containerLogin.Visibility = System.Windows.Visibility.Hidden;

            resetPelangganView();
            resetPembayaranView();
            resetLaporanView();
            resetDatabaseView();
            resetLoginView();
            resetKontakView();

            reverseLaporanView();
            reversePembayaranView();
        }

        //header
        private void btnAdmin_Click(object sender, RoutedEventArgs e)
        {
            resetHeaderView();
            this.containerAdmin.Visibility = System.Windows.Visibility.Visible;
        }

        private void btnTransaksi_Click(object sender, RoutedEventArgs e)
        {
            resetHeaderView();
            this.containerTransaksi.Visibility = System.Windows.Visibility.Visible;
        }

        private void btnPengaturan_Click(object sender, RoutedEventArgs e)
        {
            resetHeaderView();
            this.containerPengaturan.Visibility = System.Windows.Visibility.Visible;
        }

        private void btnKeluar_Click(object sender, RoutedEventArgs e)
        {
            Application.Current.Shutdown();
        }

        //menu
        private void btnDatabase_Click(object sender, RoutedEventArgs e)
        {
            resetContainerView();
            resetAllView();
            this.containerDatabase.Visibility = System.Windows.Visibility.Visible;
            resetDatabaseView();
        }

        private void btnLaporan_Click(object sender, RoutedEventArgs e)
        {
            resetContainerView();
            resetAllView();
            this.containerLaporan.Visibility = System.Windows.Visibility.Visible;
            resetLaporanView();
        }

        private void btnPembayaran_Click(object sender, RoutedEventArgs e)
        {
            resetContainerView();
            resetAllView();
            this.containerPembayaran.Visibility = System.Windows.Visibility.Visible;
            resetPembayaranView();
        }

        private void btnPelanggan_Click(object sender, RoutedEventArgs e)
        {
            resetContainerView();
            resetAllView();
            this.containerPelanggan.Visibility = System.Windows.Visibility.Visible;
            resetPelangganView();
            setPelangganComboBox();
        }

        private void btnKontak_Click(object sender, RoutedEventArgs e)
        {
            resetContainerView();
            resetAllView();
            this.containerTipeKontak.Visibility = System.Windows.Visibility.Visible;
            resetKontakView();
        }

        private void btnLogin_Click(object sender, RoutedEventArgs e)
        {
            resetContainerView();
            resetAllView();
            this.containerLogin.Visibility = System.Windows.Visibility.Visible;
            resetLoginView();
        }

        //form pelanggan
        private bool pelangganUpdated;
        private bool detailUpdated;
        private IDictionary<String, String> lsKontak;
        private IDictionary<String, String> lsKontakSave;
        private IList<String> lsKontakDelete;
        private IList<Data> listKontak;

        private void resetPelangganView()
        {
            pelangganUpdated = false;
            detailUpdated = false;
            this.txtId.IsEnabled = true;
            this.txtId.Text = "";
            this.txtNama.IsEnabled = false;
            this.txtNama.Text = "";
            this.txtJumlahTv.IsEnabled = false;
            this.txtJumlahTv.Text = "";
            this.txtAlamat.IsEnabled = false;
            this.txtAlamat.Text = "";
            this.calTglMulai.IsEnabled = false;
            this.calTglMulai.SelectedDate = null;
            this.calTglMulai.SelectedDateFormat = DatePickerFormat.Short;
            this.txtDetailPendaftaran.IsEnabled = false;
            this.txtDetailPendaftaran.Text = "";
            this.txtDetailIuran.IsEnabled = false;
            this.txtDetailIuran.Text = "";
            this.rdbStatusAktif.IsEnabled = false;
            this.rdbStatusAktif.IsChecked = false;
            this.rdbStatusNonAktif.IsEnabled = false;
            this.rdbStatusNonAktif.IsChecked = false;

            this.cbPelangganTipeKontak.IsEnabled = false;
            this.cbPelangganTipeKontak.SelectedIndex = 0;
            this.txtPelangganDetailKontak.IsEnabled = false;
            this.txtPelangganDetailKontak.Text = "";

            this.btnPelangganTambah.IsEnabled = true;
            this.btnPelangganUbah.IsEnabled = false;
            this.btnPelangganSimpan.IsEnabled = false;
            this.btnPelangganHapus.IsEnabled = false;
            this.btnPelangganReset.IsEnabled = false;
            this.btnPelangganCari.IsEnabled = true;

            this.lsKontakSave = new Dictionary<String, String>();
            this.lsKontakDelete = new List<String>();

            this.setPelangganDataGrid();
        }

        private void setPelangganDataGrid()
        {
            this.dgPelanggan.IsEnabled = true;
            this.dgPelanggan.IsReadOnly = true;

            IList<Data> lsPelanggan = service.getAll(EntityList.PELANGGAN);

            if (lsPelanggan != null)
            {
                this.dgPelanggan.Items.Clear();
                foreach (Pelanggan pelanggan in lsPelanggan)
                {
                    this.dgPelanggan.Items.Add(pelanggan);

                    ((DataGridTextColumn)this.dgPelanggan.Columns[0]).Binding = new Binding("Id");
                    ((DataGridTextColumn)this.dgPelanggan.Columns[1]).Binding = new Binding("Nama");
                    ((DataGridTextColumn)this.dgPelanggan.Columns[2]).Binding = new Binding("TglMulai");
                }
            }
        }

        private void setPelangganComboBox()
        {
            int count = 0;
            listKontak = service.getAll(EntityList.TIPEKONTAK);
            if (listKontak != null)
            {
                this.cbPelangganTipeKontak.Items.Clear();
                foreach (TipeKontak kontak in listKontak)
                {
                    if (count == 0)
                        this.cbPelangganTipeKontak.Items.Add("");
                    this.cbPelangganTipeKontak.Items.Add(kontak.Detail);
                    count++;
                }
            }
        }

        private void btnPelangganTambah_Click(object sender, RoutedEventArgs e)
        {
            resetPelangganView();
            pelangganUpdated = false;
            this.txtId.IsEnabled = true;
            this.txtNama.IsEnabled = true;
            this.txtJumlahTv.IsEnabled = true;
            this.txtAlamat.IsEnabled = true;
            this.calTglMulai.IsEnabled = true;
            this.txtDetailPendaftaran.IsEnabled = true;
            this.txtDetailIuran.IsEnabled = true;
            this.rdbStatusAktif.IsEnabled = true;
            this.rdbStatusNonAktif.IsEnabled = true;

            this.cbPelangganTipeKontak.IsEnabled = true;
            this.txtPelangganDetailKontak.IsEnabled = true;

            this.btnPelangganTambah.IsEnabled = false;
            this.btnPelangganUbah.IsEnabled = false;
            this.btnPelangganSimpan.IsEnabled = true;
            this.btnPelangganHapus.IsEnabled = false;
            this.btnPelangganReset.IsEnabled = true;
            this.btnPelangganCari.IsEnabled = false;
        }

        private void btnPelangganUbah_Click(object sender, RoutedEventArgs e)
        {
            pelangganUpdated = true;
            this.txtId.IsEnabled = true;
            this.txtNama.IsEnabled = true;
            this.txtJumlahTv.IsEnabled = true;
            this.txtAlamat.IsEnabled = true;
            this.calTglMulai.IsEnabled = true;
            this.txtDetailPendaftaran.IsEnabled = true;
            this.txtDetailIuran.IsEnabled = true;
            this.rdbStatusAktif.IsEnabled = true;
            this.rdbStatusNonAktif.IsEnabled = true;

            this.cbPelangganTipeKontak.IsEnabled = true;
            this.txtPelangganDetailKontak.IsEnabled = true;

            this.btnPelangganTambah.IsEnabled = false;
            this.btnPelangganUbah.IsEnabled = false;
            this.btnPelangganSimpan.IsEnabled = true;
            this.btnPelangganHapus.IsEnabled = false;
            this.btnPelangganReset.IsEnabled = true;
            this.btnPelangganCari.IsEnabled = false;
        }

        private void btnPelangganSimpan_Click(object sender, RoutedEventArgs e)
        {
            String command;
            Pelanggan pelanggan = null;

            if (pelangganUpdated == true)
            {
                command = "update";
            }
            else
            {
                command = "add";
            }

            try
            {
                pelanggan = this.pelangganDataManager(command);
                this.pelangganKontakDataManager(command, pelanggan);
                this.DetailDataManager(command, pelanggan);
            }
            catch (DataException exc)
            {
                Console.WriteLine(exc.Message);
                Console.WriteLine(exc.StackTrace);
                MessageBox.Show(exc.Message);
            }

            this.resetPelangganView();
        }

        private void btnPelangganHapus_Click(object sender, RoutedEventArgs e)
        {
            String command = "delete";

            Pelanggan pelanggan = this.pelangganDataManager("delete");
            this.pelangganKontakDataManager(command, pelanggan);
            this.DetailDataManager(command, pelanggan);
            resetPelangganView();
        }

        private void btnPelangganReset_Click(object sender, RoutedEventArgs e)
        {
            resetPelangganView();
        }

        private void btnPelangganCari_Click(object sender, RoutedEventArgs e)
        {
            String kode = this.txtId.Text;
            Pelanggan pelanggan = (Pelanggan)service.get(EntityList.PELANGGAN, kode);

            if (pelanggan != null)
            {
                this.txtId.Text = pelanggan.Id;
                this.txtNama.Text = pelanggan.Nama;
                this.calTglMulai.SelectedDate = DateAndTime.stringToDateTime(pelanggan.TglMulai);
                this.txtJumlahTv.Text = pelanggan.JumlahTv + "";
                this.txtAlamat.Text = pelanggan.Alamat;
                if (pelanggan.Status == 1)
                {
                    this.rdbStatusAktif.IsChecked = true;
                }
                else
                {
                    this.rdbStatusNonAktif.IsChecked = true;
                }
                setPelangganData(pelanggan);
                Details details = (Details)service.get(EntityList.DETAIL, pelanggan.Id);

                if (details != null)
                {
                    this.txtDetailPendaftaran.Text = details.Pendaftaran.ToString();
                    this.txtDetailIuran.Text = details.Iuran.ToString();
                    detailUpdated = true;
                }
                else
                {
                    MessageBox.Show("Detail pembayaran belum diatur!\nSilahkan atur!");
                }
                
                this.btnPelangganTambah.IsEnabled = false;
                this.btnPelangganUbah.IsEnabled = true;
                this.btnPelangganSimpan.IsEnabled = false;
                this.btnPelangganHapus.IsEnabled = true;
                this.btnPelangganReset.IsEnabled = true;
                this.btnPelangganCari.IsEnabled = false;
            }
            else
            {
                MessageBox.Show("Tidak ada Tipe Kontak dengan kode : " + kode);
            }
        }

        private void setPelangganData(Pelanggan pelanggan)
        {
            lsKontak = new Dictionary<String, String>();
            IList<Data> daftarKontak = service.search(EntityList.KONTAK, pelanggan.Id);
            if (daftarKontak != null)
            {
                lsKontak.Clear();
                foreach (Kontak kontak in daftarKontak)
                {
                    String tipe = kontak.TipeKontak.Kode;
                    String detail = kontak.Detail;

                    lsKontak.Add(tipe, detail);
                }
            }
        }

        private String tipeToKode(String tipe, IList<Data> list)
        {
            String kode = "";

            foreach (com.gtd.tvkabel.data.Type type in list)
            {
                if (type.Detail.Equals(tipe))
                {
                    kode = type.Kode;
                    break;
                }
            }

            return kode;
        }

        private void btnPelangganKontakTambah_Click(object sender, System.Windows.RoutedEventArgs e)
        {
            String tipe = this.cbPelangganTipeKontak.SelectedItem.ToString();
            String kode = this.tipeToKode(tipe, listKontak);
            String detail = this.txtPelangganDetailKontak.Text;

            if ((this.lsKontak != null) && (this.lsKontak.ContainsKey(kode)))
            {
                this.lsKontakDelete.Add(kode);
                this.lsKontak.Remove(kode);
            }

            try
            {
                this.lsKontakSave.Add(kode, detail);
            }
            catch (ArgumentException exc)
            {
                this.lsKontakSave.Remove(kode);
                this.lsKontakSave.Add(kode, detail);
                Console.WriteLine(exc.Message);
            }


            this.cbPelangganTipeKontak.SelectedIndex = 0;
            this.txtPelangganDetailKontak.Text = "";
        }

        private void btnPelangganKontakHapus_Click(object sender, System.Windows.RoutedEventArgs e)
        {
            String tipe = this.cbPelangganTipeKontak.SelectedItem.ToString();
            String kode = this.tipeToKode(tipe, listKontak);

            if (this.lsKontak != null)
            {
                lsKontakDelete.Add(kode);
                lsKontakSave.Remove(kode);
                lsKontak.Remove(kode);
            }

            this.cbPelangganTipeKontak.SelectedIndex = 0;
            this.txtPelangganDetailKontak.Text = "";
        }

        private void cbPelangganTipeKontak_SelectionChanged(object sender, System.Windows.Controls.SelectionChangedEventArgs e)
        {
            try
            {
                if (this.cbPelangganTipeKontak.SelectedIndex != 0)
                {
                    String selected = this.cbPelangganTipeKontak.SelectedItem.ToString();
                    String kode = this.tipeToKode(selected, listKontak);

                    foreach (System.Collections.Generic.KeyValuePair<String, String> data in lsKontak)
                    {
                        if (data.Key.Equals(kode))
                        {
                            this.txtPelangganDetailKontak.Text = data.Value;
                            break;
                        }
                        else
                        {
                            this.txtPelangganDetailKontak.Text = "";
                        }
                    }
                }
                else
                {
                    this.txtPelangganDetailKontak.Text = "";
                }
            }
            catch (NullReferenceException exc)
            {
                Console.WriteLine(exc.Message);
            }
        }

        private void rdbStatusAktif_Checked(object sender, System.Windows.RoutedEventArgs e)
        {
            this.rdbStatusAktif.IsChecked = true;
            this.rdbStatusNonAktif.IsChecked = false;
        }

        private void rdbStatusNonAktif_Checked(object sender, System.Windows.RoutedEventArgs e)
        {
            this.rdbStatusAktif.IsChecked = false;
            this.rdbStatusNonAktif.IsChecked = true;
        }

        private Pelanggan pelangganDataManager(String command)
        {
            Pelanggan data = new Pelanggan();
            data.Id = this.txtId.Text;
            data.Nama = this.txtNama.Text;
            data.TglMulai = DateAndTime.dateTimeToString(this.calTglMulai.SelectedDate.Value);
            data.JumlahTv = Int16.Parse(this.txtJumlahTv.Text);
            data.Alamat = this.txtAlamat.Text;
            data.Status = (bool)this.rdbStatusAktif.IsChecked ? (short)1 : (short)0;

            try
            {
                dataManager(data, command);
                return data;
            }
            catch (DataException exc)
            {
                throw exc;
            }
        }

        private void pelangganKontakDataManager(String command, Pelanggan pelanggan)
        {
            try
            {
                if (!command.Equals("delete"))
                {
                    foreach (String kode in this.lsKontakDelete)
                    {
                        Kontak data = new Kontak();

                        TipeKontak kontak = (TipeKontak)service.get(EntityList.TIPEKONTAK, kode);

                        data.Pelanggan = pelanggan;
                        data.TipeKontak = kontak;

                        dataManager(data, "delete");
                    }

                    foreach (System.Collections.Generic.KeyValuePair<String, String> kode in this.lsKontakSave)
                    {
                        Kontak data = new Kontak();

                        TipeKontak kontak = (TipeKontak)service.get(EntityList.TIPEKONTAK, kode.Key);

                        data.Pelanggan = pelanggan;
                        data.TipeKontak = kontak;
                        data.Detail = kode.Value;

                        dataManager(data, "add");
                    }
                }
                else
                {
                    foreach (Data data in service.getAll(EntityList.TIPEKONTAK))
                    {
                        Kontak kontak = new Kontak();
                        kontak.Pelanggan = pelanggan;
                        kontak.TipeKontak = (TipeKontak)data;

                        dataManager(kontak, command);
                    }
                }
            }
            catch (DataException exc)
            {
                throw exc;
            }
        }

        private void DetailDataManager(String command, Pelanggan pelanggan)
        {
            if ((!command.Equals("delete")) && (detailUpdated == false))
            {
                command = "add";
            }

            Details data = new Details();

            data.Pelanggan = pelanggan;
            data.Pendaftaran = Double.Parse(this.txtDetailPendaftaran.Text);
            data.Iuran = Double.Parse(this.txtDetailIuran.Text);

            dataManager(data, command);
        }

        //form pembayaran
        private void resetPembayaranView()
        {
            this.txtIdPelanggan.IsEnabled = true;
            this.txtNamaPelanggan.IsEnabled = false;
            this.txtKeteranganPembayaran.IsEnabled = false;
            this.cbTahun.IsEnabled = false;
            this.cbBulan.IsEnabled = false;
            this.txtBiaya.IsEnabled = false;
            this.txtJumlahBayar.IsEnabled = false;
            this.txtKembalian.IsEnabled = false;
            this.btnCariPelanggan.IsEnabled = true;
            this.btnHitung.IsEnabled = false;

            this.txtIdPelanggan.Text = "";
            this.txtNamaPelanggan.Text = "";
            this.txtKeteranganPembayaran.Text = "";
            this.cbTahun.SelectedIndex = 0;
            this.cbBulan.SelectedIndex = 0;
            this.txtBiaya.Text = "";
            this.txtJumlahBayar.Text = "";
            this.txtKembalian.Text = "";
            this.dgPembayaran.Items.Clear();

            this.btnPembayaranCetak.IsEnabled = false;
            this.btnPembayaranReset.IsEnabled = false;
            this.gridReport.Visibility = System.Windows.Visibility.Visible;
        }

        private void reversePembayaranView()
        {
            this.gridReport.Visibility = System.Windows.Visibility.Hidden;
        }

        private void set()
        {
            this.txtIdPelanggan.IsEnabled = true;
            this.txtNamaPelanggan.IsEnabled = false;
            this.txtKeteranganPembayaran.IsEnabled = true;
            this.cbTahun.IsEnabled = true;
            this.cbBulan.IsEnabled = true;
            this.txtBiaya.IsEnabled = false;
            this.txtJumlahBayar.IsEnabled = true;
            this.txtKembalian.IsEnabled = false;

            this.btnCariPelanggan.IsEnabled = false;
            this.btnHitung.IsEnabled = true;
            this.btnPembayaranCetak.IsEnabled = false;
            this.btnPembayaranReset.IsEnabled = true;
        }

        private void setPembayaranDataGrid(IList<Data> lsPembayaran)
        {
            this.dgPembayaran.IsEnabled = true;
            this.dgPembayaran.IsReadOnly = true;

            this.dgPembayaran.Items.Clear();
            foreach (Pembayaran pembayaran in lsPembayaran)
            {
                this.dgPembayaran.Items.Add(pembayaran);

                ((DataGridTextColumn)this.dgPembayaran.Columns[0]).Binding = new Binding("Tahun");
                ((DataGridTextColumn)this.dgPembayaran.Columns[1]).Binding = new Binding("Bulan");
                ((DataGridTextColumn)this.dgPembayaran.Columns[2]).Binding = new Binding("TanggalBayar");
            }
        }

        private void btnPembayaranReset_Click(object sender, RoutedEventArgs e)
        {
            resetPembayaranView();
        }

        private void btnCariPelanggan_Click(object sender, System.Windows.RoutedEventArgs e)
        {
            String kode = this.txtIdPelanggan.Text;
            Pelanggan pelanggan = (Pelanggan)service.get(EntityList.PELANGGAN, kode);

            if (pelanggan != null)
            {
                Details details = (Details)service.get(EntityList.DETAIL, pelanggan.Id);
                this.txtNamaPelanggan.Text = pelanggan.Nama;
                this.txtBiaya.Text = details.Iuran.ToString();

                IList<Data> lsPembayaran = service.search(EntityList.PEMBAYARAN, pelanggan.Id);
                if (lsPembayaran != null)
                {
                    this.setPembayaranDataGrid(lsPembayaran);
                }
                this.set();
            }
            else
            {
                MessageBox.Show("Tidak ada pelanggan dengan id '" + kode + "'");
            }
        }

        private void btnHitung_Click(object sender, System.Windows.RoutedEventArgs e)
        {
            try
            {
                double jumlahBayar = Double.Parse(this.txtJumlahBayar.Text);
                double biaya = Double.Parse(this.txtBiaya.Text);
                double kembalian = jumlahBayar - biaya;

                if (kembalian >= 0)
                {
                    this.txtKembalian.Text = kembalian + "";
                    this.btnPembayaranCetak.IsEnabled = true;
                }
                else
                {
                    throw new FormatException();
                }
            }
            catch (FormatException exc)
            {
                MessageBox.Show("Periksa kembali input Jumlah Bayar");
            }

        }

        private void btnPembayaranOk_Click(object sender, System.Windows.RoutedEventArgs e)
        {
            try
            {
                Pembayaran pembayaran = new Pembayaran();

                pembayaran.Pelanggan = (Pelanggan)service.get(EntityList.PELANGGAN, this.txtIdPelanggan.Text);
                pembayaran.Tahun = Int32.Parse(this.cbTahun.SelectedItem.ToString());
                pembayaran.Bulan = DateAndTime.monthToNumber(this.cbBulan.SelectedItem.ToString());
                pembayaran.Biaya = Double.Parse(this.txtBiaya.Text);
                pembayaran.TanggalBayar = DateAndTime.dateTimeToString(DateTime.Now);
                pembayaran.Keterangan = this.txtKeteranganPembayaran.Text;

                service.add(pembayaran);
                this.resetPembayaranView();
                
                Dictionary<String, String> values = new Dictionary<String, String>();
                values.Add("nomor",pembayaran.getNomorPembayaran(pembayaran.Tahun, pembayaran.Bulan));
                values.Add("id_pelanggan", pembayaran.Pelanggan.Id);
                values.Add("nama_pelanggan", pembayaran.Pelanggan.Nama);
                values.Add("biaya", pembayaran.Biaya.ToString());
                values.Add("bulan_tagihan", DateAndTime.numberToMonth(pembayaran.Bulan) + " " + pembayaran.Tahun);

                //this.configureCrystalReport(values);
                Report report = new Report(this.crystalReportViewer, Report.JenisReport.STRUK);
                report.setParameterSingleValue(values);
            }
            catch (Exception exc)
            {
                Console.WriteLine(exc.Message);
            }
        }

        //form tipe kontak
        private bool tipeKontakUpdated;

        private void resetKontakView()
        {
            tipeKontakUpdated = false;
            this.txtKodeKontak.IsEnabled = true;
            this.txtDetailKontak.IsEnabled = false;

            this.txtKodeKontak.Text = "";
            this.txtDetailKontak.Text = "";

            this.btnKontakTambah.IsEnabled = true;
            this.btnKontakUbah.IsEnabled = false;
            this.btnKontakSimpan.IsEnabled = false;
            this.btnKontakHapus.IsEnabled = false;
            this.btnKontakReset.IsEnabled = false;
            this.btnKontakCari.IsEnabled = true;

            this.setKontakDataGrid();
        }

        private void setKontakDataGrid()
        {
            this.dgTipeKontak.IsEnabled = true;
            this.dgTipeKontak.IsReadOnly = true;

            IList<Data> lsTipeKontak = service.getAll(EntityList.TIPEKONTAK);

            if (lsTipeKontak != null)
            {
                this.dgTipeKontak.Items.Clear();
                foreach (TipeKontak tipeKontak in lsTipeKontak)
                {
                    this.dgTipeKontak.Items.Add(tipeKontak);

                    ((DataGridTextColumn)this.dgTipeKontak.Columns[0]).Binding = new Binding("Kode");
                    ((DataGridTextColumn)this.dgTipeKontak.Columns[1]).Binding = new Binding("Detail");
                }
            }
        }

        private void btnKontakTambah_Click(object sender, RoutedEventArgs e)
        {
            resetKontakView();
            tipeKontakUpdated = false;
            this.txtKodeKontak.IsEnabled = true;
            this.txtDetailKontak.IsEnabled = true;

            this.btnKontakTambah.IsEnabled = false;
            this.btnKontakUbah.IsEnabled = false;
            this.btnKontakSimpan.IsEnabled = true;
            this.btnKontakHapus.IsEnabled = false;
            this.btnKontakReset.IsEnabled = true;
            this.btnKontakCari.IsEnabled = false;
        }

        private void btnKontakUbah_Click(object sender, RoutedEventArgs e)
        {
            tipeKontakUpdated = true;
            this.txtKodeKontak.IsEnabled = true;
            this.txtDetailKontak.IsEnabled = true;

            this.btnKontakTambah.IsEnabled = false;
            this.btnKontakUbah.IsEnabled = false;
            this.btnKontakSimpan.IsEnabled = true;
            this.btnKontakHapus.IsEnabled = false;
            this.btnKontakReset.IsEnabled = true;
            this.btnKontakCari.IsEnabled = false;
        }

        private void btnKontakSimpan_Click(object sender, RoutedEventArgs e)
        {
            if (tipeKontakUpdated == true)
            {
                KontakDataManager("update");
            }
            else
            {
                KontakDataManager("add");
            }
        }

        private void btnKontakHapus_Click(object sender, RoutedEventArgs e)
        {
            KontakDataManager("delete");
        }

        private void btnKontakReset_Click(object sender, RoutedEventArgs e)
        {
            resetKontakView();
        }

        private void btnKontakCari_Click(object sender, RoutedEventArgs e)
        {
            String kode = this.txtKodeKontak.Text;
            TipeKontak kontak = (TipeKontak)service.get(EntityList.TIPEKONTAK, kode);

            if (kontak != null)
            {
                this.txtDetailKontak.Text = kontak.Detail;
                this.btnKontakTambah.IsEnabled = false;
                this.btnKontakUbah.IsEnabled = true;
                this.btnKontakSimpan.IsEnabled = false;
                this.btnKontakHapus.IsEnabled = true;
                this.btnKontakReset.IsEnabled = true;
                this.btnKontakCari.IsEnabled = false;
            }
            else
            {
                MessageBox.Show("Tidak ada Tipe Kontak dengan kode : " + kode);
            }
        }

        private void KontakDataManager(String command)
        {
            TipeKontak data = new TipeKontak();

            data.Kode = this.txtKodeKontak.Text;
            data.Detail = this.txtDetailKontak.Text;

            try
            {
                switch (command)
                {
                    case "add": service.add(data);
                        break;
                    case "update": service.update(data);
                        break;
                    case "delete": service.delete(data);
                        break;
                    default:
                        break;
                }
            }
            catch (DataException exc)
            {
                Console.WriteLine(exc.Message);
                MessageBox.Show(exc.Message);
            }

            resetKontakView();
        }

        //form login
        private bool loginUpdated;

        private void resetLoginView()
        {
            loginUpdated = false;
            this.txtUsernameLogin.IsEnabled = true;
            this.txtPasswordLogin.IsEnabled = false;
            this.txtNamaLogin.IsEnabled = false;
            this.cbRoleLogin.IsEnabled= false;

            this.txtUsernameLogin.Text = "";
            this.txtPasswordLogin.Text = "";
            this.txtNamaLogin.Text = "";

            this.cbRoleLogin.Items.Clear();
            this.cbRoleLogin.Items.Add("");
            this.cbRoleLogin.Items.Add("Admin");
            this.cbRoleLogin.Items.Add("Operator");
            this.cbRoleLogin.SelectedIndex = 0;

            this.btnLoginTambah.IsEnabled = true;
            this.btnLoginUbah.IsEnabled = false;
            this.btnLoginSimpan.IsEnabled = false;
            this.btnLoginHapus.IsEnabled = false;
            this.btnLoginReset.IsEnabled = false;
            this.btnLoginCari.IsEnabled = true;
        }

        private void btnLoginCari_Click(object sender, RoutedEventArgs e)
        {
            String kode = this.txtUsernameLogin.Text;
            com.gtd.tvkabel.data.entity.Login login = (com.gtd.tvkabel.data.entity.Login)service.get(EntityList.LOGIN, kode);

            if (login != null)
            {
                this.txtPasswordLogin.Text = login.Password;
                this.txtNamaLogin.Text = login.Nama;
                this.cbRoleLogin.SelectedItem = login.Role;
                this.btnLoginTambah.IsEnabled = false;
                this.btnLoginUbah.IsEnabled = true;
                this.btnLoginSimpan.IsEnabled = false;
                this.btnLoginHapus.IsEnabled = true;
                this.btnLoginReset.IsEnabled = true;
                this.btnLoginCari.IsEnabled = false;
            }
            else
            {
                MessageBox.Show("Tidak ada Akun dengan username : " + kode);
            }
        }

        private void btnLoginTambah_Click(object sender, RoutedEventArgs e)
        {
            resetLoginView();
            loginUpdated = false;
            this.txtUsernameLogin.IsEnabled = true;
            this.txtPasswordLogin.IsEnabled = true;
            this.txtNamaLogin.IsEnabled = true;
            this.cbRoleLogin.IsEnabled = true;

            this.btnLoginTambah.IsEnabled = false;
            this.btnLoginUbah.IsEnabled = false;
            this.btnLoginSimpan.IsEnabled = true;
            this.btnLoginHapus.IsEnabled = false;
            this.btnLoginReset.IsEnabled = true;
            this.btnLoginCari.IsEnabled = false;
        }

        private void btnLoginUbah_Click(object sender, RoutedEventArgs e)
        {
            loginUpdated = true;
            this.txtUsernameLogin.IsEnabled = true;
            this.txtPasswordLogin.IsEnabled = true;
            this.txtNamaLogin.IsEnabled = true;
            this.cbRoleLogin.IsEnabled = true;

            this.btnLoginTambah.IsEnabled = false;
            this.btnLoginUbah.IsEnabled = false;
            this.btnLoginSimpan.IsEnabled = true;
            this.btnLoginHapus.IsEnabled = false;
            this.btnLoginReset.IsEnabled = true;
            this.btnLoginCari.IsEnabled = false;
        }

        private void btnLoginHapus_Click(object sender, RoutedEventArgs e)
        {
            loginDataManager("delete");
        }

        private void btnLoginReset_Click(object sender, RoutedEventArgs e)
        {
            resetLoginView();
        }

        private void btnLoginSimpan_Click(object sender, RoutedEventArgs e)
        {
            if (loginUpdated == true)
            {
                loginDataManager("update");
            }
            else
            {
                loginDataManager("add");
            }

            resetLoginView();
        }

        private void loginDataManager(String command)
        {
            com.gtd.tvkabel.data.entity.Login data = new com.gtd.tvkabel.data.entity.Login();

            data.Username = this.txtUsernameLogin.Text;
            data.Password = this.txtPasswordLogin.Text;
            data.Nama = this.txtNamaLogin.Text;
            data.Role = this.cbRoleLogin.SelectedItem.ToString();

            try
            {
                switch (command)
                {
                    case "add": service.add(data);
                        break;
                    case "update": service.update(data);
                        break;
                    case "delete": service.delete(data);
                        break;
                    default:
                        break;
                }
            }
            catch (DataException exc)
            {
                Console.WriteLine(exc.Message);
                MessageBox.Show(exc.Message);
            }

            resetLoginView();
        }

        //form database
        private bool databaseUpdated;

        private void resetDatabaseView()
        {
            databaseUpdated = false;
            this.txtDbServer.IsEnabled = false;
            this.txtDbCredUsername.IsEnabled = false;
            this.txtDbCredPassword.IsEnabled = false;
            this.txtDbDatabase.IsEnabled = false;
            this.txtDbUsername.IsEnabled = false;
            this.txtDbPassword.IsEnabled = false;

            this.txtDbServer.Text = "";
            this.txtDbCredUsername.Text = "";
            this.txtDbCredPassword.Text = "";
            this.txtDbDatabase.Text = "";
            this.txtDbUsername.Text = "";
            this.txtDbPassword.Text = "";

            this.btnDatabaseSimpan.IsEnabled = false;
            this.btnDatabaseUbah.IsEnabled = true;
            this.btnDatabaseReset.IsEnabled = false;

            ServerSetting.read(ServerSetting.DefaultSetting);

            this.txtDbServer.Text = ServerSetting.SERVER;
            this.txtDbCredUsername.Text = ServerSetting.CREDUSERNAME;
            this.txtDbCredPassword.Text = ServerSetting.CREDPASSWORD;
            this.txtDbDatabase.Text = ServerSetting.DATABASE;
            this.txtDbUsername.Text = ServerSetting.DBUSERNAME;
            this.txtDbPassword.Text = ServerSetting.DBPASSWORD;
        }

        private void btnDatabaseSimpan_Click(object sender, RoutedEventArgs e)
        {
            if (databaseUpdated == true)
            {
                ServerSetting.SERVER = this.txtDbServer.Text;
                ServerSetting.CREDUSERNAME = this.txtDbCredUsername.Text;
                ServerSetting.CREDPASSWORD = this.txtDbCredPassword.Text;
                ServerSetting.DATABASE = this.txtDbDatabase.Text;
                ServerSetting.DBUSERNAME = this.txtDbUsername.Text;
                ServerSetting.DBPASSWORD = this.txtDbPassword.Text;

                ServerSetting.write(ServerSetting.DefaultSetting);
            }
            else
            {
            }

            resetDatabaseView();
        }

        private void btnDatabaseUbah_Click(object sender, RoutedEventArgs e)
        {
            resetDatabaseView();
            databaseUpdated = true;
            this.txtDbServer.IsEnabled = true;
            this.txtDbCredUsername.IsEnabled = true;
            this.txtDbCredPassword.IsEnabled = true;
            this.txtDbDatabase.IsEnabled = true;
            this.txtDbUsername.IsEnabled = true;
            this.txtDbPassword.IsEnabled = true;

            this.btnDatabaseSimpan.IsEnabled = true;
            this.btnDatabaseUbah.IsEnabled = false;
            this.btnDatabaseReset.IsEnabled = true;
        }

        private void btnDatabaseReset_Click(object sender, RoutedEventArgs e)
        {
            resetDatabaseView();
        }

        //laporan
        private void resetLaporanView()
        {
            this.cbLaporan.IsEnabled = true;
            this.btnLaporanOK.IsEnabled = false;
            this.gridLaporanReport.Visibility = System.Windows.Visibility.Visible;
        }

        private void reverseLaporanView()
        {
            this.gridLaporanReport.Visibility = System.Windows.Visibility.Hidden;
        }

        private void cbLaporan_SelectionChanged(object sender, System.Windows.Controls.SelectionChangedEventArgs e)
        {
            String jenisLaporan = this.cbLaporan.SelectedItem.ToString();

            resetLaporanView();
            if (jenisLaporan.Equals("Bulanan"))
            {
                this.cbStatusPelanggan.Visibility = System.Windows.Visibility.Hidden;
                this.cbLaporanBulan.Visibility = System.Windows.Visibility.Visible;
                this.cbLaporanTahun.Visibility = System.Windows.Visibility.Visible;
                this.lblStatus.Visibility = System.Windows.Visibility.Hidden;
                this.lblBulan.Visibility = System.Windows.Visibility.Visible;
                this.lblTahun.Visibility = System.Windows.Visibility.Visible;
            }
            else if (jenisLaporan.Equals("Pelanggan"))
            {
                this.cbStatusPelanggan.Visibility = System.Windows.Visibility.Visible;
                this.cbLaporanBulan.Visibility = System.Windows.Visibility.Hidden;
                this.cbLaporanTahun.Visibility = System.Windows.Visibility.Hidden;
                this.lblStatus.Visibility = System.Windows.Visibility.Visible;
                this.lblBulan.Visibility = System.Windows.Visibility.Hidden;
                this.lblTahun.Visibility = System.Windows.Visibility.Hidden;
            }
            else
            {
                this.cbStatusPelanggan.Visibility = System.Windows.Visibility.Hidden;
                this.cbLaporanBulan.Visibility = System.Windows.Visibility.Hidden;
                this.cbLaporanTahun.Visibility = System.Windows.Visibility.Hidden;
                this.lblStatus.Visibility = System.Windows.Visibility.Hidden;
                this.lblBulan.Visibility = System.Windows.Visibility.Hidden;
                this.lblTahun.Visibility = System.Windows.Visibility.Hidden;
            }
            this.btnLaporanOK.IsEnabled = true;
        }

        private void btnLaporanOK_Click(object sender, System.Windows.RoutedEventArgs e)
        {
            String jenisLaporan = this.cbLaporan.SelectedItem.ToString();
            Report report = null;

            try
            {
                switch (jenisLaporan)
                {
                    case "Bulanan":
                        ArrayList arrayList = new ArrayList();
                        arrayList.Add(Int32.Parse(this.cbLaporanTahun.SelectedItem.ToString()));
                        arrayList.Add(DateAndTime.monthToNumber(this.cbLaporanBulan.SelectedItem.ToString()));
                        IList<Data> lsBulanan = service.search(EntityList.PEMBAYARAN, arrayList);

                        if ((lsBulanan != null) && (lsBulanan.Count > 0))
                        {
                            report = new Report(this.laporanReportViewer, Report.JenisReport.BULANAN);
                            report.setParameterMultipleValues(lsBulanan);
                        }
                        else
                        {
                            throw new NullReferenceException();
                        }
                        break;
                    case "Tunggakan":
                        IList<Data> lsTunggakan = new List<Data>();

                        foreach (Pelanggan pelanggan in service.getAll(EntityList.PELANGGAN))
                        {
                            if (pelanggan.Status == 1)
                            {
                                int tunggakan = Report.Tunggakan.hitungTunggakan(pelanggan);
                                if (tunggakan > 0)
                                {
                                    lsTunggakan.Add(pelanggan);
                                }
                            }
                        }

                        if (lsTunggakan.Count > 0)
                        {
                            report = new Report(this.laporanReportViewer, Report.JenisReport.TUNGGAKAN);
                            report.setParameterMultipleValues(Report.Tunggakan.sortTunggakan(lsTunggakan));
                        }
                        else
                        {
                            MessageBox.Show(lsTunggakan.Count + "");
                            throw new NullReferenceException();
                        }
                        break;
                    case "Pelanggan":
                        IList<Data> lsPelanggan = null;

                        if (this.cbStatusPelanggan.SelectedItem.ToString().Equals("Non-Aktif"))
                        {
                            lsPelanggan = service.search(EntityList.PELANGGAN, 0);
                        }
                        else
                        {
                            lsPelanggan = service.search(EntityList.PELANGGAN, 1);
                        }

                        if ((lsPelanggan != null) && (lsPelanggan.Count > 0))
                        {
                            report = new Report(this.laporanReportViewer, Report.JenisReport.PELANGGAN_AKTIF);
                            report.setParameterMultipleValues(lsPelanggan);
                        }
                        break;
                }
            }
            catch (NullReferenceException ex)
            {
                report = new Report(this.laporanReportViewer, Report.JenisReport.NONE);
            }
        }

        //utility
        private void dataManager(Data data, String command)
        {
            try
            {
                switch (command)
                {
                    case "add": service.add(data);
                        break;
                    case "update": service.update(data);
                        break;
                    case "delete": service.delete(data);
                        break;
                    default:
                        break;
                }
            }
            catch (DataException exc)
            {
                throw exc;
            }
        }
    }
}
