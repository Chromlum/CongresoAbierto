package gt.redciudadana.congresoabierto;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

/**
 * Created by Chromz on 3/22/2016.
 */
public class FeedFragment extends ListFragment {




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_feed, container, false);

        final SwipeRefreshLayout swipeL = (SwipeRefreshLayout) rootView
                .findViewById(R.id.twitter_refreshL);
        // Codigo obtenido de la documentacion de Fabric
        final UserTimeline noticias = new UserTimeline.Builder()
                .screenName("RedxGuate")
                .build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter
                .Builder(getActivity())
                .setTimeline(noticias)
                .build();

        setListAdapter(adapter);
        swipeL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeL.setRefreshing(true);
                adapter.refresh(new Callback<TimelineResult<Tweet>>() {
                    @Override
                    public void success(Result<TimelineResult<Tweet>> result) {
                        swipeL.setRefreshing(false);

                    }

                    @Override
                    public void failure(TwitterException e) {
                        Toast.makeText(getActivity(), R.string.errorcarga, Toast.LENGTH_SHORT)
                                .show();
                    }
                });
            }
        });

        return rootView;

    }
}
