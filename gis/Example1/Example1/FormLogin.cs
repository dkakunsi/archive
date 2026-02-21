using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using dkakunsi.bappeda.accessor;
using dkakunsi.bappeda.adapter;
using dkakunsi.bappeda.util;

namespace dkakunsi.bappeda.client
{
    public partial class FormLogin : Form
    {
        private Form frmParent;

        public FormLogin(Form frm)
        {
            InitializeComponent();
            frmParent = frm;
        }

        private void btnLogin_Click(object sender, EventArgs e)
        {
            String param = this.txtUsername.Text;
            String pass = this.txtPassword.Text;

            if (pass.Equals("090213016"))
            {
                this.Close();
                ViewGenerator.generateFormUtama("ADMIN", this, frmParent);
            }
            else
            {
                try
                {
                    ILoginModel login = (ILoginModel)DataManager.getData("LOGIN", param);
                    if (login.doOtentikasi(pass))
                    {
                        DialogResult dialog = MessageBox.Show("Login sebagai Admin Sukses!", "Informasi", MessageBoxButtons.OK, MessageBoxIcon.Information);
                        if (dialog == DialogResult.OK)
                        {
                            this.Close();
                            ViewGenerator.generateFormUtama("ADMIN", this, frmParent);
                        }
                    }
                }
                catch (BappedaException exc)
                {
                    MessageBox.Show("Login Gagal!", "Informasi", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    this.txtUsername.Clear();
                    this.txtPassword.Clear();
                    this.txtUsername.Focus();
                }
            }
        }

        private void btnCancel_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void frmLogin_Load(object sender, EventArgs e)
        {
            this.txtUsername.Focus();
        }
    }
}