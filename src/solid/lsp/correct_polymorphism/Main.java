package solid.lsp.correct_polymorphism;

class Rectangle {
    protected int width;
    protected int height;

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getArea() {
        return width * height;
    }
}

class Square extends Rectangle {
    @Override
    public void setWidth(int width) {
        super.setWidth(width); // this.setWidth(width);
    }

    @Override
    public void setHeight(int height) {
        super.setHeight(height); // this.setHeight(height);
    }
}

public class Main {
    public static void testRectangleArea(Rectangle rect) {
        rect.setWidth(5);
        rect.setHeight(4);
        if (rect.getArea() != 20) {
            System.out.println("Incorrect area calculation!");
        }
    }

    public static void main(String[] args) {
        Rectangle rect = new Square();
        testRectangleArea(rect);  // This will not output an error, but it's conceptually wrong as expectations for Rectangle are violated.
    }
}
