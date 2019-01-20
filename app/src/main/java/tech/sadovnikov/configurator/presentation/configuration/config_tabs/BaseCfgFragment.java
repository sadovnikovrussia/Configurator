package tech.sadovnikov.configurator.presentation.configuration.config_tabs;

import android.content.Context;
import android.view.View;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;


public abstract class BaseCfgFragment extends MvpAppCompatFragment implements BaseCfgView {
    private static final String TAG = BaseCfgFragment.class.getSimpleName();

    @InjectPresenter
    BaseCfgPresenter presenter;

    private Listener listener;


    abstract void setUp(View view);

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStartBaseCfgView();
        listener.onStartBaseCfgView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            listener = (Listener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement BaseCfgFragment.Listener");
        }
    }

    public interface Listener{
        void onStartBaseCfgView();
    }
}
