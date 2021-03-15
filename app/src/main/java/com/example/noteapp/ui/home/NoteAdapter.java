package com.example.noteapp.ui.home;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp.R;
import com.example.noteapp.interfaces.OnItemClickListener;
import com.example.noteapp.models.Note;

import java.util.ArrayList;
import java.util.List;


public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private ArrayList<Note> list;
    private int position;
    private OnItemClickListener onItemClickListener;

    public NoteAdapter() {
        list = new ArrayList<>();
     //   list.addAll(App.getDataBase().noteDao().getAll());
   //     App.getDataBase().noteDao().getAllLive().observe(lifecycleOwner, new Observer<List<Note>>() {
   //         @Override
   //     public void onChanged(List<Note> notes) {
   //             list.clear();
   //             list.addAll(notes);
  //              notifyDataSetChanged();
  //          }
  //      });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_note, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.YELLOW);
        } else {
            holder.itemView.setBackgroundColor(Color.MAGENTA);
        }
        holder.onBind(list.get(position));

    }
    public void getList(int position, Note note)
    {     list.set(position, note);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
         return list.size();
    }

    public void addItem(Note note) {
        list.add(0, note);
        notifyItemChanged(list.indexOf(0));
    }
    public void setList(List<Note> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    public void sortList(List<Note> sortAll) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    public Note getItem(int position) {
        return list.get(position);
    }
    public void remove(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textTitle;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
           itemView.setOnClickListener(v ->
                onItemClickListener.onItemClick(getAdapterPosition()));
            itemView.setOnLongClickListener(v1 ->  {
              onItemClickListener.onLongClick(getAdapterPosition());
              return true;
            });

        }
        public void onBind(Note note) {
            textTitle.setText(note.getTitle());
        }
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
