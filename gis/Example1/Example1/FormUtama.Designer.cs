namespace dkakunsi.bappeda.client
{
    partial class FormUtama
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(FormUtama));
            this.label5 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.txtSumberFile = new System.Windows.Forms.TextBox();
            this.txtLebarJln = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.txtPanjangJln = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.txtTtkAkhir = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.txtTtkAwal = new System.Windows.Forms.TextBox();
            this.label6 = new System.Windows.Forms.Label();
            this.panel2 = new System.Windows.Forms.Panel();
            this.pnlLogoSulut = new System.Windows.Forms.Panel();
            this.pnlLogoManado = new System.Windows.Forms.Panel();
            this.panel5 = new System.Windows.Forms.Panel();
            this.cbKategoriAset = new System.Windows.Forms.ComboBox();
            this.label8 = new System.Windows.Forms.Label();
            this.txtNamaAset = new System.Windows.Forms.TextBox();
            this.btnCari = new System.Windows.Forms.Button();
            this.label7 = new System.Windows.Forms.Label();
            this.panel3 = new System.Windows.Forms.Panel();
            this.pnlDataJalan = new System.Windows.Forms.Panel();
            this.label10 = new System.Windows.Forms.Label();
            this.label9 = new System.Windows.Forms.Label();
            this.sfMap1 = new EGIS.Controls.SFMap();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.btnLogin = new System.Windows.Forms.Button();
            this.toolTip1 = new System.Windows.Forms.ToolTip(this.components);
            this.btnSetting = new System.Windows.Forms.Button();
            this.btnLogout = new System.Windows.Forms.Button();
            this.btnKeluar = new System.Windows.Forms.Button();
            this.btnData = new System.Windows.Forms.Button();
            this.dialogCari = new System.Windows.Forms.OpenFileDialog();
            this.panel1 = new System.Windows.Forms.Panel();
            this.panel2.SuspendLayout();
            this.panel5.SuspendLayout();
            this.pnlDataJalan.SuspendLayout();
            this.groupBox1.SuspendLayout();
            this.panel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Font = new System.Drawing.Font("Arial", 9.5F, System.Drawing.FontStyle.Bold);
            this.label5.ImeMode = System.Windows.Forms.ImeMode.NoControl;
            this.label5.Location = new System.Drawing.Point(10, 110);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(91, 16);
            this.label5.TabIndex = 9;
            this.label5.Text = "Sumber Peta";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("Arial", 9.5F, System.Drawing.FontStyle.Bold);
            this.label4.ImeMode = System.Windows.Forms.ImeMode.NoControl;
            this.label4.Location = new System.Drawing.Point(10, 84);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(84, 16);
            this.label4.TabIndex = 7;
            this.label4.Text = "Lebar Jalan";
            // 
            // txtSumberFile
            // 
            this.txtSumberFile.Enabled = false;
            this.txtSumberFile.Location = new System.Drawing.Point(135, 109);
            this.txtSumberFile.Name = "txtSumberFile";
            this.txtSumberFile.Size = new System.Drawing.Size(181, 20);
            this.txtSumberFile.TabIndex = 8;
            // 
            // txtLebarJln
            // 
            this.txtLebarJln.Enabled = false;
            this.txtLebarJln.Location = new System.Drawing.Point(135, 83);
            this.txtLebarJln.Name = "txtLebarJln";
            this.txtLebarJln.Size = new System.Drawing.Size(131, 20);
            this.txtLebarJln.TabIndex = 6;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Arial", 9.5F, System.Drawing.FontStyle.Bold);
            this.label3.ImeMode = System.Windows.Forms.ImeMode.NoControl;
            this.label3.Location = new System.Drawing.Point(10, 58);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(100, 16);
            this.label3.TabIndex = 5;
            this.label3.Text = "Panjang Jalan";
            // 
            // txtPanjangJln
            // 
            this.txtPanjangJln.Enabled = false;
            this.txtPanjangJln.Location = new System.Drawing.Point(135, 57);
            this.txtPanjangJln.Name = "txtPanjangJln";
            this.txtPanjangJln.Size = new System.Drawing.Size(131, 20);
            this.txtPanjangJln.TabIndex = 4;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Arial", 9.5F, System.Drawing.FontStyle.Bold);
            this.label2.ImeMode = System.Windows.Forms.ImeMode.NoControl;
            this.label2.Location = new System.Drawing.Point(10, 30);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(72, 16);
            this.label2.TabIndex = 3;
            this.label2.Text = "Titik Akhir";
            // 
            // txtTtkAkhir
            // 
            this.txtTtkAkhir.Enabled = false;
            this.txtTtkAkhir.Location = new System.Drawing.Point(135, 29);
            this.txtTtkAkhir.Name = "txtTtkAkhir";
            this.txtTtkAkhir.Size = new System.Drawing.Size(181, 20);
            this.txtTtkAkhir.TabIndex = 2;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Arial", 9.5F, System.Drawing.FontStyle.Bold);
            this.label1.ImeMode = System.Windows.Forms.ImeMode.NoControl;
            this.label1.Location = new System.Drawing.Point(10, 4);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(71, 16);
            this.label1.TabIndex = 1;
            this.label1.Text = "Titik Awal";
            // 
            // txtTtkAwal
            // 
            this.txtTtkAwal.Enabled = false;
            this.txtTtkAwal.Location = new System.Drawing.Point(135, 3);
            this.txtTtkAwal.Name = "txtTtkAwal";
            this.txtTtkAwal.Size = new System.Drawing.Size(181, 20);
            this.txtTtkAwal.TabIndex = 0;
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.BackColor = System.Drawing.Color.Transparent;
            this.label6.Font = new System.Drawing.Font("Arial", 30F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Pixel);
            this.label6.ForeColor = System.Drawing.Color.White;
            this.label6.ImeMode = System.Windows.Forms.ImeMode.NoControl;
            this.label6.Location = new System.Drawing.Point(175, 36);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(809, 35);
            this.label6.TabIndex = 2;
            this.label6.Text = "Sistem Informasi Geografis dan Basis Data Aset Daerah";
            // 
            // panel2
            // 
            this.panel2.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("panel2.BackgroundImage")));
            this.panel2.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
            this.panel2.Controls.Add(this.pnlLogoSulut);
            this.panel2.Controls.Add(this.pnlLogoManado);
            this.panel2.Controls.Add(this.label6);
            this.panel2.Controls.Add(this.panel5);
            this.panel2.Location = new System.Drawing.Point(-110, -27);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(1466, 363);
            this.panel2.TabIndex = 3;
            // 
            // pnlLogoSulut
            // 
            this.pnlLogoSulut.BackColor = System.Drawing.Color.Transparent;
            this.pnlLogoSulut.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("pnlLogoSulut.BackgroundImage")));
            this.pnlLogoSulut.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
            this.pnlLogoSulut.Location = new System.Drawing.Point(1243, 99);
            this.pnlLogoSulut.Name = "pnlLogoSulut";
            this.pnlLogoSulut.Size = new System.Drawing.Size(143, 153);
            this.pnlLogoSulut.TabIndex = 4;
            // 
            // pnlLogoManado
            // 
            this.pnlLogoManado.BackColor = System.Drawing.Color.Transparent;
            this.pnlLogoManado.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("pnlLogoManado.BackgroundImage")));
            this.pnlLogoManado.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
            this.pnlLogoManado.Location = new System.Drawing.Point(1100, 99);
            this.pnlLogoManado.Name = "pnlLogoManado";
            this.pnlLogoManado.Size = new System.Drawing.Size(122, 153);
            this.pnlLogoManado.TabIndex = 3;
            // 
            // panel5
            // 
            this.panel5.Controls.Add(this.cbKategoriAset);
            this.panel5.Controls.Add(this.label8);
            this.panel5.Controls.Add(this.txtNamaAset);
            this.panel5.Controls.Add(this.btnCari);
            this.panel5.Controls.Add(this.label7);
            this.panel5.Location = new System.Drawing.Point(1038, 294);
            this.panel5.Name = "panel5";
            this.panel5.Size = new System.Drawing.Size(404, 66);
            this.panel5.TabIndex = 2;
            // 
            // cbKategoriAset
            // 
            this.cbKategoriAset.FormattingEnabled = true;
            this.cbKategoriAset.Items.AddRange(new object[] {
            "--Pilih Kategori--",
            "Aset Jalan"});
            this.cbKategoriAset.Location = new System.Drawing.Point(157, 9);
            this.cbKategoriAset.Name = "cbKategoriAset";
            this.cbKategoriAset.Size = new System.Drawing.Size(181, 21);
            this.cbKategoriAset.TabIndex = 16;
            this.cbKategoriAset.Text = "--Pilih Kategori--";
            this.cbKategoriAset.SelectedIndexChanged += new System.EventHandler(this.cbKategoriAset_SelectedIndexChanged);
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Font = new System.Drawing.Font("Arial", 9.5F, System.Drawing.FontStyle.Bold);
            this.label8.ImeMode = System.Windows.Forms.ImeMode.NoControl;
            this.label8.Location = new System.Drawing.Point(10, 10);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(93, 16);
            this.label8.TabIndex = 15;
            this.label8.Text = "Kategori Aset";
            // 
            // txtNamaAset
            // 
            this.txtNamaAset.Location = new System.Drawing.Point(158, 36);
            this.txtNamaAset.Name = "txtNamaAset";
            this.txtNamaAset.Size = new System.Drawing.Size(181, 20);
            this.txtNamaAset.TabIndex = 11;
            // 
            // btnCari
            // 
            this.btnCari.AutoEllipsis = true;
            this.btnCari.BackColor = System.Drawing.Color.Transparent;
            this.btnCari.FlatAppearance.BorderColor = System.Drawing.Color.White;
            this.btnCari.FlatAppearance.BorderSize = 0;
            this.btnCari.FlatAppearance.MouseDownBackColor = System.Drawing.Color.Transparent;
            this.btnCari.FlatAppearance.MouseOverBackColor = System.Drawing.Color.LightGray;
            this.btnCari.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnCari.Image = ((System.Drawing.Image)(resources.GetObject("btnCari.Image")));
            this.btnCari.ImeMode = System.Windows.Forms.ImeMode.NoControl;
            this.btnCari.Location = new System.Drawing.Point(344, 1);
            this.btnCari.Name = "btnCari";
            this.btnCari.Size = new System.Drawing.Size(50, 63);
            this.btnCari.TabIndex = 12;
            this.toolTip1.SetToolTip(this.btnCari, "My button1");
            this.btnCari.UseVisualStyleBackColor = false;
            this.btnCari.Click += new System.EventHandler(this.buttonCari_Click);
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Font = new System.Drawing.Font("Arial", 9.5F, System.Drawing.FontStyle.Bold);
            this.label7.ImeMode = System.Windows.Forms.ImeMode.NoControl;
            this.label7.Location = new System.Drawing.Point(11, 37);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(76, 16);
            this.label7.TabIndex = 10;
            this.label7.Text = "Nama Aset";
            // 
            // panel3
            // 
            this.panel3.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("panel3.BackgroundImage")));
            this.panel3.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
            this.panel3.Location = new System.Drawing.Point(-1, 666);
            this.panel3.Name = "panel3";
            this.panel3.Size = new System.Drawing.Size(1345, 83);
            this.panel3.TabIndex = 4;
            // 
            // pnlDataJalan
            // 
            this.pnlDataJalan.BackColor = System.Drawing.Color.Transparent;
            this.pnlDataJalan.Controls.Add(this.label10);
            this.pnlDataJalan.Controls.Add(this.label9);
            this.pnlDataJalan.Controls.Add(this.label2);
            this.pnlDataJalan.Controls.Add(this.label3);
            this.pnlDataJalan.Controls.Add(this.txtTtkAkhir);
            this.pnlDataJalan.Controls.Add(this.txtPanjangJln);
            this.pnlDataJalan.Controls.Add(this.label1);
            this.pnlDataJalan.Controls.Add(this.txtTtkAwal);
            this.pnlDataJalan.Controls.Add(this.label5);
            this.pnlDataJalan.Controls.Add(this.label4);
            this.pnlDataJalan.Controls.Add(this.txtLebarJln);
            this.pnlDataJalan.Controls.Add(this.txtSumberFile);
            this.pnlDataJalan.Location = new System.Drawing.Point(12, 19);
            this.pnlDataJalan.Name = "pnlDataJalan";
            this.pnlDataJalan.Size = new System.Drawing.Size(329, 141);
            this.pnlDataJalan.TabIndex = 8;
            this.pnlDataJalan.Visible = false;
            // 
            // label10
            // 
            this.label10.AutoSize = true;
            this.label10.Font = new System.Drawing.Font("Arial", 9.5F, System.Drawing.FontStyle.Bold);
            this.label10.ImeMode = System.Windows.Forms.ImeMode.NoControl;
            this.label10.Location = new System.Drawing.Point(272, 84);
            this.label10.Name = "label10";
            this.label10.Size = new System.Drawing.Size(44, 16);
            this.label10.TabIndex = 11;
            this.label10.Text = "Meter";
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Font = new System.Drawing.Font("Arial", 9.5F, System.Drawing.FontStyle.Bold);
            this.label9.ImeMode = System.Windows.Forms.ImeMode.NoControl;
            this.label9.Location = new System.Drawing.Point(272, 58);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(44, 16);
            this.label9.TabIndex = 10;
            this.label9.Text = "Meter";
            // 
            // sfMap1
            // 
            this.sfMap1.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.sfMap1.CentrePoint = ((System.Drawing.PointF)(resources.GetObject("sfMap1.CentrePoint")));
            this.sfMap1.CentrePoint2D = ((EGIS.ShapeFileLib.PointD)(resources.GetObject("sfMap1.CentrePoint2D")));
            this.sfMap1.Location = new System.Drawing.Point(12, 72);
            this.sfMap1.MapBackColor = System.Drawing.SystemColors.Control;
            this.sfMap1.Name = "sfMap1";
            this.sfMap1.PanSelectMode = EGIS.Controls.PanSelectMode.Pan;
            this.sfMap1.RenderQuality = EGIS.ShapeFileLib.RenderQuality.Auto;
            this.sfMap1.Size = new System.Drawing.Size(907, 613);
            this.sfMap1.TabIndex = 5;
            this.sfMap1.UseMercatorProjection = false;
            this.sfMap1.ZoomLevel = 1D;
            // 
            // groupBox1
            // 
            this.groupBox1.BackColor = System.Drawing.Color.Transparent;
            this.groupBox1.Controls.Add(this.pnlDataJalan);
            this.groupBox1.Location = new System.Drawing.Point(941, 431);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(349, 176);
            this.groupBox1.TabIndex = 7;
            this.groupBox1.TabStop = false;
            // 
            // btnLogin
            // 
            this.btnLogin.BackColor = System.Drawing.Color.Transparent;
            this.btnLogin.FlatAppearance.BorderSize = 0;
            this.btnLogin.FlatAppearance.MouseDownBackColor = System.Drawing.Color.Transparent;
            this.btnLogin.FlatAppearance.MouseOverBackColor = System.Drawing.Color.LightGray;
            this.btnLogin.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnLogin.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold);
            this.btnLogin.ForeColor = System.Drawing.Color.Black;
            this.btnLogin.Image = ((System.Drawing.Image)(resources.GetObject("btnLogin.Image")));
            this.btnLogin.ImageAlign = System.Drawing.ContentAlignment.TopCenter;
            this.btnLogin.ImeMode = System.Windows.Forms.ImeMode.NoControl;
            this.btnLogin.Location = new System.Drawing.Point(3, 3);
            this.btnLogin.Name = "btnLogin";
            this.btnLogin.Size = new System.Drawing.Size(75, 76);
            this.btnLogin.TabIndex = 9;
            this.btnLogin.Text = "LOGIN";
            this.btnLogin.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
            this.btnLogin.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.btnLogin.UseVisualStyleBackColor = false;
            this.btnLogin.Visible = false;
            this.btnLogin.Click += new System.EventHandler(this.btnLogin_Click);
            // 
            // toolTip1
            // 
            this.toolTip1.AutoPopDelay = 5000;
            this.toolTip1.InitialDelay = 1000;
            this.toolTip1.ReshowDelay = 500;
            this.toolTip1.ShowAlways = true;
            // 
            // btnSetting
            // 
            this.btnSetting.BackColor = System.Drawing.Color.Transparent;
            this.btnSetting.FlatAppearance.BorderSize = 0;
            this.btnSetting.FlatAppearance.MouseDownBackColor = System.Drawing.Color.Transparent;
            this.btnSetting.FlatAppearance.MouseOverBackColor = System.Drawing.Color.LightGray;
            this.btnSetting.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnSetting.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold);
            this.btnSetting.ForeColor = System.Drawing.Color.Black;
            this.btnSetting.Image = ((System.Drawing.Image)(resources.GetObject("btnSetting.Image")));
            this.btnSetting.ImageAlign = System.Drawing.ContentAlignment.TopCenter;
            this.btnSetting.ImeMode = System.Windows.Forms.ImeMode.NoControl;
            this.btnSetting.Location = new System.Drawing.Point(84, 4);
            this.btnSetting.Name = "btnSetting";
            this.btnSetting.Size = new System.Drawing.Size(75, 75);
            this.btnSetting.TabIndex = 10;
            this.btnSetting.Text = "SETTING";
            this.btnSetting.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
            this.btnSetting.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.btnSetting.UseVisualStyleBackColor = false;
            this.btnSetting.Visible = false;
            this.btnSetting.Click += new System.EventHandler(this.btnSetting_Click);
            // 
            // btnLogout
            // 
            this.btnLogout.BackColor = System.Drawing.Color.Transparent;
            this.btnLogout.FlatAppearance.BorderSize = 0;
            this.btnLogout.FlatAppearance.MouseDownBackColor = System.Drawing.Color.Transparent;
            this.btnLogout.FlatAppearance.MouseOverBackColor = System.Drawing.Color.LightGray;
            this.btnLogout.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnLogout.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold);
            this.btnLogout.ForeColor = System.Drawing.Color.Black;
            this.btnLogout.Image = ((System.Drawing.Image)(resources.GetObject("btnLogout.Image")));
            this.btnLogout.ImageAlign = System.Drawing.ContentAlignment.TopCenter;
            this.btnLogout.ImeMode = System.Windows.Forms.ImeMode.NoControl;
            this.btnLogout.Location = new System.Drawing.Point(3, 3);
            this.btnLogout.Name = "btnLogout";
            this.btnLogout.Size = new System.Drawing.Size(75, 76);
            this.btnLogout.TabIndex = 11;
            this.btnLogout.Text = "LOGOUT";
            this.btnLogout.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
            this.btnLogout.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.btnLogout.UseVisualStyleBackColor = false;
            this.btnLogout.Visible = false;
            this.btnLogout.Click += new System.EventHandler(this.btnLogout_Click);
            // 
            // btnKeluar
            // 
            this.btnKeluar.BackColor = System.Drawing.Color.Transparent;
            this.btnKeluar.FlatAppearance.BorderSize = 0;
            this.btnKeluar.FlatAppearance.MouseDownBackColor = System.Drawing.Color.Transparent;
            this.btnKeluar.FlatAppearance.MouseOverBackColor = System.Drawing.Color.LightGray;
            this.btnKeluar.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnKeluar.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold);
            this.btnKeluar.ForeColor = System.Drawing.Color.Black;
            this.btnKeluar.Image = ((System.Drawing.Image)(resources.GetObject("btnKeluar.Image")));
            this.btnKeluar.ImageAlign = System.Drawing.ContentAlignment.TopCenter;
            this.btnKeluar.ImeMode = System.Windows.Forms.ImeMode.NoControl;
            this.btnKeluar.Location = new System.Drawing.Point(84, 3);
            this.btnKeluar.Name = "btnKeluar";
            this.btnKeluar.Size = new System.Drawing.Size(75, 75);
            this.btnKeluar.TabIndex = 12;
            this.btnKeluar.Text = "KELUAR";
            this.btnKeluar.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
            this.btnKeluar.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.btnKeluar.UseVisualStyleBackColor = false;
            this.btnKeluar.Visible = false;
            this.btnKeluar.Click += new System.EventHandler(this.btnKeluar_Click);
            // 
            // btnData
            // 
            this.btnData.BackColor = System.Drawing.Color.Transparent;
            this.btnData.FlatAppearance.BorderSize = 0;
            this.btnData.FlatAppearance.MouseDownBackColor = System.Drawing.Color.Transparent;
            this.btnData.FlatAppearance.MouseOverBackColor = System.Drawing.Color.LightGray;
            this.btnData.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnData.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold);
            this.btnData.ForeColor = System.Drawing.Color.Black;
            this.btnData.Image = ((System.Drawing.Image)(resources.GetObject("btnData.Image")));
            this.btnData.ImageAlign = System.Drawing.ContentAlignment.TopCenter;
            this.btnData.ImeMode = System.Windows.Forms.ImeMode.NoControl;
            this.btnData.Location = new System.Drawing.Point(165, 4);
            this.btnData.Name = "btnData";
            this.btnData.Size = new System.Drawing.Size(81, 75);
            this.btnData.TabIndex = 13;
            this.btnData.Text = "DATA";
            this.btnData.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
            this.btnData.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.btnData.UseVisualStyleBackColor = false;
            this.btnData.Visible = false;
            this.btnData.Click += new System.EventHandler(this.btnData_Click);
            // 
            // dialogCari
            // 
            this.dialogCari.FileName = "ServerSetting.xml";
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.btnData);
            this.panel1.Controls.Add(this.btnKeluar);
            this.panel1.Controls.Add(this.btnSetting);
            this.panel1.Controls.Add(this.btnLogin);
            this.panel1.Controls.Add(this.btnLogout);
            this.panel1.Location = new System.Drawing.Point(941, 342);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(349, 83);
            this.panel1.TabIndex = 14;
            // 
            // FormUtama
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoScroll = true;
            this.BackColor = System.Drawing.SystemColors.ButtonHighlight;
            this.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
            this.ClientSize = new System.Drawing.Size(1355, 750);
            this.ControlBox = false;
            this.Controls.Add(this.panel1);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.sfMap1);
            this.Controls.Add(this.panel3);
            this.Controls.Add(this.panel2);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "FormUtama";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Badan Perencanaan Pembangunan Daerah";
            this.panel2.ResumeLayout(false);
            this.panel2.PerformLayout();
            this.panel5.ResumeLayout(false);
            this.panel5.PerformLayout();
            this.pnlDataJalan.ResumeLayout(false);
            this.pnlDataJalan.PerformLayout();
            this.groupBox1.ResumeLayout(false);
            this.panel1.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private EGIS.Controls.SFMap sfMap1;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.ToolTip toolTip1;
        private System.Windows.Forms.Panel panel5;
        private System.Windows.Forms.Panel panel3;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.Panel pnlLogoManado;
        private System.Windows.Forms.Panel pnlDataJalan;
        private System.Windows.Forms.Panel pnlLogoSulut;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox txtSumberFile;
        private System.Windows.Forms.TextBox txtLebarJln;
        private System.Windows.Forms.TextBox txtPanjangJln;
        private System.Windows.Forms.TextBox txtTtkAkhir;
        private System.Windows.Forms.TextBox txtTtkAwal;
        private System.Windows.Forms.TextBox txtNamaAset;
        private System.Windows.Forms.Button btnCari;
        private System.Windows.Forms.Button btnLogin;
        private System.Windows.Forms.Button btnSetting;
        private System.Windows.Forms.Button btnLogout;
        private System.Windows.Forms.Button btnKeluar;
        private System.Windows.Forms.Button btnData;
        private System.Windows.Forms.ComboBox cbKategoriAset;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.OpenFileDialog dialogCari;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Label label10;
        private System.Windows.Forms.Label label9;
    }
}