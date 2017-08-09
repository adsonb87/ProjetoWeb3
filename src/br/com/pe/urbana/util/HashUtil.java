package br.com.pe.urbana.util;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;



public class HashUtil {
	
	
	private static Logger logger =null;
	
	public void init(){
		logger = Logger.getRootLogger();
		BasicConfigurator.configure();
	}
	
	
	public static void main(String[] args) throws IOException {
		 URL PATH_ARQUIVO_LOGGER = HashUtil.class.getResource("WEB-INF/relatorios/recibo.jasper");
		
		System.out.println(PATH_ARQUIVO_LOGGER.getPath()  +"br\\com\\pe\\urbana\\log4j.properties");

		
//		Date dt = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//		logger.setLevel(Level.FATAL);
//		logger.debug("teste debug");
//		String path = "/tmp/file.txt";
//
//		String aux = sdf.format(dt);
//		BufferedWriter buffWrite = new BufferedWriter((new OutputStreamWriter(new FileOutputStream(path), Charset.forName( "ISO-8859-1" ))));
//		buffWrite.append("       Comprovante de Reagendamento \n\n");
//		buffWrite.append("Nome       :"+ "\n");
//		buffWrite.append("__________________________________________\n");
//		buffWrite.append("__________________________________________\n");
//		buffWrite.append("\n");
//		buffWrite.append("__________________________________________\n");
//		buffWrite.append("\n");
//		buffWrite.append("__________________________________________\n");
//		buffWrite.append("         (Atendente)\n\n");
//		buffWrite.append("Emitido em  :"+aux+"\n");
//		buffWrite.close();
//
//		escritor(path);
//		leitor(path);
//		
//		    PrintService [] printService  = PrintServiceLookup.lookupPrintServices(DocFlavor.INPUT_STREAM.AUTOSENSE,null);
//		    System.out.println("Quantas impressora"+ printService.length);
//		    PrintService impressoraPadrao = PrintServiceLookup.lookupDefaultPrintService();
//		    System.out.println("A  impressora padrao é " + impressoraPadrao.getName());
//		    DocFlavor docFlavor =  DocFlavor.INPUT_STREAM.AUTOSENSE;
//		    HashDocAttributeSet hashDocAttributeSet= new HashDocAttributeSet();
//		    try {         
//		          FileInputStream fileInputStream = new FileInputStream(path);
//		          Doc doc = new SimpleDoc(fileInputStream, docFlavor, hashDocAttributeSet);
//		          PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
//		          PrintService printServico =
//		                    ServiceUI.printDialog(null, 300, 200, printService, impressoraPadrao, docFlavor, printRequestAttributeSet);
//		            
//		          try {
//				      PrinterJob pjob = PrinterJob.getPrinterJob();
//				      pjob.setJobName("Graphics Demo Printout");
//				      pjob.setCopies(1);
//				      pjob.setPrintable(new Printable() {
//				        public int print(Graphics pg, PageFormat pf, int pageNum) {
//				          if (pageNum > 0) // we only print one page
//				            return Printable.NO_SUCH_PAGE; // ie., end of job
//
//				          pg.drawString("heider \n bezerra Soares \n assim mesmo", 10, 10);
//				          pg.drawString("heider \n bezerra Soares \n assim mesmo", 10, 20);
//				          pg.drawString("heider \n bezerra Soares \n assim mesmo", 30, 30);
//				          pg.drawString("heider \n bezerra Soares \n assim mesmo", 40, 10);
//				          pg.drawString("heider \n bezerra Soares \n assim mesmo", 50, 10);
//				          pg.drawString("heider \n bezerra Soares \n assim mesmo", 60, 10);
//				          pg.drawString("heider \n bezerra Soares \n assim mesmo", 70, 10);
//				          pg.drawString("heider \n bezerra Soares \n assim mesmo", 80, 10);
//				          pg.drawString("heider \n bezerra Soares \n assim mesmo", 90, 10);
//
//				          return Printable.PAGE_EXISTS;
//				        }
//				      });
//
//				      if (pjob.printDialog() == false) // choose printer
//				        return; 
//				      pjob.print(); 
//				    } catch (PrinterException pe) {
//				      pe.printStackTrace();
//				    }
//				  
//		          
//		          if (printServico != null) {
//		            DocPrintJob docPrintJob = printServico.createPrintJob();
//		                //madar imprimir documento
//		            try{                   
//		                    docPrintJob.print(doc, printRequestAttributeSet);            
//		                }  catch(PrintException ex){
//		                    JOptionPane.showMessageDialog(null,ex);
//		                }                
//		        }
//		        }   catch (FileNotFoundException ex) {
//		           JOptionPane.showMessageDialog(null,ex);
//		        }
//		    
	}
	

	public static String gerarMD5(String msg) throws Exception {
		MessageDigest m;

		try {
			m = MessageDigest.getInstance("MD5");
			m.update(msg.getBytes(), 0, msg.length());
			BigInteger i = new BigInteger(1, m.digest());

			// Formatando o resuldado em uma cadeia de 32 caracteres,
			// completando com 0 caso falte
			msg = String.format("%1$032X", i);
		}

		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return msg;

	}
	
	public static void leitor(String path) throws IOException {
		BufferedReader buffRead = new BufferedReader(new FileReader(path));
		String linha = "";
		while (true) {
			if (linha != null) {
				System.out.println(linha);

			} else
				break;
			linha = buffRead.readLine();
		}
		buffRead.close();
	}

	public static void escritor(String path) throws IOException {
		BufferedWriter buffWrite = new BufferedWriter(new FileWriter(path));
		String linha = "";
		Scanner in = new Scanner(System.in);
		System.out.println("Escreva algo: ");
		linha = in.nextLine();
		buffWrite.append(linha + "\n");
		buffWrite.close();
	}
}
