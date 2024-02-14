import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;

public class SparseMatrix<anyType> implements Iterable<anyType> {

    HashMap<Point, anyType> map = new HashMap<>();


    public anyType set(int x, int y, anyType a) {
        return map.put(new Point(x, y), a);
    }

    public anyType get(int x, int y) {
        return map.get(new Point(x, y));
    }

    public anyType remove(int x, int y) {
        return map.remove(new Point(x, y));
    }


    @Override
    public Iterator<anyType> iterator() {
        return map.values().iterator();
    }
}
