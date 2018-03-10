import java.io.*;
import java.util.*;

public class BestFit {

    public static void main(String[] args) {

        List<Bin> bins = new ArrayList<Bin>();
        List<Object> objects = new ArrayList<Object>();
        String name;
        int maxObjects;
        int curObjects;

        Scanner scanner = null;
        try {
            //Import Bins from file
            scanner = new Scanner(new File("Bins.csv"));
            scanner.nextLine(); //Skip the headers
            while(scanner.hasNextLine()){
                String[] s = scanner.nextLine().split(",");
                //Cols: Name,Max Objects,Current Objects
                
                if(s.length==3){
                    System.out.println(s[0]+"::"+s[1]+"::"+s[2]);
                    name = s[0];
                    maxObjects = Integer.parseInt(s[1]);
                    curObjects = Integer.parseInt(s[2]);
                    bins.add(new Bin(name, maxObjects, curObjects));
                }
            }

            //Import Objects from file
            scanner = new Scanner(new File("Objects.csv"));
            scanner.nextLine(); //Skip the headers
            while(scanner.hasNextLine()){
                String[] s = scanner.nextLine().split(",");
                //Cols: Name,Objects

                if(s.length==2){
                    System.out.println(s[0]+"::"+s[1]);
                    name = s[0];
                    maxObjects = (s[1].equals("")?0:Integer.parseInt(s[1]));
                    objects.add(new Object(name, maxObjects));
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            scanner.close();
        }

        //System.out.println(bins);
        //System.out.println(objects);

        //findBestFit(bins, objects);
        findBestSplit(bins, objects);
    }

    public static void findBestSplit(List<Bin> bins, List<Object> objects){

        //Sort the devices descending by metric size
        Collections.sort(objects, new Comparator<Object>() {
            @Override public int compare(Object p1, Object p2) {
                return p2.getMetrics() - p1.getMetrics(); // Descending
            }
        });
        for(Object o : objects){
            //Sort the Bins Ascending by used % then descending remaining metrics
            Collections.sort(bins, new Comparator<Bin>() {
                @Override public int compare(Bin p1, Bin p2) {
                    if(p1.getCurrentPct() - p2.getCurrentPct()==0){
                        return p2.getRemainingSize() - p1.getRemainingSize();
                    }
                    else{
                        return p1.getCurrentPct() - p2.getCurrentPct(); // Ascending
                    }
                }
            });

            for(Bin b : bins){
                if(o.getMetrics() <= b.getRemainingSize()){
                    b.setCollectorObjects(o);
                    b.setCurrentSize(b.getCurrentSize()+o.getMetrics());
                    System.out.println(b.getName()+"\t"+o.getName()+"\t("+b.getName()+" now has "+b.getRemainingSize()+" space remaining)");
                    break;
                }
            }
        }

        //Sort the Bins descending by current space
        Collections.sort(bins, new Comparator<Bin>() {
            @Override public int compare(Bin p1, Bin p2) {
                return p2.getCurrentSize() - p1.getCurrentSize(); // Descending
            }
        });
        System.out.println("\nBest Split Results:");
        for(Bin b : bins){
            //getName()+" "+getCurrentSize()+"/"+getMaxSize()+" ("+getCurrentPct()+"%) -> "+getCollectorDevices()+"\n";
            System.out.println(b.getName()+" ("+b.getCurrentSize()+" metrics): ");

            for (Object o : b.getCollectorObjects()){
                System.out.println(o);
            }
            System.out.print("\n");
        }
    }

    public static void findBestFit(List<Bin> bins, List<Object> objects){

        //Sort the devices descending by metric size
        Collections.sort(objects, new Comparator<Object>() {
            @Override public int compare(Object p1, Object p2) {
                return p2.getMetrics() - p1.getMetrics(); // Descending
            }
        });

        //Sort the Bins descending by max metric size
        Collections.sort(bins, new Comparator<Bin>() {
            @Override public int compare(Bin p1, Bin p2) {
                return p2.getMaxSize() - p1.getMaxSize(); // Descending
            }
        });

        for(Object d : objects){

            //Sort the Bins descending by remaining space
            Collections.sort(bins, new Comparator<Bin>() {
                @Override public int compare(Bin p1, Bin p2) {
                    return p2.getRemainingSize() - p1.getRemainingSize(); // Descending
                }
            });

            for(Bin c : bins){
                if(d.getMetrics() <= c.getRemainingSize()){
                    c.setCollectorObjects(d);
                    c.setCurrentSize(c.getCurrentSize()+d.getMetrics());
                    System.out.println(c.getName()+"\t"+d.getName()+"\t("+c.getName()+" now has "+c.getRemainingSize()+" space remaining)");
                    break;
                }
            }
        }


        //Sort the Bins descending by current space
        Collections.sort(bins, new Comparator<Bin>() {
            @Override public int compare(Bin p1, Bin p2) {
                return p2.getCurrentSize() - p1.getCurrentSize(); // Descending
            }
        });
        System.out.println("\nBest Fit Results:");
        System.out.println(bins.toString());


    }
}
