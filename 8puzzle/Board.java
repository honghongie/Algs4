import java.lang.Math;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
public class Board {
    private int[][] boardblocks;         //content of board
    private int n = 0;                   //width of block
    
    
    public Board(int[][] blocks)         // construct a board from an n-by-n array of blocks
                                           // (where blocks[i][j] = block in row i, column j)
    {
        n = blocks.length;
        boardblocks = new int[n][n];
        if (blocks == null || n == 0)
            throw new java.lang.NullPointerException();
        
        for (int i = 0; i < n; i ++ ){     // convert 2D array to 1D
            for (int j = 0; j < n; j ++){
                if (blocks[i][j] < 0){
                    throw new java.lang.NullPointerException();
                }
                boardblocks[i][j] = blocks[i][j]; 
            }
        }
    }
    
    public int dimension()                 // board dimension n
    {
        return n;
    }
    
    private int[] dimConvert(){
        int[] onedimblock = new int[n * n];
        int k = 0;
        for (int i = 0; i < n; i ++ ){     // convert 2D array to 1D
            for (int j = 0; j < n; j ++){
                onedimblock[k ++] = boardblocks[i][j];
            }
        }
        return onedimblock;
    }
    
    public int hamming()                   // number of blocks out of place
    {
        int[] onedim = this.dimConvert();
        int nums = onedim.length - 1;
        
        int hamdist = 0;
        for (int i = 0; i < nums; i ++){
            if ((onedim[i] != 0) && (onedim[i] != (i + 1))){
                hamdist ++;
            }
        }
        if (onedim[nums] != 0)
            hamdist ++;
        return hamdist;
    }
    
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        int manhdist = 0;
        int value, orix, oriy;
        for (int i = 0; i < n; i ++){
            for (int j = 0; j <n; j ++){
                value = boardblocks[i][j];
                if (value != 0){
                    orix = (value - 1) / n;
                    oriy = (value - 1) % n;
                    manhdist += (Math.abs(i - orix) + Math.abs(j - oriy)); 
                }
            }        
        }
        return manhdist;
    }
    
    public boolean isGoal()                // is this board the goal board?
    {
        if ((this.hamming() == 0) && (this.manhattan() == 0))
            return true;
        else
            return false;
    }
    
    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {   
        Board twinboard = new Board(boardblocks);
        for (int i = n - 1; i >= 0; i --){
            for (int j = n - 1; j > 0; j --){
                if (boardblocks[i][j] != 0 && boardblocks[i][j - 1] != 0){
                    twinboard.swap(i, j, i, j - 1);
                    return twinboard;
                }
            }
        }
        return twinboard;
    }
    
    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return Arrays.deepEquals(this.boardblocks, that.boardblocks);
    }
    
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        List<Board> obj = new ArrayList<Board>(); 
        
        int x = this.getIndex(0)[0];
        int y = this.getIndex(0)[1];
        
        Board tmp = new Board(boardblocks);
        boolean hasneighbor = tmp.swap(x, y, x, y - 1);
        if (hasneighbor){
            obj.add(tmp);
        }
        
        tmp = new Board(boardblocks);
        hasneighbor = tmp.swap(x, y, x - 1, y);
        if (hasneighbor){
            obj.add(tmp);
        }
        
        tmp = new Board(boardblocks);
        hasneighbor = tmp.swap(x, y, x, y + 1);
        if (hasneighbor){
            obj.add(tmp);
        }
        
        tmp = new Board(boardblocks);
        hasneighbor = tmp.swap(x, y, x + 1, y);
        if (hasneighbor){
            obj.add(tmp);
        }
        
        return obj;
    }
    private boolean swap(int i, int j, int indexi, int indexj){
        if (indexi < 0 || indexi >= n || indexj < 0 || indexj >=n){
            return false;
        }
        int exch = boardblocks[indexi][indexj];
        boardblocks[indexi][indexj] = boardblocks[i][j];
        boardblocks[i][j] = exch;
        return true;
    }
        
    
    private int[] getIndex(int nums){
        int[] index = new int[2];
        for (int i = 0; i < n; i ++){
            for (int j = 0; j < n; j ++){
                if (boardblocks[i][j] == nums){
                    index[0] = i;
                    index[1] = j;
                    return index;
                }
            }
        }
        return index;
    }
    
    
    public String toString()               // string representation of this board (in the output format specified below)
    {
        String ts = "";
        ts += String.valueOf(n)+"\n";
        for (int i = 0; i < n; i ++){
            for (int j = 0; j < n; j ++){
                ts += (" " + String.valueOf(boardblocks[i][j]));
            }
            ts += "\n";
        }
        return ts;
    }

    public static void main(String[] args) // unit tests (not graded)
    {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        }
        Board initial = new Board(blocks);
        
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        }
        Board initial2 = new Board(blocks);
        
        StdOut.println("initial blocks" + initial.toString());
        StdOut.println("initial2 blocks" + initial2.toString());
        StdOut.println(initial.dimension());
        StdOut.println(initial.hamming());
        StdOut.println(initial.manhattan());
        StdOut.println(initial.isGoal());
        StdOut.println(initial.twin().toString());
        
        for (Board bd: initial.neighbors())
            StdOut.println(bd.toString());
        
        StdOut.println(initial.equals(initial2));
    }   
}