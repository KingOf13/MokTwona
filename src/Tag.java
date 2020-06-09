import java.util.ArrayList;

public class Tag implements Comparable{
    private String name;
    private final ArrayList<Serie> series;

    public Tag(String name) {
        this.name = name;
        series = new ArrayList<Serie>();
    }

    public boolean add(Serie serie) {
        if (series.contains(serie)) return false;
        return series.add(serie);
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

    public String getName() {
        return name;
    }

    public ArrayList<Serie> getSeries() {
        return series;
    }

    public void setName(String name) {
        this.name = name;
    }

}
