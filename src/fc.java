import java.awt.EventQueue;
import javax.swing.JFileChooser;

public class fc {
    private static int result;
    private static String path = null;

    public static String selectDirectory() throws Exception {
         EventQueue.invokeAndWait(() -> {
             JFileChooser fc = new JFileChooser();
             fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
             result = fc.showOpenDialog(null);
             
             if(result == JFileChooser.APPROVE_OPTION) {
                path = fc.getSelectedFile().getAbsolutePath();
             } else {
                path = null;
             }
         });
         System.out.println(result);
         System.out.println(path);
         return path;
    }
 } 
