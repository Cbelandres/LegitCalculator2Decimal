package com.dauba.news;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

public class HeadlinesFragment extends Fragment {

    private ListView listView;
    private OnHeadlineSelectedListener callback;

    public interface OnHeadlineSelectedListener {
        void onHeadlineSelected(String content);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (OnHeadlineSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_headlines, container, false);
        listView = view.findViewById(R.id.headline_list);

        final String[] headlines = {"Typhoon Jenny Brings Heavy Rainfall Across Luzon", " Philippines Eyes $10 Billion Tech Industry Investment", "Philippines' Inflation Rate Reaches 4.5% Amid Rising Food Prices"," Philippines to Host 2024 ASEAN Summit in Cebu","Philippine Education Sector Adopts AI Tools in Classrooms"};
        final String[] contents = {
                "Typhoon Jenny made landfall in northern Luzon, bringing heavy rains and strong winds across several regions. The National Disaster Risk Reduction and Management Council (NDRRMC) has raised alerts in affected provinces, urging residents to evacuate from low-lying areas prone to flooding and landslides. Schools and offices have been closed in key cities as precautionary measures. Relief efforts are underway as the typhoon is expected to exit the Philippine Area of Responsibility within the next 24 hours.",
                "The Philippine government is ramping up efforts to attract investments in the technology sector, with plans to secure up to $10 billion in foreign investments by 2025. The Department of Trade and Industry (DTI) revealed its latest roadmap to enhance digital infrastructure and promote startups, aiming to transform the Philippines into a leading tech hub in Southeast Asia. Global tech giants, including Google and Amazon, have expressed interest in partnering with local companies to expand their operations in the country.",
                "The Philippine Statistics Authority (PSA) has reported that the country's inflation rate rose to 4.5% in September, driven by the increasing cost of food, particularly rice, vegetables, and meat. The government is working to stabilize food supply through import measures and targeted financial aid for farmers. Economists have raised concerns over the prolonged inflationary pressures, especially on low-income households, as basic commodity prices continue to rise.",
                "Cebu City has been selected as the host for the upcoming 2024 ASEAN Summit, where leaders from Southeast Asia will convene to discuss economic, political, and social issues affecting the region. The summit will focus on climate change, trade relations, and security cooperation. Preparations are in full swing, with local and national authorities coordinating efforts to ensure the safety and success of the event. The summit is expected to boost Cebu’s tourism and business sectors, with thousands of delegates and visitors anticipated.",
                "In a significant step towards modernization, the Department of Education (DepEd) has started the implementation of AI-based learning tools in select public schools across the country. These tools aim to provide personalized learning experiences for students, particularly in math and science subjects. The initiative is part of the government’s Digital Philippines strategy, which envisions the integration of advanced technologies in both rural and urban educational settings. Teachers are undergoing training to effectively utilize these AI platforms.",
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, headlines);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> callback.onHeadlineSelected(contents[position]));

        return view;
    }
}
