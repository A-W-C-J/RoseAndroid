package com.rose.android.ui.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.rose.android.R;
import com.rose.android.contract.AssetTotalContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MAssetTotalModel;
import com.rose.android.presenter.AssetTotalPresenter;

import java.util.ArrayList;

@Route(path = "/ui/assetTotalActivity", extras = 1)
public class AssetTotalActivity extends BaseActivity implements OnChartValueSelectedListener, AssetTotalContract.View {
  private PieChart mChart;
  private AssetTotalPresenter assetTotalPresenter;
  private MAssetTotalModel mAssetTotalModel;
  private View blank;
  private TextView consumption, cash;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    create(R.layout.activity_asset_total);
    setContentView(getRootView());
    assetTotalPresenter = new AssetTotalPresenter(userHttpClient, this);
    setTitle("资产统计");
    initViews();
    initListener();
    getAssetTotal();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (assetTotalPresenter != null) {
      assetTotalPresenter.dispose();
      assetTotalPresenter = null;
    }
  }

  @Override
  public void initViews() {
    super.initViews();
    blank = findViewById(R.id.blank);
    mChart = (PieChart) findViewById(R.id.chart);
    mChart.setUsePercentValues(true);
    mChart.getDescription().setEnabled(false);
    mChart.setExtraOffsets(5, 10, 5, 5);
    mChart.setDragDecelerationFrictionCoef(0.95f);
    mChart.setCenterText(generateCenterSpannableText());
    mChart.setDrawHoleEnabled(true);
    mChart.setHoleColor(Color.WHITE);
    mChart.setTransparentCircleColor(Color.WHITE);
    mChart.setTransparentCircleAlpha(110);
    mChart.setHoleRadius(58f);
    mChart.setTransparentCircleRadius(61f);
    mChart.setDrawCenterText(true);
    mChart.setRotationAngle(0);
    mChart.setRotationEnabled(true);
    mChart.setHighlightPerTapEnabled(true);
    mChart.setOnChartValueSelectedListener(this);
    mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
    Legend l = mChart.getLegend();
    l.setEnabled(false);
    mChart.setEntryLabelColor(Color.WHITE);
    mChart.setEntryLabelTextSize(8f);
    consumption = findViewById(R.id.consumption);
    cash = findViewById(R.id.cash);
  }

  private SpannableString generateCenterSpannableText() {
    SpannableString s = new SpannableString("资产统计");
    s.setSpan(new RelativeSizeSpan(1.7f), 0, 4, 0);
    s.setSpan(new StyleSpan(Typeface.NORMAL), 0, 4, 0);
    s.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 4, 0);
    return s;
  }

  private void setData() {
    consumption.setText(com.rose.android.utils.Utils.
        formatWithScale(com.rose.android.utils.Utils.divide1000(mAssetTotalModel.getData().getTotalStock()), 2));
    cash.setText(com.rose.android.utils.Utils.
        formatWithScale(com.rose.android.utils.Utils.divide1000(mAssetTotalModel.getData().getTotalCash()), 2));
    if (mAssetTotalModel.getData().getTotalStock() == 0 && mAssetTotalModel.getData().getTotalCash() == 0) {
      blank.setVisibility(View.VISIBLE);
      mChart.setVisibility(View.GONE);
      return;
    } else {
      blank.setVisibility(View.GONE);
      mChart.setVisibility(View.VISIBLE);
    }
    ArrayList<PieEntry> pieEntryList = new ArrayList<PieEntry>();
    ArrayList<Integer> colors = new ArrayList<Integer>();
    colors.add(Color.parseColor("#FFB700"));
    colors.add(Color.parseColor("#D03838"));
    //饼图实体 PieEntry
    PieEntry CashBalance = new PieEntry(mAssetTotalModel.getData().getTotalCash()
    );
    PieEntry ConsumptionBalance = new PieEntry(mAssetTotalModel.getData().getTotalStock());
    pieEntryList.add(CashBalance);
    pieEntryList.add(ConsumptionBalance);
    PieDataSet pieDataSet = new PieDataSet(pieEntryList, "");
    pieDataSet.setSliceSpace(3f);           //设置饼状Item之间的间隙
    pieDataSet.setSelectionShift(10f);      //设置饼状Item被选中时变化的距离
    pieDataSet.setColors(colors);           //为DataSet中的数据匹配上颜色集(饼图Item颜色)
    PieData pieData = new PieData(pieDataSet);
    pieData.setDrawValues(true);            //设置是否显示数据实体(百分比，true:以下属性才有意义)
    pieData.setValueTextColor(Color.WHITE);  //设置所有DataSet内数据实体（百分比）的文本颜色
    pieData.setValueTextSize(11f);          //设置所有DataSet内数据实体（百分比）的文本字体大小
    pieData.setValueFormatter(new PercentFormatter());//设置所有DataSet内数据实体（百分比）的文本字体格式
    mChart.setData(pieData);
    mChart.highlightValues(null);
    mChart.invalidate();
  }

  @Override
  public void onValueSelected(Entry e, Highlight h) {

  }

  @Override
  public void onNothingSelected() {

  }

  @Override
  public void getAssetTotal() {
    assetTotalPresenter.getAssetTotal();
  }

  @Override
  public BaseModel requestCallBack(BaseModel baseModel) {
    if (baseModel instanceof MAssetTotalModel) {
      mAssetTotalModel = (MAssetTotalModel) baseModel;
      setData();
    }
    return super.requestCallBack(baseModel);
  }
}
