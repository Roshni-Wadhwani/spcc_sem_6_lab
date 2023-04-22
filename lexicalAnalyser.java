import java.util.*;
import java.io.File;
import java.io.FileInputStream;
class lexicalAnalyser{
public static void main(String args[]){
try{

File file=new File("E:\\SPCC LAB\\lexical_one.c");
FileInputStream fis=new FileInputStream(file);
byte[] bytesArray=new byte[(int)file.length()];
fis.read(bytesArray);
String s=new String(bytesArray);
//System.out.println(s);

String[]keywords=new String[]{"int","double","float","void","return","main","include","stdio.h"};
String[] numbers=new String[]{"0","1","2"};
String[] operators=new String[]{"=","+"};
String[] special=new String[]{"#"};
String[] delimiter=new String[]{",",";",">","<","{","}","(",")"};

String arr[]=new String[100];
int ckey=0,cnos=0,coper=0,cspe=0,cde=0,cid=0;
arr=s.split(" ");
boolean flag=false;

//for(int i=0;i<arr.length;i++)		System.out.println(arr[i]);

String substr="";
for(int i=0;i<arr.length;i++){
if(i==arr.length-1){
substr=arr[arr.length-1].substring(0,1);
}
else{
substr=arr[i];
}
if(checkValidity(substr,keywords)){
System.out.println(substr+" is a keyword");
}
else if(checkValidity(substr,numbers)){
System.out.println(substr+" is a number");
}
else if(checkValidity(substr,operators)){
System.out.println(substr+" is an operator");
}
else if(checkValidity(substr,special)){
System.out.println(substr+" is a special character");
}
else if(checkValidity(substr,delimiter)){
System.out.println(substr+" is a delimiter");
}
else{
System.out.println(substr+" is an identifier");
}
}}

catch(Exception e){
System.out.println(e);
}
}

public static boolean checkValidity(String str, String arr[]){
boolean flag=false;
for(int j=0;j<arr.length;j++){
if(str.equals(arr[j])){
flag=true;
return flag;
}
}
return flag;
}

}