import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

//C:/Users/selev/Desktop
public class Server {

    public static int SOCKET_PORT = 0;
    public static String FILE_TO_SEND = "";
    public static String FILE_EXT="";

    public static void main (String [] args ) throws IOException {
        FileInputStream fileInputStream=null;
        BufferedInputStream bufferedInputStream=null;
        OutputStream outputStream=null;
        ServerSocket serverSocket=null;
        Socket socket=null;
        Scanner scanner=new Scanner(System.in);

        System.out.print("enter the path to file : ");
        FILE_TO_SEND=scanner.nextLine();
        System.out.print("enter the port : ");
        SOCKET_PORT=scanner.nextInt();

        try {
            serverSocket=new ServerSocket(SOCKET_PORT);
            while (true) {
                System.out.println("Waiting...");
                try {
                    socket=serverSocket.accept();
                    System.out.println("Accepted connection : " + socket);
                    outputStream=socket.getOutputStream();

                    File fileToSend=new File(FILE_TO_SEND);

                    //extension sending
                    FILE_EXT=getFileExtension(fileToSend);
                    outputStream.write(FILE_EXT.length());
                    byte[] byteArrForExt=FILE_EXT.getBytes();
                    outputStream.write(byteArrForExt);

                    //file sending
                    fileInputStream=new FileInputStream(fileToSend);
                    bufferedInputStream=new BufferedInputStream(fileInputStream);
                    byte[] byteArrForFile= new byte [(int)fileToSend.length()];
                    bufferedInputStream.read(byteArrForFile,0,byteArrForFile.length);
                    outputStream.write(byteArrForFile,0,byteArrForFile.length);

                    System.out.println("Sending " + FILE_TO_SEND + "(" + byteArrForFile.length + " bytes)");
                    outputStream.flush();
                    System.out.println("Done.");

                }catch (IOException e){
                    System.out.println(e.getMessage());
                }
                finally {
                    if (fileInputStream!=null) fileInputStream.close();
                    if (bufferedInputStream != null) bufferedInputStream.close();
                    if (outputStream != null) outputStream.close();
                    if (socket!=null) socket.close();
                    scanner.close();
                }
            }
        }
        finally {
            if (serverSocket != null) serverSocket.close();
        }
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
}
