package GameDev;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class FileWork {
    public static String read(String fileName) throws FileNotFoundException {
    //Этот спец. объект для построения строки
    //Определяем файл
    File file = new File(fileName);
    
    StringBuilder sb = new StringBuilder();
 
    exists(fileName);
 
    try {
        //Объект для чтения файла в буфер
        BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
        try {
            //В цикле построчно считываем файл
            String s;
            while ((s = in.readLine()) != null) {
                sb.append(s);
                sb.append("\n");
            }
        } finally {
            //Закрытие файла
            in.close();
        }
    } catch(IOException e) {
        throw new RuntimeException(e);
    }
 
    //Возвращаем полученный текст с файла
    return sb.toString();
}
    private static void exists(String fileName) throws FileNotFoundException {
    File file = new File(fileName);
    if (!file.exists()){
        throw new FileNotFoundException(file.getName());
    }
}
    private static String fileName;    
}
