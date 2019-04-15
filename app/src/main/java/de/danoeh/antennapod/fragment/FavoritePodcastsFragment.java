package de.danoeh.antennapod.fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_favorite_podcasts, container, false);
        subscriptionGridLayout = root.findViewById(R.id.favorite_podcasts_grid);
        registerForContextMenu(subscriptionGridLayout);
        return root;
    }

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
}
