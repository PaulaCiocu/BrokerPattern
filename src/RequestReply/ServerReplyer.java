package RequestReply;

import Commons.Address;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerReplyer {
    private ServerSocket srvS;
    private Socket s;
    private InputStream iStr;
    private OutputStream oStr;
    private String myName;
    private Address myAddr;

    public ServerReplyer(String theName, Address theAddr) {
        myName = theName;
        myAddr=theAddr;
        try {
            srvS = new ServerSocket(myAddr.port(), 1001);
            System.out.println("Replyer: Server socket:"+srvS);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error opening server socket");
        }
    }


    public void receive_transform_and_send_feedback(ByteStreamTransformer t)
    {
        int val;
        byte buffer[] = null;
        try
        {
            s = srvS.accept();
            System.out.println("Replyer accept: Socket" + s);
            iStr = s.getInputStream();
            val = iStr.read();
            buffer = new byte[val];
            iStr.read(buffer);

            byte[] data = t.transform(buffer);

            oStr = s.getOutputStream();
            oStr.write(data);
            oStr.flush();
            oStr.close();
            iStr.close();
            s.close();

        }
        catch (IOException e) {
            System.out.println("IOException in receive_transform_and_feedback"); }

    }

    protected void finalize() throws Throwable {
        super.finalize();
        srvS.close();
    }
}
