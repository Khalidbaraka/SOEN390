package de.danoeh.antennapod.fragment;

import android.util.Log;
import android.view.ContextMenu;

import de.danoeh.antennapod.R;
import de.danoeh.antennapod.core.storage.DBReader;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Fragment for displaying feed subscriptions
 */
public class FavoritePodcastsFragment extends SubscriptionFragment{

    public static final String TAG = "FavoritePodcastFragment";

    @Override
    protected void loadSubscriptions() {
        if(disposable != null) {
            disposable.dispose();
        }
        disposable = Observable.fromCallable(DBReader::getFavoritePodcastsData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    navDrawerData = result;
                    subscriptionAdapter.notifyDataSetChanged();
                }, error -> Log.e(TAG, Log.getStackTraceString(error)));
    }

    @Override
    protected void changeItemVisibility(ContextMenu menu){
        menu.findItem(R.id.remove_from_favorite_podcasts).setVisible(true);
        menu.findItem(R.id.add_to_favorites_podcasts).setVisible(false);
    }

}