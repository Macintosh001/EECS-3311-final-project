package project;

public class MyClass {
    public int num;

    public MyClass(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "MyClass with num = " + num;
    }
}
