public class Test01 {

    public static void main(String[] args) {

        try {
            System.out.println("try");
            throw new RuntimeException();
        } catch (Exception e) {
            System.out.println("catch");
            throw new RuntimeException(e);
        } finally {
            System.out.println("finally");
        }


    }

}
