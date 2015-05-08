package me.codethink.flipdescription;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    private static final int LIMIT = 2;
    private int currentLines;
    int FULLSIZE = -1;
    int THUMBNAIL = -1;
    private TextView descriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        descriptions = (TextView) findViewById(R.id.descriptions);

        currentLines = LIMIT;

        descriptions.setMaxLines(currentLines);
        descriptions.setText("1993年，位于东直门内大街的北京金鼎轩酒楼有限责任公司总店（当时名为金鼎酒楼）开业。在京城率先推出24小时营业，推动了东直门内餐饮一条街的发展，形成了众所周知的“簋街”。迄今为止，金鼎轩旗下已拥有多家分店，以经营川、粤、鲁、淮四大菜系为主，秉承并发展传统烹调技艺之精华，推动新派美食，倡导健康餐饮文化——无添加剂、无色素、无味精的绿色食品。 现在店内新添更多春节美食，年糕、食盒、花式蒸馍、礼品卡汇聚一堂，丝丝入口，醇香滋味，年味十足，诚意十足，欢迎广大的新老朋友光临！");

        //展开按钮
        final View showDetailButton = findViewById(R.id.show_detail_button);
        //背景图片
        final ImageView bg = (ImageView) findViewById(R.id.bg);
        //显示文字的layout
        final View contentView = findViewById(R.id.content);


        contentView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                //设置背景的高度
                bg.getLayoutParams().height = contentView.getHeight();
                bg.requestLayout();
                //计算收起和展开时候view的高度
                if (THUMBNAIL == -1) {
                    THUMBNAIL = contentView.getHeight();
                }

                if (FULLSIZE == -1) {
                    FULLSIZE = THUMBNAIL + getTextViewHeight(descriptions) - descriptions.getHeight();
                }
            }
        });

        showDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (currentLines == LIMIT) {
                    expand(contentView, THUMBNAIL, FULLSIZE);
                } else {
                    expand(contentView, FULLSIZE, THUMBNAIL);
                }

                //根据设置之后的TextView状态对showDetailButton进行旋转(设置旋转动画)
                Animation animation = new RotateAnimation(
                        currentLines == LIMIT ? 0 : 180,
                        currentLines == LIMIT ? 180 : 360,
                        Animation.RELATIVE_TO_SELF,
                        0.5f,
                        Animation.RELATIVE_TO_SELF,
                        0.5f);

                animation.setDuration(300);    //动画持续时间
                animation.setFillAfter(true);  //动画执行完后是否停留在执行完的状态
                showDetailButton.startAnimation(animation);
                contentView.requestLayout();  //通知parent view重绘


            }
        });
    }

    /**
     * 通过让高度随时间变化实现逐渐展开和收起的效果。此外需要注意的是这里为动画设置了监听器。
     * 为了避免文字被卷起的边缘压到，分别在展开后和收缩后对TextView的maxLine进行修改。
     * @param v
     * @param originHeight
     * @param targetHeight
     */
    public void expand(final View v, final float originHeight, final float targetHeight) {
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = /*interpolatedTime == 1
                        ? LinearLayoutCompat.LayoutParams.WRAP_CONTENT
                        :*/ (int) (originHeight + (targetHeight - originHeight) * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration(600);
        a.setFillAfter(true);
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                currentLines = currentLines == LIMIT ? Integer.MAX_VALUE : LIMIT;
                descriptions.setMaxLines(currentLines);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(a);
    }

    /**
     * 获取文字全部展开TextView的高度。
     * 这部分代码把TextView高度的计算分为两部分，文字高度desired和边距padding。
     * getLineTop(n)函数返回第ｎ行顶端的位置，当n为行数时，为最后一行底部的位置。
     * 这里写成layout.getLineTop(pTextView.getLineCount())- layout.getLineTop(0)更好理解一些。
     * @param pTextView
     * @return
     */
    private int getTextViewHeight(TextView pTextView) {
        Layout layout = pTextView.getLayout();
        int desired = layout.getLineTop(pTextView.getLineCount());
        int padding = pTextView.getCompoundPaddingTop() + pTextView.getCompoundPaddingBottom();
        return desired + padding;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
