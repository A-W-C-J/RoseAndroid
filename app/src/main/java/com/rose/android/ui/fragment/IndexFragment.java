package com.rose.android.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jude.rollviewpager.RollPagerView;
import com.rose.android.R;
import com.rose.android.RouterConfig;
import com.rose.android.contract.ClickResponseListener;
import com.rose.android.contract.GetBannerContract;
import com.rose.android.contract.GetBroadcastContract;
import com.rose.android.contract.GetNoticeContract;
import com.rose.android.contract.GetProductListContract;
import com.rose.android.model.BaseModel;
import com.rose.android.model.MBannersModel;
import com.rose.android.model.MNoticeModel;
import com.rose.android.model.MProductListModel;
import com.rose.android.model.MProductOrderBrodcastModel;
import com.rose.android.presenter.GetBannersPresenter;
import com.rose.android.presenter.GetBroadCastPresenter;
import com.rose.android.presenter.GetNoticePresenter;
import com.rose.android.presenter.GetProductListPresenter;
import com.rose.android.ui.activity.MainActivity;
import com.rose.android.ui.adapter.BannerAdapter;
import com.rose.android.ui.adapter.NoticeAdapter;
import com.rose.android.ui.adapter.ProductListAdapter;
import com.rose.android.utils.Utils;
import com.rose.android.view.MarqueeTextView;
import com.rose.android.view.ToastWithIcon;
import com.rose.android.view.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.rose.android.viewmodel.ProductListVM;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.lang.ref.WeakReference;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings({"UUF_UNUSED_FIELD", "URF_UNREAD_FIELD"})
public class IndexFragment extends BaseFragment implements GetProductListContract.View, ClickResponseListener, GetBannerContract.View, GetBroadcastContract.View, GetNoticeContract.View {
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  private static final String TAG = "IndexFragment";
  private String mParam1;
  private String mParam2;
  private GetProductListPresenter getProductListPresenter;
  private GetBannersPresenter getBannersPresenter;
  private GetBroadCastPresenter getBroadCastPresenter;
  private GetNoticePresenter getNoticePresenter;
  private RecyclerView recyclerView;
  private ProductListAdapter productListAdapter;
  private NoticeAdapter noticeAdapter;
  private HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter;
  private HeaderAndFooterRecyclerViewAdapter noticeHeaderAndFooterRecyclerViewAdapter;
  private View headerView;
  private int type = 1;
  private RefreshLayout refreshLayout;
  private RollPagerView rollPagerView;
  private View newHand, basicKnowledge, taskCenter;
  private MBannersModel bannersModel;
  private MarqueeTextView marqueeTextView;
  private String[] strings;
  private RecyclerView noticeRecycler;
  private RefreshLayout noticeRefreash;
  private View refreash;
  private int pageNo = 1;
  private int pageSize = 10;
  private MNoticeModel noticeModel;

  public IndexFragment() {
  }

  public static IndexFragment newInstance(String param1, String param2) {
    IndexFragment fragment = new IndexFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }

  @Override
  protected View getPreviewLayout(LayoutInflater inflater, ViewGroup container) {
    return inflater.inflate(R.layout.layout_preview, container, false);
  }

  @Override
  protected void onCreateViewLazy(Bundle savedInstanceState) {
    super.onCreateViewLazy(savedInstanceState);
    create(R.layout.fragment_index);
    getProductListPresenter = new GetProductListPresenter(userHttpClient, this);
    getBannersPresenter = new GetBannersPresenter(userHttpClient, this);
    getBroadCastPresenter = new GetBroadCastPresenter(userHttpClient, this);
    getNoticePresenter = new GetNoticePresenter(this, userHttpClient);
    View view = getRootView();
    setContentView(view);
    initViews(view);
    initListener();
  }

  @Override
  public void onDestroyViewLazy() {
    super.onDestroyViewLazy();
    if (getProductListPresenter != null) {
      getProductListPresenter.dispose();
      getProductListPresenter = null;
    }
    if (getBannersPresenter != null) {
      getBannersPresenter.dispose();
      getBannersPresenter = null;
    }
    if (getBroadCastPresenter != null) {
      getBroadCastPresenter.dispose();
      getBroadCastPresenter = null;
    }
    cancelTimer();
  }

  @Override
  public void getNotice(int pageNo, int pageSize) {
    getNoticePresenter.getNotice(pageNo, pageSize);
  }

  private class BroadCastHandler extends Handler {
    WeakReference<MainActivity> weakReference;

    public BroadCastHandler(MainActivity activity) {
      weakReference = new WeakReference<>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      if (weakReference != null) {
        getBroadcast();
      }
    }
  }

  @Override
  public void onResumeLazy() {
    super.onResumeLazy();
    startScheduleJob(new BroadCastHandler((MainActivity) getActivity()), 0, 60 * 1000);
  }

  @Override
  protected void onFragmentStartLazy() {
    super.onFragmentStartLazy();
    getProductList(type);
    getBanners();
    getNotice(pageNo, pageSize);
  }

  @Override
  public void initListener() {
    newHand.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.experienceActivity).withOptionsCompat(Utils.getActivityOptionsCompat(v))
        .navigation(IndexFragment.this.getActivity()));
    basicKnowledge.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.webActivity
    ).withString("url", RouterConfig.UrlConfig.basicKnowledge).withOptionsCompat(Utils.getActivityOptionsCompat(v)).withBoolean("hasHost", false)
        .navigation(IndexFragment.this.getActivity()));
    rollPagerView.setOnItemClickListener(position -> {
      if ("goto-web".equals(bannersModel.getData().getBannerList().get(position).getProtocal())) {
        ARouter.getInstance().build(RouterConfig.webActivity).withOptionsCompat(Utils.getActivityOptionsCompat(rollPagerView)).withString("url", bannersModel.getData().getBannerList().get(position).getPageUrl())
            .withBoolean("hasHost", true).navigation(IndexFragment.this.getActivity());
      } else {
        try {
          ARouter.getInstance().build(bannersModel.getData().getBannerList().get(position).getPageUrl()).withOptionsCompat(Utils.getActivityOptionsCompat(rollPagerView))
              .navigation(IndexFragment.this.getActivity());
        } catch (Exception e) {
          ToastWithIcon.init(String.format("路径%s跳转失败", bannersModel.getData().getBannerList().get(position).getPageUrl())).show();
        }
      }
    });
    taskCenter.setOnClickListener(v -> ARouter.getInstance().build(RouterConfig.taskCenterActivity).navigation());
    noticeRefreash.setOnLoadmoreListener(new OnLoadmoreListener() {
      @Override
      public void onLoadmore(RefreshLayout refreshlayout) {
        if (pageNo * pageSize < noticeModel.getTotal()) {
          ++pageNo;
          getNotice(pageNo, pageSize);
        } else {
          noticeRefreash.finishLoadmore(true);
        }

      }
    });
    refreshLayout.setOnRefreshListener(new OnRefreshListener() {
      @Override
      public void onRefresh(RefreshLayout refreshlayout) {
        getProductList(type);
      }
    });
    refreash.setOnClickListener(v -> {
      pageNo = 1;
      getNotice(pageNo, pageSize);
    });
  }

  @Override
  public void initViews(View rootView) {
    headerView = LayoutInflater.from(context).inflate(R.layout.product_list_header, null, false);
    newHand = headerView.findViewById(R.id.ll_new_hand);
    basicKnowledge = headerView.findViewById(R.id.ll_basic_knowledge);
    taskCenter = headerView.findViewById(R.id.ll_task_center);
    refreshLayout = headerView.findViewById(R.id.smart_refresh_layout);
    noticeRefreash = rootView.findViewById(R.id.smart_refresh_layout);
    refreshLayout.setEnableRefresh(true);
    refreshLayout.setEnableLoadmore(false);
    noticeRefreash.setEnableRefresh(false);
    noticeRefreash.setEnableLoadmore(true);
    recyclerView = headerView.findViewById(R.id.rcl_product_list);
    refreash = headerView.findViewById(R.id.refreash);
    LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(context);
    linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
    recyclerView.setLayoutManager(linearLayoutManager2);
    noticeRecycler = rootView.findViewById(R.id.rcl_notice_list);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    noticeRecycler.setLayoutManager(linearLayoutManager);
    noticeAdapter = new NoticeAdapter(noticeRecycler, new ClickResponseListener() {
      @Override
      public void onWholeClick(int position) {
        ARouter.getInstance().build(RouterConfig.webActivity)
            .withString("url", noticeModel.getData().getNewsList().get(position).getLink()).withBoolean("hasHost", true)
            .withString("title", "资讯详情").navigation();
      }
    }, noticeModel);
    noticeHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(noticeAdapter);
    noticeHeaderAndFooterRecyclerViewAdapter.addHeaderView(headerView);
    noticeRecycler.setAdapter(noticeHeaderAndFooterRecyclerViewAdapter);

    productListAdapter = new ProductListAdapter(recyclerView, this, null);
    headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(productListAdapter);
    recyclerView.setAdapter(headerAndFooterRecyclerViewAdapter);
    rollPagerView = headerView.findViewById(R.id.rl_banner);
    marqueeTextView = headerView.findViewById(R.id.notice_text);
  }

  @Override
  public void getProductList(int type) {
    getProductListPresenter.getProductList(type);
  }

  @Override
  public void getBanners() {
    getBannersPresenter.getBanners();
  }

  @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("RCN_REDUNDANT_NULLCHECK_OF_NONNULL_VALUE")
  @Override
  public BaseModel requestCallBack(BaseModel sv) {
    if (sv instanceof MProductListModel) {
      refreshLayout.finishRefresh(true);
      refreshLayout.finishLoadmore(true);
      ProductListVM.getInstance().setProductListModel((MProductListModel) sv);
      productListAdapter.updateData((MProductListModel) sv);
    } else if (sv instanceof MBannersModel) {
      bannersModel = (MBannersModel) sv;
      rollPagerView.setAdapter(new BannerAdapter(bannersModel.getData(), context));
    } else if (sv instanceof MProductOrderBrodcastModel) {
      if (((MProductOrderBrodcastModel) sv) != null && ((MProductOrderBrodcastModel) sv).getData() != null && ((MProductOrderBrodcastModel) sv).getData().getProductOrderBrodcastList() != null) {
        strings = new String[((MProductOrderBrodcastModel) sv).getData().getProductOrderBrodcastList().size()];
        for (int i = 0; i < ((MProductOrderBrodcastModel) sv).getData().getProductOrderBrodcastList().size(); i++) {
          strings[i] = ((MProductOrderBrodcastModel) sv).getData().getProductOrderBrodcastList().get(i).getBroadcastText() + "   " + ((MProductOrderBrodcastModel) sv).getData().getProductOrderBrodcastList().get(i).getTimeText();
        }
        marqueeTextView.initMarqueeTextView(strings, view -> {

        });
      }
    } else if (sv instanceof MNoticeModel) {
      noticeRefreash.finishRefresh(true);
      noticeRefreash.finishLoadmore(true);
      if (pageNo > 1) {
        noticeModel.getData().getNewsList().addAll(((MNoticeModel) sv).getData().getNewsList());
      } else {
        noticeModel = (MNoticeModel) sv;
      }
      noticeAdapter.updateData(noticeModel);
    }
    return super.requestCallBack(sv);
  }

  @Override
  public void onWholeClick(int position) {
    if (getActivity() != null && isAdded()) {
      ((MainActivity) getActivity()).changItem(1);
      ((MainActivity) getActivity()).changeTradeItem(position, true);
    }
  }

  @Override
  public void showError(String s, View.OnClickListener listener) {
    super.showError(s, listener);
  }


  @Override
  public void showError() {
    super.showError();
  }

  @Override
  public void showError(String s) {
    super.showError(s);
  }

  @Override
  public void showError(Throwable throwable) {
    super.showError(throwable);
  }

  @Override
  public void onPauseLazy() {
    super.onPauseLazy();
  }

  @Override
  public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    if (hidden) {
      onPauseLazy();
    }
  }

  @Override
  public void getBroadcast() {
    getBroadCastPresenter.getBroadcast();
  }
}
