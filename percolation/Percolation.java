import edu.princeton.cs.algs4.WeightedQuickUnionUF;

    
public class Percolation {
    
    private boolean[][] status; // record whether one site is opened
    private int n;
    private WeightedQuickUnionUF wqu;
    
    public Percolation(int size) // create n-by-n grid, with all sites blocked
    {
        if (size <= 0)
        {
            throw new java.lang.IllegalArgumentException();
        }
        // build a unionUF set, add begining and ending node, and union them to the first and last row
        int N = size*size;
        wqu = new WeightedQuickUnionUF(N+2);
        
        //label each number that they are closed.
        status = new boolean[size][size];
        n = size;
    }
    
    
    public void open(int row, int col)// open site (row, col) if it is not open already
    {
        if(row <= 0 || col <= 0 || row > n || col > n)
        {
            throw new java.lang.IndexOutOfBoundsException();
        }
        status[row-1][col-1] = true;
        int index = (row-1)*n + col;
        // the first row is connected to the virtual top
        
        if(row == 1)
        {
            wqu.union(index,0);
        }
        //last row is connected to the virtual bottom
        if(row == n)
        {
            wqu.union(index,n*n+1);
        }
        
        // check whether the four neighborhood sites are open
        if(row>1 && isOpen(row-1,col))
        {
            wqu.union(index,index-n);
        }
        
        if(row<n && isOpen(row+1,col))
        {
            wqu.union(index,index+n);
        }
        
        if(col>1 &&isOpen(row,col-1))
        {
            wqu.union(index,index-1);
        }
        
        if(col<n &&isOpen(row,col+1))
        {
            wqu.union(index,index+1);
        }
           
        
    }
    
    public boolean isOpen(int row, int col)// is site (row, col) open?
    {
        if (row <= 0 || col <= 0 || row > n || col > n)
        {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return status[row-1][col-1];
    }

    public boolean isFull(int row, int col)// is site (row, col) full?
    {
        if (row <= 0 || col <= 0 || row > n || col > n)
        {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (!isOpen(row,col))
        {
            return false;
        }
        int index = (row-1)*n + col;
        return wqu.connected(0, index);
    }

    public int numberOfOpenSites()// number of open sites
    {
        int Cnt = 0;
        for (int p = 0; p < n; p++)
        {
            for (int q = 0; q < n; q++)
            {   
                if (status[p][q])
                {  
                    Cnt = Cnt+1;
                }
             }               
        }         
        return Cnt;
    }
    
    public boolean percolates()// does the system percolate?
    {
        return wqu.connected(0, n*n+1);
    }
    
    public static void main(String[] args)// test client (optional)
    {
    }   

}