import java.util.*;
class operatorPrecedence{
	
	public static HashMap<Character,HashSet<String>>productions=new HashMap<>();
	public static HashMap<Character,HashSet<String>>leading=new HashMap<>();
	public static HashMap<Character,HashSet<String>>trailing=new HashMap<>();
	
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
		
		Set<Character>keys=productions.keySet();
		Set<String>terminals=new HashSet<>();
		//calculating leading
		for(Map.Entry<Character,HashSet<String>>map:productions.entrySet()){
			HashSet<String>sett=new HashSet<>();
			sett=map.getValue();
			HashSet<String>settt=new HashSet<>();
			for(String s:sett){
				if(!keys.contains(s.charAt(0))){
					settt.add(""+s.charAt(0));
				}
				else{
					if(s.length()>=2){
						settt.add(""+s.charAt(1));
					}
					else{
						settt.addAll(leading.get(s.charAt(0)));
					}
				}
			}
			terminals.addAll(settt);
			leading.put(map.getKey(),settt);
		}
		
		
		//calculating trailing
		for(Map.Entry<Character,HashSet<String>>map:productions.entrySet()){
			HashSet<String>sett=new HashSet<>();
			sett=map.getValue();
			HashSet<String>settt=new HashSet<>();
			for(String s:sett){
				if(!keys.contains(s.charAt(s.length()-1))){
					settt.add(""+s.charAt(s.length()-1));
				}
				else{
					if(s.length()>=2){
						settt.add(""+s.charAt(s.length()-2));
					}
					else{
						settt.addAll(trailing.get(s.charAt(0)));
					}
				}
			}
			terminals.addAll(settt);
			trailing.put(map.getKey(),settt);
		}
		terminals.add("$");
		System.out.println("Productions:");
		System.out.println(productions);
		System.out.println("Non-terminals:");
		System.out.println(keys);
		System.out.println("Terminals:");
		System.out.println(terminals);
		System.out.println("Leading:");
		System.out.println(leading);
		System.out.println("Trailing:");
		System.out.println(trailing);
		
		String[][]Table=new String[terminals.size()+1][terminals.size()+1];
		Table[0][0]=" ";
		
		//initialising table
		
		int i=1;
		for(String s:terminals){
			Table[i][0]=s;
			Table[0][i]=s;
			i++;
		}
		
		//rules
		//Assuming S is the start symbol
		
		//rule1
		for(int row=1;row<=terminals.size();row++){
		Set<String>temp=new HashSet<>();
			if(Table[row][0].equals("$")){
				temp=leading.get('S');
				for(String s:temp){
					for(int col=1;col<=terminals.size();col++)
						if(Table[0][col].equals(s)){
							Table[row][col]="<.";
							break;
						}	
				}
			}
		}
		
		//rule2
		int tempC=0;
		for(int col=1;col<=terminals.size();col++){
			if(Table[0][col].equals("$")){
				tempC=col;
				break;
			}
		}
		Set<String>temp=new HashSet<>();
		temp=trailing.get('S');
		for(String s:temp){
			for(int row=1;row<=terminals.size();row++)
				if(Table[row][0].equals(s)){
					Table[row][tempC]=">.";
					break;
				}
		}
		
		//rule 3
		for(Map.Entry<Character,HashSet<String>>map:productions.entrySet()){
			char key=map.getKey();
			HashSet<String>val=map.getValue();
			for(String s:val){
				if(s.length()>1){
					for(int j=0;j<s.length()-1;j++){
						if(terminals.contains(""+s.charAt(j)) && terminals.contains(""+s.charAt(j+1))){
							int row=searchRow(""+s.charAt(j),Table);
							int col=searchColumn(""+s.charAt(j+1),Table);
							Table[row][col]="=";
						}
						else if(terminals.contains(""+s.charAt(j)) && keys.contains(s.charAt(j+1))){
							int row=searchRow(""+s.charAt(j),Table);
							HashSet<String>tempVal=leading.get(s.charAt(j+1));
							for(String str:tempVal){
								int col=searchColumn(str,Table);
								Table[row][col]="<.";
							}
						}
						else if(keys.contains(s.charAt(j)) && terminals.contains(""+s.charAt(j+1))){
							int col=searchColumn(""+s.charAt(j+1),Table);
							HashSet<String>tempVal=trailing.get(s.charAt(j));
							for(String str:tempVal){
								int row=searchRow(str,Table);
								Table[row][col]=">.";
							}
							
							
						}
					}
				}
				if(s.length()>2){
					for(int j=0;j<s.length()-2;j++){
						if(terminals.contains(""+s.charAt(j)) && terminals.contains(""+s.charAt(j+2)) && keys.contains(s.charAt(j+1))){
							int row=searchRow(""+s.charAt(j),Table);
							int col=searchColumn(""+s.charAt(j+2),Table);
							Table[row][col]="=";
						}
					}
				}
			}
		}
		int row=searchRow("$",Table);
		int col=searchColumn("$",Table);
		Table[row][col]="ACCEPT";
		
		for(int h=0;h<=terminals.size();h++){
			for(int g=0;g<=terminals.size();g++){
				System.out.print(Table[h][g]+"		 ");
			}
			System.out.println();
		}
		
		validateString(Table);
	}
	
	public static int searchRow(String str,String[][]Table){
		int len=((Table[0].length)-1);
		int row=1;
		
		while(row<len){
			if(Table[row][0].equals(""+str.charAt(0))){
				return row;
			}
			row++;
		}
		//System.out.println(row);
		return row;
	}
	
	public static int searchColumn(String str,String[][]Table){
		int len=((Table[0].length)-1);
		int col=1;
		while(col<len){
			if(Table[0][col].equals(""+str.charAt(0))){
				return col;
			}
			col++;
		}
		return col;
	}
	
	public static void validateString(String[][]Table){
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter String to validate:");
		String str=sc.next();
		Deque<String>stack=new ArrayDeque<>();
		List<String>ipBuffer=new ArrayList<>();
		stack.push("$");
		str=str+"$";
		for(int i=0;i<str.length();i++)	ipBuffer.add(""+str.charAt(i));
		
		boolean flag=true;
		int pointer=0;
		String top="";
		String ptr="";
		do{
			try{
				top=stack.peek();
				ptr=ipBuffer.get(pointer);
				int row=searchRow(top,Table);
				int col=searchColumn(ptr,Table);
				if(Table[row][col].equals("<.")||Table[row][col].equals("=")){
					System.out.println("top: "+top+" ptr: "+ptr);
					System.out.println("Push "+ptr+" on stack and increment pointer");
					stack.push(ptr);
					pointer++;
					System.out.println("Stack "+stack);
					System.out.println("InputBuffer "+ipBuffer);
				}
				else{
				while(Table[row][col].equals(">.")||Table[row][col].equals("=")){
					System.out.println("top: "+top+"ptr: "+ptr);
					String temp=stack.pop();
					System.out.println("Pop "+temp);
					top=stack.peek();
					row=searchRow(top,Table);
					col=searchColumn(temp,Table);
					System.out.println("Stack "+stack);
					System.out.println("InputBuffer "+ipBuffer);
				}
				}
				
			}
			catch(Exception e){
				flag=false;
				System.out.println("Null entry");
				break;
			}
		}while(!(top.equals(ptr) && top.equals("$")) && pointer<ipBuffer.size());
		if(flag)		System.out.println("String valid");
		else			System.out.println("String not valid");
	}
}