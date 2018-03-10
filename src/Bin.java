import java.util.ArrayList;

public class Bin{

    private String name;
    private int curSize=0;
    private int maxSize=0;
    private ArrayList<Object> colObjects = new ArrayList<Object>();

    public Bin(String name, int max, int current){
        setName(name);
        setMaxSize(max);
        setCurrentSize(current);
    }

    public int getMaxSize() {

        return maxSize;
    }

    public void setMaxSize(int size){

        this.maxSize=size;
    }

    public int getCurrentSize() {

        return curSize;
    }

    public void setCurrentSize(int size){

        this.curSize=size;
    }

    public int getRemainingSize(){

        return maxSize-curSize;
    }

    public int getCurrentPct(){

        double pct = 0.0;

        if (getCurrentSize()>0){
            pct = getCurrentSize() / getMaxSize() * 100;
        }

        return (int)Math.round(pct);
    }

    public void setName(String n){

        this.name=n;
    }

    public String getName() {
        return name;
    }

    public void setCollectorObjects(Object o){

        colObjects.add(o);
    }

    public ArrayList<Object> getCollectorObjects() {

        return colObjects;
    }

    @Override
    public String toString() {
        return getName()+" "+getCurrentSize()+"/"+getMaxSize()+" ("+getCurrentPct()+"%) -> "+getCollectorObjects()+"\n";
    }

}
