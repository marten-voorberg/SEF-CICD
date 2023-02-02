import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CMDExecutorExample {
    public static void main(String[] args) {
        String line;
        String[] cmd = {"bash", "-c", "ls -la"};
        try {
            // Execute the command
            Process p = Runtime.getRuntime().exec(cmd);

            // Read the console output and print it to the standard output
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
