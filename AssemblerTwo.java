import java.util.*;
class AssemblerTwo{
	private static String SYMTAB[][]=new String[3][3];
	private static String LITTAB[][]=new String[2][2];
	private static int POOLTAB[][]=new int[2][2];
	private static String OPTAB[][]=new String[7][3];
	private static List<String>output=new ArrayList<>();
	
	public static void main(String args[]){
		takeOPTAB();
		takeSYMTAB();
		takeLITTAB();
		takePOOLTAB();
		takeICode();
		generateOutput();
	}
	public static void takeOPTAB(){
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
	public static void takePOOLTAB(){
		POOLTAB[0][0]=0;
		POOLTAB[0][1]=2;
		POOLTAB[1][0]=2;
		POOLTAB[0][0]=0;
	}
	public static void takeLITTAB(){
		LITTAB[0][0]="5";
		LITTAB[0][1]="205";
		LITTAB[1][0]="1";
		LITTAB[1][1]="206";
	}
	public static void takeSYMTAB(){
		SYMTAB[0][0]="A";
		SYMTAB[0][1]="207";
		SYMTAB[0][2]="1";
		SYMTAB[1][0]="LOOP";
		SYMTAB[1][1]="202";
		SYMTAB[1][2]="1";
		SYMTAB[2][0]="B";
		SYMTAB[2][1]="208";
		SYMTAB[2][2]="1";
	}
	public static void takeICode(){
		Scanner sc=new Scanner(System.in);
		String op="";
		String four="",one="",two="",three="";
		String s="";
		String array[]=new String[100];
		while(!s.equals("#")){
			s=sc.nextLine();
			array=s.split(" ");
			if(array[0].equals("IS")){
				one="+"+array[1];
				two=array[2];
				three=array[3];
				if(three.equals("L")){
					four=LITTAB[Integer.parseInt(array[4])][1];
				}
				else if(three.equals("S")){
					four=SYMTAB[Integer.parseInt(array[4])][1];
				}
				op=one+" "+two+" "+four;
				output.add(op);
			}
			else if (array[0].equals("DL")){
				op="+00 0 00"+array[3];
				output.add(op);
			}
			
		}
	}
	
	public static void generateOutput(){
		for(String str:output)		System.out.println(str);
	}
}