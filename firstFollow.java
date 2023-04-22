import java.util.*;
class firstFollow{
	public static Set<Character>small_letter=new HashSet<>();
	public static Map<Character,HashSet<Character>>follow=new HashMap<>();
	public static Map<Character,HashSet<Character>>first=new HashMap<>();
	
	
	public static void main(String args[]){
		//try{
		Scanner sc=new Scanner(System.in);
		System.out.println("enter number of non-terminals:");
		int no_of_non_terminals=sc.nextInt();
		Map<Character,HashSet<String>>productions=new HashMap<>();
		
		for(int i=0;i<no_of_non_terminals;i++){
			System.out.println("Enter LHS of production:");
			char key=sc.next().charAt(0);
			System.out.println("Enter number of productions:");
			int no=sc.nextInt();
			HashSet<String>set=new HashSet<>();
			System.out.println("Enter RHS of productions one by one:");
			for(int j=0;j<no;j++){
				String value=sc.next();
				set.add(value);
			}
			productions.put(key,set);
		}
		System.out.println("Productions:"+productions);
		Set<Character>keys=productions.keySet();
		
		for(char ch='a';ch<='z';ch++)			small_letter.add(ch);
		
		//calculate First of a set
		for(Map.Entry<Character,HashSet<String>>map:productions.entrySet()){
			HashSet<String>sett=new HashSet<>();
			sett=map.getValue();
			HashSet<Character>settt=new HashSet<>();
			for(String s:sett){
				if(small_letter.contains(s.charAt(0))||s.charAt(0)=='^'){
					settt.add(s.charAt(0));
				}
			}
			if(!settt.isEmpty())				first.put(map.getKey(),settt);	
		}
		for(Map.Entry<Character,HashSet<String>>map:productions.entrySet()){
			HashSet<String>sett=new HashSet<>();
			sett=map.getValue();
			HashSet<Character>settt=new HashSet<>();
			for(String s:sett){
				if(keys.contains(s.charAt(0))){
					int index=0;
					settt=firstCalculate(0,s,settt);
				}
			}
			if(!settt.isEmpty())				first.put(map.getKey(),settt);
		}
		System.out.println("first:"+first);
		//System.out.println("keyset:"+keys);
		
		//calculating follow set	
		HashSet<Character>resultt_set=new HashSet<>();
		for(HashSet<String>hset:productions.values()){
				for(String s:hset){
					for(int i=0;i<s.length()-1;i++){
						if(s.charAt(i)=='S'){
							resultt_set.addAll(first.get(s.charAt(i+1)));
						}
					}
				}
			}
		resultt_set.add('$');
		if(resultt_set.contains('^'))		resultt_set.remove('^');
		follow.put('S',resultt_set);
		
		
		for(Character ch:keys){
			if(ch!='S'){
			HashSet<Character>result_set=new HashSet<>();
			for(Map.Entry<Character,HashSet<String>>mapp:productions.entrySet()){
				HashSet<String>hset=mapp.getValue();
				char k=mapp.getKey();
				for(String s:hset){
					if(s.length()>=2){
					for(int i=0;i<s.length()-2;i++){
						if(s.charAt(i)==ch){
							//System.out.println("i:"+i);
							result_set=followCalculate(i+1,s,result_set);
							if(result_set.contains('^'))		result_set.addAll(follow.get(k));
						}
					}
					
					for(int i=s.length()-2;i<s.length()-1;i++){
						if(s.charAt(i)==ch){
							result_set.addAll(first.get(s.charAt(i+1)));
                                   if(result_set.contains('^'))		result_set.addAll(follow.get(k));
						}
					}
					
					}
					for(int i=s.length()-1;i<s.length();i++){
						if(s.charAt(i)==ch){
							result_set.addAll(follow.get(k));
						}
					}
					
				}
				}
			//System.out.println("result-set:"+result_set);
			if(result_set.contains('^'))		result_set.remove('^');
			if(!result_set.isEmpty())			follow.put(ch,result_set);
			//follow.put(last,follow.get(k));
		}}
			
			//System.out.println("follow:"+follow);
		System.out.println("follow:"+follow);
		/*}
		catch(Exception e){
			System.out.println(e);
		}*/
	}
	public static HashSet<Character> firstCalculate(int index, String str,HashSet<Character>res_set){
		char fir=str.charAt(index);
		HashSet<Character>temp=new HashSet<>();
		if(small_letter.contains(str.charAt(index)))			res_set.add(str.charAt(index));
		else if(str.charAt(index)!=('^')){
			HashSet<Character>val=first.get(str.charAt(index));
			HashSet<Character>temp_val=new HashSet<>();
			temp_val.addAll(val);
			if(temp_val.contains('^')){
				res_set=firstCalculate(index+1,str,res_set);
				temp_val.remove('^');
			}
			res_set.addAll(temp_val);
			//System.out.println(res_set);
		}						
		return res_set;
	}
	
	public static HashSet<Character> followCalculate(int index, String str,HashSet<Character>res_set){
		//System.out.println("len: "+str.length()+" index: "+index);
		char fir=str.charAt(index);
		HashSet<Character>temp=new HashSet<>();
		if(small_letter.contains(str.charAt(index)))			res_set.add(str.charAt(index));
		else if(str.charAt(index)!=('^')){
			HashSet<Character>val=first.get(str.charAt(index));
			HashSet<Character>temp_val=new HashSet<>();
			temp_val.addAll(val);
			if(temp_val.contains('^')){
				if(index+1==str.length()-1){
					res_set.addAll(first.get(str.charAt(index+1)));
				}
				else{
					res_set=followCalculate(index+1,str,res_set);
					temp_val.remove('^');
				}
			}
			res_set.addAll(temp_val);
			
			//System.out.println(res_set);
		}						
		return res_set;
	}
}

				
			