package product_scanner.product_scanner;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class LanguagesFragment extends Fragment {
    private String locale;
    private Button fr;
    private Button tr;
    private Button en;
    private Button vi;
    private TextView txt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_languages, container, false);
        findView(v);

        fr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocale("fr");

            }
        });
        tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocale("tr");
            }
        });
        vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocale("vi");
            }
        });
        en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocale("en");
            }
        });


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        txt.setText(getActivity().getResources().getString(R.string.select_l));

    }

    private void setLocale(String str) {
        SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor=saved_values.edit();
        editor.putString("locale",str);
        editor.commit();
        ((ResideMenu) getActivity()).setUpMenuItems();
        Resume.resumeActivity(getContext());

    }


    private void findView(View v) {
        tr=v.findViewById(R.id.turkish);
        fr=v.findViewById(R.id.français);
        vi=v.findViewById(R.id.viet);
        en=v.findViewById(R.id.english);
        txt=v.findViewById(R.id.textlanguage);
    }


}
