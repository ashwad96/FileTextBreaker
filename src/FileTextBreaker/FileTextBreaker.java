package hackathon;

import java.lang.Object;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyString;
import org.python.util.PythonInterpreter; 
import java.io.*;
import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 *
 * @author aswadhwa
 */
public class FileTextBreaker {
    
    public static ArrayList<Chapter> getChapters(File file) throws ScriptException, IOException {



          System.out.println("We are going to extract chapters");
          System.out.println(file.toString());
   	  
          if(file.toString().substring(file.toString().length()-3).equalsIgnoreCase("txt")){
              System.out.println("hi this is a text file");
              createChapters(file.toString()); 
          }else if(file.toString().substring(file.toString().length()-3).equalsIgnoreCase("pdf")){
              System.out.println("hi this is a pdf file");
              String txtFile = PdfToText(file.toString()); 
              createChapters(txtFile); 
              
          }
          
          ArrayList<Chapter> chapters = new ArrayList<Chapter>();
          
          String workingDir = System.getProperty("user.dir");
          String folderName = workingDir+"\\"+file.toString().substring(0, file.toString().length()-4)+"-chapters";
          System.out.println(folderName);

          File folder = new File(folderName);
          for (final File fileEntry : folder.listFiles()) {
                System.out.println(fileEntry.getName()+"\n");
                Chapter chapter = new Chapter();
                ArrayList<String> paras = ParaFromText(folderName+"\\"+fileEntry.getName());
                
                for (int i = 0; i < paras.size(); i++) {

                    Paragraph para = new Paragraph(paras.get(i));
                    chapter.addParagraph(para);
                }
                
                chapters.add(chapter);
          }

          return chapters;        
    }
    
   
    
    public static String PdfToText(String pdfFile){
        String textFileName= pdfFile.substring(0, pdfFile.length()-4)+".txt";
          try{
                //create file writer
                FileWriter fw=new FileWriter(textFileName);
                //create buffered writer
                BufferedWriter bw=new BufferedWriter(fw);
                //create pdf reader
                PdfReader pr=new PdfReader(pdfFile);
                //get the number of pages in the document
                int pNum=pr.getNumberOfPages();
                System.out.println(pNum);
                //extract text from each page and write it to the output text file
                for(int page=1;page<=pNum;page++){
                 String text = PdfTextExtractor.getTextFromPage(pr, page);
                 bw.write(text);
                 bw.newLine();

                }
                bw.flush();
                bw.close();

   
   
          }catch(Exception e){e.printStackTrace();}
         return textFileName;
    }
    
    public static ArrayList<String> ParaFromText(String fileName) throws IOException{
        
        File file = new File(fileName);
        String path = file.getAbsolutePath();
        System.out.println(path);
        
        String content = new String(Files.readAllBytes(Paths.get(fileName)));
        String tempDisplayText = content;
        System.out.println(tempDisplayText);
        
        
       StringBuilder sb = new StringBuilder();
       ArrayList<String> paras = new ArrayList<String>();
       
        if (tempDisplayText != null) {  
            int firstFullStopIndex;
            while( tempDisplayText.length() > 1000
               && 
               (firstFullStopIndex = tempDisplayText.indexOf(". ", 950)) >= 0 ){
                
                paras.add(tempDisplayText.substring(0, firstFullStopIndex) );
                
            sb.append( "<p>" );
            sb.append( tempDisplayText.substring(0, firstFullStopIndex) );
            sb.append( ".</p>" );
            tempDisplayText = tempDisplayText.substring(firstFullStopIndex + 1);
            }
            if( tempDisplayText.length() > 0 ){
                sb.append( "<p>" ).append( tempDisplayText ).append( "</p>" );
                paras.add(tempDisplayText);
            }
            tempDisplayText = sb.toString();
        }
        

        return paras;
     }
    
    public static void createChapters(String txtFile) throws FileNotFoundException, ScriptException, IOException{
 try { 
        System.out.println("fil name " + txtFile);
        PythonInterpreter pi = new PythonInterpreter();
        pi.exec("import os");
        pi.exec("import sys");
        pi.exec("import subprocess");
        pi.exec("print(os.getcwd())");
        pi.exec("subprocess.check_call(\"chapterize "+txtFile+"\", shell=True)");
        

    }catch(Exception e){
               throw new RuntimeException("error while creating chapters ", e ) ;
    }

    }
    
    public static void main(String[] args) throws FileNotFoundException, ScriptException, IOException {


        File a = new File("1342-pdf.pdf");
        ArrayList<Chapter> ch = getChapters(a);
          for (int i = 0; i < ch.size(); i++) {
               java.util.List<Paragraph> paras = ch.get(i).getParagraphs();
               for(int j=0; j<paras.size(); j++){
                   System.out.println(paras.get(j).getText()+"\n\n");
               }
               
               System.out.println("****************************");
           }
    }
    
}
