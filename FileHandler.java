import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {

	public static File saveFile = new File("Save File.txt");
	public static File debugInfo = new File("Debug Information.txt");
	
	public static void writeFile(boolean append,int ...ints){
		
		PrintWriter pw = null;
		try{
			pw = new PrintWriter(new FileOutputStream(saveFile.getName(),append));
		}catch(Exception e){
			
		}
		for(int i =0; i<ints.length;i++){
			pw.println(ints[i]);
		}
		pw.close();
	}
	public static void writeFile(File f,boolean append,String s){

		PrintWriter pw = null;
		try{
			pw = new PrintWriter(new FileOutputStream(f.getName(),append));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		pw.println(s);
		
		pw.close();
	}
	
	public static void writeFile(boolean append,ArrayList<Integer> ints){
		
		PrintWriter pw = null;
		try{
			pw = new PrintWriter(new FileOutputStream(saveFile.getName(),append));
		}catch(Exception e){
			
		}
		for(int i =0; i<ints.size();i++){
			pw.println(ints.get(i));
		}
		pw.close();
	}
	
	
	public static void saveHighScores(){
		
			for(int i=0;i<StoredInfo.highScores.size();i++){
				if(StoredInfo.highScores.get(i) < StoredInfo.score){
					StoredInfo.highScores.set(i, StoredInfo.score);
					break;
				}
			}
			writeFile(false,StoredInfo.highScores);
		
	}
	
	public static void readSave(){
		Scanner scanner = null;
		try {
			 scanner = new Scanner(saveFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while(scanner.hasNextLine()){
			StoredInfo.highScores.add(Integer.parseInt(scanner.nextLine()));
			System.out.println(StoredInfo.highScores.get(0));
		}
		scanner.close();
	}
	
	public static void clearInfo(File f){
		PrintWriter pw = null;
		try{
			pw = new PrintWriter(new FileOutputStream(f.getName()));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		pw.println();
		
		pw.close();
	}
}
