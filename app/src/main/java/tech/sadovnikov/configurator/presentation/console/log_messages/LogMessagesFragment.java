package tech.sadovnikov.configurator.presentation.console.log_messages;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.sadovnikov.configurator.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LogMessagesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LogMessagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogMessagesFragment extends MvpAppCompatFragment {
    private static final String TAG = LogMessagesFragment.class.getSimpleName();

    @BindView(R.id.tv_log_screen)
    TextView tvLogs;
    @BindView(R.id.sv_log_screen)
    ScrollView svLogs;

    private OnFragmentInteractionListener mListener;

    @InjectPresenter
    LogMessagesPresenter presenter;

    public static LogMessagesFragment newInstance(String name) {
        LogMessagesFragment fragment = new LogMessagesFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate: ");
        if (getArguments() != null) {
            String logTab = getArguments().getString("name");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG, "ON_CREATE_VIEW");
        View view = inflater.inflate(R.layout.fragment_log_messages, container, false);
        ButterKnife.bind(this, view);
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
        Log.v(TAG, "onAttach: ");
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
        Log.v(TAG, "onDetach: ");
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
