package com.example.noteapp.ui.onBoard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.noteapp.R;

public class boardAdapter extends RecyclerView.Adapter<boardAdapter.ViewHolder> {
    private final String[] titles = new String[]{"Будет сделано так, чтобы Вам было комфортно","Обращайтесь","Мы на связи"};
    private final String[] titlesExplain =new String[]{"Всегда найдем любые данные и сделаем необходимый анализ",
            "Оставьте один месседж ",
            "24 часа 7 дней в неделю"};
    private final int[] images= new int[]{
     R.raw.cat,R.raw.powerful,R.raw.soft
    };



    public interface OnStartClickListener {
        void onClick();
    }
    private OnStartClickListener onStartClickListener;

    public void setOnStartClickListener(OnStartClickListener onStartClickListener) {
        this.onStartClickListener = onStartClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pager_board, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return 3;
    }



    public boardAdapter() {
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textTitle;
        private final Button btnStart;
        private final LottieAnimationView imageView;
        private final TextView textDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textViewBoard);
            btnStart = itemView.findViewById(R.id.btnStart);
            imageView=itemView.findViewById(R.id.anime);
            textDesc = itemView.findViewById(R.id.textDescBoard);
            btnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onStartClickListener.onClick();
                }
            });
        }

        public void bind(int position) {
            imageView.setAnimation(images[position]);
            textTitle.setText(titles[position]);
            textDesc.setText(titlesExplain[position]);
            if(position==2)
                  {btnStart.setVisibility(View.VISIBLE);}
             else{btnStart.setVisibility(View.INVISIBLE);}
        }

    }

}


