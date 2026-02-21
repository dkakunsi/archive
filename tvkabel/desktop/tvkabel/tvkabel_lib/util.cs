using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;
using MySql.Data.MySqlClient;
using com.gtd.tvkabel.data.util.exception;
using com.gtd.tvkabel.data.entity;

namespace com.gtd.tvkabel.data.util
{
    public enum EntityList
    {
        PELANGGAN,
        DETAIL,
        PEMBAYARAN,
        KONTAK,
        TIPEKONTAK,
        LOGIN
    }

    public class PembayaranPk : Data
    {
        public String Pelanggan { get; set; }
        public int Tahun { get; set; }
        public int Bulan { get; set; }

    }

    public class KontakPk : Data
    {
        public String Pelanggan { get; set; }
        public String TipeKontak { get; set; }

    }

    public class ConnectionManager
    {
        private MySqlConnection connection;

        static ConnectionManager() { }

        public ConnectionManager()
        {
            Server = ServerSetting.SERVER; ;
            Database = ServerSetting.DATABASE;
            Username = ServerSetting.DBUSERNAME;
            Password = ServerSetting.DBPASSWORD;

            try
            {
                Connection = getConnection();
            }
            catch (MySqlException exc)
            {
                throw new ConnectionException("Koneksi Gagal ke server " + Server + "\n" +exc.Message);
            }
        }

        public ConnectionManager(String server, String database, String username, String password)
        {
            Server = server;
            Database = database;
            Username = username;
            Password = password;

            try
            {
                Connection = getConnection();
            }
            catch (MySqlException exc)
            {
                throw new ConnectionException("Koneksi Gagal\n" + exc.Message);
            }
        }

        private MySqlConnection getConnection()
        {
            if (connection == null)
            {
                connection = new MySqlConnection("Server=" + Server + ";Database=" + Database + ";Uid=" + Username + ";Pwd=" + Password + ";Connection Timeout=30;");

                try
                {
                    connection.Open();
                }
                catch (MySqlException exc)
                {
                    throw exc;
                }
            }
            return connection;
        }

        public MySqlConnection Connection
        {
            get
            {
                if (this.connection == null)
                {
                    this.connection = getConnection();
                }
                if (connection.State != System.Data.ConnectionState.Open)
                {
                    connection.Open();
                }
                return connection;
            }
            set
            {
                this.connection = value;
            }
        }

        public String Server
        {
            get;
            set;
        }

        public static String Database
        {
            get;
            set;
        }

        public String Username
        {
            get;
            set;
        }

        public String Password
        {
            get;
            set;
        }

        public void close()
        {
            connection.Close();
        }
    }

    public class ServerSetting
    {
        private static String _SERVER;
        private static String _CREDUSERNAME;
        private static String _CREDPASSWORD;
        private static String _DATABASE;
        private static String _DBUSERNAME;
        private static String _DBPASSWORD;
        private static String defaultSetting;

        ServerSetting() { }

        public static String SERVER
        {
            get { return _SERVER; }
            set { _SERVER = value; }
        }

        public static String CREDUSERNAME
        {
            get { return _CREDUSERNAME; }
            set { _CREDUSERNAME = value; }
        }

        public static String CREDPASSWORD
        {
            get { return _CREDPASSWORD; }
            set { _CREDPASSWORD = value; }
        }

        public static String DATABASE
        {
            get { return _DATABASE; }
            set { _DATABASE = value; }
        }

        public static String DBUSERNAME
        {
            get { return _DBUSERNAME; }
            set { _DBUSERNAME = value; }
        }

        public static String DBPASSWORD
        {
            get { return _DBPASSWORD; }
            set { _DBPASSWORD = value; }
        }

        public static String DefaultSetting
        {
            get { return getDefaultSetting(); }
            set { defaultSetting = value; }
        }

        private static String getDefaultSetting()
        {
            if (defaultSetting == null)
            {
                defaultSetting = Environment.CurrentDirectory + "\\" + "ServerSetting.xml";
            }

            return defaultSetting;
        }

        public static void read(String path)
        {
            XmlDocument xmlDoc = new XmlDocument(); //* create an xml document object.
            xmlDoc.Load(path); //* load the XML document from the specified file.

            //* Get elements.
            XmlNodeList server = xmlDoc.GetElementsByTagName("Server");
            XmlNodeList database = xmlDoc.GetElementsByTagName("Database");

            _SERVER = server[0].ChildNodes[0].InnerText.ToString();
            _CREDUSERNAME = server[0].ChildNodes[1].InnerText.ToString();
            _CREDPASSWORD = server[0].ChildNodes[2].InnerText.ToString();
            _DATABASE = database[0].ChildNodes[0].InnerText.ToString();
            _DBUSERNAME = database[0].ChildNodes[1].InnerText.ToString();
            _DBPASSWORD = database[0].ChildNodes[2].InnerText.ToString();
        }

        public static void write(String path)
        {
            XmlDocument xmlDoc = new XmlDocument(); //* create an xml document object.
            xmlDoc.Load(path);

            XmlNode node;
            node = xmlDoc.DocumentElement;

            foreach (XmlNode node1 in node.ChildNodes)
            {
                foreach (XmlNode node2 in node1.ChildNodes)
                {
                    if (node1.Name.Equals("Server"))
                    {
                        if (node2.Name.Equals("Address"))
                        {
                            node2.InnerText = _SERVER;
                        }
                        else if (node2.Name.Equals("Username"))
                        {
                            node2.InnerText = _CREDUSERNAME;
                        }
                        else if (node2.Name.Equals("Password"))
                        {
                            node2.InnerText = _CREDPASSWORD;
                        }
                    }
                    else if (node1.Name.Equals("Database"))
                    {
                        if (node2.Name.Equals("Name"))
                        {
                            node2.InnerText = _DATABASE;
                        }
                        else if (node2.Name.Equals("Username"))
                        {
                            node2.InnerText = _DBUSERNAME;
                        }
                        else if (node2.Name.Equals("Password"))
                        {
                            node2.InnerText = _DBPASSWORD;
                        }
                    }
                }
            }

            xmlDoc.Save(path);
        }
    }

    public class DateAndTime
    {

        public static String dateTimeToString(DateTime dt)
        {
            String day = dt.Day.ToString();
            String month = dt.Month.ToString();
            String year = dt.Year.ToString();

            if (day.Length != 2)
            {
                day = "0" + day;
            }
            if (month.Length != 2)
            {
                month = "0" + month;
            }

            return day + "-" + month + "-" + year;
        }

        public static DateTime stringToDateTime(String str)
        {
            int year = Int32.Parse(str.Substring(6, 4));
            int month = Int32.Parse(str.Substring(3, 2));
            int day = Int32.Parse(str.Substring(0, 2));

            DateTime dt = new DateTime(year, month, day);

            return dt;
        }

        public static String numberToMonth(int number)
        {
            switch (number)
            {
                case 1: return "Januari";
                case 2: return "Feruari";
                case 3: return "Maret";
                case 4: return "April";
                case 5: return "Mei";
                case 6: return "Juni";
                case 7: return "Juli";
                case 8: return "Agustus";
                case 9: return "September";
                case 10: return "Oktober";
                case 11: return "November";
                case 12: return "Desember";
                default: return null;
            }
        }

        public static int monthToNumber(String month)
        {
            switch (month)
            {
                case "Januari": return 1;
                case "Februari": return 2;
                case "Maret": return 3;
                case "April": return 4;
                case "Mei": return 5;
                case "Juni": return 6;
                case "Juli": return 7;
                case "Agustus": return 8;
                case "September": return 9;
                case "Oktober": return 10;
                case "November": return 11;
                case "Desember": return 12;
                default: return 0;
            }
        }

        public static String getDay(DateTime dt)
        {
            String day = dt.Day.ToString();

            if (day.Length != 2)
            {
                day = "0" + day;
            }
            return day;
        }

        public static String getMonth(DateTime dt)
        {
            String month = dt.Month.ToString();

            if (month.Length != 2)
            {
                month = "0" + month;
            }
            return month;
        }

        public static String getYear(DateTime dt)
        {
            return dt.Year.ToString();
        }
    }
}

namespace com.gtd.tvkabel.data.util.exception
{
    public class DataException : Exception
    {
	public DataException() : base() { }

	public DataException(String message) : base(message) { }
    }

    public class DataNotFoundException : DataException
    {
        public DataNotFoundException() : base() { }

        public DataNotFoundException(String message) : base(message) { }
    }

    public class DataValidationException : DataException
    {
        public DataValidationException() : base() { }

        public DataValidationException(String message) : base(message) { }
    }

    public class ConnectionException : Exception
    {
        public ConnectionException() : base() { }

        public ConnectionException(String message) : base(message) { }
    }

}
