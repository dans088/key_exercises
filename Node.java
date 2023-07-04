public class Node implements Comparable<Node> {
	
		public  int child;
		public  int cost;
		public  double path;
		public  int parent;

		public Node(int child, int cost, double path, int parent) {
			this.child = child;
			this.cost = cost;
			this.path = path;
			this.parent = parent;
		}
		
		public void setParent(int parent) {this.parent = parent;}
		public void setPath(double path) {this.path = path;}
		
		public int getChild() { return child; }
		public int getCost() { return cost; }
		public double getPath() { return path; }
		public int getParent() { return parent; }
		
		public boolean equals (Node x) {
				return this.getChild() == x.getChild();
			}
		
		public int compareTo (Node x) {
			if(this.equals(x)) {
				return 0;
			}
			else if(getCost() > x.getCost())
				return 1;
			else
				return -1;
		}
		
		public String toString() {
			return ("Node: " + getChild() + " " + " Cost " + getCost());
		}

		
	
}