import java.util.*;
class AssemblerOne{
	private static String SYMTAB[][]=new String[3][3];
	private static String LITTAB[][]=new String[2][2];
	private static int POOLTAB[][]=new int[2][2];
	private static String OPTAB[][]=new String[7][3];
	private static List<String>output=new ArrayList<>();
	
	private static int lc=0;
	private static int littabptr=0;
	private static int pooltabptr=0;
	private static int	symtabptr=0;
	
	
	public static void main(String args[]){
		takeOptab();
		POOLTAB[0][0]=0;
		POOLTAB[0][1]=0;
		algorithm();
		System.out.println();
		System.out.println("OPTAB");
		printTable(OPTAB,3);
		System.out.println();
		System.out.println("SYMTAB");
		printTable(SYMTAB,3);
		System.out.println();
		System.out.println("LITTAB");
		printTable(LITTAB,2);
		System.out.println();
		System.out.println("POOLTAB");
		printTable(POOLTAB,2);
		System.out.println();
		String l="AD"+" 5"+" 0"+" 00";
		output.add(l);
		for(String strr:output)	System.out.println(strr);
	}
	public static void takeOptab(){
		OPTAB[0][0]="ADD";
		OPTAB[0][1]="IS";
		OPTAB[0][2]="03,1";
		OPTAB[1][0]="MOVER";
		OPTAB[1][1]="IS";
		OPTAB[1][2]="04,1";
		OPTAB[2][0]="MOVEM";
		OPTAB[2][1]="IS";
		OPTAB[2][2]="05,1";
		OPTAB[3][0]="LTORG";
		OPTAB[3][1]="AD";
		OPTAB[3][2]="R11";
		OPTAB[4][0]="END";
		OPTAB[4][1]="AD";
		OPTAB[4][2]="R5";
		OPTAB[5][0]="START";
		OPTAB[5][1]="AD";
		OPTAB[5][2]="R3";
		OPTAB[6][0]="DS";
		OPTAB[6][1]="DL";
		OPTAB[6][2]="R13";
	}
	public static void algorithm(){
		//initialization
		String label="";
		String code="";
		String operand1="";
		String operand2="";
		Scanner sc=new Scanner(System.in);
		String s="";
		int opcode=-1,length=-1;
		do{
			s=sc.nextLine();
			String array[]=s.split(" ");
				//System.out.println(s);
				if(s.equals("END"))		break;
				label=(array[0]);//label
				if(array.length>=2)code=(array[1]);//mover
				if(array.length>=3)operand1=(array[2]);//a
				if(array.length>=4) operand2=(array[3]);//b
				//System.out.println("label:"+label+"code:"+code+"operand1:"+operand1+"operand2:"+operand2);
				
				//System.out.print("LABEL:"+label);
				if(!(label.equals(""))){
					boolean f=false;
					for(int p=0;p<SYMTAB.length;p++){
						if(SYMTAB[p][0]!=null && SYMTAB[p][0].equals(label)){
							f=true;
							break;
						}
					}
					if(!f){
					SYMTAB[symtabptr][0]=label;
					SYMTAB[symtabptr][1]=String.valueOf(lc);
					SYMTAB[symtabptr][2]="1";
					symtabptr+=1;
					}
					
				}
				if(code.equals("LTORG")){
					String temp="";
					for(int w=0;w<2;w++){
						String g="";
						String temp2="";
						temp=sc.nextLine();
						List<String>tempOp=new ArrayList<>();
						for(int m=0;m<LITTAB.length;m++){
							if(LITTAB[m][0]!=null && LITTAB[m][0].equals(temp)){
								LITTAB[m][1]=String.valueOf(lc);
								lc+=1;
								break;
							}
						}
						tempOp.add("AD");
					    for(int h=0;h<OPTAB.length;h++){
							if(OPTAB[h][0]!=null && OPTAB[h][0].equals("LTORG")){
							temp2=OPTAB[h][2];
							temp2=temp2.substring(1,temp2.length());
							break;
							}
						}
						tempOp.add(temp2);
						tempOp.add("L");
						tempOp.add(temp);
						for(int k=0;k<tempOp.size();k++){
							String l=tempOp.get(k);
							if(k!=0)			g=g+" "+l;
							else				g=l;
						}
						output.add(g);	
					}
					pooltabptr+=1;
					POOLTAB[pooltabptr][0]=littabptr;
					POOLTAB[pooltabptr][1]=0;
				}
				if(code.equals("START")){
					lc=Integer.parseInt(operand1);
					List<String>tempOp=new ArrayList<>();
					String g="";
					String temp="";
					tempOp.add("AD");
					for(int h=0;h<OPTAB.length;h++){
						if(OPTAB[h][0]!=null && OPTAB[h][0].equals("START")){
							temp=OPTAB[h][2];
							temp=temp.substring(1,temp.length());
							break;
						}
					}
					tempOp.add(temp);
					tempOp.add("C");
					tempOp.add(operand1);
					for(int k=0;k<tempOp.size();k++){
						String l=tempOp.get(k);
						if(k!=0)			g=g+" "+l;
						else				g=l;
					}
					output.add(g);					
				}	
				if(code.equals("DS")){
					declare_stmt("DS",label,operand1);
				}
				if(code.equals("DC")){
					declare_stmt("DC",label,operand1);
				}
				if(code.equals("ADD")){
					imperative_stmt("ADD",label,operand1,operand2);
				}
				if(code.equals("MOVEM")){
					imperative_stmt("MOVEM",label,operand1,operand2);
				}
				if(code.equals("MOVER")){
					imperative_stmt("MOVER",label,operand1,operand2);
				}
		}while(!s.equals("END"));
	}
	public static void imperative_stmt(String str,String label,String operand1,String operand2){
		int opcode=-1;//04
		int length=-1;//1
		String literal="-1";
		String type1="";
		String type2="S";
		String lastAdd="";
		boolean flag=false;
		boolean flag2=false;
		List<String>tempOp=new ArrayList<>();
		String g="";
			
		for(int h=0;h<OPTAB.length;h++){
			if(OPTAB[h][0].equals(str)){
				String twin=OPTAB[h][2];
				String arr_twin[]=twin.split(",");
				opcode=Integer.parseInt(arr_twin[0]);
				length=Integer.parseInt(arr_twin[1]);break;
			}
		}
		lc+=length;//lc=201
		if((operand1.equals("AREG")) || (operand1.equals("BREG")) || (operand1.equals("CREG"))){
			switch(operand1){
				case "AREG":type1="1";break;
				case "BREG":type1="2";break;
				case "CREG":type1="3";break;
			}
		}
		if(operand2.charAt(0)=='='){
			literal=String.valueOf(operand2.charAt(1));//5
			if(POOLTAB[pooltabptr][1]==0)		flag=false;
			else{
			for(int m=0;m<LITTAB.length;m++){
				String lit=LITTAB[m][0];
				if(lit!=null && (lit.equals(literal)))		flag=true;
			}
			}
			if(!flag){
				LITTAB[littabptr][0]=literal;
				POOLTAB[pooltabptr][1]+=1;
				littabptr+=1;
				lastAdd=String.valueOf(littabptr-1);
			}
			type2="L";
		}
		else if(operand2.charAt(0)!='='){
			for(int r=0;r<SYMTAB.length;r++){
				if(SYMTAB[r][0]!=null && SYMTAB[r][0].equals(operand2)){
					//System.out.println("Inside true"+operand2+SYMTAB[r][0]+symtabptr);
					flag2=true;
					break;
				} 
			}
			//System.out.println(symtabptr);
			if(flag2==false){
			//System.out.println("Inside false"+operand2);
			SYMTAB[symtabptr][0]=operand2;
			SYMTAB[symtabptr][1]="";
			SYMTAB[symtabptr][2]="";
			symtabptr+=1;
			}
			lastAdd=String.valueOf(symtabptr-1);
			type2="S";
			//System.out.println("label2:"+label+" "+String.valueOf(lc)+" "+symtabptr);
		}
		
		
		tempOp.add("IS");
		if(opcode!=-1)			tempOp.add(String.valueOf(opcode));
		tempOp.add(type1);
		tempOp.add(type2);
		tempOp.add(lastAdd);
		
		for(int k=0;k<tempOp.size();k++){
			String l=tempOp.get(k);
			if(k!=0)			g=g+" "+l;
			else				g=l;
		}
		output.add(g);
	}
	
	public static void declare_stmt(String s,String label,String operand){
		List<String>tempOp=new ArrayList<>();
		String type="C";
		String val=operand;
		String g="";
		
		for(int i=0;i<SYMTAB.length;i++){
			String temp=SYMTAB[i][0];
			if(label.equals(temp)){
				SYMTAB[i][1]=String.valueOf(lc);
				SYMTAB[i][2]=operand;
				//System.out.println("label3:"+label+" "+String.valueOf(lc)+" "+symtabptr);
			}
			//System.out.println("label4:"+label+" "+String.valueOf(lc)+" "+symtabptr);
		}
		lc+=Integer.parseInt(operand);
		String temp="";
		
		for(int h=0;h<OPTAB.length;h++){
			if(OPTAB[h][0]!=null && OPTAB[h][0].equals(s)){
				temp=OPTAB[h][2];
				temp=temp.substring(1,temp.length());
				break;
			}
		}
		tempOp.add("DL");
		tempOp.add(temp);
		tempOp.add(type);
		tempOp.add(val);
		
		for(int k=0;k<tempOp.size();k++){
			String l=tempOp.get(k);
			if(k!=0)			g=g+" "+l;
			else				g=l;
		}
		output.add(g);
	}
	
	public static void printTable(String[][]table,int cols){
		for(int i=0;i<table.length;i++){
			for(int j=0;j<cols;j++){
				//if(table[i][j]!=null)
				System.out.print(table[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	public static void printTable(int[][]table,int cols){
		for(int i=0;i<table.length;i++){
			for(int j=0;j<cols;j++){
				//if(table[i][j]!=null)
				System.out.print(table[i][j]+" ");
			}
			System.out.println();
		}
	}
}
	
		