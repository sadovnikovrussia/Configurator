//package tech.sadovnikov.configurator.ui.base;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.widget.Toast;
//
//import com.hannesdorfmann.mosby3.mvp.MvpActivity;
//
//import tech.sadovnikov.configurator.R;
//import tech.sadovnikov.configurator.di.component.ActivityComponent;
////import tech.sadovnikov.configurator.di.component.DaggerActivityComponent;
//import tech.sadovnikov.configurator.di.module.ActivityModule;
//
//public abstract class BaseActivity extends MvpActivity {
//    private ActivityComponent activityComponent;
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
