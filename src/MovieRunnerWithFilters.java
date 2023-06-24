import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerWithFilters {
    public void printAverageRatings() {
        ThirdRatings third = new ThirdRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        int ratingsLength = third.getRaterSize();
        System.out.println("There are " + MovieDatabase.size() + " movies.");
        System.out.println("There are " + ratingsLength + " ratings.");
        // Print movies with enough ratings
        ArrayList<Rating> ratings = third.getAverageRatings(35);
        Collections.sort(ratings);
        System.out.println(ratings.size() + " movies.");
        for (Rating rating : ratings) {
            String title = MovieDatabase.getTitle(rating.getItem());
            System.out.println(rating.getValue() + " " + title);
        }
    }
    public void printAverageRatingsByYear() {
        ThirdRatings third = new ThirdRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        YearAfterFilter yearFilter = new YearAfterFilter(2000);
        ArrayList<Rating> filtered = third.getAverageRatingsByFilter(20, yearFilter);
        System.out.println("Found " + filtered.size() + " movies.");
        for (Rating rating : filtered) {
            String title = MovieDatabase.getTitle(rating.getItem());
            System.out.println(rating.getValue() + " " + title);
        }
    }

    public void printAverageRatingsByGenre() {
        ThirdRatings third = new ThirdRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        GenreFilter genreFilter = new GenreFilter("Comedy");
        ArrayList<Rating> filtered = third.getAverageRatingsByFilter(20, genreFilter);
        System.out.println("Found " + filtered.size() + " movies.");
        for (Rating rating : filtered) {
            String title = MovieDatabase.getTitle(rating.getItem());
            System.out.println(rating.getValue() + " " + title);
        }
    }

    public void printAverageRatingsByMinutes() {
        ThirdRatings third = new ThirdRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        MinutesFilter minFilter = new MinutesFilter(105, 135);
        ArrayList<Rating> filtered = third.getAverageRatingsByFilter(5, minFilter);
        System.out.println("Found " + filtered.size() + " movies.");
        for (Rating rating : filtered) {
            String title = MovieDatabase.getTitle(rating.getItem());
            System.out.println(rating.getValue() + " " + title);
        }
    }
    public void printAverageRatingsByDirectors() {
        ThirdRatings third = new ThirdRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        DirectorsFilter directorsFilter = new DirectorsFilter("Clint Eastwood,Joel Coen,Marin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack");
        ArrayList<Rating> filtered = third.getAverageRatingsByFilter(4, directorsFilter);
        System.out.println("Found " + filtered.size() + " movies.");
        for (Rating rating : filtered) {
            String title = MovieDatabase.getTitle(rating.getItem());
            System.out.println(rating.getValue() + " " + title);
        }
    }

    public void printAverageRatingsByYearAfterAndGenre() {
        ThirdRatings third = new ThirdRatings();
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
    public void printAverageRatingsByDirectorsAndMinutes() {
        ThirdRatings third = new ThirdRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        AllFilters allFilters = new AllFilters();
        DirectorsFilter directorsFilter = new DirectorsFilter("Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack");
        MinutesFilter minutesFilter = new MinutesFilter(90, 180);
        allFilters.addFilter(directorsFilter);
        allFilters.addFilter(minutesFilter);
        ArrayList<Rating> filtered = third.getAverageRatingsByFilter(3, allFilters);
        System.out.println("Found " + filtered.size() + " movies.");
        for (Rating rating : filtered) {
            String title = MovieDatabase.getTitle(rating.getItem());
            System.out.println(rating.getValue() + " " + title);
        }
    }
    public static void main(String[] args) {
        MovieRunnerWithFilters inst = new MovieRunnerWithFilters();
//        inst.printAverageRatings();
//        inst.printAverageRatingsByYear();
//        inst.printAverageRatingsByGenre();
//        inst.printAverageRatingsByMinutes();
//        inst.printAverageRatingsByDirectors();
//        inst.printAverageRatingsByYearAfterAndGenre();
        inst.printAverageRatingsByDirectorsAndMinutes();
    }
}
