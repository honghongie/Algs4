import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {  //use resizing array to implement
    
    private Item[] s; //array to save the elements
    private int N = 0; // how many elements in the queue
    
    public RandomizedQueue()                 // construct an empty randomized queue
    {
        s = (Item[]) new Object[0];
    }
    
    private void resize(int capacity)        // to resize the array 
    {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++)
            copy[i] = s[i];
        s = copy;
    }
    
    public boolean isEmpty()                 // is the queue empty?
    {
        return N == 0;
    }
    public int size()                        // return the number of items on the queue
    {
        //if (isEmpty()) return 0;
        //int cnt = 0;
        //for (Item i : this) cnt ++;
        return N;
    }
    
    public void enqueue(Item item)           // add the item, check whether to resize
    {
        if (item == null)
        {
            throw new NullPointerException();
        }
         
        if (N == 0){ resize(2); }
        else if (N == s.length) resize(2 * s.length);
        s[N++] = item;
        
    }
    
    public Item dequeue()                    // remove and return a random item, check whether to resize
    {
        if (N != 0)
        {
            int index = StdRandom.uniform(N);
            Item rditem = s[index];
            s[index] = s[--N];
            if (N >0 && N == s.length/4) resize (s.length/2);
            return rditem;        
        }
        else
        {
            throw new java.util.NoSuchElementException();
        }     
    }
    
    public Item sample()                     // return (but do not remove) a random item
    {
        if (N == 0)
        { 
            throw new java.util.NoSuchElementException();
        }
        else
        {
            int index = StdRandom.uniform(N);
            return s[index];
        }      
    }
    
    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    {
        return new RandomizedQueueIterator<Item>(s);
    }
    
    private class RandomizedQueueIterator<Item> implements Iterator<Item>
    {   
        private RandomizedQueue<Item> shuffleds = new RandomizedQueue<Item>();
        
        public RandomizedQueueIterator(Item[] items)
        {
            for (Item o : items)
            {
                if (o ==null)
                    break;
                else
                    shuffleds.enqueue(o);
            }
        }
        public boolean hasNext(){ return !shuffleds.isEmpty();}
        public Item next()
        {
            if (shuffleds.isEmpty()){ throw new java.util.NoSuchElementException();}
            return shuffleds.dequeue();
        }
        public void remove(){ throw new java.lang.UnsupportedOperationException();}
    }
    
    public static void main(String[] args){}   // unit testing
    
}