import javax.swing.*;
import javax.swing.border.Border;

import java.awt.event.*;
import java.awt.*;
import java.text.SimpleDateFormat;  
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import java.io.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet; 

public class Chatbot extends JFrame implements ActionListener,KeyListener{

    ChatScreen chatArea;
	JTextField txt ;
	JButton snd;
	String name,message,Bname = "BOT";
	XSSFSheet sheet;
	String s1,s2;	
	int flg=0,nonsimwrd,simwrd;

    public Chatbot() {

		Font font1 = new Font("SansSerif", Font.PLAIN, 18);

		setSize(700,901);
		setLocation(500,0);
		setTitle("ChatBot");
		setLayout(null);
		getContentPane().setBackground(new java.awt.Color(60,61,75));
		
		Icon ic1 = new ImageIcon("sendblkawt.jpg");
        chatArea = new ChatScreen();
		txt = new JTextField("Enter Your Quetion Here");
		snd = new JButton(ic1);

		txt.setCaretColor(Color.WHITE);

		Border lineBorder = BorderFactory.createLineBorder(Color.GRAY,2,true);
        txt.setBorder(lineBorder);  
		
		add(chatArea);
		add(txt);
		txt.add(snd);
		
		txt.setFont(font1);
		
		snd.setBorderPainted(false);
		snd.setMnemonic('S'); 
		snd.setToolTipText("Send Quetion");

		chatArea.setBounds(-7,-7,700,838);
		txt.setBounds(2,826,682,35);
		snd.setBounds(651,5,25,25);
		
		txt.setOpaque(false);
		txt.setForeground(Color.WHITE);
		
		snd.addActionListener(this);
		txt.addKeyListener(this);
		snd.addMouseListener(new MouseAdapter() {
			
			public void mouseEntered(MouseEvent e) {
				if("Enter Your Quetion Here".equals(txt.getText())==false )
					if("".equals(txt.getText())==false)
					{
						snd.setIcon(new ImageIcon("sendM.jpg"));
						snd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));		
					}
			}

			public void mouseExited(MouseEvent e) {
				if("Enter Your Quetion Here".equals(txt.getText())==false)
					if("".equals(txt.getText())==false)
					{
						snd.setIcon(new ImageIcon("sendO.jpg"));
						snd.setCursor(Cursor.getDefaultCursor());
					}
			}
		});

		entname();
		
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");  
		Date date = new Date(); 
		String ltime = formatter.format(date);
		
		if(ltime.compareTo("04:00:00")>=0)
			 message="Good Morning "+name+" ,How may i help you?";
				 
		if(ltime.compareTo("12:00:00")>=0)
			 message="Good Afternoon "+name+" ,How may i help you?";
			 
		if(ltime.compareTo("17:00:00")>=0)
			 message="Good Evening "+name+" ,How may i help you?";
		 
		msgtoscrn(message);	

		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		setVisible(true);	
		setResizable(false);
		txt.requestFocus();
	}

	public void entname()
	{
		JTextField textField = new JTextField("Shreyash");
        Object[] message = {"Please Enter Your Name (First Name Only)!", textField};
        
        int option = JOptionPane.showConfirmDialog(null, message, "Registration", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) 
		{
            name = textField.getText();
			
			if(name.matches("^[a-zA-Z]*$")==false)
			{
				JOptionPane.showMessageDialog(null, "Please Enter Alphabets Only !");
				entname();
			}
			else if (name.isEmpty()==false)
			{
				try
				{						
					String str[] = name.split("\\s");
					name = str[0];
					
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy, hh:mmaa");  
					Date date = new Date(); 
					String ltime = formatter.format(date);
					
					saveData(name,ltime,"log");
				}catch(Exception ex){System.out.println(ex);}
			} 
			else
			{
				JOptionPane.showMessageDialog(null, "Please Enter Your Name ! (First Name Only)");
				entname();
			}
        } 
		else if (option == JOptionPane.CANCEL_OPTION || option == -1)
			System.exit(0);		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		message = txt.getText();
		if(message.equals("Enter Your Quetion Here")==false)
		{
			chatArea.addChatBox(message,name,ChatBox.BoxType.RIGHT);
			txt.setText("Enter Your Quetion Here");
			txt.setCaretPosition(0);
			flg=0;			
			message = message.toLowerCase();
			reply(message);			
		}
		txt.requestFocus();
		snd.setBorderPainted(false);
		snd.setIcon(new ImageIcon("sendblkawt.jpg"));
	}

	public void keyTyped(KeyEvent e)
	{	
	   if(e.getKeyChar() == KeyEvent.VK_ENTER)
	   {
			ActionEvent ex = new ActionEvent(this,0,"snd");			
			actionPerformed(ex);
	   }


	   
		if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE && "Enter Your Quetion Here".equals(txt.getText()) || e.getKeyChar() == KeyEvent.VK_ENTER)
		{
			e.consume();
			flg = -1;
		}
		else
			if(flg==0)
			{
				txt.setText("");
				snd.setIcon(new ImageIcon("sendO.jpg"));
				snd.setBorderPainted(true);
			}	
							
		flg++;

		if("".equals(txt.getText()) && flg>1)
		{
			txt.setText("Enter Your Quetion Here");
			txt.setCaretPosition(0);
			flg=0;
			snd.setBorderPainted(false);
			snd.setIcon(new ImageIcon("sendblkawt.jpg"));
		}
	}

	public void keyReleased(KeyEvent k)
	{
		if(k.getKeyCode()==17 )
			if("".equals(txt.getText()) && flg>=1)
			{
				txt.setText("Enter Your Quetion Here");
				txt.setCaretPosition(0);
				flg=0;
				snd.setBorderPainted(false);
				snd.setIcon(new ImageIcon("sendblkawt.jpg"));
			}
			else if("Enter Your Quetion Here".equals(txt.getText())==false)
			{
				snd.setIcon(new ImageIcon("sendO.jpg"));
				snd.setBorderPainted(true);
			}	
	}	
	
	public void keyPressed(KeyEvent kk)
	{
		if(kk.getKeyChar() == KeyEvent.VK_BACK_SPACE &&"Enter Your Quetion Here".equals(txt.getText()))
	   {
	  	 kk.consume();
		 flg = -1;
	   }
		if(kk.isAltDown() && kk.getKeyCode()==83 && "Enter Your Quetion Here".equals(txt.getText()))
		{
			kk.consume();
			flg = -1;
		}
		
		if(kk.getKeyCode()==17 && flg==0)
		{
			txt.setText("");
			flg++;
		}
	   
	}

	public void msgtoscrn(String str)
	{
		new java.util.Timer().schedule(
				new java.util.TimerTask() {
					public void run() {
						try
						{
							chatArea.addChatBox(str,Bname,ChatBox.BoxType.LEFT);						
						}catch(Exception ex){}
					}
				}, 
				700 
			);
	}
	
	public void reply(String userInput)
	{
		double threshold = 0.0;
		try{
			int j=0,i=-1,rowCount;
			XSSFSheet sheet;
			String path="./Data.xlsx",question;
			XSSFWorkbook book = new XSSFWorkbook(path);
			sheet = book.getSheet("Sheet1");

			rowCount = sheet.getPhysicalNumberOfRows();

			while(j<rowCount)
			{                    
				question = sheet.getRow(j).getCell(0).getStringCellValue();
				question = question.toLowerCase();
								
				cleanData(userInput, question);
					
				double similarity = calculateSimilarity(s1, s2);
					
				wordSim();
                
				if( simwrd > nonsimwrd && similarity > threshold)
				{
					threshold=similarity;
					i=j;
					System.out.println("sim="+nonsimwrd /2);
				}	
				j++;		
			}	
			System.out.println(threshold);
			if(threshold<0.5 || i==-1)
			{
				
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy, hh:mmaa");  
				Date date = new Date(); 
				String ltime = formatter.format(date);
				
				saveData(userInput,ltime,"que");
				
				msgtoscrn("Sorry but I don't have an answer for this question.  I am storing this question in my database.I will improve myself");
			}
			else
				msgtoscrn(sheet.getRow(i).getCell(1).getStringCellValue());
		}catch(Exception ep){}
	}

	private double calculateSimilarity(String str1, String str2) {
        Set<Character> set1 = buildSet(str1);
        Set<Character> set2 = buildSet(str2);

        Set<Character> intersection = new HashSet<>(set1);

        intersection.retainAll(set2);

        Set<Character> union = new HashSet<>(set1);
        union.addAll(set2);

        if (union.isEmpty())
            return 0.0;

        return (double) intersection.size() / union.size();
    }

    private Set<Character> buildSet(String str) {
        Set<Character> charSet = new HashSet<>();
        for (char c : str.toLowerCase().toCharArray()) {
            String s = Character.toString(c);
            if(s.matches("[A-Za-z0-9]") || s.equals("*") )
                charSet.add(c);
        }
        return charSet;		
    }
	
	public void cleanData(String str1,String str2)
	{
		String spltstr[]=str1.split("\\s");
		s1 = clean(spltstr);
		spltstr=str2.split("\\s");
		s2 = clean(spltstr);   
	}
	
	public String clean(String str[])
	{
		String words[]={"I", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours", "yourself", "yourselves",
		"he", "him", "his","what's", "himself", "she", "her", "hers", "herself", "it", "its", "itself", "they", "them", "their",
		"theirs", "themselves", "what", "which", "who", "whom", "this", "that", "these", "those", "am", "is", "are", "was",
		"were", "be", "been", "being", "have", "has", "had", "having", "do", "does", "did", "doing", "a", "an", "the", "and",
		"but", "if", "or", "because", "as", "until", "while", "of", "at", "by", "for", "with", "about", "against", "between",
		"into", "through", "during", "before", "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off",
		"over", "under", "again", "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any",
		"both", "each", "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so",
		"than", "too", "very", "s", "t", "can", "will", "just", "don", "should", "now", "d", "ll", "m", "o", "re", "ve",
		"y", "ain", "aren", "couldn", "didn", "doesn", "hadn", "hasn", "haven", "isn", "ma", "mightn", "mustn", "needn",
		"shan", "shouldn", "wasn", "weren", "won", "wouldn","tell","me","about","Who","is","the","what","which","why","how","Does","are","can","you","explain","of","in","describe","express","by","used","for","mean","means","meaning","define","defination","differ","difference","different","between","give","info","information"};
		int n,i,j,flg=0;
		n= words.length;
		
		StringBuffer sb = new StringBuffer();
		
		for(i=0;i<str.length;i++)
		{
			flg=0;

			if(str[i].equalsIgnoreCase("Not" )|| str[i].equalsIgnoreCase("No") || str[i].equalsIgnoreCase("Nothing"))
            {
               msgtoscrn("Sorry i dont have the answer for this quetion"); 
			   throw new CustomException("StopProgram");
            }
						
			for(j=0;j<n;j++)
				if(str[i].equalsIgnoreCase(words[j]))
					flg=1;

			if(flg==0)
				sb.append(str[i]+" ");
		}
		return sb.toString();
		}

	public void wordSim()
    {
        int flg;
		simwrd=0;
		nonsimwrd=0;

		String clnstr1 =s1.replaceAll("[^a-zA-Z0-9 *]", "");
		String clnstr2 =s2.replaceAll("[^a-zA-Z0-9 *]", "");

        String str1[] = clnstr1.split("\\s");
        String str2[] = clnstr2.split("\\s");

		// Can you discuss the role of the hx-* attribute in HTMX?
		
        for(int i=0;i<str1.length;i++)
        {
            flg=0;

			for(int j=0;j<str2.length;j++)
				if(str1[i].equalsIgnoreCase(str2[j]))
                {
					simwrd++;
                    flg=1;
                }

            if(flg==0)
                nonsimwrd++;   
        }
    }
	
	public void saveData(String data,String date,String type)throws Exception
	{
		String path = "./Data.xlsx";
		int i=0;

        FileInputStream fis = new FileInputStream(new File(path));
        XSSFWorkbook book = new XSSFWorkbook(fis);
		XSSFSheet sheet;
		Cell cell;
		
		if(type == "que")		
			sheet = book.getSheet("Sheet2");
		else
			sheet = book.getSheet("Sheet3");

        int rowCount = sheet.getPhysicalNumberOfRows();
        
        XSSFRow row = sheet.createRow(rowCount);
		rowCount++;

        cell = row.createCell(i++);
        cell.setCellValue(data);
		cell = row.createCell(i);
        cell.setCellValue(date);

        fis.close(); 

		FileOutputStream fos = new FileOutputStream(new File(path));
        book.write(fos);
        fos.close();
	}

	public class CustomException extends RuntimeException {
		public CustomException(String message) {
			super(message);
		}
	}

	public static void main(String args[]) {
        
        new Chatbot();
    }
}
