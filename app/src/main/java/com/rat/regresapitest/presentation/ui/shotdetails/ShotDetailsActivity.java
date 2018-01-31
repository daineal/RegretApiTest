package com.rat.regresapitest.presentation.ui.shotdetails;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.greenfrvr.hashtagview.HashtagView;
import com.rat.regresapitest.R;
import com.rat.regresapitest.RatApplication;
import com.rat.regresapitest.di.shotdetails.DaggerShotDetailsPresenterComponent;
import com.rat.regresapitest.di.shotdetails.ShotDetailsPresenterComponent;
import com.rat.regresapitest.di.shotdetails.ShotDetailsPresenterModule;
import com.rat.regresapitest.domain.global.models.Shot;
import com.rat.regresapitest.domain.global.models.UserData;
import com.rat.regresapitest.presentation.mvp.shotdetails.ShotDetailsPresenter;
import com.rat.regresapitest.presentation.mvp.shotdetails.ShotDetailsView;
import com.rat.regresapitest.presentation.ui.global.BaseMvpActivity;
import com.rat.regresapitest.presentation.ui.global.commons.AndroidPermissionsManager;
import com.rat.regresapitest.presentation.ui.global.commons.EndlessRecyclerOnScrollListener;
import com.rat.regresapitest.presentation.ui.global.commons.glide.GlideCircleTransform;
import com.rat.regresapitest.presentation.ui.global.views.dribbbletextview.RatTextView;
import com.rat.regresapitest.presentation.utils.AppUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ShotDetailsActivity extends BaseMvpActivity implements ShotDetailsView {

    private static final String KEY_SHOT_ID = "shot_id";

    public static Intent buildIntent(Context context, long shotId) {
        Intent intent = new Intent(context, ShotDetailsActivity.class);
        intent.putExtra(KEY_SHOT_ID, shotId);
        return intent;
    }

    private CoordinatorLayout shotDetailContainer;
    private Toolbar toolbar;

    private View loadingLayout;
    private View noNetworkLayout;

    private RecyclerView commentsList;
    private ImageView shotImage;
    private ProgressBar shotImageProgressBar;
    private View shotDescription;
    private TextView title;
    private RatTextView description;
    private TextView shotCreateDate;
    private View userProfileLayout;
    private TextView userName;
    private ImageView userAvatar;
    private TextView likesCount;
    private TextView viewsCount;
    private TextView bucketsCount;
    private TextView shareShotButton;
    private HashtagView hashtagView;
    private LinearLayoutManager commentsListLayoutManager;
    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH);

    @InjectPresenter
    ShotDetailsPresenter shotDetailsPresenter;

    @ProvidePresenter
    ShotDetailsPresenter providePresenter() {
        long shotId = getIntent().getLongExtra(KEY_SHOT_ID, -1);
        ShotDetailsPresenterModule presenterModule = new ShotDetailsPresenterModule(shotId);

        ShotDetailsPresenterComponent presenterComponent = DaggerShotDetailsPresenterComponent.builder()
                .applicationComponent(RatApplication.getComponent())
                .shotDetailsPresenterModule(presenterModule)
                .build();

        return presenterComponent.getPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot_details);

        initToolbar();
        initViews();

        shotDetailsPresenter.setPermissionsManager(new AndroidPermissionsManager(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        shotDetailsPresenter.removePermissionsManager();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void initViews() {
        shotDetailContainer = (CoordinatorLayout) findViewById(R.id.shot_detail_container);

        loadingLayout = findViewById(R.id.loading_layout);
        noNetworkLayout = findViewById(R.id.no_network_layout);
        noNetworkLayout.findViewById(R.id.retry_button).setOnClickListener(v -> shotDetailsPresenter.retryLoading());

        commentsList = (RecyclerView) findViewById(R.id.shot_comments);
        commentsListLayoutManager = new LinearLayoutManager(this);
        commentsList.setLayoutManager(commentsListLayoutManager);
        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(commentsListLayoutManager) {
            @Override
            public void onLoadMore() {
                shotDetailsPresenter.onLoadMoreCommentsRequest();
            }
        };

        shotImage = (ImageView) findViewById(R.id.shot_image);
        shotImageProgressBar = (ProgressBar) findViewById(R.id.shot_image_progress_bar);
        shotDescription = getLayoutInflater().inflate(R.layout.item_shot_description, commentsList, false);
        title = shotDescription.findViewById(R.id.shot_title);
        description = shotDescription.findViewById(R.id.shot_description);
        userProfileLayout = shotDescription.findViewById(R.id.user_profile_layout);
        userName = shotDescription.findViewById(R.id.user_name);
        userAvatar = shotDescription.findViewById(R.id.user_avatar);
        shotCreateDate = shotDescription.findViewById(R.id.shot_create_date);

        shotImage.setOnClickListener(v -> shotDetailsPresenter.onImageClicked());

        likesCount = shotDescription.findViewById(R.id.shot_likes_count);
        viewsCount = shotDescription.findViewById(R.id.shot_views_count);
        bucketsCount = shotDescription.findViewById(R.id.shot_buckets_count);
        shareShotButton = shotDescription.findViewById(R.id.share_shot);
        hashtagView = shotDescription.findViewById(R.id.shot_tags);
        hashtagView.addOnTagClickListener((item) -> {
            String tag = (String) item;
            shotDetailsPresenter.onTagClicked(tag);
            Toast.makeText(this, tag, Toast.LENGTH_SHORT).show();
        });

        likesCount.setOnClickListener(v -> shotDetailsPresenter.onLikeShotClicked());
    }

    @Override
    public void showShot(UserData shot) {
        shotDetailContainer.setVisibility(View.VISIBLE);

        //shot info
        title.setText(shot.getFirstName());
        shotCreateDate.setText(dateFormat.format(shot.getId()));
        if (shot.getLastName() != null) {
            description.setHtmlText(shot.getLastName());
        } else {
            description.setVisibility(View.GONE);
        }

        Glide.with(this)
                .load(shot.getAvatar())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .crossFade()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        shotDetailsPresenter.onImageLoadError();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        shotDetailsPresenter.onImageLoadSuccess();
                        return false;
                    }
                })
                .into(shotImage);

//        likesCount.setText(getResources().getQuantityString(R.plurals.likes, shot.getLikesCount(), shot.getLikesCount()));
//        viewsCount.setText(getResources().getQuantityString(R.plurals.views, shot.getViewsCount(), shot.getViewsCount()));
//        bucketsCount.setText(getResources().getQuantityString(R.plurals.buckets, shot.getBucketsCount(), shot.getBucketsCount()));
//        hashtagView.setData(shot.getTags());

        //user info
//        userName.setText(shot.getUser().getName());
//        Glide.with(this)
//                .load(shot.getUser().getAvatarUrl())
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .transform(new GlideCircleTransform(this))
//                .into(userAvatar);

        showToolbarMenu();
    }

    private void showToolbarMenu() {
        toolbar.inflateMenu(R.menu.shot_details);
        toolbar.setOnMenuItemClickListener(this::onToolbarItemSelected);
    }

    private boolean onToolbarItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.download_shot_image:
                shotDetailsPresenter.onDownloadImageClicked();
                return true;
            case R.id.open_in_browser:
//                shotDetailsPresenter.onOpenShotInBrowserClicked();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void showLoadingProgress() {
        loadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingProgress() {
        loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void showImageSavedMessage() {
        Snackbar.make(shotDetailContainer, R.string.image_saved_to_downloads_folder, Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void showStorageAccessRationaleMessage() {
        new AlertDialog.Builder(this, R.style.AppTheme_MaterialDialogStyle)
                .setTitle(R.string.storage_access_title)
                .setMessage(R.string.storage_access_rationale_message)
                .setPositiveButton(R.string.storage_access_ok_button, (dialog, which) -> shotDetailsPresenter.onDownloadImageClicked())
                .show();
    }

    @Override
    public void showAllowStorageAccessMessage() {
        new AlertDialog.Builder(this, R.style.AppTheme_MaterialDialogStyle)
                .setTitle(R.string.storage_access_title)
                .setMessage(R.string.storage_access_message)
                .setPositiveButton(R.string.storage_access_settings_button, (dialog, which) -> shotDetailsPresenter.onAppSettingsButtonClicked())
                .show();
    }

    @Override
    public void openAppSettingsScreen() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    @Override
    public void showNoNetworkLayout() {
        noNetworkLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoNetworkLayout() {
        noNetworkLayout.setVisibility(View.GONE);
    }

    @Override
    public void hideImageLoadingProgress() {
        shotImageProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}