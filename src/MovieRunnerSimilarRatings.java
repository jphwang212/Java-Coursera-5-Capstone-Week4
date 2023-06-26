import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerSimilarRatings {
    public void printAverageRatings() {
        FourthRatings fourth = new FourthRatings();
        RaterDatabase.initialize("ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
//        int ratingsLength = fourth.getRaterSize();
//        System.out.println("There are " + MovieDatabase.size() + " movies.");
//        System.out.println("There are " + ratingsLength + " ratings.");
        // Print movies with enough ratings
        ArrayList<Rating> ratings = fourth.getAverageRatings(35);
        Collections.sort(ratings);
        System.out.println(ratings.size() + " movies.");
        for (Rating rating : ratings) {
            String title = MovieDatabase.getTitle(rating.getItem());
            System.out.println(rating.getValue() + " " + title);
        }
    }
    public void printAverageRatingsByYearAfterAndGenre() {
        FourthRatings third = new FourthRatings();
        RaterDatabase.initialize("ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        AllFilters allFilters = new AllFilters();
        YearAfterFilter yearFilter = new YearAfterFilter(1990);
        GenreFilter genreFilter = new GenreFilter("Drama");
        allFilters.addFilter(yearFilter);
        allFilters.addFilter(genreFilter);
        ArrayList<Rating> filtered = third.getAverageRatingsByFilter(8, allFilters);
        System.out.println("Found " + filtered.size() + " movies.");
        for (Rating rating : filtered) {
            String title = MovieDatabase.getTitle(rating.getItem());
            System.out.println(rating.getValue() + " " + title);
        }
    }

    public void printSimilarRatings() {
        FourthRatings fourth = new FourthRatings();
        RaterDatabase.initialize("ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        ArrayList<Rating> similarRatings = fourth.getSimilarRatings("65", 20, 5);
        for (Rating rating : similarRatings) {
            String movieId = rating.getItem();
            String title = MovieDatabase.getTitle(movieId);
            System.out.println(title + ": " + rating.getValue());
        }
    }

    public static void main(String[] args) {
        MovieRunnerSimilarRatings inst = new MovieRunnerSimilarRatings();
//        inst.printAverageRatings();
        inst.printSimilarRatings();
    }
}
