using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace dkakunsi.bappeda.client
{
    public partial class FormSplash : Form
    {
        public FormSplash()
        {
            InitializeComponent();
            ProgressBar pbSplash = new ProgressBar();
        }

        private void frmSplash_Load(object sender, EventArgs e)
        {
            tmrSplash.Enabled = true;
        }

        private void tmrSplash_Tick(object sender, EventArgs e)
        {
            pbSplash.Increment(25);
            pbSplash.Show();
            if (pbSplash.Value == pbSplash.Maximum)
            {
                tmrSplash.Dispose();
                ViewGenerator.generateFormUtama("CLIENT", null, null);
                this.Hide();
            }
        }

    }
}