import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class FourthRatings {
    private double dotProduct(Rater me, Rater r) {
        HashMap<String, Double> raterMap = new HashMap<String, Double>();
        ArrayList<String> movieList = MovieDatabase.filterBy(new TrueFilter());
        double answer = 0.0;
        for (String movieId : movieList) {
            if (me.hasRating(movieId) && r.hasRating(movieId)) {
                double meScore = me.getRating(movieId) - 5.0;
                double rScore = r.getRating(movieId) - 5.0;
                double product = meScore * rScore;
                answer += product;
            }
        }
        return answer;
    }
    // get raters that are similar to id
    private ArrayList<Rating> getSimilarities(String id) {
        ArrayList<Rating> list = new ArrayList<Rating>();
        ArrayList<Rater> ratersList = RaterDatabase.getRaters();
        Rater me = RaterDatabase.getRater(id);
        for (Rater r : ratersList) {
            if (!id.equals(r.getID())) {
                double product = dotProduct(me, r);
                if (product > 0.0) {
                    Rating rating = new Rating(r.getID(), product);
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
//        System.out.println(similarRaters.size());
//        System.out.println(similarRaters.get(1041));
        ArrayList<Rating> ret = new ArrayList<Rating>();
        ArrayList<String> moviesList = MovieDatabase.filterBy(new TrueFilter());
        for (String movieId : moviesList) {
            double runningSum = 0.0;
            int numMovieRaters = 0;
            // Movie weighted avg = ((r1*s1) + (rx*sx)) / x
            // for a movie:
            //   add weighted avgs
            for (int i = 0; i < numSimilarRaters; i++) {
                String raterId = similarRaters.get(i).getItem();
                Rater raterProfile = RaterDatabase.getRater(raterId);
                if (raterProfile.hasRating(movieId)) {
                    numMovieRaters++;
                    double similarityScore = similarRaters.get(i).getValue();
                    double raterWeightedScore = raterProfile.getRating(movieId) * similarityScore;
                    runningSum += raterWeightedScore;
                }

            }
            if (numMovieRaters >= minimalRaters) {
                //   divide by # movie raters
                Rating movieWeightedAvg = new Rating(movieId, runningSum / numMovieRaters);
                ret.add(movieWeightedAvg);
            }
        }
        // return ret after sorting
        Collections.sort(ret, Collections.reverseOrder());
        return ret;
    }
    // get average score for movie id
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
