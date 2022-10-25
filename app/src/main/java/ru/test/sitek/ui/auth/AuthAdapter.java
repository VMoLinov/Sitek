package ru.test.sitek.ui.auth;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.test.sitek.databinding.AuthItemBinding;
import ru.test.sitek.local.HistoryEntity;

public class AuthAdapter extends RecyclerView.Adapter<AuthAdapter.ViewHolder> {

    private List<HistoryEntity> data = new ArrayList<>();

    void setData(List<HistoryEntity> data) {
        this.data = data;
        notifyItemInserted(data.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AuthItemBinding binding = AuthItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final AuthItemBinding binding;

        public ViewHolder(AuthItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(HistoryEntity historyEntity) {
            binding.response.append(" " + historyEntity.response);
            binding.work.append(" " + historyEntity.continueWork);
            binding.photoHash.append(" " + historyEntity.photoHash);
            binding.date.append(" " + historyEntity.currentDate);
            binding.time.append(" " + historyEntity.currentTime);
        }
    }
}
