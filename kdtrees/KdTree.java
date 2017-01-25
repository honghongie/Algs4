import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.Stack;



public class KdTree {
    
    private int cnt; // points in the set
    private Node root;
    
    private static class Node {
        private Point2D p;       // the point
        private RectHV rect;     // the axis-aligned rectangle corresponding to this node
        private Node lb;
        private Node rt;         // the left/bottom, right/top subtree
        private boolean segmentX; // use x-cooridnates to segment the space or y
        
        public Node(Point2D p, RectHV rect, Node lb, Node rt, boolean segmentX){
            this.p = p;
            this.rect = rect;
            this.lb = lb;
            this.rt = rt;
            this.segmentX = segmentX;
        }
    }
        
    private Node put (Node h, Point2D p){
        if (h.p == null && h.rect.contains(p)){
            h.p = p;                  // update node value
            double xmin = h.rect.xmin();     // update node lb, rt
            double ymin = h.rect.ymin();
            double xmax = h.rect.xmax();
            double ymax = h.rect.ymax();
            
           
            Node lb, rt;
            if (h.segmentX){          // rec seg by x, change segmentX to false
                RectHV lbrect = new RectHV (xmin, ymin, h.p.x(), ymax);
                RectHV rtrect = new RectHV (h.p.x(), ymin, xmax, ymax);
                h.lb = new Node(null, lbrect, null, null, false);
                h.rt = new Node(null, rtrect, null, null, false);
            }
            else{  // rec seg by y, change sementX to true
                
                RectHV lbrect = new RectHV (xmin, ymin, xmax, h.p.y());
                RectHV rtrect = new RectHV (xmin, h.p.y(), xmax, ymax);
                h.lb = new Node(null, lbrect, null, null, true);
                h.rt = new Node(null, rtrect, null, null, true);
            }
            return h;
        }
        if (h.lb.rect.contains(p)){
            h.lb = put (h.lb, p);
        }
        else if (h.rt.rect.contains(p)){
            h.rt = put (h.rt, p);
        } 
        return h; 
    }
    
    
    public         KdTree()                               // construct an empty set of points 
    {
        root = null; 
        cnt = 0;
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
        if (p == null)
            throw new java.lang.NullPointerException();
        
        if (isEmpty()){
            RectHV rect = new RectHV(0, 0, 1, 1);
            RectHV lbrect = new RectHV(0, 0, p.x(), 1);
            RectHV rtrect = new RectHV(p.x(), 0, 1, 1);
            boolean segX = true;
            
            Node lb = new Node(null, lbrect, null, null, false);
            Node rt = new Node(null, rtrect, null, null, false);
            root = new Node(p, rect, lb, rt, true);
            cnt++;
            return;
        }
        else{
            if (!contains(p)){
                put(root, p);
                cnt++;
            }
        }
    }
    
    public           boolean contains(Point2D point)            // does the set contain point p?
    {
        if (point == null){
            throw new java.lang.NullPointerException();
        }
        if (root == null){
            return false;
        }
        
        Node h = root;
        while(h.p != null){
            if (h.p.equals(point))
                return true;
            else if (h.lb.rect.contains(point))
                h = h.lb;
            else if (h.rt.rect.contains(point))
                h = h.rt;
        }
        return false;
    }
    
    
    public              void draw()                         // draw all points to standard draw 
    {
        if (root == null)
            return;
        Stack<Node> nodes = new Stack<Node>();
        Node h;
        nodes.push(root);
        while(nodes.size()>0){
            h = nodes.pop();
            h.p.draw();
            if (h.segmentX){
                StdDraw.line(h.p.x(),h.rect.ymin(),h.p.x(),h.rect.ymax());
            }
            else{
                StdDraw.line(h.rect.xmin(),h.p.y(),h.rect.xmax(),h.p.y());
            }
            if (h.lb.p!=null)
                nodes.push(h.lb);
            if (h.rt.p!=null)
                nodes.push(h.rt);
        }    
    }
    
    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle 
    {
        if (rect == null)
            throw new java.lang.NullPointerException();
        if (root == null)
            return null;
        
        Stack<Point2D> inside = new Stack<Point2D>();
        Stack<Node> nodes = new Stack<Node>();
        Node checked;
        nodes.push(root);
        while(nodes.size()>0){
            checked = nodes.pop();
            if (rect.intersects(checked.rect)){
                if (checked.lb.p!=null)
                    nodes.push(checked.lb);
                if (checked.rt.p!=null)
                    nodes.push(checked.rt);
                if (rect.contains(checked.p)){
                    inside.push(checked.p);
                }
            }
        }
        return inside;
    }
    
    public           Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
    {
        // compare the squares of the two distances
        if (isEmpty())
            return null;
        
        Stack<Node> nodes = new Stack<Node>();
        double minDist = Double.POSITIVE_INFINITY;
        Node checked = null;
        double distRect, distP2P;
        Point2D minP = null;
        
        nodes.push(root);
        while(nodes.size()>0){
            checked = nodes.pop();
            distRect = checked.rect.distanceSquaredTo(p);
            if (distRect < minDist){         // possible points in the subtree of the node
                distP2P = checked.p.distanceSquaredTo(p);
                if (Double.compare(distP2P,minDist)<0){
                    minDist = distP2P;
                    minP = checked.p;
                }
                if (checked.lb.p!=null)
                    nodes.push(checked.lb);
                if (checked.rt.p!=null)
                    nodes.push(checked.rt);
            }
        }
        return minP;
    }

    public static void main(String[] args)                  // unit testing of the methods (optional) 
    {
        String filename = args[0];
        In in = new In(filename);

        StdDraw.enableDoubleBuffering();

        // initialize the two data structures with point from standard input
        //PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            //brute.insert(p);
        }
        //StdOut.println(brute.size());
        
        Point2D q = new Point2D(0.975528,0.345492);
        StdOut.println(kdtree.contains(q));
        
        Point2D s = new Point2D(0.97,0.34);
        StdOut.println(kdtree.nearest(s).toString());
    }
}