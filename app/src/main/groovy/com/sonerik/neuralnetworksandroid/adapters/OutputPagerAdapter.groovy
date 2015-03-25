package com.sonerik.neuralnetworksandroid.adapters

import android.support.annotation.NonNull
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.sonerik.neuralnetworksandroid.fragments.TableFragment
import groovy.transform.CompileStatic

@CompileStatic
public class OutputPagerAdapter extends FragmentPagerAdapter {

    List table

    List fragments = [
            TableFragment.newFragment(table as ArrayList),
            [new Fragment()] * 2
    ]

    OutputPagerAdapter(FragmentManager fm, List table) {
        super(fm)
        this.table = table
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
                return "Result table"
            case 1:
                return "Statistics"
            case 2:
                return "Chart"
        }
        return ""
    }
}
