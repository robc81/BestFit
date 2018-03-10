public class Object {

    private int metrics;
    private String name;

    public Object(String n, int m){
        setName(n);
        setMetrics(m);
    }

    public int getMetrics() {
        return metrics;
    }

    public void setMetrics(int x){
        this.metrics=x;
    }

    public void setName(String n){
        this.name=n;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName()+"("+getMetrics()+" metrics)";
    }
}
