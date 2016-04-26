package fwq.hdsx.com.demolist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;
import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;
import fwq.hdsx.com.demolist.R;

public class UserAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<String> users;

    @Inject
    public UserAdapter(Context ctx, List<String> users) {
        this.inflater = LayoutInflater.from(ctx);
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_user, parent, false);

            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(users.get(position));

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_user)
        TextView textView;
        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}
