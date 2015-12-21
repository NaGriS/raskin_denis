package GameDev;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;
import java.util.Vector;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import de.jeckle.RS2DOM.RS2DOM;
import java.io.FileOutputStream;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import GameDev.FileWork;

// �������� ����� ��� ������ � ����� ������
public class GameDev {   

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, 
            SQLException, InstantiationException, IllegalAccessException, 
            FileNotFoundException, ParseException 
    {        
        Connection connect = null;
        
        java.sql.Statement rqst = null; // ������ ��� ���������� SQL ��������
        Scanner sc = new Scanner(System.in); // ����� ��� ������ � ��������
        int table_number = 0;    // ����� ��������� �������
        Vector<String> vec_tab = new Vector<String>();
        StringBuilder sb = new StringBuilder(); //������ ��� ���������� ������
        ResultSet res; // ����� ��� �������� ����������� SQL �������
        Document doc = null; //����� ��� �������� XML 
    
// ����������    
        String str_1 = null;    // ��������� ����������    
        String [] str_2 = null;
        int menu_item = 0; // ���������� = ��������� ����� ����
        int insrt1;   // ������� �������� ��� �������� INSERT
        int insrt2;   // ������� �������� ��� �������� INSERT
        int insrt3;   // ������� �������� ��� �������� INSERT
        int insrt5;   // ������� �������� ��� �������� INSERT
        int cnt_col = 0; //���������� ��� ������ ����������� �������                               
   
    // ������������ ��������
        Class.forName("org.firebirdsql.jdbc.FBDriver").newInstance();
    
    //�������� ���� � ��
        String strPath = "jdbc:firebirdsql://localhost/"
                + "C:\\Firebird\\JAVA\\GameDev\\GameDev.fdb";
        Class.forName("org.firebirdsql.jdbc.FBDriver").newInstance();
    
    //����������� � ��
        connect = DriverManager.getConnection(strPath, "SYSDBA", "masterkey");
        if (connect == null) {
            System.err.println("������ ��� ����������� � ���� ������!");}
    
    //�������� ������ ��� ���������� SQL ��������
        rqst = connect.createStatement();
        System.out.println("����������� � �� ��������� �������!");
    
    // ��������� ������ ������ ��
        DatabaseMetaData metaData = connect.getMetaData();
        ResultSet reslt=metaData.getTables(str_1, str_1, str_1, str_2);
        while(reslt.next())
	{
            str_1=reslt.getString(3);
            if(!str_1.contains("$"))
                vec_tab.add(str_1);
        }

    // ������� ������ � ����
        while (menu_item != 6) {
            
        // ����� ����
            System.out.println("\n\n*******************************************");
            System.out.println("|     GAME DEVELOPMENT AND CYBERSPORT     |");
            System.out.println("*******************************************");
            System.out.println("��������� �������:");
            System.out.println("1.����� ������ ������");
            System.out.println("2.���������� ������ � ������� GAMES");
            System.out.println("3.���������� �������� ��������� DelProcMy");
            System.out.println("4.������ �� JSON ������ � GAMES � SALES");
            System.out.println("5.������� ����������� ������� � XML");
            System.out.println("6.����� �� ���������\n");
             
        // ����������� ������ ������ ����
            System.out.println("�������� �������:");
            try
            {
                menu_item = Integer.parseInt(sc.nextLine());
            }
            catch(NumberFormatException e)
            {
                System.err.println("������! ��������� ������������ ����� "
                        + "������ ����!\n");
                continue;
            }
             if (menu_item > 6 || menu_item < 1)
                System.err.println("������! ����� ���� � ����� ������� "
                        + "�����������!\n");
            
             
// �1 -----------------------------------------------------------------------------------------------
            if (menu_item == 1)
            {               
                System.out.println("������ ������:");
                for(int i=1;i<=vec_tab.size();i++)
                {
                    System.out.printf("%d. %s\n",i,vec_tab.elementAt(i-1));
                }
                                System.out.println("������� ����� ������� ��� "
                                        + "����������� �� ����������� ��� "
                                        + "\n0 ��� �������� � �������� ����:");
                try
                {
                    table_number=Integer.parseInt(sc.nextLine());
                }
                catch(NumberFormatException e)
                {
                    System.err.println("������! ����� ������ ���� ������!");
                    continue;
                }
                if((table_number > vec_tab.size()) || (table_number < 0)){
                    System.err.println("������! �������������� ����� �������!");
                    continue;
                }
                if(table_number == 0)
                {
                    continue;
                }
                        
                System.out.println();
                
            //���������� SQL �������
          	    res = rqst.executeQuery("SELECT * from "+ 
                            vec_tab.elementAt(table_number-1)); 
                    
            // ����� ����������
                cnt_col = res.getMetaData().getColumnCount();
                
            // ����� ����������� �������
            // ����� �������� ��������:
                for(int i = 1; i < cnt_col + 1; i++){
                    System.out.print(res.getMetaData().getColumnName(i)+
                            "  |  ");
                }
            // ����� ������� � �������:
                while(res.next())
                {
                    System.out.println();
                    for (int i = 1;i < cnt_col + 1;i++)
                    {
                            Object obj = res.getObject(i);
                            if (obj!=null)
                            {
                                    System.out.print(obj+"   \t   ");
                            }
                    }
                }
                System.out.println();
                
                continue;
            }
            
// �2 -----------------------------------------------------------------------------------------------
            if (menu_item == 2 )
            {
                    // ���� ���������� ��� ���������� ���� � �������
                    System.out.println("������� ID ����:");
                    try
                    {
                        insrt1=Integer.parseInt(sc.nextLine());
                    }
                    catch(NumberFormatException e)
                    {
                        System.err.println("������ ����� ID!");
                        continue;
                    }
                    if (insrt1 < 0)
                    {
                        System.err.println("������! ID > 0!");
                        continue;
                    }
                    
                    System.out.println("������� ID ������������ ����:");
                    try
                    {
                        insrt2=Integer.parseInt(sc.nextLine());
                    }
                    catch(NumberFormatException e)
                    {
                        System.err.println("������ ����� DEV_ID!");
                        continue;
                    }
                    if (insrt2 < 0)
                    {
                        System.err.println("������! Dev_ID > 0!");
                        continue;
                    }
                    
                    System.out.println("������� ID �����:");
                    try
                    {
                        insrt3=Integer.parseInt(sc.nextLine());
                    }
                    catch(NumberFormatException e)
                    {
                        System.err.println("������ ����� GENRE_ID!");
                        continue;
                    }
                    if (insrt3 < 0)
                    {
                        System.err.println("������! ID > 0!");
                        continue;
                    }
                    
                    System.out.println("������� �������� ����:");                                
                    String insrt4 = sc.nextLine();
                    if (insrt4.length()>30 || insrt4.isEmpty())
                    {
                        System.err.println("������! ��������� ������������ ��������! "
                                            + "�������� ������ ���� ������ 0 ��������");
                        continue;
                    }                    
                    
                    System.out.println("������� ��� �������� ����:");
                    try
                    {
                        insrt5=Integer.parseInt(sc.nextLine());
                    }
                    catch(NumberFormatException e)
                    {
                        System.err.println("������ ����� ����!");
                        continue;
                    }
                    if (insrt5 < 1994 || insrt5 > 2015)
                    {
                        System.err.println("������! ������� ������������ ���");
                        continue;
                    }
                // ���� ������ ������� �� ������ �������    
                    try
                    {                             
                        rqst.executeUpdate("insert into Games values (" 
                                    + insrt1+ ",'" + insrt2 + "','" + insrt3 
                                    + "','" + insrt4 + "'," + insrt5+ ");");
                        System.out.println("������ ��������� � �������!\n");
                    }
                    catch (SQLException se)
                    {
                        System.out.println(se.getMessage());
                    }
                    
                    continue;
            }
            
// �3 -----------------------------------------------------------------------------------------------            
            if (menu_item == 3)
            {
                    System.out.println("�������� ��������� DELPROCMY ������� "
                            + "�������������� �������������\n");
                    PreparedStatement pstmt = connect.prepareStatement("{call DELPROCMY}");
                    pstmt.execute();
                    System.out.println("�������� ��������� DELPROCMY ���������.");
                    pstmt.close();
                    continue;                    
            }
            
// �4 -----------------------------------------------------------------------------------------------            
            if (menu_item == 4)
            {
            // �������� ������� ��� ��������    
                JSONParser parser = new JSONParser(); 
                String textjson = FileWork.read("C:\\Firebird\\JAVA\\GameDev\\GAMES.json");
                Object obj = parser.parse(textjson);
                JSONObject jsonObj = (JSONObject) obj;                
                JSONArray jo = (JSONArray) jsonObj.get("GAMES");
                
            // ���������� ������ � ������� EQUIP
                for (int i = 0; i<jo.size();i++)
                {
                    JSONObject element = (JSONObject) jo.get(i);
                    try
                    {
                        rqst.executeUpdate("insert into GAMES values ('" + element.get("ID") 
                                + "','" + element.get("DEV_ID")+ "','"
                                + element.get("GENRE_ID")+ "','" + element.get("NAME")+ "','" 
                                + element.get("CREATE_YEAR") + "');");
                    }
                    catch (SQLException se)
                    {
                        System.out.println(se.getMessage());
                    }
                }
                
            // ���������� ������ � ������� COLOR
                textjson = FileWork.read("C:\\Firebird\\JAVA\\GameDev\\SALES.json");
                obj = parser.parse(textjson);
                jsonObj = (JSONObject) obj;                
                jo = (JSONArray) jsonObj.get("SALES");
                
                for (int i=0; i<jo.size();i++)
                {
                    JSONObject element = (JSONObject) jo.get(i);
                    try
                    {
                        rqst.executeUpdate("insert into SALES values ('" + element.get("ID") 
                                + "','" + element.get("GAME_ID")
                                + "','" + element.get("SALE_YEAR")
                                + "','" + element.get("SALES") + "');");
                    }
                    catch (SQLException se)
                    {
                        System.out.println(se.getMessage());
                    }
                }
                
                System.out.println("\n������ ������ �� JSON ������ � "
                        + "������� GAMES � SALES ��������!");
                
                continue;                              
            }
            
// �5 -----------------------------------------------------------------------------------------------            
            if (menu_item ==5)
            {
             System.out.println("������ ������:");
                for(int i=1;i<=vec_tab.size();i++)
                {
                    System.out.printf("%d. %s\n",i,vec_tab.elementAt(i-1));
                }
                
                System.out.println("������� ����� ������� ��� �������� � XML:");
                try
                {
                    table_number=Integer.parseInt(sc.nextLine());
                }
                catch(NumberFormatException e)
                {
                    System.err.println("������! ����� ������ ���� ������!");
                    continue;
                }
                if((table_number > vec_tab.size()) || (table_number < 0)){
                    System.err.println("������! ������� � ����� ������� ����������.");
                    continue;
                }
                if(table_number == 0){
                    continue;
                }
                System.out.println();
                
                //���������� SQL �������
          	    res = rqst.executeQuery("SELECT * from " + vec_tab.elementAt(table_number-1));                                        
                
                 Document xsd = RS2DOM.ResultSet2XSDDOM(res);
                 Document d = RS2DOM.ResultSet2DOM(res);
                    try {
				Transformer myTransformer =
					(TransformerFactory.newInstance()).newTransformer();
				System.out.println(
					"�����, ����������� XML, �������������� � ���� Schema.xml");
				myTransformer.transform(
					new DOMSource(xsd),
					new StreamResult(new FileOutputStream("C:"
                                                + "\\Firebird\\JAVA\\GameDev\\Schema.xml")));
                                System.out.println(
					"\n���������� ������� �������������� � XML ���� Data.xml");
				myTransformer.transform(
					new DOMSource(d),
					new StreamResult(new FileOutputStream("C"
                                                + ":\\Firebird\\JAVA\\GameDev\\Data.xml")));
			} catch (Exception e) {
				e.printStackTrace();
			}
             continue;       
            }
            
// �6 -----------------------------------------------------------------------------------------------            
            if (menu_item == 6)
            {
                    System.out.println("���������� ������...");
                    continue;
            }
        }
       System.exit(0);
    }
}
