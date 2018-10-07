package tech.sadovnikov.configurator.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.presenter.BluetoothService;
import tech.sadovnikov.configurator.view.adapter.PairedDevicesRvAdapter;

public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private static final String TAG = "PageFragment";
    RecyclerView rvPairedDevices;
    private int mPage;

    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getInt(ARG_PAGE);
        }
    }

    @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_paired_devices, container, false);

        rvPairedDevices = view.findViewById(R.id.rv_paired_devices);
        rvPairedDevices.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        Log.d(TAG, BluetoothService.getBondedDevices().toString());
        rvPairedDevices.setAdapter(new PairedDevicesRvAdapter(BluetoothService.getBondedDevices()));

        return view;
    }
}
