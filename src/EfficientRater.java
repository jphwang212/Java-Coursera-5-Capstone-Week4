import java.util.ArrayList;
import java.util.HashMap;

public class EfficientRater implements Rater {
    private String myID;
    private HashMap<String, Rating> myRatings;

    public EfficientRater(String id) {
        myID = id;
        myRatings = new HashMap<String, Rating>();
    }

    public void addRating(String item, double rating) {
        if (!myRatings.containsKey(item)) {
            Rating adding = new Rating(item, rating);
            myRatings.put(item, adding);
        }
    }

    public boolean hasRating(String item) {
        return myRatings.containsKey(item);
    }

    public String getID() {
        return myID;
    }

    public double getRating(String item) {
        if (hasRating(item)) {
            Rating rating = myRatings.get(item);
            return rating.getValue();
        }
        return -1;
    }

    public int numRatings() {
        return myRatings.size();
    }

    public ArrayList<String> getItemsRated() {
        ArrayList<String> itemsRated = new ArrayList<String>();
        itemsRated.addAll(myRatings.keySet());
        return itemsRated;
    }

    public String toString() {
        return myID;
    }
}
