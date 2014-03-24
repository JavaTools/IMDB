package common;

import java.awt.image.BufferedImage;
import java.util.Set;

public class Movie
{
    private String title, story;
    private String rating;
    private BufferedImage cover;
    private Set<String> genres;

    public Movie(Extractor extractor)
    {
        this.title = extractor.getTitle();
        this.story = extractor.getStory();
        this.cover = extractor.getCover();
        this.rating = extractor.getRating();
        this.genres = extractor.getGenres();
    }

    public String getTitle()
    {
        return title;
    }

    public String getStory()
    {
        return story;
    }

    public String getRating()
    {
        return rating;
    }

    public BufferedImage getCover()
    {
        return cover;
    }

    public Set<String> getGenres()
    {
        return genres;
    }
}
