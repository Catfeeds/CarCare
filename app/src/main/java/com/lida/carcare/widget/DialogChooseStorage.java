package com.lida.carcare.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lida.carcare.R;
import com.lida.carcare.bean.CurrentStoreUserBean;
import com.lida.carcare.bean.StorageListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 选择仓库
 * Created by WeiQingFeng on 2016/10/28 0028.
 */

public class DialogChooseStorage extends Dialog {
    @BindView(R.id.listView)
    InnerListView listView;

    private Context context;
    private TextView textView;
    private List<StorageListBean.DataBean> data;
    private onItemClickListener listener;

    public DialogChooseStorage(Context context, TextView textView, List<StorageListBean.DataBean> data) {
        super(context, R.style.diy_dialog);
        this.textView = textView;
        this.data = data;
        init(context);
    }

    public DialogChooseStorage(Context context, int themeResId) {
        super(context, R.style.diy_dialog);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        Window w = this.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        w.setAttributes(lp);
        this.setCanceledOnTouchOutside(true);
        View v = View.inflate(context, R.layout.dialog_choosegrade, null);
        this.setContentView(v);
        ButterKnife.bind(this, v);
        listView.setAdapter(new Adapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(data.get(position).getRepertoryName());
                textView.setTag(data.get(position).getId());
                if(listener!=null){
                    listener.onItemClicked(position);
                }
                dismiss();
            }
        });
    }

    class Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public Object getItem(int position) {
            return data == null ? 0 : position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_grade, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tv.setText(data.get(position).getRepertoryName());
            viewHolder.tv.setTag(data.get(position).getId());
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.tv)
            TextView tv;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }

    public interface onItemClickListener{
        void onItemClicked(int position);
    }
}
