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
using com.gtd.tvkabel.data.dao;
using com.gtd.tvkabel.data.service;
using tvkabel_wpf.BulananDataSetTableAdapters;

namespace tvkabel_wpf
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private enum Months { JANUARI, FEBRUARI, MARET, APRIL, MEI, JUNI, JULI, AGUSTUS, SEPTEMBER, OKTOBER, NOVEMBER, DESEMBER };
        public enum Role { admin, op };

        private Role role;
        public PelangganSupport PelangganSupport { get; set; }
        public PembayaranSupport PembayaranSupport { get; set; }

        private KontakService kontakService;
        private TipeKontakService tipeKontakService;
        private DetailService detailService;
        private TunggakanService tunggakanService;
        private LoginService loginService;

        public MainWindow(Role role)
        {
            PelangganSupport = new PelangganSupport(this);
            PembayaranSupport = new PembayaranSupport(this);

            kontakService = new KontakService();
            tipeKontakService = new TipeKontakService();
            detailService = new DetailService();
            tunggakanService = new TunggakanService();
            loginService = new LoginService();

            try
            {
                InitializeComponent();
                this.testKoneksi();
                this.role = role;

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

            PelangganSupport.ResetPelanggan();
            PembayaranSupport.Reset();

            resetLaporanView();
            resetDatabaseView();
            resetLoginView();
            resetKontakView();

            reverseLaporanView();
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
            PembayaranSupport.Reset();
        }

        private void btnPelanggan_Click(object sender, RoutedEventArgs e)
        {
            resetContainerView();
            resetAllView();
            this.containerPelanggan.Visibility = System.Windows.Visibility.Visible;
            PelangganSupport.ResetPelanggan();
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
        private int keteranganEnabled;

        private void TambahPelanggan(object sender, RoutedEventArgs e)
        {
            PelangganSupport.ResetView();
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
            keteranganEnabled = 1;
        }

        private void UbahPelanggan(object sender, RoutedEventArgs e)
        {
            this.txtId.IsEnabled = true;
            this.txtNama.IsEnabled = true;
            this.txtJumlahTv.IsEnabled = true;
            this.txtAlamat.IsEnabled = true;
            this.calTglMulai.IsEnabled = true;
            this.txtDetailPendaftaran.IsEnabled = true;
            this.txtDetailIuran.IsEnabled = true;
            this.rdbStatusAktif.IsEnabled = true;
            this.rdbStatusNonAktif.IsEnabled = true;
            this.txtKeterangan.IsEnabled = true;
            this.cbPelangganTipeKontak.IsEnabled = true;
            this.txtPelangganDetailKontak.IsEnabled = true;
            this.btnPelangganTambah.IsEnabled = false;
            this.btnPelangganUbah.IsEnabled = false;
            this.btnPelangganSimpan.IsEnabled = true;
            this.btnPelangganHapus.IsEnabled = false;
            this.btnPelangganReset.IsEnabled = true;
            this.btnPelangganCari.IsEnabled = false;
            keteranganEnabled = 1;
        }

        private void SimpanPelanggan(object sender, RoutedEventArgs e)
        {
            try
            {
                PelangganSupport.SimpanPelanggan();
            }
            catch (DataException E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);

                MessageBox.Show(E.Message);
            }
            catch (Exception E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);

                MessageBox.Show("Maaf! Gagal menyimpan data");
            }
            finally
            {
                PelangganSupport.Pelanggan = null;
            }
        }

        private void HapusPelanggan(object sender, RoutedEventArgs e)
        {
            try
            {
                PelangganSupport.HapusPelanggan();
            }
            catch (DataException E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);

                MessageBox.Show(E.Message);
            }
            catch (Exception E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);

                MessageBox.Show("Maaf! Gagal menghapus data");
            }
        }

        private void ResetPelanggan(object sender, RoutedEventArgs e)
        {
            try
            {
                PelangganSupport.ResetPelanggan();
                keteranganEnabled = 0;
            }
            catch (DataException E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);
            }
            catch (Exception E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);
            }
            finally
            {
                PelangganSupport.ResetView();
            }
        }

        private void CariPelanggan(object sender, RoutedEventArgs e)
        {
            try
            {
                PelangganSupport.CariPelanggan();
            }
            catch (DataException E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);

                MessageBox.Show(E.Message);
            }
            catch (Exception E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);

                MessageBox.Show("Maaf! Gagal mencari data");
            }
        }

        private void CariPelanggan(object sender, KeyEventArgs e)
        {
            try
            {
                String keyString = e.Key.ToString();

                if (keyString.Equals("Return"))
                {
                    PelangganSupport.CariPelanggan();
                }
            }
            catch (DataException E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);

                MessageBox.Show(E.Message);
            }
            catch (Exception E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);

                MessageBox.Show("Maaf! Gagal mencari data");
            }
        }

        private void CariNama(object sender, TextChangedEventArgs e)
        {
            try
            {
                PelangganSupport.CariNama();
            }
            catch (DataException E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);
            }
            catch (Exception E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);

                MessageBox.Show("Maaf! Gagal mencari data" + "\n" + E.Message + "\n" + E.StackTrace);
            }
        }

        private void TambahKontakPelanggan(object sender, System.Windows.RoutedEventArgs e)
        {
            String tipe = "", kode = "", detail = "";
            try
            {
                tipe = this.cbPelangganTipeKontak.SelectedItem.ToString();
                kode = PelangganSupport.TipeToKode(tipe, PelangganSupport.listKontak);
                detail = this.txtPelangganDetailKontak.Text;

                if ((PelangganSupport.daftarKontak != null) && (PelangganSupport.daftarKontak.ContainsKey(kode)))
                {
                    PelangganSupport.listKontakDelete.Add(kode);
                    PelangganSupport.daftarKontak.Remove(kode);
                }

                PelangganSupport.daftarKontakSave.Add(kode, detail);


                this.cbPelangganTipeKontak.SelectedIndex = 0;
                this.txtPelangganDetailKontak.Text = "";
            }
            catch (ArgumentException exc)
            {
                PelangganSupport.daftarKontakSave.Remove(kode);
                PelangganSupport.daftarKontakSave.Add(kode, detail);

                Console.WriteLine("INFO : " + exc.Message);
            }
            catch (Exception E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);

                MessageBox.Show("Maaf! Gagal menambah kontak");
            }
        }

        private void HapusKontakPelanggan(object sender, System.Windows.RoutedEventArgs e)
        {
            try
            {
                String tipe = this.cbPelangganTipeKontak.SelectedItem.ToString();
                String kode = PelangganSupport.TipeToKode(tipe, PelangganSupport.listKontak);

                if (PelangganSupport.daftarKontak != null)
                {
                    PelangganSupport.listKontakDelete.Add(kode);
                    PelangganSupport.daftarKontakSave.Remove(kode);
                    PelangganSupport.daftarKontak.Remove(kode);
                }

                this.txtPelangganDetailKontak.Text = "";
            }
            catch (Exception E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);

                MessageBox.Show("Maaf! Gagal menghapus kontak");
            }
        }

        private void PilihTipeKontakPelanggan(object sender, System.Windows.Controls.SelectionChangedEventArgs e)
        {
            try
            {
                if (this.cbPelangganTipeKontak.SelectedIndex != 0)
                {
                    String selected = this.cbPelangganTipeKontak.SelectedItem.ToString();
                    String kode = PelangganSupport.TipeToKode(selected, PelangganSupport.listKontak);

                    foreach (System.Collections.Generic.KeyValuePair<String, String> data in PelangganSupport.daftarKontak)
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
            catch (NullReferenceException E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);
            }
        }

        private void PelangganAktif(object sender, System.Windows.RoutedEventArgs e)
        {
            //this.rdbStatusAktif.IsChecked = true;
            //this.rdbStatusNonAktif.IsChecked = false;
            this.txtKeterangan.Text = "";
            this.txtKeterangan.Visibility = System.Windows.Visibility.Hidden;
            this.lblKeterangan.Visibility = System.Windows.Visibility.Hidden;
        }

        private void PelangganNonAktif(object sender, System.Windows.RoutedEventArgs e)
        {
            //this.rdbStatusAktif.IsChecked = false;
            //this.rdbStatusNonAktif.IsChecked = true;
            this.txtKeterangan.Text = "";
            if (keteranganEnabled == 1)
            {
                this.txtKeterangan.IsEnabled = true;
            }
            else
            {
                this.txtKeterangan.IsEnabled = false;
            }
            this.txtKeterangan.Visibility = System.Windows.Visibility.Visible;
            this.lblKeterangan.Visibility = System.Windows.Visibility.Visible;
        }

        private void TableClicked(object sender, MouseButtonEventArgs e)
        {
            try
            {
                PelangganSupport.Pelanggan = (Pelanggan)this.dgPelanggan.SelectedItem;

                PelangganSupport.SetPelanggan();
                PelangganSupport.SetPelangganDetail();
                PelangganSupport.SetPelangganKontak();
            }
            catch (DataException E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);

                MessageBox.Show(E.Message);
            }
            catch (Exception E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);

                MessageBox.Show("Maaf! Gagal mencari data");
            }
            finally
            {
                this.btnPelangganTambah.IsEnabled = false;
                this.btnPelangganUbah.IsEnabled = true;
                this.btnPelangganSimpan.IsEnabled = false;
                this.btnPelangganHapus.IsEnabled = true;
                this.btnPelangganReset.IsEnabled = true;
                this.btnPelangganCari.IsEnabled = false;
            }
        }

        //form pembayaran
        private void ResetPembayaran(object sender, RoutedEventArgs e)
        {
            try
            {
                PembayaranSupport.Reset();
                PembayaranSupport.ResetView();

                this.txtIdPelanggan.Text = "";
                this.txtNamaPelanggan.Text = "";
                this.txtIuranBulanan.Text = "";
            }
            catch (DataException E)
            {
                PembayaranSupport.ResetView();

                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);
            }
            catch (Exception E)
            {
                PembayaranSupport.ResetView();

                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);
            }
        }

        private void CariPelangganPembayaran(object sender, System.Windows.RoutedEventArgs e)
        {
            try
            {
                PembayaranSupport.CariPelangganPembayaran();
            }
            catch (DataException E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);

                MessageBox.Show(E.Message);
            }
            catch (Exception E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);

                MessageBox.Show("Maaf! Gagal mencari data");
            }
        }

        private void CariPelangganPembayaran(object sender, KeyEventArgs e)
        {
            try
            {
                String keyString = e.Key.ToString();

                if (keyString.Equals("Return"))
                {
                    PembayaranSupport.CariPelangganPembayaran();
                }
            }
            catch (DataException E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);

                MessageBox.Show(E.Message);
            }
            catch (Exception E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);

                MessageBox.Show("Maaf! Gagal mencari data");
            }
        }

        private void CariNamaPembayaran(object sender, TextChangedEventArgs e)
        {
            try
            {
                PembayaranSupport.CariNama();
            }
            catch (DataException E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);
            }
            catch (Exception E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);

                MessageBox.Show("Maaf! Gagal mencari data" + "\n" + E.Message + "\n" + E.StackTrace);
            }
        }

        private void PelangganTableDoubleClicked(object sender, MouseButtonEventArgs e)
        {
            try
            {
                Pelanggan pelanggan = (Pelanggan)this.dgDaftarPelanggan.SelectedItem;
                PembayaranSupport.Pelanggan = pelanggan;

                Details details = detailService.Get(pelanggan);
                this.txtIdPelanggan.Text = pelanggan.Id;
                this.txtNamaPelanggan.Text = pelanggan.Nama;
                this.txtIuranBulanan.Text = details.Iuran.ToString();
                NumericUtil.NominalSeparator(txtIuranBulanan);
                PembayaranSupport.SetPembayaranDataGrid();
                PembayaranSupport.SetView();
            }
            catch (DataException E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);

                MessageBox.Show(E.Message);
            }
            catch (NullReferenceException E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);
            }
            catch (Exception E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);

                MessageBox.Show("Maaf! Gagal mencari data");
            }
        }

        private void PembayaranTableDoubleClicked(object sender, MouseButtonEventArgs e)
        {
            try
            {
                PembayaranSupport.SetEditable();
            }
            catch (DataException E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);

                MessageBox.Show(E.Message);
            }
            catch (Exception E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);

                MessageBox.Show("Maaf! Gagal mencari data");
            }
        }

        private void PembayaranTableRightClicked(object sender, MouseButtonEventArgs e)
        {
            DialogBox db = new DialogBox(this);
            db.Show();
            db.Focus();
        }

        private void ButtonOkClicked(object sender, System.Windows.RoutedEventArgs e)
        {
            try
            {
                PembayaranSupport.SimpanPembayaran();
            }
            catch (DataException E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);

                MessageBox.Show(E.Message);
            }
            catch (Exception E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);

                MessageBox.Show("Maaf! Gagal menyimpan data");
            }
        }

        private void KeyPressed(object sender, KeyEventArgs e)
        {
            try
            {
                if (EventUtil.IsReturnPressed(e))
                {
                    PembayaranSupport.SimpanPembayaran();
                }
            }
            catch (DataException E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);

                MessageBox.Show(E.Message);
            }
            catch (Exception E)
            {
                Console.WriteLine("INFO : " + E.Message);
                Console.WriteLine(E.StackTrace);

                MessageBox.Show("Maaf! Gagal mencari data");
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

            IList<TipeKontak> lsTipeKontak = tipeKontakService.GetAll();

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
            try
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
            catch (Exception E)
            {
                MessageBox.Show("Maaf! Gagal menyimpan data");
                Console.WriteLine("EXCEPTION HERE :");
                Console.WriteLine(E.Message);
                Console.WriteLine(E.StackTrace);
            }
        }

        private void btnKontakHapus_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                KontakDataManager("delete");
            }
            catch (Exception E)
            {
                MessageBox.Show("Maaf! Gagal menghapus data");
                Console.WriteLine("EXCEPTION HERE :");
                Console.WriteLine(E.Message);
                Console.WriteLine(E.StackTrace);
            }
        }

        private void btnKontakReset_Click(object sender, RoutedEventArgs e)
        {
            resetKontakView();
        }

        private void btnKontakCari_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                String kode = this.txtKodeKontak.Text;
                TipeKontak kontak = (TipeKontak)tipeKontakService.Get(kode);

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
            catch (Exception E)
            {
                MessageBox.Show("Maaf! Gagal mencari data");
                Console.WriteLine("EXCEPTION HERE :");
                Console.WriteLine(E.Message);
                Console.WriteLine(E.StackTrace);
            }
        }

        private void KontakDataManager(String command)
        {
            TipeKontak TipeKontak = new TipeKontak();

            TipeKontak.Kode = this.txtKodeKontak.Text;
            TipeKontak.Detail = this.txtDetailKontak.Text;

            try
            {
                switch (command)
                {
                    case "add": tipeKontakService.Add(TipeKontak);
                        break;
                    case "update": tipeKontakService.Update(TipeKontak);
                        break;
                    case "delete": tipeKontakService.Delete(TipeKontak);
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
            try
            {
                String kode = this.txtUsernameLogin.Text;
                com.gtd.tvkabel.data.entity.Login login = (com.gtd.tvkabel.data.entity.Login)loginService.Get(kode);

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
            catch (Exception E)
            {
                MessageBox.Show("Maaf! Gagal mencari data");
                Console.WriteLine("EXCEPTION HERE :");
                Console.WriteLine(E.Message);
                Console.WriteLine(E.StackTrace);
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
            try
            {
                loginDataManager("delete");
            }
            catch (Exception E)
            {
                MessageBox.Show("Maaf! Gagal menghapus data");
                Console.WriteLine("EXCEPTION HERE :");
                Console.WriteLine(E.Message);
                Console.WriteLine(E.StackTrace);
            }
        }

        private void btnLoginReset_Click(object sender, RoutedEventArgs e)
        {
            resetLoginView();
        }

        private void btnLoginSimpan_Click(object sender, RoutedEventArgs e)
        {
            try
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
            catch (Exception E)
            {
                MessageBox.Show("Maaf! Gagal menyimpan data");
                Console.WriteLine("EXCEPTION HERE :");
                Console.WriteLine(E.Message);
                Console.WriteLine(E.StackTrace);
            }
        }

        private void loginDataManager(String command)
        {
            com.gtd.tvkabel.data.entity.Login Login = new com.gtd.tvkabel.data.entity.Login();

            Login.Username = this.txtUsernameLogin.Text;
            Login.Password = this.txtPasswordLogin.Text;
            Login.Nama = this.txtNamaLogin.Text;
            Login.Role = this.cbRoleLogin.SelectedItem.ToString();

            try
            {
                switch (command)
                {
                    case "add": loginService.Add(Login);
                        break;
                    case "update": loginService.Update(Login);
                        break;
                    case "delete": loginService.Delete(Login);
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
            try
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
            catch (Exception E)
            {
                MessageBox.Show("Maaf! Terdapat beberapa kesalahan");
                Console.WriteLine("EXCEPTION HERE :");
                Console.WriteLine(E.Message);
                Console.WriteLine(E.StackTrace);
            }
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
            this.btnLaporanOK.IsEnabled = true;
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
        }

        private void btnLaporanOK_Click(object sender, System.Windows.RoutedEventArgs e)
        {
            String jenisLaporan = this.cbLaporan.SelectedItem.ToString();

            try
            {
                switch (jenisLaporan)
                {
                    case "Bulanan":
                        int Tahun = Int32.Parse(this.cbLaporanTahun.SelectedItem.ToString());
                        int Bulan = DateAndTime.monthToNumber(this.cbLaporanBulan.SelectedItem.ToString());

                        BulananReport reportBulanan = new BulananReport();
                        PembayaranBulananTableAdapter taBulanan= new PembayaranBulananTableAdapter();

                        reportBulanan.SetDataSource(taBulanan.GetData(Bulan, Tahun).DefaultView);
                        laporanReportViewer.ReportSource = reportBulanan;
                        laporanReportViewer.Refresh();
                        break;
                    case "Tunggakan":
                        TunggakanReport reportTunggakan = new TunggakanReport();
                        TunggakanTableAdapter taTunggakan = new TunggakanTableAdapter();

                        reportTunggakan.SetDataSource(taTunggakan.GetData().DefaultView);
                        laporanReportViewer.ReportSource = reportTunggakan;
                        laporanReportViewer.Refresh();
                        break;
                    case "Pelanggan":
                        if (this.cbStatusPelanggan.SelectedItem.ToString().Equals("Non-Aktif"))
                        {
                            PelangganNonAktif reportPelangganNonAktif = new PelangganNonAktif();
                            PelangganNonAktifTableAdapter taPelangganNonAktif = new PelangganNonAktifTableAdapter();

                            reportPelangganNonAktif.SetDataSource(taPelangganNonAktif.GetData().DefaultView);
                            laporanReportViewer.ReportSource = reportPelangganNonAktif;
                            laporanReportViewer.Refresh();
                        }
                        else
                        {
                            PelangganAktif reportPelangganAktif = new PelangganAktif();
                            PelangganAktifTableAdapter taPelangganAktif = new PelangganAktifTableAdapter();

                            reportPelangganAktif.SetDataSource(taPelangganAktif.GetData().DefaultView);
                            laporanReportViewer.ReportSource = reportPelangganAktif;
                            laporanReportViewer.Refresh();
                        }

                        break;
                }
            }
            catch (NullReferenceException E)
            {
                Console.WriteLine("Info : -. Detail : ");
                Console.WriteLine(E.Message);
                Console.WriteLine(E.StackTrace);

                ReportBlank report = new ReportBlank(this.laporanReportViewer);
            }
            catch (Exception E)
            {
                MessageBox.Show("Maaf! Terdapat beberapa kesalahan");
                Console.WriteLine("EXCEPTION HERE :");
                Console.WriteLine(E.Message);
                Console.WriteLine(E.StackTrace);
            }
        }

        //utility
        private void TextChanged(object sender, TextChangedEventArgs e)
        {
            if (sender is TextBox)
            {
                TextBox tb = (TextBox)sender;
                NumericUtil.NominalSeparator(tb);
            }
        }

    }
}
