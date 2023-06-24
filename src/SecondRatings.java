import java.util.ArrayList;

/**
 * Second step of Capstone.
 *
 * @jphwang212
 * @20230621
 */

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;

    public SecondRatings() {
        // default constructor
        this("ratedmovies_short.csv", "ratings_short.csv");
    }
    public SecondRatings(String moviefile, String ratingsfile) {
        FirstRatings first = new FirstRatings();
        myMovies = first.loadMovies(moviefile);
        myRaters = first.loadRaters(ratingsfile);
    }

    public int getMovieSize() {
        return myMovies.size();
    }
    public int getRaterSize() {
        return myRaters.size();
    }
    public String getTitle(String id) {
        for (Movie movie : myMovies) {
            if (id.equals(movie.getID())) {
                return movie.getTitle();
            }
        }
        return "Movie with id " + id + " not found.";
    }
    public String getID(String title) {
        for (Movie movie : myMovies) {
            if (title.equals(movie.getTitle())) {
                return movie.getID();
            }
        }
        return "NO SUCH TITLE.";
    }

    private double getAverageByID(String id, int minimalRaters) {
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
        ArrayList<Rating> averages = new ArrayList<Rating>();
        for (Movie movie : myMovies) {
            String id = movie.getID();
            double avgRating = getAverageByID(id, minimalRaters);
            if (avgRating > 0.0) {
                Rating rating = new Rating(id, avgRating);
                averages.add(rating);
            }
        }
        return averages;
    }

}
