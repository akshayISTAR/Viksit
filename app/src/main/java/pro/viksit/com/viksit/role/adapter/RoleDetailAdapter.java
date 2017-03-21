package pro.viksit.com.viksit.role.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import pro.viksit.com.viksit.R;
import pro.viksit.com.viksit.role.pojo.Role;

/**
 * Created by Akshay on 18/03/2017.
 */

public class RoleDetailAdapter extends RecyclerView.Adapter<RoleDetailAdapter.MyViewHolder>  {

    private List<Role> roles;
    private Context context;
    public RoleDetailAdapter(List<Role> roles,Context context) {
        this.roles = roles;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.role_details_card, parent, false);

        //
         return new MyViewHolder(itemView,viewType);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Role role = roles.get(position);
        holder.title.setText(role.getTitle());
    }

    @Override
    public int getItemCount() {
        return roles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView image;
        public TextView title;
        public CardView cardview;
        public RelativeLayout relativeLayout;
        public MyViewHolder(View view,int viewType) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            image = (ImageView) view.findViewById(R.id.image);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.label_layout);
            for(int i=1;i<10;i++) {
                TextView tx = new TextView(context);
                tx.setId(i);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                tx.setBackground(context.getResources().getDrawable(R.drawable.pill_bg));
                tx.setText("Laurnjhh"+i);
                tx.setTextSize(TypedValue.COMPLEX_UNIT_SP,14f);
                if(i!=1) {
                    if(i% 2 ==0) {
                        params.addRule(RelativeLayout.RIGHT_OF, (i - 1));
                        params.addRule(RelativeLayout.BELOW, (i - 2));
                    }
                    else
                        params.addRule(RelativeLayout.BELOW, (i - 2));

                }else{
                    params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                    params.addRule(RelativeLayout.ALIGN_PARENT_START);

                }
                tx.setPadding(10, 10, 10, 10);
                tx.setLayoutParams(params);
                relativeLayout.addView(tx);
            }
        }

        @Override
        public void onClick(View view) {

        }

    }

}