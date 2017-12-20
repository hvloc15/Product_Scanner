package product_scanner.product_scanner;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Locale;


public class LanguagesFragment extends Fragment {

    private Button fr;
    private Button tr;
    private Button en;
    private Button vi;

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



    private void findView(View v) {
        tr=v.findViewById(R.id.turkish);
        fr=v.findViewById(R.id.français);
        vi=v.findViewById(R.id.viet);
        en=v.findViewById(R.id.english);
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(getActivity(), ResideMenu.class);
        startActivity(refresh);
    }
}
