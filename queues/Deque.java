import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.NullPointerException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut; 

public class Deque<Item> implements Iterable<Item> {
    
    //throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator
    
    private Node<Item> first;
    private Node<Item> last;
    //private int cnt; //size of the deque
    
    private class Node<Item>
    {
        Item ele;
        Node<Item> forward;
        Node<Item> backward;
    }
    
    public Deque()                           // construct an empty deque
    {
        first = null;
        last = null;
        //cnt = 0;
    }
    
    public boolean isEmpty()                 // is the deque empty?
    {
        return first == null && last == null;
    }
    
    public int size()                        // return the number of items on the deque
    {
        if (isEmpty()) return 0;
        int count = 0;
        for (Item i : this) count ++;
        return count;
    }
    
    public void addFirst(Item item)          // add the item to the front
    {
        if (item == null)
        {
            throw new java.lang.NullPointerException();
        }
        
        if (isEmpty())
        {
            Node<Item> tempnode = new Node<Item>();
            tempnode.ele = item;
            tempnode.forward = null;
            tempnode.backward = null;
            first = tempnode;
            last = tempnode;
            //StdOut.println("Successed of adding the first!");
            return;
        }
        Node<Item> tempnode = first;
        first = new Node<Item>();
        first.ele = item;
        first.forward = tempnode;
        first.backward = null;
        tempnode.backward = first;
            //cnt ++;
        
        //StdOut.println("Successed!");
    }
    
    public void addLast(Item item)           // add the item to the end
    {
        if (item == null)
        {
            throw new java.lang.NullPointerException();
        }
        
        if (isEmpty())
        {
            Node<Item> tempnode = new Node<Item>();
            tempnode.ele = item;
            tempnode.forward = null;
            tempnode.backward = null;
            first = tempnode;
            last = tempnode;
            //cnt ++;
            return;
        }
        Node<Item> tempnode = last;
        last = new Node<Item>();
        last.ele = item;
        last.forward = null;
        last.backward = tempnode;
        tempnode.forward = last;
        //StdOut.println("Successed!");   
    }
    
    public Item removeFirst()                // remove and return the item from the front
    {
        if (isEmpty())
        {
            throw new java.util.NoSuchElementException();
        }
        
        Node<Item> tempnode = first;
        first = first.forward;
        if (first == null)
        {
            last = null;
        }
        else
        {
            first.backward = null;
        }
        Item firstitem = tempnode.ele;
        //cnt--;
        return firstitem;
        
    }
    
    public Item removeLast()                 // remove and return the item from the end
    {
        if (isEmpty())
        {
            throw new java.util.NoSuchElementException();
        }
        Node<Item> tempnode = last;
        last = tempnode.backward;
        if (last == null) 
        {
            first = null;
        }
        else
        {
            last.forward = null;
        }
        
        Item lastitem = tempnode.ele;
        //cnt --;
        return lastitem;     
        
    }
    
    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    {
        return new DequeIterator();
    }
    
    private class DequeIterator implements Iterator<Item>
    {
        private Node<Item> current = first;
        
        public boolean hasNext() { return current != null; }
        public Item next() 
        {
            if (current == null)
            {
                throw new java.util.NoSuchElementException();
            }
            Item item = current.ele;
            current = current.forward;
            return item;
        }
        public void remove()
        {
            throw new java.lang.UnsupportedOperationException();
        }
    }
    public static void main(String[] args){};   // unit testing
}