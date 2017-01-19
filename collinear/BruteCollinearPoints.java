import java.util.Arrays;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut; 
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {
 private int len;
 private int numSegs = 0;
 private LineSegment[] segs;

 public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
 {
  len = points.length;
  if (len == 0) throw new java.lang.NullPointerException();
  
// check duplicate and null
  for (int i = 0; i < len; i ++)
  {
   if (points[i] == null) throw new java.lang.NullPointerException();
   for (int j =i + 1; j <len; j ++){
       if (points[i].compareTo(points[j]) == 0)
           throw new java.lang.IllegalArgumentException();
   }
  }
  segs = new LineSegment[len];

//  Arrays.sort(points, Point.POINT_CMP);

  for (int i = 0; i < len - 3; i ++){
   for (int j = i + 1; j < len - 2; j ++){
    double slope1 = points[i].slopeTo(points[j]);
    
    for (int k = j + 1; k < len -1; k ++){
     double slope2 = points[j].slopeTo(points[k]);
     
     for (int l = k + 1; l < len; l ++){
       double slope3 = points[k].slopeTo(points[l]);
       
       if ((Double.compare(slope1, slope2)==0) && (Double.compare(slope2, slope3) == 0)){
           Point[] tmp = new Point[4];
           tmp[0] = points[i];
           tmp[1] = points[j];
           tmp[2] = points[k];
           tmp[3] = points[l];
           segs[numSegs++] = GetLineSeg(tmp);
       }
     }     
    }
   }
  }
 }
 
  private LineSegment GetLineSeg(Point[] points)
  {
      Point min = points[0];
      Point max = points[0];
      for (Point p : points){
          if (p.compareTo(min) < 0)
              min = p;
          if (p.compareTo(max) > 0)
              max = p;
      }
      LineSegment lineseg = new LineSegment(min,max);
      return lineseg;
  }

    public           int numberOfSegments()        // the number of line segments
    {
     return numSegs;
    }
    
    public LineSegment[] segments()                // the line segments
    {
     LineSegment[] realsegs = new LineSegment[numSegs];
     for (int i = 0; i < numSegs; i++)
     {
      realsegs[i] = segs[i];
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
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();
    }
}