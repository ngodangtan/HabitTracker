package ngodangtan.com.habittracker.ui.introscreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_intro.*
import ngodangtan.com.habittracker.MainActivity
import ngodangtan.com.habittracker.R
import ngodangtan.com.habittracker.data.models.IntroView

class IntroActivity : AppCompatActivity() {
    lateinit var introView: List<IntroView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        addToIntroView()


        viewPager2.adapter = ViewPagerAdapter(introView)
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        circleIndicator.setViewPager(viewPager2)

        viewPager2.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (position == 2){
                    animationButon()
                }
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })
    }
    private fun animationButon(){
        btn_start_app.visibility = View.VISIBLE

        btn_start_app.animate().apply {
            duration = 1400
            alpha(1f)
            btn_start_app.setOnClickListener {
                val i = Intent(applicationContext,MainActivity::class.java)
                startActivity(i)
                finish()
            }
        }.start()
    }
    private fun addToIntroView(){
        introView = listOf(
            IntroView("Welcon to Habit Tracker !",R.drawable.ic_tea),
            IntroView("This is the second page", R.drawable.ic_smoking),
            IntroView("This is the final page", R.drawable.ic_fastfood)
        )
    }
}