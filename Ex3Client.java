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

            System.out.print("Reading " + numberOfBytesComingIn + " bytes.\nData received:");

            for(int i=0; i < numberOfBytesComingIn; i++) {
                serverMassageArray[i] = (byte)getIS.read();
                if (i%10 == 0){
                    System.out.println();
                    System.out.print("   ");
                }
                System.out.printf("%02X", serverMassageArray[i]);
            }
            System.out.println();

            short cs = checksum(serverMassageArray);
            System.out.printf("Checksum calculated: 0x%02X", cs);


            outStream.write(cs >> 8);
            outStream.write(cs);
            System.out.println();

            // Check the result
            if(getIS.read() == 1) {
                System.out.println("Response good.");
            } else {
                System.out.println("Response bad.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static short checksum(byte[] b){
        int sum = 0, i = 0;

        while(i < b.length-1) {
            sum += ((b[i]<<8 & 0xFF00) | (b[i+1] & 0xFF));

            if((sum & 0xFFFF0000) > 0) {
                sum &= 0xFFFF;
                sum++;
            }
            i += 2;
        }

        if((b.length)%2 == 1) {
            sum += ((b[i]<<8) & 0xFF00);

            // Wrap around, carry occurred
            if((sum & 0xFFFF0000) > 0) {
                sum &= 0xFFFF;
                sum++;
            }
        }

        // Return the 1's complement
        return (short) ~(sum & 0xFFFF);
    }
}