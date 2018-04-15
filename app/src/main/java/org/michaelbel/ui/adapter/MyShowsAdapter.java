package org.michaelbel.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.michaelbel.old.ui_old.adapter.Holder;
import org.michaelbel.realm.RealmDb;
import org.michaelbel.rest.model.Show;
import org.michaelbel.ui.view.MyShowView;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 25 MAR 2018
 * Time: 16:53 MSK
 *
 * @author Michael Bel
 */

public class MyShowsAdapter extends RecyclerView.Adapter {

    private List<Show> shows;

    public MyShowsAdapter() {
        shows = new ArrayList<>();
    }

    public List<Show> getShows() {
        return shows;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(new MyShowView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Show show = shows.get(position);

        MyShowView view = (MyShowView) holder.itemView;
        view.setName(show.name);
        view.setPoster(show.posterPath);
        view.setDates(show.firstAirDate, show.lastAirDate);
        view.setStatus(show.inProduction);
        view.setDivider(position != shows.size() - 1);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        int watchedSeasons = RealmDb.getWatchedSeasonsInShow(show.showId);

        if (watchedSeasons == -1) {
            view.setProgressWatchedText(MyShowView.TYPE_SHOW, -1);
        } else if (watchedSeasons > 0) {
            view.setProgressWatchedText(MyShowView.TYPE_SEASONS, watchedSeasons);
        } else {
            int watchedEpisodes = RealmDb.getWatchedEpisodesInShow(show.showId);
            view.setProgressWatchedText(MyShowView.TYPE_EPISODES, watchedEpisodes);
        }
    }

    @Override
    public int getItemCount() {
        return shows != null ? shows.size() : 0;
    }

    public void addShows(List<Show> list) {
        shows.addAll(list);
        notifyItemInserted(shows.size() - 1);
    }
}