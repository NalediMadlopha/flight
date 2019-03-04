package com.flight.app.widget

import android.app.Activity
import android.content.Context
import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.flight.app.R
import kotlinx.android.synthetic.main.label_value_widget.view.*
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.eq
import org.mockito.ArgumentMatchers.isA
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks
import org.mockito.Spy
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest= Config.NONE, sdk = [21])
class LabelValueWidgetTest {

    private lateinit var labelValueWidget: LabelValueWidget

    @Spy
    private val spyContext: Context = Robolectric.buildActivity(Activity::class.java).get()
    @Mock
    private lateinit var mockLayoutInflater: LayoutInflater
    @Mock
    private lateinit var mockView: View
    @Mock
    private lateinit var mockTypedArray: TypedArray
    @Mock
    private lateinit var mockTopTextView: TextView
    @Mock
    private lateinit var mockBottomTextView: TextView

    @Before
    fun setUp() {
        initMocks(this)

        doReturn(mockLayoutInflater).`when`(spyContext).getSystemService(Context.LAYOUT_INFLATER_SERVICE)
        doReturn(mockTypedArray).`when`(spyContext).obtainStyledAttributes(
            null,
            R.styleable.LabelValueWidget, 0, 0
        )

        `when`(
            mockLayoutInflater.inflate(
                eq(R.layout.label_value_widget),
                isA(LabelValueWidget::class.java),
                eq(true)
            )
        ).thenReturn(mockView)

        `when`<TextView>(mockView.labelValueTopTextView).thenReturn(mockTopTextView)
        `when`<TextView>(mockView.labelValueBottomTextView).thenReturn(mockBottomTextView)

        labelValueWidget = LabelValueWidget(spyContext)
    }

    @Test
    fun LabelValueWidget_should_be_an_instance_of_LinearLayout() {
        assertTrue(LinearLayout::class.java.isInstance(labelValueWidget))
    }

    @Test
    fun inflate_layout_on_LabelValueWidget_construction() {
        verify(mockLayoutInflater).inflate(R.layout.label_value_widget, labelValueWidget, true)
    }

    @Test
    fun set_labelValueTopTextView_text_on_LabelValueWidget_construction() {
        val topText = "Top text"

        `when`(mockTypedArray.getString(R.styleable.LabelValueWidget_text_top)).thenReturn(topText)

        labelValueWidget = LabelValueWidget(spyContext)

        verify(mockTopTextView).text = topText
    }

    @Test
    fun set_labelValueBottomTextView_text_on_LabelValueWidget_construction() {
        val bottomText = "Bottom text"

        `when`(mockTypedArray.getString(R.styleable.LabelValueWidget_text_bottom)).thenReturn(bottomText)

        labelValueWidget = LabelValueWidget(spyContext)

        verify(mockBottomTextView).text = bottomText
    }

    @Test
    fun set_text_on_labelValueTopTextView() {
        val topText = "Top text"

        labelValueWidget.setTopText(topText)

        verify(mockTopTextView).text = topText
    }

    @Test
    fun set_text_on_labelValueBottomTextView() {
        val bottomText = "Bottom text"

        labelValueWidget.setBottomText(bottomText)

        verify(mockBottomTextView).text = bottomText
    }

}