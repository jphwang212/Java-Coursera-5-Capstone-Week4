import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerAverage {
    public void printAverageRatings() {
        SecondRatings second = new SecondRatings();
        int moviesLength = second.getMovieSize();
        int ratingsLength = second.getRaterSize();
        System.out.println("There are " + moviesLength + " movies.");
        System.out.println("There are " + ratingsLength + " ratings.");
        // Print movies with enough ratings
        ArrayList<Rating> ratings = second.getAverageRatings(1);
        Collections.sort(ratings);
        System.out.println(ratings.size() + " movies with >= 1 ratings.");
        for (Rating rating : ratings) {
            String title = second.getTitle(rating.getItem());
            System.out.println(rating.getValue() + " " + title);
        }
    }
    public void getAverageRatingOneMovie() {
        SecondRatings second = new SecondRatings();
        String testMovie = "Vacation";
        String movieId = second.getID(testMovie);
        ArrayList<Rating> ratings = second.getAverageRatings(3);
        ArrayList<Double> average = new ArrayList<Double>();
        double sum = 0.0;
        for (Rating rating : ratings) {
            if (movieId.equals(rating.getItem())) {
                double value = rating.getValue();
                average.add(value);
                sum += value;
            }
        }
        System.out.println("Average = " + (sum / average.size()));
    }

    public static void main(String[] args) {
        MovieRunnerAverage inst = new MovieRunnerAverage();
        inst.printAverageRatings();
//        inst.getAverageRatingOneMovie();
    }
}
