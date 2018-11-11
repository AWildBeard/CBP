public class Tester {
    public static void main(String[] args) {
        User client = new User();

        System.out.println(client);
        System.out.println("Resetting key to defgh");
        client.setKey("defgh");
        System.out.println(client);
    }
}