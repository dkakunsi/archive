import java.util.Scanner;

abstract class Provider {
    protected int jenis; /* 1. prabayar, 2 pasca bayar */
    protected double rate;
    protected double durasi;
    
    public int getJenis() {
        return jenis;
    }
    
    public void setJenis(int jenis) {
        this.jenis = jenis;
    }
    
    public double getRate() {
        return rate;
    }
    
    public void setRate(double rate) {
        this.rate = rate;
    }
    
    public double getDurasi() {
        return durasi;
    }
    
    public void setDurasi(double durasi) {
        this.durasi = durasi;
    }

    public double hitung_hargaTotal() {
        return rate * durasi;
    }
    
    public abstract double hitung_hargaTotal(double rate, double durasi);
    
    public void print_info() {
        System.out.println(String.format("Jenis %d rate %.1f durasi %.1f", jenis, rate, durasi));
    }
}

class Handphone extends Provider {
    private double saldo;
    
    public double getSaldo() {
        return saldo;
    }
    
    public void setSaldo(double saldo) {
            this.saldo = saldo;
    }
    
    public double hitung_hargaTotal(double rate, double durasi) {
        setRate(rate);
        setDurasi(durasi);
        
        return hitung_hargaTotal();
    }
    
    public float hitung_durasiMax() {
        double hasil = saldo / rate;
        
        if (jenis == 1) {
            return (float)hasil;
        } else {
            return 0;
        }
    }
    
    public float hitung_durasiMax(int jenis, double saldo, double rate) {
        setJenis(jenis);
        setSaldo(saldo);
        setRate(rate);

        return hitung_durasiMax();
    }
}

public class Telekomunikasi {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Handphone handphone = new Handphone();
        handphone.setJenis(scanner.nextInt());
        handphone.setRate(scanner.nextDouble());
        
        if (handphone.getJenis() == 1) {
            handphone.setSaldo(scanner.nextDouble());
        }
        
        handphone.setDurasi(scanner.nextDouble());
        if (handphone.getJenis() == 1 && handphone.getDurasi() > handphone.hitung_durasiMax()) {
            System.out.println(String.format("durasi Max adalah %.1f menit", handphone.hitung_durasiMax()));
        } else {
            System.out.println(String.format("%.1f", handphone.hitung_hargaTotal()));
            handphone.print_info();
        }
    }
}
