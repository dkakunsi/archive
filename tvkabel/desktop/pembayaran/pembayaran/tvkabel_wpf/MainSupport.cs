using com.gtd.tvkabel.data.entity;
using com.gtd.tvkabel.data.service;
using com.gtd.tvkabel.data.util;
using com.gtd.tvkabel.data.util.exception;
using com.gtd.tvkabel.ui.util;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Controls;
using System.Windows.Data;

/* Class pendukung untuk Class MainWindow*/
namespace tvkabel_wpf
{

    public abstract class Support
    {
        public PelangganService PelangganService { get; set; }
        public TipeKontakService TipeKontakService { get; set; }
        public KontakService KontakService { get; set; }
        public DetailService DetailService { get; set; }
        public TunggakanService TunggakanService { get; set; }
        public PembayaranService PembayaranService { get; set; }
        public MainWindow Window { get; set; }

        public Pelanggan Pelanggan { get; set; }

        public Support() { }
        public Support(MainWindow window)
        {
            this.Window = window;
            PelangganService = new PelangganService();
            KontakService = new KontakService();
            TipeKontakService = new TipeKontakService();
            PembayaranService = new PembayaranService();
            DetailService = new DetailService();
            TunggakanService = new TunggakanService();
        }
    }

    public class PelangganSupport : Support
    {
        public IDictionary<String, String> daftarKontak;
        public IDictionary<String, String> daftarKontakSave;
        public IList<String> listKontakDelete;
        public IList<TipeKontak> listKontak;

        public PelangganSupport() : base()
        {
            PelangganService = new PelangganService();
            KontakService = new KontakService();
            TipeKontakService = new TipeKontakService();
            PembayaranService = new PembayaranService();
            DetailService = new DetailService();
            TunggakanService = new TunggakanService();

            daftarKontakSave = new Dictionary<String, String>();
            listKontakDelete = new List<String>();
            daftarKontak = new Dictionary<String, String>();
            listKontak = new List<TipeKontak>();
        }

        public PelangganSupport(MainWindow window) : base(window)
        {
            PelangganService = new PelangganService();
            KontakService = new KontakService();
            TipeKontakService = new TipeKontakService();
            PembayaranService = new PembayaranService();
            DetailService = new DetailService();
            TunggakanService = new TunggakanService();

            this.Window = window;
            daftarKontakSave = new Dictionary<String, String>();
            listKontakDelete = new List<String>();
            daftarKontak = new Dictionary<String, String>();
            listKontak = new List<TipeKontak>();
        }

        public void SetPelangganDataGrid()
        {
            Window.dgPelanggan.IsEnabled = true;
            Window.dgPelanggan.IsReadOnly = true;

            IList<Pelanggan> lsPelanggan = PelangganService.GetAll();
            this.SetDataGrid(lsPelanggan);
        }

        private void SetDataGrid(IList<Pelanggan> list)
        {
            if (list != null)
            {
                Window.dgPelanggan.Items.Clear();
                foreach (Pelanggan pelanggan in list)
                {
                    Window.dgPelanggan.Items.Add(pelanggan);

                    ((DataGridTextColumn)Window.dgPelanggan.Columns[0]).Binding = new Binding("Id");
                    ((DataGridTextColumn)Window.dgPelanggan.Columns[1]).Binding = new Binding("Nama");
                    ((DataGridTextColumn)Window.dgPelanggan.Columns[2]).Binding = new Binding("TglMulai");
                }
            }
        }

        public void SetPelangganComboBoxTipeKontak()
        {
            try
            {
                int count = 0;
                listKontak = TipeKontakService.GetAll();
                if (listKontak != null)
                {
                    Window.cbPelangganTipeKontak.Items.Clear();
                    foreach (TipeKontak kontak in listKontak)
                    {
                        if (count == 0)
                            Window.cbPelangganTipeKontak.Items.Add("");
                        Window.cbPelangganTipeKontak.Items.Add(kontak.Detail);
                        count++;
                    }
                }
            }
            catch (DataException E)
            {
                throw E;
            }
        }

        public void SetPelanggan(Pelanggan pelanggan)
        {
            Window.txtId.Text = pelanggan.Id;
            Window.txtNama.Text = pelanggan.Nama;
            Window.calTglMulai.SelectedDate = DateAndTime.stringToDateTime(pelanggan.TglMulai);
            Window.txtJumlahTv.Text = pelanggan.JumlahTv + "";
            Window.txtAlamat.Text = pelanggan.Alamat;
            if (pelanggan.Status == 1)
            {
                Window.rdbStatusAktif.IsChecked = true;
            }
            else
            {
                Window.rdbStatusNonAktif.IsChecked = true;
            }
            Window.txtKeterangan.Text = pelanggan.Keterangan;
        }

        public void SetPelanggan()
        {
            SetPelanggan(Pelanggan);
        }

        public void SetPelangganKontak(IList<Kontak> arg)
        {
            if (arg != null)
            {
                daftarKontak.Clear();
                foreach (Kontak kontak in arg)
                {
                    String tipe = kontak.TipeKontak.Kode;
                    String detail = kontak.Detail;

                    daftarKontak.Add(tipe, detail);
                }
            }
            else
            {
                throw new DataException("ARG IS NULL");
            }
        }

        public void SetPelangganKontak()
        {
            SetPelangganKontak(KontakService.GetByPelanggan(Pelanggan));
        }

        public void SetPelangganDetail(Details detail)
        {
            Window.txtDetailPendaftaran.Text = detail.Pendaftaran.ToString();
            Window.txtDetailIuran.Text = detail.Iuran.ToString();
        }

        public void SetPelangganDetail()
        {
            SetPelangganDetail(DetailService.Get(Pelanggan));
        }

        public Pelanggan ConstructPelanggan()
        {
            Pelanggan Pelanggan = new Pelanggan();

            Pelanggan.Id = Window.txtId.Text;
            Pelanggan.Nama = Window.txtNama.Text;
            Pelanggan.TglMulai = DateAndTime.dateTimeToString(Window.calTglMulai.SelectedDate.Value);
            Pelanggan.JumlahTv = Int16.Parse(Window.txtJumlahTv.Text);
            Pelanggan.Alamat = Window.txtAlamat.Text;
            Pelanggan.Status = (bool)Window.rdbStatusAktif.IsChecked ? (short)1 : (short)0;
            Pelanggan.Keterangan = Window.txtKeterangan.Text;

            return Pelanggan;
        }

        public Pelanggan ConstructPelanggan(Pelanggan Pelanggan)
        {
            Pelanggan.Id = Window.txtId.Text;
            Pelanggan.Nama = Window.txtNama.Text;
            Pelanggan.TglMulai = DateAndTime.dateTimeToString(Window.calTglMulai.SelectedDate.Value);
            Pelanggan.JumlahTv = Int16.Parse(Window.txtJumlahTv.Text);
            Pelanggan.Alamat = Window.txtAlamat.Text;
            Pelanggan.Status = (bool)Window.rdbStatusAktif.IsChecked ? (short)1 : (short)0;
            Pelanggan.Keterangan = Window.txtKeterangan.Text;

            return Pelanggan;
        }

        public Details ConstructDetail(Pelanggan pelanggan)
        {
            Details Detail = new Details();
            Detail.Pelanggan = pelanggan;

            Detail.Pendaftaran = Double.Parse(Window.txtDetailPendaftaran.Text);
            Detail.Iuran = Double.Parse(Window.txtDetailIuran.Text);

            return Detail;
        }

        public Details ConstructDetail()
        {
            return ConstructDetail(Pelanggan);
        }

        public void ResetPelanggan()
        {
            Pelanggan = null;
            daftarKontak.Clear();
            daftarKontakSave.Clear();
            listKontak.Clear();
            listKontakDelete.Clear();

            SetPelangganDataGrid();
            SetPelangganComboBoxTipeKontak();

            ResetView();
        }

        public void ResetView()
        {
            Window.txtId.IsEnabled = true;
            Window.txtId.Text = "";
            Window.txtNama.IsEnabled = true;
            Window.txtNama.Text = "";
            Window.txtJumlahTv.IsEnabled = false;
            Window.txtJumlahTv.Text = "";
            Window.txtAlamat.IsEnabled = false;
            Window.txtAlamat.Text = "";
            Window.lblKeterangan.Visibility = System.Windows.Visibility.Hidden;
            Window.txtKeterangan.Text = "";
            Window.txtKeterangan.Visibility = System.Windows.Visibility.Hidden;
            Window.calTglMulai.IsEnabled = false;
            Window.calTglMulai.SelectedDate = null;
            Window.calTglMulai.SelectedDateFormat = DatePickerFormat.Short;
            Window.txtDetailPendaftaran.IsEnabled = false;
            Window.txtDetailPendaftaran.Text = "";
            Window.txtDetailIuran.IsEnabled = false;
            Window.txtDetailIuran.Text = "";
            Window.rdbStatusAktif.IsEnabled = false;
            Window.rdbStatusAktif.IsChecked = false;
            Window.rdbStatusNonAktif.IsEnabled = false;
            Window.rdbStatusNonAktif.IsChecked = false;

            Window.cbPelangganTipeKontak.IsEnabled = false;
            Window.cbPelangganTipeKontak.SelectedIndex = 0;
            Window.txtPelangganDetailKontak.IsEnabled = false;
            Window.txtPelangganDetailKontak.Text = "";

            Window.btnPelangganTambah.IsEnabled = true;
            Window.btnPelangganUbah.IsEnabled = false;
            Window.btnPelangganSimpan.IsEnabled = false;
            Window.btnPelangganHapus.IsEnabled = false;
            Window.btnPelangganReset.IsEnabled = false;
            Window.btnPelangganCari.IsEnabled = true;
        }

        public void CariPelanggan()
        {
            try
            {
                String kode = Window.txtId.Text;
                Pelanggan = PelangganService.Get(kode);

                SetPelanggan();
                SetPelangganKontak();
                SetPelangganDetail();
            }
            catch (DataException E)
            {
                throw E;
            }
            finally
            {
                Window.btnPelangganTambah.IsEnabled = false;
                Window.btnPelangganUbah.IsEnabled = true;
                Window.btnPelangganSimpan.IsEnabled = false;
                Window.btnPelangganHapus.IsEnabled = true;
                Window.btnPelangganReset.IsEnabled = true;
                Window.btnPelangganCari.IsEnabled = false;
            }
        }

        public void CariNama()
        {
            String nama = Window.txtNama.Text;
            IList<Pelanggan> lsPelanggan = PelangganService.getByNama(nama);
            SetDataGrid(lsPelanggan);
        }

        public void SimpanPelanggan(Pelanggan Pelanggan)
        {
            if ((Pelanggan != null) && (Pelanggan.Kode != 0))
            {
                Pelanggan = ConstructPelanggan(Pelanggan);
                PelangganService.Update(Pelanggan);
                DetailService.Update(ConstructDetail(Pelanggan));
                SimpanKontakPelanggan(Pelanggan);
                TunggakanService.Update(Pelanggan);
            }
            else
            {
                Pelanggan = ConstructPelanggan();
                Pelanggan = PelangganService.Add(Pelanggan);
                DetailService.Add(ConstructDetail(Pelanggan));
                SimpanKontakPelanggan(Pelanggan);
                TunggakanService.Add(Pelanggan);
            }

            ResetPelanggan();
        }

        public void SimpanPelanggan()
        {
            SimpanPelanggan(this.Pelanggan);
        }

        public void SimpanKontakPelanggan(Pelanggan pelanggan)
        {
            try
            {
                HapusKontakPelanggan(pelanggan);

                foreach (System.Collections.Generic.KeyValuePair<String, String> kode in this.daftarKontakSave)
                {
                    Kontak Kontak = new Kontak();
                    TipeKontak tipeKontak = (TipeKontak)TipeKontakService.Get(kode.Key);

                    Kontak.Pelanggan = pelanggan;
                    Kontak.TipeKontak = tipeKontak;
                    Kontak.Detail = kode.Value;

                    KontakService.Add(Kontak);
                }
            }
            catch (DataException E)
            {
                throw E;
            }
        }

        public void SimpanKontakPelanggan()
        {
            SimpanKontakPelanggan(Pelanggan);
        }

        public void HapusKontakPelanggan(Pelanggan pelanggan)
        {
            try
            {
                foreach (String kode in this.listKontakDelete)
                {
                    Kontak Kontak = new Kontak();
                    TipeKontak tipeKontak = (TipeKontak)TipeKontakService.Get(kode);

                    Kontak.Pelanggan = pelanggan;
                    Kontak.TipeKontak = tipeKontak;

                    KontakService.Delete(Kontak);
                }
            }
            catch (DataException E)
            {
                throw E;
            }
        }

        public void HapusKontakPelanggan()
        {
            HapusKontakPelanggan(Pelanggan);
        }

        public void HapusPelanggan(Pelanggan Pelanggan)
        {
            PelangganService.DeleteByKode(Pelanggan);
            DetailService.Delete(Pelanggan);
            KontakService.DeleteByPelanggan(Pelanggan);
            TunggakanService.Delete(Pelanggan);
            PembayaranService.DeleteByPelanggan(Pelanggan);

            ResetPelanggan();
        }

        public void HapusPelanggan()
        {
            HapusPelanggan(this.Pelanggan);
        }

        public String TipeToKode(String tipe, IList<TipeKontak> list)
        {
            String kode = "";

            foreach (com.gtd.tvkabel.data.entity.TipeKontak type in list)
            {
                if (type.Detail.Equals(tipe))
                {
                    kode = type.Kode;
                    break;
                }
            }

            return kode;
        }

        public String TipeToKode(String tipe)
        {
            return TipeToKode(tipe, listKontak);
        }
    }

    public class PembayaranSupport : Support
    {
        public Pembayaran Pembayaran { get; set; }

        public PembayaranSupport() : base() { }
        public PembayaranSupport(MainWindow window) : base(window)
        {
        }

        public void ResetView()
        {
            Window.txtIdPelanggan.IsEnabled = true;
            Window.txtNamaPelanggan.IsEnabled = true;
            Window.txtKeteranganPembayaran.IsEnabled = true;
            Window.cbTahun.IsEnabled = false;
            Window.cbBulan.IsEnabled = false;
            Window.txtIuranBulanan.IsEnabled = false;
            Window.txtJumlahBayar.IsEnabled = false;
            Window.calTanggalBayar.IsEnabled = false;
            Window.btnCariPelanggan.IsEnabled = true;
            Window.btnPembayaranCetak.IsEnabled = false;
            Window.btnPembayaranReset.IsEnabled = true;

            Window.calTanggalBayar.SelectedDate = null;
            Window.txtKeteranganPembayaran.Text = "";
            Window.cbTahun.SelectedIndex = 0;
            Window.cbBulan.SelectedIndex = 0;
            Window.txtJumlahBayar.Text = "";
            Window.dgPembayaran.Items.Clear();
        }

        public void Reset()
        {
            SetDaftarPelangganDataGrid();
            ResetView();
        }

        public void SetView()
        {
            Window.txtIdPelanggan.IsEnabled = true;
            Window.txtKeteranganPembayaran.IsEnabled = true;
            Window.cbTahun.IsEnabled = true;
            Window.cbBulan.IsEnabled = true;
            Window.txtJumlahBayar.IsEnabled = true;
            Window.calTanggalBayar.IsEnabled = true;
            Window.btnCariPelanggan.IsEnabled = true;
            Window.btnPembayaranCetak.IsEnabled = true;
            Window.txtJumlahBayar.Text = "";
        }

        public void SetDaftarPelangganDataGrid()
        {
            try
            {
                Window.dgDaftarPelanggan.IsEnabled = true;
                Window.dgDaftarPelanggan.IsReadOnly = true;

                SetDaftarPelangganDataGrid(PelangganService.GetByStatus(1));
            }
            catch (DataException E)
            {
                throw E;
            }
        }

        public void SetDaftarPelangganDataGrid(IList<Pelanggan> lsPelanggan)
        {
            if (lsPelanggan != null)
            {
                Window.dgDaftarPelanggan.Items.Clear();
                foreach (Pelanggan pelanggan in lsPelanggan)
                {
                    Window.dgDaftarPelanggan.Items.Add(pelanggan);

                    ((DataGridTextColumn)Window.dgDaftarPelanggan.Columns[0]).Binding = new Binding("Id");
                    ((DataGridTextColumn)Window.dgDaftarPelanggan.Columns[1]).Binding = new Binding("Nama");
                }
            }
        }

        public void SetPembayaranDataGrid(IList<Pembayaran> lsPembayaran)
        {
            if (lsPembayaran != null)
            {
                Window.dgPembayaran.IsEnabled = true;
                Window.dgPembayaran.IsReadOnly = true;

                Window.dgPembayaran.Items.Clear();
                foreach (Pembayaran pembayaran in lsPembayaran)
                {
                    Window.dgPembayaran.Items.Add(pembayaran);

                    ((DataGridTextColumn)Window.dgPembayaran.Columns[0]).Binding = new Binding("Tahun");
                    ((DataGridTextColumn)Window.dgPembayaran.Columns[1]).Binding = new Binding("Bulan");
                    ((DataGridTextColumn)Window.dgPembayaran.Columns[2]).Binding = new Binding("TanggalBayar");
                }
            }
            else
            {
                Window.dgPembayaran.Items.Clear();
            }
        }

        public void SetPembayaranDataGrid()
        {
            SetPembayaranDataGrid(PembayaranService.GetByPelanggan(Pelanggan));
        }

        public void CariPelangganPembayaran()
        {
            try
            {
                String id = Window.txtIdPelanggan.Text;
                Pelanggan pelanggan = (Pelanggan)PelangganService.Get(id);

                Details details = (Details)DetailService.Get(pelanggan);
                Window.txtNamaPelanggan.Text = pelanggan.Nama;
                Window.txtIuranBulanan.Text = details.Iuran.ToString();
                NumericUtil.NominalSeparator(Window.txtIuranBulanan);
                SetPembayaranDataGrid(PembayaranService.GetByPelanggan(pelanggan));
                SetView();
            }
            catch (DataException E)
            {
                throw E;
            }
        }

        public void CariNama()
        {
            String nama = Window.txtNamaPelanggan.Text;
            SetDaftarPelangganDataGrid(PelangganService.getByNama(nama));
        }

        public void SimpanPembayaran()
        {
            try
            {
                Pembayaran pembayaran = new Pembayaran();

                pembayaran.Pelanggan = (Pelanggan)PelangganService.Get(Window.txtIdPelanggan.Text);
                pembayaran.Tahun = Int32.Parse(Window.cbTahun.SelectedItem.ToString());
                pembayaran.Bulan = DateAndTime.monthToNumber(Window.cbBulan.SelectedItem.ToString());
                pembayaran.TanggalBayar = DateAndTime.dateTimeToString(Window.calTanggalBayar.SelectedDate.Value);
                pembayaran.Keterangan = Window.txtKeteranganPembayaran.Text;
                pembayaran.Biaya = Window.txtJumlahBayar.Text.Equals("") ? Double.Parse(Window.txtIuranBulanan.Text) : Double.Parse(Window.txtJumlahBayar.Text);

                if (Window.dgPembayaran.Items.Contains(pembayaran) == true)
                {
                    UpdatePembayaran(pembayaran);
                }
                else
                {
                    SimpanPembayaran(pembayaran);
                }
                ResetView();
                SetView();
                SetPembayaranDataGrid(PembayaranService.GetByPelanggan(pembayaran.Pelanggan));

                PrintStruk formCetak = new PrintStruk(pembayaran);
                formCetak.Show();
            }
            catch (DataException E)
            {
                throw E;
            }
        }

        public void SimpanPembayaran(Pembayaran pembayaran)
        {
            PembayaranService.Add(pembayaran);
            TunggakanService.Update(pembayaran.Pelanggan);
        }

        public void UpdatePembayaran(Pembayaran pembayaran)
        {
            PembayaranService.Update(pembayaran);
        }

        public void SetEditable()
        {
            Pembayaran pembayaran = (Pembayaran)Window.dgPembayaran.SelectedItem;
            Pelanggan pelanggan = pembayaran.Pelanggan;
            Details details = DetailService.Get(pelanggan);

            Window.txtIdPelanggan.Text = pelanggan.Id;
            Window.txtNamaPelanggan.Text = pelanggan.Nama;
            Window.txtIuranBulanan.Text = details.Iuran.ToString();
            NumericUtil.NominalSeparator(Window.txtIuranBulanan);
            Window.cbTahun.SelectedItem = pembayaran.Tahun.ToString();
            Window.cbBulan.SelectedItem = DateAndTime.numberToMonth(pembayaran.Bulan);
            Window.txtJumlahBayar.Text = pembayaran.Biaya.ToString();
            NumericUtil.NominalSeparator(Window.txtJumlahBayar);
            Window.calTanggalBayar.SelectedDate = DateAndTime.stringToDateTime(pembayaran.TanggalBayar);
            Window.txtKeteranganPembayaran.Text = pembayaran.Keterangan;
        }

        public void DeletePembayaran(Pembayaran pembayaran)
        {
            Pelanggan = pembayaran.Pelanggan;
            PembayaranService.Delete(pembayaran);
            TunggakanService.Update(Pelanggan);
            SetPembayaranDataGrid();
        }

        public void DeletePembayaran()
        {
            Pembayaran pembayaran = (Pembayaran)Window.dgPembayaran.SelectedItem;
            DeletePembayaran(pembayaran);
        }
    }
}
