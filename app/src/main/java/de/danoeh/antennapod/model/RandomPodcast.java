package de.danoeh.antennapod.model;

public class RandomPodcast {
    private String podcastTitle;
    private String podcastDescription;
    private String podcastPublisher;

    public String getPodcastTitle() {
        return podcastTitle;
    }

    public void setPodcastTitle(String podcastTitle) {
        this.podcastTitle = podcastTitle;
    }

    public String getPodcastDescription() {
        return podcastDescription;
    }

    public void setPodcastDescription(String podcastDescription) {
        this.podcastDescription = podcastDescription;
    }

    public String getPodcastPublisher() {
        return podcastPublisher;
    }

    public void setPodcastPublisher(String podcastPublisher) {
        this.podcastPublisher = podcastPublisher;
    }
}
