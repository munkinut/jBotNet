package net.munki.jbotnet.client;

import net.munki.jbotnet.interfaces.JBotInterface;

import java.io.Serializable;
import java.util.Objects;

public class JBot implements JBotInterface, Serializable {

    private static final long serialVersionUID = 225L;

    private String name;
    private String nickname;
    private String description;
    private String ownerEmail;
    private String server;
    private String channel;
    private String ip;
    private int port;

    public JBot(String name,
                String nickname,
                String description,
                String ownerEmail,
                String server,
                String channel,
                String ip,
                int port) {
        super();
        this.name = name;
        this.nickname = nickname;
        this.description = description;
        this.ownerEmail = ownerEmail;
        this.server = server;
        this.channel = channel;
        this.ip = ip;
        this.port = port;
    }

    @Override
    public boolean execute() {
        return false;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getNickname() {
        return this.nickname;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getOwnerEmail() {
        return this.ownerEmail;
    }

    @Override
    public String getServer() {
        return this.server;
    }

    @Override
    public String getChannel() {
        return this.channel;
    }

    @Override
    public String getIp() {
        return this.ip;
    }

    @Override
    public int getPort() {
        return this.port;
    }

    @Override
    public boolean setNickname(String nickname) {
        return false;
    }

    @Override
    public boolean setDescription(String description) {
        return false;
    }

    @Override
    public boolean setOwnerEmail(String ownerEmail) {
        return false;
    }

    @Override
    public boolean setServer(String server) {
        return false;
    }

    @Override
    public boolean setChannel(String channel) {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JBot jBot = (JBot) o;
        return name.equals(jBot.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
