import java.util.ArrayList;

public class Tag implements Comparable{
    private final String name;
    private final ArrayList<Serie> series= new ArrayList<Serie>();
    private static Database db = null;
    private int size = 0;

    public Tag(String name) {
        this.name = name;
    }

    public Tag(String name, Serie serie) {
        this.name = name;
        series.add(serie);
    }

    public boolean add(Serie serie) {
        if (series.contains(serie)) return false;
        size ++;
        return series.add(serie);
    }

    public static boolean setDb(Database newdb) {
        if (db != null) return false;
        db = newdb;
        return true;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Tag) return name.compareTo(((Tag) o).name);
        throw new ClassCastException("Comparing a tag with something else : BAD IDEA");
    }

    @Override
    public boolean equals(Object obj) {
        int cmp = -1;
        try {
            cmp = compareTo(obj);
        } catch (ClassCastException e) {
            return false;
        }
        return cmp == 0;
    }

    public boolean containsSerie(Serie serie) {
        return series.contains(serie);
    }

    public String getName() {
        return name;
    }

    public Serie[] getSeries() {
        return series.toArray(new Serie[size]);
    }


    private void updateInDB() {

    }

}
