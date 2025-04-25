package com.example.hotel_booking_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hotel_booking_app.R;
import com.example.hotel_booking_app.models.ManagementTeam;
import com.bumptech.glide.Glide;
import java.util.List;

public class ManagementTeamAdapter extends RecyclerView.Adapter<ManagementTeamAdapter.ViewHolder> {

    private List<ManagementTeam> teamMembers;
    private Context context;

    public ManagementTeamAdapter(List<ManagementTeam> teamMembers, Context context) {
        this.teamMembers = teamMembers;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_management_team, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ManagementTeam member = teamMembers.get(position);
        Glide.with(context).load(member.getImage()).into(holder.ivMember);
        holder.tvName.setText(member.getName());
        holder.tvJobTitle.setText(member.getJobTitle());
    }

    @Override
    public int getItemCount() {
        return teamMembers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMember;
        TextView tvName, tvJobTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ivMember = itemView.findViewById(R.id.iv_member);
            tvName = itemView.findViewById(R.id.tv_name);
            tvJobTitle = itemView.findViewById(R.id.tv_job_title);
        }
    }
}