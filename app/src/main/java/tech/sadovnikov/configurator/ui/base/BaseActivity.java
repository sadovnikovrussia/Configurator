//package tech.sadovnikov.configurator.ui.base;
//
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.widget.Toast;
//
//import com.hannesdorfmann.mosby3.mvp.MvpActivity;
//import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
//import com.hannesdorfmann.mosby3.mvp.MvpView;
//
//import tech.sadovnikov.configurator.R;
//import tech.sadovnikov.configurator.di.component.ActivityComponent;
////import tech.sadovnikov.configurator.di.component.DaggerActivityComponent;
//import tech.sadovnikov.configurator.di.module.ActivityModule;
//
//public abstract class BaseActivity<V extends MvpView,P extends MvpPresenter> extends MvpActivity {
//    private ActivityComponent activityComponent;
//
//    @NonNull
//    @Override
//    public P getPresenter() {
//        return super.getPresenter();
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        initializeInjector();
//    }
//
//    private void initializeInjector() {
//        //activityComponent = DaggerActivityComponent.builder().activityModule(new ActivityModule(this)).build();
//    }
//
//    public ActivityComponent getActivityComponent() {
//        return activityComponent;
//    }
//
//    public void showMessage(String message) {
//        if (message != null) {
//            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show();
//        }
//    }
//
//}
