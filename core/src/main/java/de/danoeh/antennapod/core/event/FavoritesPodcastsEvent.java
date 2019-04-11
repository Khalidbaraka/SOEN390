package de.danoeh.antennapod.core.event;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import de.danoeh.antennapod.core.feed.Feed;

public class FavoritesPodcastsEvent {

    public enum Action {
        ADDED, REMOVED
    }

    private final Action action;
    private final Feed item;

    private FavoritesPodcastsEvent(Action action, Feed item) {
        this.action = action;
        this.item = item;
    }

    public static FavoritesPodcastsEvent added(Feed item) {
        return new FavoritesPodcastsEvent(Action.ADDED, item);
    }

    public static FavoritesPodcastsEvent removed(Feed item) {
        return new FavoritesPodcastsEvent(Action.REMOVED, item);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("action", action)
                .append("item", item)
                .toString();
    }

}
