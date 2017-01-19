import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;
import java.util.LinkedList;

public class Solver {
    private MinPQ<SearchNode> mq = new MinPQ<SearchNode>();
    private MinPQ<SearchNode> twinmq = new MinPQ<SearchNode>();
    private SearchNode goal = null;
    private boolean solvable = true;
    
    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        mq = new MinPQ<SearchNode>(hamdist());
        twinmq = new MinPQ<SearchNode>(hamdist());
        
        SearchNode initnode = new SearchNode(initial, 0, null);
        mq.insert(initnode);
        
        Board twin = initial.twin();
        SearchNode twinnode = new SearchNode(twin, 0, null);
        twinmq.insert(twinnode);
        
        boolean solved = false;
        //run at most 5 loops to observe
        //int flag = 0;
        //while(flag < 5){
        //    if ( !solved )
        //        solved = solve();
        //    flag ++;
        //}
        while( !solved ){
            solved = solve();
        }
    }
    
    private Comparator<SearchNode> hamdist() {
        return new Comparator<SearchNode>(){
            @Override
            public int compare(SearchNode node1, SearchNode node2){
                int ham1 = node1.bd.manhattan();
                int ham2 = node2.bd.manhattan();
                return ham1 - ham2 + node1.moves - node2.moves;
            }
        };
    }
    
    
    private class SearchNode {
        private Board bd;
        private int moves;
        private SearchNode prev;
        
        public SearchNode(Board b, int m, SearchNode p){
            bd = b;
            moves = m;
            prev = p;
        }
        
        public boolean equals(Object y){
            if (y == this) return true;
            if (y == null) return false;
            if (y.getClass() != this.getClass()) return false;
            
            SearchNode that = (SearchNode) y;
            if (that.bd.equals(bd))
                return true;
            else
                return false;
        } 
    }
    
    private boolean solve(){
        SearchNode node = mq.delMin();
        //StdOut.println("mq deque");
        //StdOut.println(node.bd.toString());
        if (node.bd.isGoal()){
            goal = node;
            return true;
        }
        
        for (Board bd : node.bd.neighbors()){
            SearchNode n = new SearchNode(bd, node.moves + 1, node);
            if (n.equals(node.prev)){                  //same as previous one of node, not add to the queue
                continue;
            }
            mq.insert(n);
            //StdOut.println("Enque mq with n");
            //StdOut.println(n.bd.toString());
        }
        
        //For the twinnode
        SearchNode twinnode = twinmq.delMin();
        //StdOut.println("twinmq deque");
        //StdOut.println(twinnode.bd.toString());
        if (twinnode.bd.isGoal()){
            solvable = false;
            return true;
        }
        
        for (Board bd : twinnode.bd.neighbors()){
            SearchNode n = new SearchNode(bd, node.moves + 1, node);
            if (n.equals(twinnode.prev)){
                continue;
            }
            twinmq.insert(n);
            //StdOut.println("Enque mq with n");
            //StdOut.println(n.bd.toString());
        }
        Board twin = node.bd.twin();
        twinnode = new SearchNode(twin, 0, null);
        twinmq.insert(twinnode);
        
        return false;
    }
    
    
    public boolean isSolvable()            // is the initial board solvable?
    {
        return solvable;
    }
    
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        if (!solvable)
            return -1;
        else
            return goal.moves;
    }
    
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        if (!solvable)
            return null;
        else{
            LinkedList<Board> ll = new LinkedList<Board>();
            SearchNode node = goal;
            ll.addFirst(node.bd);
            while( node.prev != null){
                ll.addFirst(node.prev.bd);
                node = node.prev;
            }
            return ll;
        }
    }
    
    public static void main(String[] args) // solve a slider puzzle (given below)
    {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        StdOut.println("Initialize blocks");
        StdOut.println(initial.toString());
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}