import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AutoComplete {
	
	static class TrieNode {		
	    char data;     
	    LinkedList<TrieNode> children = new LinkedList<>();
	    boolean isEnd = false; // Checks if the word entered is a valid word
 
	    //Constructor, Time O(1), Space O(1)
	    TrieNode(char c) {
	    	data = c;
	    } 
	    
	    //find the node by char, the same functionality as children[ch] in array implementation 
	    //Time O(k), Space O(1), k is number of children of this node 
	    TrieNode getChild(char c) {
	    	if (children != null)
	    		for (TrieNode child : children)
	    			if (child.data == c) // is entered character same as the node
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
	        node.isEnd = true; // sets the value to true if entered character/ word is a valid word/character
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
			if (node.isEnd)  // and 
				res.add(prefix + node.data);		
			for (TrieNode child : node.children) 				
				helper(child, res, prefix + node.data);						
		}	
	}    
	public static void main(String[] args) {   
		java.util.Scanner input = new java.util.Scanner(System.in);       
		Trie t = new Trie(); 
		String word;
		try {
			File file = new File("Data.txt");
			if(file.createNewFile()){
				System.out.println("File " + file.getName() + " is created successfully");
			}
			else {
				BufferedReader br = new BufferedReader(new FileReader(file));
				while((word = br.readLine()) != null){
					t.insert(word);
				}
			}

		}
		catch(IOException exception){
			System.out.println("An unexpected error occured");
			exception.printStackTrace();
		}
		
        System.out.println("Enter a word to predict");
		System.out.println(t.autocomplete(input.nextLine()));			
	}
}