package com.sonerik.neuralnetworksandroid.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.sonerik.neuralnetworksandroid.fragments.TableFragment
import groovy.transform.CompileStatic

@CompileStatic
public class OutputPagerAdapter extends FragmentPagerAdapter {

    List outputTable
    List inputTable

    List fragments

    OutputPagerAdapter(FragmentManager fm, List inputTable, List outputTable) {
        super(fm)
        this.inputTable = inputTable
        this.outputTable = outputTable
        fragments = [
                TableFragment.newFragment(inputTable as ArrayList),
                TableFragment.newFragment(outputTable as ArrayList),
                new Fragment()
        ]
    }

    @Override
    Fragment getItem(int position) {
        fragments[position] as Fragment
    }

    @Override
    int getCount() {
        fragments.size()
    }

    @Override
    CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Input table"
            case 1:
                return "Result table"
            case 2:
                return "Chart"
        }
        return ""
    }
}
