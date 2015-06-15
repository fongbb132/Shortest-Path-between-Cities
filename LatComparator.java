import java.io.Serializable;
import java.util.Comparator;
/**
 * Ka Wing Fong
 * 109794011
 * HW 6
 * CSE 214-R03
 * Recitation TA: Sun Lin
 * Grading TA: Ke Ma
 * @author Ka Wing Fong
 */


/**
 * The Comparator object to compare the Latitudes of 2 cities.
 */
public class LatComparator implements Comparator<City>,Serializable {

    /**
     * Override the compare method in Comparator
     * @param o1 the first object
     * @param o2 the second object
     * @return -1 is o1 is smaller than o2, 0 if they are equal, and 1 if o1 is greater than o2 base on their Latitudes.
     */
    @Override
    public int compare(City o1, City o2) {
        if(o1.getLocation().getLat()<o2.getLocation().getLat()){
            return -1;
        }else if(o1.getLocation().getLat()==o2.getLocation().getLat()){
            return 0;
        }else{
            return 1;
        }
    }
}
