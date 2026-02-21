using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using MySql.Data.MySqlClient;
using dkakunsi.bappeda.util;

namespace dkakunsi.bappeda.util.report
{
    public class MyReport
    {
        private static ConnectionManager conn;

        static MyReport()
        {
            conn = new ConnectionManager();
        }

        public static Report cretaeSingleReport(String namaJalan)
        {
            MySqlCommand cmd;
            MySqlDataAdapter adap;

            // Code to get data from database 
            cmd = conn.Connection.CreateCommand();
            cmd.CommandText = "SELECT NAMA_JALAN AS \"NAMA JALAN\", TITIK_AWAL AS \"TITIK AWAL\", TITIK_AKHIR AS \"TITIK AKHIR\", PANJANG_JALAN AS \"PANJANG JALAN\", LEBAR_JALAN AS \"LEBAR JALAN\" "
                + "FROM ASET_JALAN where NAMA_JALAN = '" + namaJalan + "'";
            // Create a Dataset and using DataAdapter to fill it 
            adap = new MySqlDataAdapter();
            adap.SelectCommand = cmd;

            Report report = new Report();
            report.Clear();
            adap.Fill(report, "Aset Jalan");

            conn.close();

            return report;
        }

        public static Report cretaeListReport(String namaJalan)
        {
            MySqlCommand cmd;
            MySqlDataAdapter adap;

            // Code to get data from database 
            cmd = conn.Connection.CreateCommand();
            cmd.CommandText = "SELECT NAMA_JALAN AS \"NAMA JALAN\", TITIK_AWAL AS \"TITIK AWAL\", TITIK_AKHIR AS \"TITIK AKHIR\", PANJANG_JALAN AS \"PANJANG JALAN\", LEBAR_JALAN AS \"LEBAR JALAN\" "
                + "FROM ASET_JALAN";
            // Create a Dataset and using DataAdapter to fill it 
            adap = new MySqlDataAdapter();
            adap.SelectCommand = cmd;

            Report report = new Report();
            report.Clear();
            adap.Fill(report, "Aset Jalan");

            conn.close();

            return report;
        }
    }
}
