package com.example.mymovies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView ivPoster, ivRetornar;
    private TextView tvTitulo, tvGeneros, tvAno, tvDescricao;

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetailFragment newInstance(String param1, String param2) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static MovieDetailFragment newInstance(String title, String year, ArrayList<String> genres, String posterUrl, String extract) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("ano",year);
        args.putStringArrayList("genres", genres);
        args.putString("posterUrl", posterUrl);
        args.putString("extract", extract);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        ivPoster = view.findViewById(R.id.ivPoster);
        ivRetornar = view.findViewById(R.id.ivRetornar);
        tvTitulo = view.findViewById(R.id.tvTitulo);
        tvGeneros = view.findViewById(R.id.tvGeneros);
        tvAno = view.findViewById(R.id.tvAno);
        tvDescricao = view.findViewById(R.id.tvDescricao);

        ivRetornar.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack()
        );

        // Recuperar argumentos e configurar os elementos de UI
        if (getArguments() != null) {
            String title = getArguments().getString("title");
            ArrayList<String> genres = getArguments().getStringArrayList("genres");
            String posterUrl = getArguments().getString("posterUrl");
            String ano = getArguments().getString("ano");
            String descricao = getArguments().getString("extract");

            tvTitulo.setText(title);
            tvGeneros.setText(String.join(", ", genres));
            tvAno.setText(ano);
            tvDescricao.setText(descricao);

            // Carregar a imagem no ImageView usando Glide
            Glide.with(this)
                    .load(posterUrl)
                    .into(ivPoster);
        }

        return view;
    }

}