package com.sonerik.neuralnetworksandroid.activities
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.Toolbar
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.InjectView
import com.sonerik.neuralnetworksandroid.App
import com.sonerik.neuralnetworksandroid.R
import com.sonerik.neuralnetworksandroid.events.InputLoadedEvent
import com.sonerik.neuralnetworksandroid.fragments.InputFragment
import com.sonerik.neuralnetworksandroid.fragments.LoadInputFragment
import com.squareup.otto.Subscribe
import groovy.transform.CompileStatic

@CompileStatic
public class MainActivity extends FragmentActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        contentView = R.layout.activity_main
        SwissKnife.inject(this)
        App.bus.register(this)
    }

    @Override
    protected void onDestroy() {
        super.onDestroy()
        App.bus.unregister(this)
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState)
        toolbar.setTitle(R.string.app_name)
        supportFragmentManager.beginTransaction().replace(R.id.container, new LoadInputFragment()).commit()
    }

    @Subscribe
    void onInputLoaded(InputLoadedEvent e) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, InputFragment.newFragment(e.input as ArrayList))
                .commit()
    }

}
