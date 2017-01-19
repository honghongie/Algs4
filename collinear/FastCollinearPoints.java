import java.util.*;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut; 
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {
    
 private int len;
 private int numSegs = 0;
 private List<LineSegment> segs = new ArrayList<>();

 public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
 {
     len = points.length;
     if (len == 0) throw new java.lang.NullPointerException();
     
     //add points with same slope together
     Point[] temp = Arrays.copyOf(points, points.length);
     Arrays.sort(temp);
     
     // check duplicate and null
     for (int i = 0; i < len - 1; i ++)
     {
         if (points[i] == null) throw new java.lang.NullPointerException();
         if (points[i].compareTo(points[i + 1]) == 0)
             throw new java.lang.IllegalArgumentException();
     }
     
     for (Point p : points)
     {        
         Arrays.sort(temp, p.slopeOrder());
         List<Point> slopepoints = new ArrayList<>();
         double slope = 0;
         double pre = Double.NEGATIVE_INFINITY;
      
         for(int j = 1; j < len; j ++)
         {
             slope = p.slopeTo(temp[j]);
             if (Double.compare(slope, pre) == 0)
             {
                 slopepoints.add(temp[j]);
             }
             else{
                 if (slopepoints.size() >= 3){
                     int numPoints = slopepoints.size();
                     Point[] pointstmp = slopepoints.toArray(new Point[numPoints]);
                     Arrays.sort(pointstmp);
                     if (p.compareTo(pointstmp[0]) < 0){
                         numSegs++;
                         segs.add(new LineSegment (p, pointstmp[numPoints-1]));
                     }
                 }
                 slopepoints.clear();
                 slopepoints.add(temp[j]);
             }
             pre = slope;
         }
          
         if (slopepoints.size() >= 3){
             int numPoints = slopepoints.size();
             Point[] pointstmp = slopepoints.toArray(new Point[numPoints]);
             Arrays.sort(pointstmp);
             if (p.compareTo(pointstmp[0]) < 0){
                 numSegs++;
                 segs.add(new LineSegment (p, pointstmp[numPoints-1]));
             }
          }
      }
  }
 
    public           int numberOfSegments()        // the number of line segments
    {
        return numSegs;
    }
    
    public LineSegment[] segments()                // the line segments
    {
     
     LineSegment[] realsegs = new LineSegment[numSegs];
     int n = 0;
     for(LineSegment each : segs)
     {
      realsegs[n++] = each;
     }
     return realsegs;
    }
    
    public static void main(String[] args) {
    // read the n points from a file
    In in = new In(args[0]);
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
        int x = in.readInt();
        int y = in.readInt();
        points[i] = new Point(x, y);
    }

    // draw the points
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
        p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();
    }
}