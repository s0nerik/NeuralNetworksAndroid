package com.sonerik.neuralnetworksandroid.activities

import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.Toolbar
import android.widget.EditText
import com.afollestad.materialdialogs.MaterialDialog
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.InjectView
import com.sonerik.neuralnetworksandroid.App
import com.sonerik.neuralnetworksandroid.R
import com.sonerik.neuralnetworksandroid.events.InputLoadedEvent
import com.sonerik.neuralnetworksandroid.events.NetworkStudyOver
import com.sonerik.neuralnetworksandroid.fragments.InputFragment
import com.sonerik.neuralnetworksandroid.fragments.LoadInputFragment
import com.sonerik.neuralnetworksandroid.fragments.OutputFragment
import com.squareup.otto.Subscribe
import groovy.transform.CompileStatic
import me.alexrs.prefs.lib.Prefs

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
    void onEnterAnimationComplete() {
        super.onEnterAnimationComplete()
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState)
        toolbar.setTitle(R.string.app_name)
        toolbar.inflateMenu(R.menu.main)
        toolbar.onMenuItemClickListener = {
            switch (it.itemId) {
                case R.id.action_settings:
                    def view = layoutInflater.inflate(R.layout.dialog_settings, null)

                    def defaults = [
                            learningRate: 0.115d,
                            maxError: 1.0d,
                            maxEpochs: 100i,
                            hiddenLayers: 2i,
                            nodesEachLayer: 6i
                    ]

                    def prefs = Prefs.with(applicationContext as Context)

                    defaults.each { key, value ->
                        def editText = view.findViewById(R.id["settings_${key}"] as int) as EditText
                        editText.setText((prefs.all[key]?:defaults[key]).toString())
                    }

                    new MaterialDialog.Builder(this)
                            .title("Settings")
                            .customView(view)
                            .positiveText(android.R.string.ok)
                            .negativeText(android.R.string.cancel)
                            .callback(new MaterialDialog.ButtonCallback() {
                                @Override
                                void onPositive(MaterialDialog dialog) {
                                    defaults.each { String key, Object value ->
                                        def editText = view.findViewById(R.id["settings_${key}"] as int) as EditText
                                        switch (defaults[key].class) {
                                            case Double:
                                                prefs.save(key, editText.getText().toFloat())
                                                break
                                            case Integer:
                                                prefs.save(key, editText.getText().toInteger())
                                                break
                                        }
                                    }
                                }
                            })
                            .cancelable(false)
                            .build()
                            .show()
                    break
            }
            true
        }
        supportFragmentManager.beginTransaction().replace(R.id.container, new LoadInputFragment()).commit()
    }

    @Subscribe
    void onInputLoaded(InputLoadedEvent e) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, InputFragment.newFragment(e.input as ArrayList))
                .commit()
    }

    @Subscribe
    void onStudyOver(NetworkStudyOver e) {
        supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container, OutputFragment.newFragment(e.data as ArrayList))
                .commit()
    }

}
