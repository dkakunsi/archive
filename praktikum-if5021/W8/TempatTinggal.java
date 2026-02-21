public abstract class TempatTinggal {

    protected int luas;
    protected double hargaBahanBangunan;
    
    public int getLuas() {
        return luas;
    }
    
    public void setLuas(int luas) {
        this.luas = luas;
    }
    
    public double getHargaBahanBangunan() {
        return hargaBahanBangunan;
    }
    
    public void setHargaBahanBangunan(double hargaBahanBangunan) {
        this.hargaBahanBangunan = hargaBahanBangunan;
    }
    
    public double hitung_biayaBangun() {
        return luas * hargaBahanBangunan;
    }
    
    public abstract double hitung_biayaBangun(int luas, double hargaBahanBangunan);

    public static void main(String[] args) {
        TempatTinggal rumah = new Rumah(10, 1000000);
        System.out.println(rumah.hitung_biayaBangun());
    }
}

interface Kendaraan {

    float hitung_jarakTempuh();

    float hitung_jarakTempuh(float bensin, float pemakaianBensin);

}

class Rumah extends TempatTinggal {

    public Rumah(int luas, double hargaBahanBangunan) {
        super();
        this.luas = luas;
        this.hargaBahanBangunan = hargaBahanBangunan;
    }
    
    public double hitung_biayaBangun(int luas, double hargaBahanBangunan) {
        setLuas(luas);
        setHargaBahanBangunan(hargaBahanBangunan);
        
        return hitung_biayaBangun();
    }
}

class Karavan extends TempatTinggal implements Kendaraan {

    private float bensin;
    private float pemakaianBensin;
    
    public float getBensin() {
        return bensin;
    }
    
    public void setBensin(float bensin) {
        this.bensin = bensin;
    }
    
    public float getPemakaianBensin() {
        return pemakaianBensin;
    }
    
    public void setPemakaianBensin(float pemakaianBensin) {
        this.pemakaianBensin = pemakaianBensin;
    }
    
    public double hitung_biayaBangun() {
        return (luas * hargaBahanBangunan) * 3;
    }
    
    public double hitung_biayaBangun(int luas, double hargaBahanBangunan) {
        setLuas(luas);
        setHargaBahanBangunan(hargaBahanBangunan);
        
        return hitung_biayaBangun();
    }
    
    public float hitung_jarakTempuh() {
        return bensin * pemakaianBensin;
    }
    
    public float hitung_jarakTempuh(float bensin, float pemakaianBensin) {
        setBensin(bensin);
        setPemakaianBensin(pemakaianBensin);
        
        return hitung_jarakTempuh();
    }
}

