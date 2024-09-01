package com.example.notificationwithrecycleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notificationwithrecycleview.databinding.CostumeUserItemBinding;

import java.util.ArrayList;

public class UserAddpater extends RecyclerView.Adapter<UserAddpater.UserHolder> {
    ArrayList<User> arrayList;
    Context context;
    onclick onclick;

    public UserAddpater(Context context, ArrayList<User> arrayList, com.example.notificationwithrecycleview.onclick onclick) {
        this.context = context;
        this.arrayList = arrayList;
        this.onclick = onclick;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CostumeUserItemBinding binding=CostumeUserItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new UserHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        holder.setBinding(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CostumeUserItemBinding binding;
        public UserHolder(@NonNull CostumeUserItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
            binding.getRoot().setOnClickListener(this);

        }

        void setBinding(User user){
            binding.useImageView.setImageResource(user.getImage());
            binding.userTitle.setText(user.getName());
            binding.useEmail.setText(user.getEmail());
            binding.useSalary.setText(String.valueOf(user.getSalary()));
        }


        @Override
        public void onClick(View v) {
            if(onclick!=null){
                onclick.onclickuser(arrayList.get(getAdapterPosition()));

            }
        }
    }
}
