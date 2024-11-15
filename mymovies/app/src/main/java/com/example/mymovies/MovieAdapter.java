package com.example.mymovies;


import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.net.URL;
import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {
    private int resource;
    public MovieAdapter(@NonNull Context context, int resource, @NonNull List<Movie> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(this.resource, parent, false);
        }

        Movie m = getItem(position);
        if (m != null) {
            TextView tvAno = convertView.findViewById(R.id.tvAno);
            TextView tvTitulo = convertView.findViewById(R.id.tvTitulo);
            ImageView ivThumb = convertView.findViewById(R.id.ivThumb);

            // Verificar se a URL da imagem não é nula ou vazia
            if (m.thumbnail != null && !m.thumbnail.isEmpty()) {
                // Usar Glide para carregar a imagem a partir da URL
                Glide.with(getContext())
                        .load(m.thumbnail)  // URL da imagem
                        .apply(new RequestOptions().placeholder(R.drawable.imgpadrao))  // Imagem padrão enquanto carrega
                        .into(ivThumb);  // Imagem que será exibida
            } else {
                ivThumb.setImageResource(R.drawable.imgpadrao);  // Usar uma imagem padrão, caso a URL seja inválida
            }

            tvAno.setText(String.valueOf(m.year));  // Certifique-se de que o ano seja um texto
            tvTitulo.setText(m.title);
        }

        return convertView;
    }


}
