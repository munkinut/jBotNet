package net.munki.jbotnet.interfaces;
 
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface JBotNetServerInterface extends Remote {
    boolean register(JBotInterface jBotInterface) throws RemoteException;
    boolean deregister(JBotInterface jBotInterface) throws RemoteException;
}
