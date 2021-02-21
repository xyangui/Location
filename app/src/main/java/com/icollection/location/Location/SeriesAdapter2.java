package com.icollection.location.Location;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.icollection.location.Data.Location.LocationGet2;
import com.icollection.location.R;

import java.util.List;

public class SeriesAdapter2 extends BaseQuickAdapter<LocationGet2, BaseViewHolder> {

    private Context context;
    private boolean isEbay;

    public SeriesAdapter2(List<LocationGet2> data, Context context, boolean isEbay) {
        super(R.layout.item_series, data);
        this.context = context;
        this.isEbay = isEbay;
    }

    @Override
    protected void convert(BaseViewHolder helper, LocationGet2 item) {

        helper.setText(R.id.text_bcode, item.getBcode());
        helper.setText(R.id.text_des, item.getDescription());

        if(isEbay) {
            helper.setText(R.id.text_pl, item.getLocation_list().get_EB_Location());
        } else {
            helper.setText(R.id.text_pl, item.getLocation_list().get_PL_Location());
        }

//        helper.addOnClickListener(R.id.image_like_red);
//        helper.addOnClickListener(R.id.image_like_black);
//        helper.addOnClickListener(R.id.text_follow);
//        helper.addOnClickListener(R.id.text_private_message);
//        helper.addOnClickListener(R.id.image_interest_comment);
//
//        //如果是自己发的动态，隐藏私信
//        if(Integer.valueOf(item.getUser_id()) == StaticData.getUserId()){
//            helper.getView(R.id.text_private_message).setVisibility(View.INVISIBLE);
//        }
//
//        //圆形头像
//        if (!TextUtils.isEmpty(item.getUser_icon())){
//
//            Glide.with(mContext)
//                    .load(Http.API_URL_IMAGE + item.getUser_icon())
//                    .into((ImageView) helper.getView(R.id.circle_image_user_icon));
//        }
//        //城市
//        CityRepository
//                .getInstance()
//                .getCityNameById(String.valueOf(item.getCity_id()))
//                .doOnNext(cityName -> {
//                    helper.setText(R.id.text_city,cityName);
//                });
//
//        //城市前面图标变颜色
//        int color;
//        switch (item.getCity_id()){
//            case 1:
//                color = R.color.Melbourne;
//                break;
//            case 2:
//                color = R.color.Sydney;
//                break;
//            case 3:
//                color = R.color.Adelaide;
//                break;
//            case 4:
//                color = R.color.Brisbane;
//                break;
//            case 6:
//                color = R.color.Perth;
//                break;
//            default:
//                color = R.color.defaultCity;
//        }
//        //城市前面图标变颜色
//        ImageView imageView = helper.getView(R.id.image_location_icon);
//        Drawable wrappedDrawable = DrawableCompat.wrap(imageView.getDrawable());
//        DrawableCompat.setTintList(wrappedDrawable,
//                ColorStateList.valueOf(context.getResources().getColor(color)));
//        imageView.setImageDrawable(wrappedDrawable);
//
//        //图片
//        if (!TextUtils.isEmpty(item.getImage_url())) {
//
//            GridView myGridView = helper.getView(R.id.my_grid_view);
//            myGridView.setAdapter(new ContentImageAdapter(item.getImage_url(), context));
//        }
//
//        //名字
//        if (TextUtils.isEmpty(item.getUser_name())) {
//            helper.setText(R.id.text_username, "无");
//        } else {
//            helper.setText(R.id.text_username, item.getUser_name());
//        }
//
//        //内容
//        if (TextUtils.isEmpty(item.getContent())) {
//            helper.setText(R.id.text_content, "无");
//        } else {
//            helper.setText(R.id.text_content, item.getContent());
//        }
//
//        //评论数量
//        if(item.getNum_of_comments() == 0){
//            helper.getView(R.id.text_comment_num).setVisibility(View.GONE);
//        } else {
//            helper.getView(R.id.text_comment_num).setVisibility(View.VISIBLE);
//            helper.setText(R.id.text_comment_num, String.valueOf(item.getNum_of_comments()));
//        }
//
//        //点赞数量
//        if(item.getLikes() == 0){
//            helper.getView(R.id.text_like_num).setVisibility(View.GONE);
//        } else {
//            helper.setText(R.id.text_like_num, String.valueOf(item.getLikes()));
//        }
//
//        //是否已经点赞
//        if(item.getLike_status() == 1){
//            helper.getView(R.id.image_like_black).setVisibility(View.GONE);
//            helper.getView(R.id.image_like_red).setVisibility(View.VISIBLE);
//        } else {
//            helper.getView(R.id.image_like_black).setVisibility(View.VISIBLE);
//            helper.getView(R.id.image_like_red).setVisibility(View.GONE);
////            ((ImageView) helper.getView(R.id.image_like))
////                    .setImageResource(R.mipmap.like_black);
//        }
//
//        //是否已经关注
//        TextView textFollow = (TextView)helper.getView(R.id.text_follow);
//        if(item.getFollow_status() == 1){
//            textFollow.setText("已关注");
//            textFollow.setTextColor(context.getResources().getColor(R.color.gray));
//            textFollow.setBackground(context.getResources().getDrawable(R.drawable.yellow_background_black_text2_gray));
//        } else {
//            textFollow.setText("+关注");
//            textFollow.setTextColor(context.getResources().getColor(R.color.black));
//            textFollow.setBackground(context.getResources().getDrawable(R.drawable.yellow_background_black_text2));
//        }
    }

//    private Drawable tintDrawable(Drawable drawable ,ColorStateList colors) {
//        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
//        DrawableCompat.setTintList(wrappedDrawable, colors);
//        return wrappedDrawable;
//    }
}

