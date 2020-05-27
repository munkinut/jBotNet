package net.munki.jbotnet.interfaces;
 
public interface JBotInterface {

    boolean execute();
    String getName();
    String getNickname();
    String getDescription();
    String getOwnerEmail();
    String getServer();
    String getChannel();
    String getIp();
    int getPort();
    boolean setNickname(String nickname);
    boolean setDescription(String description);
    boolean setOwnerEmail(String ownerEmail);
    boolean setServer(String server);
    boolean setChannel(String channel);

}
