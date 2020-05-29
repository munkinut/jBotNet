package net.munki.jbotnet.client;

import net.munki.jbotnet.interfaces.JBotInterface;

import java.rmi.RemoteException;
import java.util.concurrent.CopyOnWriteArrayList;

public class JBotNetClientRunner {

    static JBotNetClient jbnc;

    public static void main(String[] args) {
        try {
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }

            jbnc = new JBotNetClient(args[0]);
            CopyOnWriteArrayList<JBotInterface> botList = new CopyOnWriteArrayList<>();

            for(int i = 0; i < 3; i++) {

                String botName = new StringBuilder("javamunk_").append(i).toString();
                JBotInterface jBot = new JBot(botName, botName, "", "", "localhost", "#javamunk", "", 0);

                jbnc.exportBot(jBot);

                if (jbnc.register(jBot)) botList.add(jBot);;

            }

            for (JBotInterface jb : botList) {
                jbnc.deregister(jb);
                botList.remove(jb);
            }

        } catch (JBotNetClientException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

}

