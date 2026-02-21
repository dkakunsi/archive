using System;
using System.Collections.Generic;
using System.Windows.Forms;
using dkakunsi.bappeda.util;

namespace dkakunsi.bappeda.client
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            // ServerSetting.read(ServerSetting.getDefXmlPath());
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new FormSplash());
        }
    }
}