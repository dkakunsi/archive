using com.gtd.tvkabel.data.entity;
using com.gtd.tvkabel.ui.util;
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
    /// Interaction logic for PrintStruk.xaml
    /// </summary>
    public partial class PrintStruk : Window
    {
        public PrintStruk(Pembayaran pembayaran)
        {
            InitializeComponent();

            ReportStruk report = new ReportStruk(crystalReportViewer);
            IDictionary<String, String> values = report.getFields(pembayaran);
            report.setParameterSingleValue(values);
        }

        private void btnClose_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }
    }
}
