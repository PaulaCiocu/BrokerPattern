public class MathClient {
    public static void main(String args[])
    {
        Math proxyClient = (Math) BrokerLibrary.lookUp("MathServer");
        System.out.println(proxyClient.getSum());
        System.out.println(proxyClient.getMultiplication());
    }
}
