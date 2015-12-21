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

// Основной класс для работы с базой данных
public class GameDev {   

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, 
            SQLException, InstantiationException, IllegalAccessException, 
            FileNotFoundException, ParseException 
    {        
        Connection connect = null;
        
        java.sql.Statement rqst = null; // объект для выполнения SQL запросов
        Scanner sc = new Scanner(System.in); // класс для работы с консолью
        int table_number = 0;    // номер введенной таблицы
        Vector<String> vec_tab = new Vector<String>();
        StringBuilder sb = new StringBuilder(); //объект для построения строки
        ResultSet res; // Класс для хранения результатов SQL запроса
        Document doc = null; //Класс для хранения XML 
    
// Переменные    
        String str_1 = null;    // Строковые переменные    
        String [] str_2 = null;
        int menu_item = 0; // переменная = выбранный пункт меню
        int insrt1;   // входной аргумент для операции INSERT
        int insrt2;   // входной аргумент для операции INSERT
        int insrt3;   // входной аргумент для операции INSERT
        int insrt5;   // входной аргумент для операции INSERT
        int cnt_col = 0; //переменная для вывода содержимого таблицы                               
   
    // Иницализация драйвера
        Class.forName("org.firebirdsql.jdbc.FBDriver").newInstance();
    
    //Указание пути к БД
        String strPath = "jdbc:firebirdsql://localhost/"
                + "C:\\Firebird\\JAVA\\GameDev\\GameDev.fdb";
        Class.forName("org.firebirdsql.jdbc.FBDriver").newInstance();
    
    //Подключение к БД
        connect = DriverManager.getConnection(strPath, "SYSDBA", "masterkey");
        if (connect == null) {
            System.err.println("Ошибка при подключении к базе данных!");}
    
    //Создание класса для выполнения SQL запросов
        rqst = connect.createStatement();
        System.out.println("Подключение к БД выполнено успешно!");
    
    // Получение списка таблиц БД
        DatabaseMetaData metaData = connect.getMetaData();
        ResultSet reslt=metaData.getTables(str_1, str_1, str_1, str_2);
        while(reslt.next())
	{
            str_1=reslt.getString(3);
            if(!str_1.contains("$"))
                vec_tab.add(str_1);
        }

    // Процесс работы с меню
        while (menu_item != 6) {
            
        // вывод меню
            System.out.println("\n\n*******************************************");
            System.out.println("|     GAME DEVELOPMENT AND CYBERSPORT     |");
            System.out.println("*******************************************");
            System.out.println("Возможные команды:");
            System.out.println("1.Вывод списка таблиц");
            System.out.println("2.Добавление записи в таблицу GAMES");
            System.out.println("3.Выполнение хранимой процедуры DelProcMy");
            System.out.println("4.Импорт из JSON данных в GAMES и SALES");
            System.out.println("5.Экспорт содержимого таблицы в XML");
            System.out.println("6.Выход из программы\n");
             
        // Определение номера пункта меню
            System.out.println("Выберите команду:");
            try
            {
                menu_item = Integer.parseInt(sc.nextLine());
            }
            catch(NumberFormatException e)
            {
                System.err.println("Ошибка! Проверьте правильность ввода "
                        + "пункта меню!\n");
                continue;
            }
             if (menu_item > 6 || menu_item < 1)
                System.err.println("Ошибка! Пункт меню с таким номером "
                        + "отсутствует!\n");
            
             
// №1 -----------------------------------------------------------------------------------------------
            if (menu_item == 1)
            {               
                System.out.println("Список таблиц:");
                for(int i=1;i<=vec_tab.size();i++)
                {
                    System.out.printf("%d. %s\n",i,vec_tab.elementAt(i-1));
                }
                                System.out.println("Введите номер таблицы для "
                                        + "отображения ее содержимого или "
                                        + "\n0 для возврата в основное меню:");
                try
                {
                    table_number=Integer.parseInt(sc.nextLine());
                }
                catch(NumberFormatException e)
                {
                    System.err.println("Ошибка! Номер должен быть числом!");
                    continue;
                }
                if((table_number > vec_tab.size()) || (table_number < 0)){
                    System.err.println("Ошибка! Несуществующий номер таблицы!");
                    continue;
                }
                if(table_number == 0)
                {
                    continue;
                }
                        
                System.out.println();
                
            //Выполнение SQL запроса
          	    res = rqst.executeQuery("SELECT * from "+ 
                            vec_tab.elementAt(table_number-1)); 
                    
            // Вывод результата
                cnt_col = res.getMetaData().getColumnCount();
                
            // Вывод содержимого таблицы
            // Вывод названия столбцов:
                for(int i = 1; i < cnt_col + 1; i++){
                    System.out.print(res.getMetaData().getColumnName(i)+
                            "  |  ");
                }
            // Вывод записей в таблице:
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
            
// №2 -----------------------------------------------------------------------------------------------
            if (menu_item == 2 )
            {
                    // Ввод аргументов для добавдения игры в таблицу
                    System.out.println("Введите ID игры:");
                    try
                    {
                        insrt1=Integer.parseInt(sc.nextLine());
                    }
                    catch(NumberFormatException e)
                    {
                        System.err.println("Ошибка ввода ID!");
                        continue;
                    }
                    if (insrt1 < 0)
                    {
                        System.err.println("Ошибка! ID > 0!");
                        continue;
                    }
                    
                    System.out.println("Введите ID разработчика игры:");
                    try
                    {
                        insrt2=Integer.parseInt(sc.nextLine());
                    }
                    catch(NumberFormatException e)
                    {
                        System.err.println("Ошибка ввода DEV_ID!");
                        continue;
                    }
                    if (insrt2 < 0)
                    {
                        System.err.println("Ошибка! Dev_ID > 0!");
                        continue;
                    }
                    
                    System.out.println("Введите ID жанра:");
                    try
                    {
                        insrt3=Integer.parseInt(sc.nextLine());
                    }
                    catch(NumberFormatException e)
                    {
                        System.err.println("Ошибка ввода GENRE_ID!");
                        continue;
                    }
                    if (insrt3 < 0)
                    {
                        System.err.println("Ошибка! ID > 0!");
                        continue;
                    }
                    
                    System.out.println("Введите название игры:");                                
                    String insrt4 = sc.nextLine();
                    if (insrt4.length()>30 || insrt4.isEmpty())
                    {
                        System.err.println("Ошибка! Проверьте правильность названия! "
                                            + "Название должно быть короче 0 символов");
                        continue;
                    }                    
                    
                    System.out.println("Введите год создания игры:");
                    try
                    {
                        insrt5=Integer.parseInt(sc.nextLine());
                    }
                    catch(NumberFormatException e)
                    {
                        System.err.println("Ошибка ввода года!");
                        continue;
                    }
                    if (insrt5 < 1994 || insrt5 > 2015)
                    {
                        System.err.println("Ошибка! Введите существующий год");
                        continue;
                    }
                // Ввод данных таблицу по новому клиенту    
                    try
                    {                             
                        rqst.executeUpdate("insert into Games values (" 
                                    + insrt1+ ",'" + insrt2 + "','" + insrt3 
                                    + "','" + insrt4 + "'," + insrt5+ ");");
                        System.out.println("Запись добавлена в таблицу!\n");
                    }
                    catch (SQLException se)
                    {
                        System.out.println(se.getMessage());
                    }
                    
                    continue;
            }
            
// №3 -----------------------------------------------------------------------------------------------            
            if (menu_item == 3)
            {
                    System.out.println("Хранимая процедура DELPROCMY удаляет "
                            + "неиспользуемых разработчиков\n");
                    PreparedStatement pstmt = connect.prepareStatement("{call DELPROCMY}");
                    pstmt.execute();
                    System.out.println("Хранимая процедура DELPROCMY выполнена.");
                    pstmt.close();
                    continue;                    
            }
            
// №4 -----------------------------------------------------------------------------------------------            
            if (menu_item == 4)
            {
            // Создание объекта для парсинга    
                JSONParser parser = new JSONParser(); 
                String textjson = FileWork.read("C:\\Firebird\\JAVA\\GameDev\\GAMES.json");
                Object obj = parser.parse(textjson);
                JSONObject jsonObj = (JSONObject) obj;                
                JSONArray jo = (JSONArray) jsonObj.get("GAMES");
                
            // Добавление данных в таблицу EQUIP
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
                
            // Добавление данных в таблицу COLOR
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
                
                System.out.println("\nИмпорт данных из JSON файлов в "
                        + "таблицы GAMES и SALES выполнен!");
                
                continue;                              
            }
            
// №5 -----------------------------------------------------------------------------------------------            
            if (menu_item ==5)
            {
             System.out.println("Список таблиц:");
                for(int i=1;i<=vec_tab.size();i++)
                {
                    System.out.printf("%d. %s\n",i,vec_tab.elementAt(i-1));
                }
                
                System.out.println("Введите номер таблицы для экспорта в XML:");
                try
                {
                    table_number=Integer.parseInt(sc.nextLine());
                }
                catch(NumberFormatException e)
                {
                    System.err.println("Ошибка! Номер должен быть числом!");
                    continue;
                }
                if((table_number > vec_tab.size()) || (table_number < 0)){
                    System.err.println("Ошибка! Таблица с таким номером отсутсвует.");
                    continue;
                }
                if(table_number == 0){
                    continue;
                }
                System.out.println();
                
                //Выполнение SQL запроса
          	    res = rqst.executeQuery("SELECT * from " + vec_tab.elementAt(table_number-1));                                        
                
                 Document xsd = RS2DOM.ResultSet2XSDDOM(res);
                 Document d = RS2DOM.ResultSet2DOM(res);
                    try {
				Transformer myTransformer =
					(TransformerFactory.newInstance()).newTransformer();
				System.out.println(
					"Схема, описывающая XML, экспортирована в файл Schema.xml");
				myTransformer.transform(
					new DOMSource(xsd),
					new StreamResult(new FileOutputStream("C:"
                                                + "\\Firebird\\JAVA\\GameDev\\Schema.xml")));
                                System.out.println(
					"\nСодержимое таблицы экспортировано в XML файл Data.xml");
				myTransformer.transform(
					new DOMSource(d),
					new StreamResult(new FileOutputStream("C"
                                                + ":\\Firebird\\JAVA\\GameDev\\Data.xml")));
			} catch (Exception e) {
				e.printStackTrace();
			}
             continue;       
            }
            
// №6 -----------------------------------------------------------------------------------------------            
            if (menu_item == 6)
            {
                    System.out.println("Завершение работы...");
                    continue;
            }
        }
       System.exit(0);
    }
}
