import java.util.*;
class LLParser{
	
	public static HashMap<Character,HashSet<String>>productions=new HashMap<>();
	public static HashMap<Character,HashSet<String>>first=new HashMap<>();
	public static HashMap<Character,HashSet<String>>follow=new HashMap<>();
	
	public static void main(String args[]){
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter number of non-terminals:");
		int n=sc.nextInt();//non-terminals
		
		for(int i=0;i<n;i++){//taking productions
			System.out.println("Enter non-terminal:");
			char non_terminal=sc.next().charAt(0);
			System.out.println("Enter number of productions for the Non-terminal:");
			int prod=sc.nextInt();
			HashSet<String>values=new HashSet<>();
			for(int j=0;j<prod;j++){
				String val=sc.next();
				values.add(val);
			}
			productions.put(non_terminal,values);
		}
		
		for(char key:productions.keySet()){//taking first for a non-terminal
			System.out.println("Enter stop to end the first for the non-terminal");
			String addFirst="";
			System.out.println("For non-terminal "+key);
			HashSet<String>temp=new HashSet<>();
			while(!addFirst.equals("stop")){
				System.out.println("Enter first:");
				addFirst=sc.next();
				if(!addFirst.equals("stop"))		temp.add(addFirst);
			}
			first.put(key,temp);
			
		}
		
		for(char key:productions.keySet()){//taking follow for a non-terminal
			System.out.println("Enter stop to end the follow for the non-terminal");
			String addFollow="";
			System.out.println("For non-terminal "+key);
			HashSet<String>temp=new HashSet<>();
			while(!addFollow.equals("stop")){
				System.out.println("Enter follow:");
				addFollow=sc.next();
				if(!addFollow.equals("stop"))		temp.add(addFollow);
			}
			follow.put(key,temp);
			
		}
		
		HashSet<String>terminals=new HashSet<>();
		//identifying terminals using first and follow
		for(Map.Entry<Character,HashSet<String>> map:first.entrySet()){
			HashSet<String>terminalss=new HashSet<>();
			terminalss=map.getValue();
			for(String str:terminalss)		terminals.add(str);
		}
		for(Map.Entry<Character,HashSet<String>> map:follow.entrySet()){
			HashSet<String>terminalss=new HashSet<>();
			terminalss=map.getValue();
			for(String str:terminalss)		terminals.add(str);
		}
		//^ is not included in parsing table so removing it
		terminals.remove("^");
		System.out.println(terminals);
		
		String[][]parsingTable=new String[n+1][terminals.size()+1];
		parsingTable[0][0]=" ";
		int i=1;
		System.out.println(productions.keySet());
		
		//initializing parsingtable
		for(char ch:productions.keySet()){
			parsingTable[i][0]=""+ch;
			i++;
		}
		int j=1;
		for(String s:terminals){
			parsingTable[0][j]=s;
			j++;
		}
		for(int u=1;u<n+1;u++){
			for(int v=1;v<terminals.size()+1;v++){
				parsingTable[u][v]="-";
			}
		}
		
		//inserting values in parsingtable
		for(Map.Entry<Character,HashSet<String>> map:productions.entrySet()){
			char key=map.getKey();
			String keyy=""+key;
			HashSet<String>temp=new HashSet<>();
			temp=map.getValue();
			for(String s:temp){
			    HashSet<String>temporary=new HashSet<>();
				char ch=s.charAt(0);
				// if first character is non-terminal take first of that non-terminal
				if(productions.containsKey(ch)){
					temporary.addAll(first.get(ch));
					
					//searching for row 
					int row=1;
					while(!parsingTable[row][0].equals(keyy)){
						row++;
					}
					//searching for column
					for(String str:temporary){
						int col=1;
						while(!parsingTable[0][col].equals(str)){
							col++;
						}
						parsingTable[row][col]=s;
					}
				}
				else if(ch=='^'){
					//if first character of rhs '^' taking follow of lhs
					temporary.addAll(follow.get(key));
					int row=1;
					while(!parsingTable[row][0].equals(keyy)){
						row++;
					}
					for(String str:temporary){
						int col=1;
						while(!parsingTable[0][col].equals(str)){
							col++;
						}
						parsingTable[row][col]=s;
					}
				}
				else{
					//if rhs is a terminal directly put in parsingtable
					int row=1;
					String chh=ch+"";
					while(!parsingTable[row][0].equals(keyy)){
						row++;
					}
					int col=1;
					while((!parsingTable[0][col].equals(chh))&&(!parsingTable[0][col].equals(s))){
						col++;
					}
					parsingTable[row][col]=s;
				}
			}
		}
		
		//printing parsingtable
		for(int r=0;r<n+1;r++){
			for(int c=0;c<terminals.size()+1;c++){
				System.out.print(parsingTable[r][c]+"     ");
			}
			System.out.println();
		}
		//calling function to printaction..
		printActions(parsingTable);
	}
	
	public static void printActions(String[][]parsingTable){
		Scanner sc=new Scanner(System.in);
		String s="";
		System.out.println("Enter stop to stop taking string:");
		System.out.println("Enter the string to be validated(space in between):");
		s=sc.nextLine();
		System.out.println("Enter the start symbol:");
		String startSymbol=sc.next();
		
		//data structures used
		//stack
		Deque<String>stack=new ArrayDeque<>();
		//input buffer
		List<String>ipBuffer=new ArrayList<>();
		String[]strArray=s.split(" ");
		
		for(int i=0;i<strArray.length;i++){
			ipBuffer.add(strArray[i]);
		}
		ipBuffer.add("$");
		stack.push("$");
		stack.push(startSymbol);
		
		//2 pointers, one pointing to stack and other to input buffer
		//top is pointing to stack and ptr is pointing to input buffer
		//pointer is used to give index to ptr in input buffer
		int pointer=0;
		String top=stack.peek();
		char t=top.charAt(0);
		String ptr=ipBuffer.get(pointer);
		
		//if top and ptr both are same and == "$" we have to end, saying string is valid
		while(!(top.equals(ptr) && top.equals("$")) && pointer<ipBuffer.size()){
			//if top is not equal to ptr, perform:-
			//pop top of stack and push the rhs of productions in reverse order
			if(!top.equals(ptr)){
				//searching for row and column
				int row=1,col=1;
				//if top is a non-terminal then, searching top in row and ptr in column
				if(productions.containsKey(t)){
					while(!parsingTable[row][0].equals(top)){
						row++;
					}
					while(!parsingTable[0][col].equals(ptr)){
						col++;
					}
				}
				// else if top is a terminal then, searching top in column and ptr row
				else{
					while((!parsingTable[0][col].equals(top))){
						col++;
					}
					while(!parsingTable[row][0].equals(ptr)){
						row++;
					}
				}
				String res="";
				//gives the production present at that row and column in parsing table
				res=parsingTable[row][col];
				//if blank means error
				if(res.equals("-"))					break;
				System.out.println("top: "+top+" ptr: "+ptr+" Production: "+res);
				System.out.println("Since top not equal to ptr, therefore pop " +stack.peek()+" and pushing RHS of production in Reverse Order");
				stack.pop();
				for(int z=0;z<res.length();z++){
					char character=res.charAt(res.length()-(1+z));
					if(character!='^')					stack.push(""+character);
				}
				top=stack.peek();
				t=top.charAt(0);
				ptr=ipBuffer.get(pointer);
			}
			else{
				//if top is equal to ptr, perform:-
				//pop top of stack and increment pointer
				System.out.println("top: "+top+"ptr: "+ptr);
				System.out.println("Since top is equal to ptr , pop "+stack.peek()+" and Incrementing pointer");
				stack.pop();
				pointer+=1;
				top=stack.peek();
				t=top.charAt(0);
				ptr=ipBuffer.get(pointer);
			}
			System.out.println();
		}
		
		if(pointer<ipBuffer.size() && top.equals(ptr) && top.equals("$")){
			System.out.println("String is valid");
		}
		else{
			System.out.println("String is not valid");
		}
	}
}