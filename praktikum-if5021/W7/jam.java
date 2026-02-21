import java.util.Scanner;

public class jam {

    private int JJ;
    private int MM;
    private int DD;
    
    public jam() {
        this(0, 0, 0);
    }
    
    public jam(int JJ, int MM, int DD) {
        super();
        this.JJ = JJ;
        this.MM = MM;
        this.DD = DD;
    }
    
    public int getJJ() {
        return JJ;
    }
    
    public void setJJ(int JJ) {
        this.JJ = JJ;
    }
    
    public int getMM() {
        return MM;
    }
    
    public void setMM(int MM) {
        this.MM = MM;
    }
    
    public int getDD() {
        return DD;
    }
    
    public void setDD(int DD) {
        this.DD = DD;
    }
    
    public boolean IsValid() {
        return IsValid(JJ, MM, DD);
    }
    
    public boolean IsValid(int JJ, int MM, int DD) {
        return (JJ >= 0 && JJ <= 23) &&
            (MM >= 0 && MM <= 59) &&
            (DD >= 0 && DD <= 59);
    }
    
    public void TulisJam() {
        System.out.println(String.format("%d:%d:%d", JJ, MM, DD));
    }
    
    public boolean IsAfter(jam other) {
		boolean flag = false;
		
		if (JJ > other.getJJ()) {
			flag = true;
		} else if (JJ == other.getJJ()) {
			if (MM > other.getMM()) {
				flag = true;
			} else if (MM == other.getMM() && (DD > other.getDD())) {
				flag = true;
			}
		}

		return flag;
    }
    
    public long Durasi(jam other) {
        int j = JJ - other.getJJ();
        int m = MM - other.getMM();
        int d = DD - other.getDD();
		
		int hasil = j * 3600 + m * 60 + d;
        
        return hasil < 0 ? (hasil + (hasil * -2)) : hasil;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        jam j1 = new jam();
        jam other = new jam(1, 0, 0);
        jam other2 = new jam(3, 0, 0);
        int j, m, d;
        
        j1.setJJ(scanner.nextInt());
        j1.setMM(scanner.nextInt());
        j1.setDD(scanner.nextInt());
        
        j1.TulisJam();
        System.out.println(j1.IsValid(1, 59, 5));
        System.out.println(j1.IsValid(24, 59, 5));
        System.out.println(j1.IsValid(22, 61, 5));
        System.out.println(j1.IsValid(22, 59, 70));
		
		System.out.println();
        System.out.println(j1.IsAfter(other));
        System.out.println(j1.IsAfter(other2));
		
		System.out.println();
        System.out.println(j1.Durasi(other));
        System.out.println(j1.Durasi(other2));
    }
}

