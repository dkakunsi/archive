using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Controls;
using System.Windows.Input;

namespace tvkabel_wpf
{
    public class NumericUtil
    {
        public static void NominalSeparator(TextBox sender)
        {
            if (sender.Text.Length > 0)
            {
                try
                {
                    string s = sender.Text.Replace(",", "");

                    int i = int.Parse(s);
                    sender.Text = String.Format("{0:###,###,###,###}", i);

                    sender.SelectionStart = sender.Text.Length;
                }
                catch (Exception E)
                {
                    Console.WriteLine("Exception Cathced :");
                    Console.WriteLine(E.StackTrace);
                }
            }
        }
    }

    public class EventUtil
    {
        public static bool IsReturnPressed(KeyEventArgs e)
        {
            return e.Key.ToString().Equals("Return");
        }
    }
}
