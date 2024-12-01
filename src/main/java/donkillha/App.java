package donkillha;

import donkillha.controller.DatabaseManager;
import donkillha.controller.TablesCreator;
import donkillha.model.Admin;
import donkillha.model.Teacher;
/**
 * Hello world!
 *
 */
public class App 
{

    
    public static void main( String[] args )
    {
        
        // In case there wasn't a database ready yet
        DatabaseManager mysql=new DatabaseManager();
        TablesCreator tablesCreator = new TablesCreator(mysql);
        // This method will make the tables in case they don't exist and handles the foreign keys too
        tablesCreator.initialise();
        Admin admin = new Admin(mysql);
        admin.addNewTeacher(new Teacher("John","Doe",168473));

    }
}
