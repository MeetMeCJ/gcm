package gcm.play.android.samples.com.gcmquickstart.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import gcm.play.android.samples.com.gcmquickstart.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentPageSlider.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentPageSlider#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPageSlider extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String title;
    private String content;
    private int photo;

    private OnFragmentInteractionListener mListener;

    public FragmentPageSlider() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param titulo Parameter 1.
     * @param contenido Parameter 2.
     * @return A new instance of fragment FragmentPageSlider.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentPageSlider newInstance(String titulo, String contenido,int foto) {
        FragmentPageSlider fragment = new FragmentPageSlider();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, titulo);
        args.putString(ARG_PARAM2, contenido);
        args.putInt(ARG_PARAM3, foto);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_PARAM1);
            content = getArguments().getString(ARG_PARAM2);
            photo = getArguments().getInt(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_page_slider, container, false);

        TextView tvTitle= (TextView) view.findViewById(R.id.fragTitle);
        TextView tvContent= (TextView) view.findViewById(R.id.fragContent);
        ImageView imageView= (ImageView) view.findViewById(R.id.fragImage);

        tvTitle.setText(title);
        tvContent.setText(content);
        imageView.setImageResource(photo);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
