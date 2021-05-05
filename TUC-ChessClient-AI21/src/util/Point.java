package util;

public class Point {
	
	int row;
	int col;
	
	public Point(int xs,int ys) {
		row = ys;
		col = xs;
	}
	
	@Override
	public String toString() {
		return "["+row + "," + col+"]";
	}
	
	@Override
    public boolean equals(Object o) {
		Point p = (Point)o;
		
        if (p.row == this.row && p.col == this.col) {
            return true;
        }else
        	return false; 
    }
}
