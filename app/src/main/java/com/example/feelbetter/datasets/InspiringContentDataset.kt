package com.example.feelbetter.datasets

import com.example.feelbetter.R
import com.example.feelbetter.models.InspiringContent

class InspiringContentDataset {
    fun loadInspiring(): List<InspiringContent> {
        return listOf<InspiringContent>(
            InspiringContent(
                R.string.inspiring_title_1,
                R.string.inspiring_desc_1,
                R.drawable.dont_allow
            ),
            InspiringContent(
                R.string.inspiring_title_2,
                R.string.inspiring_desc_2,
                R.drawable.belive
            ),
            InspiringContent(
                R.string.inspiring_title_3,
                R.string.inspiring_desc_3,
                R.drawable.doing_my_best
            ),
            InspiringContent(
                R.string.inspiring_title_4,
                R.string.inspiring_desc_4,
                R.drawable.i_can_i_do
            ),
        )
    }
}