package com.ljheee.studyclock.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ljheee.studyclock.R;
import com.ljheee.studyclock.bean.SinglePlan;

import java.util.ArrayList;

/**
 * 自定义适配器
 */
public class PlanAdapter extends BaseAdapter {

    /**
     * 上下文
     */
    Context context;

    /**
     * 数据
     */
//    File[] files;

    // 或者列表（对象）
    ArrayList<SinglePlan> fileList;


    // 加载、解析 XML（系统服务 XmlPullParser xpp）
    LayoutInflater layoutInflater;

    /**
     * 文件适配器：构造方法
     *
     * @param context   上下文
     * @param fileList     数据
     */
    public PlanAdapter(Context context, ArrayList<SinglePlan> fileList) {
        this.context = context;
        this.fileList = fileList;

        // 获得服务实例
        layoutInflater = LayoutInflater.from(context);

//        layoutInflater = (LayoutInflater) context.getSystemService(
//                Context.LAYOUT_INFLATER_SERVICE);
    }



    /**
     * 获得数据总数
     *
     * @return
     */
    @Override
    public int getCount() {
        return fileList.size();
    }

    /**
     * 获得特定位置的数据
     *
     * @param i 位置
     * @return
     */
    @Override
    public SinglePlan getItem(int i) {
        return fileList.get(i);
    }

    /**
     * 获得特定位置数据的 ID
     *
     * @param i 位置
     * @return  数据在数据库中 PK(id)
     */
    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * 创建视图项
     *
     * @param i             位置
     * @param convertView   可复用的视图，可能 null (需要新建)
     * @param viewGroup     适配器视图
     * @return
     */
    @Override
    public View getView(
            int i,
            View convertView,
            ViewGroup viewGroup) {

        ViewHolder holder;

        if (convertView == null) {
            // 没有可复用，需要创建
            // 开销很大 加载文件、XML 解析 控件和布局的
            convertView = layoutInflater.inflate(R.layout.plan_item, null);

            // 每个视图项需要一个 viewHolder
            // 构造ViewHolder把View convertView传给它
            holder = new ViewHolder(convertView);

            // 视图项关联了它的视图结构
            convertView.setTag(holder);
        } else {
            // 有复用视图项，不创建，并直接获得结构
            holder = (ViewHolder) convertView.getTag();
        }


        // 加载数据
        holder.bindData(fileList.get(i));

        return convertView;
    }

    /**
     * ViewHolder 模式
     */
    static class ViewHolder {

        ImageView icon;
        TextView title;
        TextView info;
        ImageButton action;
        int id;


        /**
         * 构造方法
         * 得到View v   通过findViewById获得布局引用
         * @param v
         */
        public ViewHolder(View v) {
            icon = (ImageView) v.findViewById(R.id.imageView_icon);
            title = (TextView) v.findViewById(R.id.textView_name);
            info = (TextView) v.findViewById(R.id.textView_info);
            action = (ImageButton) v.findViewById(R.id.imageButton_action);
        }


        public void bindData(SinglePlan plan) {
//            icon.setImageResource(
//                    file.isDirectory()
//                            ? R.drawable.ic_folder
//                            : R.drawable.ic_more_vert_black_24dp);
            icon.setImageResource(R.mipmap.ic_plan);

            title.setText(plan.getPlanName());
        }
    }



}