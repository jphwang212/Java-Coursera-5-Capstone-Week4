public class DirectorsFilter implements Filter {
    private String[] directors;
    public DirectorsFilter(String filterDirectors) {
        directors = filterDirectors.split(",");
    }

    @Override
    public boolean satisfies(String id) {
        String movieDirectors = MovieDatabase.getDirector(id);
        for (String director : directors) {
            if (movieDirectors.contains(director)) {
                return true;
            }
        }
        return false;
    }
}
