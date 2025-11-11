public class ArithmeticOperations {
    public native int add(int a, int b);

    public native int substract(int a, int b);

    public native int multiply(int a, int b);

    public native int divide(int a, int b);

    static {
        System.loadLibrary("ArithmeticOperations");
    }

    public static void main(String[] args) {
        ArithmeticOperations ops = new ArithmeticOperations();
        int a = 10, b = 5;
        System.out.println("=== ArithmeticOperations ===");
        System.out.println("Addition (" + a + " + " + b + "): " + ops.add(a, b));
        System.out.println("Substraction (" + a + " + " + b + "): " + ops.substract(a, b));
        System.out.println("Multiplication (" + a + " + " + b + "): " + ops.multiply(a, b));
        System.out.println("Division (" + a + " + " + b + "): " + ops.divide(a, b));

    }
}