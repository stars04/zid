import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Main_Zomboid {
    public static void main(String[] args) throws Exception {


	String msg1 = """
	                       Please select the folder where your mods are located
                       
                       Example Windows: C:/Users/YourUserName/SteamLibrary/steamapps/workshop/content/108600
                       Example Mac/Linux: /Users/YourUserName/Library/Application Support/Steam/steamapps/workshop/content/108600/  
                       """;
        String msg2 = "Please select the folder where you want your text file to be saved";
        String title = "Select Locations";
 
        int messageType = JOptionPane.INFORMATION_MESSAGE;
         
        //Message box one to the user, explaining what to do
        JOptionPane.showMessageDialog(null, msg1, title, messageType);
  
 
        //Initializing variables needed to grab file paths and write out
 	//Some variables are Initialized later due to dependance on some of the loops
	String Workshopitems = "WorkshopItems=";
        String ModIds = "ModIds=";
        String Maplist = "Map=";
        String osName = System.getProperty("os.name");
        String path = null;
        String prepath = null;
        String preDest = null;
        String selectedDirectory;
 	String dest = null;
        Boolean isUnix = null;
 	
        //=======================================================================================================
        //===SECTION 0: Need to grab file paths for workshop and final text id and correct path if windows    ===
        //=======================================================================================================
 	
        //We check what operating system the user is runnning so that we 
	//can correct filepaths accordingly
        if (osName.contains("Windows")) {
            isUnix = false;
        } else if (osName.contains("Mac") || osName.contains("Linux")) {
            isUnix = true;
        }
 
 	//First try section to grab directory of zomboid workshop folders
        try {
            selectedDirectory = fc.selectDirectory();
            prepath = selectedDirectory;
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
 
 
        //If the user is using windows, replaces backslashes with 
 	//forward slashes to make path usable
        if (isUnix == false) {
            path = prepath.replace("\\", "/");
        }
        else {
            path = prepath;
        }
        //Message box two to the user, explaining what to do
        JOptionPane.showMessageDialog(null, msg2, title, messageType);
 	
 	//Second try block, we now look for the destination to place 
 	//the future text file containing our info
        try {
            selectedDirectory = fc.selectDirectory();
            preDest = selectedDirectory;
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
 
        //If the user is using windows, replaces backslashes with 
 	//forward slashes to make path usable
 	//May need to add the /*.txt to the windows destination
        if (isUnix == false) {
            dest = preDest.replace("\\", "/")+"/ZomboididModInfo.txt";
        }
        else {
            dest = preDest+"/ZomboidModInfo.txt";
        }
	 
        //Finish initializing most of the variables	
        File parentFolds = new File(path);
        String[] modFoldID = parentFolds.list();
        int numMods = modFoldID.length;
        File[] modSubFoldsFile = new File[numMods];
        String[] modSubParent = new String[numMods];
        String[] modDir = new String[numMods];
        String[] modSubDir = new String[numMods];
        String[] modMapPaths = new String[numMods];
        File[] modMapstemp = new File[numMods];
        ArrayList<String> modMaps = new ArrayList<>();
        String kw1 = "id=";
        String line = "empty";
 	
        //=======================================================================================================
        //===SECTION 1: Getting the mod and workshop information needed to write out to the server config file===
        //=======================================================================================================
 
        //Loop to create the full path for each mod folder and update 
	//Workshopitems with every mod folder ID as well as the path for each mod.info file
        for (int i = 0; i < numMods; i++) {
            modDir[i] = path +"/"+ modFoldID[i] + "/";
            modSubParent[i] = modDir[i] + "mods/";
            modSubFoldsFile[i] = new File(modSubParent[i]);
            modSubDir[i] = modSubParent[i] + modSubFoldsFile[i].list()[0] + "/mod.info";
            Workshopitems += modFoldID[i] + ";";  
        }
 
        //Loop to read each mod.info file and update ModIds with the ID of each mod
	for (int j = 0 ; j < numMods; j++) {
		BufferedReader in = new BufferedReader(new FileReader(modSubDir[j]));
		while (line != null) {
			line = in.readLine();
			if (line.contains(kw1)) {
				ModIds += line.replaceAll(kw1, "") + ";";
				line = "empty";
				break;
			}
		}
	} 
        //Here we make a loop that will create a path for EVERY mod 
	//As IF it was a map mod, further ahead we will check if the path is valid
        for (int m = 0; m < numMods; m++) {
            modMapPaths[m] = modSubParent[m] + modSubFoldsFile[m].list()[0] + "/media/maps/";
            modMapstemp[m] = new File(modMapPaths[m]);
        }
 
        //We now check if the path is valid, if it is we get the 
	//folders in that directory which have the map names needed and grab them
        for (int x = 0; x < numMods; x++) {
            try {
                modMaps.add(modMapstemp[x].list()[0]); 
            }
            catch (NullPointerException e) {
                System.out.println("No maps found in the directory, this was path number " + x + " of " + numMods + " total paths");
            }
        }
 
        //We now loop through the modMaps arraylist and add each map to the Maplist string
        for (int i = 0; i < modMaps.size(); i++) {
            Maplist += modMaps.get(i) + ";";
        }
        System.out.println(Maplist);
 
	//Finally prepares the text content to be written to the file 
	//by storing needed info into a string
        String textContent = Workshopitems + "\n" + "\n" + ModIds + "\n" + "\n" + Maplist;
 
        //=======================================================================================================
        //===SECTION 2: Getting the mod and workshop information actually written to a text file on your pc!!!===
        //=======================================================================================================
 
        try {
            FileWriter writer = new FileWriter(dest);
            writer.write(textContent);
            writer.close();
            System.out.println("Successfully wrote to the file.");
          } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
          }
     }
}
