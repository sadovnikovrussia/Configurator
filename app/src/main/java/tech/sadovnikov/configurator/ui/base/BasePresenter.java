//package tech.sadovnikov.configurator.ui.base;
//
//import android.support.annotation.NonNull;
//
//import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
//import com.hannesdorfmann.mosby3.mvp.MvpView;
//
//import io.reactivex.disposables.CompositeDisposable;
//import tech.sadovnikov.configurator.model.data.AppDataManager;
//import tech.sadovnikov.configurator.utils.rx.SchedulerProvider;
//
//public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {
//    private static final String TAG = "BasePresenter";
//
//    private final AppDataManager mDataManager;
//    private final SchedulerProvider schedulerProvider;
//    private final CompositeDisposable mCompositeDisposable;
//
//    private V mMvpView;
//
//    public BasePresenter(AppDataManager mDataManager, SchedulerProvider schedulerProvider, CompositeDisposable mCompositeDisposable) {
//        this.mDataManager = mDataManager;
//        this.schedulerProvider = schedulerProvider;
//        this.mCompositeDisposable = mCompositeDisposable;
//    }
//
//    @Override
//    public void attachView(@NonNull V view) {
//        mMvpView = view;
//
//    }
//
//    @Override
//    public void detachView(boolean retainInstance) {
//
//    }
//
//    @Override
//    public void detachView() {
//        mCompositeDisposable.dispose();
//        mMvpView = null;
//
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
