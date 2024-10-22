package Server;

import java.io.*;
import java.util.Properties;

public class Configurations {

    private static Configurations instanceSingeltone=null;
    Properties prop;

    private Configurations() {
        prop = new Properties();
        try {
            InputStream input = Configurations.class.getClassLoader().getResourceAsStream("config.properties");
            if (input != null) {
                prop.load(input);
            } else {
                System.out.println("File not found");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //the public function that check if there is an instance of this class return it, else creat new one and then return it
    public static Configurations getInstance() {
        if(instanceSingeltone==null)
            instanceSingeltone=new Configurations();
        return instanceSingeltone;
    }

    //get the requested property
    public String getProp(String key) {
        return prop.getProperty(key);
    }

    //set new value of properties to the file
    public void setProp(String key, String value) {
        try{
            OutputStream out = new FileOutputStream("C:\\Users\\User\\Desktop\\Maze_Project\\MAZE\\resources\\config.properties");
            //set the new property
            prop.setProperty(key,value);
            //save it to the file
            prop.store(out,"");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
