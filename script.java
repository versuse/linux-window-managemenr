import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WindowManager {

    public static void moveWindow(String windowId, int x, int y) throws IOException {
        runCommand("xdotool windowmove " + windowId + " " + x + " " + y);
    }

    public static void resizeWindow(String windowId, int width, int height) throws IOException {
        runCommand("xdotool windowsize " + windowId + " " + width + " " + height);
    }

    public static void activateWindow(String windowId) throws IOException {
        runCommand("xdotool windowactivate " + windowId);
    }

    public static String getActiveWindowId() throws IOException {
        Process process = Runtime.getRuntime().exec("xdotool getactivewindow");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        return reader.readLine();
    }

    private static void runCommand(String command) throws IOException {
        Process process = Runtime.getRuntime().exec(command);
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java WindowManager <command>");
            System.out.println("Commands:");
            System.out.println("  move <x> <y>          - Move active window to specified coordinates");
            System.out.println("  resize <width> <height> - Resize active window to specified dimensions");
            System.out.println("  activate               - Activate the active window");
            System.exit(1);
        }

        String command = args[0];

        try {
            if (command.equals("move")) {
                String windowId = getActiveWindowId();
                System.out.print("Enter coordinates (x y): ");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String[] coordinates = reader.readLine().split(" ");
                int x = Integer.parseInt(coordinates[0]);
                int y = Integer.parseInt(coordinates[1]);
                moveWindow(windowId, x, y);
            } else if (command.equals("resize")) {
                String windowId = getActiveWindowId();
                System.out.print("Enter dimensions (width height): ");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String[] dimensions = reader.readLine().split(" ");
                int width = Integer.parseInt(dimensions[0]);
                int height = Integer.parseInt(dimensions[1]);
                resizeWindow(windowId, width, height);
            } else if (command.equals("activate")) {
                String windowId = getActiveWindowId();
                activateWindow(windowId);
            } else {
                System.out.println("Unknown command: " + command);
                System.exit(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
