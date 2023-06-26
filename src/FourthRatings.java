import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class FourthRatings {
    private double dotProduct(Rater me, Rater r) {
        HashMap<String, Double> raterMap = new HashMap<String, Double>();
        ArrayList<String> meRated = me.getItemsRated();
        ArrayList<String> rRated = r.getItemsRated();
        double answer = 0.0;
        for (String rating : meRated) {
            double rate = me.getRating(rating);
            raterMap.put(rating, rate - 5.0);
        }
        for (String ratingId : rRated) {
            if (raterMap.containsKey(ratingId)) {
                double rating = r.getRating(ratingId);
                double theirRating = raterMap.get(ratingId) - 5.0;
                raterMap.put(ratingId, rating * theirRating);
            }
        }
        for (String ratingId : raterMap.keySet()) {
            answer += raterMap.get(ratingId);
        }
        return answer;
    }
    // get raters that are similar to id
    private ArrayList<Rating> getSimilarities(String id) {
        ArrayList<Rating> list = new ArrayList<Rating>();
        Rater me = RaterDatabase.getRater(id);
        for (Rater r : RaterDatabase.getRaters()) {
            if (!id.equals(r.getID())) {
                double product = dotProduct(me, r);
                Rating rating = new Rating(r.getID(), product);
                if (rating.getValue() > 0.0) {
                    list.add(rating);
                }
            }
        }
        Collections.sort(list, Collections.reverseOrder());
        return list;
    }
    // get ratings of movies and their weighted avg ratings
    // using only top numSimilarRaters
    public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters) {
        // Get similarity ratings
        ArrayList<Rating> similarRaters = getSimilarities(id);
        if (similarRaters.size() < numSimilarRaters) {
            return new ArrayList<Rating>();
        }
        List<Rating> topSimilarRaters = similarRaters.subList(0, numSimilarRaters);
        ArrayList<Rating> ret = new ArrayList<Rating>();
        ArrayList<String> moviesList = MovieDatabase.filterBy(new TrueFilter());
        for (String movieId : moviesList) {
            double runningSum = 0.0;
            for (Rating rater : topSimilarRaters) {
                String raterId = rater.getItem();
                Rater raterAll = RaterDatabase.getRater(raterId);
                if (raterAll.hasRating(movieId)) {
                    runningSum += rater.getValue();
                } else break;
            }
            Rating retRating = new Rating(movieId, runningSum);
            ret.add(retRating);
        }
        // return ret after sorting
        Collections.sort(ret, Collections.reverseOrder());
        return ret;
    }
    private double getAverageByID(String id, int minimalRaters) {
        ArrayList<Rater> myRaters = RaterDatabase.getRaters();
        ArrayList<Double> movieRatings = new ArrayList<Double>();
        double sum = 0.0;
        for (Rater rater : myRaters) {
            if (rater.hasRating(id)) {
                double rating = rater.getRating(id);
                movieRatings.add(rating);
                sum += rating;
            }
        }
        if (movieRatings.size() < minimalRaters) {
            return 0.0;
        }
        return sum / movieRatings.size();
    }

    public ArrayList<Rating> getAverageRatings(int minimalRaters) {
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        ArrayList<Rating> averages = new ArrayList<Rating>();

        for (String id : movies) {
            double avgRating = getAverageByID(id, minimalRaters);
            if (avgRating > 0.0) {
                Rating rating = new Rating(id, avgRating);
                averages.add(rating);
            }
        }
        return averages;
    }

    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {
        ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);
        ArrayList<Rating> averages = new ArrayList<Rating>();
        for (String s : movies) {
            double avgRating = getAverageByID(s, minimalRaters);
            if (avgRating > 0.0) {
                Rating rating = new Rating(s, avgRating);
                averages.add(rating);

            }
        }
        return averages;
    }
}
