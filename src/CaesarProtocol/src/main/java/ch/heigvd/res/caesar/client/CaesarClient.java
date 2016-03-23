package ch.heigvd.res.caesar.client;

import ch.heigvd.res.caesar.protocol.Protocol;

import java.io.*;
import java.util.Scanner;
import java.util.logging.Logger;
import java.net.Socket;

/**
 *
 * @author Olivier Liechti (olivier.liechti@heig-vd.ch)
 */
public class CaesarClient {

    private static final Logger LOG = Logger.getLogger(CaesarClient.class.getName());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tH:%1$tM:%1$tS::%1$tL] Client > %5$s%n");
        LOG.info("Caesar client starting...");

        try {
            ////// INITIALISATION
            int offset;
            Scanner scanner = new Scanner(System.in);

            Socket serverSocket = new Socket("localhost", Protocol.PORT);
            LOG.info("connected");
            InputStream fromServer = serverSocket.getInputStream();
            OutputStream toServer = serverSocket.getOutputStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(fromServer)
            );
            PrintWriter writer = new PrintWriter(toServer);
            ////// DEBUT PROTOCOL
            // 2 : client attends message server
            System.out.println(reader.readLine());
            // 3 : client donne la constante
            String constant = scanner.nextLine();
            offset = Integer.parseInt(constant);
            LOG.info("Constante : "+offset);
            writer.write(constant+"\n");
            writer.flush();
            LOG.info("Constante envoyée");
            // 5 : serveur renvoie confirmation
            System.out.println(reader.readLine());
            // 7,10
            String pulledString;
            String sentString = "";
            while (!sentString.equalsIgnoreCase("bye")) {
                sentString = scanner.nextLine();
                writer.write(Protocol.crypt(sentString, offset)+"\n");
                writer.flush();

                pulledString = reader.readLine();
                LOG.info("Serveur a lu : "+pulledString+" > "+ Protocol.decrypt(pulledString, offset));
            }

            fromServer.close();
            toServer.close();
            serverSocket.close();

        } catch (IOException e) {
            LOG.info("Server not found");
        }
    }

}
