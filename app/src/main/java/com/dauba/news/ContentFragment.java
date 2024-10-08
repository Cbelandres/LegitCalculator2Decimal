package com.dauba.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ContentFragment extends Fragment {

    private TextView contentTextView;

    public static ContentFragment newInstance(String content) {
        ContentFragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putString("content", content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        contentTextView = view.findViewById(R.id.news_content);

        if (getArguments() != null) {
            contentTextView.setText(getArguments().getString("content"));
        }

        return view;
    }
}
