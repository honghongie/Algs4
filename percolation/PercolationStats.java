import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.lang.*;

public class PercolationStats {
   private double[] results;
   private double sum;
   private int n;
   private double stdev;
   
   public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
   {
       results = new double[trials];
       for(int i = 0; i < trials; i++)
       {
           Percolation per = new Percolation(n);
           
           while(!per.percolates()) // choose a random site to open,until it percolate
           {
               int row = StdRandom.uniform(1, n+1);
               int col = StdRandom.uniform(1, n+1);       
               per.open(row, col);          
               
           }
           int opened = per.numberOfOpenSites();       
           results[i] = (double)opened/(n*n);
       }
   }
   
   public double mean()                          // sample mean of percolation threshold
   {
       sum = 0;
       n = results.length;
       for (int i = 0; i < n; i++)
       {
           sum += results[i];
       }
       return sum/n;
   }

   public double stddev()                        // sample standard deviation of percolation threshold
   {
       double Sumsq=0;
       for (int i = 0; i < n; i++)
       {
           double gap = results[i] - sum/n;
           Sumsq += gap*gap;
       }
       stdev = Sumsq/(n-1);
       return stdev;
   }
   
   public double confidenceLo()                  // low  endpoint of 95% confidence interval
   {
       return sum/n - 1.96*stdev/Math.sqrt(n);
       
   }
   
   public double confidenceHi()                  // high endpoint of 95% confidence interval
   {
       return sum/n + 1.96*stdev/Math.sqrt(n);
       
   }  
   
   public static void main(String[] args)        // test client (described below)
   {
       int n = StdIn.readInt();
       int trials = StdIn.readInt();
       if (n <= 0 || trials <= 0)
       {
           throw new java.lang.IllegalArgumentException();
       }
       PercolationStats perstat = new PercolationStats(n,trials);
       StdOut.print("mean =                    ");
       StdOut.println(perstat.mean());
       StdOut.print("stddev =                  ");
       StdOut.println(perstat.stddev());
       StdOut.print("95% confidence interval = ");
       StdOut.print(perstat.confidenceLo());
       StdOut.print("   ");
       StdOut.println(perstat.confidenceHi());
   }
}