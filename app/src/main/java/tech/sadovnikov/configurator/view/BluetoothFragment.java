package tech.sadovnikov.configurator.view;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Switch;

import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.presenter.BluetoothService;
import tech.sadovnikov.configurator.view.adapter.BluetoothDevicesAdapter;


/**
 * Фрагмент для отображения bluetooth устройств
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnBluetoothFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class BluetoothFragment extends Fragment {

    private static final String TAG = "BluetoothFragment";
    String mKeySwitchBt = "SwitchBt";

    // UI
    Switch switchBt;

    RecyclerView rvBtPairedDevices;
    RecyclerView rvBtAvailableDevices;

    private OnBluetoothFragmentInteractionListener mListener;

    public BluetoothFragment() {
        // Required empty public constructor
        Log.v(TAG, "onConstructor");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v(TAG, "onCreateView");
        final View inflate = inflater.inflate(R.layout.fragment_bluetooth, container, false);
        initUi(inflate);
        return inflate;
    }

    private void initUi(View inflate) {
        rvBtPairedDevices = inflate.findViewById(R.id.rv_paired_devices);
        rvBtAvailableDevices = inflate.findViewById(R.id.rv_available_devices);
        rvBtPairedDevices.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        rvBtAvailableDevices.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        Log.d(TAG, BluetoothService.getBondedDevices().toString());
        rvBtPairedDevices.setAdapter(new BluetoothDevicesAdapter(BluetoothService.getBondedDevices()));
        switchBt = inflate.findViewById(R.id.sw_bluetooth);
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
        if (context instanceof OnBluetoothFragmentInteractionListener) {
            mListener = (OnBluetoothFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBluetoothFragmentInteractionListener");
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
    public interface OnBluetoothFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

        void onSwitchBtStateChanged(boolean state);

        void onRvBtItemClicked(int position);

    }


}
