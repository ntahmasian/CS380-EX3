/**
 * Created by Narvik on 4/19/17.
 */

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;


public class Ex3Client {
    public static void main ( String [] args ) {

        try (Socket socket = new Socket("codebank.xyz", 38103)) {

            // Shows we are connected to the server
            System.out.println("Connected to server.");

            int numberOfBytesComingIn = 0;
            InputStream getIS = socket.getInputStream();
            OutputStream outStream = socket.getOutputStream();

            numberOfBytesComingIn = getIS.read();

            byte serverMassageArray[] = new byte[numberOfBytesComingIn];

            for(int i=0; i < numberOfBytesComingIn; i++) {
                serverMassageArray[i] = (byte)getIS.read();
            }

            checksum(serverMassageArray);





            System.out.print("Reading 133 bytes.\nData received:\n");



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static short checksum(byte[] b){

        return 0;
    }
}
