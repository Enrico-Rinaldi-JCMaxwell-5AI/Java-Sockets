/*
 * socketWorker.java ha il compito di gestire la connessione al socket da parte di un Client.
 * Elabora il testo ricevuto che in questo caso viene semplicemente mandato indietro con l'aggiunta 
 * di una indicazione che e' il testo che viene dal Server.
 */
import java.net.*;
import java.io.*;

/**
 *
 * @author Prof. Matteo Palitto
 */
class SocketWorker implements Runnable {
  private Socket client;
  public String Nickname=null;

    //Constructor: inizializza le variabili
    SocketWorker(Socket client) {
        this.client = client;
        System.out.println("Connesso con: " + client);
    }

    // Questa e' la funzione che viene lanciata quando il nuovo "Thread" viene generato
    public void run(){
        
        BufferedReader in = null;
        PrintWriter out = null;
        try{
          // connessione con il socket per ricevere (in) e mandare(out) il testo
          in = new BufferedReader(new InputStreamReader(client.getInputStream()));
          out = new PrintWriter(client.getOutputStream(), true);
        } catch (IOException e) {
          System.out.println("Errore: in|out fallito");
          System.exit(-1);
        }
        
        String line = "";
        int clientPort = client.getPort(); //il "nome" del mittente (client)
        while(line != null){
          try{
            line = in.readLine();
            if(Nickname==null)
            {
                Nickname=line;
            }else{
                if(line.equals("Nickname")){
                    System.out.println("Client list requested. Sent to: "+Nickname);
                    for(int i=0;i<ServerTestoMultiThreaded.SocketList.size();i++)
                    {
                        out.println("Server-->" + Nickname + ">> " + ServerTestoMultiThreaded.SocketList.get(i).Nickname);
                    }
                }else{
            //Manda lo stesso messaggio appena ricevuto con in aggiunta il "nome" del client
                    out.println("Server-->" + Nickname + ">> " + line);
            //scrivi messaggio ricevuto su terminale
                    System.out.println(Nickname + ">> " + line);
                    }
                }
           } catch (IOException e) {
            System.out.println("lettura da socket fallito");
            System.exit(-1);
           }
        }
        try {
            client.close();
            System.out.println("connessione con client: " + client + " terminata!");
        } catch (IOException e) {
            System.out.println("Errore connessione con client: " + client);
        }
    }
}
