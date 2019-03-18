package de.danoeh.antennapod.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import de.danoeh.antennapod.R;
import de.danoeh.antennapod.activity.MainActivity;
import de.danoeh.antennapod.adapter.FavoritePodcastsAdapter;

import de.danoeh.antennapod.core.asynctask.FeedRemover;
import de.danoeh.antennapod.core.dialog.ConfirmationDialog;
import de.danoeh.antennapod.core.feed.EventDistributor;
import de.danoeh.antennapod.core.feed.Feed;
import de.danoeh.antennapod.core.preferences.PlaybackPreferences;
import de.danoeh.antennapod.core.service.playback.PlaybackService;
import de.danoeh.antennapod.core.storage.DBReader;
import de.danoeh.antennapod.core.storage.DBWriter;
import de.danoeh.antennapod.core.util.FeedItemUtil;
import de.danoeh.antennapod.core.util.IntentUtils;
import de.danoeh.antennapod.dialog.RenameFeedDialog;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Fragment for displaying feed subscriptions
 */
public class FavoritePodcastsFragment extends Fragment{


    public static final String TAG = "FavoritePodcastsFragment";

    private static final int EVENTS = EventDistributor.FEED_LIST_UPDATE
            | EventDistributor.UNREAD_ITEMS_UPDATE;

    private GridView favoritePodcastsGridLayout;
    private DBReader.NavDrawerData navDrawerData;
    private FavoritePodcastsAdapter favoritePodcastsAdapter;
    private View root;
    private int mPosition = -1;

    private Disposable disposable;
    public FavoritePodcastsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        // So, we certainly *don't* have an options menu,
        // but unless we say we do, old options menus sometimes
        // persist.  mfietz thinks this causes the ActionBar to be invalidated
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_favorite_podcast, container, false);
        favoritePodcastsGridLayout = root.findViewById(R.id.favorite_podcasts_grid);
        registerForContextMenu(favoritePodcastsGridLayout);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        favoritePodcastsAdapter = new FavoritePodcastsAdapter((MainActivity)getActivity(), itemAccess);

        favoritePodcastsGridLayout.setAdapter(favoritePodcastsAdapter);

        //loadSubscriptions();

        favoritePodcastsGridLayout.setOnItemClickListener(favoritePodcastsAdapter);

        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.favorite_podcasts_label);
        }

        EventDistributor.getInstance().register(contentUpdate);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(disposable != null) {
            disposable.dispose();
        }
    }

    private void loadSubscriptions() {
        if(disposable != null) {
            disposable.dispose();
        }
        disposable = Observable.fromCallable(DBReader::getNavDrawerData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    navDrawerData = result;
                    favoritePodcastsAdapter.notifyDataSetChanged();
                }, error -> Log.e(TAG, Log.getStackTraceString(error)));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo adapterInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
        int position = adapterInfo.position;

        Object selectedObject = favoritePodcastsAdapter.getItem(position);
        if (selectedObject.equals(FavoritePodcastsAdapter.ADD_ITEM_OBJ)) {
            mPosition = position;
            return;
        }

        Feed feed = (Feed)selectedObject;

        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.nav_feed_context, menu);

        menu.setHeaderTitle(feed.getTitle());

        mPosition = position;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        final int position = mPosition;
        mPosition = -1; // reset
        if(position < 0) {
            return false;
        }

        Object selectedObject = favoritePodcastsAdapter.getItem(position);
        if (selectedObject.equals(FavoritePodcastsAdapter.ADD_ITEM_OBJ)) {
            // this is the add object, do nothing
            return false;
        }

        Feed feed = (Feed)selectedObject;
        switch(item.getItemId()) {
            case R.id.mark_all_seen_item:
                ConfirmationDialog markAllSeenConfirmationDialog = new ConfirmationDialog(getActivity(),
                        R.string.mark_all_seen_label,
                        R.string.mark_all_seen_confirmation_msg) {

                    @Override
                    public void onConfirmButtonPressed(DialogInterface dialog) {
                        dialog.dismiss();

                        Observable.fromCallable(() -> DBWriter.markFeedSeen(feed.getId()))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(result -> loadSubscriptions(),
                                        error -> Log.e(TAG, Log.getStackTraceString(error)));
                    }
                };
                markAllSeenConfirmationDialog.createNewDialog().show();
                return true;
            case R.id.mark_all_read_item:
                ConfirmationDialog markAllReadConfirmationDialog = new ConfirmationDialog(getActivity(),
                        R.string.mark_all_read_label,
                        R.string.mark_all_read_confirmation_msg) {

                    @Override
                    public void onConfirmButtonPressed(DialogInterface dialog) {
                        dialog.dismiss();
                        Observable.fromCallable(() -> DBWriter.markFeedRead(feed.getId()))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(result -> loadSubscriptions(),
                                        error -> Log.e(TAG, Log.getStackTraceString(error)));
                    }
                };
                markAllReadConfirmationDialog.createNewDialog().show();
                return true;
            case R.id.rename_item:
                new RenameFeedDialog(getActivity(), feed).show();
                return true;
            case R.id.add_to_favorites_podcasts:
                DBWriter.addFavoritePodcastItem(feed);
                return true;
            case R.id.remove_item:
                final FeedRemover remover = new FeedRemover(getContext(), feed) {
                    @Override
                    protected void onPostExecute(Void result) {
                        super.onPostExecute(result);
                        //loadSubscriptions();
                    }
                };
                ConfirmationDialog conDialog = new ConfirmationDialog(getContext(),
                        R.string.remove_feed_label,
                        getString(R.string.feed_delete_confirmation_msg, feed.getTitle())) {
                    @Override
                    public void onConfirmButtonPressed(
                            DialogInterface dialog) {
                        dialog.dismiss();
                        long mediaId = PlaybackPreferences.getCurrentlyPlayingFeedMediaId();
                        if (mediaId > 0 &&
                                FeedItemUtil.indexOfItemWithMediaId(feed.getItems(), mediaId) >= 0) {
                            Log.d(TAG, "Currently playing episode is about to be deleted, skipping");
                            remover.skipOnCompletion = true;
                            int playerStatus = PlaybackPreferences.getCurrentPlayerStatus();
                            if(playerStatus == PlaybackPreferences.PLAYER_STATUS_PLAYING) {
                                IntentUtils.sendLocalBroadcast(getContext(), PlaybackService.ACTION_PAUSE_PLAY_CURRENT_EPISODE);

                            }
                        }
                        remover.executeAsync();
                    }
                };
                conDialog.createNewDialog().show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //loadSubscriptions();
    }

    private final EventDistributor.EventListener contentUpdate = new EventDistributor.EventListener() {
        @Override
        public void update(EventDistributor eventDistributor, Integer arg) {
            if ((EVENTS & arg) != 0) {
                Log.d(TAG, "Received contentUpdate Intent.");
                //loadSubscriptions();
            }
        }
    };

    private final FavoritePodcastsAdapter.ItemAccess itemAccess = new FavoritePodcastsAdapter.ItemAccess() {
        @Override
        public int getCount() {
            if (navDrawerData != null) {
                return navDrawerData.feeds.size();
            } else {
                return 0;
            }
        }

        @Override
        public Feed getItem(int position) {
            if (navDrawerData != null && 0 <= position && position < navDrawerData.feeds.size()) {
                return navDrawerData.feeds.get(position);
            } else {
                return null;
            }
        }

        @Override
        public int getFeedCounter(long feedId) {
            return navDrawerData != null ? navDrawerData.feedCounters.get(feedId) : 0;
        }
    };
}
