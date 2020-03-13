package com.example.bolasepak;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.lang.String;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    List<Match> Mdata;
//    List<String> TeamLogo;
  //  int images1[],images2[];
    Context context;

    public MyAdapter(Context con, List<Match> Mdata){
        context = con;
        this.Mdata = Mdata;
    //    this.TeamLogo = TeamLogo;
      //  images1 = img1;
       // images2 = img2;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.home_match,parent,false);
        //return new MyViewHolder(view);
        final MyViewHolder viewHolder = new MyViewHolder(view) ;
        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date schedule = null;
                try {
                    schedule = new SimpleDateFormat("YYYY-MM-dd").parse(Mdata.get(viewHolder.getAdapterPosition()).getSchedule());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date now = new Date();
                if (now.getTime()>schedule.getTime()){
                    Intent i = new Intent(context, EventDetail.class);
                    i.putExtra("id", Mdata.get(viewHolder.getAdapterPosition()).getEvent());
                    context.startActivity(i);
                }else{
                    Intent i = new Intent(context, NextEventDetail.class);
                    i.putExtra("id", Mdata.get(viewHolder.getAdapterPosition()).getEvent());
                    context.startActivity(i);
                }
            }
        });
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.myTeam1.setText(Mdata.get(position).getTeamHome());
        holder.myTeam2.setText(Mdata.get(position).getTeamAway());
        holder.myScore1.setText(Mdata.get(position).getScoreHome());
        holder.myScore2.setText(Mdata.get(position).getScoreAway());
        holder.mySchedule.setText(ConvertDate(Mdata.get(position).getSchedule()));
      //  holder.myImg1.setImageResource(images1[position]);
      //  holder.myImg2.setImageResource(images2[position]);
        Picasso.get().load(HomePage.map.get(Mdata.get(position).getIdHome())).into(holder.myImg1);
        Picasso.get().load(HomePage.map.get(Mdata.get(position).getIdAway())).into(holder.myImg2);
    }

    @Override
    public int getItemCount() {
        return Mdata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView myTeam1, myTeam2, myScore1, myScore2, mySchedule;
        ImageView myImg1, myImg2;
        ConstraintLayout view_container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view_container = itemView.findViewById(R.id.container);
            myTeam1 = itemView.findViewById(R.id.TeamHome);
            myTeam2 = itemView.findViewById(R.id.TeamAway);
            myScore1 = itemView.findViewById(R.id.ScoreHome);
            myScore2 = itemView.findViewById(R.id.ScoreAway);
            mySchedule = itemView.findViewById(R.id.MatchSchedule);
            myImg1 = itemView.findViewById(R.id.LogoHome);
            myImg2 = itemView.findViewById(R.id.LogoAway);

        }
    }
    public String ConvertDate(String Date){
        String month[] = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        String result,splt[];
        splt = Date.split("-");
        result = splt[2]+ " " + month[Integer.parseInt(splt[1])-1] + " " + splt[0];
        return result;
    }
}
