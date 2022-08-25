import java.io.*;
class Sample {
 
    public static void main(String[] args) throws IOException
    {
        // create instance of directory
        File dir = new File("D:\\hcl\\eclipse-workspace\\simplilearn\\phase-3-project\\sporty-shoes-api\\src\\main\\java\\com\\sporty\\shoes\\enteties");
 
        // create object of PrintWriter for output file
        PrintWriter pw = new PrintWriter("output.txt");
 
        // Get list of all the files in form of String Array
       File[] fileNames = dir.listFiles();
 
        // loop for reading the contents of all the files
        // in the directory GeeksForGeeks
        for (File fileName : fileNames) {
            String name=fileName.getName();
            if(name.contains(".java")) {
        	System.out.println(fileName.getName());
             
            // create instance of file from Name of
            // the file stored in string Array
            File f = fileName;
 
            // create object of BufferedReader
            BufferedReader br = new BufferedReader(new FileReader(f));
           
            pw.println(fileName.getName());
 
            // Read from current file
            String line = br.readLine();
            while (line != null) {
 
                // write to the output file
                pw.println(line);
                line = br.readLine();
            }
            pw.flush();
            }
        }
        System.out.println("Reading from all files" +
        " in directory " + dir.getName() + " Completed");
    }
}