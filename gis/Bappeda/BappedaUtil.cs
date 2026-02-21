using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;
using System.Collections;
using MySql.Data.MySqlClient;
using dkakunsi.bappeda.accessor;
using dkakunsi.bappeda.adapter;
using dkakunsi.bappeda.entity;

namespace dkakunsi.bappeda.util
{

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

        public static String DEFAULTSETTING
        {
            get { return getDefaultSetting(); }
            set { defaultSetting = value; }
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

            Console.WriteLine(_SERVER);
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

        private static String getDefaultSetting()
        {
            if (defaultSetting == null)
            {
                defaultSetting = @"D:\BAPPEDA\ServerSetting.xml";
            }

            return defaultSetting;
        }

    }

    public class ConnectionManager
    {
        private MySqlConnection connection;

        public MySqlConnection Connection
        {
            get;
            set;
        }

        public String Server
        {
            get;
            set;
        }

        public String Database
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
                throw new BappedaException("Koneksi Gagal\n" + exc.Message);
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
                throw new BappedaException("Koneksi Gagal\n" + exc.Message);
            }
        }

        private MySqlConnection getConnection()
        {
            connection = new MySqlConnection("Server=" + Server + ";Database=" + Database + ";Uid=" + Username + ";Pwd=" + Password + ";Connection Timeout=30;");

            try
            {
                connection.Open();
                return connection;
            }
            catch (MySqlException exc)
            {
                throw exc;
            }
        }

        public void close()
        {
            connection.Close();
        }

    }

    public class BappedaException : SystemException
    {
        public BappedaException() { }
        public BappedaException(String message) : base(message) { }

    }

    public class DataManager
    {
        private const String ASET = "ASET";
        private const String PETA = "PETA";
        private const String LOGIN = "LOGIN";

        private static Adapter adapter;

        public static IDataModel getEmpty(String type)
        {
            if (type.Equals(ASET))
            {
                return getAdapterAset().getEmpty();
            }
            else if (type.Equals(PETA))
            {
                return getAdapterPeta().getEmpty();
            }
            else if (type.Equals(LOGIN))
            {
                return getAdapterLogin().getEmpty();
            }
            else
            {
                return null;
            }
        }

        public static IDataModel getData(String type, String param)
        {
            try
            {
                if (type.Equals(ASET))
                {
                    return getAdapterAset().getData(param);
                }
                else if (type.Equals(PETA))
                {
                    return getAdapterPeta().getData(param);
                }
                else if (type.Equals(LOGIN))
                {
                    return getAdapterLogin().getData(param);
                }
                else
                {
                    return null;
                }
            }
            catch (SystemException exc)
            {
                throw new BappedaException(exc.Message);
            }
        }

        public static ArrayList getAll(String type)
        {
            try
            {
                if (type.Equals(ASET))
                {
                    return getAdapterAset().getAll();
                }
                else if (type.Equals(PETA))
                {
                    return getAdapterPeta().getAll();
                }
                else if (type.Equals(LOGIN))
                {
                    return getAdapterLogin().getAll();
                }
                else
                {
                    return null;
                }
            }
            catch (SystemException exc)
            {
                throw new BappedaException(exc.Message);
            }
        }

        public static void tambah(IDataModel model)
        {
            try
            {
                if (model is AsetJalanModel)
                {
                    getAdapterAset().tambah(model);
                }

                if (model is PetaModel)
                {
                    getAdapterPeta().tambah(model);
                }

                if (model is LoginModel)
                {
                    getAdapterLogin().tambah(model);
                }
            }
            catch (SystemException exc)
            {
                throw new BappedaException(exc.Message);
            }
        }

        public static void ubah(IDataModel model)
        {
            try
            {
                if (model is AsetJalanModel)
                {
                    getAdapterAset().ubah((AsetJalanModel)model);
                }

                if (model is PetaModel)
                {
                    getAdapterPeta().ubah((PetaModel)model);
                }

                if (model is LoginModel)
                {
                    getAdapterLogin().ubah((LoginModel)model);
                }
            }
            catch (SystemException exc)
            {
                throw new BappedaException(exc.Message);
            }
        }

        public static void hapus(IDataModel model)
        {
            try
            {
                if (model is AsetJalanModel)
                {
                    getAdapterAset().hapus((AsetJalanModel)model);
                }

                if (model is PetaModel)
                {
                    getAdapterPeta().hapus((PetaModel)model);
                }

                if (model is LoginModel)
                {
                    getAdapterLogin().hapus((LoginModel)model);
                }
            }
            catch (SystemException exc)
            {
                throw new BappedaException(exc.Message);
            }
        }

        private static Adapter getAdapterAset()
        {
            adapter = new AsetAdapter();

            return adapter;
        }

        private static Adapter getAdapterPeta()
        {
            adapter = new PetaAdapter();

            return adapter;
        }

        private static Adapter getAdapterLogin()
        {
            adapter = new LoginAdapter();

            return adapter;
        }
    }
}
