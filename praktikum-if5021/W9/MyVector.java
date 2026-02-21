import java.util.Scanner;
import java.util.Vector;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class MyVector {
    public static void main(String[] args) {
        Vector<Integer> vector = new Vector<>();
        Scanner scanner = new Scanner(System.in);
        Iterator<Integer> iterator;
        List<Integer> indeksZ = new ArrayList<>();
        
        int bilangan;
        Integer Z;
        
        double ratarata;
        double deviasi;
        int jumlahZ;

        Integer elemen;
        int i;
        boolean awal = true;
        
        bilangan = scanner.nextInt();
        while(bilangan != -999) {
            vector.add(new Integer(bilangan));
            bilangan = scanner.nextInt();
        }
        
        Z = new Integer(scanner.nextInt());
        
        ratarata = 0;
        iterator = vector.iterator();
        while(iterator.hasNext()) {
            ratarata += iterator.next();
        }
        ratarata /= vector.size();

        jumlahZ = 0;
        deviasi = 0f;
        iterator = vector.iterator();
        i = 0;
        while(iterator.hasNext()) {
            elemen = iterator.next();
            deviasi += Math.pow((elemen - ratarata), 2);
            
            if (Z.equals(elemen)) {
                jumlahZ++;
                indeksZ.add(i);
            }
            i++;
        }

        double mark = (double)1 / (double)vector.size();
        deviasi *= mark;
        deviasi = Math.sqrt(deviasi);

        //System.out.println(String.format("%.3f"), ratarata);
        //System.out.println(deviasi);
        System.out.printf("%.3f", ratarata);
        System.out.println();
        System.out.printf("%.3f", deviasi);
        System.out.println();
        System.out.println(jumlahZ);
        
        if (indeksZ.size() > 0) {
            for(Integer idx : indeksZ) {
                if (awal) {
                    System.out.print(idx);
                    awal = false;
                } else {
                    System.out.print(String.format(",%s", idx));
                }
            }
            System.out.println();
        } else {
            System.out.println(String.format("%d tidak ada", Z));
        }
    }
}
