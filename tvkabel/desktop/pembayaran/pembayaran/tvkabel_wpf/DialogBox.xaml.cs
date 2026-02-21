using com.gtd.tvkabel.data.entity;
using com.gtd.tvkabel.data.util.exception;
using System;
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
using System.Windows.Shapes;

namespace tvkabel_wpf
{
    /// <summary>
    /// Interaction logic for DialogBox.xaml
    /// </summary>
    public partial class DialogBox : Window
    {
        private MainWindow Window;
        private Pembayaran Pembayaran;

        public DialogBox()
        {
            InitializeComponent();
        }

        public DialogBox(MainWindow Window)
        {
            InitializeComponent();
            this.Window = Window;
        }

        public DialogBox(MainWindow Window, Pembayaran Pembayaran)
        {
            InitializeComponent();
            this.Window = Window;
            this.Pembayaran = Pembayaran;
        }

        private void BtnEdit_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                this.Close();
                Window.PembayaranSupport.SetEditable();
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

        private void BtnHapus_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                this.Close();
                Window.PembayaranSupport.DeletePembayaran();
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
    }
}
