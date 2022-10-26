import java.util.*;
public class AutoComplete {
	
	static class TrieNode {		
	    char data;     
	    LinkedList<TrieNode> children = new LinkedList<>();
	    boolean isEnd = false; 
 
	    //Constructor, Time O(1), Space O(1)
	    TrieNode(char c) {
	    	data = c;
	    } 
	    
	    //find the node by char, the same functionality as children[ch] in array implementation 
	    //Time O(k), Space O(1), k is number of children of this node 
	    TrieNode getChild(char c) {
	    	if (children != null)
	    		for (TrieNode child : children)
	    			if (child.data == c)
	    				return child;
	        return null;
	    }	    
	}
 
	static class Trie {
		
	    TrieNode root = new TrieNode(' '); 
	   	    
	    //Add a word to trie, Iteration, Time O(s), Space O(s), s is word length
	    void insert(String word) {       
	        TrieNode node = root; 	 
	        for (char ch : word.toCharArray()) {	           
	            if (node.getChild(ch) == null) 
	            	node.children.add(new TrieNode(ch));
	            node = node.getChild(ch);	            		           
	        }
	        node.isEnd = true;
	    }
	       	    
	    //find the node with prefix's last char, then call helper to find all words using recursion
		//Recursion, Time O(n), Space O(n), n is number of nodes involved (include prefix and branches)
	    List<String> autocomplete(String prefix) {     
		    TrieNode node = root;
		    List<String> res = new ArrayList<String>(); 
		    for (char ch: prefix.toCharArray()) { //find end of prefix
		    	node = node.getChild(ch);	     
		    	if (node == null) 
		    		return new ArrayList<String>();      
		    } 
		    helper(node, res, prefix.substring(0, prefix.length()-1));
		    return res;
		}
		
		//recursion helper, Time O(n), Space O(n), n is number of nodes in branches
	    void helper(TrieNode node, List<String> res, String prefix) {		
			if (node.isEnd)  
				res.add(prefix + node.data);		
			for (TrieNode child : node.children) 				
				helper(child, res, prefix + node.data);						
		}	
	}    
	public static void main(String[] args) {   
		java.util.Scanner input = new java.util.Scanner(System.in);       
		Trie t = new Trie();            
        t.insert("amazon"); 
        t.insert("amazon prime"); 
		t.insert("amazing"); 			 
        t.insert("amazing spider man"); 
        t.insert("amazed");
        t.insert("apple"); 
		t.insert("car");
		t.insert("electric car");
		t.insert("caramel");
		t.insert("care");
        System.out.println("Enter a word to predict");
		System.out.println(t.autocomplete(input.nextLine()));			
	}
}