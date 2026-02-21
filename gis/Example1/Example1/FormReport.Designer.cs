namespace dkakunsi.bappeda.client
{
    partial class FormReport
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(FormReport));
            this.reportViewer1 = new Microsoft.Reporting.WinForms.ReportViewer();
            this.bntKeluar = new System.Windows.Forms.Button();
            this.btnCari = new System.Windows.Forms.Button();
            this.lblNamaJalan = new System.Windows.Forms.Label();
            this.txtNamaJalan = new System.Windows.Forms.TextBox();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.panel1 = new System.Windows.Forms.Panel();
            this.groupBox1.SuspendLayout();
            this.groupBox2.SuspendLayout();
            this.SuspendLayout();
            // 
            // reportViewer1
            // 
            this.reportViewer1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.reportViewer1.Location = new System.Drawing.Point(3, 16);
            this.reportViewer1.Name = "reportViewer1";
            this.reportViewer1.Size = new System.Drawing.Size(727, 461);
            this.reportViewer1.TabIndex = 0;
            // 
            // bntKeluar
            // 
            this.bntKeluar.BackColor = System.Drawing.Color.Transparent;
            this.bntKeluar.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.bntKeluar.FlatAppearance.BorderSize = 0;
            this.bntKeluar.FlatAppearance.MouseDownBackColor = System.Drawing.Color.Gray;
            this.bntKeluar.FlatAppearance.MouseOverBackColor = System.Drawing.Color.Silver;
            this.bntKeluar.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.bntKeluar.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.bntKeluar.Image = ((System.Drawing.Image)(resources.GetObject("bntKeluar.Image")));
            this.bntKeluar.Location = new System.Drawing.Point(673, 617);
            this.bntKeluar.Name = "bntKeluar";
            this.bntKeluar.Size = new System.Drawing.Size(68, 71);
            this.bntKeluar.TabIndex = 3;
            this.bntKeluar.Text = "KELUAR";
            this.bntKeluar.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.bntKeluar.UseVisualStyleBackColor = false;
            this.bntKeluar.Click += new System.EventHandler(this.bntKeluar_Click);
            // 
            // btnCari
            // 
            this.btnCari.BackColor = System.Drawing.Color.Transparent;
            this.btnCari.FlatAppearance.BorderSize = 0;
            this.btnCari.FlatAppearance.MouseDownBackColor = System.Drawing.Color.Gray;
            this.btnCari.FlatAppearance.MouseOverBackColor = System.Drawing.Color.Silver;
            this.btnCari.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnCari.Image = ((System.Drawing.Image)(resources.GetObject("btnCari.Image")));
            this.btnCari.Location = new System.Drawing.Point(357, 11);
            this.btnCari.Name = "btnCari";
            this.btnCari.Size = new System.Drawing.Size(72, 75);
            this.btnCari.TabIndex = 2;
            this.btnCari.Text = "Report";
            this.btnCari.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.btnCari.UseVisualStyleBackColor = false;
            this.btnCari.Click += new System.EventHandler(this.btnCari_Click);
            // 
            // lblNamaJalan
            // 
            this.lblNamaJalan.AutoSize = true;
            this.lblNamaJalan.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblNamaJalan.Location = new System.Drawing.Point(28, 32);
            this.lblNamaJalan.Name = "lblNamaJalan";
            this.lblNamaJalan.Size = new System.Drawing.Size(84, 15);
            this.lblNamaJalan.TabIndex = 1;
            this.lblNamaJalan.Text = "Nama Jalan";
            // 
            // txtNamaJalan
            // 
            this.txtNamaJalan.Location = new System.Drawing.Point(144, 31);
            this.txtNamaJalan.Name = "txtNamaJalan";
            this.txtNamaJalan.Size = new System.Drawing.Size(160, 20);
            this.txtNamaJalan.TabIndex = 0;
            // 
            // groupBox1
            // 
            this.groupBox1.BackColor = System.Drawing.Color.Transparent;
            this.groupBox1.Controls.Add(this.reportViewer1);
            this.groupBox1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.groupBox1.Location = new System.Drawing.Point(11, 134);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(733, 480);
            this.groupBox1.TabIndex = 3;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Laporan";
            // 
            // groupBox2
            // 
            this.groupBox2.BackColor = System.Drawing.Color.Transparent;
            this.groupBox2.Controls.Add(this.btnCari);
            this.groupBox2.Controls.Add(this.lblNamaJalan);
            this.groupBox2.Controls.Add(this.txtNamaJalan);
            this.groupBox2.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.groupBox2.Location = new System.Drawing.Point(11, 12);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(450, 92);
            this.groupBox2.TabIndex = 4;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "Kontrol";
            // 
            // panel1
            // 
            this.panel1.BackColor = System.Drawing.Color.Transparent;
            this.panel1.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("panel1.BackgroundImage")));
            this.panel1.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
            this.panel1.Location = new System.Drawing.Point(642, 13);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(99, 115);
            this.panel1.TabIndex = 5;
            // 
            // FormReport
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("$this.BackgroundImage")));
            this.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
            this.ClientSize = new System.Drawing.Size(759, 694);
            this.Controls.Add(this.panel1);
            this.Controls.Add(this.bntKeluar);
            this.Controls.Add(this.groupBox2);
            this.Controls.Add(this.groupBox1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "FormReport";
            this.Text = "FormReport";
            this.groupBox1.ResumeLayout(false);
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button bntKeluar;
        private System.Windows.Forms.Button btnCari;
        private System.Windows.Forms.Label lblNamaJalan;
        private System.Windows.Forms.TextBox txtNamaJalan;
        private Microsoft.Reporting.WinForms.ReportViewer reportViewer1;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.Panel panel1;
    }
}