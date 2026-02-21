import java.util.List;
import java.util.ArrayList;

class Tree<T> {
	private T value;
	private Tree<T> parent;
	private List<Tree<T>> children;
	
	public Tree(T value) {
		super();
		setValue(value);
		children = new ArrayList<>();
	}
	
	public T getValue() {
		return value;
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	public Tree<T> getParent() {
		return parent;
	}
	
	public void setParent(Tree<T> parent) {
		this.parent = parent;
	}
	
	public List<Tree<T>> getChildren() {
		return children;
	}
	
	public Tree<T> addChildren(T value) {
		Tree<T> child = new Tree<>(value);
		child.setParent(this);
		
		children.add(child);
		
		return child;
	}
	
	public boolean isRoot() {
		return parent == null;
	}
	
	public boolean isLeaf() {
		return children.isEmpty();
	}
	
	public boolean isLastChild() {
		boolean bool = false;
		List<Tree<T>> siblings;
		int siblingsSize = 0;
		
		if (isRoot()) {
			bool = true;
		} else {
			siblings = parent.getChildren();
			siblingsSize = siblings.size();
			
			if (siblings.get(siblingsSize - 1).equals(this)) {
				bool = true;
			}
		}
		
		return bool;
	}
	
	public void print() {
		int numberOfChildren = children.size();
		
		if (isRoot()) {
			System.out.print("{");
		}

		System.out.print(value);
		
		if (!isLeaf()) {
			System.out.print(":{");

			for (Tree<T> t : children) {
				t.print();
				
				if (numberOfChildren > 1) {
					System.out.print(",");
					numberOfChildren--;
				}
			}

			System.out.print("}");
		}

		if (isRoot()) {
			System.out.print("}");
		}
	}
}

public class TreeTest {
	public static void main(String[] args) {
		Tree<Integer> tree = new Tree<>(0);
		Tree<Integer> children1 = tree.addChildren(1);
		Tree<Integer> grandChildren1 = children1.addChildren(11);
		Tree<Integer> grandChildren2 = children1.addChildren(12);
		Tree<Integer> grandChildren3 = children1.addChildren(13);
		Tree<Integer> children2 = tree.addChildren(2);
		
		tree.print();
	}
}