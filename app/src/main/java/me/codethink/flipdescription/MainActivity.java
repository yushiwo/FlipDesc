package me.codethink.flipdescription;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    private static final int LIMIT = 2;
    private int currentLines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView descriptions = (TextView) findViewById(R.id.descriptions);

        currentLines = LIMIT;

        descriptions.setMaxLines(currentLines);
        descriptions.setText("1993年，位于东直门内大街的北京金鼎轩酒楼有限责任公司总店（当时名为金鼎酒楼）开业。在京城率先推出24小时营业，推动了东直门内餐饮一条街的发展，形成了众所周知的“簋街”。迄今为止，金鼎轩旗下已拥有多家分店，以经营川、粤、鲁、淮四大菜系为主，秉承并发展传统烹调技艺之精华，推动新派美食，倡导健康餐饮文化——无添加剂、无色素、无味精的绿色食品。 现在店内新添更多春节美食，年糕、食盒、花式蒸馍、礼品卡汇聚一堂，丝丝入口，醇香滋味，年味十足，诚意十足，欢迎广大的新老朋友光临！");

        final View showDetailButton = findViewById(R.id.show_detail_button);

        showDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentLines = currentLines == LIMIT ? Integer.MAX_VALUE : LIMIT;
                descriptions.setMaxLines(currentLines);
                showDetailButton.setRotation(180 - showDetailButton.getRotation());
            }
        });
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
