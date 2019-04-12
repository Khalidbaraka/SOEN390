package de.danoeh.antennapod.core.event;


import android.support.annotation.NonNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Arrays;
import java.util.List;

import de.danoeh.antennapod.core.feed.Feed;

public class FeedEventBus {

    public enum Action {
        UPDATE, DELETE_MEDIA
    }

    @NonNull
    private final Action action;
    @NonNull public final List<Feed> items;

    private FeedEventBus(Action action, List<Feed> items) {
        this.action = action;
        this.items = items;
    }

    public static FeedEventBus deletedMedia(List<Feed> items) {
        return new FeedEventBus(Action.DELETE_MEDIA, items);
    }

    public static FeedEventBus updated(List<Feed> items) {
        return new FeedEventBus(Action.UPDATE, items);
    }

    public static FeedEventBus updated(Feed... items) {
        return new FeedEventBus(Action.UPDATE, Arrays.asList(items));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("action", action)
                .append("items", items)
                .toString();
    }

}
