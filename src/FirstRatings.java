import edu.duke.*;
import org.apache.commons.csv.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
public class FirstRatings {
    public ArrayList<Rater> loadRaters(String filename) {
        HashMap<String, Rater> raterMap = new HashMap<String, Rater>();
        ArrayList<Rater> raterData = new ArrayList<Rater>();
        DirectoryResource dr = new DirectoryResource();
        for (File file : dr.selectedFiles()) {
            if (filename.equals(file.getName())) {
                FileResource fr = new FileResource(file);
                CSVParser parser = fr.getCSVParser();
                for (CSVRecord record : parser) {
                    String raterId = record.get("rater_id");
                    String movieId = record.get("movie_id");
                    double rating = Integer.parseInt(record.get("rating"));
                    Rater rater = new EfficientRater(raterId);
                    if (!raterMap.containsKey(raterId)) {
                        rater.addRating(movieId, rating);
                        raterMap.put(raterId, rater);
                    } else {
                        rater = raterMap.get(raterId);
                        if (!rater.hasRating(movieId)) {
                            rater.addRating(movieId, rating);
                        }
                    }
                }
            }
        }
        for (String raterId : raterMap.keySet()) {
            raterData.add(raterMap.get(raterId));
        }
        return raterData;
    }
    public ArrayList<Movie> loadMovies(String filename) {
        ArrayList<Movie> movieData = new ArrayList<Movie>();
        DirectoryResource dr = new DirectoryResource();
        for (File fr : dr.selectedFiles()) {
            if (filename.equals(fr.getName())) {
                FileResource f = new FileResource(fr);
                CSVParser parser = f.getCSVParser();
                for (CSVRecord record : parser) {
                    String id = record.get("id");
                    String title = record.get("title");
                    String year = record.get("year");
                    String genres = record.get("genre");
                    String director = record.get("director");
                    String country = record.get("country");
                    int minutes = Integer.parseInt(record.get("minutes"));
                    String poster = record.get("poster");
//                    if (genres.contains("Comedy")) {
//                        genreCount++;
//                    }
//                    if (minutes > 150) {
//                        timeCount++;
//                    }
                    Movie currMovie = new Movie(id, title, year, genres, director, country, poster, minutes);
                    movieData.add(currMovie);
                }
            }
        }
        return movieData;
    }

    public void testLoadMovies() {
        ArrayList<Movie> movieData = loadMovies("ratedmovies_short.csv");
        System.out.println("There are " + movieData.size() + " movies.");
//        for (Movie movie : movieData) {
//            System.out.print(movie + " ");
//        }
        HashMap<String, ArrayList<String>> directorMoviesMap = new HashMap<String, ArrayList<String>>();
        int genreCount = 0;
        int timeCount = 0;
        for (Movie movie : movieData) {
            String movieId = movie.getID();
            if (movie.getGenres().contains("Comedy")) {
                genreCount++;
            }
            if (movie.getMinutes() > 150) {
                timeCount++;
            }
            String[] directors = movie.getDirector().split(",");
            for (int i = 0; i < directors.length; i++) {
                ArrayList<String> directorMovies = new ArrayList<String>();
                if (!directorMoviesMap.containsKey(directors[i])) {
                    directorMovies.add(movieId);
                    directorMoviesMap.put(directors[i], directorMovies);
                } else {
                    directorMovies = directorMoviesMap.get(directors[i]);
                    if (!directorMovies.contains(movieId)) {
                        directorMovies.add(movieId);
                        directorMoviesMap.replace(movieId, directorMovies);
                    }
                }
            }
        }
        int largestSize = 0;
        ArrayList<String> directorLargest = new ArrayList<String>();
        for (String director : directorMoviesMap.keySet()) {
            int size = directorMoviesMap.get(director).size();
            if (size > largestSize) {
                largestSize = size;
                directorLargest.clear();
                directorLargest.add(director);
            } else if (size == largestSize && !directorLargest.contains(director)) {
                directorLargest.add(director);
            }
//            System.out.println(director + ": " + directorMoviesMap.get(director));
        }
        System.out.println("There are " + genreCount + " Comedy movies.");
        System.out.println("There are " + timeCount + " movies longer than 150.");
        System.out.println(directorLargest.toString() + " directors had movies with size " + largestSize);
    }
    public void testLoadRaters() {
        ArrayList<Rater> raterData = loadRaters("ratings_short.csv");
        System.out.println("There are " + raterData.size() + " raters.");
        String raterId = "193";
        String movieId = "1798709";
        int maxRatings = 0;
        HashMap<String, ArrayList<String>> movieRatingMap = new HashMap<String, ArrayList<String>>();
        ArrayList<String> mostRatings = new ArrayList<String>();
        for (Rater rater : raterData) {
//            System.out.print(rater.getID() + " ");
            String currRaterId = rater.getID();
            ArrayList<String> movies = rater.getItemsRated();
            for (String movie : movies) {
                if (!movieRatingMap.containsKey(movie)) {
                    ArrayList<String> raters = new ArrayList<String>();
                    raters.add(currRaterId);
                    movieRatingMap.put(movie, raters);
                } else {
                    ArrayList<String> raters = movieRatingMap.get(movie);
                    raters.add(currRaterId);
                    movieRatingMap.replace(movie, raters);
                }
            }
            int currentRatings = rater.getItemsRated().size();
            if (currentRatings > maxRatings) {
                maxRatings = currentRatings;
                mostRatings.clear();
                mostRatings.add(rater.getID());
            } else if (currentRatings == maxRatings && !mostRatings.contains(rater)) {
                mostRatings.add(rater.getID());
            }
            if (raterId.equals(rater.getID())) {
                System.out.println(raterId + " has " + rater.getItemsRated().size() + " ratings.");
            }
        }
        System.out.println("Most ratings by " + mostRatings.toString());
//        System.out.println("Max num of ratings = " + raterData.get().);
        System.out.println(movieRatingMap.get(movieId).size() + " raters.");
        System.out.println(movieRatingMap.size() + " unique movies.");
    }

    public static void main(String[] args) {
        FirstRatings inst = new FirstRatings();
        inst.testLoadMovies();
//        inst.testLoadRaters();
    }
}
