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


// public class Test {
//    private static int result;
//    public String path = null;
//    public void main(String[] args) throws Exception {
//         EventQueue.invokeAndWait(new Runnable() {
//            @Override
//            public void run() {
//                String folder = System.getProperty("user.dir");
//                JFileChooser fc = new JFileChooser(folder);
//                result = fc.showOpenDialog(null);
               
//                if(result == JFileChooser.APPROVE_OPTION) {
//                     path = fc.getSelectedFile().getAbsolutePath();
//                 } else {
//                     path = null;
//                 }
//            }
//        });
//        System.out.println(result);
//        System.out.println(path);
//    }
// }


 
