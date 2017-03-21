import java.util.List;
import java.util.ArrayList;
import java.io.Console;
// NOTE: No other Java libraries allowed (automatic 0)
/*
THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
A TUTOR OR CODE WRITTEN BY OTHER STUDENTS.  ___ANDREW C JONES 3/20/17____
*/



class EMD<K extends Comparable<K>, V> implements RangeMap<K,V> {
    class Node {
        Node left;
        Node right;
        KVPair<K,V> kv;
    }

    private Node root;

    // Add the key and a value to your RangeMap. (For EMD, this would be the
    // name of the movie (key) and its description (value), respectively.) If
    // there is a duplicate key, the old entry should be overwritten with the
    // new value.

    // @param key - used to identify and sort/rank the value to add
    // @param value - data associated with key
    // @returns nothing - after execution, the KV pair will be placed in the proper position in the tree
    public void add(K key, V value) {
        // TODO: Implement me(basic score)
        if(root == null) //if tree is empty
        {
            root = new Node();
            root.kv = new KVPair<K, V>(key, value);
            return;
        }

        // < 0 if root key less than argument key, 0 if equal, > 0 if root key > arg key
        int cmp = root.kv.key.compareTo(key);

        if(cmp < 0)  //root is less than key so go right
        {
            //if null, this is the proper position for the given kv pair, so create a new node
            if (root.right == null)
            {
                root.right = new Node();
                root.right.kv = new KVPair<K,V>(key, value);
            }
            else //if not null, recur down to right subtree
                addRecur(key, value, root.right);
        }
        else if (cmp > 0) //root is greater than key so go left
        {
            //if null, this is the proper position for the given kv pair, so create a new node
            if(root.left == null)
            {
                root.left = new Node();
                root.left.kv = new KVPair<K, V>(key, value);
            }
            else //if not null, recur down to left subtree
                addRecur(key, value, root.left);
        }
        else //root is equal to key so replace its kv
            root.kv = new KVPair<K, V>(key, value);
    }

    //recursive helper method for the add method -- 
    //traverses the BST until the proper position for the given key is found
    //
    // @param key - used to identify and sort/rank the value to add
    // @param value - data associated with key
    // @param node, type Node - our current position in the BST. the root element for the subtree into which we must insert key/value.
    public void addRecur(K key, V value, Node node) 
    {
         // < 0 if root key less than argument key, 0 if equal, > 0 if root key > arg key       
        int cmp = node.kv.key.compareTo(key);

        if(cmp < 0)  //root is less than key so go right
        {
            //if right child is null, this is proper position for KV pair, insert it here
            if (node.right == null)
            {
                node.right = new Node();
                node.right.kv = new KVPair<K,V>(key, value);
            }
            else //if right child not null, recur on right subtree
                addRecur(key, value, node.right);
        }
        else if (cmp > 0) //root is greater than key so go left
        {
            //if left child is null, this is proper position for KV pair, insert it here
            if(node.left == null)
            {
                node.left = new Node();
                node.left.kv = new KVPair<K, V>(key, value);
            }
            else //if left child not null, recur on left subtree
                addRecur(key, value, node.left);
        }
        else //node (root of current subtree) is equal to key so replace its kv
            node.kv = new KVPair<K, V>(key, value);

    }

    // Retrieve the value corresponding to key, or return null if the key is
    // not in your RangeMap. The comparison between keys should be exact.
    // (For EMD, this would correspond to the lower-case name of the movie
    // (key).)
    public V get(K key) 
    {
        // DONE: Implement me(basic score)
        if (root == null) //empty tree
            return null;

        // if negative, root is smaller than key so go right. if positive, root is larger than key so go left. 
        int cmp = root.kv.key.compareTo(key);
        if(cmp < 0)
            return getRecur(key, root.right);
        else if(cmp > 0)
            return getRecur(key, root.left);
        else //they are equal return root's value
            return root.kv.value;
    }

    //recursively implements binary search on the tree, either returns the value of the node wiht key 
    // @param key, 
    // or returns null if it doesn't exist
    // @param node is the root of the subtree over which we binary search for a given recursive call
    public V getRecur(K key, Node node) 
    {
        //reached end of tree, target node doesn't exist
        if (node == null)
            return null;

        // if negative, root is smaller than key so go right. if positive, root is larger than key so go left. 
        int cmp = node.kv.key.compareTo(key);
        if(cmp < 0)
            return getRecur(key, node.right);
        else if(cmp > 0)
            return getRecur(key, node.left);
        else
            return node.kv.value;

    }

    // Return the key in the RangeMap that's lexicographically next after
    // 'key', or return null otherwise. (For EMD, this would correspond to
    // the name of the movie that's next after the one specified. 
    // If key is exactly the name of a movie, next should still return
    // the following movie in the database.)
    // Note that key does not have to exist in the database.

    // Algorithm explanation - similar to binary search except we do not know exactly what value we want in advance,
    // so we maintain a current element. if a given node is larger than the key but smaller than our current "next" candidate,
    // we replace the next candidate with that key and traverse left (to find potentially a node which is smaller than current candidate
    // but later than key) - if it is smaller than the key, we traverse right to find a node which is larger than the current key. 
    // if they are equal, we traverse right to find something larger
    public K next(K key) {
        // TODO: Implement me(EC for intermediate score)

        
        if(root == null)
            return null;
        K currentNext;

        int cmp = root.kv.key.compareTo(key);
        if (cmp <= 0)
            return nextRecur(key, currentNext, root.right);
        else // root is larger than key
        {
            currentNext = root.kv.key;
            return nextRecur(key, currentNext, root.left);
        }
        
    }

    // Return a list of key-value pairs in the RangeMap that are between the
    // strings start and end, both inclusive. The list should be in
    // lexicographic order. If no keys match, the method should return the empty list.
    // (For EMD, range would return an alphabetic list of movies titles that
    // are between the two parameter strings). Note that neither start nor
    // end have to exist in the database.
    public List<KVPair<K,V>> range(K start, K end) {
        // TODO: Implement me(EC for full score)
        return null;
    }

    // Removes the key-value pair with key specified by the parameter from
    // the RangeMap. Does nothing if the key does not exist. 
    // Extra Credit beyond 100%
    public void remove(K key) {
        // TODO: Implement me(EC beyond full score)
    }

    /////////////////////////////////////////////////
    // You shouldn't have to change anything below //
    /////////////////////////////////////////////////
    public static void main(String[] args) {
        EMD<String, String> emd = new EMD<String, String>();
        In in;
        In inputFile = null;

        // read from a given input file instead?
        if(args.length > 0) {
            inputFile = new In(args[0]);
        }

        while(true) {
            if(inputFile != null ? !inputFile.hasNextLine() : !StdIn.hasNextLine()) {
                break;
            }
            String input =(inputFile != null ? inputFile.readLine() : StdIn.readLine());

            // process commands from the user
            String[] line = input.split("/");
            switch(line[0].charAt(0)) {
                // Open and read a file with "Movie/Information..." lines
                case 'o':                // e.g. "open/movies.txt"
                    in = new In(line[1]);
                    // clean out the old movies
                    emd = new EMD<String, String>();
                    while(in.hasNextLine()) {
                        String[] arr = in.readLine().split("/");
                        // Test only lower case strings for simplicity
                        emd.add(arr[0].toLowerCase(), arr[1]);
                    }
                    break;

                    // Add a new movie
                case 'a':                // e.g. "add/Shredder/Foot Clan Ninja"
                    System.out.println("Adding '" + line[1] + "' ...");
                    emd.add(line[1].toLowerCase(), line[2]);
                    break;

                    // Look up a movie
                case 'g':                // e.g. "get/shredder"
                    System.out.println("Looking up '" + line[1] + "' ...");
                    System.out.println(emd.get(line[1].toLowerCase()));
                    break;

                    // Find next movie after a string(like auto-complete)
                case 'n':                // e.g. "next/shred" would return "shredder"
                    System.out.println("Looking up next movie after '" + line[1] + "' ...");
                    System.out.println(emd.next(line[1].toLowerCase()));
                    break;

                    // Remove a movie
                case 'r':                // e.g. "remove/Shredder"
                    System.out.println("Removing '" + line[1] + "' ...");
                    emd.remove(line[1].toLowerCase());
                    break;

                    // Find movies in a range
                case 'f':                // e.g. "find/shed/shre/"
                    // might print "sherlock" and "shrek",
                    // but not "shredder" since it's outside
                    // the range
                    List<KVPair<String, String>> list;
                    System.out.println("Searching range of '" + line[1] + "'-'" + line[2] + "' ...");
                    list = emd.range(line[1].toLowerCase(), line[2].toLowerCase());
                    if(list == null)
                    {
                        System.out.println("Not found.\n");
                    } else {
                        // print out all movies in this range
                        for(KVPair<String, String>kv : list)
                            System.out.println(kv);
                    }
                    break;
                default:
                    System.out.println("Unknown command. ");
                    break;
            }
        }
    }
};