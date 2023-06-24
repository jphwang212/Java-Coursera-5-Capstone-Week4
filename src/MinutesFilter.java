public class MinutesFilter implements Filter {
    private int minutesMin;
    private int minutesMax;
    public MinutesFilter(int min, int max) {
        minutesMin = min;
        minutesMax = max;
    }

    @Override
    public boolean satisfies(String id) {
        return MovieDatabase.getMinutes(id) >= minutesMin && MovieDatabase.getMinutes(id) <= minutesMax;
    }
}
