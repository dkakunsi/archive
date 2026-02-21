namespace dkakunsi.bappeda.client
{
    partial class FormData
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(FormData));
            this.tpAsetJalan = new System.Windows.Forms.TabPage();
            this.dgData = new System.Windows.Forms.DataGridView();
            this.namaJalan = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.titikAwal = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.titikAkhir = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.panjangJalan = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.lebarJalan = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.btnKeluar = new System.Windows.Forms.Button();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.btnReset = new System.Windows.Forms.Button();
            this.btnUbah = new System.Windows.Forms.Button();
            this.btnSimpan = new System.Windows.Forms.Button();
            this.btnHapus = new System.Windows.Forms.Button();
            this.btnTambah = new System.Windows.Forms.Button();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.label8 = new System.Windows.Forms.Label();
            this.label7 = new System.Windows.Forms.Label();
            this.cbPeta = new System.Windows.Forms.ComboBox();
            this.label6 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.btnCari = new System.Windows.Forms.Button();
            this.label4 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.txtLebarJalan = new System.Windows.Forms.TextBox();
            this.txtPanjangJalan = new System.Windows.Forms.TextBox();
            this.txtTitikAkhir = new System.Windows.Forms.TextBox();
            this.txtTitikAwal = new System.Windows.Forms.TextBox();
            this.txtNamaJalan = new System.Windows.Forms.TextBox();
            this.tab = new System.Windows.Forms.TabControl();
            this.toolTip1 = new System.Windows.Forms.ToolTip(this.components);
            this.dialogCari = new System.Windows.Forms.OpenFileDialog();
            this.tpAsetJalan.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dgData)).BeginInit();
            this.groupBox2.SuspendLayout();
            this.groupBox1.SuspendLayout();
            this.tab.SuspendLayout();
            this.SuspendLayout();
            // 
            // tpAsetJalan
            // 
            this.tpAsetJalan.BackColor = System.Drawing.Color.Transparent;
            this.tpAsetJalan.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("tpAsetJalan.BackgroundImage")));
            this.tpAsetJalan.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
            this.tpAsetJalan.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.tpAsetJalan.Controls.Add(this.dgData);
            this.tpAsetJalan.Controls.Add(this.btnKeluar);
            this.tpAsetJalan.Controls.Add(this.groupBox2);
            this.tpAsetJalan.Controls.Add(this.groupBox1);
            this.tpAsetJalan.ForeColor = System.Drawing.SystemColors.ControlText;
            this.tpAsetJalan.Location = new System.Drawing.Point(4, 22);
            this.tpAsetJalan.Name = "tpAsetJalan";
            this.tpAsetJalan.Padding = new System.Windows.Forms.Padding(3);
            this.tpAsetJalan.Size = new System.Drawing.Size(609, 697);
            this.tpAsetJalan.TabIndex = 0;
            this.tpAsetJalan.Text = "Aset Jalan";
            // 
            // dgData
            // 
            this.dgData.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgData.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.namaJalan,
            this.titikAwal,
            this.titikAkhir,
            this.panjangJalan,
            this.lebarJalan});
            this.dgData.Location = new System.Drawing.Point(22, 375);
            this.dgData.Name = "dgData";
            this.dgData.Size = new System.Drawing.Size(566, 235);
            this.dgData.TabIndex = 10;
            // 
            // namaJalan
            // 
            this.namaJalan.HeaderText = "Nama Jalan";
            this.namaJalan.Name = "namaJalan";
            this.namaJalan.ReadOnly = true;
            // 
            // titikAwal
            // 
            this.titikAwal.HeaderText = "Titik Awal";
            this.titikAwal.Name = "titikAwal";
            this.titikAwal.ReadOnly = true;
            // 
            // titikAkhir
            // 
            this.titikAkhir.HeaderText = "Titik Akhir";
            this.titikAkhir.Name = "titikAkhir";
            this.titikAkhir.ReadOnly = true;
            // 
            // panjangJalan
            // 
            this.panjangJalan.HeaderText = "Panjang Jalan";
            this.panjangJalan.Name = "panjangJalan";
            this.panjangJalan.ReadOnly = true;
            // 
            // lebarJalan
            // 
            this.lebarJalan.HeaderText = "Lebar Jalan";
            this.lebarJalan.Name = "lebarJalan";
            this.lebarJalan.ReadOnly = true;
            // 
            // btnKeluar
            // 
            this.btnKeluar.FlatAppearance.BorderSize = 0;
            this.btnKeluar.FlatAppearance.CheckedBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(224)))), ((int)(((byte)(224)))), ((int)(((byte)(224)))));
            this.btnKeluar.FlatAppearance.MouseDownBackColor = System.Drawing.Color.Silver;
            this.btnKeluar.FlatAppearance.MouseOverBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(224)))), ((int)(((byte)(224)))), ((int)(((byte)(224)))));
            this.btnKeluar.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnKeluar.Image = ((System.Drawing.Image)(resources.GetObject("btnKeluar.Image")));
            this.btnKeluar.Location = new System.Drawing.Point(519, 616);
            this.btnKeluar.Name = "btnKeluar";
            this.btnKeluar.Size = new System.Drawing.Size(69, 73);
            this.btnKeluar.TabIndex = 6;
            this.btnKeluar.Text = "Keluar";
            this.btnKeluar.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
            this.btnKeluar.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.btnKeluar.UseVisualStyleBackColor = true;
            this.btnKeluar.Click += new System.EventHandler(this.btnKeluar_Click);
            // 
            // groupBox2
            // 
            this.groupBox2.BackColor = System.Drawing.Color.Transparent;
            this.groupBox2.Controls.Add(this.btnReset);
            this.groupBox2.Controls.Add(this.btnUbah);
            this.groupBox2.Controls.Add(this.btnSimpan);
            this.groupBox2.Controls.Add(this.btnHapus);
            this.groupBox2.Controls.Add(this.btnTambah);
            this.groupBox2.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.groupBox2.ForeColor = System.Drawing.Color.Black;
            this.groupBox2.Location = new System.Drawing.Point(22, 247);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(566, 111);
            this.groupBox2.TabIndex = 8;
            this.groupBox2.TabStop = false;
            // 
            // btnReset
            // 
            this.btnReset.FlatAppearance.BorderSize = 0;
            this.btnReset.FlatAppearance.CheckedBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(224)))), ((int)(((byte)(224)))), ((int)(((byte)(224)))));
            this.btnReset.FlatAppearance.MouseDownBackColor = System.Drawing.Color.Silver;
            this.btnReset.FlatAppearance.MouseOverBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(224)))), ((int)(((byte)(224)))), ((int)(((byte)(224)))));
            this.btnReset.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnReset.Image = ((System.Drawing.Image)(resources.GetObject("btnReset.Image")));
            this.btnReset.Location = new System.Drawing.Point(450, 19);
            this.btnReset.Name = "btnReset";
            this.btnReset.Size = new System.Drawing.Size(69, 73);
            this.btnReset.TabIndex = 5;
            this.btnReset.Text = "Reset";
            this.btnReset.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
            this.btnReset.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.btnReset.UseVisualStyleBackColor = true;
            this.btnReset.Click += new System.EventHandler(this.btnReset_Click);
            // 
            // btnUbah
            // 
            this.btnUbah.FlatAppearance.BorderSize = 0;
            this.btnUbah.FlatAppearance.CheckedBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(224)))), ((int)(((byte)(224)))), ((int)(((byte)(224)))));
            this.btnUbah.FlatAppearance.MouseDownBackColor = System.Drawing.Color.Silver;
            this.btnUbah.FlatAppearance.MouseOverBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(224)))), ((int)(((byte)(224)))), ((int)(((byte)(224)))));
            this.btnUbah.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnUbah.Image = ((System.Drawing.Image)(resources.GetObject("btnUbah.Image")));
            this.btnUbah.Location = new System.Drawing.Point(140, 19);
            this.btnUbah.Name = "btnUbah";
            this.btnUbah.Size = new System.Drawing.Size(70, 73);
            this.btnUbah.TabIndex = 4;
            this.btnUbah.Text = "Ubah";
            this.btnUbah.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.btnUbah.UseVisualStyleBackColor = true;
            this.btnUbah.Click += new System.EventHandler(this.btnUbah_Click);
            // 
            // btnSimpan
            // 
            this.btnSimpan.FlatAppearance.BorderSize = 0;
            this.btnSimpan.FlatAppearance.CheckedBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(224)))), ((int)(((byte)(224)))), ((int)(((byte)(224)))));
            this.btnSimpan.FlatAppearance.MouseDownBackColor = System.Drawing.Color.Silver;
            this.btnSimpan.FlatAppearance.MouseOverBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(224)))), ((int)(((byte)(224)))), ((int)(((byte)(224)))));
            this.btnSimpan.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnSimpan.Image = ((System.Drawing.Image)(resources.GetObject("btnSimpan.Image")));
            this.btnSimpan.ImageAlign = System.Drawing.ContentAlignment.TopCenter;
            this.btnSimpan.Location = new System.Drawing.Point(249, 19);
            this.btnSimpan.Margin = new System.Windows.Forms.Padding(0);
            this.btnSimpan.Name = "btnSimpan";
            this.btnSimpan.Size = new System.Drawing.Size(69, 73);
            this.btnSimpan.TabIndex = 3;
            this.btnSimpan.Text = "Simpan";
            this.btnSimpan.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
            this.btnSimpan.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.btnSimpan.UseVisualStyleBackColor = true;
            this.btnSimpan.Click += new System.EventHandler(this.btnSimpan_Click);
            // 
            // btnHapus
            // 
            this.btnHapus.FlatAppearance.BorderSize = 0;
            this.btnHapus.FlatAppearance.CheckedBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(224)))), ((int)(((byte)(224)))), ((int)(((byte)(224)))));
            this.btnHapus.FlatAppearance.MouseDownBackColor = System.Drawing.Color.Silver;
            this.btnHapus.FlatAppearance.MouseOverBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(224)))), ((int)(((byte)(224)))), ((int)(((byte)(224)))));
            this.btnHapus.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnHapus.Image = ((System.Drawing.Image)(resources.GetObject("btnHapus.Image")));
            this.btnHapus.Location = new System.Drawing.Point(350, 19);
            this.btnHapus.Name = "btnHapus";
            this.btnHapus.Size = new System.Drawing.Size(69, 73);
            this.btnHapus.TabIndex = 2;
            this.btnHapus.Text = "Hapus";
            this.btnHapus.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.btnHapus.UseVisualStyleBackColor = true;
            this.btnHapus.Click += new System.EventHandler(this.btnHapus_Click);
            // 
            // btnTambah
            // 
            this.btnTambah.FlatAppearance.BorderSize = 0;
            this.btnTambah.FlatAppearance.CheckedBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(224)))), ((int)(((byte)(224)))), ((int)(((byte)(224)))));
            this.btnTambah.FlatAppearance.MouseDownBackColor = System.Drawing.Color.Silver;
            this.btnTambah.FlatAppearance.MouseOverBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(224)))), ((int)(((byte)(224)))), ((int)(((byte)(224)))));
            this.btnTambah.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnTambah.Image = ((System.Drawing.Image)(resources.GetObject("btnTambah.Image")));
            this.btnTambah.Location = new System.Drawing.Point(41, 19);
            this.btnTambah.Name = "btnTambah";
            this.btnTambah.Size = new System.Drawing.Size(69, 73);
            this.btnTambah.TabIndex = 1;
            this.btnTambah.Text = "Tambah";
            this.btnTambah.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.btnTambah.UseVisualStyleBackColor = true;
            this.btnTambah.Click += new System.EventHandler(this.btnTambah_Click);
            // 
            // groupBox1
            // 
            this.groupBox1.BackColor = System.Drawing.Color.Transparent;
            this.groupBox1.Controls.Add(this.label8);
            this.groupBox1.Controls.Add(this.label7);
            this.groupBox1.Controls.Add(this.cbPeta);
            this.groupBox1.Controls.Add(this.label6);
            this.groupBox1.Controls.Add(this.label5);
            this.groupBox1.Controls.Add(this.btnCari);
            this.groupBox1.Controls.Add(this.label4);
            this.groupBox1.Controls.Add(this.label3);
            this.groupBox1.Controls.Add(this.label2);
            this.groupBox1.Controls.Add(this.label1);
            this.groupBox1.Controls.Add(this.txtLebarJalan);
            this.groupBox1.Controls.Add(this.txtPanjangJalan);
            this.groupBox1.Controls.Add(this.txtTitikAkhir);
            this.groupBox1.Controls.Add(this.txtTitikAwal);
            this.groupBox1.Controls.Add(this.txtNamaJalan);
            this.groupBox1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.groupBox1.ForeColor = System.Drawing.Color.Black;
            this.groupBox1.Location = new System.Drawing.Point(22, 30);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(566, 211);
            this.groupBox1.TabIndex = 7;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Data";
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label8.Location = new System.Drawing.Point(447, 143);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(39, 13);
            this.label8.TabIndex = 19;
            this.label8.Text = "Meter";
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label7.Location = new System.Drawing.Point(447, 112);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(39, 13);
            this.label7.TabIndex = 18;
            this.label7.Text = "Meter";
            // 
            // cbPeta
            // 
            this.cbPeta.FormattingEnabled = true;
            this.cbPeta.Items.AddRange(new object[] {
            "-- Pilih Peta --"});
            this.cbPeta.Location = new System.Drawing.Point(187, 170);
            this.cbPeta.Name = "cbPeta";
            this.cbPeta.Size = new System.Drawing.Size(299, 21);
            this.cbPeta.TabIndex = 17;
            this.cbPeta.Text = "-- Pilih Peta --";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label6.Location = new System.Drawing.Point(23, 173);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(33, 13);
            this.label6.TabIndex = 16;
            this.label6.Text = "Peta";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label5.Location = new System.Drawing.Point(22, 147);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(73, 13);
            this.label5.TabIndex = 15;
            this.label5.Text = "Lebar Jalan";
            // 
            // btnCari
            // 
            this.btnCari.FlatAppearance.BorderSize = 0;
            this.btnCari.FlatAppearance.CheckedBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(224)))), ((int)(((byte)(224)))), ((int)(((byte)(224)))));
            this.btnCari.FlatAppearance.MouseDownBackColor = System.Drawing.Color.Silver;
            this.btnCari.FlatAppearance.MouseOverBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(224)))), ((int)(((byte)(224)))), ((int)(((byte)(224)))));
            this.btnCari.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnCari.Image = ((System.Drawing.Image)(resources.GetObject("btnCari.Image")));
            this.btnCari.Location = new System.Drawing.Point(505, 13);
            this.btnCari.Name = "btnCari";
            this.btnCari.Size = new System.Drawing.Size(42, 39);
            this.btnCari.TabIndex = 0;
            this.btnCari.UseVisualStyleBackColor = true;
            this.btnCari.Click += new System.EventHandler(this.btnCari_Click);
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label4.Location = new System.Drawing.Point(23, 112);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(87, 13);
            this.label4.TabIndex = 14;
            this.label4.Text = "Panjang Jalan";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(23, 86);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(65, 13);
            this.label3.TabIndex = 13;
            this.label3.Text = "Titik Akhir";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.Location = new System.Drawing.Point(23, 56);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(63, 13);
            this.label2.TabIndex = 12;
            this.label2.Text = "Titik Awal";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(22, 26);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(73, 13);
            this.label1.TabIndex = 11;
            this.label1.Text = "Nama Jalan";
            // 
            // txtLebarJalan
            // 
            this.txtLebarJalan.Location = new System.Drawing.Point(187, 140);
            this.txtLebarJalan.Name = "txtLebarJalan";
            this.txtLebarJalan.Size = new System.Drawing.Size(254, 20);
            this.txtLebarJalan.TabIndex = 9;
            // 
            // txtPanjangJalan
            // 
            this.txtPanjangJalan.Location = new System.Drawing.Point(187, 109);
            this.txtPanjangJalan.Name = "txtPanjangJalan";
            this.txtPanjangJalan.Size = new System.Drawing.Size(254, 20);
            this.txtPanjangJalan.TabIndex = 8;
            // 
            // txtTitikAkhir
            // 
            this.txtTitikAkhir.Location = new System.Drawing.Point(187, 79);
            this.txtTitikAkhir.Name = "txtTitikAkhir";
            this.txtTitikAkhir.Size = new System.Drawing.Size(299, 20);
            this.txtTitikAkhir.TabIndex = 7;
            // 
            // txtTitikAwal
            // 
            this.txtTitikAwal.Location = new System.Drawing.Point(187, 49);
            this.txtTitikAwal.Name = "txtTitikAwal";
            this.txtTitikAwal.Size = new System.Drawing.Size(299, 20);
            this.txtTitikAwal.TabIndex = 6;
            // 
            // txtNamaJalan
            // 
            this.txtNamaJalan.Location = new System.Drawing.Point(187, 19);
            this.txtNamaJalan.Name = "txtNamaJalan";
            this.txtNamaJalan.Size = new System.Drawing.Size(299, 20);
            this.txtNamaJalan.TabIndex = 5;
            // 
            // tab
            // 
            this.tab.Controls.Add(this.tpAsetJalan);
            this.tab.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.tab.Location = new System.Drawing.Point(-3, -1);
            this.tab.Name = "tab";
            this.tab.SelectedIndex = 0;
            this.tab.Size = new System.Drawing.Size(617, 723);
            this.tab.TabIndex = 0;
            // 
            // FormData
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(610, 717);
            this.ControlBox = false;
            this.Controls.Add(this.tab);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "FormData";
            this.Text = "Badan Perencanaan Pembangunan Daerah | Data";
            this.tpAsetJalan.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.dgData)).EndInit();
            this.groupBox2.ResumeLayout(false);
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.tab.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TabPage tpAsetJalan;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.Button btnReset;
        private System.Windows.Forms.Button btnUbah;
        private System.Windows.Forms.Button btnSimpan;
        private System.Windows.Forms.Button btnHapus;
        private System.Windows.Forms.Button btnTambah;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.ComboBox cbPeta;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Button btnCari;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox txtLebarJalan;
        private System.Windows.Forms.TextBox txtPanjangJalan;
        private System.Windows.Forms.TextBox txtTitikAkhir;
        private System.Windows.Forms.TextBox txtTitikAwal;
        private System.Windows.Forms.TextBox txtNamaJalan;
        private System.Windows.Forms.TabControl tab;
        private System.Windows.Forms.Button btnKeluar;
        private System.Windows.Forms.ToolTip toolTip1;
        private System.Windows.Forms.OpenFileDialog dialogCari;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.DataGridView dgData;
        private System.Windows.Forms.DataGridViewTextBoxColumn namaJalan;
        private System.Windows.Forms.DataGridViewTextBoxColumn titikAwal;
        private System.Windows.Forms.DataGridViewTextBoxColumn titikAkhir;
        private System.Windows.Forms.DataGridViewTextBoxColumn panjangJalan;
        private System.Windows.Forms.DataGridViewTextBoxColumn lebarJalan;

    }
}

