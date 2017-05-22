package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import de.fachstudie.fachstudie_template.R;
import model.NavDrawerItem;

/**
 * Class for the Navigation-Drawer-Adapter.
 */

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
    // List of NavDrawerItems.
    List<NavDrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    /**
     * Constructor.
     * @param context
     * @param data
     */
    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    /**
     * Remove element fromd data with specific position.
     * @param position
     */
    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get the view.
        View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // Current NavDrawerItem.
        NavDrawerItem current = data.get(position);
        // Set Title-text.
        holder.title.setText(current.getTitle());
    }

    /**
     * Returns data size.
     * @return
     */
    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        // Title
        TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            // Title by id.
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
