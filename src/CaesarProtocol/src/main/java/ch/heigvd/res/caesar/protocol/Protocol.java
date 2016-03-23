package ch.heigvd.res.caesar.protocol;

import java.text.Normalizer;

/**
 *
 * @author Olivier Liechti
 */
public class Protocol {

    public static final int PORT = 22356;

    // 1 : serveur demande la constante de offset
    // 2 : client attends message server
    // 3 : client donne la constante
    // 4 : serveur inscrit la constant
    // 5 : serveur renvoie confirmation

    // 6 : Serveur : attend message
    // 7 : Client  : envoie message (crypté)
    // 8 : Serveur : reçoit message (décrypte)
    // 9 : Serveur : renvoit message et se remet en 6:
    // 10: Client reçoit message et se remet en 7 si OK:
    // 10.1 : Si message différent de l'original : message ERREUR

    public static String crypt(String msg, int offset) {
        String s = "";
        for(int i = 0; i < msg.length(); i++) {
            char c = msg.charAt(i);

            s += (char)(c - offset);
        }
        return s;
    }

    public static String decrypt(String msg, int offset) {
        String s = "";
        for(int i = 0; i < msg.length(); i++) {
            char c = msg.charAt(i);

            s += (char)(c + offset);
        }
        return s;
    }

}
