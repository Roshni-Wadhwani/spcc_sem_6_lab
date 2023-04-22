import java.util.*;
class macros{
	private static int mdtc=0;
	private static int mntc=0;
	private static String mnt[][]=new String[10][3];
	private static HashMap<Integer,String> mdt=new HashMap<>();
	private static HashMap<String,Integer> ala=new HashMap<>();
	private static HashMap<String,Integer>macroName=new HashMap<>();
	private static List<String>output=new ArrayList<>();
			
	public static void main(String args[]){
		passOne();
	}
	public static void passOne(){
		Scanner sc=new Scanner(System.in);
		String s=sc.nextLine();
		while(s.equals("MACRO")){
			String str=sc.nextLine();
			String[] array=str.split(" ");
			
			if(str.equals("MEND"))		break;
			
			
			if(macroName.isEmpty()){
				mnt[mntc][0]=String.valueOf(mntc)+1;
				mnt[mntc][1]=array[1];
				mnt[mntc][2]=String.valueOf(mdtc)+1;
				macroName.put(array[1],mdtc+1);
				mntc+=1;
				int index=1;
				for(String process:array){
					if(!(process.equals(",")))
						if(!(macroName.containsKey(process))){
						ala.put(process,index);
						index+=1;
						}
				}
			}
			
			List<String>temp=new ArrayList<>();
			String m="";
			
			if(mdt.isEmpty()){
				for(String process:array){
					if(!(process.equals(",")))
					temp.add(process);
				}
			}
			else{
				for(String process:array){
					if(!(process.equals(","))){
					if(ala.containsKey(process)){
						temp.add("#"+ala.get(process));
					}
					else	temp.add(process);
					}
				}
			}
			for(int i=0;i<temp.size();i++){
				String process=temp.get(i);
				if(i!=0)		m=m+" "+process;
				else			m=process;
			}
			mdt.put(mdtc+1,m);
			
			mdtc+=1;
			
		}
		mdt.put(mdtc+1,"MEND");
		System.out.println();
		System.out.println("MACRO NAME TABLE:");
		System.out.println("Index     Macroname    MDTIndex");     
		for(int i=0;i<mntc;i++){
			for(int j=0;j<3;j++){
				System.out.print(mnt[i][j]+"        ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("ARGUMENT LIST ARRAY:");
		System.out.println("Index    Argument"); 
		
        for(int iteration=1;iteration<=ala.size();iteration++){	
		for(Map.Entry<String,Integer>map:ala.entrySet()){
			int val=map.getValue();
			String key=map.getKey();
			if(val==iteration)		System.out.println(val+"       "+key);
		}
		}
			
		System.out.println();
		System.out.println("MACRO DEFINITION TABLE:");
		System.out.println("Index    Card"); 
		
        for(int iteration=1;iteration<=mdt.size();iteration++){	
		for(Map.Entry<Integer,String>map:mdt.entrySet()){
			String val=map.getValue();
			int key=map.getKey();
			if(key==iteration)		System.out.println(key+"       "+val);
		}
		}
		System.out.println();
		passTwo();
		
	}
	public static void passTwo(){
		Scanner sc=new Scanner(System.in);
		String s="";
		do{
			s=sc.nextLine();
			List<String>temp=new ArrayList<>();
			String arraytemp[]=s.split(" ");
			List<String>array=new ArrayList<>();
			for(String t:arraytemp){
				if(!t.equals(","))		array.add(t);
			}
			int n=array.size();
			if(n>1){
				String macroname=array.get(1);//incr
				if(macroName.containsKey(macroname)){
					int mdtindex=macroName.get(macroname);//01
					String mdtString=mdt.get(mdtindex);//&lab....
					String mdtArray[]=mdtString.split(" ");
					
					if(array.size()==mdtArray.length){
						HashMap<String,Integer>tempAla=new HashMap<>();
						for(int i=0;i<mdtArray.length;i++){
							String arr=mdtArray[i];
							if(!arr.equals(macroname)){
								if(ala.containsKey(arr)){
									tempAla.put(array.get(i),ala.get(arr));
								}
							}
						}
						String r="";
						for(int j=mdtindex+1;j<=mdtc;j++){
							mdtString=mdt.get(j);
							mdtArray=mdtString.split(" ");
							List<String>op=new ArrayList<>();
							for(int h=0;h<mdtArray.length;h++){
								if(mdtArray[h].charAt(0)=='#'){
									String num=String.valueOf(mdtArray[h].charAt(1));
									for(Map.Entry<String,Integer>map:tempAla.entrySet()){
										int val=map.getValue();
										String key=map.getKey();
										if(String.valueOf(val).equals(num)){
												op.add(key);
												break;
										}
									}
								}
								else{
									op.add(mdtArray[h]);
								}
							}
							String g="";
							for(int k=0;k<op.size();k++){
								String l=op.get(k);
								if(k!=0)			g=g+" "+l;
								else				g=l;
							}
							output.add(g);
						}
					}
				}
				else {
					String g="";
					for(int k=0;k<array.size();k++){
						String l=array.get(k);
						if(k!=0)			g=g+" "+l;
						else				g=l;
					}
					output.add(g);
				}
			}
			else			output.add(s);
		}while(!s.equals("END"));
		System.out.println();
		System.out.println("Expanded code:");
		for(int m=0;m<output.size();m++)	System.out.println(output.get(m));
	}
}
		