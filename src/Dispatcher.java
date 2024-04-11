
import RequestReply.*;
import MessageMarshaller.*;
import Registry.*;
import Commons.Address;

import java.util.HashMap;
import java.util.Map;


class DispatcherTransformer implements ByteStreamTransformer
{
    private MessageDispatcher originalServer;

    public DispatcherTransformer(MessageDispatcher s)
    {
        originalServer = s;
    }

    public byte[] transform(byte[] in)
    {
        Message msg;
        Marshaller m = new Marshaller();
        msg = m.unmarshal(in);

        Message answer = originalServer.get_answer(msg);

        byte[] bytes = m.marshal(answer);
        return bytes;

    }
}


class MessageDispatcher
{

    static HashMap<String, Address> serverRegistry = new HashMap<>();

    public Message get_answer(Message msg)
    {
        Message answer =null;
        String[] words = msg.data.split("\\s+");

        if(words[0].equals("REQ")){

            System.out.println(words[0] + " " + words[1] + " " + words[2] + words[3]);
            int port = Integer.parseInt(words[2]);
            String dest = words[3];
            String name =words[1];

            System.out.println("you are a server");
            Entry registerAddress =new Entry(dest,port);
            Registry.instance().put(name, registerAddress);

            serverRegistry.put(name,registerAddress);
            answer = new Message("Dispatcher", "I recorded " + serverRegistry.get(name).port() + " " + serverRegistry.get(name).dest());
        }
        else if(words[0].equals("GETADDR")){
            System.out.println(words[0] + " " + words[1]);
            System.out.println("you are a client");
            String nameServer = words[1];

            answer =new Message("Dispatcher",  nameServer + " " + serverRegistry.get(nameServer).port() + " " + serverRegistry.get(nameServer).dest());
        }

        return answer;
    }
}

public class Dispatcher{
    public static void main(String args[])
    {


        new Configuration();

        ByteStreamTransformer transformer = new DispatcherTransformer(new MessageDispatcher());

        Address myAddr = Registry.instance().get("Dispatcher");

        Replyer r = new Replyer("Dispatcher", myAddr);

        while (true) {
        	r.receive_transform_and_send_feedback(transformer);
        }


    }

}