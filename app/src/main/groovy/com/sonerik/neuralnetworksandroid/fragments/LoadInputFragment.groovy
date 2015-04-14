package com.sonerik.neuralnetworksandroid.fragments
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.OnClick
import com.sonerik.neuralnetworksandroid.App
import com.sonerik.neuralnetworksandroid.R
import com.sonerik.neuralnetworksandroid.events.InputLoadedEvent
import groovy.transform.CompileStatic

@CompileStatic
public class LoadInputFragment extends Fragment {

    @Override
    View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView inflater, container, savedInstanceState
        def v = inflater.inflate R.layout.fragment_load_input, container, false
        SwissKnife.inject this, v
        return v
    }

    @OnClick([R.id.load_input_text, R.id.load_input_icon])
    void onLoadFromCsvClicked() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT)
        Uri uri = Uri.parse Environment.externalStorageDirectory.path
        intent.setDataAndType uri, "text/csv"
        startActivityForResult Intent.createChooser(intent, "Open folder"), 1337
    }

    @Override
    void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState)

        def csv = new File(new File(Environment.externalStorageDirectory, "Download"), "variant_1.csv")
        App.bus.post new InputLoadedEvent(input: parseCsv(csv))
    }

    @Override
    void onActivityResult(int requestCode, int resultCode, Intent intent) {
        List<List> tableData

        if (requestCode == 1337) {
            if (resultCode == Activity.RESULT_OK && intent) {
                tableData = parseCsv(new File(intent.data?.path))
            } else {
                tableData = Collections.nCopies(600, 0..6 as List)
            }

            App.bus.post new InputLoadedEvent(input: tableData)
        }
    }

    private List<List> parseCsv(File file) {
        def csv = file?.readLines()?.collect { String it ->
            it.replaceAll(" ", "").split(",").toList()
        }

        return csv
    }

}
