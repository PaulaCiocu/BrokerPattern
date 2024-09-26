public class WeatherDataCenter implements Weather{
    @Override
    public String getWeather() {
        return "Partly cloudy";
    }

    @Override
    public String getTemperature() {
        return "29°";
    }
}
