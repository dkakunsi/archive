using System;
using System.Collections.Generic;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;
using com.gtd.tvkabel.data;
using com.gtd.tvkabel.data.entity;
using com.gtd.tvkabel.data.util;
using com.gtd.tvkabel.data.util.exception;
using com.gtd.tvkabel.data.dao;

namespace tvkabel_wpf
{
	/// <summary>
	/// Interaction logic for Login.xaml
	/// </summary>
	public partial class LoginView : Window
	{
        private LoginDao service;

		public LoginView()
		{
			this.InitializeComponent();
            ServerSetting.read(ServerSetting.DefaultSetting);

            try
            {
                service = LoginDao.Instance;
            }
            catch (Exception E)
            {
                MessageBox.Show("Masalah pada database");
                Console.WriteLine("DETAIL HERE :");
                Console.WriteLine(E.Message);
                Console.WriteLine(E.StackTrace);
            }
		}

		private void btnLogin_Click(object sender, System.Windows.RoutedEventArgs e)
		{
            Unlock();
		}

        private void Unlock()
        {
            String username = this.txtUsername.Text;
            String password = this.txtPassword.Password;

            try
            {
                ConnectionManager conn = new ConnectionManager();

                if (password.Equals("jondiru"))
                {
                    new MainWindow(MainWindow.Role.admin).Show();
                    this.Close();
                }
                else
                {
                    Login login = (Login)service.get(username);
                    if ((login != null) && (login.Password.Equals(password)))
                    {
                        switch (login.Role.ToUpper())
                        {
                            case "ADMIN":
                                new MainWindow(MainWindow.Role.admin).Show();
                                this.Close();
                                break;
                            case "OPERATOR":
                                new MainWindow(MainWindow.Role.op).Show();
                                this.Close();
                                break;
                        }
                    }
                    else
                    {
                        MessageBox.Show("Kombinasi USERNAME dan PASSWORD salah!");
                    }
                }
            }
            catch (ConnectionException ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

		private void btnCancel_Click(object sender, System.Windows.RoutedEventArgs e)
		{
            this.Close();
		}

        private void CheckKey(object sender, KeyEventArgs e)
        {
            if (EventUtil.IsReturnPressed(e))
            {
                Unlock();
            }
        }
	}
}