import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.TreeSet;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class PointSET {
    
    private TreeSet<Point2D> ps;
    private int cnt; // points in the set
    
    public         PointSET()                               // construct an empty set of points 
    {
        ps = new TreeSet<Point2D>();
    }
    
    public           boolean isEmpty()                      // is the set empty? 
    {
        return cnt==0;
    }
    
    public               int size()                         // number of points in the set
    {
        return cnt;
    }
    
    public              void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        if (!ps.contains(p)){
            ps.add(p);
            cnt++;
        }
    }
    
    public           boolean contains(Point2D p)            // does the set contain point p?
    {
        return (ps.contains(p));
    }
    
    public              void draw()                         // draw all points to standard draw 
    {
        for (Point2D p : ps){
            p.draw();
        }
        return;
    }
    
    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle 
    {
        TreeSet<Point2D> pointsinRec = new TreeSet<Point2D>();
        for (Point2D p : ps){
            if (rect.contains(p)){
                pointsinRec.add(p);
            }
        }
        return pointsinRec;
    }
    
    public           Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
    {
        // compare the squares of the two distances
        if (isEmpty())
            return null;
        else{
            double minDist = Double.POSITIVE_INFINITY;
            double dist;
            Point2D minP = null;
            for (Point2D point : ps){
                dist = (point.x()-p.x()) * (point.x()-p.x()) + (point.y()-p.y()) * (point.y()-p.y());
                if (dist < minDist){
                    minDist = dist;
                    minP = point;
                }
            }
            return minP;
        }
    }

    public static void main(String[] args)                  // unit testing of the methods (optional) 
    {
    }
}