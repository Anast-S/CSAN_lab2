import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static int SOCKET_PORT = 0;
    public static String SERVER = "";
    public static String FILE_TO_RECEIVED = "";
    public static String FILE_EXT="";
    public static int FILE_SIZE = 6022386;

    public static void main (String [] args ) throws IOException {
        FileOutputStream fileOutputStream=null;
        BufferedOutputStream bufferedOutputStream=null;
        InputStream inputStream=null;
        Socket socket=null;
        Scanner scanner=new Scanner(System.in);

        System.out.print("enter the path to save : ");
        FILE_TO_RECEIVED=scanner.nextLine();
        System.out.print("enter the server : ");
        SERVER=scanner.nextLine();
        System.out.print("enter the port : ");
        SOCKET_PORT=scanner.nextInt();

        try {
           socket=new Socket(SERVER,SOCKET_PORT);
           System.out.println("Connecting...");

           inputStream=socket.getInputStream();

           int extLen=inputStream.read();
           byte[] byteArrForExt=new byte[extLen];
           inputStream.read(byteArrForExt);
           FILE_EXT= new String(byteArrForExt);
           System.out.println("The extension of receiving file is : "+FILE_EXT);

           // receive file
           File fileToReceive=new File(FILE_TO_RECEIVED);
           fileOutputStream=new FileOutputStream(fileToReceive);
           bufferedOutputStream=new BufferedOutputStream(fileOutputStream);
           byte[] byteArrForFile=new byte[FILE_SIZE];
           int realSizeOfFile=inputStream.read(byteArrForFile,0,byteArrForFile.length);

           bufferedOutputStream.write(byteArrForFile,0,realSizeOfFile);
            System.out.println("File " + FILE_TO_RECEIVED
                    + " downloaded (" + realSizeOfFile + " bytes read)");
            bufferedOutputStream.flush();
        }catch (IOException e){
            System.out.print(e.getMessage());
        }
        finally {
            if (fileOutputStream != null) fileOutputStream.close();
            if (bufferedOutputStream != null) bufferedOutputStream.close();
            if (inputStream != null) inputStream.close();
            if (socket != null) socket.close();
            scanner.close();
        }
    }

}