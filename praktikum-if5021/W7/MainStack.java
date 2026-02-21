import java.util.Scanner;

/**/
class Stack {
    private static final int MAX_EL = 10;
    private final int NIL = 0;
    private int[] T;
    private int top;
    private int max;
    
    public Stack() {
		this(MAX_EL);
    }
    
    public Stack(int max) {
        super();
        this.max = max;
        T = new int[max + 1];
        top = NIL;
    }
    
    public int getTop() {
        return top;
    }
    
    public void setTop(int top) {
        this.top = top;
    }
    
    public int getInfoTop() {
        return T[top];
    }
    
    public void setInfoTop(int infoTop) {
        T[top] = infoTop;
    }
	
	public int getMax() {
		return max;
	}
    
    public boolean isEmpty() {
        return top <= NIL;
    }
    
    public boolean isFull() {
        return top >= max;
    }
    
    public void push(int x) {
        if (!isFull()) {
            top++;
            setInfoTop(x);
        }
    }
    
    public int pop() {
        int x = NIL;
        
        if (!isEmpty()) {
            x = getInfoTop();
            top--;
        }

        return x;
    }
}

public class MainStack {

    public static void main(String[] args) {
        Scanner scanner;
        Stack stack;
        int N, i, jumlah;

        scanner = new Scanner(System.in);
        N = scanner.nextInt();
        stack = new Stack();
        
        for (i = 1; i <= N; i++) {
            if (i % 2 != 0) {
                stack.push(i);
			}
        }
        
        jumlah = 0;
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
            jumlah++;
        }
        
        System.out.println("Empty stack");
        System.out.println(jumlah);
    }
}