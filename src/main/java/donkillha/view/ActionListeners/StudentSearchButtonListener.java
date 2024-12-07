package donkillha.view.ActionListeners;
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  
public class StudentSearchButtonListener implements ActionListener {      
    @Override  
    public void actionPerformed(ActionEvent e) {  
        // Calling the backend to provide us with details about the student's score
        System.out.println("Search is in progress...");  
    }  
}
